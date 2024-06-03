/*

   Deadwood GUI helper file
   Author: Moushumi Sharmin
   This file shows how to create a simple GUI using Java Swing and Awt Library
   Classes Used: JFrame, JLabel, JButton, JLayeredPane

*/

import java.awt.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.util.Random;

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
  
  //JButtons
  JButton bAct;
  JButton bRehearse;
  JButton bMove;
  JButton bTakeRole;
  JButton bUpgrade;
  JButton bEndTurn;
  
  // JLayered Pane
  JLayeredPane bPane;

  JTextArea inputArea;
  JTextArea outputArea;

  String currentAction = "";

  
  // Constructor
  
  public BoardLayersListener(Controller c) {
      
       // Set the title of the JFrame
       super("Deadwood");
       controller = c; 
       // Set the exit option for the JFrame
       setDefaultCloseOperation(EXIT_ON_CLOSE);
      
       // Create the JLayeredPane to hold the display, cards, dice and buttons
       bPane = getLayeredPane();
    
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
         j.setOpaque(true);
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
         shot[i].setOpaque(true);
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
       bUpgrade.setBackground(Color.white);
       bUpgrade.setBounds(icon.getIconWidth()+10,150,110, 20);
       bUpgrade.addMouseListener(new boardMouseListener());

       bEndTurn = new JButton("END TURN");
       bEndTurn.setBackground(Color.white);
       bEndTurn.setBounds(icon.getIconWidth()+10,180,110, 20);
       bEndTurn.addMouseListener(new boardMouseListener());

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
       inputArea.setBounds(icon.getIconWidth() + 10, 330, 150, 50 );
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

       //Output Arwea
       outputArea = new JTextArea();
       outputArea.setEditable(false);
       outputArea.setLineWrap(true);
       outputArea.setWrapStyleWord(true);
       outputArea.setBounds(icon.getIconWidth() + 10, 220, 150, 100);
       outputArea.setVisible(true);
       bPane.add(outputArea, new Integer(2));



      }
   public void flash(JLabel curJ) {
      posTemp = curJ.getY();
      offset = 0;
      direction = -1;
      Xtrack = curJ.getX();
      timer = new Timer(500, new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            if (Math.abs(curJ.getX() - Xtrack) > 1) {
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
   
   
   

  }

  public void diceRoll(int faceNum) {
   if (rollFlag) {
      return;
   }
   rollCounter = 0;
    ImageIcon im = new ImageIcon("dice/w" + faceNum +".png");
    Image img = im.getImage();
    img = img.getScaledInstance(110, 110, Image.SCALE_SMOOTH);
    im = new ImageIcon(img);
    diceRoll = new JLabel();
    diceRoll.setIcon(im);
    diceRoll.setBounds(boardlabel.getWidth() + 10, 400, 110, 110);
    diceRoll.setOpaque(true);
    bPane.add(diceRoll,new Integer(4));
    diceRoll.setVisible(true);
    int delay = 20;
    Random rand = new Random();
    final Timer roll = new Timer(delay,null);
    roll.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
         int fnum = rand.nextInt(6) + 1;

         if (rollCounter == 20) {
            fnum = faceNum;
            roll.setDelay(2600);
         }
         if (rollCounter >= 21) {
            diceRoll.setVisible(false);
            roll.stop();
         }
         else {
         ImageIcon i = new ImageIcon("dice/w" + fnum+ ".png");
         Image ig = i.getImage();
         ig = ig.getScaledInstance(110, 110, Image.SCALE_SMOOTH);
         i = new ImageIcon(ig);
         diceRoll.setIcon(i);
         rollCounter++;
         roll.setDelay((int)(roll.getDelay()*1.2));
         }
      }
    });
    roll.start();
  }
  
  // This class implements Mouse Events
  
  class boardMouseListener implements MouseListener{
      
      // Code for the different button clicks
      public void mouseClicked(MouseEvent e) {
         
         if (e.getSource()== bAct){
            controller.act();
            currentAction = "";
         }
         else if (e.getSource()== bRehearse){
            controller.rehearse();
            currentAction = "";
         }
         else if (e.getSource()== bMove){
            appendToOutput("Enter your destination in the input box below and press Enter.");
            inputArea.setVisible(true);
            currentAction = "move";
         }
         else if (e.getSource() == bTakeRole){
            appendToOutput("Enter the role you want to take in the input box below and press Enter.");
            inputArea.setVisible(true);
            currentAction = "takeRole";
         }
         else if (e.getSource() == bUpgrade){
            controller.upgrade(3,"Dollars");
            appendToOutput("Enter the rank you want to upgrade to and the currency (Dollars/Credits) in the input box below and press Enter. Please seperate them with a space.\n");
            inputArea.setVisible(true);
            currentAction = "";
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

   public void appendToOutput(String text) {
      outputArea.setText(null);
      outputArea.append(text + "\n");
      outputArea.setCaretPosition(outputArea.getDocument().getLength()); // Auto-scroll to the bottom
   }
 
   public void processInput(String input) {
      appendToOutput("Input received: " + input);
      if (currentAction.equalsIgnoreCase("move")) {
         appendToOutput("moving initiated");
            controller.move(input);
      } else if (currentAction.equalsIgnoreCase("takeRole")) {
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
   public void closeText() {
      inputArea.setVisible(false);
   }
   //not sure if this is needed
   public void update() {
      bPane.repaint();
      super.repaint();
   }
  public static void main(String[] args) {
  
    
    BoardLayersListener board = new BoardLayersListener(null);
    board.setVisible(true);
    
    // Take input from the user about number of players
    int players = Integer.parseInt(JOptionPane.showInputDialog(board, "How many players?")); 
    board.makePlayers(players);
    board.controller = new Controller(players,board);
  }
}