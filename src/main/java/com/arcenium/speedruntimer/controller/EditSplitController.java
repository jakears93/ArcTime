package com.arcenium.speedruntimer.controller;

import com.arcenium.speedruntimer.model.GameInfo;
import com.arcenium.speedruntimer.service.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EditSplitController {
    /******************** Services ********************/
    private final SplitEditorService splitEditorService;
    private SplitEditorUiService uiService;

    /******************** Ui Components ********************/
    @FXML
    private VBox root;

    /******************** Constructors ********************/
    public EditSplitController(GameInfo gameInfo) {
        this.splitEditorService = new SplitEditorService(gameInfo);
    }

    /******************** Initializers ********************/
    @FXML
    public void initialize(){
        this.uiService = new SplitEditorUiService(root, splitEditorService);
        this.uiService.initUi();
        String initMode = ((Label) root.getChildren().get(0)).getText();
        if(initMode.equals("Edit Splits")){
            uiService.initSplits();
        }
    }

    public static void cancel(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}//End of EditSplitController

