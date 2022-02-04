package com.arcenium.speedruntimer.model;

public enum ComparisonType {
    /******************** Enum Values ********************/
    PB("PB"),
    BEST("BEST");

    /******************** Enum Fields ********************/
    private final String text;

    /******************** Enum Constructors ********************/
    ComparisonType(String text){
        this.text = text;
    }

    public static ComparisonType fromString(String text){
        for(ComparisonType ct : ComparisonType.values()){
            if(ct.text.equalsIgnoreCase(text)){
                return ct;
            }
        }
        return null;
    }

    /******************** Getters ********************/
    public String getText(){
        return this.text;
    }
}//End of ComparisonType Enum
