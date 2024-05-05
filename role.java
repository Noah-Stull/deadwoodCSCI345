public class Role {
    public final String name;
    public final int rank;
    private Player player; 

    public Role(String name, int rank) {
        this.name = name;
        this.rank = rank;
    }

    public boolean isOccupied() {
        if (player == null) return false;
        return true;
    }
    public void giveRole(Player p) {
        this.player = p;
    }
    public Player getPlayer() {
        return null;
    }
}

