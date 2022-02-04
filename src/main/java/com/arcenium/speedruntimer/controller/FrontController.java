package com.arcenium.speedruntimer.controller;

import com.arcenium.speedruntimer.model.Split;
import com.arcenium.speedruntimer.model.components.Component;
import com.arcenium.speedruntimer.service.GlobalKeyListener;
import com.arcenium.speedruntimer.service.RunService;
import com.arcenium.speedruntimer.utility.Converter;
import com.arcenium.speedruntimer.utility.SettingsManager;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import java.util.List;

public class FrontController{
    /******************** Services ********************/
    private final GlobalKeyListener globalKeyListener;
    private final RunService runService;

    /******************** Ui Components ********************/
    @FXML
    private GridPane Split_Grid;
    @FXML
    private Label Run_Title_Label;
    @FXML
    private Label Main_Timer;
    private List<Label> componentNameLabels;
    private List<Label> componentValueLabels;
    private Timeline updater;

    /******************** Constructors ********************/
    public FrontController(){
        globalKeyListener = new GlobalKeyListener(this);
        runService = new RunService();
    }

    /******************** Initializers ********************/
    @FXML
    public void initialize(){
        runService.initRun();
        initLabels();
    }

    public void initLabels(){
        //Init run title labels
        Run_Title_Label.setText(runService.getGameSplits().getGameTitle());
        Run_Title_Label.setFont(Font.font(SettingsManager.getINSTANCE().getSettings().getFontStyle(), FontWeight.BOLD, SettingsManager.getINSTANCE().getSettings().getTitleFontSize()));

        //Init split labels
        int rowId = 0;
        Converter converter = Converter.getINSTANCE();
        for(Split split : runService.getGameSplits().getSplits()){
            Label pic = new Label();
            Label name = new Label(split.getName());
            Label timeSave = new Label();
            Label pb = new Label(converter.secondsToTimeString(split.getPbTime()));
            name.setFont(new Font(SettingsManager.getINSTANCE().getSettings().getFontStyle(), SettingsManager.getINSTANCE().getSettings().getGeneralFontSize()));
            timeSave.setFont(new Font(14));
            pb.setFont(new Font(14));
            //TODO why does setting this to a ridiculous number work?
            pb.setMaxWidth(10000);
            pb.setAlignment(Pos.CENTER_RIGHT);
            pb.setTextAlignment(TextAlignment.RIGHT);
            Split_Grid.addRow(rowId, pic, name, timeSave, pb);
            rowId++;
        }

        //Init main timer and sub timer
        Main_Timer.setFont(new Font(SettingsManager.getINSTANCE().getSettings().getFontStyle(), SettingsManager.getINSTANCE().getSettings().getMainTimerFontSize()));

        //Init components

    }

    private void initUiUpdater() {
        updater = new Timeline(new KeyFrame(Duration.millis(10), e->
        {
            //Poll time
            double rawTime = runService.getTimer().poll();
            double time = rawTime/1000;
            String timeString = Converter.getINSTANCE().secondsToTimeString(time);
            //Update Timer label
            Main_Timer.setText(timeString);
            //Update split labels
            //TODO clean up and set all split values
            Label updateSplitTime = (Label) Split_Grid.getChildren().get(runService.getRunSplitIndex()*4+3);
            updateSplitTime.setText(timeString);

            Label updateTimeDifference = (Label) Split_Grid.getChildren().get(runService.getRunSplitIndex()*4+2);
            Split split = runService.getGameSplits().getSplits().get(runService.getRunSplitIndex());
            split.updateLength(rawTime);
            double timeDifference = split.getLength()- split.getPbTime();
            if(timeDifference < 0){
                updateTimeDifference.setText(Converter.getINSTANCE().secondsToTimeString(timeDifference));
            }
            else{
                updateTimeDifference.setText("+"+Converter.getINSTANCE().secondsToTimeString(timeDifference));
            }
            //Update component labels
            int componentIndex = 0;
            for(Component component : runService.getComponents()){
                //Set Label Name
                componentNameLabels.get(componentIndex).setText(component.getName());
                //Set Label Value
                componentNameLabels.get(componentIndex).setText(component.getValue());
            }
        }));
        updater.setCycleCount(Animation.INDEFINITE);
        updater.play();
    }

    private void stopUiUpdater(){
        updater.stop();
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
            initUiUpdater();
        }
        //If all splits have been ended, finalize the run
        else if(runService.isActive() && runService.getRunSplitIndex() >= runService.getNumOfSplits()-1){
            runService.stopRun();
            stopUiUpdater();
            runService.finalizeRun();
        }
        //Regular split behaviour
        else{
            //Get current time
            double splitTime = runService.getTimer().poll();
            //Update internal state
            runService.nextSplit(splitTime);

            //Set previous split labels to final values.
            //TODO set proper values
            Label updateSplitTime = (Label) Split_Grid.getChildren().get((runService.getRunSplitIndex()-1)*4+3);
            String time = Converter.getINSTANCE().secondsToTimeString(runService.getModifiedGameSplits().getSplits().get(runService.getRunSplitIndex()-1).getLength());
            updateSplitTime.setText(time);
        }
    }

    public void resetHandler(){}

    public void previousSegmentHandler(){}

    public void skipSegmentHandler(){}

    public void togglePauseHandler(){}
}//End of Front Controller Class