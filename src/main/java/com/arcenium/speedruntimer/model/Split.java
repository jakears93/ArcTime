package com.arcenium.speedruntimer.model;

public class Split {
    //----------Fields / Attributes----------//
    private long id;
    private String name;
    private double startTime;
    private double endTime;
    private double length;
    private double pbTime;
    private double bestTime;

    //----------Constructors----------//
    public Split(String name, double pbTime, double bestTime) {
        this.name = name;
        this.startTime = 0;
        this.endTime = 0;
        this.length = 0;
        this.pbTime = pbTime;
        this.bestTime = bestTime;
    }

    //----------Default Methods----------//
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
        this.length = this.endTime - this.startTime;
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
}//End of Split Class
