package com.arcenium.speedruntimer.service;

import com.arcenium.speedruntimer.ArcTime;
import com.arcenium.speedruntimer.config.Colours;
import com.arcenium.speedruntimer.model.ComparisonType;
import com.arcenium.speedruntimer.model.GameInfo;
import com.arcenium.speedruntimer.model.Split;
import com.arcenium.speedruntimer.utility.Converter;
import com.arcenium.speedruntimer.utility.SettingsManager;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
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


    private ContextMenu contextMenu;


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
        view.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        initContextMenu();
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
        mainTimerLabel.setText(Converter.getINSTANCE().secondsToTimeString(0));
        splitTimerLabel.setText(Converter.getINSTANCE().secondsToTimeString(0));
        currentSplitNameLabel.setText(runService.getGameInfo().getSplits().get(0).getName());
        currentSplitPbLabel.setText("PB: "+Converter.getINSTANCE().secondsToTimeString(runService.getGameInfo().getSplits().get(0).getPbTime()));
        currentSplitBestLabel.setText("BEST: "+Converter.getINSTANCE().secondsToTimeString(runService.getGameInfo().getSplits().get(0).getBestTime()));
    }

    private void initComponentLayout(){
        //TODO init components
    }


    /******************** Context Menu Functions ********************/
    private void initContextMenu(){
        // Create the context menu items
        MenuItem editSplits = new MenuItem("Edit");
        editSplits.setOnAction(e-> {
            //Do nothing is run is active
            if(runService.isActive()){
                e.consume();
                return;
            }
            //Open up blank split template
            try{
                Stage stage = initEditSplitStage();
                stage.show();
            }
            catch (IOException exception){
                exception.printStackTrace();
            }
            e.consume();
        });

        MenuItem newSplits = new MenuItem("New");
        newSplits.setOnAction(e-> {
            //Do nothing is run is active
            if(runService.isActive()){
                e.consume();
                return;
            }
            //Open up blank split template
            try{
                Stage stage = initEditSplitStage();
                stage.show();
            }
            catch (IOException exception){
                exception.printStackTrace();
            }
            e.consume();
        });

        MenuItem openSplits = new MenuItem("Open");
        openSplits.setOnAction(e-> {
            //TODO popup to open splits
            System.out.println("Opening GameInfo");
            List<GameInfo> games = SettingsManager.getINSTANCE().getAllGames();
            for(GameInfo game : games){
                System.out.println("game = " + game);
            }
            e.consume();
        });

        MenuItem saveSplits = new MenuItem("Save");
        saveSplits.setOnAction(e-> {
            //TODO save game info
            System.out.println("Saving GameInfo");
            e.consume();
        });

        MenuItem about = new MenuItem("About");
        about.setOnAction(e-> {
            //TODO show about app
            System.out.println("About");
            e.consume();
        });

        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(e-> {
            e.consume();
            System.exit(0);
        });

        MenuItem settings = new MenuItem("Settings");
        settings.setOnAction(e-> {
            //TODO load settings page
            System.out.println("Settings Page Loading");
            e.consume();
        });

        // Create a context menu
        contextMenu = new ContextMenu(editSplits, newSplits, openSplits, saveSplits, about, settings, exit);
        contextMenu.setAutoHide(false);

        // Add the context menu to the entire scene graph.
        view.setOnContextMenuRequested(event-> {
            contextMenu.show(view, event.getScreenX(), event.getScreenY());
            event.consume();
        });

        //Hide Context Menu if clicking outside options
        view.setOnMouseReleased(event->{
            if(event.getButton() == MouseButton.PRIMARY && contextMenu.isShowing()){
                contextMenu.hide();
                event.consume();
            }
        });
    }

    private Stage initEditSplitStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ArcTime.class.getResource("edit-splits.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), SettingsManager.getINSTANCE().getSettings().getEditWindowWidth(), SettingsManager.getINSTANCE().getSettings().getEditWindowHeight());
        Stage stage = new Stage();
        stage.setScene(scene);
        return stage;
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

        //Get split timer
        String splitTimeString = Converter.getINSTANCE().secondsToTimeString(splitTime);

        //Update time difference with comparison if within 10% of time
        Split currentSplitComparison = runService.getReferenceGameInfo().getSplits().get(runService.getRunSplitIndex());
        double timeDifference = runService.getSumOfSplitLengths()+splitTime - runService.getSumOfCurrentComparisonSplits();
        if(runService.getSumOfSplitLengths()+splitTime > runService.getSumOfCurrentComparisonSplits()-15){
            Label updateTimeDifference = (Label) splitLayout.getChildren().get(runService.getRunSplitIndex()*4+1);
            if(timeDifference < 0){
                updateTimeDifference.setText(Converter.getINSTANCE().secondsToTimeString(timeDifference));
                updateTimeDifference.setTextFill(SettingsManager.getINSTANCE().getSettings().getColours().getAheadOfTimeColor());
                splitTimerLabel.setText(splitTimeString);
                splitTimerLabel.setTextFill(SettingsManager.getINSTANCE().getSettings().getColours().getAheadOfTimeColor());

            }
            else{
                updateTimeDifference.setText("+"+Converter.getINSTANCE().secondsToTimeString(timeDifference));
                updateTimeDifference.setTextFill(SettingsManager.getINSTANCE().getSettings().getColours().getBehindTimeColor());
                splitTimerLabel.setText(splitTimeString);
                splitTimerLabel.setTextFill(SettingsManager.getINSTANCE().getSettings().getColours().getBehindTimeColor());
            }
        }
    }

    public void updateTimerArea(){
        Split split = runService.getGameInfo().getSplits().get(runService.getRunSplitIndex());
        currentSplitNameLabel.setText(split.getName());
        currentSplitPbLabel.setText("PB: "+Converter.getINSTANCE().secondsToTimeString(split.getPbTime()));
        currentSplitBestLabel.setText("BEST: "+Converter.getINSTANCE().secondsToTimeString(split.getBestTime()));
    }

    public void updateSplitGrid(){

    }

    public void updateComponentGrid(){

    }

    public void finalizeLastSplit(double runTime){
        //Get split time by subtracting the previous split's end time
        double splitTime = runService.getGameInfo().getSplits().get(runService.getRunSplitIndex()-1).getLength();

        //Update last split time label
        Label updateSplitTime = (Label) splitLayout.getChildren().get((runService.getRunSplitIndex()-1)*4+2);
        updateSplitTime.setText(Converter.getINSTANCE().secondsToTimeString(splitTime));

        //Update last splits total time field
        Label splitTotalTime = (Label) splitLayout.getChildren().get((runService.getRunSplitIndex()-1)*4+3);
        splitTotalTime.setText(Converter.getINSTANCE().secondsToTimeString(runTime/1000));

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
            updateTimeDifference.setTextFill(SettingsManager.getINSTANCE().getSettings().getColours().getAheadOfTimeColor());
        }
        else{
            updateTimeDifference.setText("+"+Converter.getINSTANCE().secondsToTimeString(timeDifference));
            updateTimeDifference.setTextFill(SettingsManager.getINSTANCE().getSettings().getColours().getBehindTimeColor());

        }
    }

    public void finalizeRun(double runTime){
        //Get split time by subtracting the previous split's end time
        double splitTime = runService.getGameInfo().getSplits().get(runService.getNumOfSplits()-1).getLength();

        //Update timer label times
        mainTimerLabel.setText(Converter.getINSTANCE().secondsToTimeString(runTime/1000));
        splitTimerLabel.setText(Converter.getINSTANCE().secondsToTimeString(splitTime));

        //Update last split time label
        Label updateSplitTime = (Label) splitLayout.getChildren().get((runService.getNumOfSplits()-1)*4+2);
        updateSplitTime.setText(Converter.getINSTANCE().secondsToTimeString(splitTime));

        //Update last splits total time field
        Label splitTotalTime = (Label) splitLayout.getChildren().get((runService.getNumOfSplits()-1)*4+3);
        splitTotalTime.setText(Converter.getINSTANCE().secondsToTimeString(runTime/1000));

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

}//End of UiService Class
