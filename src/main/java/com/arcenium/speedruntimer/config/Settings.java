package com.arcenium.speedruntimer.config;

public class Settings {
    //----------Fields / Attributes----------//
    FileManager fileManager;
    KeyMap keyMap;

    //----------Constructors----------//

    public Settings() {
        this.fileManager = new FileManager();
        this.keyMap = new KeyMap();
    }

    //----------Class Specific Methods----------//

    //----------Default Methods----------//


    public FileManager getFileManager() {
        return fileManager;
    }

    public KeyMap getKeyMap() {
        return keyMap;
    }
}//End of Settings Class
