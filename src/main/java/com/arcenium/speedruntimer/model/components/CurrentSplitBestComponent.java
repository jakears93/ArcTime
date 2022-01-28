package com.arcenium.speedruntimer.model.components;

import com.arcenium.speedruntimer.model.GameSplits;
import com.arcenium.speedruntimer.utility.SettingsManager;
import org.apache.commons.math3.util.Precision;

public class CurrentSplitBestComponent implements Component{
    private final String name;
    private double valueInSeconds;

    public CurrentSplitBestComponent() {
        this.name = "Best:";
        this.valueInSeconds = 0;
    }

    @Override
    public void update(GameSplits splits, int currentSplitIndex) {
        valueInSeconds = splits.getSplits().get(currentSplitIndex).getBestTime();
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
