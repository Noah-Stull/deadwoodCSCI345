import java.io.File;

public class Board {
    private Set[] sets;
    private Card[] deck;
    private int wrapCounter;

    //The XML file may be passed to this constructor
    public Board(String f, File f2)  {
        wrapCounter = 0;
        
        ParseCard parser = new ParseCard();
        deck = parser.parse(f);

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
