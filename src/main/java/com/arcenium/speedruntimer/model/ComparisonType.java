package com.arcenium.speedruntimer.model;

public enum ComparisonType {
    PB("PB"),
    BEST("BEST");

    private String text;

    ComparisonType(String text){
        this.text = text;
    }

    public String getText(){
        return this.text;
    }

    public static ComparisonType fromString(String text){
        for(ComparisonType ct : ComparisonType.values()){
            if(ct.text.equalsIgnoreCase(text)){
                return ct;
            }
        }
        return null;
    }
}
