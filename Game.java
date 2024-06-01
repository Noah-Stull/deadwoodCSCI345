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
    public void initializeIcons() {
        for (Set s : board.getSceneSets()) {
            controller.updateIcon(s,"CardBack-small.jpg", s.getX(), s.getY());
        }
        int x = board.getSets()[board.getSets().length - 1].getX();
        int y = board.getSets()[board.getSets().length - 1].getY();
        for (Player s : players) {
            controller.updateIcon(s, x, y);
        }
        controller.updateIcon(players[0], 730,205);
        controller.updateIcon(players[1], 730,249);
        controller.updateIcon(players[2], 774,249);
        controller.updateIcon(players[3], 818,249);
        controller.updateIcon(players[4], 818,340);
        controller.updateIcon(players[5], 818,384);
        controller.updateIcon(players[6], 774,384);
        controller.updateIcon(players[7], 640,384);

    }
    public void initializePLayerLocations() {
        Set[] sets = board.getSets();
        int[][] trainStation = {{15,200},{15,247},{15,295},{15,341},{15,390},{160,280},{160,327},{160,380}};
        int[][] secretHideOut = {{255,820},{299,830},{343,820},{387,830},{15,683},{59,683},{103,683},{147,683}};
        int[][] church = {{738,660},{782,660},{828,675},{872,678},{738,704},{782,704},{620,850},{810,850}};
        int[][] hotel = {{1005,465},{1049,465},{1140,620},{950,676},{950,720},{950,764},{950,808},{950,852}};
        int[][] mainStreet = {{780,145},{824,145},{868,145},{912,160},{956,170},{1020,180},{1064,180},{1108,180}};
        int[][] jail = {{270,130},{270,174},{314,174},{358,174},{402,190},{446,202},{490,200},{534,180}};
        int[][] generalStore = {{370,255},{414,255},{458,255},{502,255},{546,255},{290,395},{334,395},{567,304}};
        int[][] ranch = {{275,624},{319,624},{363,624},{540,520},{540,564},{540,608},{238,575},{238,531}};
        int[][] bank = {{605,590},{649,590},{770,590},{814,590},{858,590},{824,460},{868,481},{824,504}};
        int[][] saloon = {{730,205},{730,249},{774,249},{818,249},{818,340},{818,384},{774,384},{640,384}};
        int[][] trailer = {{},{},{},{},{},{},{},{}};


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
