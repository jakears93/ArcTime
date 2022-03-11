package com.arcenium.speedruntimer.service;

import com.arcenium.speedruntimer.model.GameInfo;
import com.arcenium.speedruntimer.model.Split;
import com.arcenium.speedruntimer.utility.SettingsManager;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class SplitEditorService {
    /******************** Fields ********************/
    private GameInfo gameInfo;

    /******************** Constructor and Initializers ********************/
    public SplitEditorService(GameInfo gameInfo) {
        if(gameInfo == null){
            gameInfo = new GameInfo();
        }
        this.gameInfo = gameInfo;
    }

    /******************** Functions ********************/
    public void save(GridPane gameInfoGrid, GridPane splitsGrid){
        System.out.println("Saving GameInfo");

        gameInfo.setGameTitle(((TextField) gameInfoGrid.getChildren().get(3)).getText());
        gameInfo.setCategory(((TextField) gameInfoGrid.getChildren().get(4)).getText());
        gameInfo.setAttempts(0);

        double totalTime = 0;
        double sumOfBest = 0;

        for(int rowIndex=0; rowIndex<splitsGrid.getRowCount(); rowIndex++){
            Split split = new Split();
            split.setName(((TextField) splitsGrid.getChildren().get(rowIndex*3)).getText());
            split.setPbTime(Double.parseDouble(((TextField) splitsGrid.getChildren().get(rowIndex*3+1)).getText()));
            split.setBestTime(Double.parseDouble(((TextField) splitsGrid.getChildren().get(rowIndex*3+2)).getText()));
            split.setStartTime(totalTime);
            totalTime += split.getPbTime();
            split.setEndTime(totalTime);
            split.updateLength();
            gameInfo.getSplits().add(split);
            sumOfBest += split.getBestTime();
        }
        gameInfo.setPb(totalTime);
        gameInfo.setSumOfBest(sumOfBest);

        SettingsManager.getINSTANCE().saveGameInfo(gameInfo);
    }

    /******************** Getters ********************/
    public GameInfo getGameInfo() {
        return gameInfo;
    }

}//End of SplitEditorService
