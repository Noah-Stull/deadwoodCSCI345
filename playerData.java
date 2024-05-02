public class playerData{
    private final String playerName;
    private location playerLocation;
    private int rank;
    private int dollars;
    private int credits;
    private int rehearseChips;
    private int role;


    public playerData(String playerName, location playerLocation, int rank, int dollars, int credits, int rehearseChips, int role) {
        this.playerName = playerName;
        this.playerLocation = playerLocation;
        this.rank = rank;
        this.dollars = dollars;
        this.credits = credits;
        this.rehearseChips = rehearseChips;
    }

    public String getplayerName() {
        return playerName;
    }

    public location getplayerLocation() {
        return playerLocation;
    }

    public void setplayerLocation(location playerLocation) {
        this.playerLocation = playerLocation;
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

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

}
