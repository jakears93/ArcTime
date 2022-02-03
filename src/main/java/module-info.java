module com.arcenium.speedruntimer {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires jnativehook;
    requires com.fasterxml.jackson.databind;
    requires commons.math3;
    requires java.logging;


    opens com.arcenium.speedruntimer to javafx.fxml;
    exports com.arcenium.speedruntimer;
    exports com.arcenium.speedruntimer.controller;
    exports com.arcenium.speedruntimer.model;
    opens com.arcenium.speedruntimer.controller to javafx.fxml;
}