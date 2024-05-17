import java.util.Scanner;

public class Player {
    private PlayerData playerData;
    private boolean hasMoved;

    public Player(String playerName, Set playerSet, int rank, int dollars, int credits) {
        this.playerData = new PlayerData(playerName, playerSet, rank, dollars, credits, 0, null);
        this.hasMoved = false;
    }

    public void play() {
        Scanner scan = new Scanner(System.in);
    while (true) {
        System.out.println("Choose your move...");
        System.out.println("1. Act");
        System.out.println("2. Rehearse");
        System.out.println("3. Move");
        System.out.println("4. Take Role");
        System.out.println("5. Upgrade");
        System.out.println("6. End Turn");
        int choice = scan.nextInt();

        switch (choice) {
            case 1:
                if (!act()) {
                    System.out.println("This move did not work");
                } else {
                    endTurn();
                    return;
                }
                break;
            case 2:
                if (!rehearse()) {
                    System.out.println("This move did not work");
                } else {
                    endTurn();
                    return;
                }
                break;
            case 3:
                if (!move()) {
                    System.out.println("This move did not work");
                } else {
                    endTurn();
                    return;
                }
                break;
            case 4:
                System.out.println("Choose a role...");
                // Assuming roles are displayed and selected here.
                Role[] roles = playerData.getplayerSet().getRoles();
                for (int i = 0; i < roles.length; i++) {
                    System.out.println(i + ":  " + roles[i].name + "  " + roles[i].rank + "  " + roles[i].catchPhrase);
                }
                if (chosenRole != null && !takeRole(chosenRole)) {
                    System.out.println("This move did not work");
                } else {
                    endTurn();
                    return;
                }
                break;
            case 5:
                System.out.println("Choose a rank to upgrade to...");
                int rank = scan.nextInt();
                if (!upgrade(rank)) {
                    System.out.println("This move did not work");
                } else {
                    endTurn();
                    return;
                }
                break;
            case 6:
                endTurn();
                return;
            default:
                System.out.println("Invalid choice. Please choose again.");
        }
    }
        

    }

    //tells the set that is attached to playerData that we are acting
    public boolean act() {
        if (playerData.getRole() == null) return false;
        Dice diceRoll = new Dice();
        int rollNum = diceRoll.rollDice();
        int rehearseChips = playerData.getrehearseChips();
        int roleType = playerData.getRole().getroleType();
        System.out.println("You rolled a: " + rollNum + "and have a " + rehearseChips + " bonus!");
        if(rollNum + rehearseChips >= playerData.getplayerSet().getCard().budget) {
            System.out.println("Success!");
            if(roleType == 1) {
                playerData.addCredits(2);
            } else if(roleType == 2) {
                playerData.addCredits(1);
                playerData.addDollars(1);
            }
            playerData.getplayerSet().act();
            return true;
        } 
        else  if (roleType == 2) {
            playerData.addDollars(1);
            System.out.println("Failure! off-card reward given...");
            return true;
        }
        else {
            System.out.println("Failure!");
            return true;
        }
    }

    public boolean rehearse() {

        if(playerData.getrehearseChips() == playerData.getplayerSet().getCard().budget - 1) {
            System.out.println("You have guaranteed success! Try acting!");
            return false;
        } else {
            playerData.addRehearseChips(1);
            return true;
        }
    }

    public boolean move() {
        if(hasMoved) {
            System.out.println("You have already moved in this turn.");
            return false;
        }

        Set currentSet = playerData.getplayerSet();
        System.out.println("Choose a destination area:(1, 2, 3, 4)");

        Set[] adjacentSets = currentSet.getNeighborSets();
        //Display available adjacent sets
        for (int i = 0; i < adjacentSets.length; i++) {
            System.out.println((i + 1) + ": " + adjacentSets[i].getName());
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            int choice = scanner.nextInt();

            if(choice >= 1 && choice <= adjacentSets.length) {
                Set destination = adjacentSets[choice - 1];
                System.out.println("Moving to " + destination.getName());
                playerData.setplayerSet(destination);
                hasMoved = true;
                //flip card if needed
                break;
            } else {
                System.out.println("Invalid choice. Choose again.");   
            }
        }
        return true;
    }

    public boolean takeRole(Role r) {
        return playerData.getplayerSet().takeRole(r);//attempts and returns boolean
    }

    public boolean upgrade(int rank) {

        //Check if player is in casting office
        Set currentSet = playerData.getplayerSet();
        if(!currentSet.getName().equals("Casting Office")) {
            System.out.println("You can only upgrade at the Casting Office.");
            return false;
        }

        System.out.println("Upgrade options:");
        System.out.println("Rank 2: Cost - 4 dollars or 5 credits");
        System.out.println("Rank 3: Cost - 10 dollars or 10 credits");
        System.out.println("Rank 4: Cost - 18 dollars or 15 credits");
        System.out.println("Rank 5: Cost - 28 dollars or 20 credits");
        System.out.println("Rank 6: Cost - 40 dollars or 25 credits");

        Scanner scanner = new Scanner(System.in);
        int rankChoice;

        // Player chooses new rank
        while (true) {
            System.out.println("Choose the rank you want to upgrade to: (2-6)");
            rankChoice = scanner.nextInt();
            if (rankChoice >= 2 && rankChoice <= 6) {
                break;
            }
            System.out.println("Invalid choice. Please choose again.");
        }

        //Check if players rank is below chosen upgrade rank
        if (rankChoice <= playerData.getRank()) {
            System.out.println("You cannot upgrade to a rank equal to or lower than your current rank.");
            return false;
        }

        //Player chooses currency type
        int currencyChoice;
        while(true) {
            System.out.println("Choose currency for upgrade: (1. Dollars, 2. Credits)");
            currencyChoice = scanner.nextInt();
            if(currencyChoice == 1 || currencyChoice == 2) {
                break;
            }
            System.out.println("Invalid choice. Please choose again.");
        }

        int cost;
        int[] costDollars = {0,0,4,10,18,28,40};
        int[] costCredits = {0,0,10,15,20,25};
        if(currencyChoice == 1) {
            cost = costDollars[rankChoice];
            if (playerData.getDollars() < cost) {
                System.out.println("Insufficient currency to upgrade.");
                return false;                
            }
            else {
                playerData.setDollars(playerData.getDollars() - cost);
            }
        }
        if(currencyChoice == 2) {
            cost = costCredits[rankChoice];
            if (playerData.getCredits() < cost) {
                System.out.println("Insufficient currency to upgrade.");
                return false;                
            }
            else {
                playerData.setCredits(playerData.getCredits() - cost);
            }
        }
        playerData.setRank(rank);
        System.out.println("Player upgraded to rank " + rank);
        return true;        
    }

    public void endTurn() {
        
    }
    public void addDollars(int a) {
        playerData.addDollars(a);
    }
    public void addCredits(int a) {
        playerData.addCredits(a);
    }
}
