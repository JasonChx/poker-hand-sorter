package main.model.combination;

import java.util.List;

public class Rests {

    private List<Integer> valueList;

    public Rests(List<Integer> valueList) {
        this.valueList = valueList;
    }

    public List<Integer> getValueList() {
        return valueList;
    }

    public int beat(Rests rests){
        for(int i = 0; i<this.valueList.size(); i++){
            if(this.valueList.get(this.valueList.size()-1-i) > rests.getValueList().get(this.valueList.size()-1-i)){
                return 1;
            } else if(this.valueList.get(this.valueList.size()-1-i) < rests.getValueList().get(this.valueList.size()-1-i)){
                return -1;
            }
        }
        return 0;
    }
}
