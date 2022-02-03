package com.arcenium.speedruntimer.model.components;

import com.arcenium.speedruntimer.model.GameSplits;
import com.arcenium.speedruntimer.utility.Converter;

public class CurrentSplitBestComponent implements Component{
    private final String name;
    private double valueInSeconds;
    private final Converter converter;

    public CurrentSplitBestComponent() {
        this.name = "Best:";
        this.valueInSeconds = 0;
        this.converter = Converter.getINSTANCE();
    }

    @Override
    public void update(GameSplits splits, int currentSplitIndex) {
        valueInSeconds = splits.getSplits().get(currentSplitIndex).getBestTime();
    }

    @Override
    public String getValue() {
        return converter.secondsToTimeString(valueInSeconds);
    }

    @Override
    public String getName() {
        return this.name;
    }
}
