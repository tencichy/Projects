package com.damiskot;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AlertGenerator {
    public AlertGenerator(String alertTitle, String alertText, Alert.AlertType alertType) {
            Alert alert = new Alert(alertType);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(getClass().getResourceAsStream("img/warningIcon.png")));
            alert.setTitle(alertTitle);
            alert.setContentText(alertText);
            alert.setHeaderText(null);
            alert.showAndWait();
    }

    public AlertGenerator(String alertTitle, String headerText, String alertText, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("img/warningIcon.png")));
        alert.setTitle(alertTitle);
        alert.setHeaderText(headerText);
        alert.setContentText(alertText);
        alert.showAndWait();
    }
}
