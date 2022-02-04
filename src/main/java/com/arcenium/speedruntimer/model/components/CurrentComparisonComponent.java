package com.arcenium.speedruntimer.model.components;

import com.arcenium.speedruntimer.model.ComparisonType;
import com.arcenium.speedruntimer.model.GameSplits;
import com.arcenium.speedruntimer.utility.SettingsManager;

public class CurrentComparisonComponent implements Component{
    /******************** Component Fields ********************/
    private final String name;
    private ComparisonType comparisonType;

    /******************** Constructor ********************/
    public CurrentComparisonComponent() {
        this.comparisonType = SettingsManager.getINSTANCE().getSettings().getComparisonType();
        this.name = "Current Comparison Type:";
    }

    /******************** Mandatory Functions For Interface ********************/
    @Override
    public void update(GameSplits splits, int currentSplitIndex) {
        this.comparisonType = SettingsManager.getINSTANCE().getSettings().getComparisonType();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getValue() {
        return comparisonType.name();
    }
}//End of CurrentComparisonComponent Class
