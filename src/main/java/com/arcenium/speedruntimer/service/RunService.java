package com.arcenium.speedruntimer.service;

import com.arcenium.speedruntimer.model.ComparisonType;
import com.arcenium.speedruntimer.model.GameInfo;
import com.arcenium.speedruntimer.model.SpeedTimer;
import com.arcenium.speedruntimer.model.Split;
import com.arcenium.speedruntimer.model.components.Component;
import com.arcenium.speedruntimer.utility.SettingsManager;
import java.util.ArrayList;
import java.util.List;


public class RunService {
    /******************** Fields ********************/
    private GameInfo referenceGameInfo;
    private GameInfo gameInfo;
    private SpeedTimer timer;
    private int runSplitIndex;
    private boolean hasNewBest;
    private boolean isActive;
    private int numOfSplits;
    private double sumOfSplitLengths;
    private double sumOfCurrentComparisonSplits;
    List<Component> components;

    /******************** Constructor and Initializers ********************/
    public RunService() {
    }

    public void initRun(){
        loadComponents();
        loadGameInfo();
    }

    private void loadComponents() {
        components = new ArrayList<>();
        //Load components used from settings
        //Add components to list.
    }

    private void loadGameInfo(){
        //TODO temp solution of hardcoding game to load
        GameInfo gameInfo = SettingsManager.getINSTANCE().loadGameInfo("Super Mario 64", "16 Star");
        this.setReferenceGameInfo(gameInfo);
    }

    public void setReferenceGameInfo(GameInfo referenceGameInfo) {
        //Clone splits, so we can modify them but keep the originals if user doesn't want to save them
        this.referenceGameInfo = referenceGameInfo;
        numOfSplits = referenceGameInfo.getSplits().size();
        this.gameInfo = cloneGameInfo(referenceGameInfo);

    }

    private GameInfo cloneGameInfo(GameInfo gameInfo){
        GameInfo clonedGameInfo = new GameInfo();
        clonedGameInfo.setGameTitle(gameInfo.getGameTitle());
        clonedGameInfo.setCategory(gameInfo.getCategory());
        clonedGameInfo.setAttempts(gameInfo.getAttempts());
        clonedGameInfo.setPb(gameInfo.getPb());
        clonedGameInfo.setSumOfBest(gameInfo.getSumOfBest());

        List<Split> clonedSplits = new ArrayList<>();
        for (Split split : gameInfo.getSplits()) {
            clonedSplits.add(split.clone());
        }
        clonedGameInfo.setSplits(clonedSplits);
        return clonedGameInfo;
    }

    /******************** Start of Run Service Functions ********************/
    public void startRun(){
        hasNewBest = false;
        isActive = true;
        this.runSplitIndex = 0;
        this.sumOfSplitLengths = 0;
        this.gameInfo.incrementAttempts();
        this.timer = new SpeedTimer();
        if(SettingsManager.getINSTANCE().getSettings().getComparisonType() == ComparisonType.PB){
            this.sumOfCurrentComparisonSplits = referenceGameInfo.getSplits().get(0).getPbTime();
        }
        else if(SettingsManager.getINSTANCE().getSettings().getComparisonType() == ComparisonType.BEST){
            this.sumOfCurrentComparisonSplits = referenceGameInfo.getSplits().get(0).getBestTime();
        }
        timer.startTimer();
    }

    /******************** Middle of Run Service Functions ********************/
    public void nextSplit(double splitTime){
        //Increment split index
        this.runSplitIndex++;
        //Set start time
        this.gameInfo.getSplits().get(runSplitIndex).setStartTime(splitTime);
        //Update last split
        Split lastSplit = this.gameInfo.getSplits().get(runSplitIndex-1);
        lastSplit.setEndTime(splitTime);
        //Update best time in modified split list
        lastSplit.updateLength();
        System.out.println(lastSplit.getName() +": " + lastSplit.getLength());
        if(lastSplit.getLength() > 0 && lastSplit.getLength() < lastSplit.getBestTime()){
            lastSplit.setBestTime(lastSplit.getLength());
        }
        sumOfSplitLengths += lastSplit.getLength();

        if(SettingsManager.getINSTANCE().getSettings().getComparisonType() == ComparisonType.PB){
            this.sumOfCurrentComparisonSplits += referenceGameInfo.getSplits().get(runSplitIndex).getPbTime();
        }
        else if(SettingsManager.getINSTANCE().getSettings().getComparisonType() == ComparisonType.BEST){
            this.sumOfCurrentComparisonSplits += referenceGameInfo.getSplits().get(runSplitIndex).getBestTime();
        }
    }

    public void togglePauseRun(){
        this.timer.togglePauseTimer();
        isActive = !this.isActive;

    }

    public void skipSplit(){
        this.gameInfo.getSplits().get(runSplitIndex).setEndTime(0);
        this.gameInfo.getSplits().get(runSplitIndex).setLength(0);
        //Start new split
        this.runSplitIndex++;
        //Set start time of next split to start time of last one to prevent false best time
        this.gameInfo.getSplits().get(runSplitIndex).setStartTime(gameInfo.getSplits().get(runSplitIndex).getStartTime());
    }

    public void previousSplit(){
        if(this.runSplitIndex > 0){
            this.runSplitIndex--;
            this.gameInfo.getSplits().get(runSplitIndex).setEndTime(0);
            this.gameInfo.getSplits().get(runSplitIndex).setLength(0);
        }
    }

    /******************** End of Run Service Functions ********************/
    public void stopRun(){
        timer.stopTimer();
        isActive = false;
    }

    public void finalizeRun(){
        //Update final split
        double splitTime = timer.poll();
        Split finalSplit = this.gameInfo.getSplits().get(runSplitIndex);
        finalSplit.setEndTime(splitTime);
        //Update best time in modified split list
        finalSplit.updateLength();
        System.out.println(finalSplit.getName() + finalSplit.getLength());

        if(finalSplit.getLength() > 0 && finalSplit.getLength() < finalSplit.getBestTime()){
            gameInfo.getSplits().get(runSplitIndex).setBestTime(finalSplit.getLength());
        }

        checkForPb();
        checkForBests();
        this.setReferenceGameInfo(gameInfo);
        SettingsManager.getINSTANCE().saveGameInfo(this.referenceGameInfo);
    }

    public boolean checkForPb(){
        double sumOfTime = 0;
        for(int i = 0; i< gameInfo.getSplits().size(); i++){
            sumOfTime += gameInfo.getSplits().get(i).getLength();
        }
        System.out.println("Final Time: "+sumOfTime);
        if(sumOfTime < gameInfo.getPb() || gameInfo.getPb() == 0){
            gameInfo.setPb(sumOfTime);
            updatePbTimes(gameInfo.getSplits());
            System.out.println("new pb: "+sumOfTime);

            return true;
        }
        return false;
    }

    private void updatePbTimes(List<Split> splits){
        for(Split split : splits){
            split.setPbTime(split.getLength());
        }
    }

    public boolean checkForBests(){
        boolean hasNewBest = false;
        double sumOfBest = 0;
        for(int i = 0; i< gameInfo.getSplits().size(); i++){
            if(gameInfo.getSplits().get(i).getLength() < referenceGameInfo.getSplits().get(i).getBestTime()){
                hasNewBest = true;
            }
            sumOfBest += gameInfo.getSplits().get(i).getBestTime();
        }
        if(hasNewBest){
            System.out.println("new best: "+sumOfBest);
            gameInfo.setSumOfBest(sumOfBest);
            return true;
        }
        return false;
    }

    public void saveRun(){
        timer.stopTimer();
    }

    /******************** Getters and Setters ********************/
    public int getRunSplitIndex() {
        return runSplitIndex;
    }

    public void setRunSplitIndex(int runSplitIndex) {
        this.runSplitIndex = runSplitIndex;
    }

    public int getNumOfSplits() {
        return numOfSplits;
    }

    public void setNumOfSplits(int numOfSplits) {
        this.numOfSplits = numOfSplits;
    }

    public SpeedTimer getTimer() {
        return timer;
    }

    public void setTimer(SpeedTimer timer) {
        this.timer = timer;
    }

    public GameInfo getReferenceGameInfo() {
        return referenceGameInfo;
    }

    public boolean isHasNewBest() {
        return hasNewBest;
    }

    public void setHasNewBest(boolean hasNewBest) {
        this.hasNewBest = hasNewBest;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public GameInfo getGameInfo() {
        return gameInfo;
    }

    public void setGameInfo(GameInfo gameInfo) {
        this.gameInfo = gameInfo;
    }

    public List<Component> getComponents() {
        return components;
    }

    public double getSumOfSplitLengths() {
        return sumOfSplitLengths;
    }

    public void setSumOfSplitLengths(double sumOfSplitLengths) {
        this.sumOfSplitLengths = sumOfSplitLengths;
    }

    public double getSumOfCurrentComparisonSplits() {
        return sumOfCurrentComparisonSplits;
    }

    public void setSumOfCurrentComparisonSplits(double sumOfCurrentComparisonSplits) {
        this.sumOfCurrentComparisonSplits = sumOfCurrentComparisonSplits;
    }
}//End of RunService Class
