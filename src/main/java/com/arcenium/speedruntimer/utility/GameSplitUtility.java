package com.arcenium.speedruntimer.utility;

import com.arcenium.speedruntimer.config.FileManager;
import com.arcenium.speedruntimer.model.GameSplits;
import com.arcenium.speedruntimer.model.Split;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameSplitUtility {
    /******************** Required Inputs ********************/
    private final FileManager fileManager;

    /******************** Constructor ********************/
    public GameSplitUtility(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    /******************** Utility Functions ********************/
    public GameSplits loadGameSplits(String gameTitle, String category) throws FileNotFoundException {
        String infoFilePath = fileManager.getSplitsDirectoryPath()+"/"+gameTitle+"/"+category+".info";
        File gameSplitsInfoFile = new File(infoFilePath);
        String splitsFilePath = fileManager.getSplitsDirectoryPath()+"/"+gameTitle+"/"+category+".splits";
        File gameSplitsFile = new File(splitsFilePath);

        GameSplits gameSplits = new GameSplits();
        if(!gameSplitsInfoFile.exists()){
            throw new FileNotFoundException("GameSplits Info File not found");
        }
        if(!gameSplitsFile.exists()){
            throw new FileNotFoundException("GameSplits Splits File not found");
        }

        try{
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> gameSplitsInfoMap = mapper.readValue(gameSplitsInfoFile, new TypeReference<>() {});
            gameSplits.setGameTitle(gameTitle);
            gameSplits.setCategory(category);
            gameSplits.setAttempts((int)(gameSplitsInfoMap.get("numOfAttempts")));
            gameSplits.setPb((double)(gameSplitsInfoMap.get("pbTime")));
            gameSplits.setSumOfBest((double) (gameSplitsInfoMap.get("sumOfBest")));
            System.out.println("Game Splits Info Loaded from File: " + gameSplitsInfoFile.getPath());

            List<Split> splits = mapper.readValue(gameSplitsFile, new TypeReference<>(){});
            gameSplits.setSplits(splits);

            System.out.println("Game Splits Loaded from File: " + gameSplitsFile.getPath());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error Loading GameSplit Object.");
            return null;
        }
        return gameSplits;
    }

    public void saveGameSplits(GameSplits gameSplits){
        File directory = new File(fileManager.getSplitsDirectoryPath()+"/"+gameSplits.getGameTitle());
        if(!directory.exists()){
            directory.mkdirs();
        }
        String infoFilePath = fileManager.getSplitsDirectoryPath()+"/"+gameSplits.getGameTitle()+"/"+gameSplits.getCategory()+".info";
        File gameSplitsInfoFile = new File(infoFilePath);
        String splitsFilePath = fileManager.getSplitsDirectoryPath()+"/"+gameSplits.getGameTitle()+"/"+gameSplits.getCategory()+".splits";
        File gameSplitsFile = new File(splitsFilePath);

        Map<String, Object> infoMap = new HashMap<>();
        infoMap.put("gameTitle", gameSplits.getGameTitle());
        infoMap.put("category", gameSplits.getCategory());
        infoMap.put("numOfAttempts", gameSplits.getAttempts());
        infoMap.put("pbTime", gameSplits.getPb());
        infoMap.put("sumOfBest", gameSplits.getSumOfBest());

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(gameSplitsInfoFile, infoMap);
            System.out.println("GameSplit Info Saved to "+gameSplitsInfoFile.getPath());

            mapper.writerWithDefaultPrettyPrinter().writeValue(gameSplitsFile, gameSplits.getSplits());

            System.out.println("GameSplit Splits Saved to "+gameSplitsFile.getPath());
        } catch (IOException e) {
            System.out.println("Error saving GameSplit.");
            e.printStackTrace();
        }
    }
}//End of GameSplitUtility Class
