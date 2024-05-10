public class Set{
    private Role[] roles;
    private Card sceneCard;
    public final Board board;
    private boolean visited;
    private int shotCounter;
    private Set[] neighborSets;
    public final String name;

    public Set(Board b, Role[] r, int shots, Set[] sets, String name) {
        board = b;
        roles = r;
        sceneCard = b.getCard();
        visited = false;
        shotCounter = shots;
        this.neighborSets = sets;
        this.name = name;
    }

    public boolean takeRole(Role r) {
        return false;
        //checks if role r is available and on this set, if not this returns false
    }

    //called if player sucessfully acts
    public void act() {
        shotCounter--;
        if (shotCounter == 0) {
            if (sceneCard.hasPlayers()) {
                rewardOnCard();
                rewardOffCard();
            }
            wrapUp();
        }
    }
    private void rewardOnCard() {
        Player[] p = sceneCard.getPlayers();
        int[] rewards = new int[sceneCard.roles.length];
        for (int i = 0; i < rewards.length;i++) {

        }
    }
    private void rewardOffCard() {
        for (Role r : roles) {
            if (r.getPlayer() == null) continue;
            r.getPlayer().addDollars(r.rank);
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
    public Set[] getNeighborSets() {
        return neighborSets;
    }

    public String getName() {
        return name;
    }
}
