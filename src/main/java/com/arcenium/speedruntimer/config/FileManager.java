package com.arcenium.speedruntimer.config;

import java.io.File;

public class FileManager {
    private String configRoot;
    private File root;
    private File savedKeyMappingsFile;

    public FileManager() {
        //TODO add microsoft and mac os default files
        String OS = System.getProperty("os.name").toLowerCase();
        if(isUnix(OS)){
            configRoot = System.getProperty("user.home")+"/.config/ArcTime";
        }
        else{
            System.out.println("Invalid OS. Aborting Program.");
            System.exit(1);
        }
        this.root = new File(configRoot);
        if(!this.root.exists()){
            root.mkdirs();
        }

        this.savedKeyMappingsFile = new File(configRoot+"/keymap.json");
    }

    public String getConfigRoot() {
        return configRoot;
    }

    public void setConfigRoot(String configRoot) {
        this.configRoot = configRoot;
    }

    public File getSavedKeyMappingsFile() {
        return savedKeyMappingsFile;
    }

    public void setSavedKeyMappingsFile(File savedKeyMappingsFile) {
        this.savedKeyMappingsFile = savedKeyMappingsFile;
    }

    private boolean isWindows(String OS) {
        return (OS.indexOf("win") >= 0);
    }

    private boolean isMac(String OS) {
        return (OS.indexOf("mac") >= 0);
    }

    private boolean isUnix(String OS) {
        return (OS.indexOf("nix") >= 0
                || OS.indexOf("nux") >= 0
                || OS.indexOf("aix") > 0);
    }

    private boolean isSolaris(String OS) {
        return (OS.indexOf("sunos") >= 0);
    }
}
