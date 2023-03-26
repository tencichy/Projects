import com.google.gson.Gson;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class MController {
    static User user;
    private Stage editStage = new Stage();
    private Stage addStage = new Stage();
    private Gson gson = new Gson();
    private EditController editController;

    @FXML
    private TextField searchTextField;

    @FXML
    private TableView<Item> itemsTable;

    @FXML
    private TableColumn<Item,Integer> idColumn;

    @FXML
    private TableColumn<Item,String> nameColumn;

    @FXML
    private TableColumn<Item,String> exNameColumn;

    @FXML
    private TableColumn<Item,String> brandColumn;

    @FXML
    private TableColumn<Item,Integer> quantityColumn;

    @FXML
    private TableColumn<Item,Double> priceColumn;

    @FXML
    private TableColumn<Item,Long> eanColumn;

    @FXML
    private Label helloLabel;

    @FXML
    private void initialize(){
        helloLabel.setText("Hello, " + user.getName());
        try {
            FXMLLoader loader1 = new FXMLLoader();
            FXMLLoader loader2 = new FXMLLoader();
            loader1.setLocation(MainApp.class.getResource("editLayout.fxml"));
            loader2.setLocation(MainApp.class.getResource("addLayout.fxml"));
            AnchorPane anchorPane = loader1.load();
            anchorPane.getStyleClass().add("anchorPane");
            AnchorPane anchorPane2 = loader2.load();
            anchorPane2.getStyleClass().add("anchorPane");
            editController = loader1.getController();
            Scene scene = new Scene(anchorPane);
            scene.getStylesheets().add("darkStyle.css");
            Scene scene2 = new Scene(anchorPane2);
            scene2.getStylesheets().add("darkStyle.css");
            editStage.setScene(scene);
            editStage.setResizable(false);
            editStage.setTitle("Inventory - Warehouse - Edit");
            editStage.setOnShowing(event -> editController.initAfterShown());
            addStage.setScene(scene2);
            addStage.setResizable(false);
            addStage.setTitle("Inventory - Warehouse - Add");
            EditController.stage = editStage;
            AddController.stage = addStage;
        }catch (IOException e){
            new TextAlertGenerator(e, Alert.AlertType.ERROR);
        }

        idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        eanColumn.setCellValueFactory(new PropertyValueFactory<>("EAN"));
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescription().getName()));
        exNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescription().getExtendedName()));
        brandColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescription().getBrand()));

        downloadData();
    }

    @FXML
    private void editAction(){
        if(itemsTable.getSelectionModel().getSelectedItem() != null){
            editController.setItemToEdit(itemsTable.getSelectionModel().getSelectedItem());
            editStage.show();
        }
    }
    @FXML
    private void addAction(){
        addStage.show();
    }

    @FXML
    private void removeAction(){
        if(itemsTable.getSelectionModel().getSelectedItem() != null){
            if(checkPass(user)) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/inventory?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "inventoryApp", "JaVa!@#$App");
                    String query = "delete from items where ID = ?";
                    PreparedStatement pStatement = connect.prepareStatement(query);
                    pStatement.setInt(1, itemsTable.getSelectionModel().getSelectedItem().getID());
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
                    pStatement.setString(6, gson.toJson(new Changes(itemsTable.getSelectionModel().getSelectedItem(), null)));
                    pStatement.setInt(7, 0);
                    pStatement.executeUpdate();
                    connect.close();
                    downloadData();
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
        dialog.setTitle("Inventory - Warehouse");
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

    void downloadData(){
        ObservableList<Item> items = FXCollections.observableArrayList();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/inventory?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "inventoryApp", "JaVa!@#$App");
            Statement statement = connect.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from items");
            while (resultSet.next()) {
                items.add(new Item(resultSet.getInt("ID"),gson.fromJson(resultSet.getString("description"),Description.class),resultSet.getInt("quantity"),resultSet.getDouble("price"),resultSet.getLong("EAN")));
            }
        }catch (Exception e){
            new TextAlertGenerator(e, Alert.AlertType.ERROR);
        }
        FilteredList<Item> filteredItems = new FilteredList<>(items,p -> true);
        searchTextField.textProperty().addListener(((observable, oldValue, newValue) -> {
            filteredItems.setPredicate(item -> {
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                String lowerCase = newValue.toLowerCase();
                if(item.getDescription().getName().toLowerCase().contains(lowerCase)){
                    return true;
                }else if(item.getDescription().getBrand().toLowerCase().contains(lowerCase)){
                    return true;
                }else if(item.getDescription().getExtendedName().toLowerCase().contains(lowerCase)){
                    return true;
                }else if(String.valueOf(item.getEAN()).contains(lowerCase)){
                    return true;
                }
                return false;
            });

        }));
        SortedList<Item> sortedItem = new SortedList<>(filteredItems);
        sortedItem.comparatorProperty().bind(itemsTable.comparatorProperty());
        itemsTable.setItems(sortedItem);
    }

}
