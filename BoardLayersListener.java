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
  
  // JLayered Pane
  JLayeredPane bPane;
  
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
       mLabel.setBounds(icon.getIconWidth()+40,0,100,20);
       bPane.add(mLabel,new Integer(2));

       // Create Action buttons
       bAct = new JButton("ACT");
       bAct.setBackground(Color.white);
       bAct.setBounds(icon.getIconWidth()+10, 30,100, 20);
       bAct.addMouseListener(new boardMouseListener());
       
       bRehearse = new JButton("REHEARSE");
       bRehearse.setBackground(Color.white);
       bRehearse.setBounds(icon.getIconWidth()+10,60,100, 20);
       bRehearse.addMouseListener(new boardMouseListener());
       
       bMove = new JButton("MOVE");
       bMove.setBackground(Color.white);
       bMove.setBounds(icon.getIconWidth()+10,90,100, 20);
       bMove.addMouseListener(new boardMouseListener());

       // Place the action buttons in the top layer
       bPane.add(bAct, new Integer(2));
       bPane.add(bRehearse, new Integer(2));
       bPane.add(bMove, new Integer(2));
  }
  public void makePlayers(int numPlayers) {
   // Add a dice to represent a player. 
   // Role for Crusty the prospector. The x and y co-ordiantes are taken from Board.xml file
   playerlabel = new JLabel[numPlayers];
   for (int i = 0; i < playerlabel.length;i++) {
      playerlabel[i] = new JLabel();
   }
   String[] playerDice = {"dice/r1.png","dice/r2.png","dice/r3.png","dice/r4.png","dice/r5.png","dice/r6.png","dice/b1.png","dice/b2.png","dice/b3.png"};
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
            //controller.act();
            System.out.println("Acting is Selected\n");
         }
         else if (e.getSource()== bRehearse){
            System.out.println("Rehearse is Selected\n");
         }
         else if (e.getSource()== bMove){
            System.out.println("Move is Selected\n");
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