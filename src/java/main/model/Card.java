package main.model;

public class Card {

    private int value;
    private String suit;

    public Card(String s) {
        switch(s.toLowerCase().charAt(0)){
            case 'a':
                this.value = 14;
                break;
            case 't':
                this.value = 10;
                break;
            case 'j':
                this.value = 11;
                break;
            case 'q':
                this.value = 12;
                break;
            case 'k':
                this.value = 13;
                break;
            default:
                this.value = Character.getNumericValue(s.toLowerCase().charAt(0));
        }

        this.suit = String.valueOf(s.toUpperCase().charAt(1));

    }

    public int getValue() {
        return value;
    }

    public String getSuit() {
        return suit;
    }
}
