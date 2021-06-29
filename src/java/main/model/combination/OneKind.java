package main.model.combination;

public class OneKind {

    private int value;

    public OneKind(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public int beat(OneKind oneKind){
        if(this.value > oneKind.getValue()){
            return 1;
        } else if(this.value < oneKind.getValue()){
            return -1;
        }
        return 0;
    }
}
