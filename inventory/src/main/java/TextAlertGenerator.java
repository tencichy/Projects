import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.util.Arrays;

public class TextAlertGenerator {
    public TextAlertGenerator(Exception stackTrace, Alert.AlertType type){
        Alert alert = new Alert(type);
        DialogPane dPane = alert.getDialogPane();
        dPane.getStylesheets().add("darkStyle.css");
        dPane.getStyleClass().add("logPane");
        alert.setTitle(type.name().toLowerCase().substring(0,1).toUpperCase() + type.name().substring(1).toLowerCase());
        alert.setHeaderText(stackTrace.getMessage());
        alert.setContentText(stackTrace.getClass().toString());

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(Arrays.toString(stackTrace.getStackTrace()));
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }
}
