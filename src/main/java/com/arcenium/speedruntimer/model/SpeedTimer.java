package com.arcenium.speedruntimer.model;

import java.util.Calendar;

public class SpeedTimer implements Runnable {
    //----------Fields / Attributes----------//
    private Thread thread = null;
    private String info;
    private boolean isActive = false;
    private double startMillis = 0;
    private double timeElapsedMillis = 0;

    //----------Constructors----------//
    public SpeedTimer(){
        thread = new Thread(this);
        thread.start();
    }

    //----------Class Specific Methods----------//
    @Override
    public void run() {
        try{
            while(true){
                while(isActive){
                    timeElapsedMillis= Calendar.getInstance().getTimeInMillis() - startMillis;
                    info = String.valueOf(timeElapsedMillis/1000);
                    thread.sleep(10);
                }
                thread.sleep(10);
            }
        } catch (Exception e){

        }
    }

    public void startTimer(){
        startMillis = Calendar.getInstance().getTimeInMillis();
        isActive = true;
    }

    public void stopTimer() {
        isActive = false;
    }

    //----------Default Methods----------//
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public double getStartMillis() {
        return startMillis;
    }

    public void setStartMillis(double startMillis) {
        this.startMillis = startMillis;
    }

    public double getTimeElapsedMillis() {
        return timeElapsedMillis;
    }

    public void setTimeElapsedMillis(double timeElapsedMillis) {
        this.timeElapsedMillis = timeElapsedMillis;
    }
}//End of SpeedTimer Class
