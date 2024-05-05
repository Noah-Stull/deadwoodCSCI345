import java.io.File;

public class Board {
    private Set[] sets;
    private Card[] deck;
    private int wrapCounter;

    //The XML file may be passed to this constructor
    public Board(File f) {
        wrapCounter = 0;
        //parse XML for other fields
    }
    private void shuffleDeck() {
        //use either collections shuffle or Random class with swaps
    }
    public Card getCard() {
        return deck[0/*some moving index */];
    }
    
    //call to check whether wrapCoutner>1
    public boolean moreScenes() {
        return (wrapCounter>1);
    }
}
