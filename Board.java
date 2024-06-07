import java.util.Random;
import java.util.Arrays;

public class Board {
    private Set[] sets;
    private Card[] deck;
    private int wrapCounter;
    private int deckIndex = -1;
    Controller controller;

    //The XML file may be passed to this constructor
    public Board(String f, String f2, Controller c)  {
        controller = c;
        ParseCard parser = new ParseCard();
        deck = parser.parse(f);
        shuffleDeck();
        ParseSet parser1 = new ParseSet();
        sets = parser1.parse(f2,this,controller);
        //start wrapCounter at max scene sets
        wrapCounter = 10;
    }
    //Resest all owned sets and wrap counter
    public void resetBoard() {
        for (Set s : sets) {
            s.reset();
        }
        wrapCounter = 10;
    }

    //shuffles array of cards
    private void shuffleDeck() {
        Random rand = new Random();
        for (int i = 0; i < deck.length; i++) {
            Card temp = deck[i];
            int swapIndex = rand.nextInt(deck.length);
            deck[i] = deck[swapIndex];
            deck[swapIndex] = temp;
        }
    }
    //returns a card and iterates through deck
    public Card getCard() {
        deckIndex++;
        if (deckIndex >= deck.length) {
            deckIndex = 0;
        }
        return deck[deckIndex];
    }
    //call to check whether wrapCoutner>1
    public boolean moreScenes() {
        return !(sets.length - 1 <= wrapCounter);
    }
    //Called each time a scene wraps by the scene
    public void wrapScene() {
        wrapCounter--;
        if(wrapCounter == 1) {
            controller.endDay();
        }
    }
    //gets all sets
    public Set[] getSets() {
        return sets;
    }
    //returns all but the last two sets which are special.
    public Set[] getSceneSets() {
        return Arrays.copyOfRange(sets,0,sets.length - 2);
    }
}
