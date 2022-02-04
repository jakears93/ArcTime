package com.arcenium.speedruntimer.utility;

import org.apache.commons.math3.util.Precision;

public class Converter {
    /******************** Singleton Instance ********************/
    private static Converter INSTANCE;

    /******************** Constructors ********************/
    private Converter(){}

    /******************** Singleton Retriever ********************/
    public static Converter getINSTANCE(){
        if(INSTANCE == null){
            INSTANCE = new Converter();
        }
        return INSTANCE;
    }

    /******************** Utility Functions ********************/
    public String secondsToTimeString(double secondsInput){
        StringBuilder sb = new StringBuilder();
        int hours = (int) (secondsInput/3600);
        int minutes = (int)((secondsInput%3600)/60);
        double seconds = secondsInput%60;
        seconds = Precision.round(seconds, SettingsManager.getINSTANCE().getSettings().getTimerDecimalAccuracy());
        if(hours>0){
            sb.append(hours);
            sb.append(":");
            sb.append(minutes);
            sb.append(":");
            sb.append(seconds);
        }
        else if(minutes>0){
            sb.append(minutes);
            sb.append(":");
            sb.append(seconds);
        }
        else{
            sb.append(seconds);
        }
        return sb.toString();
    }
}//End of Converter Class
