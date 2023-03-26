import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

public class SController {
    static User user;
    private Gson gson = new Gson();
    private Stage stage = new Stage();

    private ObservableList<ShopEntry> shopEntries = FXCollections.observableArrayList();

    @FXML
    private TextField eanCodeField;

    @FXML
    private Label helloLabel;

    @FXML
    private TableView<Item> productTable;

    @FXML
    private TableColumn<Item,String> productList;

    @FXML
    private TableView<ShopEntry> shoppingListTable;

    @FXML
    private TableColumn<ShopEntry,String> shoppingListColumn;

    @FXML
    private TextField searchField;

    @FXML
    private Spinner<Integer> eanQuantity;

    @FXML
    private Spinner<Integer> searchQuantity;

    @FXML
    private Label sumLabel;

    @FXML
    private void initialize(){
        helloLabel.setText("Hello, " + user.getName());
        try {
            FXMLLoader loader1 = new FXMLLoader();
            loader1.setLocation(MainApp.class.getResource("editEntryLayout.fxml"));
            AnchorPane anchorPane = loader1.load();
            anchorPane.getStyleClass().add("anchorPane");
            Scene scene = new Scene(anchorPane);
            scene.getStylesheets().add("darkStyle.css");
            EntryController entryController = loader1.getController();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Inventory - Shop - Edit");
            stage.setOnShowing((value)->entryController.afterShow());
        }catch (IOException e){
            new TextAlertGenerator(e, Alert.AlertType.ERROR);
        }
        productList.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().toSimpleString()));
        shoppingListColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().toString()));
        eanQuantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,Integer.MAX_VALUE,1));
        searchQuantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,Integer.MAX_VALUE,1));
        downloadData();
        productTable.setRowFactory( tv -> {
            TableRow<Item> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty() && event.getButton().equals(MouseButton.PRIMARY)) {
                    if(row.getItem().getQuantity() != 0) {
                        shopEntries.add(new ShopEntry(row.getItem().getID(), row.getItem().getDescription().getName(), row.getItem().getDescription().getBrand(), row.getItem().getQuantity(), searchQuantity.getValue(), row.getItem().getPrice()));
                    }else {
                        new AlertGenerator("Inventory - Shop","No item in stock", Alert.AlertType.INFORMATION);
                    }
                }
            });
            return row;
        });
        shoppingListTable.setRowFactory( tv -> {
            TableRow<ShopEntry> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty() && event.getButton().equals(MouseButton.PRIMARY)) {
                    EntryController.shopEntry = new ShopEntry(row.getItem().getID(),row.getItem().getName(),row.getItem().getBrand(),row.getItem().getQuantityBefore(),row.getItem().getQuantity(),row.getItem().getPrice());
                    stage.show();
                }
            });
            return row;
        });
        eanCodeField.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
                if(eanCodeField.textProperty().get().matches("\\d*")){
                    try {
                        Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/inventory?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "inventoryApp", "JaVa!@#$App");
                        Statement statement = connect.createStatement();
                        ResultSet resultSet = statement.executeQuery("select * from items");
                        while (resultSet.next()) {
                            if(Long.parseLong(eanCodeField.textProperty().get()) == resultSet.getLong("EAN")){
                                if(resultSet.getInt("quantity") != 0) {
                                    Description description = gson.fromJson(resultSet.getString("description"), Description.class);
                                    shopEntries.add(new ShopEntry(resultSet.getInt("ID"), description.getName(), description.getBrand(), resultSet.getInt("quantity"), eanQuantity.getValue(), resultSet.getDouble("price")));
                                }else{
                                    new AlertGenerator("Inventory - Shop","No item in stock", Alert.AlertType.INFORMATION);
                                }
                            }
                        }
                    }catch (Exception e){
                        new TextAlertGenerator(e, Alert.AlertType.ERROR);
                    }
                    eanCodeField.textProperty().setValue(null);
                }
            }
        });

        shoppingListTable.setItems(shopEntries);

        shopEntries.addListener((ListChangeListener<ShopEntry>) c -> {
            Double sum = 0.0;
            for (ShopEntry i:shopEntries) {
                sum += i.getSumPrice();
            }
            sum = Math.round(sum * 100) / 100.0d;
            sumLabel.setText("Sum: " + sum + " PLN");
        });
    }

    void removeEntry(ShopEntry shopEntry){
        for (int i = 0; i < shopEntries.size(); i++) {
            if(shopEntries.get(i).equals(shopEntry)){
                shopEntries.remove(i);
                break;
            }
        }
        stage.close();
    }

    void updateEntry(ShopEntry shopEntryToRemove,ShopEntry shopEntry){
        for (int i = 0; i < shopEntries.size(); i++) {
            if(shopEntries.get(i).equals(shopEntryToRemove)){
                shopEntries.remove(i);
                break;
            }
        }
        shopEntries.add(shopEntry);
        stage.close();
    }

    private boolean checkPass(User user){
        boolean returnVal = false;
        final String[] pass = new String[1];
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Inventory - Shop");
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
    private void checkoutAction() {
        if (!shopEntries.isEmpty()) {
        if(checkPass(user)) {
            try {
                Double sum = 0.0;
                for (ShopEntry i : shopEntries) {
                    sum += i.getSumPrice();
                }
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/inventory?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "inventoryApp", "JaVa!@#$App");
                String query = "insert changes(name,surname,PESEL,requestDate,requestTime,requestChange,requestType) values(?,?,?,?,?,?,?)";
                PreparedStatement pStatement = connect.prepareStatement(query);
                pStatement.setString(1, user.getName());
                pStatement.setString(2, user.getSurname());
                pStatement.setLong(3, user.getPESEL());
                pStatement.setDate(4, Date.valueOf(LocalDate.now()));
                pStatement.setTime(5, Time.valueOf(LocalTime.now()));
                pStatement.setString(6, gson.toJson(new ShopChanges(new ArrayList<>(shopEntries), Math.round(sum * 100) / 100.0d)));
                pStatement.setInt(7, 1);
                pStatement.executeUpdate();
                connect.close();
                for (ShopEntry e : shopEntries) {
                    updateQuery(e);
                }
            } catch (Exception e) {
                new TextAlertGenerator(e, Alert.AlertType.ERROR);
            }

            Platform.runLater(() -> shopEntries.clear());
        }
    }
    }

    private void updateQuery(ShopEntry entry){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/inventory?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "inventoryApp", "JaVa!@#$App");
            String query = "update items set quantity = ? where ID = ?";
            PreparedStatement pStatement = connect.prepareStatement(query);
            pStatement.setInt(1,entry.getQuantityBefore() - entry.getQuantity());
            pStatement.setInt(2,entry.getID());
            pStatement.executeUpdate();
            connect.close();
        }catch (Exception e){
            new TextAlertGenerator(e, Alert.AlertType.ERROR);
        }
    }

    private void downloadData(){
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
        FilteredList<Item> filteredItems = new FilteredList<>(items, p -> true);
        searchField.textProperty().addListener(((observable, oldValue, newValue) -> {
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
        sortedItem.comparatorProperty().bind(productTable.comparatorProperty());
        productTable.setItems(sortedItem);
    }
}
