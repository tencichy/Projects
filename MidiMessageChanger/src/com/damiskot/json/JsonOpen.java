package com.damiskot.json;

import com.damiskot.AlertGenerator;
import com.damiskot.TextAlertGenerator;
import com.google.gson.Gson;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class JsonOpen {

    public MidiJSON open(Stage stage){
        Gson gson = new Gson();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select preset to load");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MMC preset file (*.mmcp)","*.mmcp"));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File file = fileChooser.showOpenDialog(stage);
        if(file != null) {
            String input = readJson(file.getPath());
            return gson.fromJson(input, MidiJSON.class);
        }else {
            return null;
        }

    }

    public MidiJSON openFromFile(String path){
        Gson gson = new Gson();
        return gson.fromJson(readJson(path),MidiJSON.class);
    }


    private static String readJson(String path) {
        StringBuilder builder = new StringBuilder();
        String text;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            while ((text = reader.readLine()) != null) {
                builder.append(text);
            }
        } catch (IOException e) {
            new TextAlertGenerator(e, Alert.AlertType.ERROR);
        }
        return builder.toString();
    }

}
