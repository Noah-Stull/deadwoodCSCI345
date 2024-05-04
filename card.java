public class Card {
    public final role[] roles;
    public final String name;
    public final String description;

    //all card data is publically read only, so no getters and setters
    
    public Card(role[] roles, String name, String description) {
        this.roles = roles;
        this.name = name;
        this.description = description;
    }
}
