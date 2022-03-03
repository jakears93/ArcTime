package com.arcenium.speedruntimer.service;

import com.arcenium.speedruntimer.model.ComparisonType;
import com.arcenium.speedruntimer.model.Split;
import com.arcenium.speedruntimer.utility.Converter;
import com.arcenium.speedruntimer.utility.SettingsManager;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.util.List;

public class UiService {
    /******************** Fields ********************/
    private final RunService runService;
    private Timeline updater;

    /******************** UI Elements ********************/
    private final VBox view;
    //Run Info Layout Elements
    private GridPane runInfoLayout;
    private Label runTitleLabel;
    private Label runCategoryLabel;
    private Label runCountLabel;
    private Separator infoSplitSeparator;
    //Split Layout Elements
    private GridPane splitLayout;
    private List<Label> splitTitleLabels;
    private List<Label> splitTimeLabels;
    private Separator splitTimerSeparator;
    //Timer Layout Elements
    private GridPane timerLayout;
    private Label mainTimerLabel;
    private Label splitTimerLabel;
    private Label currentSplitNameLabel;
    private Label currentSplitPbLabel;
    private Label currentSplitBestLabel;
    private Separator timerComponentSeparator;
    //Stat Component Layout Elements
    private GridPane componentLayout;
    private List<Label> componentTitleLabels;
    private List<Label> componentInfoLabels;

    /******************** Constructor ********************/
    public UiService(VBox view, RunService runService) {
        this.view = view;
        this.runService = runService;
    }

    /******************** Init Functions ********************/
    public void initUi(){
        //Get references from FXML file
        List<Node> uiElements = view.getChildren();
        try {
            this.runInfoLayout = (GridPane) uiElements.get(0);
            this.infoSplitSeparator = (Separator) uiElements.get(1);
            this.splitLayout = (GridPane) uiElements.get(2);
            this.splitTimerSeparator = (Separator) uiElements.get(3);
            this.timerLayout = (GridPane) uiElements.get(4);
            this.timerComponentSeparator = (Separator) uiElements.get(5);
            this.componentLayout = (GridPane) uiElements.get(6);
        }catch (ClassCastException | NullPointerException exception){
            exception.printStackTrace();
            System.exit(-1);
        }

        initInfoLayout();
        initSplitLayout();
        initTimerLayout();
        initComponentLayout();

    }

    public void initUiUpdater() {
        //Update Attempt Counter
        updateGameInfo();
        //Set auto updater
        updater = new Timeline(new KeyFrame(Duration.millis(10), e->
        {
            //Poll time
            double rawTime = runService.getTimer().poll();
            double time = rawTime/1000;
            String timeString = Converter.getINSTANCE().secondsToTimeString(time);

            //Update Timer label
            mainTimerLabel.setText(timeString);

            //Update current split labels
            updateCurrentSplit(time);
        }));
        updater.setCycleCount(Animation.INDEFINITE);
        updater.play();
    }

    public void stopUiUpdater(){
        updater.stop();
    }

    private void initInfoLayout(){
        List<Node> infoElements = runInfoLayout.getChildren();
        try{
        runTitleLabel = (Label) infoElements.get(0);
        runCategoryLabel = (Label) infoElements.get(1);
        runCountLabel = (Label) infoElements.get(2);
        }catch (ClassCastException | NullPointerException exception){
            exception.printStackTrace();
            System.exit(-1);
        }
        runTitleLabel.setText(runService.getReferenceGameInfo().getGameTitle());
        runCategoryLabel.setText(runService.getReferenceGameInfo().getCategory());
        runCountLabel.setText(Integer.toString(runService.getReferenceGameInfo().getAttempts()));
    }

    private void initSplitLayout(){
        //TODO limit to 10 items shown
        int rowId = 0;
        Converter converter = Converter.getINSTANCE();
        double totalTime = 0;
        for(Split split : runService.getReferenceGameInfo().getSplits()){
            Label name = new Label(split.getName());
            Label timeSave = new Label();
            double timeComparison = split.getPbTime();
            totalTime+=timeComparison;
            Label pb = new Label(converter.secondsToTimeString(timeComparison));
            Label totalTimeLabel = new Label(converter.secondsToTimeString(totalTime));
            name.setFont(new Font(SettingsManager.getINSTANCE().getSettings().getFontStyle(), SettingsManager.getINSTANCE().getSettings().getGeneralFontSize()));
            timeSave.setFont(new Font(14));
            pb.setFont(new Font(14));
            //TODO why does setting this to a ridiculous number work?
            pb.setMaxWidth(10000);
            pb.setAlignment(Pos.CENTER_RIGHT);
            pb.setTextAlignment(TextAlignment.RIGHT);
            totalTimeLabel.setFont(new Font(14));
            totalTimeLabel.setMaxWidth(100000);
            totalTimeLabel.setAlignment(Pos.CENTER_RIGHT);
            totalTimeLabel.setTextAlignment(TextAlignment.RIGHT);
            this.splitLayout.addRow(rowId, name, timeSave, pb, totalTimeLabel);
            rowId++;
        }
    }

    private void initTimerLayout(){
        List<Node> timerElements = timerLayout.getChildren();
        try{
            mainTimerLabel = (Label) timerElements.get(0);
            currentSplitNameLabel = (Label) timerElements.get(1);
            currentSplitPbLabel = (Label) timerElements.get(2);
            currentSplitBestLabel = (Label) timerElements.get(3);
            splitTimerLabel = (Label) timerElements.get(4);
        }catch (ClassCastException | NullPointerException exception){
            exception.printStackTrace();
            System.exit(-1);
        }
    }

    private void initComponentLayout(){
        //TODO init components
    }

    /******************** Update Functions ********************/
    private void updateGameInfo(){
        runTitleLabel.setText(runService.getReferenceGameInfo().getGameTitle());
        runCategoryLabel.setText(runService.getReferenceGameInfo().getCategory());
        runCountLabel.setText(Integer.toString(runService.getReferenceGameInfo().getAttempts()));
    }

    private void updateCurrentSplit(double polledTime){
        //Get split time by subtracting the previous split's end time
        double splitTime;
        if(runService.getRunSplitIndex() == 0){
            splitTime = polledTime;
        }
        else{
            splitTime = polledTime-runService.getSumOfSplitLengths();
        }

        //Update split timer
        String splitTimeString = Converter.getINSTANCE().secondsToTimeString(splitTime);
        splitTimerLabel.setText(splitTimeString);

        //Update time difference with comparison if within 10% of time
        Split currentSplitComparison = runService.getReferenceGameInfo().getSplits().get(runService.getRunSplitIndex());
        double timeDifference = runService.getSumOfSplitLengths()+splitTime - runService.getSumOfCurrentComparisonSplits();
        if(runService.getSumOfSplitLengths()+splitTime > runService.getSumOfCurrentComparisonSplits()-15){
            Label updateTimeDifference = (Label) splitLayout.getChildren().get(runService.getRunSplitIndex()*4+1);
            if(timeDifference < 0){
                updateTimeDifference.setText(Converter.getINSTANCE().secondsToTimeString(timeDifference));
            }
            else{
                updateTimeDifference.setText("+"+Converter.getINSTANCE().secondsToTimeString(timeDifference));
            }
        }
    }

    public void updateSplitGrid(){

    }

    public void updateComponentGrid(){

    }

    public void finalizeLastSplit(){
        //Get split time by subtracting the previous split's end time
        double splitTime = runService.getGameInfo().getSplits().get(runService.getRunSplitIndex()-1).getLength();

        //Update last split time label
        Label updateSplitTime = (Label) splitLayout.getChildren().get((runService.getRunSplitIndex()-1)*4+2);
        updateSplitTime.setText(Converter.getINSTANCE().secondsToTimeString(splitTime));

        //Update time difference with comparison
        Split currentSplitComparison = runService.getReferenceGameInfo().getSplits().get(runService.getRunSplitIndex()-1);

        double currentSplitComparisonTime = 0;
        if(SettingsManager.getINSTANCE().getSettings().getComparisonType() == ComparisonType.PB){
            currentSplitComparisonTime = runService.getReferenceGameInfo().getSplits().get(runService.getRunSplitIndex()).getPbTime();
        }
        else if(SettingsManager.getINSTANCE().getSettings().getComparisonType() == ComparisonType.BEST){
            currentSplitComparisonTime = runService.getReferenceGameInfo().getSplits().get(runService.getRunSplitIndex()).getPbTime();
        }

        double timeDifference = runService.getSumOfSplitLengths()+splitTime - runService.getSumOfCurrentComparisonSplits() - currentSplitComparisonTime;
        Label updateTimeDifference = (Label) splitLayout.getChildren().get((runService.getRunSplitIndex()-1)*4+1);
        if(timeDifference < 0){
            updateTimeDifference.setText(Converter.getINSTANCE().secondsToTimeString(timeDifference));
        }
        else{
            updateTimeDifference.setText("+"+Converter.getINSTANCE().secondsToTimeString(timeDifference));
        }
    }

    public void finalizeRun(){
        //Get split time by subtracting the previous split's end time
        double splitTime = runService.getGameInfo().getSplits().get(runService.getNumOfSplits()-1).getLength();

        //Update last split time label
        Label updateSplitTime = (Label) splitLayout.getChildren().get((runService.getNumOfSplits()-1)*4+2);
        updateSplitTime.setText(Converter.getINSTANCE().secondsToTimeString(splitTime));

        //Update time difference with comparison
        Split currentSplitComparison = runService.getReferenceGameInfo().getSplits().get(runService.getNumOfSplits()-1);

        double currentSplitComparisonTime = 0;
        if(SettingsManager.getINSTANCE().getSettings().getComparisonType() == ComparisonType.PB){
            currentSplitComparisonTime = runService.getReferenceGameInfo().getSplits().get(runService.getNumOfSplits()-1).getPbTime();
        }
        else if(SettingsManager.getINSTANCE().getSettings().getComparisonType() == ComparisonType.BEST){
            currentSplitComparisonTime = runService.getReferenceGameInfo().getSplits().get(runService.getNumOfSplits()-1).getPbTime();
        }

        double timeDifference = runService.getSumOfSplitLengths()+splitTime - runService.getSumOfCurrentComparisonSplits() - currentSplitComparisonTime;
        Label updateTimeDifference = (Label) splitLayout.getChildren().get((runService.getNumOfSplits()-1)*4+1);
        if(timeDifference < 0){
            updateTimeDifference.setText(Converter.getINSTANCE().secondsToTimeString(timeDifference));
        }
        else{
            updateTimeDifference.setText("+"+Converter.getINSTANCE().secondsToTimeString(timeDifference));
        }
    }

    /******************** Default Functions ********************/
    public GridPane getSplitLayout() {
        return this.splitLayout;
    }
}//End of UiService Class
