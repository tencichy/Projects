import com.google.gson.Gson;
import ets.EtsData;
import ets.Truck;
import eu.hansolo.medusa.Gauge;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBoxTreeItem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class DebugGaugesController {

    static ArrayList<CheckBoxTreeItem<?>> checkedItems;
    static URL telemetryUrl;
    static Integer timeInterval;

    private Gson gson = new Gson();
    private Timer timer;

    @FXML
    private Gauge speed;

    @FXML
    private Gauge rpm;

    @FXML
    private Gauge fuel;

    @FXML
    private Gauge waterTemperature;

    @FXML
    private Gauge airPressure;

    @FXML
    private Gauge oilTemperature;

    @FXML
    private Gauge oilPressure;

    @FXML
    private Gauge batteryVoltage;

    public void initialize(){

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
                        etsData = gson.fromJson(json, EtsData.class);
                        for (CheckBoxTreeItem<?> item: checkedItems) {
                            if(item.getParent().getValue().toString().equals("truck")){
                                switch (item.getValue().toString()) {
                                    case "speed":
                                        speed.setValue((Double) Truck.class.getDeclaredField(item.getValue().toString()).get(etsData.getTruck()));
                                        break;
                                    case "engineRpm":
                                        rpm.setValue((Double) Truck.class.getDeclaredField(item.getValue().toString()).get(etsData.getTruck()) / 100);
                                        break;
                                    case "fuel":
                                        fuel.setValue(map(Math.round((Double) Truck.class.getDeclaredField(item.getValue().toString()).get(etsData.getTruck())), 0, Math.round((Double) Truck.class.getDeclaredField("fuelCapacity").get(etsData.getTruck())), 0, 100));
                                        break;
                                    case "waterTemperature":
                                        waterTemperature.setValue((Double) Truck.class.getDeclaredField(item.getValue().toString()).get(etsData.getTruck()));
                                        break;
                                    case "fuelWarningOn":
                                        fuel.setLedOn((boolean)Truck.class.getDeclaredField(item.getValue().toString()).get(etsData.getTruck()));
                                        break;
                                    case "waterTemperatureWarningOn":
                                        waterTemperature.setLedOn((boolean)Truck.class.getDeclaredField(item.getValue().toString()).get(etsData.getTruck()));
                                        break;
                                    case "airPressure":
                                        airPressure.setValue((Double) Truck.class.getDeclaredField(item.getValue().toString()).get(etsData.getTruck()));
                                        break;
                                    case "airPressureWarningOn":
                                        airPressure.setLedOn((boolean)Truck.class.getDeclaredField(item.getValue().toString()).get(etsData.getTruck()));
                                        break;
                                    case "oilTemperature":
                                        oilTemperature.setValue((Double) Truck.class.getDeclaredField(item.getValue().toString()).get(etsData.getTruck()));
                                        break;
                                    case "oilPressure":
                                        oilPressure.setValue((Double) Truck.class.getDeclaredField(item.getValue().toString()).get(etsData.getTruck()));
                                        break;
                                    case "oilPressureWarningOn":
                                        oilPressure.setLedOn((boolean) Truck.class.getDeclaredField(item.getValue().toString()).get(etsData.getTruck()));
                                        break;
                                    case "batteryVoltage":
                                        batteryVoltage.setValue((Double) Truck.class.getDeclaredField(item.getValue().toString()).get(etsData.getTruck()));
                                        break;
                                    case "batteryVoltageWarningOn":
                                        batteryVoltage.setLedOn((boolean) Truck.class.getDeclaredField(item.getValue().toString()).get(etsData.getTruck()));
                                        break;
                                }
                            }
                        }
                    }
                    in.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        timer.schedule(task,0,timeInterval);
    }

    private long map(long x, long in_min, long in_max, long out_min, long out_max)
    {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }


}
