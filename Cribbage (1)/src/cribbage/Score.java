package cribbage;

import ch.aplu.jcardgame.*;

import java.util.ArrayList;

/**
 * W09 team 03
 * Adapter to get the total valid rules
 */
public abstract class Score{
    protected Hand hand;
    Score(Hand currentHand){
        hand = currentHand;
    }

    abstract public ArrayList<Rule> getValidRules(Deck deck);
}
