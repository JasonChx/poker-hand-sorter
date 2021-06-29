package main.model.combination;

public class TwoKinds {

    private int highValue;

    private int lowValue;

    public TwoKinds(int highValue, int lowValue) {
        this.highValue = highValue;
        this.lowValue = lowValue;
    }

    public int getHighValue() {
        return highValue;
    }

    public int getLowValue() {
        return lowValue;
    }

    public int beat(TwoKinds twoKinds){
        if(this.highValue > twoKinds.getHighValue()){
            return 1;
        } else if(this.highValue < twoKinds.getHighValue()){
            return -1;
        } else if(this.lowValue > twoKinds.getLowValue()){
            return 1;
        } else if(this.lowValue < twoKinds.getLowValue()){
            return -1;
        }
        return 0;
    }
}
