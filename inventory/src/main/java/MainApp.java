import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainApp extends Application {

    private Stage primaryStage;
    private User user;
    private AtomicBoolean logged = new AtomicBoolean(false);


    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        this.primaryStage.setResizable(false);
        this.primaryStage.setOnCloseRequest(event -> {
            removeFromActiveUsers(user);
            System.exit(0);
        });

        login();
        if(!logged.get()){
            System.exit(0);
        }
        if(user.getAccountType() == 0){
            EditUserController.user = user;
            AddUserController.user = user;
            AController.user = user;
            initALayout();
            this.primaryStage.setTitle("Inventory - Admin");
        }else if(user.getAccountType() == 1) {
            EditController.user = user;
            AddController.user = user;
            MController.user = user;
            initMLayout();
            this.primaryStage.setTitle("Inventory - Warehouse");
        }else if(user.getAccountType() == 2){
            SController.user = user;
            initSLayout();
            this.primaryStage.setTitle("Inventory - Shop");
        }
    }

    private void login(){
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Inventory");
        dialog.setHeaderText("Login to Inventory System");
        dialog.setGraphic(new ImageView(MainApp.class.getResource("login.png").toString()));
        DialogPane dPane = dialog.getDialogPane();
        dPane.getStylesheets().add("darkStyle.css");
        dPane.getStyleClass().add("logPane");
        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        TextField username = new TextField();
        PasswordField password = new PasswordField();
        Glyph userGlyph = new Glyph("FontAwesome", FontAwesome.Glyph.USER);
        Glyph lockGlyph = new Glyph("FontAwesome",FontAwesome.Glyph.LOCK);
        userGlyph.setColor(Color.WHITESMOKE);
        lockGlyph.setColor(Color.WHITESMOKE);
        userGlyph.setFontSize(30);
        lockGlyph.setFontSize(30);
        grid.add(userGlyph, 0, 0);
        grid.add(username, 1, 0);
        grid.add(lockGlyph, 0, 1);
        grid.add(password, 1, 1);
        Button loginButton = (Button)dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR,14));
        Button cancelButton = (Button)dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancelButton.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR,14));
        loginButton.getStyleClass().add("logButton");
        loginButton.setDisable(true);
        cancelButton.getStyleClass().add("logButton");
        username.textProperty().addListener(((observable, oldValue, newValue) -> {
            loginButton.setDisable(!(!newValue.trim().isEmpty() && !password.getText().trim().isEmpty()));
        }));
        password.textProperty().addListener(((observable, oldValue, newValue) -> {
            loginButton.setDisable(!(!newValue.trim().isEmpty() && !username.getText().trim().isEmpty()));
        }));
        dialog.getDialogPane().setContent(grid);
        Platform.runLater(username::requestFocus);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });
        Optional<Pair<String, String>> result = dialog.showAndWait();
        result.ifPresent(usernamePassword -> {
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/inventory?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","inventoryApp","JaVa!@#$App");
                Statement statement = connect.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from users");
                while (resultSet.next()){
                    if(usernamePassword.getKey().equals(resultSet.getString("login")) && usernamePassword.getValue().equals(resultSet.getString("pass"))){
                        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/inventory?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","inventoryApp","JaVa!@#$App");
                        Statement state = conn.createStatement();
                        ResultSet rSet = state.executeQuery("select * from activeusers");
                        boolean active = false;
                        while (rSet.next()){
                            if(usernamePassword.getKey().equals(rSet.getString("login")) && usernamePassword.getValue().equals(rSet.getString("pass"))){
                                active = true;
                            }
                        }
                        if(!active){
                            user = new User(resultSet.getInt("ID"),resultSet.getString("login"),resultSet.getString("pass"),resultSet.getInt("accountType"),resultSet.getString("name"),resultSet.getString("surname"),resultSet.getLong("PESEL"));
                            addToActiveUsers(user);
                            logged.set(true);
                        }
                    }
                }
                if(!logged.get()){
                    connect.close();
                    login();
                }
            }catch (Exception e){
                new TextAlertGenerator(e, Alert.AlertType.ERROR);
            }
        });
    }

    private void addToActiveUsers(User user){
    try {
        Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/inventory?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "inventoryApp", "JaVa!@#$App");
        String query = "insert activeusers(login,pass) values(?,?)";
        PreparedStatement pStatement = connect.prepareStatement(query);
        pStatement.setString(1, user.getLogin());
        pStatement.setString(2, user.getPassword());
        pStatement.executeUpdate();
        connect.close();
    } catch (Exception e) {
        new TextAlertGenerator(e, Alert.AlertType.ERROR);
    }
    }

    private void removeFromActiveUsers(User user){
        Integer ID = -1;
        try {
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/inventory?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "inventoryApp", "JaVa!@#$App");
            String query = "select * from activeusers where login = ? and pass = ?";
            PreparedStatement pStatement = connect.prepareStatement(query);
            pStatement.setString(1, user.getLogin());
            pStatement.setString(2, user.getPassword());
            ResultSet resultSet = pStatement.executeQuery();
            if(resultSet.next()){
                ID = resultSet.getInt("ID");
            }else{
                new AlertGenerator("Inventory","Something went wrong", Alert.AlertType.WARNING);
            }
            connect.close();
        } catch (Exception e) {
            new TextAlertGenerator(e, Alert.AlertType.ERROR);
        }
        try {
            if(ID >= 0) {
                Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/inventory?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "inventoryApp", "JaVa!@#$App");
                String query = "delete from activeusers where ID = ?";
                PreparedStatement pStatement = connect.prepareStatement(query);
                pStatement.setInt(1, ID);
                pStatement.executeUpdate();
                connect.close();
            }else{
                new AlertGenerator("Inventory","Something went wrong", Alert.AlertType.WARNING);
            }
        }catch (Exception e){
            new TextAlertGenerator(e, Alert.AlertType.ERROR);
        }
    }

    private void initSLayout(){
        try {
            FXMLLoader loaderMain = new FXMLLoader();
            loaderMain.setLocation(MainApp.class.getResource("sLayout.fxml"));
            AnchorPane anchorPane = loaderMain.load();
            anchorPane.getStyleClass().add("anchorPane");

            EntryController.sController = loaderMain.getController();
            Scene scene = new Scene(anchorPane);
            scene.getStylesheets().add("darkStyle.css");

            primaryStage.setScene(scene);

            primaryStage.show();
        }catch (IOException e){
            new TextAlertGenerator(e, Alert.AlertType.ERROR);
        }
    }

    private void initALayout(){
        try {
            FXMLLoader loaderMain = new FXMLLoader();
            loaderMain.setLocation(MainApp.class.getResource("aLayout.fxml"));
            AnchorPane anchorPane = loaderMain.load();
            anchorPane.getStyleClass().add("anchorPane");

            AddUserController.aController = loaderMain.getController();
            Scene scene = new Scene(anchorPane);
            scene.getStylesheets().add("darkStyle.css");

            primaryStage.setScene(scene);

            primaryStage.show();
        }catch (IOException e){
            new TextAlertGenerator(e, Alert.AlertType.ERROR);
        }
    }

    private void initMLayout(){
        try {
            FXMLLoader loaderMain = new FXMLLoader();
            loaderMain.setLocation(MainApp.class.getResource("mLayout.fxml"));
            AnchorPane anchorPane = loaderMain.load();
            anchorPane.getStyleClass().add("anchorPane");

            EditController.controller = loaderMain.getController();
            AddController.controller = loaderMain.getController();

            Scene scene = new Scene(anchorPane);
            scene.getStylesheets().add("darkStyle.css");

            primaryStage.setScene(scene);

            primaryStage.show();
        }catch (IOException e){
            new TextAlertGenerator(e, Alert.AlertType.ERROR);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
