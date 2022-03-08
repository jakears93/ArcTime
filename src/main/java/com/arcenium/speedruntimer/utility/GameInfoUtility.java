package com.arcenium.speedruntimer.utility;

import com.arcenium.speedruntimer.config.FileManager;
import com.arcenium.speedruntimer.model.GameInfo;
import com.arcenium.speedruntimer.model.Split;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameInfoUtility {
    /******************** Required Inputs ********************/
    private final FileManager fileManager;

    /******************** Constructor ********************/
    public GameInfoUtility(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    /******************** Utility Functions ********************/
    public GameInfo loadGameInfo(String gameTitle, String category) throws FileNotFoundException {
        String infoFilePath = fileManager.getSplitsDirectoryPath()+"/"+gameTitle+"/"+category+".info";
        File gameSplitsInfoFile = new File(infoFilePath);
        String splitsFilePath = fileManager.getSplitsDirectoryPath()+"/"+gameTitle+"/"+category+".splits";
        File gameSplitsFile = new File(splitsFilePath);

        GameInfo gameInfo = new GameInfo();
        if(!gameSplitsInfoFile.exists()){
            throw new FileNotFoundException("GameSplits Info File not found");
        }
        if(!gameSplitsFile.exists()){
            throw new FileNotFoundException("GameSplits Splits File not found");
        }

        try{
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> gameSplitsInfoMap = mapper.readValue(gameSplitsInfoFile, new TypeReference<>() {});
            gameInfo.setGameTitle(gameTitle);
            gameInfo.setCategory(category);
            gameInfo.setAttempts((int)(gameSplitsInfoMap.get("numOfAttempts")));
            gameInfo.setPb((double)(gameSplitsInfoMap.get("pbTime")));
            gameInfo.setSumOfBest((double) (gameSplitsInfoMap.get("sumOfBest")));
            System.out.println("Game Splits Info Loaded from File: " + gameSplitsInfoFile.getPath());

            List<Split> splits = mapper.readValue(gameSplitsFile, new TypeReference<>(){});
            gameInfo.setSplits(splits);

            System.out.println("Game Splits Loaded from File: " + gameSplitsFile.getPath());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error Loading GameSplit Object.");
            return null;
        }
        return gameInfo;
    }

    public void saveGameInfo(GameInfo gameInfo){
        File directory = new File(fileManager.getSplitsDirectoryPath()+"/"+ gameInfo.getGameTitle());
        if(!directory.exists()){
            directory.mkdirs();
        }
        String infoFilePath = fileManager.getSplitsDirectoryPath()+"/"+ gameInfo.getGameTitle()+"/"+ gameInfo.getCategory()+".info";
        File gameSplitsInfoFile = new File(infoFilePath);
        String splitsFilePath = fileManager.getSplitsDirectoryPath()+"/"+ gameInfo.getGameTitle()+"/"+ gameInfo.getCategory()+".splits";
        File gameSplitsFile = new File(splitsFilePath);

        Map<String, Object> infoMap = new HashMap<>();
        infoMap.put("gameTitle", gameInfo.getGameTitle());
        infoMap.put("category", gameInfo.getCategory());
        infoMap.put("numOfAttempts", gameInfo.getAttempts());
        infoMap.put("pbTime", gameInfo.getPb());
        infoMap.put("sumOfBest", gameInfo.getSumOfBest());

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(gameSplitsInfoFile, infoMap);
            System.out.println("GameSplit Info Saved to "+gameSplitsInfoFile.getPath());

            mapper.writerWithDefaultPrettyPrinter().writeValue(gameSplitsFile, gameInfo.getSplits());

            System.out.println("GameSplit Splits Saved to "+gameSplitsFile.getPath());
        } catch (IOException e) {
            System.out.println("Error saving GameSplit.");
            e.printStackTrace();
        }
    }

    public List<GameInfo> getAllGames(){
        ArrayList<GameInfo> games = new ArrayList<>();
        String infoDirectoryPath = fileManager.getSplitsDirectoryPath()+"/";
        File[] gameDirectories = new File(infoDirectoryPath).listFiles(File::isDirectory);
        for (File game : gameDirectories){
            File[] gameCategories = new File(game+"/").listFiles(g-> g.getName().endsWith("info"));
            for (File category : gameCategories) {
                try {
                    games.add(loadGameInfo(game.getName(), category.getName().substring(0, category.getName().length()-5)));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return games;
    }
}//End of GameSplitUtility Class
