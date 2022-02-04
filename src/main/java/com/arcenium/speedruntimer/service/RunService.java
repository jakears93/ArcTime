package com.arcenium.speedruntimer.service;

import com.arcenium.speedruntimer.model.GameSplits;
import com.arcenium.speedruntimer.model.SpeedTimer;
import com.arcenium.speedruntimer.model.Split;
import com.arcenium.speedruntimer.model.components.Component;
import com.arcenium.speedruntimer.utility.SettingsManager;
import java.util.ArrayList;
import java.util.List;


public class RunService {
    /******************** Fields ********************/
    private GameSplits gameSplits;
    private GameSplits modifiedGameSplits;
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
        loadGameSplits();
    }

    private void loadComponents() {
        components = new ArrayList<>();
        //Load components used from settings
        //Add components to list.
    }

    private void loadGameSplits(){
        //TODO temp solution of hardcoding game to load
        GameSplits gameSplits = SettingsManager.getINSTANCE().loadGameSplits("Super Mario 64", "16 Star");
        this.setGameSplits(gameSplits);
    }

    public void setGameSplits(GameSplits gameSplits) {
        //Clone splits, so we can modify them but keep the originals if user doesn't want to save them
        this.gameSplits = gameSplits;
        numOfSplits = gameSplits.getSplits().size();
        this.modifiedGameSplits = cloneGamesplits(gameSplits);

    }

    private GameSplits cloneGamesplits(GameSplits gameSplits){
        GameSplits clonedGameSplits = new GameSplits();
        clonedGameSplits.setGameTitle(gameSplits.getGameTitle());
        clonedGameSplits.setCategory(gameSplits.getCategory());
        clonedGameSplits.setAttempts(gameSplits.getAttempts());
        clonedGameSplits.setPb(gameSplits.getPb());
        clonedGameSplits.setSumOfBest(gameSplits.getSumOfBest());

        List<Split> clonedSplits = new ArrayList<>();
        for (Split split : gameSplits.getSplits()) {
            clonedSplits.add(split.clone());
        }
        clonedGameSplits.setSplits(clonedSplits);
        return clonedGameSplits;
    }

    /******************** Start of Run Service Functions ********************/
    public void startRun(){
        hasNewBest = false;
        isActive = true;
        this.runSplitIndex = 0;
        this.modifiedGameSplits.incrementAttempts();
        this.timer = new SpeedTimer();
        timer.startTimer();
    }

    /******************** Middle of Run Service Functions ********************/
    public void nextSplit(double splitTime){
        //Increment split index
        this.runSplitIndex++;
        //Set start time
        this.modifiedGameSplits.getSplits().get(runSplitIndex).setStartTime(splitTime);
        //Update last split
        Split lastSplit = this.modifiedGameSplits.getSplits().get(runSplitIndex-1);
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
        this.modifiedGameSplits.getSplits().get(runSplitIndex).setEndTime(0);
        this.modifiedGameSplits.getSplits().get(runSplitIndex).setLength(0);
        //Start new split
        this.runSplitIndex++;
        //Set start time of next split to start time of last one to prevent false best time
        this.modifiedGameSplits.getSplits().get(runSplitIndex).setStartTime(modifiedGameSplits.getSplits().get(runSplitIndex).getStartTime());
    }

    public void previousSplit(){
        if(this.runSplitIndex > 0){
            this.runSplitIndex--;
            this.modifiedGameSplits.getSplits().get(runSplitIndex).setEndTime(0);
            this.modifiedGameSplits.getSplits().get(runSplitIndex).setLength(0);
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
        Split finalSplit = this.modifiedGameSplits.getSplits().get(runSplitIndex);
        finalSplit.setEndTime(splitTime);
        //Update best time in modified split list
        finalSplit.updateLength();
        System.out.println(finalSplit.getName() + finalSplit.getLength());

        if(finalSplit.getLength() > 0 && finalSplit.getLength() < finalSplit.getBestTime()){
            modifiedGameSplits.getSplits().get(runSplitIndex).setBestTime(finalSplit.getLength());
        }

        checkForPb();
        checkForBests();
        this.setGameSplits(modifiedGameSplits);
        SettingsManager.getINSTANCE().saveGameSplits(this.gameSplits);
    }

    public boolean checkForPb(){
        double sumOfTime = 0;
        for(int i = 0; i< modifiedGameSplits.getSplits().size(); i++){
            sumOfTime += modifiedGameSplits.getSplits().get(i).getLength();
        }
        System.out.println("Final Time: "+sumOfTime);
        if(sumOfTime < modifiedGameSplits.getPb() || modifiedGameSplits.getPb() == 0){
            modifiedGameSplits.setPb(sumOfTime);
            updatePbTimes(modifiedGameSplits.getSplits());
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
        for(int i=0; i<modifiedGameSplits.getSplits().size(); i++){
            if(modifiedGameSplits.getSplits().get(i).getLength() < gameSplits.getSplits().get(i).getBestTime()){
                hasNewBest = true;
            }
            sumOfBest += modifiedGameSplits.getSplits().get(i).getBestTime();
        }
        if(hasNewBest){
            System.out.println("new best: "+sumOfBest);
            modifiedGameSplits.setSumOfBest(sumOfBest);
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

    public GameSplits getGameSplits() {
        return gameSplits;
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

    public GameSplits getModifiedGameSplits() {
        return modifiedGameSplits;
    }

    public void setModifiedGameSplits(GameSplits modifiedGameSplits) {
        this.modifiedGameSplits = modifiedGameSplits;
    }

    public List<Component> getComponents() {
        return components;
    }
}//End of RunService Class
