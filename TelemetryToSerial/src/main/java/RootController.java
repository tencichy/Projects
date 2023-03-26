import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class RootController {

    static MainController mainController;

    static Stage stage;

    public void initialize(){

    }

    public void handleOpen(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select preference to load");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TTS preference file (*.ttsp)","*.ttsp"));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File file = fileChooser.showOpenDialog(stage);
        if(file != null){
            mainController.setDataToOpen(readJson(file.getPath()));
        }
    }

    public void handleSave(){
        String data = mainController.getDataToSave();
        if(data != null){
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save preferences");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TTS preference file (*.ttsp)","*.ttsp"));
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            File file = fileChooser.showSaveDialog(stage);
            if(file != null){
                try {
                    FileWriter fileWriter = new FileWriter(file);
                    fileWriter.write(data);
                    fileWriter.close();
                }catch (IOException e){
                    new TextAlertGenerator(e, Alert.AlertType.ERROR);
                }
            }
        }
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
