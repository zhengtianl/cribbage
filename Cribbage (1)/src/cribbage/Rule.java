package cribbage;

import java.util.ArrayList;
import ch.aplu.jcardgame.*;

/**
 * W09 team 03
 * Represents rules in game
 */
public abstract class Rule {
    public enum RuleType{
        FIFTEEN, THIRTYONE, RUNS, PAIRS, FLUSH, JACK
    }
    protected RuleType rule;
    protected int addition;
    protected Deck deck;
    public Hand result;
    Rule(){}

    Rule(RuleType r, int additionInfo){
        rule = r;
        addition = additionInfo;
        result = null;
    }

    public abstract boolean checkIsPair(Hand hand);

    public abstract boolean checkIsRun(Hand hand);

    public abstract boolean checkIsFlush(Hand hand);

    public abstract boolean checkIsJack(Hand hand);

    public boolean checkIs31(Hand hand){
        if(Cribbage.total(hand) == 31){
            rule = RuleType.THIRTYONE;
            addition = 0;
            return true;
        }
        return false;
    }

    public boolean checkIs15(Hand hand){
        if(Cribbage.total(hand) == 15){
            rule = RuleType.FIFTEEN;
            addition = 0;
            return true;
        }
        return false;
    }

    static private void getTotalRuleTotalTo15(Hand tmpHand, ArrayList<Rule> validRules, Deck deck, ArrayList<Card> cards, int idx, int left){
        if(left == 0){
            ShowRule rule = new ShowRule(null, null);
            rule.rule = RuleType.FIFTEEN;
            rule.addition = 0;
            rule.result = new Hand(deck);
            for(Card c: cards){
                rule.result.insert(c.getSuit(), c.getRank(), false);
            }
            validRules.add(rule);
            return ;
        }
        if(idx >= 5){
            return ;
        }
        if(Cribbage.cardValue(tmpHand.get(idx)) <= left){
            cards.add(tmpHand.get(idx));
            getTotalRuleTotalTo15(tmpHand, validRules, deck, cards, idx + 1, left - Cribbage.cardValue(tmpHand.get(idx)));
            cards.remove(cards.size() - 1);
        }
        getTotalRuleTotalTo15(tmpHand, validRules, deck, cards, idx + 1, left);
    }

    static ArrayList<Rule> getValidShowRules(Hand hand, Card start, Deck deck){
        ArrayList<Rule> validRules = new ArrayList<Rule>();
        Hand tmpHand = new Hand(deck);
        tmpHand.insert(start.getSuit(), start.getRank(), false);
        for(Card c: hand.getCardList()){
            tmpHand.insert(c.getSuit(), c.getRank(), false);
        }
        tmpHand.sort(Hand.SortType.POINTPRIORITY, false);
        getTotalRuleTotalTo15(tmpHand, validRules, deck, new ArrayList<Card>(), 0, 15);
        ShowRule tmpRule = new ShowRule(start, deck);
        if(tmpRule.checkIsPair(hand)){
            validRules.add((ShowRule)tmpRule.clone());
        }
        if(tmpRule.checkIsRun(hand)){
            validRules.add((ShowRule)tmpRule.clone());
        }
        return validRules;
    }

    static ArrayList<Rule> getValidPlayRules(Hand hand, Deck deck){
        ArrayList<Rule> validRules = new ArrayList<Rule>();
        PlayRule tmpRule = new PlayRule(deck);
        if(tmpRule.checkIs31(hand) || tmpRule.checkIs15(hand)){
            validRules.add((PlayRule)tmpRule.clone());
        }
        if(tmpRule.checkIsRun(hand)){
            validRules.add((PlayRule)tmpRule.clone());
        }
        if(tmpRule.checkIsPair(hand)){
            validRules.add((PlayRule)tmpRule.clone());
        }
        if(tmpRule.checkIsFlush(hand)){
            validRules.add((PlayRule)tmpRule.clone());
        }
        if(tmpRule.checkIsJack(hand)){
            validRules.add((PlayRule)tmpRule.clone());
        }
        return validRules;
    }

    @Override
    public String toString(){
        switch (rule) {
            case PAIRS:
                return String.format("pair%d", addition);
            case THIRTYONE:
                return "thirtyone";
            case FIFTEEN:
                return "fifteen";
            case RUNS:
                return String.format("run%d", addition);
            case JACK:
                return "jack";
            case FLUSH:
                return String.format("flush%d", addition);
            default:
                return "";
        }
    }

    public int toScore(){
        switch (rule) {
            case PAIRS:
                if(addition == 2){
                    return 2;
                }
                else if(addition == 3){
                    return 6;
                }
                else if(addition == 4){
                    return 12;
                }
            case THIRTYONE:
            case FIFTEEN:
                return 2;
            case RUNS:
                return addition;
            case JACK:
                return 1;
            case FLUSH:
                return addition;
            default:
                return 0;
        }
    }
}
