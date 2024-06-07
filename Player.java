import java.util.Arrays;

public class Player {
    private PlayerData playerData;
    private boolean hasMoved;
    private boolean turnOver = false;
    Controller controller;

    public Player(String playerName, Set playerSet, int rank, int dollars, int credits,Controller c) {
        controller = c;
        this.playerData = new PlayerData(playerName, playerSet, rank, dollars, credits, 0, null);
        this.hasMoved = false;
    }

    //tells the set that is attached to playerData that we are acting
    public boolean act() {
        //dont act if turn has already been taked
        if (turnOver) {
            controller.pushText("You have no more moves this turn");
            return false;
        }
        //does not act if not on a role
        if (playerData.getRole() == null) {
            controller.pushText("You must be on a role to act");
            return false;
        }
        //rolls a dice and makes tells controller to make the visual
        Dice diceRoll = new Dice();
        int rollNum = diceRoll.rollDice();
        controller.rollDice(rollNum); // this will display visual result
        int rehearseChips = playerData.getrehearseChips();
        int roleType = playerData.getRole().getroleType();
        controller.pushText("You rolled a: " + rollNum + "and have a " + rehearseChips + " bonus!");
        //check if successful
        if(rollNum + rehearseChips >= playerData.getplayerSet().getCard().budget) {
            controller.pushText("Success!");
            if(roleType == 1) {
                playerData.addCredits(2);
            } else if(roleType == 2) {
                playerData.addCredits(1);
                playerData.addDollars(1);
            }
            playerData.getplayerSet().act();
            turnOver = true;
            controller.updatePlayerData();
            return true;
        } 
        //reward off card
        else  if (roleType == 2) {
            playerData.addDollars(1);
            controller.pushText("Failure! off-card reward given...");
            turnOver = true;
            controller.updatePlayerData();
            return true;
        }
        else {
            controller.pushText("Failure!");
            turnOver = true;
            controller.updatePlayerData();
            return true;
        }
    }
    //Rehearse method. No gamestates assumed prior to call.
    public boolean rehearse() {
        //No moves left
        if (turnOver) {
            controller.pushText("You have no more moves this turn");
            return false;
        }
        //checks if not on a role
        if (playerData.getRole() == null) {
            controller.pushText("Not on a role");
            return false;
        }
        //cannot make useless rehearsals
        if(playerData.getrehearseChips() == playerData.getplayerSet().getCard().budget - 1) {
            controller.pushText("You have guaranteed success! Try acting!");
            return false;
        } else { //Successful case
            playerData.addRehearseChips(1);
            turnOver = true;
            controller.updatePlayerData();
            controller.addCounter(this, playerData.getrehearseChips());
            return true;
        }
    }
    //Player move method
    public boolean move(Set target) {
        if (turnOver) {
            controller.pushText("You have no more moves this turn");
            return false;
        }
        //can only move once
        if(hasMoved) {
            controller.pushText("You have already moved in this turn.");
            return false;
        }
        //must not be on a role
        if (playerData.getRole() != null) {
            controller.pushText("You are acting on a role!!");
            return false;
        }
        //must be next to target set
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
        controller.updatePlayerData();
        return true;

    }
    //returns neighbors. Implemented for purpose of allowing Buttons to be used for moving
    public Set[] getneighbors() {
        return this.playerData.getplayerSet().getNeighborSets();
    }
    //Role methjod for player. No preconditions assumed.
    public boolean takeRole(Role r) {
        if (turnOver) {
            controller.pushText("You have no more moves this turn");
            return false;
        }
        //Cannot take roles on special sets
        if (playerData.getplayerSet().getName().equalsIgnoreCase("office") || playerData.getplayerSet().getName().equalsIgnoreCase("trailer")) {
            controller.pushText("Cannot take role on this set");
            return false;
        }
        //Cannot take roles on sets that have wrapped up
        if (playerData.getplayerSet().isWrapped()) {
            controller.pushText("The set is already wrapped");
            return false;
        }
        //checks if players rank is high enough
        if (playerData.getRank() < r.rank) {
            controller.pushText("Level too low");
            return false;
        }
        //Checks if player is not already on a role
        if (playerData.getRole() != null) {
            controller.pushText("Already on role");
            return false;
        }
        //Asks set to give role to player
        if (!playerData.getplayerSet().takeRole(r,this)) {
            return false;
        }//attempts and returns boolean
        playerData.setRole(r);
        controller.updateIcon(this, r.getX(), r.getY());
        //NEED TO MOVE PLAYER TO THE COORDS OF THE ROLE
        turnOver = true;
        controller.updatePlayerData();
        return true;
    }

    //Player upgrade method.
    public boolean upgrade(int rank, String currency) {
        if (turnOver) {
            controller.pushText("You have no more moves this turn");
            return false;
        }
        //Check if player is in casting office : redundant, left in for safety
        Set currentSet = playerData.getplayerSet();
        if(!currentSet.getName().equalsIgnoreCase("Office")) {
            controller.pushText("You can only upgrade in the Casting Office.");
            return false;
        }
        //Checks for allowed rank range
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
        controller.updatePlayerData();
        return true;        
    }

    public void endRole() {
        playerData.setRole(null);
        playerData.setrehearseChips(0);
        int x = playerData.getplayerSet().getCoords(Integer.parseInt(playerData.getplayerName()))[0];
        int y = playerData.getplayerSet().getCoords(Integer.parseInt(playerData.getplayerName()))[1];
        //takes the player off the role
        controller.updateIcon(this, x, y);
        controller.updatePlayerData();
        controller.addCounter(this, 0);
    }
    //called to reset midturn players states
    public void endTurn() {
        hasMoved = false;
        turnOver = false;
        controller.updatePlayerData();
    }
    //data updaters
    public void addDollars(int a) {
        playerData.addDollars(a);
        controller.updatePlayerData();
    }
    public void addCredits(int a) {
        playerData.addCredits(a);
        controller.updatePlayerData();
    }
    public void newPlayerTurn() {
        hasMoved = false;
        controller.updatePlayerData();
    }
    //reverts Set, RehearseChips,Role
    public void reset(Set start) {
        playerData.setplayerSet(start);
        playerData.setRole(null);
        playerData.setrehearseChips(0);
        controller.addCounter(this, 0);
        int coords[] =  playerData.getplayerSet().getCoords(Integer.parseInt(playerData.getplayerName()));
        controller.updateIcon(this, coords[0],coords[1]);
    }
    public String getName() {
        return (Integer.parseInt(playerData.getplayerName()) + 1) + "";
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
    public int[] getVisibleData() {
        int[] m = {playerData.getRank(),playerData.getDollars(),playerData.getCredits()};
        return m;
    }
    public Set getPlayerSet() {
        return playerData.getplayerSet();
    }
}
