public class Role implements Comparable<Role>{
    public final String name;
    public final int rank;
    public final String catchPhrase;
    public final int roleType; //1 is on 2 is off
    private Player player; 

    public Role(String name, int rank, String catchPhrase, int roleType) {
        this.name = name;
        this.rank = rank;
        this.roleType = roleType;
        this.catchPhrase = catchPhrase;
        player = null;
    }

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
    public void reset() {
        this.player = null;
    }

@Override public int compareTo(Role r) {
        return r.rank-this.rank;
    }
}

