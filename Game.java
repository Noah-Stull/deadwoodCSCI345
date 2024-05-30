import java.io.File;

public class Game {
    private Player[] players;
    private Board board;
    private int day;
    private int totalDays;
    Controller controller;

    public Game(int numPlayers,Controller c) {
        controller = c;
        board = new Board("cards.xml","board.xml",controller);
        Set start = board.getSets()[board.getSets().length - 1];
        players = new Player[numPlayers];
        if (numPlayers < 4) totalDays = 3;
        else totalDays = 4;
        if (numPlayers < 5) {
            System.out.println("Making " + numPlayers + " players");
            for(int i = 0; i < numPlayers; i++) {
            players[i] = new Player((i + ""), start, 1, 0, 0,c);
            }
        } 
        if (numPlayers == 5) {
            for(int i = 0; i < numPlayers; i++) {
                players[i] = new Player((i + ""), start, 1, 0, 2,c);
                }
        } 
        if (numPlayers == 6) {
            for(int i = 0; i < numPlayers; i++) {
                players[i] = new Player((i + ""), start, 1, 0, 4,c );
                }
        } 
        if (numPlayers > 6) {
            for(int i = 0; i < numPlayers; i++) {
                players[i] = new Player((i + ""), start, 2, 0, 0,c );
                }
        } 
        day = 1;
    }

    //Goes through each player until some exit condition is reached
    private void play() {
        System.out.println("playing");
        int turn = 0;
        while(true) {
            if (turn > players.length - 1) {
                turn = 0;
                continue;
            }
            System.out.println("Player " + (turn + 1) + "  take your turn");
            players[turn].newPlayerTurn();
            if (!board.moreScenes() && day >= totalDays - 1) {
                break;
            }
            else if (!board.moreScenes()) {
                endDay();
            }
            turn++;
        }
        endDay();
    }
    public void endDay() {
        day++;
        if (players.length < 4 && day == 3) endGame();
        else if (players.length > 3 && day == 4) endGame();
        else {
            board.resetBoard();
            //go through each set and check values, then update the view values
            for (Player p : players) {
                p.reset(board.getSets()[board.getSets().length - 1]); //put everyone back to start
            }
        }
    }

    private void endGame() {
        Player pmax = players[0];
        for (Player p : players) {
            if (p.getScore() > pmax.getScore()) {
                pmax = p;
            }
        }
        System.out.println("The game has ended");
        System.out.println("The winner is... player " + pmax.getName);
    }

    public Player[] getPlayers() {
        return players;
    }
    public Board getBoard() {
        return board;
    }
 }
