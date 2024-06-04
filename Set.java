import java.util.Arrays;

public class Set{
    private Role[] roles;
    private Card sceneCard;
    public final Board board;
    private boolean visited = false; //used only for display
    private int shotCounterTotal;
    private int shotCounter;
    private Set[] neighborSets;
    public final String name;
    private boolean wrapped = false;
    private Controller controller;
    private int[] area; //x,y,w,h
    public int[][] positions = new int[8][2];
    private ShotCounter[] shots;

    public Set(Board b, Role[] r, int shots, Set[] sets, String name, Controller c, int[] area, ShotCounter[] sh ) {
        board = b;
        roles = r;
        visited = false;
        shotCounterTotal = shots;
        shotCounter = shots;
        this.neighborSets = sets;
        this.name = name;
        this.area = area;
        controller = c;
        if(name.equalsIgnoreCase("Office") || name.equalsIgnoreCase("trailer")) {
            sceneCard = null;
            sh = null;
        }
        else {      
            sceneCard = b.getCard();
            for (Role rtemp : sceneCard.roles) {
                rtemp.overridePosition(area[0], area[1]);
            }
            this.shots = sh;
        }
    }
    
    public boolean takeRole(Role r, Player p) {
        if (wrapped) {
            controller.pushText("This scene is wrapped!");
            return false;
        }
        //checked if already occupied
        if(r.getPlayer() != null){
            controller.pushText("This role is occupied");
            return false;
        }
        for (Role rs : this.getRoles()) {
            if (rs == r)  {
                r.giveRole(p);
                return true;
            }
        }
        return false;
        //checks if role r is available and on this set, if not this returns false
    }

    //called if player sucessfully acts
    public void act() {
        if (wrapped) {System.out.println("ERROR: Scene already wrapped");}; //Assume that this cannot be called
        controller.updateIcon(shots[shotCounter-1], false);
        shotCounter--;
        if (shotCounter == 0) {
            wrapUp();
        }
    }
    private void rewardOnCard() {
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
        rewardAllPlayers();

        //removes current roles associated with this set
        for (Role r : roles) {
            if (r.getPlayer() == null) continue;
            r.getPlayer().endRole();
            r.giveRole(null);
        }
        for (Role r : sceneCard.roles) {
            if (r.getPlayer() == null) continue;
            r.getPlayer().endRole();
            r.giveRole(null);            
        }
        controller.updateIcon(this,false);
        wrapped = true;
        board.wrapScene();
    }
    //resets and gets new card from Board pointer
    public void reset() {
        //special sets
        if (sceneCard == null) {
            return;
        }
        sceneCard = board.getCard();
        controller.updateIcon(this, "CardBack-small.jpg");
        controller.updateIcon(this, true); // does nothing on first day. After that it makes card visible again
        shotCounter = shotCounterTotal;
        for (ShotCounter c : shots) {
            controller.updateIcon(c, true);
        }
        visited = false; // card should be down
        for (Role r : roles) {
            r.reset();
        }
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
        //this does nothing for non scene sets
        if (sceneCard == null) return;
        //if it is false at this point then flip the card
        if (!visited) {
            controller.updateIcon(this, sceneCard.img);
            visited = true;
        }
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
        if (roles == null) return null;
        Role[] allRoles = new Role[roles.length + sceneCard.roles.length];
        for (int i = 0; i < roles.length; i++) {
            allRoles[i] = roles[i];
        }
        for(int i = roles.length; i < allRoles.length; i++) {
            allRoles[i] = sceneCard.roles[i - roles.length];
        }
        return allRoles;
    }
    public int getX() {
        return area[0];
    }
    public int getY() {
        return area[1];
    }
    public int[] getCoords(int playerNumber) {
        return positions[playerNumber];
    }
    public boolean isWrapped () {
        return wrapped;
    }
    public ShotCounter[] getShots() {
        return this.shots;
    }
}
//gerru5