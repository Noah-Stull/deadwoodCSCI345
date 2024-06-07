public class Role implements Comparable<Role>{
    public final String name;
    public final int rank;
    public final String catchPhrase;
    public final int roleType; //1 is on 2 is off
    private Player player; 
    private int x;
    private int y;

    public Role(String name, int rank, String catchPhrase, int roleType, int x, int y) {
        this.name = name;
        this.rank = rank;
        this.roleType = roleType;
        this.catchPhrase = catchPhrase;
        this.x = x;
        this.y = y;
        player = null;

    }
    //public getters and setters
    public boolean isOccupied() {
        if (player == null) return false;
        return true;
    }
    public void giveRole(Player p) {
        this.player = p;
    }
    //Role type of true = on the card, false = off the card
    public int getroleType() {
        return roleType;
    }
    public Player getPlayer() {
        return this.player;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }    
    //resets so that no player is on role
    public void reset() {
        this.player = null;
    }


    /*This method is called only for on card roles.
     *      When the card is placed on a set, the position of the role
     *      must set relative to the set coordinates. This method adds the origin to the pre existing coordinates.
     */
    public void overridePosition(int xbase, int ybase) {
        this.x += xbase;
        this.y += ybase;
    }
    //Allows implementation of comparable and Collections utility usage
    @Override public int compareTo(Role r) {
           return r.rank-this.rank;
    }
}

