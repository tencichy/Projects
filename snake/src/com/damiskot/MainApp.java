package com.damiskot;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootPane;

    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Snake");
        this.primaryStage.setResizable(false);
        this.primaryStage.setOnCloseRequest(event -> System.exit(0));

        initRootLayout();

        initMainLayout();

    }

    private void initRootLayout(){
        try {
            FXMLLoader loaderRoot = new FXMLLoader();
            loaderRoot.setLocation(MainApp.class.getResource("views/rootLayout.fxml"));
            rootPane = (BorderPane) loaderRoot.load();

            Scene scene = new Scene(rootPane);

            GameController.gameScene = scene;

            primaryStage.setScene(scene);

            primaryStage.show();
        }catch (IOException e){
            new TextAlertGenerator(e, Alert.AlertType.ERROR);
        }
    }
    private void initMainLayout(){
        try {
            FXMLLoader loaderMain = new FXMLLoader();
            loaderMain.setLocation(MainApp.class.getResource("views/gameLayout.fxml"));
            AnchorPane anchorPane = (AnchorPane) loaderMain.load();
            anchorPane.setStyle("-fx-background-color: black");

            GameController.gamePane = anchorPane;

            rootPane.setCenter(anchorPane);
        }catch (IOException e){
            new TextAlertGenerator(e, Alert.AlertType.ERROR);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}