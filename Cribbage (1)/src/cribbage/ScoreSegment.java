package cribbage;

import ch.aplu.jcardgame.*;

import java.util.ArrayList;
/**
 * W09 team 03
 * Adapter to get the total valid rules in segment
 */
public class ScoreSegment extends Score{
    ScoreSegment(Hand hand){
        super(hand);
    }

    public ArrayList<Rule> getValidRules(Deck deck){
        return Rule.getValidPlayRules(hand, deck);
    }
}
