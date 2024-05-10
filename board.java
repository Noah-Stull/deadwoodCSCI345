import java.io.File;
import java.util.Random;

public class Board {
    private Set[] sets;
    private Card[] deck;
    private int wrapCounter;
    private int deckIndex = -1;

    //The XML file may be passed to this constructor
    public Board(String f, File f2)  {
        wrapCounter = 0;
        
        ParseCard parser = new ParseCard();
        deck = parser.parse(f);

    }
    private void shuffleDeck() {
        Random rand = new Random();
        for (int i = 0; i < deck.length; i++) {
            Card temp = deck[i];
            int swapIndex = rand.nextInt(deck.length);
            deck[i] = deck[swapIndex];
            deck[swapIndex] = temp;
        }
    }
    public Card getCard() {
        deckIndex++;
        return deck[deckIndex];
        
    }
    
    //call to check whether wrapCoutner>1
    public boolean moreScenes() {
        return (wrapCounter>1);
    }
}
