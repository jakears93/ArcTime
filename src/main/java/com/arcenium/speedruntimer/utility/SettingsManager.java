package com.arcenium.speedruntimer.utility;

import com.arcenium.speedruntimer.config.Settings;

import java.io.File;
import java.io.FileNotFoundException;

public class SettingsManager {
    Settings settings;
    KeyMapUtility keyMapUtility;

    public SettingsManager() {
        settings = new Settings();
        keyMapUtility = new KeyMapUtility(settings.getFileManager(), settings.getKeyMap());
        init();
    }

    private void init(){
        try {
            this.keyMapUtility.initKeys();
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
        this.settings.getFileManager().setConfigRoot(path);
    }

    public void setKeyBindingSaveFile(String path){
        settings.getFileManager().setSavedKeyMappingsFile(new File(path));
    }


    public Settings getSettings() {
        return settings;
    }
}
