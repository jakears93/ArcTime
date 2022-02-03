package com.arcenium.speedruntimer.service;

import com.arcenium.speedruntimer.model.GameSplits;
import com.arcenium.speedruntimer.model.SpeedTimer;
import com.arcenium.speedruntimer.model.Split;


public class RunService {
    private GameSplits gameSplits;
    private SpeedTimer timer;
    private int runSplitIndex;
    private boolean hasNewBest;
    private boolean isActive;
    private int numOfSplits;

    public RunService() {
    }

    public void startRun(){
        this.runSplitIndex = 0;
        this.gameSplits.incrementAttempts();
        this.timer = new SpeedTimer();
        timer.startTimer();
        hasNewBest = false;
        isActive = true;
    }

    public void togglePauseRun(){
        this.timer.togglePauseTimer();
        isActive = !this.isActive;

    }

    public void stopRun(){
        timer.stopTimer();
        isActive = false;
    }

    public void resetRun(GameSplits gameSplits){
        this.gameSplits = gameSplits;
        timer.stopTimer();
    }

    public void nextSplit(){
        //Get current time
        double splitTime = timer.getTimeElapsedMillis();
        //Start new split
        this.runSplitIndex++;
        this.gameSplits.getSplits().get(runSplitIndex).setStartTime(splitTime);
        //Update last split
        Split lastSplit = this.gameSplits.getSplits().get(runSplitIndex-1);
        lastSplit.setEndTime(splitTime);
        //TODO lastSplit.updateLength();
    }

    public void skipSplit(){
        this.gameSplits.getSplits().get(runSplitIndex).setEndTime(-1);
        //Start new split
        this.runSplitIndex++;
        //Set start time of next split to start time of last one to prevent false best time
        this.gameSplits.getSplits().get(runSplitIndex).setStartTime(this.gameSplits.getSplits().get(runSplitIndex).getStartTime());
    }

    public void previousSplit(){
        if(this.runSplitIndex > 0){
            this.runSplitIndex--;
            this.gameSplits.getSplits().get(runSplitIndex).setEndTime(0);
            this.gameSplits.getSplits().get(runSplitIndex).setLength(0);
        }
    }

    //GETTERS AND SETTERS
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

    public void setGameSplits(GameSplits gameSplits) {
        this.gameSplits = gameSplits;
        numOfSplits = gameSplits.getSplits().size();
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
}
