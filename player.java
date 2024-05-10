import java.util.Scanner;

public class Player {
    private PlayerData playerData;
    private boolean hasMoved;

    public Player(String playerName, Set playerSet, int rank, int dollars, int credits) {
        this.playerData = new PlayerData(playerName, playerSet, rank, dollars, credits, 0, null);
        this.hasMoved = false;
    }

    public void play() {
        System.out.println("Choose your move...");
        Scanner scan = new Scanner(System.in);
        int choice = scan.nextInt();

        switch(choice){
        case 1: 
            if(!act()) System.out.println("This move did not work");  
            else {
                endTurn();
            }       
        case 2:
            if(!rehearse()) System.out.println("This move did not work"); 
            else {
                endTurn();
            }
        case 3:  
            System.out.println("Choose a location...");
        
        case 4:

        case 5:

        case 6: 
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

        if(playerData.getrehearseChips() == 6) {
            System.out.println("You have guaranteed success! Try acting!");
            return false;
        } else {
            playerData.addRehearseChips(1);
            return true;
        }
    }

    public boolean move(Location destination) {
        return false;
    }

    public boolean takeRole(Role r) {
        return playerData.getplayerSet().takeRole(r);//attempts and returns boolean
    }

    public boolean upgrade(int rank) {
        return false;
    }

    public void endTurn() {

    }
}
