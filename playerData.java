
public class PlayerData{
    private final String playerName;
    private Set playerSet;
    private int rank;
    private int dollars;
    private int credits;
    private int rehearseChips;
    private int role; //this is just the level

    //Constructor
    public PlayerData(String playerName, Set playerSet, int rank, int dollars, int credits, int rehearseChips, int role) {
        this.playerName = playerName;
        this.playerSet = playerSet;
        this.rank = rank;
        this.dollars = dollars;
        this.credits = credits;
        this.rehearseChips = rehearseChips;
        role = 0;
    }
    // Getters/Setters
    public String getplayerName() {
        return playerName;
    }

    public Set getplayerSet() {
        return playerSet;
    }

    public void setplayerSet(Set playerSet) {
        this.playerSet = playerSet;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getDollars() {
        return dollars;
    }

    public void setDollars(int dollars) {
        this.dollars = dollars;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getrehearseChips() {
        return rehearseChips;
    }

    public void setrehearseChips(int rehearseChips) {
        this.rehearseChips = rehearseChips;
    }

    public void addRehearseChips(int amount) {
        rehearseChips += amount;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

}