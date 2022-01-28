package com.arcenium.speedruntimer.controller;

import com.arcenium.speedruntimer.model.SpeedTimer;
import com.arcenium.speedruntimer.model.Split;
import com.arcenium.speedruntimer.service.GlobalKeyListener;
import com.arcenium.speedruntimer.service.SplitService;
import com.arcenium.speedruntimer.utility.SettingsManager;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class FrontController{
    //TODO maybe make manager static in launcher class to be accessible to all views?
    private final SettingsManager settingsManager;
    private final GlobalKeyListener globalKeyListener;
    private final SplitService splitService;

    @FXML
    private GridPane Split_Grid_0;
    @FXML
    private Label Run_Title_Label;
    @FXML
    private Label Main_Timer;

    private SpeedTimer timer = new SpeedTimer();
    private Timeline updater;

    private int index = -1;


    public FrontController(){
        settingsManager = new SettingsManager();
        globalKeyListener = new GlobalKeyListener(this, settingsManager);
        splitService = new SplitService();
    }

    @FXML
    public void initialize(){
        initLabels();
    }

    public void initLabels(){
        Run_Title_Label.setText("Super Mario 64");
        int rowId = 0;
        for(Split split : splitService.getSplits()){
            Label pic = new Label();
            Label name = new Label(split.getName());
            Label timeSave = new Label();
            Label pb = new Label(String.valueOf(split.getPbTime()));
            name.setFont(new Font(14));
            timeSave.setFont(new Font(14));
            pb.setFont(new Font(14));
            //TODO why does setting this to a ridiculous number work?
            pb.setMaxWidth(10000);
            pb.setAlignment(Pos.CENTER_RIGHT);
            pb.setTextAlignment(TextAlignment.RIGHT);


            Split_Grid_0.addRow(rowId, pic, name, timeSave, pb);
            rowId++;
        }
    }


    public void startStopHandler(){
        if(timer.isActive() && splitService.getCurrentSplitIndex() >= splitService.getNumberOfSplits()-1){
            updater.stop();
            timer.stopTimer();
        }
        else if(!timer.isActive()){
            timer.startTimer();
            updater = new Timeline(new KeyFrame(Duration.millis(10), e->
            {
                Main_Timer.setText(timer.getInfo());
                splitService.getSplits().get(splitService.getCurrentSplitIndex()).setStartTime(timer.getTimeElapsedMillis());
                Label updateSplitTime = (Label) Split_Grid_0.getChildren().get(splitService.getCurrentSplitIndex()*4+3);
                updateSplitTime.setText(timer.getInfo());
            }));
            updater.setCycleCount(Animation.INDEFINITE);
            updater.play();
        }
        else{
            splitService.getSplits().get(splitService.getCurrentSplitIndex()+1).setStartTime(timer.getTimeElapsedMillis());
            splitService.setCurrentSplitIndex(splitService.getCurrentSplitIndex()+1);
        }
    }

}