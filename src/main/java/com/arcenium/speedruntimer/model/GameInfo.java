package com.arcenium.speedruntimer.model;

import com.arcenium.speedruntimer.utility.SettingsManager;

import java.util.ArrayList;
import java.util.List;

public class GameInfo {
    /******************** Fields ********************/
    private String gameTitle;
    private String category;
    private int attempts;
    private int completedAttempts;
    private double pb;
    private double sumOfBest;
    private List<Split> splits;
    private ComparisonType comparisonType;

    /******************** Constructors ********************/
    public GameInfo() {
    }

    public GameInfo(String gameTitle, String category, int attempts, int completedAttempts, double pb, double sumOfBest) {
        this.gameTitle = gameTitle;
        this.category = category;
        this.attempts = attempts;
        this.completedAttempts = completedAttempts;
        this.pb = pb;
        this.sumOfBest = sumOfBest;
        this.splits = new ArrayList<>();
        this.comparisonType = SettingsManager.getINSTANCE().getSettings().getComparisonType();
    }

    /******************** Logic Functions ********************/

    public void incrementAttempts(){
        this.attempts++;
    }

    public void incCompletedAttempts(){
        this.completedAttempts++;
    }

    /******************** Getters and Setters ********************/

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public int getCompletedAttempts() {
        return completedAttempts;
    }

    public void setCompletedAttempts(int completedAttempts) {
        this.completedAttempts = completedAttempts;
    }

    public double getPb() {
        return pb;
    }

    public void setPb(double pb) {
        this.pb = pb;
    }

    public double getSumOfBest() {
        return sumOfBest;
    }

    public void setSumOfBest(double sumOfBest) {
        this.sumOfBest = sumOfBest;
    }

    public List<Split> getSplits() {
        return splits;
    }

    public void setSplits(List<Split> splits) {
        this.splits = splits;
    }

    public ComparisonType getComparisonType() {
        return comparisonType;
    }
}//End of GameSplits Class
