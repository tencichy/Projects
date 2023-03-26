package com.damiskot.json;

import com.damiskot.TextAlertGenerator;
import com.google.gson.Gson;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JsonSave {

    public JsonSave(MidiJSON saveValue,Stage stage){

        Gson gson = new Gson();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save preset");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MMC preset file (*.mmcp)","*.mmcp"));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File file = fileChooser.showSaveDialog(stage);
        String json = gson.toJson(saveValue);
        if(file != null) {
            try {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(json);
                fileWriter.close();
            } catch (IOException e) {
                new TextAlertGenerator(e, Alert.AlertType.ERROR);
            }
        }

    }

}
