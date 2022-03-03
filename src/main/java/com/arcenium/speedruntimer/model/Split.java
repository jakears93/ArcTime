package com.arcenium.speedruntimer.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Split {
    /******************** Split Information Fields ********************/
    private String name;
    private double startTime;
    private double endTime;
    private double length;
    private double pbTime;
    private double bestTime;

    /******************** Constructors ********************/
    @JsonCreator
    public Split(
            @JsonProperty("name") String name,
            @JsonProperty("pbTime") double pbTime,
            @JsonProperty("bestTime") double bestTime,
            @JsonProperty("startTime") double startTime,
            @JsonProperty("endTime") double endTime,
            @JsonProperty("length") double length) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.length = length;
        this.pbTime = pbTime;
        this.bestTime = bestTime;
    }

    public Split() {
    }

    /******************** Update Functions ********************/
    public void updateLength() {
        this.length = (this.endTime - this.startTime)/1000;
    }

    public void updateLength(double currentTime) {
        this.length = (currentTime - this.startTime)/1000;
    }

    /******************** Default Functions ********************/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getStartTime() {
        return startTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public double getEndTime() {
        return endTime;
    }

    public void setEndTime(double endTime) {
        this.endTime = endTime;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getPbTime() {
        return pbTime;
    }

    public void setPbTime(double pbTime) {
        this.pbTime = pbTime;
    }

    public double getBestTime() {
        return bestTime;
    }

    public void setBestTime(double bestTime) {
        this.bestTime = bestTime;
    }

    @Override
    public String toString() {
        return "Split{" +
                "name='" + name + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", length=" + length +
                '}';
    }

    public Split clone(){
        Split clone = new Split();
        clone.setPbTime(this.pbTime);
        clone.setBestTime(this.bestTime);
        clone.setName(this.name);
        clone.setStartTime(this.startTime);
        clone.setEndTime(this.endTime);
        clone.setLength(this.length);
        return clone;
    }
}//End of Split Class
