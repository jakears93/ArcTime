package com.arcenium.speedruntimer.config;

import javafx.scene.paint.Color;

public class Colours {
    /******************** Ui Colour Fields ********************/
    private Color bestTimeColor;
    private Color aheadOfTimeColor;
    private Color behindTimeColor;
    private Color aheadButLostTimeColor;
    private Color behindButGainedTimeColor;

    /******************** Getters and Setters ********************/
    public Color getBestTimeColor() {
        return bestTimeColor;
    }

    public void setBestTimeColor(Color bestTimeColor) {
        this.bestTimeColor = bestTimeColor;
    }

    public Color getAheadOfTimeColor() {
        return aheadOfTimeColor;
    }

    public void setAheadOfTimeColor(Color aheadOfTimeColor) {
        this.aheadOfTimeColor = aheadOfTimeColor;
    }

    public Color getBehindTimeColor() {
        return behindTimeColor;
    }

    public void setBehindTimeColor(Color behindTimeColor) {
        this.behindTimeColor = behindTimeColor;
    }

    public Color getAheadButLostTimeColor() {
        return aheadButLostTimeColor;
    }

    public void setAheadButLostTimeColor(Color aheadButLostTimeColor) {
        this.aheadButLostTimeColor = aheadButLostTimeColor;
    }

    public Color getBehindButGainedTimeColor() {
        return behindButGainedTimeColor;
    }

    public void setBehindButGainedTimeColor(Color behindButGainedTimeColor) {
        this.behindButGainedTimeColor = behindButGainedTimeColor;
    }
}//End of Colours Class
