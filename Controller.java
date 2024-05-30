import java.util.HashMap;
import javax.swing.*;

public class Controller {
    Game g;
    Player[] players;
    Player player; //current players turn
    private int turn = 0;
    HashMap<Object, JLabel> map = new HashMap<Object, JLabel>();

    public Controller(int numPlayers) {
        g = new Game(numPlayers,this);
        players = g.getPlayers();
        player = players[0];
        
    }
    public void play() {
        int turn = 0;
        while(true) {
            if (turn > players.length - 1) {
                turn = 0;
                continue;
            }
            player = players[turn];
            //======== wait for action listener
            // System.out.println("Player " + (turn + 1) + "  take your turn");
            // players[turn].newPlayerTurn();
            // players[turn].play();
            // if (!board.moreScenes() && day >= totalDays - 1) {
            //     break;
            // }
            // else if (!board.moreScenes()) {
            //     endDay();
            // }
            turn++;
        }
    
    }

    public void endTurn() {
        turn++;
        if (turn >= players.length) {
            turn = 0;
        }
        g.endDay();
        player = players[turn];
    }
    //position and image update
    public void updateIcon(Object o, String img, int x, int y) {
        JLabel j = map.get(o);
        ImageIcon cIcon =  new ImageIcon(img);
        j.setIcon(cIcon);
        j.setAlignmentX(x);
        j.setAlignmentY(y);
    }
    //image update only
    public void updateIcon(Object o, String img) {
        JLabel j = map.get(o);
        ImageIcon cIcon = new ImageIcon(img);
        j.setIcon(cIcon);
    }
    //position update only
    public void updateIcon(Object o, int x, int y) {
        JLabel j = map.get(o);
        j.setAlignmentX(x);
        j.setAlignmentY(y);
    }
    public void rehearse() {
        if (!player.rehearse()) {
            
        }
    }
}
