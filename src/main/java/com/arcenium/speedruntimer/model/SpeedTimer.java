package com.arcenium.speedruntimer.model;

public class SpeedTimer implements Runnable {
    /******************** Fields ********************/
    private final Thread thread;
    private boolean isActive = false;
    private boolean exit = false;
    private boolean isPaused = false;
    private double startMillis = 0;
    private double timeElapsedMillis = 0;

    /******************** Constructor ********************/
    public SpeedTimer(){
        thread = new Thread(this);
        thread.start();
    }

    /******************** Logic Functions ********************/
    @Override
    public void run() {
        try{
            while(!exit){
                while(isActive){
                    timeElapsedMillis= System.currentTimeMillis() - startMillis;
                    thread.sleep(10);
                }
                //Count time on pause
                double startPauseTime = System.currentTimeMillis();
                double pauseTimeElapsed = 0;
                while(isPaused){
                    pauseTimeElapsed = System.currentTimeMillis() - startPauseTime;
                    thread.sleep(10);
                }
                //Add pause time to start time to counter time that was paused
                startMillis += pauseTimeElapsed;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void startTimer(){
        startMillis = System.currentTimeMillis();
        isActive = true;
        isPaused = false;
        exit = false;
    }

    public void togglePauseTimer(){
        this.isActive = !this.isActive;
        this.isPaused = !this.isPaused;
    }

    public void stopTimer() {
        isActive = false;
        isPaused = false;
        exit = true;
    }

    public double poll() {
        return timeElapsedMillis;
    }

    /******************** Getters and Setters ********************/

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

}//End of SpeedTimer Class
