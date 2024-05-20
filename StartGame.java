public class StartGame {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Please enter the number of players as a single argument.");
            return;
        }

        int numPlayers = 0;
        try {
            numPlayers = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid integer for the number of players.");
            return;
        }

        if (numPlayers < 2 || numPlayers > 8) {
            System.out.println("Please enter a valid number of players (between 2 and 8).");
            return;
        }

        new Game(numPlayers);
        
    }
}
