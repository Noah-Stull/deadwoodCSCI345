
public class Player {
    private PlayerData playerData;
    private boolean hasMoved;

    public Player(PlayerData playerData) {
        this.playerData = playerData;
        this.hasMoved = false;
    }

    public int act(role role) {
        return 1;
    }

    public boolean rehearse(int rehearseChips) {
        return false;
    }

    public boolean move(Location destination) {
        return false;
    }

    public boolean upgrade(int rank) {
        return false;
    }

    public void endTurn() {

    }
}
