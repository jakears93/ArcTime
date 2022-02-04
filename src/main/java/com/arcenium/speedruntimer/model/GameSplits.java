package com.arcenium.speedruntimer.model;

import java.util.ArrayList;
import java.util.List;

public class GameSplits {
    /******************** Fields ********************/
    private String gameTitle;
    private String category;
    private int attempts;
    private double pb;
    private double sumOfBest;
    private List<Split> splits;

    /******************** Constructors ********************/
    public GameSplits() {
    }

    public GameSplits(String gameTitle, String category, int attempts, double pb, double sumOfBest) {
        this.gameTitle = gameTitle;
        this.category = category;
        this.attempts = attempts;
        this.pb = pb;
        this.sumOfBest = sumOfBest;
        this.splits = new ArrayList<>();
    }

    /******************** Logic Functions ********************/

    public void incrementAttempts(){
        this.attempts++;
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
}//End of GameSplits Class
