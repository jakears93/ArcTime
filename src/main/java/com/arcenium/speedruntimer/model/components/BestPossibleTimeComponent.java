package com.arcenium.speedruntimer.model.components;

import com.arcenium.speedruntimer.model.GameSplits;
import com.arcenium.speedruntimer.model.Split;
import com.arcenium.speedruntimer.utility.SettingsManager;
import org.apache.commons.math3.util.Precision;

public class BestPossibleTimeComponent implements Component{
    private final String name;
    private double valueInSeconds;

    public BestPossibleTimeComponent() {
        this.name = "Best Possible Time";
    }

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
        StringBuilder sb = new StringBuilder();
        int hours = (int) (valueInSeconds/3600);
        int minutes = (int)((valueInSeconds%3600)/60);
        double seconds = valueInSeconds%60;
        seconds = Precision.round(seconds, SettingsManager.getINSTANCE().getSettings().getTimerDecimalAccuracy());
        if(hours>0){
            sb.append(hours);
            sb.append(":");
            sb.append(minutes);
            sb.append(":");
            sb.append(seconds);
        }
        else if(minutes>0){
            sb.append(minutes);
            sb.append(":");
            sb.append(seconds);
        }
        else{
            sb.append(seconds);
        }
        return sb.toString();
    }

    @Override
    public String getName() {
        return this.name;
    }
}
