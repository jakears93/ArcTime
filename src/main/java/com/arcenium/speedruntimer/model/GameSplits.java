package com.arcenium.speedruntimer.model;

import java.util.ArrayList;
import java.util.List;

public class GameSplits {
    private String gameTitle;
    private String gameSubtitle;
    private String category;
    private int attempts;
    private double pb;
    private double sumOfBest;
    private List<Split> splits;

    public GameSplits(String gameTitle, String gameSubtitle, String category, int attempts, double pb, double sumOfBest) {
        this.gameTitle = gameTitle;
        this.gameSubtitle = gameSubtitle;
        this.category = category;
        this.attempts = attempts;
        this.pb = pb;
        this.sumOfBest = sumOfBest;
        this.splits = new ArrayList<>();
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public String getGameSubtitle() {
        return gameSubtitle;
    }

    public void setGameSubtitle(String gameSubtitle) {
        this.gameSubtitle = gameSubtitle;
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
}
