
public class Location {
    public final Location neighbors;
    public final String name;

    public Location(String name, Location neighbors){
        this.name = name;
        this.neighbors = neighbors;
    }

    public Location getNeighbors() {
        return neighbors;
    }

    public String getName() {
        return name;
    }
}

