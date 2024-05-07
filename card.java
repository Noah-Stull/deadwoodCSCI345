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

}
