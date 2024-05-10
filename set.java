public class Set{
    private Role[] roles;
    private Card sceneCard;
    public final Board board;
    boolean visited;
    private int shotCounter;

    public Set(Board b, Role[] r, int shots) {
        board = b;
        roles = r;
        sceneCard = b.getCard();
        visited = false;
        shotCounter = shots;
    }

    public boolean takeRole(Role r) {
        return false;
        //checks if role r is available and on this set, if not this returns false
    }

    //successful act
    public void act() {
        shotCounter--;
        if (shotCounter == 0) {
            if (sceneCard.hasPlayers()) {
                int[] 
            }
        }
    }
    //rolls dice and rewards players based on position
    private void rewardAllPlayers() {

    }
    //this finishes set
    private void wrapUp() {

    }
    //resets and gets new card from Board pointer
    public void reset() {

    }
    public Card getCard() {
        return sceneCard;
    }

}
