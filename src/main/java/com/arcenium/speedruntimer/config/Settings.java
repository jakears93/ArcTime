package com.arcenium.speedruntimer.config;

import com.arcenium.speedruntimer.model.ComparisonType;

public class Settings {
    //----------Fields / Attributes----------//
    private FileManager fileManager;
    private KeyMap keyMap;
    private ComparisonType comparisonType;

    private int timerDecimalAccuracy;

    //----------Constructors----------//

    public Settings() {
        this.fileManager = new FileManager();
        this.keyMap = new KeyMap();
    }

    //----------Class Specific Methods----------//

    //----------Default Methods----------//
    public FileManager getFileManager() {
        return fileManager;
    }

    public KeyMap getKeyMap() {
        return keyMap;
    }

    public int getTimerDecimalAccuracy() {
        return timerDecimalAccuracy;
    }

    public void setTimerDecimalAccuracy(int timerDecimalAccuracy) {
        this.timerDecimalAccuracy = timerDecimalAccuracy;
    }

    public ComparisonType getComparisonType() {
        return comparisonType;
    }

    public void setComparisonType(ComparisonType comparisonType) {
        this.comparisonType = comparisonType;
    }
}//End of Settings Class
