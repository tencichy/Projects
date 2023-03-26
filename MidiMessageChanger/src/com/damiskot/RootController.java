package com.damiskot;

import com.damiskot.json.JsonOpen;
import com.damiskot.json.JsonSave;
import com.damiskot.json.JsonThread;
import com.damiskot.json.MidiJSON;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RootController {

    static List<JsonThread> JsonThreads = new ArrayList<>();

    //static List<JsonMessage> jsonMessages = new ArrayList<>();

    static GeneratorController generatorController;

    static Stage stage;

    private Stage aboutStage = new Stage();

    private Stage helpStage = new Stage();

    @FXML
    private Menu fileMenu;

    public RootController(){}

    @FXML
    private void initialize(){


        try {
            FXMLLoader loader = new FXMLLoader();
            FXMLLoader loader1 = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("views/aboutLayout.fxml"));
            loader1.setLocation(MainApp.class.getResource("views/usageLayout.fxml"));
            AnchorPane generatorLayout = (AnchorPane) loader.load();
            AnchorPane helpLayout = (AnchorPane) loader1.load();
            Scene scene = new Scene(generatorLayout);
            Scene scene1 = new Scene(helpLayout);
            aboutStage.setScene(scene);
            aboutStage.setResizable(false);
            aboutStage.getIcons().add(new Image(getClass().getResourceAsStream("img/aboutIcon.png")));
            helpStage.setScene(scene1);
            helpStage.setResizable(false);
            helpStage.getIcons().add(new Image(getClass().getResourceAsStream("img/helpIcon.png")));
        }catch (IOException e){
            new TextAlertGenerator(e, Alert.AlertType.ERROR);
        }
        fileMenu.setDisable(true);
    }

    @FXML
    private void handleAbout(){
        aboutStage.show();
    }

    @FXML
    private void handleHowToUse(){
        helpStage.show();
    }

    @FXML
    private void handleSaveFile(){
        new JsonSave(new MidiJSON(JsonThreads),stage);
    }

    @FXML
    private void handleOpenFile(){
        MidiJSON midiJSON = new JsonOpen().open(stage);
        if(midiJSON != null) {
            generatorController.loadPreset(midiJSON.getJsonThreads());
        }
    }

    void setFileMenuDisable(boolean value){
        fileMenu.setDisable(value);
    }

}
