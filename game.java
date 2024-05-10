import java.io.File;

public class Game {
    private Player[] players;
    private Board board;
    private int day;

    public Game(int numPlayers) {

        board = new Board("board.xml", new File("Card.xml"));
        players = new Player[numPlayers];
        if (numPlayers < 5) {
            for(int i = 0; i < numPlayers; i++) {
            players[i] = new Player((i + ""), null, 1, 0, 0);
            }
        } 
        if (numPlayers == 5) {
            for(int i = 0; i < numPlayers; i++) {
                players[i] = new Player((i + ""), null, 1, 0, 2);
                }
        } 
        if (numPlayers == 6) {
            for(int i = 0; i < numPlayers; i++) {
                players[i] = new Player((i + ""), null, 1, 0, 4 );
                }
        } 
        if (numPlayers > 6) {
            for(int i = 0; i < numPlayers; i++) {
                players[i] = new Player((i + ""), null, 2, 0, 0 );
                }
        } 
        day = 1;
    }

    //Goes through each player until some exit condition is reached
    private void play() {
        int turn = 0;
        while(true) {
            if (turn > players.length - 1) {
                turn = 0;
                continue;
            }
            players[turn].play();
            turn++;
        }
    }

    private void endDay() {
        day++;
    }

    private void endGame() {
        
    }
}
