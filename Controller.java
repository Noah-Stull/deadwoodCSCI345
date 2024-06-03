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
        // also maps shotCounters
        Set[] s = g.getBoard().getSceneSets();
        JLabel[] sl = b.cardlabels; //there should always be 10
        JLabel[] shl = b.shot;
        int shotIndex = 0;
        for (int i = 0; i < sl.length;i++) {
            map.put(s[i],sl[i]);
            for (int j = 0; j < s[i].getShots().length;j++) {
                ShotCounter curShot = s[i].getShots()[j];
                map.put(curShot, shl[shotIndex]);
                updateIcon(curShot,curShot.x,curShot.y);
                shotIndex++;
            }
        }

        

        //sets up game visuals
        g.initializeIcons();
        view.flash(map.get(player));
    }

    public void endTurn() {
        view.stopFlash(map.get(player));
        turn++;
        if (turn >= players.length) {
            turn = 0;
        }
        player.endTurn();
        player = players[turn];
        view.appendToOutput("Player: " + turn + "'s turn");
        view.flash(map.get(player));
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
                }
                 else {
                    view.closeText();
                    return;
                }
            }
        }
        view.closeText();
        view.appendToOutput("Target location not found, try fixing spelling.");
    }

    //This needs an int referring to the intended roles position in role list
    //  A window must pop up in the view to inform players of 
    public void takeRole(String roleName) {
        Role[] allRoles = player.getSetRoles();
        if (allRoles == null) {
            view.appendToOutput("There are no roles on here!");
            view.closeText();
            return;
        }
        for (Role r : allRoles) {
            System.out.println(r.name);
            if (roleName.equalsIgnoreCase(r.name)) {
                if (player.takeRole(r)) {
                    view.appendToOutput("Successfully taken role");
                    view.closeText();
                    return;
                }
                else {
                    view.closeText();
                    return;
                }
            }
        }
        view.appendToOutput("There is no role with that name!");
        view.closeText();
    }

    public void upgrade(int rank, String currency) {
        if (player.upgrade(rank, currency)) {
            view.appendToOutput("Successfullt upgraded to rank " + rank);
        }
        view.closeText();
    }

    public void pushText(String s) {
        view.appendToOutput(s);
    }
    public void updatePlayerData() {
        PlayerData playerData = player.getPlayerData();
        String data = "\nRank: " + playerData.getRank();
        view.updatePlayerData(data);
    }

    public void act() {
        player.act();
        view.closeText();
    }
    public void rehearse() {
        if (player.rehearse()) {
            pushText("You have successfullt rehearsed!");
        }
    }
    public String getPlayerColor(int pnum) {
        return colors[pnum];
    }

    public void rollDice(int val) {
        view.diceRoll(val);
    }
}

