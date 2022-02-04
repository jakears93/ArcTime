package com.arcenium.speedruntimer.config;

import java.io.File;

//TODO convert to a singleton
public class FileManager {
    /******************** Files and Paths ********************/
    private String configRootPath;
    private final File savedKeyMappingsFile;
    private final File settingsFile;
    private final File colourSettingsFile;
    private final String splitsDirectoryPath;

    /******************** Constructor ********************/
    public FileManager() {
        //TODO add microsoft and mac os default files
        String OS = System.getProperty("os.name").toLowerCase();
        if(isUnix(OS)){
            configRootPath = System.getProperty("user.home")+"/.config/ArcTime";
        }
        else{
            System.out.println("Invalid OS. Aborting Program.");
            System.exit(1);
        }
        File root = new File(configRootPath);
        if(!root.exists()){
            root.mkdirs();
        }

        this.splitsDirectoryPath = configRootPath +"/splits";
        File gameSplitDir = new File(this.splitsDirectoryPath);
        if(!gameSplitDir.exists()){
            gameSplitDir.mkdirs();
        }

        this.savedKeyMappingsFile = new File(configRootPath +"/keymap.cfg");
        this.settingsFile = new File(configRootPath +"/settings.cfg");
        this.colourSettingsFile = new File(configRootPath +"/colours.cfg");
    }

    /******************** Getters and Setters ********************/
    public void setConfigRootPath(String configRootPath) {
        this.configRootPath = configRootPath;
    }

    public File getSavedKeyMappingsFile() {
        return savedKeyMappingsFile;
    }

    public File getSettingsFile() {
        return settingsFile;
    }

    public File getColourSettingsFile() {
        return colourSettingsFile;
    }

    public String getSplitsDirectoryPath(){
        return splitsDirectoryPath;
    }

    /******************** Functions to determine OS ********************/
    private boolean isWindows(String OS) {
        return (OS.contains("win"));
    }

    private boolean isMac(String OS) {
        return (OS.contains("mac"));
    }

    private boolean isUnix(String OS) {
        return (OS.contains("nix")
                || OS.contains("nux")
                || OS.contains("aix"));
    }

    private boolean isSolaris(String OS) {
        return (OS.contains("sunos"));
    }

}//End of File Manager
