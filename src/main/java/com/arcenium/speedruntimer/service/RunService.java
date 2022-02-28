package com.arcenium.speedruntimer.service;

import com.arcenium.speedruntimer.model.GameInfo;
import com.arcenium.speedruntimer.model.SpeedTimer;
import com.arcenium.speedruntimer.model.Split;
import com.arcenium.speedruntimer.model.components.Component;
import com.arcenium.speedruntimer.utility.SettingsManager;
import java.util.ArrayList;
import java.util.List;


public class RunService {
    /******************** Fields ********************/
    private GameInfo gameInfo;
    private GameInfo modifiedGameInfo;
    private SpeedTimer timer;
    private int runSplitIndex;
    private boolean hasNewBest;
    private boolean isActive;
    private int numOfSplits;
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
        this.setGameInfo(gameInfo);
    }

    public void setGameInfo(GameInfo gameInfo) {
        //Clone splits, so we can modify them but keep the originals if user doesn't want to save them
        this.gameInfo = gameInfo;
        numOfSplits = gameInfo.getSplits().size();
        this.modifiedGameInfo = cloneGameInfo(gameInfo);

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
        this.modifiedGameInfo.incrementAttempts();
        this.timer = new SpeedTimer();
        timer.startTimer();
    }

    /******************** Middle of Run Service Functions ********************/
    public void nextSplit(double splitTime){
        //Increment split index
        this.runSplitIndex++;
        //Set start time
        this.modifiedGameInfo.getSplits().get(runSplitIndex).setStartTime(splitTime);
        //Update last split
        Split lastSplit = this.modifiedGameInfo.getSplits().get(runSplitIndex-1);
        lastSplit.setEndTime(splitTime);
        //Update best time in modified split list
        lastSplit.updateLength();
        System.out.println(lastSplit.getName() +": " + lastSplit.getLength());
        if(lastSplit.getLength() > 0 && lastSplit.getLength() < lastSplit.getBestTime()){
            lastSplit.setBestTime(lastSplit.getLength());
        }
    }

    public void togglePauseRun(){
        this.timer.togglePauseTimer();
        isActive = !this.isActive;

    }

    public void skipSplit(){
        this.modifiedGameInfo.getSplits().get(runSplitIndex).setEndTime(0);
        this.modifiedGameInfo.getSplits().get(runSplitIndex).setLength(0);
        //Start new split
        this.runSplitIndex++;
        //Set start time of next split to start time of last one to prevent false best time
        this.modifiedGameInfo.getSplits().get(runSplitIndex).setStartTime(modifiedGameInfo.getSplits().get(runSplitIndex).getStartTime());
    }

    public void previousSplit(){
        if(this.runSplitIndex > 0){
            this.runSplitIndex--;
            this.modifiedGameInfo.getSplits().get(runSplitIndex).setEndTime(0);
            this.modifiedGameInfo.getSplits().get(runSplitIndex).setLength(0);
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
        Split finalSplit = this.modifiedGameInfo.getSplits().get(runSplitIndex);
        finalSplit.setEndTime(splitTime);
        //Update best time in modified split list
        finalSplit.updateLength();
        System.out.println(finalSplit.getName() + finalSplit.getLength());

        if(finalSplit.getLength() > 0 && finalSplit.getLength() < finalSplit.getBestTime()){
            modifiedGameInfo.getSplits().get(runSplitIndex).setBestTime(finalSplit.getLength());
        }

        checkForPb();
        checkForBests();
        this.setGameInfo(modifiedGameInfo);
        SettingsManager.getINSTANCE().saveGameInfo(this.gameInfo);
    }

    public boolean checkForPb(){
        double sumOfTime = 0;
        for(int i = 0; i< modifiedGameInfo.getSplits().size(); i++){
            sumOfTime += modifiedGameInfo.getSplits().get(i).getLength();
        }
        System.out.println("Final Time: "+sumOfTime);
        if(sumOfTime < modifiedGameInfo.getPb() || modifiedGameInfo.getPb() == 0){
            modifiedGameInfo.setPb(sumOfTime);
            updatePbTimes(modifiedGameInfo.getSplits());
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
        for(int i = 0; i< modifiedGameInfo.getSplits().size(); i++){
            if(modifiedGameInfo.getSplits().get(i).getLength() < gameInfo.getSplits().get(i).getBestTime()){
                hasNewBest = true;
            }
            sumOfBest += modifiedGameInfo.getSplits().get(i).getBestTime();
        }
        if(hasNewBest){
            System.out.println("new best: "+sumOfBest);
            modifiedGameInfo.setSumOfBest(sumOfBest);
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

    public GameInfo getGameInfo() {
        return gameInfo;
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

    public GameInfo getModifiedGameInfo() {
        return modifiedGameInfo;
    }

    public void setModifiedGameInfo(GameInfo modifiedGameInfo) {
        this.modifiedGameInfo = modifiedGameInfo;
    }

    public List<Component> getComponents() {
        return components;
    }
}//End of RunService Class
