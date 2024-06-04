import java.io.File;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

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
        initializePLayerLocations();

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
        int[][] office = {{8,515},{8,559},{8,603},{176,495},{176,539},{176,583},{176,627},{8,471}};
        int[][] trailer = {{1100,240},{1050,240},{1144,240},{1144,284},{1150,328},{990,263},{987,340},{987,384}};
        sets[0].positions = trainStation;
        sets[1].positions = secretHideOut;
        sets[2].positions = church;
        sets[3].positions = hotel;
        sets[4].positions = mainStreet;
        sets[5].positions = jail;
        sets[6].positions = generalStore;
        sets[7].positions = ranch;
        sets[8].positions = bank;
        sets[9].positions = saloon;
        sets[10].positions = office;
        sets[11].positions = trailer;
        for (int i = 0; i < players.length; i++) {
            controller.updateIcon(players[i], sets[sets.length - 1].getCoords(i)[0],sets[sets.length - 1].getCoords(i)[1]);
        }
    }
    
    public boolean endDay() {
        day++;
        if (players.length < 4 && day > 3) {
            return false;
        }
        else if (players.length > 3 && day > 4) {
            return false;
        }
        else {
            board.resetBoard();
            //go through each set and check values, then update the view values
            for (Player p : players) {
                p.reset(board.getSets()[board.getSets().length - 1]); //put everyone back to start
            }
            return true;
        }
    }
    public Player[] getWinners() {
        List<Player> winners = new ArrayList<>();
        int highScore = 0;
        for (Player p : players) {
            if(p.getScore() > highScore) highScore = p.getScore();
        }
        for (Player p : players) {
            if (p.getScore() == highScore) {
                winners.add(p);
            }
        }
        return winners.toArray(new Player[0]);
    }

    public Player[] getPlayers() {
        return players;
    }
    public Board getBoard() {
        return board;
    }
    public int getDay() {
        return day;
    }
 }
