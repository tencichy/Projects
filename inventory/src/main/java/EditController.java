import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class EditController {

    static MController controller;
    static Stage stage;
    static User user;

    private Item itemToEdit;
    private Item itemToSave;
    private Gson gson = new Gson();

    @FXML
    private Label beforeEditLabel;

    @FXML
    private Label afterEditLabel;

    @FXML
    private TextField nameField;

    @FXML
    private TextField exNameField;

    @FXML
    private TextField brandField;

    @FXML
    private Spinner<Integer> quantityField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField eanField;

    void setItemToEdit(Item itemToEdit) {
        this.itemToEdit = itemToEdit;
    }

    @FXML
    private void initialize(){
        nameField.getStyleClass().add("validFields");
        exNameField.getStyleClass().add("validFields");
        brandField.getStyleClass().add("validFields");
        priceField.getStyleClass().add("validFields");
        eanField.getStyleClass().add("validFields");

        nameField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue.trim().isEmpty()){
                Description description = itemToSave.getDescription();
                description.setName(nameField.getPromptText());
                itemToSave.setDescription(description);
                afterEditLabel.setText("To save: { " + itemToSave.toString() + " }");
            }else {
                Description description = itemToSave.getDescription();
                description.setName(newValue);
                itemToSave.setDescription(description);
                afterEditLabel.setText("To save: { " + itemToSave.toString() + " }");
            }
        }));
        exNameField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue.trim().isEmpty()){
                Description description = itemToSave.getDescription();
                description.setExtendedName(exNameField.getPromptText());
                itemToSave.setDescription(description);
                afterEditLabel.setText("To save: { " + itemToSave.toString() + " }");
            }else {
                Description description = itemToSave.getDescription();
                description.setExtendedName(newValue);
                itemToSave.setDescription(description);
                afterEditLabel.setText("To save: { " + itemToSave.toString() + " }");
            }
        }));
        brandField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue.trim().isEmpty()){
                Description description = itemToSave.getDescription();
                description.setBrand(brandField.getPromptText());
                itemToSave.setDescription(description);
                afterEditLabel.setText("To save: { " + itemToSave.toString() + " }");
            }else {
                Description description = itemToSave.getDescription();
                description.setBrand(newValue);
                itemToSave.setDescription(description);
                afterEditLabel.setText("To save: { " + itemToSave.toString() + " }");
            }
        }));
        priceField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue.trim().isEmpty()){
                itemToSave.setPrice(Double.valueOf(priceField.getPromptText()));
                afterEditLabel.setText("To save: { " + itemToSave.toString() + " }");
            }else {
                itemToSave.setPrice(Double.valueOf(newValue));
                afterEditLabel.setText("To save: { " + itemToSave.toString() + " }");
            }
        }));
        eanField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue.trim().isEmpty()) {
                itemToSave.setEAN(Long.valueOf(eanField.getPromptText()));
                afterEditLabel.setText("To save: { " + itemToSave.toString() + " }");
            } else {
                if(newValue.matches("\\d*")) {
                    itemToSave.setEAN(Long.valueOf(newValue));
                    afterEditLabel.setText("To save: { " + itemToSave.toString() + " }");
                }
            }
        }));
        quantityField.valueProperty().addListener(((observable, oldValue, newValue) -> {
            if(itemToSave != null){
                itemToSave.setQuantity(newValue);
                afterEditLabel.setText("To save: { " + itemToSave.toString() + " }");
            }
        }));
    }

    void initAfterShown(){
        itemToSave = new Item(itemToEdit.getID(),new Description(itemToEdit.getDescription().getName(),itemToEdit.getDescription().getExtendedName(),itemToEdit.getDescription().getBrand()),itemToEdit.getQuantity(),itemToEdit.getPrice(),itemToEdit.getEAN());
        beforeEditLabel.setText("To edit: { " + itemToEdit.toString() + " }");
        nameField.setPromptText(itemToEdit.getDescription().getName());
        exNameField.setPromptText(itemToEdit.getDescription().getExtendedName());
        brandField.setPromptText(itemToEdit.getDescription().getBrand());
        quantityField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,Integer.MAX_VALUE,itemToEdit.getQuantity()));
        priceField.setPromptText(String.valueOf(itemToEdit.getPrice()));
        eanField.setPromptText(String.valueOf(itemToEdit.getEAN()));
        afterEditLabel.setText("To save: { " + itemToSave.toString() + " }");
    }

    @FXML
    private void save(){
    if(checkPass(user)) {
        if (itemToSave.equals(itemToEdit)) {
            stage.close();
        } else {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/inventory?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "inventoryApp", "JaVa!@#$App");
                String query = "update items set description = ?, quantity = ?, price = ?, EAN = ? where ID = ?";
                PreparedStatement pStatement = connect.prepareStatement(query);
                pStatement.setString(1, gson.toJson(itemToSave.getDescription()));
                pStatement.setInt(2, itemToSave.getQuantity());
                pStatement.setDouble(3, itemToSave.getPrice());
                pStatement.setLong(4, itemToSave.getEAN());
                pStatement.setInt(5, itemToSave.getID());
                pStatement.executeUpdate();
                connect.close();
            } catch (Exception e) {
                new TextAlertGenerator(e, Alert.AlertType.ERROR);
            }
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/inventory?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "inventoryApp", "JaVa!@#$App");
                String query = "insert changes(name,surname,PESEL,requestDate,requestTime,requestChange,requestType) values(?,?,?,?,?,?,?)";
                PreparedStatement pStatement = connect.prepareStatement(query);
                pStatement.setString(1, user.getName());
                pStatement.setString(2, user.getSurname());
                pStatement.setLong(3, user.getPESEL());
                pStatement.setDate(4, Date.valueOf(LocalDate.now()));
                pStatement.setTime(5, Time.valueOf(LocalTime.now()));
                pStatement.setString(6, gson.toJson(new Changes(itemToEdit, itemToSave)));
                pStatement.setInt(7, 0);
                pStatement.executeUpdate();
                connect.close();
                controller.downloadData();
                stage.close();
            } catch (Exception e) {
                new TextAlertGenerator(e, Alert.AlertType.ERROR);
            }

        }
    }
    }

    private boolean checkPass(User user){
        boolean returnVal = false;
        final String[] pass = new String[1];
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Inventory - Warehouse - Edit");
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
