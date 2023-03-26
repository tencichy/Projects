package com.damiskot;

import com.damiskot.json.JsonOpen;
import com.damiskot.json.MidiJSON;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application{

    private Stage primaryStage;
    private BorderPane rootLayout;

    private void initGeneratorLayout(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("views/generatorLayout.fxml"));
            AnchorPane generatorLayout = loader.load();

            rootLayout.setCenter(generatorLayout);

            GeneratorController generatorController = loader.getController();
            RootController.generatorController = generatorController;

            if(!getParameters().getRaw().isEmpty()){
                    MidiJSON jsonOpened = new JsonOpen().openFromFile(getParameters().getUnnamed().get(0));
                    generatorController.loadPreset(jsonOpened.getJsonThreads()/*,jsonOpened.getJsonMessages()*/);
            }

        }catch (IOException e) {
            new TextAlertGenerator(e, Alert.AlertType.ERROR);
        }

    }


    private void initRootLayout(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("views/rootLayout.fxml"));
            rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            GeneratorController.fxmlLoader = loader;

            primaryStage.show();
        }catch (IOException e){
           new TextAlertGenerator(e,Alert.AlertType.ERROR);
        }


    }

    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Midi Message Changer");
        this.primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("img/mainIcon-256.png")));
        this.primaryStage.setResizable(false);
        this.primaryStage.sizeToScene();
        this.primaryStage.setOnCloseRequest(event -> System.exit(0));

        RootController.stage = this.primaryStage;

        AboutController.hostServices = getHostServices();
        UsageController.hostServices = getHostServices();

        initRootLayout();

        initGeneratorLayout();

    }

    public static void main(String[] args) { launch(args); }

}
