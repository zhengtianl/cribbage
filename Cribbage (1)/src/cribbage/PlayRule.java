package cribbage;

import java.util.ArrayList;
import ch.aplu.jcardgame.*;

/**
 * W09 team 03
 * Represents rules in play stage
 */
public class PlayRule extends Rule{
    PlayRule(Deck deck){
        super();
        this.deck = deck;
    }

    PlayRule(RuleType rule, int addition){
        super(rule, addition);
    }

    @Override
    public boolean checkIsPair(Hand hand){
        int size = hand.getNumberOfCards();
        int cnt = 0;
        if(size >= 2){
            size -= 1;
            while(size >= 1 && Cribbage.cardOrder(hand.get(size)) == Cribbage.cardOrder(hand.get(size - 1))){
                size -= 1;
                cnt += 1;
            }
            if(cnt > 0){
                rule = RuleType.PAIRS;
                addition = cnt + 1;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkIsRun(Hand hand){
        int size = hand.getNumberOfCards();
        if(size >= 3){
            for(int i = size; i >= 3; i--){
                Hand tmpHand = new Hand(deck);
                for(int j = 0; j < i; j++){
                    Card c = hand.get(size - j - 1);
                    tmpHand.insert(c.getSuit(), c.getRank(), false);
                }
                tmpHand.sort(Hand.SortType.POINTPRIORITY, false);
                int cnt = 0;
                for(int j = 0; j < i; ++j){
                    if(Cribbage.cardOrder(tmpHand.get(j)) - Cribbage.cardOrder(tmpHand.get(0)) == j) {
                        cnt += 1;
                    }
                }
                if(cnt == size){
                    rule = RuleType.RUNS;
                    addition = cnt;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean checkIsJack(Hand hand){
        return false;
    }

    @Override
    public boolean checkIsFlush(Hand hand){
        return false;
    }

    @Override
    protected PlayRule clone() {
        PlayRule clone = new PlayRule(rule, addition);
        return clone;
    }
}
