package com.arcenium.speedruntimer.model.components;

import com.arcenium.speedruntimer.model.GameSplits;
import com.arcenium.speedruntimer.model.Split;
import com.arcenium.speedruntimer.utility.Converter;

public class BestPossibleTimeComponent implements Component{
    /******************** Component Fields ********************/
    private final String name;
    private double valueInSeconds;
    private final Converter converter;

    /******************** Constructor ********************/
    public BestPossibleTimeComponent() {
        this.name = "Best Possible Time";
        this.converter = Converter.getINSTANCE();
    }

    /******************** Mandatory Functions For Interface ********************/
    @Override
    public void update(GameSplits splits, int currentSplitIndex) {
        valueInSeconds = 0.00;
        int index = 0;
        for(Split split : splits.getSplits()){
            if(index < currentSplitIndex){
                valueInSeconds+=split.getLength();
            }
            else{
                valueInSeconds+=split.getBestTime();
            }
            index++;
        }
    }

    @Override
    public String getValue() {
        return converter.secondsToTimeString(valueInSeconds);
    }

    @Override
    public String getName() {
        return this.name;
    }
}//End of BestPossibleTimeComponent Class
