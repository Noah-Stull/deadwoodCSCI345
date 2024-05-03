public class Set {
    private role[] roles;
    private card sceneCard;
    public final Board board;
    boolean visited;

    public Set(Board b, role[] r) {
        board = b;
        roles = r;
        sceneCard = b.getCard();
        visited = false;
    }
}
