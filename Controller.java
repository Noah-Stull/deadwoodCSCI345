import java.util.HashMap;
import javax.swing.*;

public class Controller {
    Game g;
    Player[] players;
    Player player; //current players turn
    private int turn = 0;
    HashMap<Object, JLabel> map = new HashMap<Object, JLabel>();
    BoardLayersListener view = new BoardLayersListener(this);
    private String[] colors = {"r","b","c","g","o","p","w","v","y"};

    public Controller(int numPlayers, BoardLayersListener b) {
        g = new Game(numPlayers,this);
        players = g.getPlayers();
        player = players[0];
        view = b;
        
        //maps all player objects to their JLabels
        Player[] p = g.getPlayers();
        JLabel[] pl = b.playerlabel;
        for (int i = 0 ; i< p.length;i++) {
            map.put(p[i], pl[i]);
        }

        //maps all sets[cards] to their JLabels
        Set[] s = g.getBoard().getSceneSets();
        JLabel[] sl = b.cardlabels; //there should always be 10
        for (int i = 0; i < sl.length;i++) {
            System.out.println(s[i].hashCode());
            map.put(s[i],sl[i]);
        }

        //sets up game visuals
        g.initializeIcons();

    }

    public void endTurn() {
        turn++;
        if (turn >= players.length) {
            turn = 0;
        }
        player = players[turn];
    }
    //position and image update
    public void updateIcon(Object o, String img, int x, int y) {
        JLabel j = map.get(o);
        ImageIcon cIcon =  new ImageIcon(img);
        j.setIcon(cIcon);
        j.setBounds(x,y,j.getWidth(),j.getHeight());
        view.update();
    }
    //image update only
    public void updateIcon(Object o, String img) {
        JLabel j = map.get(o);
        ImageIcon cIcon = new ImageIcon(img);
        j.setIcon(cIcon);
        view.update();
    }
    //position update only
    public void updateIcon(Object o, int x, int y) {
        JLabel j = map.get(o);
        j.setBounds(x,y,j.getWidth(),j.getHeight());
        view.update();
    }
    //update visible or non visible
    public void updateIcon(Object o,boolean vis) {
        map.get(o).setVisible(vis);
    }

    //visual must ask for a string input field representing target location which will be fed into this method
    //  this method will find the cooresponding set via linear search
    public void move(String s) {
        for (Set neighbor : player.getneighbors()) {
            if (neighbor.getName().equalsIgnoreCase(s)) {
                view.appendToOutput("target found");
                if (player.move(neighbor)) {
                    view.appendToOutput("Move successful to " + s);
                    view.closeText();
                    return;
                } else {
                    view.appendToOutput("Move unsuccessful");
                    view.closeText();
                    return;
                }
            }
            else {
                view.appendToOutput("target not found.");
                view.closeText();
            }
        }
        view.closeText();
        view.appendToOutput("Target location not found, try fixing spelling.");
    }

    //This needs an int referring to the intended roles position in role list
    //  A window must pop up in the view to inform players of 
    public void takeRole(int roleNum) {
        Role[] allRoles = player.getSetRoles();
        if (player.takeRole(allRoles[roleNum])) {
            //role successfully taken. Turn ends
            view.appendToOutput("ROle taken successfully.");
            view.closeText();
            endTurn();
        }
        //this did not work
        view.appendToOutput("Role could not be taken.");
        view.closeText();
    }

    public void upgrade(int rank, String currency) {
        if (player.upgrade(rank, currency)) {
            view.appendToOutput("Player upgraded to rank " + rank);
        } else {
            PlayerData playerData = player.getPlayerData();
            Set currentSet = playerData.getplayerSet();
            if (!currentSet.getName().equalsIgnoreCase("Office")) {
                view.appendToOutput("You can only upgrade at the Casting Office.");
            } else if (rank <= playerData.getRank() || rank > 6) {
                view.appendToOutput("Invalid rank selected.");
            } else if (!(currency.equalsIgnoreCase("Dollars") || currency.equalsIgnoreCase("Dollar") ||
                         currency.equalsIgnoreCase("credit") || currency.equalsIgnoreCase("credits"))) {
                view.appendToOutput("Invalid currency.");
            } else {
                view.appendToOutput("Insufficient currency to upgrade.");
            }
        }
    }

    public void act() {
        if (player.act()) {
            //success
            endTurn();
        }
        else {
            //Tell text field that this did not work
        }
    }
    public void rehearse() {
        if (!player.rehearse()) {
            
        }
    }
    public String getPlayerColor(int pnum) {
        return colors[pnum];
    }

    public void roleDice(int val) {
        //display role of dice from dice file
    }
}

