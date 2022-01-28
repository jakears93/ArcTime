package com.arcenium.speedruntimer.config;

import com.arcenium.speedruntimer.model.ComparisonType;

public class Settings {
    //----------Fields / Attributes----------//
    private boolean isModifyingSettings;
    private FileManager fileManager;
    private KeyMap keyMap;
    private Colours colours;
    private ComparisonType comparisonType;
    private int timerDecimalAccuracy;
    private int refreshIntervalInMillis;
    private boolean showKeyEventLogging;

    //----------Constructors----------//
    public Settings() {
        this.fileManager = new FileManager();
        this.keyMap = new KeyMap();
        this.colours = new Colours();
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

    public boolean isModifyingSettings() {
        return isModifyingSettings;
    }

    public void setModifyingSettings(boolean modifyingSettings) {
        isModifyingSettings = modifyingSettings;
    }

    public boolean isShowKeyEventLogging() {
        return showKeyEventLogging;
    }

    public void setShowKeyEventLogging(boolean showKeyEventLogging) {
        this.showKeyEventLogging = showKeyEventLogging;
    }

    public int getRefreshIntervalInMillis() {
        return refreshIntervalInMillis;
    }

    public void setRefreshIntervalInMillis(int refreshIntervalInMillis) {
        this.refreshIntervalInMillis = refreshIntervalInMillis;
    }

    public Colours getColours() {
        return colours;
    }

    public void setColours(Colours colours) {
        this.colours = colours;
    }
}//End of Settings Class
