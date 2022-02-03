package com.arcenium.speedruntimer.model;

import com.arcenium.speedruntimer.utility.Converter;

import java.util.Calendar;

public class SpeedTimer implements Runnable {
    //----------Fields / Attributes----------//
    private Thread thread = null;
    private String info;
    private boolean isActive = false;
    private boolean exit = false;
    private double startMillis = 0;
    private double timeElapsedMillis = 0;
    private final Converter converter;

    //----------Constructors----------//
    public SpeedTimer(){
        this.converter = Converter.getINSTANCE();
        thread = new Thread(this);
        thread.start();
    }

    //----------Class Specific Methods----------//
    @Override
    public void run() {
        try{
            while(!exit){
                while(isActive){
                    timeElapsedMillis= Calendar.getInstance().getTimeInMillis() - startMillis;
                    info = converter.secondsToTimeString(timeElapsedMillis / 1000);
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

    public void togglePauseTimer(){
        this.isActive = !this.isActive;
    }

    public void stopTimer() {
        isActive = false;
        exit = false;
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
