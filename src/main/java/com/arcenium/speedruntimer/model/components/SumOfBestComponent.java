package com.arcenium.speedruntimer.model.components;

import com.arcenium.speedruntimer.model.GameSplits;
import com.arcenium.speedruntimer.model.Split;
import com.arcenium.speedruntimer.utility.Converter;

public class SumOfBestComponent implements Component{
    private final String name;
    private double valueInSeconds;
    private final Converter converter;

    public SumOfBestComponent() {
        this.name = "Sum Of Best Segments";
        this.valueInSeconds = 0;
        this.converter = Converter.getINSTANCE();
    }

    @Override
    public void update(GameSplits splits, int currentSplitIndex) {
        valueInSeconds = 0.00;
        for(Split split : splits.getSplits()){
            valueInSeconds+=split.getBestTime();
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
}
