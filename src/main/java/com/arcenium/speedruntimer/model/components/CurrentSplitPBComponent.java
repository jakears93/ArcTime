package com.arcenium.speedruntimer.model.components;

import com.arcenium.speedruntimer.model.GameInfo;
import com.arcenium.speedruntimer.utility.Converter;

public class CurrentSplitPBComponent implements Component{
    /******************** Component Fields ********************/
    private final String name;
    private double valueInSeconds;
    private final Converter converter;

    /******************** Constructor ********************/
    public CurrentSplitPBComponent() {
        this.name = "PB:";
        this.valueInSeconds = 0;
        this.converter = Converter.getINSTANCE();
    }

    /******************** Mandatory Functions For Interface ********************/
    @Override
    public void update(GameInfo gameInfo, int currentSplitIndex) {
        valueInSeconds = gameInfo.getSplits().get(currentSplitIndex).getPbTime();
    }

    @Override
    public String getValue() {
        return converter.secondsToTimeString(valueInSeconds);
    }

    @Override
    public String getName() {
        return this.name;
    }
}//End of CurrentSplitPBComponent Class
