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
        wrapCounter = 10;
    }
    public void resetBoard() {
        for (Set s : sets) {
            s.reset();
        }
        wrapCounter = 10;
    }

    //shuffles array of cads
    private void shuffleDeck() {
        Random rand = new Random();
        for (int i = 0; i < deck.length; i++) {
            Card temp = deck[i];
            int swapIndex = rand.nextInt(deck.length);
        System.out.println("Switching card indexes  " + i + " and " + swapIndex);;
            deck[i] = deck[swapIndex];
            deck[swapIndex] = temp;
        }
    }
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
    public void wrapScene() {
        wrapCounter--;
        if(wrapCounter == 1) {
            controller.endDay();
        }
    }
    public Set[] getSets() {
        return sets;
    }
    //returns all but the last two sets which are special.
    public Set[] getSceneSets() {
        return Arrays.copyOfRange(sets,0,sets.length - 2);
    }
}
