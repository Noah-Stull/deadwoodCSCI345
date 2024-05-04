
public class Player {
    private PlayerData playerData;
    private boolean hasMoved;

    public Player(String playerName, Location playerLocation, int rank, int dollars, int credits, int rehearseChips, int role) {
        this.playerData = new PlayerData(playerName, playerLocation, rank, dollars, credits, rehearseChips, role);
        this.hasMoved = false;
    }

    //tells the set that is attached to playerData that we are acting
    public boolean act() {
        return false;
    }

    public boolean rehearse(int rehearseChips) {
        return false;
    }

    public boolean move(Location destination) {
        return false;
    }

    public boolean takeRole(Role r) {
        return false;
    }

    public boolean upgrade(int rank) {
        return false;
    }

    public void endTurn() {

    }
}
