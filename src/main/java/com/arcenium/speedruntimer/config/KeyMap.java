package com.arcenium.speedruntimer.config;

import org.jnativehook.keyboard.NativeKeyEvent;
import java.util.HashMap;
import java.util.Map;

public class KeyMap {
    /******************** Key Maps ********************/
    private Map<String, Integer> keys;
    private final Map<String, Integer> defaultKeys;

    /******************** Constructor/Initializer ********************/
    public KeyMap() {
        this.keys = new HashMap<>();
        defaultKeys = new HashMap<>();
        defaultKeys.put("Start / Split", NativeKeyEvent.VC_ENTER);
        defaultKeys.put("Toggle Pause", NativeKeyEvent.VC_P);
        defaultKeys.put("Reset", NativeKeyEvent.VC_R);
        defaultKeys.put("Skip Split", NativeKeyEvent.VC_RIGHT);
        defaultKeys.put("Previous Split", NativeKeyEvent.VC_LEFT);
        defaultKeys.put("Toggle Global Hotkeys", NativeKeyEvent.VC_MINUS);
    }

    /******************** Getters and Setters ********************/
    public Map<String, Integer> getKeys() {
        return keys;
    }

    public void setKeys(Map<String, Integer> keys){
        this.keys = keys;
    }

    public Map<String, Integer> getDefaultKeys(){
        return this.defaultKeys;
    }

    public int getStartKey(){
        return keys.getOrDefault("Start / Split", -1);
    }

    public int getResetKey(){
        return keys.getOrDefault("Reset", -1);
    }

    public int getPauseKey(){
        return keys.getOrDefault("Toggle Pause", -1);
    }

    public int getSkipKey(){
        return keys.getOrDefault("Skip Split", -1);
    }

    public int getPreviousKey(){
        return keys.getOrDefault("Previous Split", -1);
    }

    public int getToggleGlobalHotkeysKey(){
        return keys.getOrDefault("Toggle Global Hotkeys", -1);
    }
}//End of KeyMap Class
