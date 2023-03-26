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
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;
import java.util.Optional;

public class AController {

    private Stage addUserStage = new Stage();
    private Stage editUserStage = new Stage();
    private Stage incomeStage = new Stage();
    private Gson gson = new Gson();
    static User user;

    @FXML
    private TableView<User> userList;

    @FXML
    private TableColumn<User,String> userColumn;

    @FXML
    private TextField userSearch;

    @FXML
    private TableView<ChangeEntry> changesTable;

    @FXML
    private TableColumn<ChangeEntry,String> changesNameColumn;

    @FXML
    private TableColumn<ChangeEntry,String> changesSurnameColumn;

    @FXML
    private TableColumn<ChangeEntry,String> changesPeselColumn;

    @FXML
    private TableColumn<ChangeEntry,String> changesRTimeColumn;

    @FXML
    private TableColumn<ChangeEntry,String> changesRDateColumn;

    @FXML
    private TextField changesSearchField;

    @FXML
    private TextArea changesText;

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
    private TextField itemsSearchField;

    @FXML
    private DatePicker incomeFrom;

    @FXML
    private DatePicker incomeTo;

    @FXML
    private void initialize(){
        try {
            FXMLLoader loader1 = new FXMLLoader();
            loader1.setLocation(MainApp.class.getResource("addUserLayout.fxml"));
            AnchorPane anchorPane = loader1.load();
            anchorPane.getStyleClass().add("anchorPane");
            Scene scene = new Scene(anchorPane);
            scene.getStylesheets().add("darkStyle.css");
            addUserStage.setScene(scene);
            addUserStage.setResizable(false);
            addUserStage.setTitle("Inventory - Admin - Add user");
            AddUserController.stage = addUserStage;

            FXMLLoader loader2 = new FXMLLoader();
            loader2.setLocation(MainApp.class.getResource("editUserLayout.fxml"));
            AnchorPane anchorPane2 = loader2.load();
            anchorPane2.getStyleClass().add("anchorPane");
            Scene scene2 = new Scene(anchorPane2);
            scene2.getStylesheets().add("darkStyle.css");
            EditUserController editUserController = loader2.getController();
            editUserStage.setScene(scene2);
            editUserStage.setResizable(false);
            editUserStage.setTitle("Inventory - Admin - Edit user");
            editUserStage.setOnShowing(event -> editUserController.afterShow());
            EditUserController.stage = editUserStage;

            FXMLLoader loader3 = new FXMLLoader();
            loader3.setLocation(MainApp.class.getResource("incomeLayout.fxml"));
            AnchorPane anchorPane3 = loader3.load();
            anchorPane3.getStyleClass().add("anchorPane");
            Scene scene3 = new Scene(anchorPane3);
            scene3.getStylesheets().add("darkStyle.css");
            IncomeController incomeController = loader3.getController();
            incomeStage.setScene(scene3);
            incomeStage.setResizable(false);
            incomeStage.setTitle("Inventory - Admin - Income");
            incomeStage.setOnShowing(event -> incomeController.afterShow());
        }catch (IOException e){
            new TextAlertGenerator(e, Alert.AlertType.ERROR);
        }
        userSearch.getStyleClass().add("logFields");
        itemsSearchField.getStyleClass().add("logFields");
        changesSearchField.getStyleClass().add("logFields");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        eanColumn.setCellValueFactory(new PropertyValueFactory<>("EAN"));
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescription().getName()));
        exNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescription().getExtendedName()));
        brandColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescription().getBrand()));

        changesNameColumn.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getName()));
        changesSurnameColumn.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getSurname()));
        changesPeselColumn.setCellValueFactory(value -> new SimpleStringProperty(String.valueOf(value.getValue().getPESEL())));
        changesRDateColumn.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getRequestDate().toString()));
        changesRTimeColumn.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getRequestTime().toString()));
        userColumn.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().toString()));
        changesTable.setRowFactory( tv -> {
            TableRow<ChangeEntry> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton().equals(MouseButton.PRIMARY)) {
                    if(row.getItem().getRequestType() == 0){
                        if(row.getItem().getRequestChangeChanges().getItemBefore() == null && row.getItem().getRequestChangeChanges().getItemAfter() != null){
                            changesText.setText("Added new item: { " + row.getItem().getRequestChangeChanges().getItemAfter().toString() + " }");
                        }else if(row.getItem().getRequestChangeChanges().getItemBefore() != null && row.getItem().getRequestChangeChanges().getItemAfter() == null){
                            changesText.setText("Deleted item: { " + row.getItem().getRequestChangeChanges().getItemBefore() + " }");
                        }else if(row.getItem().getRequestChangeChanges().getItemBefore() != null && row.getItem().getRequestChangeChanges().getItemAfter() != null){
                            Item itemBefore = row.getItem().getRequestChangeChanges().getItemBefore();
                            Item itemAfter = row.getItem().getRequestChangeChanges().getItemAfter();
                            changesText.clear();
                            if(!Objects.equals(itemAfter.getDescription().getName(), itemBefore.getDescription().getName())){
                                changesText.appendText("ID: " + itemAfter.getID() + " - change item name from: " + itemBefore.getDescription().getName() + " to: " + itemAfter.getDescription().getName() + "\n");
                            }
                            if(!Objects.equals(itemAfter.getDescription().getExtendedName(), itemBefore.getDescription().getExtendedName())){
                                changesText.appendText("ID: " + itemAfter.getID() + " - change item extended name from: " + itemBefore.getDescription().getExtendedName() + " to: " + itemAfter.getDescription().getExtendedName() + "\n");
                            }
                            if(!Objects.equals(itemAfter.getDescription().getBrand(), itemBefore.getDescription().getBrand())){
                                changesText.appendText("ID: " + itemAfter.getID() + " - change item brand from: " + itemBefore.getDescription().getBrand() + " to: " + itemAfter.getDescription().getBrand() + "\n");
                            }
                            if(!Objects.equals(itemAfter.getQuantity(), itemBefore.getQuantity())){
                                changesText.appendText("ID: " + itemAfter.getID() + " - change item quantity from: " + itemBefore.getQuantity() + " to: " + itemAfter.getQuantity() + "\n");
                            }
                            if(!Objects.equals(itemAfter.getPrice(), itemBefore.getPrice())){
                                changesText.appendText("ID: " + itemAfter.getID() + " - change item price from: " + itemBefore.getPrice() + " to: " + itemAfter.getPrice() + "\n");
                            }
                            if(!Objects.equals(itemAfter.getEAN(), itemBefore.getEAN())){
                                changesText.appendText("ID: " + itemAfter.getID() + " - change item EAN from: " + itemBefore.getEAN() + " to: " + itemAfter.getEAN() + "\n");
                            }
                        }
                    }else if(row.getItem().getRequestType() == 1){
                        changesText.setText(row.getItem().getRequestChangeShop().toString());
                    }

                }
            });
            return row;
        });
        getUsers();
        getChanges();
        getItems();
    }

    @FXML
    private void addUser(){
        addUserStage.show();
    }

    @FXML
    private void removeUser(){
        if(userList.getSelectionModel().getSelectedItem() != null){
        if(checkPass(user)){
            try {
                Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/inventory?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "inventoryApp", "JaVa!@#$App");
                String query = "delete from users where ID = ?";
                PreparedStatement pStatement = connect.prepareStatement(query);
                pStatement.setInt(1,userList.getSelectionModel().getSelectedItem().getID());
                pStatement.executeUpdate();
                connect.close();
            } catch (Exception e) {
                new TextAlertGenerator(e, Alert.AlertType.ERROR);
            }
            getUsers();
        }
        }
    }

    @FXML
    private void editUser(){
        if(userList.getSelectionModel().getSelectedItem() != null) {
            EditUserController.userToEdit = userList.getSelectionModel().getSelectedItem();
            editUserStage.show();
        }
    }

    @FXML
    private void removeRequest(){
        if(changesTable.getSelectionModel().getSelectedItem() != null){
        if(checkPass(user)) {
            try {
                Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/inventory?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "inventoryApp", "JaVa!@#$App");
                String query = "delete from changes where ID = ?";
                PreparedStatement pStatement = connect.prepareStatement(query);
                pStatement.setInt(1, changesTable.getSelectionModel().getSelectedItem().getID());
                pStatement.executeUpdate();
                connect.close();
            } catch (Exception e) {
                new TextAlertGenerator(e, Alert.AlertType.ERROR);
            }
            changesText.setText("");
            getChanges();
        }
        }
    }

    @FXML
    private void clearRequests(){
    if(checkPass(user)) {
        try {
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/inventory?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "inventoryApp", "JaVa!@#$App");
            String query = "truncate table changes";
            PreparedStatement pStatement = connect.prepareStatement(query);
            pStatement.executeUpdate();
            connect.close();
        } catch (Exception e) {
            new TextAlertGenerator(e, Alert.AlertType.ERROR);
        }
        changesText.setText("");
        getChanges();
    }
    }

    @FXML
    private void showIncome(){
        if(incomeFrom.getValue() != null && incomeTo.getValue() == null) {
            IncomeController.from = Date.valueOf(incomeFrom.getValue());
            incomeStage.show();
        }
        if(incomeTo.getValue() != null && incomeFrom.getValue() == null) {
            IncomeController.to = Date.valueOf(incomeTo.getValue());
            incomeStage.show();
        }
        if(incomeTo.getValue() != null && incomeFrom.getValue() != null){
            IncomeController.from = Date.valueOf(incomeFrom.getValue());
            IncomeController.to = Date.valueOf(incomeTo.getValue());
            incomeStage.show();
        }

    }

    private boolean checkPass(User user){
        boolean returnVal = false;
        final String[] pass = new String[1];
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Inventory - Admin");
        dialog.setHeaderText("Enter your password to confirm operation");
        DialogPane dPane = dialog.getDialogPane();
        dPane.getStylesheets().add("darkStyle.css");
        dPane.getStyleClass().add("logPane");
        ButtonType confirmButtonType = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);
        PasswordField password = new PasswordField();
        Node confirmButton = dialog.getDialogPane().lookupButton(confirmButtonType);
        confirmButton.setDisable(true);
        password.textProperty().addListener((observable, oldValue, newValue) -> confirmButton.setDisable(newValue.trim().isEmpty()));
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

    private void getItems(){
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
        itemsSearchField.textProperty().addListener(((observable, oldValue, newValue) -> {
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

    void getUsers(){
        ObservableList<User> users = FXCollections.observableArrayList();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/inventory?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "inventoryApp", "JaVa!@#$App");
            Statement statement = connect.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from users");
            while (resultSet.next()) {
                users.add(new User(resultSet.getInt("ID"),resultSet.getString("login"),resultSet.getString("pass"),resultSet.getInt("accountType"),resultSet.getString("name"),resultSet.getString("surname"),resultSet.getLong("PESEL")));
            }
        }catch (Exception e){
            new TextAlertGenerator(e, Alert.AlertType.ERROR);
        }
        FilteredList<User> filteredItems = new FilteredList<>(users, p -> true);
        userSearch.textProperty().addListener(((observable, oldValue, newValue) -> {
            filteredItems.setPredicate(item -> {
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                String lowerCase = newValue.toLowerCase();
                if(item.getName().toLowerCase().contains(lowerCase)){
                    return true;
                }else if(item.getSurname().toLowerCase().contains(lowerCase)){
                    return true;
                }else if(String.valueOf(item.getPESEL()).contains(lowerCase)){
                    return true;
                }
                return false;
            });

        }));
        SortedList<User> sortedItem = new SortedList<>(filteredItems);
        sortedItem.comparatorProperty().bind(userList.comparatorProperty());
        userList.setItems(sortedItem);
    }

    private void getChanges(){
        ObservableList<ChangeEntry> changes = FXCollections.observableArrayList();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/inventory?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "inventoryApp", "JaVa!@#$App");
            Statement statement = connect.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from changes");
            while (resultSet.next()) {
                if(resultSet.getInt("requestType") == 0){
                    changes.add(new ChangeEntry(resultSet.getInt("ID"),resultSet.getString("name"),resultSet.getString("surname"),resultSet.getInt("PESEL"),resultSet.getDate("requestDate"),resultSet.getTime("requestTime"),gson.fromJson(resultSet.getString("requestChange"),Changes.class),resultSet.getInt("requestType")));
                }else if(resultSet.getInt("requestType") == 1){
                    changes.add(new ChangeEntry(resultSet.getInt("ID"),resultSet.getString("name"),resultSet.getString("surname"),resultSet.getInt("PESEL"),resultSet.getDate("requestDate"),resultSet.getTime("requestTime"),gson.fromJson(resultSet.getString("requestChange"),ShopChanges.class),resultSet.getInt("requestType")));
                }
            }
        }catch (Exception e){
            new TextAlertGenerator(e, Alert.AlertType.ERROR);
        }
        FilteredList<ChangeEntry> filteredItems = new FilteredList<>(changes, p -> true);
        changesSearchField.textProperty().addListener(((observable, oldValue, newValue) -> {
            filteredItems.setPredicate(item -> {
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                String lowerCase = newValue.toLowerCase();
                if(item.getName().toLowerCase().contains(lowerCase)){
                    return true;
                }else if(item.getSurname().toLowerCase().contains(lowerCase)){
                    return true;
                }else if(String.valueOf(item.getPESEL()).contains(lowerCase)){
                    return true;
                }
                return false;
            });

        }));
        SortedList<ChangeEntry> sortedItem = new SortedList<>(filteredItems);
        sortedItem.comparatorProperty().bind(changesTable.comparatorProperty());
        changesTable.setItems(sortedItem);
    }

}
