package com.arcenium.speedruntimer.utility;

import com.arcenium.speedruntimer.config.Settings;
import com.arcenium.speedruntimer.model.GameSplits;
import java.io.FileNotFoundException;

public class SettingsManager {
    /******************** Singleton Instance and Children ********************/
    private static SettingsManager INSTANCE;
    private static Settings settings;
    private static KeyMapUtility keyMapUtility;
    private static SettingsUtility settingsUtility;
    private static GameSplitUtility gameSplitUtility;

    /******************** Constructor and Initializer ********************/
    private SettingsManager(){
        settings = new Settings();
        keyMapUtility = new KeyMapUtility(settings.getFileManager(), settings.getKeyMap());
        settingsUtility = new SettingsUtility(settings.getFileManager(), settings);
        gameSplitUtility = new GameSplitUtility(settings.getFileManager());
        init();
    }

    private void init(){
        try {
            keyMapUtility.initKeys();
            settingsUtility.loadSettings();
            settingsUtility.loadColours();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /******************** Singleton Retriever ********************/
    public static SettingsManager getINSTANCE(){
        if(INSTANCE == null){
            INSTANCE = new SettingsManager();
        }
        return INSTANCE;
    }

    /******************** Utility Functions ********************/
    public void saveKeyBinds(){
        keyMapUtility.saveKeyMap(keyMapUtility.getKeyMap().getKeys());
    }

    public void loadKeyBinds(){
        try {
            keyMapUtility.loadKeyMapping();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadDefaultKeyBinds(){
        keyMapUtility.resetKeysToDefault();
    }

    public void setConfigDirectoryRoot(String path){
        settings.getFileManager().setConfigRootPath(path);
    }

    public Settings getSettings() {
        return settings;
    }

    public void saveGameSplits(GameSplits gameSplits){
        gameSplitUtility.saveGameSplits(gameSplits);
    }

    public GameSplits loadGameSplits(String gameTitle, String category) {
        try {
            return gameSplitUtility.loadGameSplits(gameTitle, category);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}//End of SettingsManager Class
