import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.CustomPasswordField;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Optional;

public class EditUserController {

    static User userToEdit;
    static Stage stage;
    private User userToSave;
    static User user;


    @FXML
    private TextField loginField;

    @FXML
    private CustomPasswordField oldPasswordField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private CustomPasswordField repeatNewPasswordField;

    @FXML
    private Spinner<Integer> accountTypeField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField peselField;

    @FXML
    private Label afterEditLabel;

    @FXML
    private Label beforeEditLabel;

    @FXML
    private void initialize(){
        loginField.getStyleClass().add("validFields");
        oldPasswordField.getStyleClass().add("validFields");
        newPasswordField.getStyleClass().add("validFields");
        repeatNewPasswordField.getStyleClass().add("validFields");
        nameField.getStyleClass().add("validFields");
        surnameField.getStyleClass().add("validFields");
        peselField.getStyleClass().add("validFields");
        Glyph glyph1 = new Glyph("FontAwesome", FontAwesome.Glyph.TIMES);
        Glyph glyph2 = new Glyph("FontAwesome", FontAwesome.Glyph.TIMES);
        glyph1.setColor(Color.RED);
        glyph2.setColor(Color.RED);
        oldPasswordField.setRight(glyph1);
        oldPasswordField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(oldPasswordField.textProperty().get().trim().isEmpty() || !oldPasswordField.textProperty().get().equals(userToEdit.getPassword())){
                oldPasswordField.setRight(glyph1);
            }else{
                oldPasswordField.setRight(new Glyph());
            }
        }));
        newPasswordField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(!repeatNewPasswordField.textProperty().get().trim().isEmpty()){
                if(!newPasswordField.textProperty().get().trim().isEmpty()){
                    if(repeatNewPasswordField.textProperty().get().equals(newPasswordField.textProperty().get())){
                        repeatNewPasswordField.setRight(new Glyph());
                        userToSave.setPassword(newPasswordField.textProperty().get());
                    }else {
                        repeatNewPasswordField.setRight(glyph2);
                    }
                }else{
                    repeatNewPasswordField.setRight(new Glyph());
                }
            }else if(!newValue.trim().isEmpty()){
                repeatNewPasswordField.setRight(glyph2);
            }else{
                repeatNewPasswordField.setRight(new Glyph());
            }
        }));
        repeatNewPasswordField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(!newValue.trim().isEmpty()){
                if(!newPasswordField.textProperty().get().trim().isEmpty()){
                    if(repeatNewPasswordField.textProperty().get().equals(newPasswordField.textProperty().get())){
                        repeatNewPasswordField.setRight(new Glyph());
                        userToSave.setPassword(newPasswordField.textProperty().get());
                    }else {
                        repeatNewPasswordField.setRight(glyph2);
                    }
                }else{
                    repeatNewPasswordField.setRight(new Glyph());
                }
            }
        }));
        loginField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue.trim().isEmpty()){
                userToSave.setLogin(loginField.getPromptText());
                afterEditLabel.setText("To save: { " + userToSave.toFullString() + " }");
            }else {
                userToSave.setLogin(loginField.textProperty().get());
                afterEditLabel.setText("To save: { " + userToSave.toFullString() + " }");
            }
        }));
        nameField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue.trim().isEmpty()){
                userToSave.setName(nameField.getPromptText());
                afterEditLabel.setText("To save: { " + userToSave.toFullString() + " }");
            }else {
                userToSave.setName(nameField.textProperty().get());
                afterEditLabel.setText("To save: { " + userToSave.toFullString() + " }");
            }
        }));
        surnameField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue.trim().isEmpty()){
                userToSave.setSurname(surnameField.getPromptText());
                afterEditLabel.setText("To save: { " + userToSave.toFullString() + " }");
            }else {
                userToSave.setSurname(surnameField.textProperty().get());
                afterEditLabel.setText("To save: { " + userToSave.toFullString() + " }");
            }
        }));
        peselField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue.trim().isEmpty()){
                userToSave.setPESEL(Long.valueOf(peselField.getPromptText()));
                afterEditLabel.setText("To save: { " + userToSave.toFullString() + " }");
            }else {
                if(newValue.matches("\\d*")) {
                    userToSave.setPESEL(Long.valueOf(peselField.textProperty().get()));
                    afterEditLabel.setText("To save: { " + userToSave.toFullString() + " }");
                }
            }
        }));
        accountTypeField.valueProperty().addListener(((observable, oldValue, newValue) -> {
            userToSave.setAccountType(newValue);
            afterEditLabel.setText("To save: { " + userToSave.toFullString() + " }");
        }));
    }

    @FXML
    private void save(){
    if(checkPass(user)) {
        if (userToSave.equals(userToEdit)) {
            stage.close();
        } else {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/inventory?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "inventoryApp", "JaVa!@#$App");
                String query = "update users set login = ?, pass = ?, accountType = ?, name = ?, surname = ?, PESEL = ? where ID = ?";
                PreparedStatement pStatement = connect.prepareStatement(query);
                pStatement.setString(1, userToSave.getLogin());
                pStatement.setString(2, userToSave.getPassword());
                pStatement.setInt(3, userToSave.getAccountType());
                pStatement.setString(4, userToSave.getName());
                pStatement.setString(5, userToSave.getSurname());
                pStatement.setLong(6, userToSave.getPESEL());
                pStatement.setInt(7, userToSave.getID());
                pStatement.executeUpdate();
                connect.close();
                stage.close();
            } catch (Exception e) {
                new TextAlertGenerator(e, Alert.AlertType.ERROR);
            }
        }
    }
    }

    void afterShow(){
        beforeEditLabel.setText("To edit: { " + userToEdit.toFullString() + " }");
        userToSave = new User(userToEdit.getID(),userToEdit.getLogin(),userToEdit.getPassword(),userToEdit.getAccountType(),userToEdit.getName(),userToEdit.getSurname(),userToEdit.getPESEL());
        loginField.setPromptText(userToEdit.getLogin());
        accountTypeField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,2,userToEdit.getAccountType()));
        nameField.setPromptText(userToEdit.getName());
        surnameField.setPromptText(userToEdit.getSurname());
        peselField.setPromptText(String.valueOf(userToEdit.getPESEL()));
        afterEditLabel.setText("To save: { " + userToSave.toFullString() + " }");
    }

    private boolean checkPass(User user){
        boolean returnVal = false;
        final String[] pass = new String[1];
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Inventory - Admin - Edit user");
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
