import javafx.scene.control.Alert;

public class AlertGenerator {
    public AlertGenerator(String alertTitle, String alertText, Alert.AlertType alertType) {
            Alert alert = new Alert(alertType);
            alert.setTitle(alertTitle);
            alert.setContentText(alertText);
            alert.setHeaderText(null);
            alert.showAndWait();
    }

    public AlertGenerator(String alertTitle, String headerText, String alertText, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertTitle);
        alert.setHeaderText(headerText);
        alert.setContentText(alertText);
        alert.showAndWait();
    }
}
