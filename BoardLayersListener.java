/*

   Deadwood GUI helper file
   Author: Moushumi Sharmin
   This file shows how to create a simple GUI using Java Swing and Awt Library
   Classes Used: JFrame, JLabel, JButton, JLayeredPane

   We use this as the "View" componenet.

*/

import java.awt.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.util.Random;
import javax.swing.border.*;

public class BoardLayersListener extends JFrame {

   Controller controller; //For action listener
   //These are to move the player's dice
   private Timer timer = null;
   private int posTemp = 0;
   private int direction = 1;
   private int offset;
   private int Xtrack = 0;
   //these are for the diceRoll effect
   private int rollCounter = 0;
   private boolean rollFlag = false;

  // JLabels
  JLabel boardlabel;
  JLabel cardlabels[];
  JLabel playerlabel[] = null;
  JLabel mLabel;
  JLabel diceRoll = null;
  JLabel shot[];
  JLabel practiceCounter[] = null;

  //JButtons
  JButton bAct;
  JButton bRehearse;
  JButton bMove;
  JButton bTakeRole;
  JButton bUpgrade;
  JButton bEndTurn;
  JButton[] destinations;

  //for ending the day and game
  JButton bEndDay;
  JTextArea endDay;
  JButton next;
  TitledBorder tb;
  JLabel backCover;

  // JLayered Pane
  JLayeredPane bPane;

  JTextArea inputArea;
  JTextArea outputArea;
  JTextArea playerDataArea;
 
  //For the player data 
  TitledBorder tborder;
  //Flag for extracting text inputs
  String currentAction = "";

  
  // Constructor
  
  public BoardLayersListener(Controller c) {
      
       // Set the title of the JFrame
       super("Deadwood");
       controller = c; 
       // Set the exit option for the JFrame
       setDefaultCloseOperation(EXIT_ON_CLOSE);

       setExtendedState(JFrame.MAXIMIZED_BOTH);
       // Create the JLayeredPane to hold the display, cards, dice and buttons
       bPane = getLayeredPane();    
       getContentPane().setBackground(new Color(74, 52, 17)); // This changes the background color
       // Create the deadwood board
       boardlabel = new JLabel();
       ImageIcon icon =  new ImageIcon("board.jpg");
       boardlabel.setIcon(icon); 
       boardlabel.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());
      
       // Add the board to the lowest layer
       bPane.add(boardlabel, new Integer(0));
      
       // Set the size of the GUI
       setSize(icon.getIconWidth()+200,icon.getIconHeight());
       // creates all scene cards. All have same position and image before game start.
       ImageIcon cIcon =  new ImageIcon("cards/01.png");
       cardlabels = new JLabel[10];
       for (int i = 0 ; i < cardlabels.length;i++) {
         cardlabels[i] = new JLabel();
       }
       for (JLabel j : cardlabels) {
         j.setIcon(cIcon);
         j.setBounds(20,65,cIcon.getIconWidth()+2,cIcon.getIconHeight());
         j.setOpaque(false);
         j.setVisible(true);
         bPane.add(j, new Integer(1));
       }
      

       //create shot counter array
       ImageIcon sIcon = new ImageIcon("shot.png");
       shot = new JLabel[22];
       for (int i = 0 ; i < shot.length; i++) {
         shot[i] = new JLabel();
         shot[i].setIcon(sIcon);
         shot[i].setBounds(0,0,sIcon.getIconWidth(),sIcon.getIconHeight());
         shot[i].setOpaque(false);
         shot[i].setVisible(true);
         bPane.add(shot[i],new Integer(2));
       }

       // Create the Menu for action buttons
       mLabel = new JLabel("MENU");
       mLabel.setBounds(icon.getIconWidth()+40,0,110,20);
       bPane.add(mLabel,new Integer(2));

       // Create Action buttons
       bAct = new JButton("ACT");
       bAct.setBackground(Color.white);
       bAct.setBounds(icon.getIconWidth()+10, 30,110, 20);
       bAct.addMouseListener(new boardMouseListener());
       
       bRehearse = new JButton("REHEARSE");
       bRehearse.setBackground(Color.white);
       bRehearse.setBounds(icon.getIconWidth()+10,60,110, 20);
       bRehearse.addMouseListener(new boardMouseListener());
       
       bMove = new JButton("MOVE");
       bMove.setBackground(Color.white);
       bMove.setBounds(icon.getIconWidth()+10,90,110, 20);
       bMove.addMouseListener(new boardMouseListener());

       bTakeRole = new JButton("TAKE ROLE");
       bTakeRole.setBackground(Color.white);
       bTakeRole.setBounds(icon.getIconWidth()+10,120,110, 20);
       bTakeRole.addMouseListener(new boardMouseListener());

       bUpgrade = new JButton("UPGRADE");
       bUpgrade.setForeground(Color.gray);
       bUpgrade.setBounds(icon.getIconWidth()+10,150,110, 20);

       bEndTurn = new JButton("END TURN");
       bEndTurn.setBackground(Color.white);
       bEndTurn.setBounds(icon.getIconWidth()+10,180,110, 20);
       bEndTurn.addMouseListener(new boardMouseListener());

       bEndDay = new JButton("END DAY EARLY");
       bEndDay.setBackground(Color.white);
       bEndDay.setBounds(icon.getIconWidth()+10,210,110, 20);
       bEndDay.addMouseListener(new boardMouseListener());
       bPane.add(bEndDay,new Integer(2));

       //Move option buttons(initialized unnamed)
       destinations = new JButton[4];
       for(int i = 0; i < 4; i++) {
         destinations[i] = new JButton("");
         destinations[i].setBackground(Color.white);
         destinations[i].setBounds(icon.getIconWidth()+10, 30 + (i * 30),110, 20);
         destinations[i].addMouseListener(new boardMouseListener());
         destinations[i].setVisible(false);
         bPane.add(destinations[i], new Integer(2));
       }

       next = new JButton("Continue");
       next.setBackground(Color.darkGray);
       next.setBounds(600,527,100,40);
       next.addMouseListener(new boardMouseListener());
       next.setVisible(false);
       bPane.add(next,new Integer(7));

       backCover = new JLabel("") {
         @Override
         protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(new Color(0, 0, 0, 200)); // Black with 100 alpha for translucency
            g2d.fillRect(0, 0, getWidth(), getHeight());
            super.paintComponent(g2d);
            g2d.dispose();
         }
       };
       backCover.setBounds(0,0,getWidth(),getHeight());
       backCover.setOpaque(false);
       backCover.setVisible(true);
       bPane.add(backCover,new Integer(5));

       // Place the action buttons in the top layer
       bPane.add(bAct, new Integer(2));
       bPane.add(bRehearse, new Integer(2));
       bPane.add(bMove, new Integer(2));
       bPane.add(bTakeRole, new Integer(2));
       bPane.add(bUpgrade, new Integer(2));
       bPane.add(bEndTurn, new Integer(2));


       // Input Area
       inputArea = new JTextArea();
       inputArea.setLineWrap(true);
       inputArea.setWrapStyleWord(true);
       inputArea.setBorder(BorderFactory.createLineBorder(new Color(33,22,5),4));
       inputArea.setBounds(icon.getIconWidth() + 10, 350, 150, 50 );
       inputArea.setVisible(false);
       inputArea.addKeyListener(new KeyAdapter() {
       public void keyPressed(KeyEvent e) {
          if (e.getKeyCode() == KeyEvent.VK_ENTER) {
             String input = inputArea.getText().trim();
             inputArea.setText(" ");
             processInput(input);
             e.consume();
            }
         }
      });
       bPane.add(inputArea, new Integer(2));

       //Output Area
       outputArea = new JTextArea();
       outputArea.setEditable(false);
       outputArea.setLineWrap(true);
       outputArea.setWrapStyleWord(true);
       outputArea.setBorder(BorderFactory.createLineBorder(new Color(33,22,5),4));
       outputArea.setBounds(icon.getIconWidth() + 10, 240, 150, 100);
       outputArea.setVisible(true);
       bPane.add(outputArea, new Integer(2));

       // Player Data Area
       playerDataArea = new JTextArea();
       playerDataArea.setEditable(false);
       playerDataArea.setLineWrap(true);
       playerDataArea.setWrapStyleWord(true);
       tborder = BorderFactory.createTitledBorder("Player 1 | Day 1");
       Border lineb = BorderFactory.createLineBorder(Color.BLACK,5);
       tborder.setTitleFont(new Font("Serif",Font.BOLD,16));
       playerDataArea.setBorder(BorderFactory.createCompoundBorder(lineb, tborder));
       playerDataArea.setBounds(icon.getIconWidth() + 10, 410, 150, 100);
       playerDataArea.setVisible(true);
       bPane.add(playerDataArea, new Integer(2));

       //End Day Screen
       endDay = new JTextArea();
       endDay.setEditable(false);
       endDay.setLineWrap(false);
       endDay.setWrapStyleWord(true);
       tb = BorderFactory.createTitledBorder("End Of Day!");
       Border lb = BorderFactory.createLineBorder(Color.BLACK,8);
       tb.setTitleFont(new Font("Serif",Font.BOLD,30));
       endDay.setBorder(BorderFactory.createCompoundBorder(lb,tb));
       endDay.setBounds(250, 250, 500, 350);
       endDay.setBackground(new Color(135, 173, 94));
       endDay.setFont(new Font("Monospaced",Font.BOLD,16));
       endDay.setVisible(false);
       bPane.add(endDay,new Integer(6)); // very top layer
      }

   public void flash(JLabel curJ) {
      posTemp = curJ.getY();
      offset = 0;
      direction = -1;
      Xtrack = curJ.getX();
      timer = new Timer(500, new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            if ((Math.abs(curJ.getX() - Xtrack) > 1) || (Math.abs(curJ.getY() - posTemp) > 5)) {
               Xtrack = curJ.getX();
               posTemp = curJ.getY();
            }
            offset += (direction * 5);
             curJ.setBounds(curJ.getX(),posTemp+offset,curJ.getWidth(),curJ.getHeight());
             if (offset >= -5 || offset <= 0) {
               direction *= -1;
             }
         }
       });      
       timer.start();
   }
   public void stopFlash(JLabel curJ) {
      curJ.setBounds(curJ.getX(), posTemp, curJ.getWidth(), curJ.getHeight());
      timer.stop();
   }
  public void makePlayers(int numPlayers) {
   // Add a dice to represent a player. 
   // Role for Crusty the prospector. The x and y co-ordiantes are taken from Board.xml file
   playerlabel = new JLabel[numPlayers];
   for (int i = 0; i < playerlabel.length;i++) {
      playerlabel[i] = new JLabel();
   }
   String[] playerDice = {"dice/r1.png","dice/b1.png","dice/c1.png","dice/g1.png","dice/o1.png","dice/p1.png","dice/w1.png","dice/v1.png","dice/y1.png"};
   for (int i = 0; i < playerlabel.length;i++) {
      JLabel j = playerlabel[i];
      ImageIcon pIcon = new ImageIcon(playerDice[i]);
      j.setIcon(pIcon);
      j.setBounds(114,227,46,46);
      j.setVisible(true);
      bPane.add(j,new Integer(3));      
   }
   //create accompanying practice counters
   practiceCounter = new JLabel[numPlayers];
   for (int i = 0; i < practiceCounter.length;i++) {
      practiceCounter[i] = new JLabel("");
      practiceCounter[i].setFont(new Font("Arial",Font.BOLD,14));
      practiceCounter[i].setForeground(Color.BLACK);
      practiceCounter[i].setHorizontalAlignment(SwingConstants.CENTER);
      practiceCounter[i].setVerticalAlignment(SwingConstants.CENTER);
      practiceCounter[i].setBounds(0,0,44,20); ////edit this
      practiceCounter[i].setVisible(false);
      bPane.add(practiceCounter[i],new Integer(4));
   }
  }

  public void diceRoll(int faceNum) {
   if (rollFlag) {
      return;
   }
   rollFlag = true;
   rollCounter = 0;
    ImageIcon im = new ImageIcon("dice/w" + faceNum +".png");
    Image img = im.getImage();
    img = img.getScaledInstance(110, 110, Image.SCALE_SMOOTH);
    im = new ImageIcon(img);
    diceRoll = new JLabel();
    diceRoll.setIcon(im);
    diceRoll.setBounds(boardlabel.getWidth() + 10, 650, 90, 90);
    diceRoll.setOpaque(false);
    bPane.add(diceRoll,new Integer(4));
    diceRoll.setVisible(true);
    int delay = 22;
    Random rand = new Random();
    final Timer roll = new Timer(delay,null);
    roll.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
         int fnum = rand.nextInt(6) + 1;

         if (rollCounter == 19 || rollCounter == 20) {
            fnum = faceNum;
            roll.setDelay(2000);
         }

         if (rollCounter >= 21) {
            diceRoll.setVisible(false);
            roll.stop();
            rollFlag = false;
         } else {
         ImageIcon i = new ImageIcon("dice/w" + fnum+ ".png");
         Image ig = i.getImage();
         ig = ig.getScaledInstance(90, 90, Image.SCALE_SMOOTH);
         i = new ImageIcon(ig);
         diceRoll.setIcon(i);
         rollCounter++;
         roll.setDelay((int)(roll.getDelay()*1.2));
         }
      }
    });
    roll.start();
  }
  
  public void giveMouseListener(JButton bb) {
   bb.addMouseListener(new boardMouseListener());
  }


  // This class implements Mouse Events
  
  class boardMouseListener implements MouseListener{
      // Code for the different button clicks
      public void mouseClicked(MouseEvent e) {
         //Sections to render buttons disabled when end day menu is visible.
         if (endDay.isVisible()){
            if (e.getSource()==next) {
               endDay.setVisible(false);
               endDay.setText(null);
               next.setVisible(false);
               backCover.setVisible(false);
            }
            return;
         }
         if (e.getSource() == destinations[0]) {
            controller.moveToNeighborIndex(0);
         }
         else if (e.getSource() == destinations[1]) {
            controller.moveToNeighborIndex(1);
         }
         else if (e.getSource() == destinations[2]) {
            controller.moveToNeighborIndex(2);
         }
         if (e.getSource() == destinations[3]) {
            controller.moveToNeighborIndex(3);
         }
         if (e.getSource()== bAct){
            controller.act();
            currentAction = "";
         }
         else if(e.getSource() == bEndDay) {
            controller.endDay();
            currentAction = "";
         }
         else if (e.getSource()== bRehearse){
            controller.rehearse();
            currentAction = "";
         }
         else if (e.getSource()== bMove){
            appendToOutput("Select your destination");
            //removes old buttons to allow move buttons to popup
            bAct.setVisible(false);
            bRehearse.setVisible(false);
            bMove.setVisible(false);
            bTakeRole.setVisible(false);
            bUpgrade.setVisible(false);
            bEndTurn.setVisible(false);
            bEndDay.setVisible(false);
            controller.neighborButtons();
            currentAction = "move";
         }
         else if (e.getSource() == bTakeRole){
            appendToOutput("Enter the role you want to take in the input box below and press Enter.");
            inputArea.setVisible(true);
            currentAction = "takeRole";
         }
         else if (e.getSource() == bUpgrade){
            appendToOutput("Enter the rank you want to upgrade to and the currency (Dollars/Credits) in the input box below and press Enter. Please seperate them with a space.\n");
            inputArea.setVisible(true);
            currentAction = "upgrade";
         }
         else if (e.getSource() == bEndTurn){
            controller.endTurn();
            currentAction = "";
         }           
      }
      public void mousePressed(MouseEvent e) {
      }
      public void mouseReleased(MouseEvent e) {
      }
      public void mouseEntered(MouseEvent e) {
      }
      public void mouseExited(MouseEvent e) {
      }
   }

   //called to exit from moving state
   public void resetButtons() {
      bAct.setVisible(true);
      bRehearse.setVisible(true);
      bMove.setVisible(true);
      bTakeRole.setVisible(true);
      bUpgrade.setVisible(true);
      bEndTurn.setVisible(true);
      bEndDay.setVisible(true);
      for (JButton jbm : destinations) {
         jbm.setVisible(false);
      }
   }
   //puts text in text output area
   public void appendToOutput(String text) {
      outputArea.setText(null);
      outputArea.append(text + "\n");
      outputArea.setCaretPosition(outputArea.getDocument().getLength()); // Auto-scroll to the bottom
   }
 
   //Used to handle the text input listened inputs
   public void processInput(String input) {
      appendToOutput("Input received: " + input);
      if (currentAction.equalsIgnoreCase("takeRole")) {
          controller.takeRole(input);
      } else if (currentAction.equalsIgnoreCase("upgrade")) {
         String[] parts = input.split(" ");
         if (parts.length == 2) {
             try {
                 int rank = Integer.parseInt(parts[0]);
                 String currency = parts[1];
                 controller.upgrade(rank, currency);
             } catch (NumberFormatException e) {
                 appendToOutput("Invalid input format. Please enter rank and currency.");
             }
         } else {
             appendToOutput("Invalid input format. Please enter rank and currency.");
         }
     }
      currentAction = "";
   }
   //closes input area
   public void closeText() {
      inputArea.setVisible(false);
   }
   //used to change playerData field
   public void updatePlayerData(String data) {
      playerDataArea.setText(data);
   }
   //not sure if this is needed
   public void update() {
      bPane.repaint();
      super.repaint();
   }
}