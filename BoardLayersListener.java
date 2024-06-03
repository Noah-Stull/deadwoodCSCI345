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

public class BoardLayersListener extends JFrame {

   Controller controller; //For action listener

  // JLabels
  JLabel boardlabel;
  JLabel cardlabels[];
  JLabel playerlabel[] = null;
  JLabel mLabel;
  
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
       outputArea.setVisible(false);
       bPane.add(outputArea, new Integer(2));

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
   //playerlabel.setBounds(114,227,pIcon.getIconWidth(),pIcon.getIconHeight());  
   
  }
  
  // This class implements Mouse Events
  
  class boardMouseListener implements MouseListener{
      
      // Code for the different button clicks
      public void mouseClicked(MouseEvent e) {
         
         if (e.getSource()== bAct){
            controller.act();
            appendToOutput("Acting is Selected\n");
            inputArea.setVisible(false);
            outputArea.setVisible(false);
         }
         else if (e.getSource()== bRehearse){
            controller.rehearse();
            appendToOutput("Rehearse is Selected\n");
            inputArea.setVisible(false);
            outputArea.setVisible(false);
         }
         else if (e.getSource()== bMove){
            appendToOutput("Enter your destination in the input box below and press Enter.");
            inputArea.setVisible(true);
            outputArea.setVisible(true);
            currentAction = "move";
         }
         else if (e.getSource() == bTakeRole){
            appendToOutput("Enter the role you want to take in the input box below and press Enter.");
            inputArea.setVisible(true);
            outputArea.setVisible(true);
            currentAction = "takeRole";
         }
         else if (e.getSource() == bUpgrade){
            controller.upgrade(3,"Dollars");
            appendToOutput("Upgrade is Selected\n");
            inputArea.setVisible(false);
            outputArea.setVisible(false);
         }
         else if (e.getSource() == bEndTurn){
            controller.endTurn();
            appendToOutput("End Turn is Selected\n");
            inputArea.setVisible(false);
            outputArea.setVisible(false);
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
      outputArea.append(text + "\n");
      outputArea.setCaretPosition(outputArea.getDocument().getLength()); // Auto-scroll to the bottom
   }
 
   public void processInput(String input) {
      appendToOutput("Input received: " + input);
      if (currentAction.equalsIgnoreCase("move")) {
         appendToOutput("moving initiated");
            controller.move(input);
      } else if (currentAction.equalsIgnoreCase("takeRole")) {
          controller.takeRole(Integer.parseInt(input));
      }
      currentAction = "";
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