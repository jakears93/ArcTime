package com.arcenium.speedruntimer.controller;

import com.arcenium.speedruntimer.model.GameSplits;
import com.arcenium.speedruntimer.model.SpeedTimer;
import com.arcenium.speedruntimer.model.Split;
import com.arcenium.speedruntimer.service.GlobalKeyListener;
import com.arcenium.speedruntimer.service.RunService;
import com.arcenium.speedruntimer.service.SplitService;
import com.arcenium.speedruntimer.utility.Converter;
import com.arcenium.speedruntimer.utility.SettingsManager;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class FrontController{
    private final GlobalKeyListener globalKeyListener;
    private final SplitService splitService;
    private final RunService runService;

    @FXML
    private GridPane Split_Grid_0;
    @FXML
    private Label Run_Title_Label;
    @FXML
    private Label Main_Timer;

    private Timeline updater;

    public FrontController(){
        globalKeyListener = new GlobalKeyListener(this);
        splitService = new SplitService();
        runService = new RunService();
    }

    @FXML
    public void initialize(){
        loadGameSplits();
        initLabels();
    }

    public void loadGameSplits(){
        //TODO temp solution of hardcoding game to load
        GameSplits gameSplits = SettingsManager.getINSTANCE().loadGameSplits("Super Mario 64", "16 Star");
        runService.setGameSplits(gameSplits);
    }

    public void initLabels(){
        Run_Title_Label.setText(runService.getGameSplits().getGameTitle());
        Run_Title_Label.setFont(Font.font(SettingsManager.getINSTANCE().getSettings().getFontStyle(), FontWeight.BOLD, SettingsManager.getINSTANCE().getSettings().getTitleFontSize()));

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


            Split_Grid_0.addRow(rowId, pic, name, timeSave, pb);
            rowId++;
        }
        Main_Timer.setFont(new Font(SettingsManager.getINSTANCE().getSettings().getFontStyle(), SettingsManager.getINSTANCE().getSettings().getMainTimerFontSize()));
    }


    /******************** Event Handlers ********************/

    public void startStopHandler(){
        if(runService.isActive() && runService.getRunSplitIndex() >= runService.getNumOfSplits()-1){
            updater.stop();
            runService.stopRun();
        }
        else if(!runService.isActive()){
            runService.startRun();
            updater = new Timeline(new KeyFrame(Duration.millis(100), e->
            {
                Main_Timer.setText(runService.getTimer().getInfo());
                Label updateSplitTime = (Label) Split_Grid_0.getChildren().get(runService.getRunSplitIndex()*4+3);
                updateSplitTime.setText(runService.getTimer().getInfo());
            }));
            updater.setCycleCount(Animation.INDEFINITE);
            updater.play();
        }
        else{
            runService.nextSplit();
        }
    }

    public void resetHandler(){}

    public void previousSegmentHandler(){}

    public void skipSegmentHandler(){}

    public void togglePauseHandler(){}

    public boolean isPaused() {
        //TODO remove
        return false;
    }
}