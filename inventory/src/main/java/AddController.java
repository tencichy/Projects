import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class AddController {

    static MController controller;
    static Stage stage;
    static User user;

    private Item itemToSave = new Item(0,new Description("","",""),0,0.0,0L);
    private Gson gson = new Gson();

    @FXML
    private Label afterEditLabel;

    @FXML
    private CustomTextField nameField;

    @FXML
    private CustomTextField exNameField;

    @FXML
    private CustomTextField brandField;

    @FXML
    private Spinner<Integer> quantityField;

    @FXML
    private CustomTextField priceField;

    @FXML
    private CustomTextField eanField;

    @FXML
    private void initialize(){
        quantityField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,Integer.MAX_VALUE,0));
        Glyph glyph1 = new Glyph("FontAwesome", FontAwesome.Glyph.TIMES);
        Glyph glyph2 = new Glyph("FontAwesome", FontAwesome.Glyph.TIMES);
        Glyph glyph3 = new Glyph("FontAwesome", FontAwesome.Glyph.TIMES);
        Glyph glyph4 = new Glyph("FontAwesome", FontAwesome.Glyph.TIMES);
        Glyph glyph5 = new Glyph("FontAwesome", FontAwesome.Glyph.TIMES);
        glyph1.setColor(Color.RED);
        glyph2.setColor(Color.RED);
        glyph3.setColor(Color.RED);
        glyph4.setColor(Color.RED);
        glyph5.setColor(Color.RED);
        nameField.setRight(glyph1);
        exNameField.setRight(glyph2);
        brandField.setRight(glyph3);
        priceField.setRight(glyph4);
        eanField.setRight(glyph5);
        nameField.getStyleClass().add("validFields");
        exNameField.getStyleClass().add("validFields");
        brandField.getStyleClass().add("validFields");
        priceField.getStyleClass().add("validFields");
        eanField.getStyleClass().add("validFields");
        afterEditLabel.setText("To add: { " + itemToSave.toString() + " }");
        nameField.textProperty().addListener(((observable, oldValue, newValue) -> {
                if(newValue.trim().isEmpty()){
                    nameField.setRight(glyph1);
                }else{
                    nameField.setRight(new Glyph());
                }
                Description description = itemToSave.getDescription();
                description.setName(newValue);
                itemToSave.setDescription(description);
                afterEditLabel.setText("To add: { " + itemToSave.toString() + " }");

        }));
        exNameField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue.trim().isEmpty()){
                exNameField.setRight(glyph2);
            }else{
                exNameField.setRight(new Glyph());
            }
                Description description = itemToSave.getDescription();
                description.setExtendedName(newValue);
                itemToSave.setDescription(description);
                afterEditLabel.setText("To add: { " + itemToSave.toString() + " }");
        }));
        brandField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue.trim().isEmpty()){
                brandField.setRight(glyph3);
            }else{
                brandField.setRight(new Glyph());
            }
                Description description = itemToSave.getDescription();
                description.setBrand(newValue);
                itemToSave.setDescription(description);
                afterEditLabel.setText("To add: { " + itemToSave.toString() + " }");

        }));
        priceField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue.trim().isEmpty()){
                priceField.setRight(glyph4);
            }else{
                priceField.setRight(new Glyph());
                itemToSave.setPrice(Double.valueOf(newValue));
                afterEditLabel.setText("To add: { " + itemToSave.toString() + " }");
            }
        }));
        eanField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue.trim().isEmpty() || !newValue.matches("\\d*")){
                eanField.setRight(glyph5);
            }else{
                eanField.setRight(new Glyph());
            }
            if(newValue.matches("\\d*") && !newValue.isEmpty()) {
                    itemToSave.setEAN(Long.valueOf(newValue));
                    afterEditLabel.setText("To add: { " + itemToSave.toString() + " }");
            }
        }));
        quantityField.valueProperty().addListener(((observable, oldValue, newValue) -> {
            if(itemToSave != null){
                itemToSave.setQuantity(newValue);
                afterEditLabel.setText("To add: { " + itemToSave.toString() + " }");
            }
        }));
    }

    @FXML
    private void save() {
    if(checkPass(user,"Inventory - Warehouse - Add")){
        if (nameField.textProperty().get().trim().isEmpty() && exNameField.textProperty().get().trim().isEmpty() && brandField.textProperty().get().trim().isEmpty() && priceField.textProperty().get().trim().isEmpty() && eanField.textProperty().get().trim().isEmpty()) {
            new AlertGenerator("Inventory - Warehouse - Add", "Fill all fields!", Alert.AlertType.ERROR);
        } else {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost/inventory?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "inventoryApp", "JaVa!@#$App");
                String q = "select * from items where EAN = ?";
                PreparedStatement p = con.prepareStatement(q);
                p.setLong(1, itemToSave.getEAN());
                ResultSet resultSet = p.executeQuery();
                if (!resultSet.next()) {
                    con.close();
                    try {
                        Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/inventory?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "inventoryApp", "JaVa!@#$App");
                        String query = "insert items(description,quantity,price,EAN) values(?,?,?,?)";
                        PreparedStatement pStatement = connect.prepareStatement(query);
                        pStatement.setString(1, gson.toJson(itemToSave.getDescription()));
                        pStatement.setInt(2, itemToSave.getQuantity());
                        pStatement.setDouble(3, itemToSave.getPrice());
                        pStatement.setLong(4, itemToSave.getEAN());
                        pStatement.executeUpdate();
                        connect.close();
                    } catch (Exception e) {
                        new TextAlertGenerator(e, Alert.AlertType.ERROR);
                    }
                    try {
                        Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/inventory?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "inventoryApp", "JaVa!@#$App");
                        String query = "insert changes(name,surname,PESEL,requestDate,requestTime,requestChange,requestType) values(?,?,?,?,?,?,?)";
                        PreparedStatement pStatement = connect.prepareStatement(query);
                        pStatement.setString(1, user.getName());
                        pStatement.setString(2, user.getSurname());
                        pStatement.setLong(3, user.getPESEL());
                        pStatement.setDate(4, Date.valueOf(LocalDate.now()));
                        pStatement.setTime(5, Time.valueOf(LocalTime.now()));
                        pStatement.setString(6, gson.toJson(new Changes(null, itemToSave)));
                        pStatement.setInt(7, 0);
                        pStatement.executeUpdate();
                        connect.close();
                        controller.downloadData();
                        stage.close();
                    } catch (Exception e) {
                        new TextAlertGenerator(e, Alert.AlertType.ERROR);
                    }
                    Platform.runLater(() -> {
                        nameField.setText("");
                        exNameField.setText("");
                        brandField.setText("");
                        priceField.setText("");
                        eanField.setText("");
                    });
                    itemToSave = new Item(0, new Description("", "", ""), 0, 0.0, 0L);
                    afterEditLabel.setText("To add: { " + itemToSave.toString() + " }");
                } else {
                    new AlertGenerator("Inventory - Warehouse - Add", "Item with EAN: " + itemToSave.getEAN() + ", exists!", Alert.AlertType.ERROR);
                    con.close();
                }
            } catch (Exception e) {
                new TextAlertGenerator(e, Alert.AlertType.ERROR);
            }
        }
    }
    }

    @FXML
    private void cancel(){
        Platform.runLater(() -> {
            nameField.setText("");
            exNameField.setText("");
            brandField.setText("");
            priceField.setText("");
            eanField.setText("");
        });
        itemToSave = new Item(0,new Description("","",""),0,0.0,0L);
        stage.close();
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

}
