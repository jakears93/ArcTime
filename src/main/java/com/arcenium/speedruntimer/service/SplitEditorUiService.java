package com.arcenium.speedruntimer.service;

import com.arcenium.speedruntimer.controller.EditSplitController;
import com.arcenium.speedruntimer.model.GameInfo;
import com.arcenium.speedruntimer.model.Split;
import com.arcenium.speedruntimer.utility.Converter;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class SplitEditorUiService {
    /******************** Fields ********************/
    private final SplitEditorService splitEditorService;

    /******************** UI Elements ********************/
    private final VBox root;
    private GridPane gameInfoGrid;
    private TextField gameTitleEditable;
    private TextField runCategoryEditable;
    private TextField runOffsetEditable;
    private ScrollPane scrollPane;
    private GridPane splitsLayout;
    private Button cancelButton;
    private Button saveButton;

    /******************** Constructor ********************/
    public SplitEditorUiService(VBox root, SplitEditorService splitEditorService) {
        this.root = root;
        this.splitEditorService = splitEditorService;
    }

    /******************** Init Functions ********************/
    public void initUi() {
        this.gameInfoGrid = (GridPane) root.getChildren().get(1);
        this.gameTitleEditable = (TextField) gameInfoGrid.getChildren().get(3);
        this.runCategoryEditable = (TextField) gameInfoGrid.getChildren().get(4);
        this.runOffsetEditable = (TextField) gameInfoGrid.getChildren().get(5);

        this.scrollPane = (ScrollPane) root.getChildren().get(3);
        this.splitsLayout = (GridPane) scrollPane.getContent();

        this.saveButton = (Button) ((GridPane)(root.getChildren().get(4))).getChildren().get(0);
        this.cancelButton = (Button) ((GridPane)(root.getChildren().get(4))).getChildren().get(1);

        saveButton.setOnAction(e-> splitEditorService.save(gameInfoGrid, splitsLayout));
        cancelButton.setOnAction(EditSplitController::cancel);
    }

    public void initSplits() {
        GameInfo gameInfo = splitEditorService.getGameInfo();
        gameTitleEditable.setText(gameInfo.getGameTitle());
        runCategoryEditable.setText(gameInfo.getCategory());
        //TODO implement offset
        runOffsetEditable.setText("0.00");
        List<Split> splitList = gameInfo.getSplits();
        int rowIndex = 0;
        for(Split split : splitList){
            TextField splitName = new TextField(split.getName());
            TextField splitPB = new TextField(Converter.getINSTANCE().secondsToTimeString(split.getPbTime()));
            TextField splitBest = new TextField(Converter.getINSTANCE().secondsToTimeString(split.getBestTime()));
            splitsLayout.add(splitName, 0, rowIndex);
            splitsLayout.add(splitPB, 1, rowIndex);
            splitsLayout.add(splitBest, 2, rowIndex);
            rowIndex++;
        }
    }
}//End of SplitEditorUIService
