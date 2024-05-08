import java.util.Scanner;

public class Player {
    private PlayerData playerData;
    private boolean hasMoved;
    private Card currentCard;

    public Player(String playerName, Set playerSet, int rank, int dollars, int credits, int rehearseChips, int role) {
        this.playerData = new PlayerData(playerName, playerSet, rank, dollars, credits, rehearseChips, role);
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
        Dice diceRoll = new Dice();
        int rollNum = diceRoll.rollDice();
        int rehearseChips = playerData.getrehearseChips();
        System.out.println("You rolled a: " + rollNum + "and recieve a " + rehearseChips + " bonus!");
        if(rollNum + rehearseChips >= currentCard.budget) {
            System.out.println("Success!");
            return true;
        } else {
            return false;
        }
    }

    public boolean rehearse() {
        return false;
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
