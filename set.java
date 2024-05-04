public class Set {
    private role[] roles;
    private Card sceneCard;
    public final Board board;
    boolean visited;

    public Set(Board b, role[] r) {
        board = b;
        roles = r;
        sceneCard = b.getCard();
        visited = false;
    }
}
