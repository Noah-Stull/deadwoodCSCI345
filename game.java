public class Game {
    private Player[] players;
    private Board board;
    private int day;

    public Game(int numPlayers) {
        players = new Player[numPlayers];
        for(int i = 0; i < numPlayers; i++) {
            PlayerData playerData = createPlayerData();
            players[i] = new Player(playerData);
        }
        day = 1;
    }

    private PlayerData createPlayerData() {

    }

    private void endDay() {
        day++;
    }

    private void endGame() {
        
    }
}
