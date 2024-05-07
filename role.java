public class Role {
    public final String name;
    public final int rank;
    public final String catchPhrase;
    private Player player; 

    public Role(String name, int rank, String catchPhrase) {
        this.name = name;
        this.rank = rank;
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
    public Player getPlayer() {
        return null;
    }
}

