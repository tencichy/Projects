import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;

public class AlertGenerator {
    public AlertGenerator(String alertTitle, String alertText, Alert.AlertType alertType) {
            Alert alert = new Alert(alertType);
            DialogPane dPane = alert.getDialogPane();
            dPane.getStylesheets().add("darkStyle.css");
            dPane.getStyleClass().add("logPane");
            alert.setTitle(alertTitle);
            alert.setContentText(alertText);
            alert.setHeaderText(null);
            alert.showAndWait();
    }

    public AlertGenerator(String alertTitle, String headerText, String alertText, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        DialogPane dPane = alert.getDialogPane();
        dPane.getStylesheets().add("darkStyle.css");
        dPane.getStyleClass().add("logPane");
        alert.setTitle(alertTitle);
        alert.setHeaderText(headerText);
        alert.setContentText(alertText);
        alert.showAndWait();
    }
}
