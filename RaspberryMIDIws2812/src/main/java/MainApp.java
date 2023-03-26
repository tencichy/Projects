import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane root;

    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("WS2812 Python Generator");
        this.primaryStage.setResizable(false);
        this.primaryStage.setOnCloseRequest(event -> System.exit(0));

        initRootLayout();

        initMainLayout();

    }

    private void initMainLayout(){
        try {
            FXMLLoader loaderMain = new FXMLLoader();
            loaderMain.setLocation(MainApp.class.getResource("mainLayout.fxml"));
            AnchorPane anchorPane = loaderMain.load();
            RootController.mainController = loaderMain.getController();
            root.setCenter(anchorPane);
            primaryStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void initRootLayout(){
        try {
            FXMLLoader loaderMain = new FXMLLoader();
            loaderMain.setLocation(MainApp.class.getResource("rootLayout.fxml"));
            BorderPane borderPane = loaderMain.load();
            root = borderPane;
            Scene scene = new Scene(borderPane);
            primaryStage.setScene(scene);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}