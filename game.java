public class Game {
    private Player[] players;
    private Board board;
    private int day;

    public Game(int numPlayers) {
        players = new Player[numPlayers];
        for(int i = 0; i < numPlayers; i++) {
            PlayerData playerData = createPlayerData("Player" + (i + 1), null, i, 0, 0, 0, 0);
            // players[i] = new Player(playerData);
        }
        day = 1;
    }

    //initial playerData must be defined in player class,
    // as it varries with different total players
    private PlayerData createPlayerData(String playerName, Set playerSet, int rank, int dollars, int credits, int rehearseChips, int role) {
        return new PlayerData(playerName, playerSet, rank, dollars, credits, rehearseChips, role);
    }

    //Goes through each player until some exit condition is reached
    private void play() {
    }

    private void endDay() {
        day++;
    }

    private void endGame() {
        
    }
}
