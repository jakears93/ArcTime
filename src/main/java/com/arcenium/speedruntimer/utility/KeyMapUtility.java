package com.arcenium.speedruntimer.utility;

import com.arcenium.speedruntimer.config.FileManager;
import com.arcenium.speedruntimer.config.KeyMap;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public class KeyMapUtility {
    /******************** Required Inputs ********************/
    private final FileManager fileManager;
    private final KeyMap keyMap;

    /******************** Constructor ********************/
    public KeyMapUtility(FileManager fileManager, KeyMap keyMap) {
        this.fileManager = fileManager;
        this.keyMap = keyMap;
    }

    /******************** Utility Functions ********************/
    public KeyMap getKeyMap() {
        return keyMap;
    }

    public void initKeys() throws FileNotFoundException {
        if(!this.fileManager.getSavedKeyMappingsFile().exists()){
            //Set keys to default values
            resetKeysToDefault();

            //Save keys to conf file
            saveKeyMap(keyMap.getKeys());
        }
        else{
            loadKeyMapping();
        }
    }

    public void saveKeyMap(Map<String, Integer> keys){
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(fileManager.getSavedKeyMappingsFile(), keys);
            System.out.println("KeyMap Saved: "+keyMap.getKeys());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadKeyMapping() throws FileNotFoundException {
        if(!this.fileManager.getSavedKeyMappingsFile().exists()){
            throw new FileNotFoundException("Key Mapping Config not found");
        }

        try{
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Integer> keys = mapper.readValue(fileManager.getSavedKeyMappingsFile(), new TypeReference<>() {});
            keyMap.setKeys(keys);
            System.out.println("KeyMap Loaded from File: "+keyMap.getKeys());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error Loading KeyMap. Loading default key mapping instead.");
            resetKeysToDefault();
        }
    }

    public void resetKeysToDefault(){
        //Set keys to default values
        this.keyMap.setKeys(this.keyMap.getDefaultKeys());
    }
}//End of KeyMapUtility Class
