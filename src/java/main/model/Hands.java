package main.model;

import main.model.combination.OneKind;
import main.model.combination.Rests;
import main.model.combination.TwoKinds;
import main.type.Combination;

import java.util.*;

public class Hands {

    private List<Card> cards;

    private List<Integer> valueList = new ArrayList<>();

    private Map<Integer, Integer> valueMap = new HashMap<>();

    private OneKind oneKind;

    private Rests rests;

    private TwoKinds twoKinds;

    private Combination combination;

    private boolean isSameSuit;

    private boolean isStraight;

    public Hands(String string) {
        List<Card> cards = new ArrayList<>();
        for(String s: string.split(" ")){
            cards.add(new Card(s));
        }
        this.cards = cards;
    }

    public Hands(List<Card> cards) {
        this.cards = cards;
    }

    public OneKind getOneKind() {
        return oneKind;
    }

    public Rests getRests() {
        return rests;
    }

    public TwoKinds getTwoKinds() {
        return twoKinds;
    }

    public Combination getCombination() {
        return combination;
    }

    private void determineSameSuit(){
        String s = this.cards.get(0).getSuit();
        for (Card c: cards) {
            if(!c.getSuit().equals(s)){
                this.isSameSuit = false;
                return;
            }
        }
        this.isSameSuit = true;
    }

    private void populateValueList(){
        for (Card c: cards) {
            this.valueList.add(c.getValue());
        }
        Collections.sort(this.valueList);
    }

    private void populateValueMap(){
        for (int value: this.valueList) {
            if(this.valueMap.containsKey(value)){
                this.valueMap.put(value, this.valueMap.get(value) + 1);
            } else {
                this.valueMap.put(value, 1);
            }
        }
    }

    private void determineStraight(){
        for (int i = 0; i<4; i++) {
            if(this.valueList.get(i + 1) - this.valueList.get(i) != 1){
                this.isStraight = false;
                return;
            }
        }
        this.isStraight = true;
    }

    public void determineCombination(){
        this.populateValueList();
        this.populateValueMap();
        this.determineStraight();
        this.determineSameSuit();

        if(this.isRoyalFlush()){
            this.combination = Combination.ROYAL_FLUSH;
        } else if(this.isStraightFlush()){
            this.combination = Combination.STRAIGHT_FLUSH;
        } else if(this.isFourOfKind()){
            this.combination = Combination.FOUR_OF_A_KIND;
        } else if(this.isFullHouse()){
            this.combination = Combination.FULL_HOUSE;
        } else if(this.isFlush()){
            this.combination = Combination.FLUSH;
        } else if(this.isStraight()){
            this.combination = Combination.STRAIGHT;
        } else if(this.isThreeOfAKind()){
            this.combination = Combination.THREE_OF_A_KIND;
        } else if(this.isTwoPairs()){
            this.combination = Combination.TWO_PAIRS;
        } else if(this.isPair()){
            this.combination = Combination.PAIR;
        } else { // high cards
            this.rests = new Rests(this.valueList);
            this.combination = Combination.HIGH_CARD;
        }
    }

    private boolean isRoyalFlush(){
        return this.isStraight && this.isSameSuit && this.valueList.get(4) == 14; // Ace value is 14
    }

    private boolean isStraightFlush(){
        if(this.isStraight && this.isSameSuit && this.valueList.get(4) != 14){ // Ace value is 14
            this.oneKind = new OneKind(this.valueList.get(4));
            return true;
        }
        return false;
    }

    private boolean isFourOfKind(){
        if(this.valueMap.size() == 2 && this.valueMap.containsValue(4)){
            for(int key: this.valueMap.keySet()){
                if(this.valueMap.get(key) == 4){
                    this.oneKind = new OneKind(key);
                } else {
                    this.rests = new Rests(Collections.singletonList(key));
                }
            }
            return true;
        }
        return false;
    }

    private boolean isFullHouse(){
        if(this.valueMap.size() == 2 && this.valueMap.containsValue(3)){
            int highValue = 0;
            int lowValue = 0;
            for(int key: this.valueMap.keySet()){
                if(this.valueMap.get(key) == 3){
                    highValue = key;
                } else {
                    lowValue = key;
                }
            }
            this.twoKinds = new TwoKinds(highValue, lowValue);
            return true;
        }
        return false;
    }

    private boolean isFlush(){
        if(this.isSameSuit){
            this.rests = new Rests(this.valueList);
            return true;
        }
        return false;
    }

    private boolean isStraight(){
        if(this.isStraight){
            this.oneKind = new OneKind(this.valueList.get(4));
            return true;
        }
        return false;
    }

    private boolean isThreeOfAKind(){
        if(this.valueMap.size() == 3 && this.valueMap.containsValue(3)){
            List<Integer> list = new ArrayList<>();
            for(int key: this.valueMap.keySet()){
                if(this.valueMap.get(key) == 3){
                    this.oneKind = new OneKind(key);
                } else {
                    list.add(key);
                }
            }
            this.rests = new Rests(list);
            return true;
        }
        return false;
    }

    private boolean isTwoPairs(){
        if(this.valueMap.size() == 3 && !this.valueMap.containsValue(3)){
            List<Integer> list = new ArrayList<>();
            int highValue = 0;
            int lowValue = 15;
            for(int key: this.valueMap.keySet()){
                if(this.valueMap.get(key) == 2){
                    if(key > highValue){
                        highValue = key;
                    }
                    if(key < lowValue){
                        lowValue = key;
                    }
                } else {
                    list.add(key);
                }
            }
            this.twoKinds = new TwoKinds(highValue, lowValue);
            this.rests = new Rests(list);
            return true;
        }
        return false;
    }

    private boolean isPair(){
        if(this.valueMap.size() == 4 && this.valueMap.containsValue(2)){
            List<Integer> list = new ArrayList<>();
            for(int key: this.valueMap.keySet()){
                if(this.valueMap.get(key) == 2){
                    this.oneKind = new OneKind(key);
                } else {
                    list.add(key);
                }
            }
            this.rests = new Rests(list);
            return true;
        }
        return false;
    }

    public int beat(Hands hands){
        if(this.combination.getRank() > hands.getCombination().getRank()){
            return 1;
        } else if(this.combination.getRank() < hands.getCombination().getRank()){
            return -1;
        } else {
            int result = 0;
            switch(this.combination.getRank()){
                case 10:
                    return 0;
                case 9:
                case 5:
                    return this.oneKind.beat(hands.getOneKind());
                case 8:
                case 4:
                case 2:
                    result = this.oneKind.beat(hands.getOneKind());
                    return result == 0 ? this.rests.beat(hands.rests) : result;
                case 7:
                    return this.twoKinds.beat(hands.getTwoKinds());
                case 3:
                    result = this.twoKinds.beat(hands.getTwoKinds());
                    return result == 0 ? this.rests.beat(hands.rests) : result;
                default:
                    return this.rests.beat(hands.rests);
            }
        }
    }

}
