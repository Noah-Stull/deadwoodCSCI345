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
        this.img = "cards/" + img;
    }
    //checks if players on owned roles
    public boolean hasPlayers() {
        for (Role r : roles) {
            if(!(r.getPlayer() == null)) return true;
        }
        return false;
    }
    //returns players on owned roles
    public Player[] getPlayers() {
        ArrayList<Player> arr = new ArrayList<Player>();
        for (Role r : roles) {
            if(!(r.getPlayer() == null)) arr.add(r.getPlayer());
        }
        return arr.toArray(new Player[0]);
    }

    public String getName() {
        return name;
    }

}
