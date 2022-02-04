package com.arcenium.speedruntimer;

import com.arcenium.speedruntimer.utility.SettingsManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class ArcTime extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //Init the settings manager
        SettingsManager.getINSTANCE();

        FXMLLoader fxmlLoader = new FXMLLoader(ArcTime.class.getResource("main-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), SettingsManager.getINSTANCE().getSettings().getWindowWidth(), SettingsManager.getINSTANCE().getSettings().getWindowHeight());
        stage.setTitle("Arc Time");
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(windowEvent -> {
            Platform.exit();
            System.exit(0);
        });
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    public static void main(String[] args) {
        launch();
    }
}//End of ArcTime Class