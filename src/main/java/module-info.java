module com.arcenium.speedruntimer {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires jnativehook;
    requires com.fasterxml.jackson.databind;


    opens com.arcenium.speedruntimer to javafx.fxml;
    exports com.arcenium.speedruntimer;
    exports com.arcenium.speedruntimer.controller;
    opens com.arcenium.speedruntimer.controller to javafx.fxml;
}