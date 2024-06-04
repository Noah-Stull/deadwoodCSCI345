import java.awt.*;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class Controller {
    Game g;
    Player[] players;
    Player player; //current players turn
    private int turn = 0;
    HashMap<Object, JLabel> map = new HashMap<Object, JLabel>();
    BoardLayersListener view = new BoardLayersListener(this);
    private String[] colors = {"r","b","c","g","o","p","w","v","y"};
    private String[] colorNames = {"red", "blue", "cyan", "green", "orange", "pink", "white", "violet", "yellow"};

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

        //maps player JLabel to their shotCounter JLabel
        for (int i = 0; i < pl.length;i++) {
            map.put(pl[i], view.practiceCounter[i]);
        }

        //sets up game visuals
        g.initializeIcons();
        view.flash(map.get(player));
        updatePlayerData();
        view.backCover.setVisible(false);
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
        view.tborder.setTitle("Player " + (turn+1)+ " | Day " + g.getDay());
        view.playerDataArea.repaint();
        view.flash(map.get(player));
        updatePlayerData();;
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
            view.appendToOutput("Successfully upgraded to rank " + rank);
        }
        view.closeText();
    }

    public void pushText(String s) {
        view.appendToOutput(s);
    }
    public void updatePlayerData() {
        int[] pnums = player.getVisibleData();
        int rank = pnums[0]; int dollars = pnums[1];int credits =pnums[2];
        String data = 
                      "Rank: " + rank +
                      "\nDollars: " + dollars +
                      "\nCredits: " + credits +
                       "\nColor: " +  colorNames[turn]
                      
                    // +
                    //   "\nRehearse Chips: " + playerData.getrehearseChips() +
                    //   "\nRole: " + (playerData.getRole() != null ? playerData.getRole().name : "None") +
                    //   "\nLocation: " + playerData.getplayerSet().getName()
                      ;
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
    public void addCounter(Player p, int c) {
        JLabel pyer = map.get(p);
        JLabel pcer = map.get(pyer);
        if (c == 0) {
            pcer.setVisible(false);
            return;
        }
        pcer.setVisible(true);
        pcer.setText("+" + c);
        pcer.setBounds(pyer.getX() + 20, pyer.getY() - 5, pcer.getWidth(),pcer.getHeight());
    }
    public void endDay() {
        if(g.endDay()) {
            view.endDay.append("Player  Color    Rank    Dollars   Credits\n");
            view.endDay.append(getAllPlayerInfoString());
            view.backCover.setVisible(true);
            view.next.setVisible(true);
            view.endDay.setVisible(true);
            view.tborder.setTitle(("Player " + (turn+1) + " | Day" + g.getDay()));
        }
        else { //This is the end of the game area
            view.endDay.setText(null);
            view.tb.setTitleFont(new Font("Serif",Font.BOLD,35));
            view.tb.setTitleJustification(TitledBorder.CENTER);
            Player[] winners = g.getWinners();
            if (winners.length == 1) {
                view.tb.setTitle("THE WINNER IS...");
            }
            else {
                view.tb.setTitle("THE WINNERS ARE...");
            }
            view.endDay.setFont(new Font("Monospaced",Font.BOLD,26));
            view.endDay.setBackground(new Color(234, 235, 195));
            view.endDay.append("Player    Color     Score\n");
            for (Player pp : winners) {
                view.endDay.append(String.format("%-"+12+"s",pp.getName()) + String.format("%-"+12+"s",(colorNames[Integer.parseInt(pp.getName())-1])) + String.format("%-"+5+"s",pp.getScore()) + "\n");
            }
            view.backCover.setVisible(true);
            view.endDay.setVisible(true);
            
        }
    }
    private String getAllPlayerInfoString() {
        String temp = "";
        for (int i = 0; i < players.length;i++) {
            int[] data = players[i].getVisibleData();
            temp = temp +String.format("%-"+6+"s",(1+i)+"") + String.format("%-"+11+"s",colorNames[i])  + String.format("%-" + 9+"s", ""+data[0]) +String.format("%-" + 9+"s", ""+data[1]) + String.format("%-" + 10+"s", ""+data[2]) + "\n";
        }
        return temp;
    }
}

