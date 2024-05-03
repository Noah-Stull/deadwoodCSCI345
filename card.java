public class card {
    public final role[] roles;
    public final String name;
    public final String description;

    //all card data is publically read only, so no getters and setters
    
    public card(role[] roles, String name, String description) {
        this.roles = roles;
        this.name = name;
        this.description = description;
    }
}
