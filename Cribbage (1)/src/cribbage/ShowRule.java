package cribbage;

import java.util.ArrayList;
import ch.aplu.jcardgame.*;

/**
 * W09 team 03
 * Represents rules in show stage
 */
public class ShowRule extends Rule{
    Card starter;
    ShowRule(Card card, Deck deck){
        super();
        starter = card;
        this.deck = deck;
    }

    ShowRule(RuleType rule, int addition, Card card, Deck deck){
        super(rule, addition);
        starter = card;
        this.deck = deck;
    }

    private Hand getNewHand(Hand hand){
        Hand tmpHand = new Hand(deck);
        tmpHand.insert(starter.getSuit(), starter.getRank(), false);
        for(Card c: hand.getCardList()){
            tmpHand.insert(c.getSuit(), c.getRank(), false);
        }
        // tmpHand.sort(Hand.SortType.POINTPRIORITY, false);
        return tmpHand;
    }


    // need FIX?
    @Override
    public boolean checkIsPair(Hand hand){
        hand = getNewHand(hand);
        for(int i = 0; i < hand.getNumberOfCards(); ++i){
            int cnt = 0;
            for(int j = i + 1; j < hand.getNumberOfCards(); ++j){
                if(Cribbage.cardOrder(hand.get(i)) == Cribbage.cardOrder(hand.get(j))) {
                    cnt += 1;
                }
            }
            if(cnt > 0) {
                cnt = 0;
                result = new Hand(deck);
                for(int j = i; j < hand.getNumberOfCards(); ++j){
                    if(Cribbage.cardOrder(hand.get(i)) == Cribbage.cardOrder(hand.get(j))) {
                        result.insert(hand.get(j).getSuit(), hand.get(j).getRank(), false);
                        cnt += 1;
                    }
                }
                rule = RuleType.PAIRS;
                addition = cnt;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkIsRun(Hand hand){
        hand = getNewHand(hand);
        for(int i = 0; i < hand.getNumberOfCards(); ++i){
            int cnt = 0;
            for(int j = i + 1; j < hand.getNumberOfCards(); ++j){
                if(Cribbage.cardOrder(hand.get(j)) - Cribbage.cardOrder(hand.get(i)) == j - i) {
                    cnt += 1;
                }
            }
            if(cnt >= 2) {
                cnt = 0;
                result = new Hand(deck);
                for(int j = i; j < hand.getNumberOfCards(); ++j){
                    if(Cribbage.cardOrder(hand.get(j)) - Cribbage.cardOrder(hand.get(i)) == j - i) {
                        result.insert(hand.get(j).getSuit(), hand.get(j).getRank(), false);
                        cnt += 1;
                    }
                }
                rule = RuleType.RUNS;
                addition = cnt;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkIsJack(Hand hand){
        for(int i = 0; i < hand.getNumberOfCards(); ++i){
            Card c = hand.get(i);
            // is jack
            if(Cribbage.cardOrder(c) == 11 && c.getSuit() == starter.getSuit()){
                rule = RuleType.JACK;
                addition = 0;
                result = new Hand(deck);
                result.insert(c.getSuit(), c.getRank(), false);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkIsFlush(Hand hand){
        int cnt = 0;
        Card c = hand.get(0);
        for(int i = 0; i < hand.getNumberOfCards(); ++i){
            // is jack
            if(c.getSuit() == hand.get(i).getSuit()){
                cnt++;
            }
        }
        if(cnt == 4){
            if(c.getSuit() == starter.getSuit()){
                cnt++;
            }
        }
        if(cnt >= 4){
            result = new Hand(deck);
            if(cnt == 5){
                result.insert(starter.getSuit(), starter.getRank(), false);
            }
            for(int i = 0; i < hand.getNumberOfCards(); ++i){
                c = hand.get(i);
                result.insert(c.getSuit(), c.getRank(), false);
            }
            rule = RuleType.FLUSH;
            addition = cnt;
            return true;
        }
        return false;
    }

    @Override
    protected ShowRule clone() {
        ShowRule clone = new ShowRule(rule, addition, starter, deck);
        clone.result = this.result;
        this.result = null;
        return clone;
    }
}
