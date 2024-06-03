import java.util.Arrays;
import java.util.Scanner;

public class Player {
    private PlayerData playerData;
    private boolean hasMoved;
    Controller controller;

    public Player(String playerName, Set playerSet, int rank, int dollars, int credits,Controller c) {
        controller = c;
        this.playerData = new PlayerData(playerName, playerSet, rank, dollars, credits, 0, null);
        this.hasMoved = false;
    }

    // public void play() {
    //     Scanner scan = new Scanner(System.in);
    // boolean keepGoing = true;
    // while (keepGoing) {
    //     //Print player info each turn
    //     System.out.println("\nPlayer Info:");
    //     System.out.println("Rank: " + playerData.getRank());
    //     System.out.println("Credits: " + playerData.getCredits());
    //     System.out.println("Dollars: " + playerData.getDollars());
    //     System.out.println();


    //     String currentSet = playerData.getplayerSet().getName();
    //     System.out.println("You are currently at: " + currentSet);
    //     System.out.println("Choose your move...");
    //     System.out.println("3. Move");

    //     if (currentSet.equalsIgnoreCase("Office")) {
    //         System.out.println("5. Upgrade");
    //     } else if (!currentSet.equalsIgnoreCase("Trailer")) {
    //         System.out.println("1. Act");
    //         System.out.println("2. Rehearse");
    //         System.out.println("4. Take Role");
    //     }

    //     System.out.println("6. End Turn");
    //     int choice = scan.nextInt();

    //     switch (choice) {
    //         case 1:
    //             if (currentSet.equalsIgnoreCase("Office") || currentSet.equalsIgnoreCase("Trailer")) {
    //                 System.out.println("Invalid Choice. Choose again.");
    //                 continue;
    //             }
    //             if (!act()) {
    //                 System.out.println("This move did not work");
    //                 continue;
    //             } else {
    //                 endTurn();
    //                 keepGoing = false;
    //             }
    //             break;
    //         case 2:
    //             if (currentSet.equalsIgnoreCase("Office") || currentSet.equalsIgnoreCase("Trailer")) {
    //                 System.out.println("Invalid Choice. Choose again.");
    //             }
    //             else if(playerData.getRole() == null) {
    //                 System.out.println("\nYou must be on a role\n");
    //             }
    //             else if (!rehearse()) {
    //                 System.out.println("This move did not work");
    //             } else {
    //                 endTurn();
    //                 keepGoing = false;
    //             }
    //             break;
    //         case 3:
    //             if (!move()) {
    //                 System.out.println("This move did not work");
    //             } else {
    //                 System.out.println("You have moved");
    //             }
    //             break;
    //         case 4:
    //             if (currentSet.equalsIgnoreCase("Office") || currentSet.equalsIgnoreCase("Trailer")) {
    //                 System.out.println("Invalid Choice. Choose again.");
    //                 continue;
    //             }
    //             System.out.println("Choose a role...");
    //             Role[] roles = playerData.getplayerSet().getRoles();
    //             for (int i = 0; i < roles.length; i++) {
    //                 System.out.println(i + ":  " + roles[i].name + "  " + roles[i].rank + "  " + roles[i].catchPhrase);
    //             }
    //             int roleChoice = scan.nextInt();
    //             if (roleChoice < 0 || roleChoice >= roles.length) {
    //                 System.out.println("This move did not work");
    //             } else { //If role was already chosen
    //                 Role chosenRole = roles[roleChoice];
    //                 if (!takeRole(chosenRole)) {
    //                     System.out.println("This move did not work");
    //                 } else {
    //                     endTurn();
    //                     return;
    //                 }
    //             }
    //             break;
    //         case 5:
    //             System.out.println("Choose a rank to upgrade to...");
    //             int rank = scan.nextInt();
    //             if (!upgrade(rank)) {
    //                 System.out.println("This move did not work");
    //             } else {
    //                 System.out.println("You have upgraded...");
    //             }
    //             break;
    //         case 6:
    //             endTurn();
    //             return;
    //         default:
    //             System.out.println("Invalid choice. Please choose again.");
    //     }
    // }
        

    // }

    //tells the set that is attached to playerData that we are acting
    public boolean act() {
        if (playerData.getRole() == null) return false;
        Dice diceRoll = new Dice();
        int rollNum = diceRoll.rollDice();
        controller.roleDice(rollNum); // this will display visual result
        int rehearseChips = playerData.getrehearseChips();
        int roleType = playerData.getRole().getroleType();
        controller.pushText("You rolled a: " + rollNum + "and have a " + rehearseChips + " bonus!"); // THis can be moved to controller to be in view text field
        if(rollNum + rehearseChips >= playerData.getplayerSet().getCard().budget) {
            controller.pushText("Success!");
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
            controller.pushText("Failure! off-card reward given...");
            return true;
        }
        else {
            controller.pushText("Failure!");
            return true;
        }
    }

    public boolean rehearse() {

        if(playerData.getrehearseChips() == playerData.getplayerSet().getCard().budget - 1) {
            controller.pushText("You have guaranteed success! Try acting!");
            return false;
        } else {
            playerData.addRehearseChips(1);
            return true;
        }
    }

    public boolean move(Set target) {
        if(hasMoved) {
            controller.pushText("You have already moved in this turn.");
            return false;
        }
        if (playerData.getRole() != null) {
            controller.pushText("You are acting on a role!!");
            return false;
        }
        if (!Arrays.asList(getneighbors()).contains(target)) {
            controller.pushText("This is not a valid move");
            return false;
        }
        //we know it is a neighbor and we can move at this point
        playerData.setplayerSet(target);
        hasMoved = true;
        int x = playerData.getplayerSet().getCoords(Integer.parseInt(playerData.getplayerName()))[0];
        int y = playerData.getplayerSet().getCoords(Integer.parseInt(playerData.getplayerName()))[1];
        playerData.getplayerSet().visit();
        controller.updateIcon(this,x,y);
        return true;

    }
    public Set[] getneighbors() {
        return this.playerData.getplayerSet().getNeighborSets();
    }
    
    public boolean takeRole(Role r) {
        if (playerData.getplayerSet().isWrapped()) {
            controller.pushText("The set is already wrapped");
            return false;
        }
        if (playerData.getRank() < r.rank) {
            controller.pushText("Level too low");
            return false;
        }
        if (playerData.getRole() != null) {
            controller.pushText("Already on role");
            return false;
        }
        if (!playerData.getplayerSet().takeRole(r,this)) {
            return false;
        }//attempts and returns boolean
        playerData.setRole(r);
        controller.updateIcon(this, r.getX(), r.getY());
        //NEED TO MOVE PLAYER TO THE COORDS OF THE ROLE
        return true;
    }

    public boolean upgrade(int rank, String currency) {

        //Check if player is in casting office
        Set currentSet = playerData.getplayerSet();
        if(!currentSet.getName().equalsIgnoreCase("Office")) {
            controller.pushText("You can only upgrade in the Casting Office.");
            return false;
        }

        if (rank <= playerData.getRank() || rank > 6) {
            controller.pushText("Invalid Rank selected.");
            return false;
        }

        if (!(currency.equalsIgnoreCase("Dollars") || currency.equalsIgnoreCase("Dollar") ||
         currency.equalsIgnoreCase("credit") || currency.equalsIgnoreCase("credits"))) {
            controller.pushText("Invalid Currency selected.");
            return false;
        }

        int cost;
        int[] costDollars = {0,0,4,10,18,28,40};
        int[] costCredits = {0,0,10,15,20,25};
        if(currency.equalsIgnoreCase("dollars") || currency.equalsIgnoreCase("Dollar")) {
            cost = costDollars[rank];
            if (playerData.getDollars() < cost) {
                controller.pushText("Insufficient currency to upgrade.");
                return false;                
            }
            else {
                playerData.setDollars(playerData.getDollars() - cost);
                String color = controller.getPlayerColor(Integer.parseInt(playerData.getplayerName()));
                controller.updateIcon(this, "dice/" + color + rank + ".png");
            }
        }
        if(currency.equalsIgnoreCase("credit") || currency.equalsIgnoreCase("credits")) {
            cost = costCredits[rank];
            if (playerData.getCredits() < cost) {
                controller.pushText("Insufficient currency to upgrade.");
                return false;                
            }
            else {
                playerData.setCredits(playerData.getCredits() - cost);
                String color = controller.getPlayerColor(Integer.parseInt(playerData.getplayerName()));
                controller.updateIcon(this, "dice/" + color + rank + ".png");
            }
        }
        playerData.setRank(rank);
        controller.pushText("Player upgraded to rank " + rank);
        return true;        
    }

    public void endRole() {
        playerData.setRole(null);
        playerData.setrehearseChips(0);
        int x = playerData.getplayerSet().getCoords(Integer.parseInt(playerData.getplayerName()))[0];
        int y = playerData.getplayerSet().getCoords(Integer.parseInt(playerData.getplayerName()))[1];
        //takes the player off the role
        controller.updateIcon(this, x, y);
    }

    public void endTurn() {
        hasMoved = false;
    }
    public void addDollars(int a) {
        playerData.addDollars(a);
    }
    public void addCredits(int a) {
        playerData.addCredits(a);
    }
    public void newPlayerTurn() {
        hasMoved = false;
    }
    public void reset(Set start) {
        playerData.setplayerSet(start);
        playerData.setRole(null);
        playerData.setrehearseChips(0);
    }
    public int getScore() {
        int score = 0;
        score += playerData.getCredits();
        score += playerData.getDollars();
        score += (6 * playerData.getRank());
        return score;
    }
    public Role[] getSetRoles() {
        return playerData.getplayerSet().getRoles();
    }
    public PlayerData getPlayerData() {
        return playerData;
    }
}
