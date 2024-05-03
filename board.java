import java.io.File;

public class board {
    Location[] location;
    card[] deck;
    int wrapCounter;

    //The XML file may be passed to this constructor
    public board(File f) {
        deck = new card[36/*make this number of cards */];
        //parse XML
    }
    private void shuffleDeck() {
        //use either collections shuffle or Random class with swaps
    }
    public card getCard() {
        return deck[0/*some moving index */];
    }
    
    //call to check whether wrapCoutner>1
    public boolean moreScenes() {
        return true;
    }
}
