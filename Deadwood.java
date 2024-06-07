import javax.swing.JOptionPane;

public class Deadwood {
      public static void main(String[] args) {
  
    BoardLayersListener board = new BoardLayersListener(null);
    board.setVisible(true);
    
    // Take input from the user about number of players
    int players;
    while(true) {
      players = Integer.parseInt(JOptionPane.showInputDialog(board, "How many players?")); 
      if (players > 1 && players < 9) break;
    }
    board.makePlayers(players);
    board.controller = new Controller(players,board);
  }
}
