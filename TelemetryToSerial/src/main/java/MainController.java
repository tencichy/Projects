import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import ets.*;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class MainController {

    private static final int[] baudrateArray = {300,1200,2400,4800,9600,19200,38400,57600,74880,115200,250000,500000,1000000,2000000};
    private static final int[] databitsArray = {SerialPort.DATABITS_5,SerialPort.DATABITS_6,SerialPort.DATABITS_7,SerialPort.DATABITS_8};

    private static boolean connectedToTelemetry = false;
    private static boolean connectedToSerial = false;

    private static URL telemetryUrl = null;

    private OutputStream outputStream;

    private static final ArrayList<CheckBoxTreeItem<?>> checkedItems = new ArrayList<>();

    private final Gson gson = new Gson();

    private Stage debugTextStage;

    @FXML
    private ComboBox<String> serialPorts;

    @FXML
    private Button scanPort;

    @FXML
    private ComboBox<Integer> baudrate;

    @FXML
    private ComboBox<Integer> databits;

    @FXML
    private ComboBox<String> stopbits;

    @FXML
    private ComboBox<String> parity;

    @FXML
    private Button connectToSerial;

    @FXML
    private TextArea telemetryApiUrl;

    @FXML
    private Button connectToTelemetry;

    @FXML
    private Button startService;

    @FXML
    private TreeView<String> selectJson;

    @FXML
    private RadioButton dataFormatJson;

    @FXML
    private RadioButton dataFormatMessagePack;

    @FXML
    private Spinner<Integer> timeInterval;

    @FXML
    private Label ppsLabel;

    @FXML
    private Button showDebugData;

    public void initialize(){
        //TODO: add MessagePack support
        dataFormatMessagePack.setDisable(true);
        dataFormatJson.setSelected(true);
        dataFormatJson.setDisable(true);

        showDebugData.setDisable(true);

        timeInterval.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(10,500,50,5));
        timeInterval.setEditable(true);
        timeInterval.valueProperty().addListener((obs,oldValue,newValue)-> ppsLabel.setText(String.valueOf(1000/newValue)));

        scanSerial();
        scanPort.setOnMouseClicked(mouseEvent -> scanSerial());
        for (int i : baudrateArray) {
            baudrate.getItems().add(i);
        }
        for (int i : databitsArray) {
            databits.getItems().add(i);
        }
        stopbits.getItems().add("1");
        stopbits.getItems().add("2");
        stopbits.getItems().add("1.5");
        parity.getItems().add("None");
        parity.getItems().add("Odd");
        parity.getItems().add("Even");
        parity.getItems().add("Mark");
        parity.getItems().add("Space");

        connectToSerial.setOnMouseClicked(event -> connectToSerialDevice());
        connectToTelemetry.setOnMouseClicked(event -> connectToTelemetry());
        startService.setOnMouseClicked(event -> startService());
        showDebugData.setOnMouseClicked(event -> {
            findCheckItems();
            DebugTextController.checkedItems = new ArrayList<>(checkedItems);
            DebugTextController.timeInterval = timeInterval.getValue();
            debugTextStage.show();
        });

        selectJson.setCellFactory(new Callback<>() {
            @Override
            public TreeCell<String> call(TreeView<String> param) {
                return new CheckBoxTreeCell<>(){
                    @Override
                    public void updateItem(String item, boolean empty){
                        super.updateItem(item, empty);
                        if(empty){
                            setGraphic(null);
                            setText(null);
                        }else if (!(getTreeItem() instanceof CheckBoxTreeItem)){
                            setGraphic(null);
                        }
                    }
                };
            }
        });

        try {
            FXMLLoader loaderMain = new FXMLLoader();
            loaderMain.setLocation(MainApp.class.getResource("debugTextLayout.fxml"));
            AnchorPane anchorPane = loaderMain.load();
            DebugTextController debugTextController = loaderMain.getController();
            debugTextStage = new Stage();
            debugTextStage.setScene(new Scene(anchorPane));
            debugTextStage.setOnShowing(event -> debugTextController.start());
            debugTextStage.setOnCloseRequest(event -> debugTextController.stopTimer());
            debugTextStage.setTitle("TTS - Text Debug");
        }catch (IOException e){
            e.printStackTrace();
        }

        TreeItem<String> object = new TreeItem<>("object");
        TreeItem<String> game = new TreeItem<>("game");
        game.getChildren().add(new CheckBoxTreeItem<>("connected"));
        game.getChildren().add(new CheckBoxTreeItem<>("gameName"));
        game.getChildren().add(new CheckBoxTreeItem<>("paused"));
        game.getChildren().add(new CheckBoxTreeItem<>("time"));
        game.getChildren().add(new CheckBoxTreeItem<>("timeScale"));
        game.getChildren().add(new CheckBoxTreeItem<>("nextRestStopTime"));
        game.getChildren().add(new CheckBoxTreeItem<>("version"));
        game.getChildren().add(new CheckBoxTreeItem<>("telemetryPluginVersion"));
        object.getChildren().add(game);
        TreeItem<String> truck = new TreeItem<>("truck");
        truck.getChildren().add(new CheckBoxTreeItem<>("id"));
        truck.getChildren().add(new CheckBoxTreeItem<>("make"));
        truck.getChildren().add(new CheckBoxTreeItem<>("model"));
        truck.getChildren().add(new CheckBoxTreeItem<>("speed"));
        truck.getChildren().add(new CheckBoxTreeItem<>("cruiseControlSpeed"));
        truck.getChildren().add(new CheckBoxTreeItem<>("cruiseControlOn"));
        truck.getChildren().add(new CheckBoxTreeItem<>("odometer"));
        truck.getChildren().add(new CheckBoxTreeItem<>("gear"));
        truck.getChildren().add(new CheckBoxTreeItem<>("displayedGear"));
        truck.getChildren().add(new CheckBoxTreeItem<>("forwardGears"));
        truck.getChildren().add(new CheckBoxTreeItem<>("reverseGears"));
        truck.getChildren().add(new CheckBoxTreeItem<>("shifterType"));
        truck.getChildren().add(new CheckBoxTreeItem<>("engineRpm"));
        truck.getChildren().add(new CheckBoxTreeItem<>("engineRpmMax"));
        truck.getChildren().add(new CheckBoxTreeItem<>("fuel"));
        truck.getChildren().add(new CheckBoxTreeItem<>("fuelCapacity"));
        truck.getChildren().add(new CheckBoxTreeItem<>("fuelAverageConsumption"));
        truck.getChildren().add(new CheckBoxTreeItem<>("fuelWarningFactor"));
        truck.getChildren().add(new CheckBoxTreeItem<>("fuelWarningOn"));
        truck.getChildren().add(new CheckBoxTreeItem<>("wearEngine"));
        truck.getChildren().add(new CheckBoxTreeItem<>("wearTransmission"));
        truck.getChildren().add(new CheckBoxTreeItem<>("wearCabin"));
        truck.getChildren().add(new CheckBoxTreeItem<>("wearChassis"));
        truck.getChildren().add(new CheckBoxTreeItem<>("wearWheels"));
        truck.getChildren().add(new CheckBoxTreeItem<>("userSteer"));
        truck.getChildren().add(new CheckBoxTreeItem<>("userThrottle"));
        truck.getChildren().add(new CheckBoxTreeItem<>("userBrake"));
        truck.getChildren().add(new CheckBoxTreeItem<>("userClutch"));
        truck.getChildren().add(new CheckBoxTreeItem<>("gameSteer"));
        truck.getChildren().add(new CheckBoxTreeItem<>("gameThrottle"));
        truck.getChildren().add(new CheckBoxTreeItem<>("gameBrake"));
        truck.getChildren().add(new CheckBoxTreeItem<>("gameClutch"));
        truck.getChildren().add(new CheckBoxTreeItem<>("shifterSlot"));
        truck.getChildren().add(new CheckBoxTreeItem<>("engineOn"));
        truck.getChildren().add(new CheckBoxTreeItem<>("electricOn"));
        truck.getChildren().add(new CheckBoxTreeItem<>("wipersOn"));
        truck.getChildren().add(new CheckBoxTreeItem<>("retarderBrake"));
        truck.getChildren().add(new CheckBoxTreeItem<>("retarderStepCount"));
        truck.getChildren().add(new CheckBoxTreeItem<>("parkBrakeOn"));
        truck.getChildren().add(new CheckBoxTreeItem<>("motorBrakeOn"));
        truck.getChildren().add(new CheckBoxTreeItem<>("brakeTemperature"));
        truck.getChildren().add(new CheckBoxTreeItem<>("adblue"));
        truck.getChildren().add(new CheckBoxTreeItem<>("adblueCapacity"));
        truck.getChildren().add(new CheckBoxTreeItem<>("adblueAverageConsumption"));
        truck.getChildren().add(new CheckBoxTreeItem<>("adblueWarningOn"));
        truck.getChildren().add(new CheckBoxTreeItem<>("airPressure"));
        truck.getChildren().add(new CheckBoxTreeItem<>("airPressureWarningOn"));
        truck.getChildren().add(new CheckBoxTreeItem<>("airPressureWarningValue"));
        truck.getChildren().add(new CheckBoxTreeItem<>("airPressureEmergencyOn"));
        truck.getChildren().add(new CheckBoxTreeItem<>("airPressureEmergencyValue"));
        truck.getChildren().add(new CheckBoxTreeItem<>("oilTemperature"));
        truck.getChildren().add(new CheckBoxTreeItem<>("oilPressure"));
        truck.getChildren().add(new CheckBoxTreeItem<>("oilPressureWarningOn"));
        truck.getChildren().add(new CheckBoxTreeItem<>("oilPressureWarningValue"));
        truck.getChildren().add(new CheckBoxTreeItem<>("waterTemperature"));
        truck.getChildren().add(new CheckBoxTreeItem<>("waterTemperatureWarningOn"));
        truck.getChildren().add(new CheckBoxTreeItem<>("waterTemperatureWarningTemperature"));
        truck.getChildren().add(new CheckBoxTreeItem<>("batteryVoltage"));
        truck.getChildren().add(new CheckBoxTreeItem<>("batteryVoltageWarningOn"));
        truck.getChildren().add(new CheckBoxTreeItem<>("batteryVoltageWarningValue"));
        truck.getChildren().add(new CheckBoxTreeItem<>("lightsDashboardOn"));
        truck.getChildren().add(new CheckBoxTreeItem<>("blinkerLeftActive"));
        truck.getChildren().add(new CheckBoxTreeItem<>("blinkerRightActive"));
        truck.getChildren().add(new CheckBoxTreeItem<>("blinkerLeftOn"));
        truck.getChildren().add(new CheckBoxTreeItem<>("blinkerRightOn"));
        truck.getChildren().add(new CheckBoxTreeItem<>("lightsParkingOn"));
        truck.getChildren().add(new CheckBoxTreeItem<>("lightsBeamLowOn"));
        truck.getChildren().add(new CheckBoxTreeItem<>("lightsBeamHighOn"));
        truck.getChildren().add(new CheckBoxTreeItem<>("lightsAuxFrontOn"));
        truck.getChildren().add(new CheckBoxTreeItem<>("lightsAuxRoofOn"));
        truck.getChildren().add(new CheckBoxTreeItem<>("lightsBeaconOn"));
        truck.getChildren().add(new CheckBoxTreeItem<>("lightsBrakeOn"));
        truck.getChildren().add(new CheckBoxTreeItem<>("lightsReverseOn"));
        object.getChildren().add(truck);
        TreeItem<String> trailer = new TreeItem<>("trailer");
        trailer.getChildren().add(new CheckBoxTreeItem<>("attached"));
        trailer.getChildren().add(new CheckBoxTreeItem<>("id"));
        trailer.getChildren().add(new CheckBoxTreeItem<>("name"));
        trailer.getChildren().add(new CheckBoxTreeItem<>("mass"));
        trailer.getChildren().add(new CheckBoxTreeItem<>("wear"));
        object.getChildren().add(trailer);
        TreeItem<String> job = new TreeItem<>("job");
        job.getChildren().add(new CheckBoxTreeItem<>("income"));
        job.getChildren().add(new CheckBoxTreeItem<>("deadlineTime"));
        job.getChildren().add(new CheckBoxTreeItem<>("remainingTime"));
        job.getChildren().add(new CheckBoxTreeItem<>("sourceCity"));
        job.getChildren().add(new CheckBoxTreeItem<>("sourceCompany"));
        job.getChildren().add(new CheckBoxTreeItem<>("destinationCity"));
        job.getChildren().add(new CheckBoxTreeItem<>("destinationCompany"));
        object.getChildren().add(job);
        TreeItem<String> navigation = new TreeItem<>("navigation");
        navigation.getChildren().add(new CheckBoxTreeItem<>("estimatedTime"));
        navigation.getChildren().add(new CheckBoxTreeItem<>("estimatedDistance"));
        navigation.getChildren().add(new CheckBoxTreeItem<>("speedLimit"));
        object.getChildren().add(navigation);

        selectJson.setRoot(object);
        selectJson.setShowRoot(false);

    }

    private void startService(){
        if(connectedToTelemetry && connectedToSerial){
            selectJson.setDisable(true);
            timeInterval.setDisable(true);
            findCheckItems();
            Timer timer = new Timer();

            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    try{
                        BufferedReader in = new BufferedReader(new InputStreamReader(telemetryUrl.openStream()));
                        String json;
                        EtsData etsData;
                        while ((json = in.readLine()) != null){
                            etsData = gson.fromJson(json, EtsData.class);
                            JsonObject toJson = new JsonObject();
                            for (CheckBoxTreeItem<?> item: checkedItems) {
                                switch (item.getParent().getValue().toString()) {
                                    case "game":
                                        toJson.add(item.getValue().toString(), gson.toJsonTree(Game.class.getDeclaredField(item.getValue().toString()).get(etsData.getGame())));
                                        break;
                                    case "truck":
                                        if(Truck.class.getDeclaredField(item.getValue().toString()).get(etsData.getTruck()) instanceof Double){
                                            toJson.add(item.getValue().toString(), gson.toJsonTree(Math.round((Double) Truck.class.getDeclaredField(item.getValue().toString()).get(etsData.getTruck()))));
                                        }else{
                                            toJson.add(item.getValue().toString(), gson.toJsonTree(Truck.class.getDeclaredField(item.getValue().toString()).get(etsData.getTruck())));
                                        }
                                        break;
                                    case "trailer":
                                        toJson.add(item.getValue().toString(), gson.toJsonTree(Trailer.class.getDeclaredField(item.getValue().toString()).get(etsData.getTrailer())));
                                        break;
                                    case "job":
                                        toJson.add(item.getValue().toString(), gson.toJsonTree(Job.class.getDeclaredField(item.getValue().toString()).get(etsData.getJob())));
                                        break;
                                    case "navigation":
                                        toJson.add(item.getValue().toString(), gson.toJsonTree(Navigation.class.getDeclaredField(item.getValue().toString()).get(etsData.getNavigation())));
                                        break;
                                }
                            }
                            String jsonToSend = gson.toJson(toJson);
                            jsonToSend += "\n";
                            outputStream.write(jsonToSend.getBytes());
                            System.out.println(jsonToSend);
                        }
                        in.close();
                    }catch (Exception e){
                       new TextAlertGenerator(e, Alert.AlertType.ERROR);
                    }
                }
            };
            startService.setDisable(true);
            timer.schedule(task,0,timeInterval.getValue());
        }else{
            if(!connectedToTelemetry){
                new AlertGenerator("Error","Connect to Telemetry!", Alert.AlertType.INFORMATION);
            }else {
                new AlertGenerator("Error","Connect to Arduino!", Alert.AlertType.INFORMATION);
            }
        }
    }

    private void findCheckItems(){
        ((List<CheckBoxTreeItem<?>>) MainController.checkedItems).clear();
        for (TreeItem<?> item: selectJson.getRoot().getChildren()) {
            for (TreeItem<?> subItem: item.getChildren()) {
                if(subItem instanceof CheckBoxTreeItem){
                    if(((CheckBoxTreeItem<?>) subItem).isSelected()){
                        ((List<CheckBoxTreeItem<?>>) MainController.checkedItems).add((CheckBoxTreeItem<?>) subItem);
                    }
                }else{
                    for (TreeItem<?> subSubItem: subItem.getChildren()) {
                        if(subSubItem instanceof CheckBoxTreeItem){
                            if(((CheckBoxTreeItem<?>) subSubItem).isSelected()){
                                ((List<CheckBoxTreeItem<?>>) MainController.checkedItems).add((CheckBoxTreeItem<?>) subSubItem);
                            }
                        }
                    }
                }
            }
        }
    }

    String getDataToSave(){
        if(connectedToSerial && connectedToTelemetry) {
            findCheckItems();
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            return gson.toJson(new DataToSave(serialPorts.getSelectionModel().getSelectedItem(),baudrate.getSelectionModel().getSelectedItem(),
            databits.getSelectionModel().getSelectedItem(),stopbits.getSelectionModel().getSelectedItem(),parity.getSelectionModel().getSelectedItem(),telemetryApiUrl.getText(),
            changeList(),timeInterval.getValue()));
        }
        return null;
    }

    private ArrayList<CheckBoxTreeItemToJson> changeList(){
        if(!MainController.checkedItems.isEmpty()) {
            ArrayList<CheckBoxTreeItemToJson> listToReturn = new ArrayList<>();
            for (CheckBoxTreeItem<?> item : MainController.checkedItems) {
                listToReturn.add(new CheckBoxTreeItemToJson(item.getValue().toString(), item.getParent().getValue().toString(), item.getParent().getParent().getValue().toString()));
            }
            return listToReturn;
        }
        return null;
    }

    void setDataToOpen(String json){
        DataToSave dataToOpen = gson.fromJson(json,DataToSave.class);
        scanSerial();
        baudrate.getSelectionModel().select(dataToOpen.getBaudrate());
        databits.getSelectionModel().select(dataToOpen.getDatabits());
        stopbits.getSelectionModel().select(dataToOpen.getStopbits());
        parity.getSelectionModel().select(dataToOpen.getParity());
        for (String s: serialPorts.getItems()) {
            if(s.equals(dataToOpen.getComPort())){
                serialPorts.getSelectionModel().select(dataToOpen.getComPort());
                connectToSerialDevice();
            }
        }
        telemetryApiUrl.clear();
        telemetryApiUrl.setText(dataToOpen.getTelemetryApiUrl());
        if(testUrl(dataToOpen.getTelemetryApiUrl())) connectToTelemetry();
        timeInterval.valueFactoryProperty().setValue(new SpinnerValueFactory.IntegerSpinnerValueFactory(10,500,dataToOpen.getTimeInterval(),5));
        if(dataToOpen.getCheckedItems() != null){
            for (CheckBoxTreeItemToJson item: dataToOpen.getCheckedItems()) {
                for (TreeItem<?> treeItem: selectJson.getRoot().getChildren()) {
                    for (TreeItem<?> subTreeItem: treeItem.getChildren()) {
                        if(subTreeItem instanceof CheckBoxTreeItem){
                            if(subTreeItem.getValue().toString().equals(item.getObject()) && subTreeItem.getParent().getValue().toString().equals(item.getObjectParent()) &&
                               subTreeItem.getParent().getParent().getValue().toString().equals(item.getObjectParentParent())){
                                ((CheckBoxTreeItem<?>) subTreeItem).setSelected(true);
                            }
                        }else{
                            for (TreeItem<?> subSubTreeItem: subTreeItem.getChildren()) {
                                if(subSubTreeItem instanceof CheckBoxTreeItem){
                                    if(subSubTreeItem.getValue().toString().equals(item.getObject()) && subSubTreeItem.getParent().getValue().toString().equals(item.getObjectParent()) &&
                                            subSubTreeItem.getParent().getParent().getValue().toString().equals(item.getObjectParentParent())){
                                        ((CheckBoxTreeItem<?>) subSubTreeItem).setSelected(true);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void scanSerial(){
        serialPorts.getItems().clear();
        Enumeration portList = CommPortIdentifier.getPortIdentifiers();
        while(portList.hasMoreElements()){
            CommPortIdentifier portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                serialPorts.getItems().add(portId.getName());
            }
        }
    }

    private void connectToSerialDevice(){
        if(serialPorts.getSelectionModel().getSelectedItem() != null && baudrate.getSelectionModel().getSelectedItem() != null &&
           databits.getSelectionModel().getSelectedItem() != null && stopbits.getSelectionModel().getSelectedItem() != null &&
           parity.getSelectionModel().getSelectedItem() != null) {
            try {
                int tempStopbits = -1;
                int tempParity = -1;
                CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier(serialPorts.getSelectionModel().getSelectedItem());
                SerialPort serialPort = (SerialPort) portId.open("TTS", 2000);
                outputStream = serialPort.getOutputStream();
                switch (stopbits.getSelectionModel().getSelectedItem()) {
                    case "1":
                        tempStopbits = SerialPort.STOPBITS_1;
                        break;
                    case "2":
                        tempStopbits = SerialPort.STOPBITS_2;
                        break;
                    case "1.5":
                        tempStopbits = SerialPort.STOPBITS_1_5;
                        break;
                }
                switch (parity.getSelectionModel().getSelectedItem()) {
                    case "None":
                        tempParity = SerialPort.PARITY_NONE;
                        break;
                    case "Odd":
                        tempParity = SerialPort.PARITY_ODD;
                        break;
                    case "Even":
                        tempParity = SerialPort.PARITY_EVEN;
                        break;
                    case "Mark":
                        tempParity = SerialPort.PARITY_MARK;
                        break;
                    case "Space":
                        tempParity = SerialPort.PARITY_SPACE;
                        break;
                }
                serialPort.setSerialPortParams(baudrate.getSelectionModel().getSelectedItem(),databits.getSelectionModel().getSelectedItem(),tempStopbits,tempParity);
                connectToSerial.setDisable(true);
                serialPorts.setDisable(true);
                baudrate.setDisable(true);
                databits.setDisable(true);
                stopbits.setDisable(true);
                parity.setDisable(true);
                scanPort.setDisable(true);
                connectedToSerial = true;
            }catch (Exception e){
                new TextAlertGenerator(e, Alert.AlertType.ERROR);
            }
        }
    }

    private boolean testUrl(String sUrl){
        try {
            URL url = new URL(sUrl);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            urlConn.connect();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private void connectToTelemetry(){
    try {
        if (testUrl(telemetryApiUrl.getText())) {
            URL tempURL = new URL(telemetryApiUrl.getText());
            telemetryUrl = tempURL;
            connectToTelemetry.setDisable(true);
            telemetryApiUrl.setDisable(true);
            connectedToTelemetry = true;
            DebugTextController.telemetryUrl = tempURL;
            showDebugData.setDisable(false);
        }
    }catch (Exception e){
        new TextAlertGenerator(e, Alert.AlertType.ERROR);
    }
    }

}
