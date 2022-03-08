package com.arcenium.speedruntimer.config;

import com.arcenium.speedruntimer.model.ComparisonType;

public class Settings {
    /******************** Setting Fields ********************/
    private boolean isModifyingSettings;
    private final FileManager fileManager;
    private final KeyMap keyMap;
    private final Colours colours;
    private ComparisonType comparisonType;
    private int windowHeight;
    private int windowWidth;
    private int editWindowHeight;
    private int editWindowWidth;
    private int timerDecimalAccuracy;
    private int refreshIntervalInMillis;
    private boolean showKeyEventLogging;
    private int generalFontSize;
    private int titleFontSize;
    private int subtitleFontSize;
    private int mainTimerFontSize;
    private int splitTimerFontSize;
    private String fontStyle;

    /******************** Constructor ********************/
    public Settings() {
        this.fileManager = new FileManager();
        this.keyMap = new KeyMap();
        this.colours = new Colours();
    }

    /******************** Getters and Setters ********************/
    public boolean isModifyingSettings() {
        return isModifyingSettings;
    }

    public void setModifyingSettings(boolean modifyingSettings) {
        isModifyingSettings = modifyingSettings;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public KeyMap getKeyMap() {
        return keyMap;
    }

    public Colours getColours() {
        return colours;
    }

    public ComparisonType getComparisonType() {
        return comparisonType;
    }

    public void setComparisonType(ComparisonType comparisonType) {
        this.comparisonType = comparisonType;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    public int getEditWindowHeight() {
        return editWindowHeight;
    }

    public void setEditWindowHeight(int editWindowHeight) {
        this.editWindowHeight = editWindowHeight;
    }

    public int getEditWindowWidth() {
        return editWindowWidth;
    }

    public void setEditWindowWidth(int editWindowWidth) {
        this.editWindowWidth = editWindowWidth;
    }

    public int getTimerDecimalAccuracy() {
        return timerDecimalAccuracy;
    }

    public void setTimerDecimalAccuracy(int timerDecimalAccuracy) {
        this.timerDecimalAccuracy = timerDecimalAccuracy;
    }

    public int getRefreshIntervalInMillis() {
        return refreshIntervalInMillis;
    }

    public void setRefreshIntervalInMillis(int refreshIntervalInMillis) {
        this.refreshIntervalInMillis = refreshIntervalInMillis;
    }

    public boolean isShowKeyEventLogging() {
        return showKeyEventLogging;
    }

    public void setShowKeyEventLogging(boolean showKeyEventLogging) {
        this.showKeyEventLogging = showKeyEventLogging;
    }

    public int getGeneralFontSize() {
        return generalFontSize;
    }

    public void setGeneralFontSize(int generalFontSize) {
        this.generalFontSize = generalFontSize;
    }

    public int getTitleFontSize() {
        return titleFontSize;
    }

    public void setTitleFontSize(int titleFontSize) {
        this.titleFontSize = titleFontSize;
    }

    public int getSubtitleFontSize() {
        return subtitleFontSize;
    }

    public void setSubtitleFontSize(int subtitleFontSize) {
        this.subtitleFontSize = subtitleFontSize;
    }

    public int getMainTimerFontSize() {
        return mainTimerFontSize;
    }

    public void setMainTimerFontSize(int mainTimerFontSize) {
        this.mainTimerFontSize = mainTimerFontSize;
    }

    public int getSplitTimerFontSize() {
        return splitTimerFontSize;
    }

    public void setSplitTimerFontSize(int splitTimerFontSize) {
        this.splitTimerFontSize = splitTimerFontSize;
    }

    public String getFontStyle() {
        return fontStyle;
    }

    public void setFontStyle(String fontStyle) {
        this.fontStyle = fontStyle;
    }
}//End of Settings Class
