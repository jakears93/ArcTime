package com.arcenium.speedruntimer.model.components;

import com.arcenium.speedruntimer.model.ComparisonType;
import com.arcenium.speedruntimer.model.GameInfo;
import com.arcenium.speedruntimer.model.Split;
import com.arcenium.speedruntimer.utility.Converter;
import com.arcenium.speedruntimer.utility.SettingsManager;
import org.apache.commons.math3.util.Precision;

public class PreviousSegmentComponent implements Component{
    /******************** Component Fields ********************/
    private final String name;
    private boolean isFirstSplit;
    private ComparisonType type;
    private double timeDifference;
    private final Converter converter;

    /******************** Constructor ********************/
    public PreviousSegmentComponent() {
        this.type = SettingsManager.getINSTANCE().getSettings().getComparisonType();
        this.name = "Previous Segment("+type+")";
        this.isFirstSplit = true;
        this.timeDifference = 0.00;
        this.converter = Converter.getINSTANCE();
    }

    /******************** Mandatory Functions For Interface ********************/
    @Override
    public void update(GameInfo gameInfo, int currentSplitIndex) {
        if(currentSplitIndex == 0){
            this.isFirstSplit = true;
            return;
        }
        this.isFirstSplit = false;
        this.type = SettingsManager.getINSTANCE().getSettings().getComparisonType();

        Split split = gameInfo.getSplits().get(currentSplitIndex-1);
        if(this.type == ComparisonType.PB){
            this.timeDifference = split.getPbTime()- split.getLength();
        }
        else if(this.type == ComparisonType.BEST){
            this.timeDifference = split.getBestTime()- split.getLength();
        }
    }

    @Override
    public String getValue() {
        if(isFirstSplit){
            return "";
        }
        else{
            StringBuilder sb = new StringBuilder();
            timeDifference = Precision.round(timeDifference, SettingsManager.getINSTANCE().getSettings().getTimerDecimalAccuracy());
            if(timeDifference > 0.00){
                sb.append("+");
            }
            sb.append(converter.secondsToTimeString(timeDifference));
            return sb.toString();
        }
    }

    @Override
    public String getName() {
        return this.name;
    }
}//End of PreviousSegmentComponent Class
