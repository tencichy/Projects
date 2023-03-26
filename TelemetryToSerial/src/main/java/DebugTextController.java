import com.google.gson.Gson;
import ets.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class DebugTextController {

    static ArrayList<CheckBoxTreeItem<?>> checkedItems;
    static URL telemetryUrl;
    static Integer timeInterval;

    private Gson gson = new Gson();
    private Timer timer;
    private Stage debugGaugesStage;

    @FXML
    private TextArea debugText;

    @FXML
    private Button showGauges;

    public void initialize(){
        debugText.setEditable(false);
        try {
            FXMLLoader loaderMain = new FXMLLoader();
            loaderMain.setLocation(MainApp.class.getResource("debugGaugesLayout.fxml"));
            AnchorPane anchorPane = loaderMain.load();
            DebugGaugesController debugGaugesController = loaderMain.getController();
            debugGaugesStage = new Stage();
            debugGaugesStage.setScene(new Scene(anchorPane));
            debugGaugesStage.setOnShowing(event -> debugGaugesController.start());
            debugGaugesStage.setOnCloseRequest(event -> debugGaugesController.stopTimer());
            debugGaugesStage.setTitle("TTS - Debug Gauges");
        }catch (IOException e){
            e.printStackTrace();
        }
        showGauges.setOnMouseClicked(event -> {
            DebugGaugesController.checkedItems = new ArrayList<>(checkedItems);
            DebugGaugesController.telemetryUrl = telemetryUrl;
            DebugGaugesController.timeInterval = timeInterval;
            debugGaugesStage.show();
        });
    }

    void stopTimer(){
        timer.cancel();
        timer.purge();
    }

    void start(){
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try{
                    BufferedReader in = new BufferedReader(new InputStreamReader(telemetryUrl.openStream()));
                    String json;
                    EtsData etsData;
                    while ((json = in.readLine()) != null){
                        debugText.clear();
                        etsData = gson.fromJson(json, EtsData.class);
                        StringBuilder builder = new StringBuilder();
                        for (CheckBoxTreeItem<?> item: checkedItems) {
                            if(item.getParent().getValue().toString().equals("game")){
                                builder.append(item.getValue().toString()).append(": ").append(Game.class.getDeclaredField(item.getValue().toString()).get(etsData.getGame())).append("\n");
                            }else if(item.getParent().getValue().toString().equals("truck")){
                                builder.append(item.getValue().toString()).append(": ").append(Truck.class.getDeclaredField(item.getValue().toString()).get(etsData.getTruck())).append("\n");
                            }else if(item.getParent().getValue().toString().equals("trailer")){
                                builder.append(item.getValue().toString()).append(": ").append(Trailer.class.getDeclaredField(item.getValue().toString()).get(etsData.getTrailer())).append("\n");
                            }else if(item.getParent().getValue().toString().equals("job")){
                                builder.append(item.getValue().toString()).append(": ").append(Job.class.getDeclaredField(item.getValue().toString()).get(etsData.getJob())).append("\n");
                            }else if(item.getParent().getValue().toString().equals("navigation")){
                                builder.append(item.getValue().toString()).append(": ").append(Navigation.class.getDeclaredField(item.getValue().toString()).get(etsData.getNavigation())).append("\n");
                            }
                        }
                        Platform.runLater(()->debugText.setText(builder.toString()));
                    }
                    in.close();
                }catch (Exception e){
                    new TextAlertGenerator(e, Alert.AlertType.ERROR);
                }
            }
        };
        timer.schedule(task,0,timeInterval);
    }

}
