public class Set{
    private Role[] roles;
    private Card sceneCard;
    public final Board board;
    boolean visited;
    private int shotCounter;
    private Set[] neighborSets;

    public Set(Board b, Role[] r, int shots, Set[] sets) {
        board = b;
        roles = r;
        sceneCard = b.getCard();
        visited = false;
        shotCounter = shots;
        this.neighborSets = sets;
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
                rewardOnCard();
                
            }
        }
    }
    private void rewardOnCard() {
        Player[] p = sceneCard.getPlayers();
        int[] rewards = new int[sceneCard.roles.length];
    }
    private void rewardOffCard() {
        
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
