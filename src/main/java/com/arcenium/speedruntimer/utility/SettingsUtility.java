package com.arcenium.speedruntimer.utility;

import com.arcenium.speedruntimer.config.FileManager;
import com.arcenium.speedruntimer.config.Settings;
import com.arcenium.speedruntimer.model.ComparisonType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SettingsUtility {
    private final FileManager fileManager;
    private final Settings settings;

    public SettingsUtility(FileManager fileManager, Settings settings) {
        this.fileManager = fileManager;
        this.settings = settings;
        loadSettings();
        loadColours();
    }

    public void loadSettings(){
        if(!this.fileManager.getSettingsFile().exists()){
            loadDefaultSettings();
            saveSettings();
            return;
        }

        try{
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> settingsMap = mapper.readValue(fileManager.getSettingsFile(), new TypeReference<>() {
            });

            settings.setComparisonType(ComparisonType.fromString((String)settingsMap.get("ComparisonType")));
            settings.setTimerDecimalAccuracy((Integer) settingsMap.get("TimerDecimalAccuracy"));
            settings.setRefreshIntervalInMillis((Integer) settingsMap.get("RefreshIntervalInMillis"));
            settings.setShowKeyEventLogging((Boolean) settingsMap.get("ShowKeyEventLogging"));
            settings.setGeneralFontSize((Integer) settingsMap.get("GeneralFontSize"));
            settings.setTitleFontSize((Integer) settingsMap.get("TitleFontSize"));
            settings.setSubtitleFontSize((Integer) settingsMap.get("SubTitleFontSize"));
            settings.setMainTimerFontSize((Integer) settingsMap.get("MainTimerFontSize"));
            settings.setSplitTimerFontSize((Integer) settingsMap.get("SplitTimerFontSize"));
            settings.setWindowHeight((Integer) settingsMap.get("WindowHeight"));
            settings.setWindowWidth((Integer) settingsMap.get("WindowWidth"));


            System.out.println("Settings Loaded from File "+this.fileManager.getSettingsFile().getPath());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error Loading Settings. Loading default settings instead.");
            loadDefaultSettings();
        }
    }

    public void loadColours(){
        if(!this.fileManager.getColourSettingsFile().exists()){
            loadDefaultColours();
            saveColours();
            return;
        }

        try{
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> coloursMap = mapper.readValue(fileManager.getColourSettingsFile(), new TypeReference<>() {
            });

            settings.getColours().setBestTimeColor(Color.valueOf(coloursMap.get("BestTimeColor")));
            settings.getColours().setAheadOfTimeColor(Color.valueOf(coloursMap.get("AheadOfTimeColor")));
            settings.getColours().setAheadButLostTimeColor(Color.valueOf(coloursMap.get("AheadButLostTimeColor")));
            settings.getColours().setBehindTimeColor(Color.valueOf(coloursMap.get("BehindTimeColor")));
            settings.getColours().setBehindButGainedTimeColor(Color.valueOf(coloursMap.get("BehindButGainedTimeColor")));
            System.out.println("Colours Loaded from File "+this.fileManager.getColourSettingsFile().getPath());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error Loading Colours. Loading default colours instead.");
            loadDefaultColours();
        }
    }

    public void loadDefaultSettings(){
        settings.setComparisonType(ComparisonType.PB);
        settings.setTimerDecimalAccuracy(2);
        settings.setRefreshIntervalInMillis(100);
        settings.setShowKeyEventLogging(false);
        settings.setGeneralFontSize(14);
        settings.setTitleFontSize(18);
        settings.setSubtitleFontSize(16);
        settings.setMainTimerFontSize(35);
        settings.setSplitTimerFontSize(25);
        settings.setWindowHeight(600);
        settings.setWindowWidth(250);
    }

    public void loadDefaultColours(){
        settings.getColours().setBestTimeColor(Color.GOLD);
        settings.getColours().setAheadOfTimeColor(Color.GREEN);
        settings.getColours().setAheadButLostTimeColor(Color.LIGHTGREEN);
        settings.getColours().setBehindTimeColor(Color.RED);
        settings.getColours().setBehindButGainedTimeColor(Color.SALMON);
    }

    public void saveSettings(){
        Map<String, Object> settingsMap = new HashMap<>();
        settingsMap.put("ComparisonType", settings.getComparisonType().getText());
        settingsMap.put("TimerDecimalAccuracy", settings.getTimerDecimalAccuracy());
        settingsMap.put("RefreshIntervalInMillis", settings.getRefreshIntervalInMillis());
        settingsMap.put("ShowKeyEventLogging", settings.isShowKeyEventLogging());
        settingsMap.put("GeneralFontSize" , settings.getGeneralFontSize());
        settingsMap.put("TitleFontSize" , settings.getTitleFontSize());
        settingsMap.put("SubTitleFontSize" , settings.getSubtitleFontSize());
        settingsMap.put("MainTimerFontSize" , settings.getMainTimerFontSize());
        settingsMap.put("SplitTimerFontSize" , settings.getSplitTimerFontSize());
        settingsMap.put("WindowHeight" , settings.getWindowHeight());
        settingsMap.put("WindowWidth" , settings.getWindowWidth());

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(fileManager.getSettingsFile(), settingsMap);
            System.out.println("Settings Saved to "+fileManager.getSettingsFile().getPath());
        } catch (IOException e) {
            System.out.println("Error saving Settings.");
            e.printStackTrace();
        }
    }

    public void saveColours(){
        Map<String, String> colourMap = new HashMap<>();
        colourMap.put("BestTimeColor", settings.getColours().getBestTimeColor().toString());
        colourMap.put("AheadOfTimeColor", settings.getColours().getAheadOfTimeColor().toString());
        colourMap.put("AheadButLostTimeColor", settings.getColours().getAheadButLostTimeColor().toString());
        colourMap.put("BehindTimeColor", settings.getColours().getBehindTimeColor().toString());
        colourMap.put("BehindButGainedTimeColor", settings.getColours().getBehindButGainedTimeColor().toString());

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(fileManager.getColourSettingsFile(), colourMap);
            System.out.println("Colours Saved to "+fileManager.getColourSettingsFile().getPath());
        } catch (IOException e) {
            System.out.println("Error saving Colours.");
            e.printStackTrace();
        }
    }
}
