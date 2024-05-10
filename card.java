import java.util.ArrayList;

public class Card {
    public final Role[] roles;
    public final String name;
    public final String description;
    public final int budget;
    public final String img;

    //all card data is publically read only, so no getters and setters
    
    public Card(Role[] roles, String name, String description, int budget, String img) {
        this.roles = roles;
        this.name = name;
        this.description = description;
        this.budget = budget;
        this.img = img;
    }
    public boolean hasPlayers() {
        for each (Role r : roles) {
            if(!(r.getPlayer() == null)) return true;
        }
        return false;
    }
    public Player[] getPlayers() {
        ArrayList<Integer> arr = new ArrayList<Integer>();
        for each (Role r : roles) {
            if(!(r.getPlayer() == null)) arr.add(r.getPlayer());
        }
        return arr.toArray();
    }

}
