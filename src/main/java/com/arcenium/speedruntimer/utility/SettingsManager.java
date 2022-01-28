package com.arcenium.speedruntimer.utility;

import com.arcenium.speedruntimer.config.Settings;

import java.io.File;
import java.io.FileNotFoundException;

public class SettingsManager {
    private static SettingsManager INSTANCE;
    private static Settings settings;
    private static KeyMapUtility keyMapUtility;

    private SettingsManager(){
        settings = new Settings();
        keyMapUtility = new KeyMapUtility(settings.getFileManager(), settings.getKeyMap());
        init();
    }

    public static SettingsManager getINSTANCE(){
        if(INSTANCE == null){
            INSTANCE = new SettingsManager();
        }
        return INSTANCE;
    }

    private void init(){
        try {
            keyMapUtility.initKeys();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

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
        settings.getFileManager().setConfigRoot(path);
    }

    public void setKeyBindingSaveFile(String path){
        settings.getFileManager().setSavedKeyMappingsFile(new File(path));
    }


    public Settings getSettings() {
        return settings;
    }
}
