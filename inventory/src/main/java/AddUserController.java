import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.CustomPasswordField;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Optional;

public class AddUserController {

    static Stage stage;
    static AController aController;
    static User user;

    @FXML
    private CustomTextField loginField;

    @FXML
    private CustomPasswordField passwordField;

    @FXML
    private CustomPasswordField repeatPasswordField;

    @FXML
    private Spinner<Integer> accountTypeField;

    @FXML
    private CustomTextField nameField;

    @FXML
    private CustomTextField surnameField;

    @FXML
    private CustomTextField peselField;

    @FXML
    private void initialize(){
        accountTypeField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,2,0));
        Glyph glyph1 = new Glyph("FontAwesome", FontAwesome.Glyph.TIMES);
        Glyph glyph2 = new Glyph("FontAwesome", FontAwesome.Glyph.TIMES);
        Glyph glyph3 = new Glyph("FontAwesome", FontAwesome.Glyph.TIMES);
        Glyph glyph4 = new Glyph("FontAwesome", FontAwesome.Glyph.TIMES);
        Glyph glyph5 = new Glyph("FontAwesome", FontAwesome.Glyph.TIMES);
        Glyph glyph6 = new Glyph("FontAwesome", FontAwesome.Glyph.TIMES);
        glyph1.setColor(Color.RED);
        glyph2.setColor(Color.RED);
        glyph3.setColor(Color.RED);
        glyph4.setColor(Color.RED);
        glyph5.setColor(Color.RED);
        glyph6.setColor(Color.RED);
        loginField.setRight(glyph1);
        passwordField.setRight(glyph2);
        repeatPasswordField.setRight(glyph3);
        nameField.setRight(glyph4);
        surnameField.setRight(glyph5);
        peselField.setRight(glyph6);
        loginField.getStyleClass().add("validFields");
        passwordField.getStyleClass().add("validFields");
        repeatPasswordField.getStyleClass().add("validFields");
        nameField.getStyleClass().add("validFields");
        surnameField.getStyleClass().add("validFields");
        peselField.getStyleClass().add("validFields");
        loginField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue.trim().isEmpty()){
                loginField.setRight(glyph1);
            }else{
                loginField.setRight(new Glyph());
            }
        }));
        passwordField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue.trim().isEmpty()){
                passwordField.setRight(glyph2);
            }else{
                passwordField.setRight(new Glyph());
            }
        }));
        repeatPasswordField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue.trim().isEmpty() || !repeatPasswordField.textProperty().get().equals(passwordField.textProperty().get())){
                repeatPasswordField.setRight(glyph3);
            }else{
                repeatPasswordField.setRight(new Glyph());
            }
        }));
        nameField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue.trim().isEmpty()){
                nameField.setRight(glyph4);
            }else{
                nameField.setRight(new Glyph());
            }
        }));
        surnameField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue.trim().isEmpty()){
                surnameField.setRight(glyph5);
            }else{
                surnameField.setRight(new Glyph());
            }
        }));
        peselField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue.trim().isEmpty() || !newValue.matches("\\d*")){
                peselField.setRight(glyph6);
            }else{
                peselField.setRight(new Glyph());
            }
        }));
    }

    @FXML
    private void addUser(){
        if (loginField.textProperty().get().trim().isEmpty() && passwordField.textProperty().get().trim().isEmpty() && repeatPasswordField.textProperty().get().trim().isEmpty() &&
                nameField.textProperty().get().trim().isEmpty() && surnameField.textProperty().get().trim().isEmpty() && peselField.textProperty().get().trim().isEmpty() && peselField.textProperty().get().matches("\\d*")) {
            new AlertGenerator("Inventory - Admin - Add user", "Fill all fields!", Alert.AlertType.ERROR);
        } else {
            if(checkPass(user,"Inventory - Admin - Add user")) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/inventory?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "inventoryApp", "JaVa!@#$App");
                    String query = "insert users(login,pass,accountType,name,surname,PESEL) values(?,?,?,?,?,?)";
                    PreparedStatement pStatement = connect.prepareStatement(query);
                    pStatement.setString(1, loginField.textProperty().get());
                    pStatement.setString(2, passwordField.textProperty().get());
                    pStatement.setInt(3, accountTypeField.getValue());
                    pStatement.setString(4, nameField.textProperty().get());
                    pStatement.setString(5, surnameField.textProperty().get());
                    pStatement.setLong(6, Long.valueOf(peselField.textProperty().get()));
                    pStatement.executeUpdate();
                    connect.close();
                    stage.close();
                } catch (Exception e) {
                    new TextAlertGenerator(e, Alert.AlertType.ERROR);
                }
                aController.getUsers();
            }
        }

    }

    private boolean checkPass(User user,String title){
        boolean returnVal = false;
        final String[] pass = new String[1];
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText("Enter your password to confirm operation");
        DialogPane dPane = dialog.getDialogPane();
        dPane.getStylesheets().add("darkStyle.css");
        dPane.getStyleClass().add("logPane");
        ButtonType confirmButtonType = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);
        PasswordField password = new PasswordField();
        Node confirmButton = dialog.getDialogPane().lookupButton(confirmButtonType);
        confirmButton.setDisable(true);
        password.textProperty().addListener((observable, oldValue, newValue) -> {
            confirmButton.setDisable(newValue.trim().isEmpty());
        });
        AnchorPane pane = new AnchorPane();
        pane.getChildren().add(password);
        AnchorPane.setTopAnchor(password,10d);
        AnchorPane.setLeftAnchor(password,10d);
        AnchorPane.setRightAnchor(password,10d);
        dialog.getDialogPane().setContent(pane);
        dialog.setResultConverter(dialogButton -> {
            if(dialogButton == confirmButtonType){
                return password.getText();
            }
            return null;
        });
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(value -> pass[0] = value);
        if(pass[0] != null && pass[0].equals(user.getPassword())){
            returnVal = true;
        }
        return returnVal;
    }

    @FXML
    private void cancel(){
        stage.close();
    }

}
