package com.arcenium.speedruntimer.controller;

import com.arcenium.speedruntimer.service.GlobalKeyListener;
import com.arcenium.speedruntimer.service.RunService;
import com.arcenium.speedruntimer.service.UiService;
import com.arcenium.speedruntimer.utility.Converter;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class FrontController{
    /******************** Services ********************/
    private final GlobalKeyListener globalKeyListener;
    private final RunService runService;
    private UiService uiService;

    /******************** Ui Components ********************/
    private Timeline updater;
    @FXML
    private VBox vBox;

    /******************** Constructors ********************/
    public FrontController(){
        globalKeyListener = new GlobalKeyListener(this);
        runService = new RunService();
    }

    /******************** Initializers ********************/
    @FXML
    public void initialize(){
        this.uiService = new UiService(vBox, runService);
        runService.initRun();
        uiService.initUi();
    }

    /******************** General Functions ********************/
    public boolean isPaused(){
        //TODO decide on functionality for pause
        return false;
    }

    /******************** Event Handlers ********************/
    public void startStopHandler(){
        //If run hasn't started, start run
        if(!runService.isActive()){
            runService.startRun();
            uiService.initUiUpdater();
        }
        //If all splits have been ended, finalize the run
        else if(runService.isActive() && runService.getRunSplitIndex() >= runService.getNumOfSplits()-1){
            runService.stopRun();
            uiService.stopUiUpdater();
            runService.finalizeRun();
        }
        //Regular split behaviour
        else{
            //Get current time
            double splitTime = runService.getTimer().poll();
            //Update internal state
            runService.nextSplit(splitTime);

            //Set previous split labels to final values.
            //TODO belongs in UiService
            Label updateSplitTime = (Label) uiService.getSplitLayout().getChildren().get((runService.getRunSplitIndex()-1)*4+3);
            String time = Converter.getINSTANCE().secondsToTimeString(runService.getModifiedGameInfo().getSplits().get(runService.getRunSplitIndex()-1).getLength());
            updateSplitTime.setText(time);
        }
    }

    public void resetHandler(){}

    public void previousSegmentHandler(){}

    public void skipSegmentHandler(){}

    public void togglePauseHandler(){}
}//End of Front Controller Class