package com.arcenium.speedruntimer.service;

import com.arcenium.speedruntimer.model.Split;
import com.arcenium.speedruntimer.model.components.Component;
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
        //TODO ugly, clean up
        updater = new Timeline(new KeyFrame(Duration.millis(10), e->
        {
            //Poll time
            double rawTime = runService.getTimer().poll();
            double time = rawTime/1000;
            String timeString = Converter.getINSTANCE().secondsToTimeString(time);
            //Update Timer label
            mainTimerLabel.setText(timeString);
            //Update split labels
            //TODO clean up and set all split values
            Label updateSplitTime = (Label) splitLayout.getChildren().get(runService.getRunSplitIndex()*4+3);
            updateSplitTime.setText(timeString);

            Label updateTimeDifference = (Label) splitLayout.getChildren().get(runService.getRunSplitIndex()*4+2);
            Split split = runService.getGameInfo().getSplits().get(runService.getRunSplitIndex());
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
//                //Set Label Name
//                componentNameLabels.get(componentIndex).setText(component.getName());
//                //Set Label Value
//                componentNameLabels.get(componentIndex).setText(component.getValue());
            }
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
        runCountLabel = (Label) infoElements.get(1);
        }catch (ClassCastException | NullPointerException exception){
            exception.printStackTrace();
            System.exit(-1);
        }
        runTitleLabel.setText(runService.getGameInfo().getGameTitle());
        runCountLabel.setText(Integer.toString(runService.getGameInfo().getAttempts()));
    }

    private void initSplitLayout(){
        //TODO limit to 15 items shown
        int rowId = 0;
        Converter converter = Converter.getINSTANCE();
        for(Split split : runService.getGameInfo().getSplits()){
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
            this.splitLayout.addRow(rowId, pic, name, timeSave, pb);
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
            splitTimerLabel = (Label) timerElements.get(0);
        }catch (ClassCastException | NullPointerException exception){
            exception.printStackTrace();
            System.exit(-1);
        }
    }

    private void initComponentLayout(){
        //TODO init components
    }

    /******************** Default Functions ********************/
    public GridPane getSplitLayout() {
        return this.splitLayout;
    }
}//End of UiService Class
