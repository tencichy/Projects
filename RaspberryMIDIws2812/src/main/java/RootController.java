import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.*;

public class RootController {

    static MainController mainController;
    private Gson gson = new Gson();

    @FXML
    public void initialize(){

    }

    @FXML
    public void open(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select WS2812 file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("WS2812 files (*.ws2812)","*.ws2812"));
        File file = fileChooser.showOpenDialog(null);
        if(file != null){
            try {
                FileReader reader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(reader);
                ToSave opened = gson.fromJson(bufferedReader.readLine(),ToSave.class);
                mainController.setCommands(opened.getCommands());
                mainController.setLeds(opened.getLedNumber());
            } catch (IOException e) {
                new TextAlertGenerator(e, Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    public void save(){
        if(!mainController.getCommands().isEmpty()){
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select WS2812 file");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("WS2812 files (*.ws2812)","*.ws2812"));
            File file = fileChooser.showSaveDialog(null);
            if(file != null){
                try {
                    FileWriter fileWriter = new FileWriter(file);
                    fileWriter.write(gson.toJson(new ToSave(mainController.getLeds(),mainController.getCommands())));
                    fileWriter.close();
                    MainController.save = true;
                    MainController.lastFile = file.getPath();
                } catch (IOException e) {
                    new TextAlertGenerator(e, Alert.AlertType.ERROR);
                }
            }
        }
    }


}
