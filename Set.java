import java.util.Arrays;

public class Set{
    private Role[] roles;
    private Card sceneCard;
    public final Board board;
    private boolean visited = false;
    private int shotCounterTotal;
    private int shotCounter;
    private Set[] neighborSets;
    public final String name;

    public Set(Board b, Role[] r, int shots, Set[] sets, String name) {
        board = b;
        roles = r;
        sceneCard = b.getCard();
        visited = false;
        shotCounterTotal = shots;
        shotCounter = shots;
        this.neighborSets = sets;
        this.name = name;
    }

    public boolean takeRole(Role r) {
        return false;
        //checks if role r is available and on this set, if not this returns false
    }

    //called if player sucessfully acts
    public void act() {
        shotCounter--;
        if (shotCounter == 0) {
            if (sceneCard.hasPlayers()) {
                rewardOnCard();
                rewardOffCard();
            }
            wrapUp();
        }
    }
    private void rewardOnCard() {
        Player[] p = sceneCard.getPlayers();
        int[] rolls = new int[sceneCard.budget];
        Role[] cardRoles = sceneCard.roles;
        Arrays.sort(cardRoles); // insure this
        Dice d = new Dice();
        for (int i = 0; i < rolls.length;i++) {
            rolls[i] = d.rollDice();        
        }
        Arrays.sort(rolls); //check this to make sure zero is highest
        for (int i = 0; i < rolls.length;i++) {
            Role curRole = cardRoles[(i % cardRoles.length)];
            if(curRole.getPlayer() == null) continue;
            curRole.getPlayer().addDollars(rolls[rolls.length - 1 - i]);
        }

    }
    private void rewardOffCard() {
        for (Role r : roles) {
            if (r.getPlayer() == null) continue;
            r.getPlayer().addDollars(r.rank);
        }
    }

    //rolls dice and rewards players based on position
    private void rewardAllPlayers() {
        //check if there are players working on any card role
        boolean b = false;
        for (Role r : sceneCard.roles) {
            if (r.getPlayer() != null) b = true;
        }
        if (!b) return; //no players detected on card

        rewardOnCard();
        rewardOffCard();
    }
    //this finishes set
    private void wrapUp() {
        board.wrapScene();
        rewardAllPlayers();
    }
    //resets and gets new card from Board pointer
    public void reset() {
        sceneCard = board.getCard();
        shotCounter = shotCounterTotal;
        visited = false; // card should be down
    }
    public Card getCard() {
        return sceneCard;
    }
    public Set[] getNeighborSets() {
        return neighborSets;
    }

    public String getName() {
        return name;
    }
    public void visit() {
        //if it is false at this point then flip the card
        visited = true;
    }

    public void addNeighbor(Set s) {
        int i = 0;
        while (true) {
            if (i >= neighborSets.length) return;
            if (neighborSets[i] == null){ neighborSets[i] = s; return;}
            i++;
        }
    }

    public Role[] getRoles() {
        Role[] allRoles = new Role[roles.length + sceneCard.roles.length];
        for (int i = 0; i < roles.length; i++) {
            allRoles[i] = roles[i];
        }
        for(int i = roles.length; i < allRoles.length; i++) {
            allRoles[i] = sceneCard.roles[i - roles.length];
        }
        return allRoles;
    }
}
//gerru5