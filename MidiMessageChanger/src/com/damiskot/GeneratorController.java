package com.damiskot;

import com.damiskot.json.JsonThread;
import com.damiskot.midi.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Line;

import javax.sound.midi.*;
import java.util.ArrayList;
import java.util.List;

public class GeneratorController {

    private MidiCommands MidiCommands = new MidiCommands();

    private boolean connected = false;

    private Object synchroObject = new Object();

    private Receiver output = null;

    static FXMLLoader fxmlLoader;

    private RootController rootController;

    private List<ComboBox> noteComboBoxes = new ArrayList<>();

    @FXML
    private Line vl; //This line above: Set velocity

    @FXML
    private CubicCurve kcc2; //This is curve line from kcc to vl

    @FXML
    private CubicCurve ccc2; //This is curve line is from ccc to vl

    @FXML
    private CubicCurve cccc; //This is curve line under Radio Button: Select CC message

    @FXML
    private Line ccl; //This is line under cccc

    @FXML
    private CubicCurve ccc; //This is curve line under Radio Button: Select chord

    @FXML
    private Line cl; //This is line under ccc

    @FXML
    private CubicCurve kcc; //This is curve line under Radio Button: Select keys to send

    @FXML
    private Line kl; //This is line under kcc

    @FXML
    private Label hmkts; //hmkts - label text: How many keys to send

    @FXML
    private Label kts; //kts - label text: Keys to send

    @FXML
    private Label cbn; //cbn - label text: Chord base note

    @FXML
    private Label toc; //toc - label text: Type of chord

    @FXML
    private Label scn; //scn - label text: Select CC number

    @FXML
    private  Label sv; //sv - label text: Select value

    @FXML
    private Label st; //st - label text: Set velocity

    @FXML
    private RadioButton radioFromInput;

    @FXML
    private RadioButton radioFromNumber;

    @FXML
    private Spinner<Integer> velocityNumber;

    @FXML
    private ComboBox<String> typeOfChord;

    @FXML
    private ComboBox<String> chordBaseNote;

    @FXML
    private Spinner<Integer> midiChannel;

    @FXML
    private Label midiChannelLabel;

    @FXML
    private Spinner<Integer> ccNumber;

    @FXML
    private Spinner<Integer> ccValue;

    @FXML
    private RadioButton radioKeys;

    @FXML
    private RadioButton radioChord;

    @FXML
    private RadioButton radioCC;

    @FXML
    private ComboBox<String> keyToClick;

    @FXML
    private ComboBox<Integer> howManyKeys;

    @FXML
    private ComboBox<String> keyToSend1;

    @FXML
    private ComboBox<String> keyToSend2;

    @FXML
    private ComboBox<String> keyToSend3;

    @FXML
    private ComboBox<String> keyToSend4;

    @FXML
    private Tab setChordsTab;

    @FXML
    private ComboBox<MidiDevice.Info> inputDevices;

    @FXML
    private ComboBox<MidiDevice.Info> outputDevices;

    @FXML
    private Button scanButton;

    @FXML
    private Button connectButton;

    @FXML
    private CheckBox debug;

    @FXML
    private CheckBox returnCC;

    @FXML
    private TextArea textArea;

    @FXML
    private Tab debugTab;

    @FXML
    private Tab settingsTab;

    @FXML
    private Tab eventsListTab;

    @FXML
    private TabPane tabs;

    @FXML
    private Button panicButton;

    @FXML
    private TableView<MidiTable> listOfThreads;

    @FXML
    private TableColumn<MidiTable, String> listOfThreadsColumn;


    public GeneratorController(){

    }

    @FXML
    private void initialize(){

        rootController = fxmlLoader.getController();

        SingleSelectionModel<Tab> selectionModel = tabs.getSelectionModel();
        selectionModel.select(settingsTab);

        listOfThreadsColumn.setCellValueFactory(cellValue -> cellValue.getValue().showMessageProperty());

        velocityNumber.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,127));
        midiChannel.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,16));
        ccNumber.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,127));
        ccValue.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,127));

        ToggleGroup radioGroup2 = new ToggleGroup();
        radioFromInput.setToggleGroup(radioGroup2);
        radioFromNumber.setToggleGroup(radioGroup2);

        ToggleGroup radioGroup1 = new ToggleGroup();
        radioChord.setToggleGroup(radioGroup1);
        radioKeys.setToggleGroup(radioGroup1);
        radioCC.setToggleGroup(radioGroup1);
        debug.setDisable(true);
        debugTab.setDisable(true);
        setChordsTab.setDisable(true);
        eventsListTab.setDisable(true);
        connectButton.setStyle("-fx-text-fill: green");

        keyToClick.setItems(FXCollections.observableArrayList());
        keyToSend1.setItems(FXCollections.observableArrayList());
        keyToSend2.setItems(FXCollections.observableArrayList());
        keyToSend3.setItems(FXCollections.observableArrayList());
        keyToSend4.setItems(FXCollections.observableArrayList());
        chordBaseNote.setItems(FXCollections.observableArrayList());
        for (int i = 21; i < MidiCommands.getParam1().size() + 21; i++) {
            keyToClick.getItems().addAll(FXCollections.observableArrayList(MidiCommands.getParam1().get(i)));
            keyToSend1.getItems().addAll(FXCollections.observableArrayList(MidiCommands.getParam1().get(i)));
            keyToSend2.getItems().addAll(FXCollections.observableArrayList(MidiCommands.getParam1().get(i)));
            keyToSend3.getItems().addAll(FXCollections.observableArrayList(MidiCommands.getParam1().get(i)));
            keyToSend4.getItems().addAll(FXCollections.observableArrayList(MidiCommands.getParam1().get(i)));
            chordBaseNote.getItems().addAll(FXCollections.observableArrayList(MidiCommands.getParam1().get(i)));
        }

        howManyKeys.setItems(FXCollections.observableArrayList());
        for (int i = 1; i < 5; i++) {
            howManyKeys.getItems().addAll(FXCollections.observableArrayList(i));
        }

        howManyKeys.getSelectionModel().selectFirst();

        noteComboBoxes.add(chordBaseNote);
        noteComboBoxes.add(keyToClick);
        noteComboBoxes.add(keyToSend1);
        noteComboBoxes.add(keyToSend2);
        noteComboBoxes.add(keyToSend3);
        noteComboBoxes.add(keyToSend4);


        radioFromNumber.setSelected(true);

        radioKeys.setSelected(true);
        keysGroupState(false);
        chordGroupState(true);
        ccGroupState(true);

        panicButton.setDisable(true);

        //TODO: make chords avaiable
        radioChord.setDisable(true); //Disable chords

        checkInputDevices();

    }

    @FXML
    private void handlerVelocityRadio(){
        if(radioFromInput.isSelected()){
            velocityNumber.setDisable(true);
        }else if(radioFromNumber.isSelected()){
            velocityNumber.setDisable(false);
        }
    }

    @FXML
    private void handlerRadio(){
        if(radioKeys.isSelected()){
            keysGroupState(false);
            chordGroupState(true);
            ccGroupState(true);
            handlerHowManyKeys();
            handlerVelocityRadio();
        }else if(radioChord.isSelected()){
            keysGroupState(true);
            chordGroupState(false);
            ccGroupState(true);
            handlerVelocityRadio();
        }else if(radioCC.isSelected()){
            keysGroupState(true);
            chordGroupState(true);
            ccGroupState(false);
        }
    }

    private void keysGroupState(boolean state){
        if(state){
            vl.setOpacity(0.1);
            kcc.setOpacity(0.1);
            kl.setOpacity(0.1);
            kcc2.setOpacity(0.1);
        }else {
            vl.setOpacity(1);
            kcc.setOpacity(1);
            kl.setOpacity(1);
            kcc2.setOpacity(1);
        }
        hmkts.setDisable(state);
        howManyKeys.setDisable(state);
        kts.setDisable(state);
        keyToSend1.setDisable(state);
        keyToSend2.setDisable(state);
        keyToSend3.setDisable(state);
        keyToSend4.setDisable(state);

        st.setDisable(state);
        radioFromNumber.setDisable(state);
        radioFromInput.setDisable(state);
        velocityNumber.setDisable(state);

    }

    private void chordGroupState(boolean state){
        if(state) {
            ccc.setOpacity(0.1);
            cl.setOpacity(0.1);
            ccc2.setOpacity(0.1);
            if(!radioKeys.isSelected()){
                vl.setOpacity(0.1);
            }
        }else {
            ccc.setOpacity(1);
            cl.setOpacity(1);
            vl.setOpacity(1);
            ccc2.setOpacity(1);
        }
        cbn.setDisable(state);
        chordBaseNote.setDisable(state);
        toc.setDisable(state);
        typeOfChord.setDisable(state);

        if(!radioKeys.isSelected()) {
            st.setDisable(state);
            radioFromNumber.setDisable(state);
            radioFromInput.setDisable(state);
            velocityNumber.setDisable(state);
        }
    }

    private void ccGroupState(boolean state){
        if(state) {
            cccc.setOpacity(0.1);
            ccl.setOpacity(0.1);
        }else {
            cccc.setOpacity(1);
            ccl.setOpacity(1);
        }
        returnCC.setDisable(state);
        scn.setDisable(state);
        ccNumber.setDisable(state);
        sv.setDisable(state);
        ccValue.setDisable(state);
    }


    @FXML
    private void handlerHowManyKeys(){
        if(howManyKeys.getSelectionModel().getSelectedItem() == 1){
            keyToSend1.setDisable(false);
            keyToSend2.setDisable(true);
            keyToSend3.setDisable(true);
            keyToSend4.setDisable(true);
        }else if(howManyKeys.getSelectionModel().getSelectedItem() == 2){
            keyToSend1.setDisable(false);
            keyToSend2.setDisable(false);
            keyToSend3.setDisable(true);
            keyToSend4.setDisable(true);
        }else if(howManyKeys.getSelectionModel().getSelectedItem() == 3){
            keyToSend1.setDisable(false);
            keyToSend2.setDisable(false);
            keyToSend3.setDisable(false);
            keyToSend4.setDisable(true);
        }else if(howManyKeys.getSelectionModel().getSelectedItem() == 4){
            keyToSend1.setDisable(false);
            keyToSend2.setDisable(false);
            keyToSend3.setDisable(false);
            keyToSend4.setDisable(false);

        }
    }

    @FXML
    private void handleDebugCheckbox(){
        if(debug.isSelected()){
            debugTab.setDisable(false);
        }else {
            debugTab.setDisable(true);
        }
    }

    @FXML
    private void handlePanic(){
        try {
            for (int i = 0; i < 16; i++) {
                System.out.println(i);
                for (int x = 20; x < 109; x++) {
                    output.send(new ShortMessage(ShortMessage.NOTE_OFF, i, x, 127), -1);
                }
            }

        }catch (Exception e){
            new TextAlertGenerator(e, Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleClearButton(){
        textArea.setText(null);
    }

    @FXML
    private void handleConnectButton(){
        MidiConnect MidiConnect = new MidiConnect();
        if(!connected) {
            if (inputDevices.getSelectionModel().getSelectedItem().toString() != null && outputDevices.getSelectionModel().getSelectedItem().toString() != null) {
                MidiDevice.Info outDevice = outputDevices.getSelectionModel().getSelectedItem();
                MidiConnect.connect(inputDevices.getSelectionModel().getSelectedItem(), outDevice, textArea, debug, noteComboBoxes, MidiCommands,synchroObject);
                try {
                   MidiThread.output = MidiSystem.getMidiDevice(outDevice).getReceiver();
                   MidiVelocityThread.output = MidiSystem.getMidiDevice(outDevice).getReceiver();
                   output = MidiSystem.getMidiDevice(outDevice).getReceiver();
                   com.damiskot.midi.MidiConnect.output = MidiSystem.getMidiDevice(outDevice).getReceiver();
                } catch (MidiUnavailableException e) {
                    new TextAlertGenerator(e, Alert.AlertType.ERROR);
                }
                rootController.setFileMenuDisable(false);
                inputDevices.setDisable(true);
                outputDevices.setDisable(true);
                scanButton.setDisable(true);
                setChordsTab.setDisable(false);
                eventsListTab.setDisable(false);
                debug.setDisable(false);
                panicButton.setDisable(false);
                connectButton.setText("Disconnect");
                connectButton.setStyle("-fx-text-fill: red");
                connected = true;
                handlerHowManyKeys();
            }
        }else{
            for (int i = 0; i < listOfThreads.getItems().size(); i++) {
                listOfThreads.getItems().get(i).getThread().interrupt();
            }
            listOfThreads.getItems().clear();
            MidiConnect.close(inputDevices.getSelectionModel().getSelectedItem(),outputDevices.getSelectionModel().getSelectedItem(),textArea,debug);
            output = null;
            MidiThread.output = null;
            com.damiskot.midi.MidiConnect.output = null;
            MidiVelocityThread.output = null;
            rootController.setFileMenuDisable(true);
            inputDevices.setDisable(false);
            outputDevices.setDisable(false);
            scanButton.setDisable(false);
            setChordsTab.setDisable(true);
            eventsListTab.setDisable(true);
            debugTab.setDisable(true);
            debug.setSelected(false);
            debug.setDisable(true);
            panicButton.setDisable(true);
            connectButton.setText("Connect");
            connectButton.setStyle("-fx-text-fill: green");
            connected = false;
            }
    }

    @FXML
    private void handleScanButton(){
        checkInputDevices();
    }

    @FXML
    private void handleAddEvent(){
            if (radioKeys.isSelected()) {
                if (radioFromInput.isSelected()) {
                    if (howManyKeys.getSelectionModel().getSelectedItem() == 1) {
                        try {
                            ShortMessage msg1On = new ShortMessage(ShortMessage.NOTE_ON, midiChannel.getValue(), (int) MidiCommands.getKeyFromValue(MidiCommands.getParam1(), keyToSend1.getSelectionModel().getSelectedItem())-12, 0);
                            ShortMessage msg1Off = new ShortMessage(ShortMessage.NOTE_OFF, midiChannel.getValue(), (int) MidiCommands.getKeyFromValue(MidiCommands.getParam1(), keyToSend1.getSelectionModel().getSelectedItem())-12, 0);
                            MidiVelocityThread thread = new MidiVelocityThread(synchroObject,keyToClick.getSelectionModel().getSelectedItem(), 1, new ShortMessage[]{msg1On}, new ShortMessage[]{msg1Off});
                            thread.start();
                            JsonThread jsonThread = new JsonThread(keyToClick.getSelectionModel().getSelectedItem(),new ShortMessage[]{msg1On},new ShortMessage[]{msg1Off},false,true,1,false);
                            RootController.JsonThreads.add(jsonThread);
                            listOfThreads.getItems().add(new MidiTable(thread ,jsonThread, keyToClick.getSelectionModel().getSelectedItem(), 1, new ShortMessage[]{msg1On}));
                            addToUsedNotes((Integer)MidiCommands.getKeyFromValue(MidiCommands.getParam1(),keyToClick.getSelectionModel().getSelectedItem())-12);
                        } catch (InvalidMidiDataException e) {
                            new TextAlertGenerator(e, Alert.AlertType.ERROR);
                        }
                    } else if (howManyKeys.getSelectionModel().getSelectedItem() == 2) {
                        try {
                            ShortMessage msg1On = new ShortMessage(ShortMessage.NOTE_ON, midiChannel.getValue(), (int) MidiCommands.getKeyFromValue(MidiCommands.getParam1(), keyToSend1.getSelectionModel().getSelectedItem())-12, 0);
                            ShortMessage msg1Off = new ShortMessage(ShortMessage.NOTE_OFF, midiChannel.getValue(), (int) MidiCommands.getKeyFromValue(MidiCommands.getParam1(), keyToSend1.getSelectionModel().getSelectedItem())-12, 0);
                            ShortMessage msg2On = new ShortMessage(ShortMessage.NOTE_ON, midiChannel.getValue(), (int) MidiCommands.getKeyFromValue(MidiCommands.getParam1(), keyToSend2.getSelectionModel().getSelectedItem())-12, 0);
                            ShortMessage msg2Off = new ShortMessage(ShortMessage.NOTE_OFF, midiChannel.getValue(), (int) MidiCommands.getKeyFromValue(MidiCommands.getParam1(), keyToSend2.getSelectionModel().getSelectedItem())-12, 0);
                            MidiVelocityThread thread = new MidiVelocityThread(synchroObject,keyToClick.getSelectionModel().getSelectedItem(), 2, new ShortMessage[]{msg1On, msg2On}, new ShortMessage[]{msg1Off, msg2Off});
                            thread.start();
                            JsonThread jsonThread = new JsonThread(keyToClick.getSelectionModel().getSelectedItem(),new ShortMessage[]{msg1On,msg2On},new ShortMessage[]{msg1Off,msg2Off},false,true,2,false);
                            RootController.JsonThreads.add(jsonThread);
                            listOfThreads.getItems().add(new MidiTable(thread ,jsonThread, keyToClick.getSelectionModel().getSelectedItem(), 2, new ShortMessage[]{msg1On, msg2On}));
                            addToUsedNotes((Integer)MidiCommands.getKeyFromValue(MidiCommands.getParam1(),keyToClick.getSelectionModel().getSelectedItem())-12);
                        } catch (InvalidMidiDataException e) {
                            new TextAlertGenerator(e, Alert.AlertType.ERROR);
                        }
                    } else if (howManyKeys.getSelectionModel().getSelectedItem() == 3) {
                        try {
                            ShortMessage msg1On = new ShortMessage(ShortMessage.NOTE_ON, midiChannel.getValue(), (int) MidiCommands.getKeyFromValue(MidiCommands.getParam1(), keyToSend1.getSelectionModel().getSelectedItem())-12, 0);
                            ShortMessage msg1Off = new ShortMessage(ShortMessage.NOTE_OFF, midiChannel.getValue(), (int) MidiCommands.getKeyFromValue(MidiCommands.getParam1(), keyToSend1.getSelectionModel().getSelectedItem())-12, 0);
                            ShortMessage msg2On = new ShortMessage(ShortMessage.NOTE_ON, midiChannel.getValue(), (int) MidiCommands.getKeyFromValue(MidiCommands.getParam1(), keyToSend2.getSelectionModel().getSelectedItem())-12, 0);
                            ShortMessage msg2Off = new ShortMessage(ShortMessage.NOTE_OFF, midiChannel.getValue(), (int) MidiCommands.getKeyFromValue(MidiCommands.getParam1(), keyToSend2.getSelectionModel().getSelectedItem())-12, 0);
                            ShortMessage msg3On = new ShortMessage(ShortMessage.NOTE_ON, midiChannel.getValue(), (int) MidiCommands.getKeyFromValue(MidiCommands.getParam1(), keyToSend3.getSelectionModel().getSelectedItem())-12, 0);
                            ShortMessage msg3Off = new ShortMessage(ShortMessage.NOTE_OFF, midiChannel.getValue(), (int) MidiCommands.getKeyFromValue(MidiCommands.getParam1(), keyToSend3.getSelectionModel().getSelectedItem())-12, 0);
                            MidiVelocityThread thread = new MidiVelocityThread(synchroObject,keyToClick.getSelectionModel().getSelectedItem(), 3, new ShortMessage[]{msg1On, msg2On, msg3On}, new ShortMessage[]{msg1Off, msg2Off, msg3Off});
                            thread.start();
                            JsonThread jsonThread = new JsonThread(keyToClick.getSelectionModel().getSelectedItem(),new ShortMessage[]{msg1On,msg2On,msg3On},new ShortMessage[]{msg1Off,msg2Off,msg3Off},false,true,3,false);
                            RootController.JsonThreads.add(jsonThread);
                            listOfThreads.getItems().add(new MidiTable(thread ,jsonThread, keyToClick.getSelectionModel().getSelectedItem(), 3, new ShortMessage[]{msg1On, msg2On, msg3On}));
                            addToUsedNotes((Integer)MidiCommands.getKeyFromValue(MidiCommands.getParam1(),keyToClick.getSelectionModel().getSelectedItem())-12);
                        } catch (InvalidMidiDataException e) {
                            new TextAlertGenerator(e, Alert.AlertType.ERROR);
                        }
                    } else if (howManyKeys.getSelectionModel().getSelectedItem() == 4) {
                        try {
                            ShortMessage msg1On = new ShortMessage(ShortMessage.NOTE_ON, midiChannel.getValue(), (int) MidiCommands.getKeyFromValue(MidiCommands.getParam1(), keyToSend1.getSelectionModel().getSelectedItem())-12, 0);
                            ShortMessage msg1Off = new ShortMessage(ShortMessage.NOTE_OFF, midiChannel.getValue(), (int) MidiCommands.getKeyFromValue(MidiCommands.getParam1(), keyToSend1.getSelectionModel().getSelectedItem())-12, 0);
                            ShortMessage msg2On = new ShortMessage(ShortMessage.NOTE_ON, midiChannel.getValue(), (int) MidiCommands.getKeyFromValue(MidiCommands.getParam1(), keyToSend2.getSelectionModel().getSelectedItem())-12, 0);
                            ShortMessage msg2Off = new ShortMessage(ShortMessage.NOTE_OFF, midiChannel.getValue(), (int) MidiCommands.getKeyFromValue(MidiCommands.getParam1(), keyToSend2.getSelectionModel().getSelectedItem())-12, 0);
                            ShortMessage msg3On = new ShortMessage(ShortMessage.NOTE_ON, midiChannel.getValue(), (int) MidiCommands.getKeyFromValue(MidiCommands.getParam1(), keyToSend3.getSelectionModel().getSelectedItem())-12, 0);
                            ShortMessage msg3Off = new ShortMessage(ShortMessage.NOTE_OFF, midiChannel.getValue(), (int) MidiCommands.getKeyFromValue(MidiCommands.getParam1(), keyToSend3.getSelectionModel().getSelectedItem())-12, 0);
                            ShortMessage msg4On = new ShortMessage(ShortMessage.NOTE_ON, midiChannel.getValue(), (int) MidiCommands.getKeyFromValue(MidiCommands.getParam1(), keyToSend4.getSelectionModel().getSelectedItem())-12, 0);
                            ShortMessage msg4Off = new ShortMessage(ShortMessage.NOTE_OFF, midiChannel.getValue(), (int) MidiCommands.getKeyFromValue(MidiCommands.getParam1(), keyToSend4.getSelectionModel().getSelectedItem())-12, 0);
                            MidiVelocityThread thread = new MidiVelocityThread(synchroObject,keyToClick.getSelectionModel().getSelectedItem(), 4, new ShortMessage[]{msg1On, msg2On, msg3On, msg4On}, new ShortMessage[]{msg1Off, msg2Off, msg3Off, msg4Off});
                            thread.start();
                            JsonThread jsonThread = new JsonThread(keyToClick.getSelectionModel().getSelectedItem(),new ShortMessage[]{msg1On,msg2On,msg3On,msg4On},new ShortMessage[]{msg1Off,msg2Off,msg3Off,msg4Off},false,true,4,false);
                            RootController.JsonThreads.add(jsonThread);
                            listOfThreads.getItems().add(new MidiTable(thread ,jsonThread, keyToClick.getSelectionModel().getSelectedItem(), 4, new ShortMessage[]{msg1On, msg2On, msg3On, msg4On}));
                            addToUsedNotes((Integer)MidiCommands.getKeyFromValue(MidiCommands.getParam1(),keyToClick.getSelectionModel().getSelectedItem())-12);
                        } catch (InvalidMidiDataException e) {
                            new TextAlertGenerator(e, Alert.AlertType.ERROR);
                        }
                    }
                } else{
                if (howManyKeys.getSelectionModel().getSelectedItem() == 1) {
                    try {
                        ShortMessage msg1On = new ShortMessage(ShortMessage.NOTE_ON, midiChannel.getValue(), (int) MidiCommands.getKeyFromValue(MidiCommands.getParam1(), keyToSend1.getSelectionModel().getSelectedItem())-12, velocityNumber.getValue());
                        ShortMessage msg1Off = new ShortMessage(ShortMessage.NOTE_OFF, midiChannel.getValue(), (int) MidiCommands.getKeyFromValue(MidiCommands.getParam1(), keyToSend1.getSelectionModel().getSelectedItem())-12, velocityNumber.getValue());
                        MidiThread thread = new MidiThread(synchroObject,keyToClick.getSelectionModel().getSelectedItem(), 1, new ShortMessage[]{msg1On}, new ShortMessage[]{msg1Off});
                        thread.start();
                        JsonThread jsonThread = new JsonThread(keyToClick.getSelectionModel().getSelectedItem(),new ShortMessage[]{msg1On},new ShortMessage[]{msg1Off},false,false,1,false);
                        RootController.JsonThreads.add(jsonThread);
                        listOfThreads.getItems().add(new MidiTable(thread ,jsonThread, keyToClick.getSelectionModel().getSelectedItem(), 1, new ShortMessage[]{msg1On}));
                        addToUsedNotes((Integer)MidiCommands.getKeyFromValue(MidiCommands.getParam1(),keyToClick.getSelectionModel().getSelectedItem())-12);
                    } catch (InvalidMidiDataException e) {
                        new TextAlertGenerator(e, Alert.AlertType.ERROR);
                    }
                } else if (howManyKeys.getSelectionModel().getSelectedItem() == 2) {
                    try {
                        ShortMessage msg1On = new ShortMessage(ShortMessage.NOTE_ON, midiChannel.getValue(), (int) MidiCommands.getKeyFromValue(MidiCommands.getParam1(), keyToSend1.getSelectionModel().getSelectedItem())-12, velocityNumber.getValue());
                        ShortMessage msg1Off = new ShortMessage(ShortMessage.NOTE_OFF, midiChannel.getValue(), (int) MidiCommands.getKeyFromValue(MidiCommands.getParam1(), keyToSend1.getSelectionModel().getSelectedItem())-12, velocityNumber.getValue());
                        ShortMessage msg2On = new ShortMessage(ShortMessage.NOTE_ON, midiChannel.getValue(), (int) MidiCommands.getKeyFromValue(MidiCommands.getParam1(), keyToSend2.getSelectionModel().getSelectedItem())-12, velocityNumber.getValue());
                        ShortMessage msg2Off = new ShortMessage(ShortMessage.NOTE_OFF, midiChannel.getValue(), (int) MidiCommands.getKeyFromValue(MidiCommands.getParam1(), keyToSend2.getSelectionModel().getSelectedItem())-12, velocityNumber.getValue());
                        MidiThread thread = new MidiThread(synchroObject,keyToClick.getSelectionModel().getSelectedItem(), 2, new ShortMessage[]{msg1On, msg2On}, new ShortMessage[]{msg1Off, msg2Off});
                        thread.start();
                        JsonThread jsonThread = new JsonThread(keyToClick.getSelectionModel().getSelectedItem(),new ShortMessage[]{msg1On,msg2On},new ShortMessage[]{msg1Off,msg2Off},false,false,2,false);
                        RootController.JsonThreads.add(jsonThread);
                        listOfThreads.getItems().add(new MidiTable(thread ,jsonThread, keyToClick.getSelectionModel().getSelectedItem(), 2, new ShortMessage[]{msg1On, msg2On}));
                        addToUsedNotes((Integer)MidiCommands.getKeyFromValue(MidiCommands.getParam1(),keyToClick.getSelectionModel().getSelectedItem())-12);
                    } catch (InvalidMidiDataException e) {
                        new TextAlertGenerator(e, Alert.AlertType.ERROR);
                    }
                } else if (howManyKeys.getSelectionModel().getSelectedItem() == 3) {
                    try {
                        ShortMessage msg1On = new ShortMessage(ShortMessage.NOTE_ON, midiChannel.getValue(), (int) MidiCommands.getKeyFromValue(MidiCommands.getParam1(), keyToSend1.getSelectionModel().getSelectedItem())-12, velocityNumber.getValue());
                        ShortMessage msg1Off = new ShortMessage(ShortMessage.NOTE_OFF, midiChannel.getValue(), (int) MidiCommands.getKeyFromValue(MidiCommands.getParam1(), keyToSend1.getSelectionModel().getSelectedItem())-12, velocityNumber.getValue());
                        ShortMessage msg2On = new ShortMessage(ShortMessage.NOTE_ON, midiChannel.getValue(), (int) MidiCommands.getKeyFromValue(MidiCommands.getParam1(), keyToSend2.getSelectionModel().getSelectedItem())-12, velocityNumber.getValue());
                        ShortMessage msg2Off = new ShortMessage(ShortMessage.NOTE_OFF, midiChannel.getValue(), (int) MidiCommands.getKeyFromValue(MidiCommands.getParam1(), keyToSend2.getSelectionModel().getSelectedItem())-12, velocityNumber.getValue());
                        ShortMessage msg3On = new ShortMessage(ShortMessage.NOTE_ON, midiChannel.getValue(), (int) MidiCommands.getKeyFromValue(MidiCommands.getParam1(), keyToSend3.getSelectionModel().getSelectedItem())-12, velocityNumber.getValue());
                        ShortMessage msg3Off = new ShortMessage(ShortMessage.NOTE_OFF, midiChannel.getValue(), (int) MidiCommands.getKeyFromValue(MidiCommands.getParam1(), keyToSend3.getSelectionModel().getSelectedItem())-12, velocityNumber.getValue());
                        MidiThread thread = new MidiThread(synchroObject,keyToClick.getSelectionModel().getSelectedItem(), 3, new ShortMessage[]{msg1On, msg2On, msg3On}, new ShortMessage[]{msg1Off, msg2Off, msg3Off});
                        thread.start();
                        JsonThread jsonThread = new JsonThread(keyToClick.getSelectionModel().getSelectedItem(),new ShortMessage[]{msg1On,msg2On,msg3On},new ShortMessage[]{msg1Off,msg2Off,msg3Off},false,false,3,false);
                        RootController.JsonThreads.add(jsonThread);
                        listOfThreads.getItems().add(new MidiTable(thread, jsonThread,keyToClick.getSelectionModel().getSelectedItem(), 3, new ShortMessage[]{msg1On, msg2On, msg3On}));
                        addToUsedNotes((Integer)MidiCommands.getKeyFromValue(MidiCommands.getParam1(),keyToClick.getSelectionModel().getSelectedItem())-12);
                    } catch (InvalidMidiDataException e) {
                        new TextAlertGenerator(e, Alert.AlertType.ERROR);
                    }
                } else if (howManyKeys.getSelectionModel().getSelectedItem() == 4) {
                    try {
                        ShortMessage msg1On = new ShortMessage(ShortMessage.NOTE_ON, midiChannel.getValue(), (int) MidiCommands.getKeyFromValue(MidiCommands.getParam1(), keyToSend1.getSelectionModel().getSelectedItem())-12, velocityNumber.getValue());
                        ShortMessage msg1Off = new ShortMessage(ShortMessage.NOTE_OFF, midiChannel.getValue(), (int) MidiCommands.getKeyFromValue(MidiCommands.getParam1(), keyToSend1.getSelectionModel().getSelectedItem())-12, velocityNumber.getValue());
                        ShortMessage msg2On = new ShortMessage(ShortMessage.NOTE_ON, midiChannel.getValue(), (int) MidiCommands.getKeyFromValue(MidiCommands.getParam1(), keyToSend2.getSelectionModel().getSelectedItem())-12, velocityNumber.getValue());
                        ShortMessage msg2Off = new ShortMessage(ShortMessage.NOTE_OFF, midiChannel.getValue(), (int) MidiCommands.getKeyFromValue(MidiCommands.getParam1(), keyToSend2.getSelectionModel().getSelectedItem())-12, velocityNumber.getValue());
                        ShortMessage msg3On = new ShortMessage(ShortMessage.NOTE_ON, midiChannel.getValue(), (int) MidiCommands.getKeyFromValue(MidiCommands.getParam1(), keyToSend3.getSelectionModel().getSelectedItem())-12, velocityNumber.getValue());
                        ShortMessage msg3Off = new ShortMessage(ShortMessage.NOTE_OFF, midiChannel.getValue(), (int) MidiCommands.getKeyFromValue(MidiCommands.getParam1(), keyToSend3.getSelectionModel().getSelectedItem())-12, velocityNumber.getValue());
                        ShortMessage msg4On = new ShortMessage(ShortMessage.NOTE_ON, midiChannel.getValue(), (int) MidiCommands.getKeyFromValue(MidiCommands.getParam1(), keyToSend4.getSelectionModel().getSelectedItem())-12, velocityNumber.getValue());
                        ShortMessage msg4Off = new ShortMessage(ShortMessage.NOTE_OFF, midiChannel.getValue(), (int) MidiCommands.getKeyFromValue(MidiCommands.getParam1(), keyToSend4.getSelectionModel().getSelectedItem())-12, velocityNumber.getValue());
                        MidiThread thread = new MidiThread(synchroObject,keyToClick.getSelectionModel().getSelectedItem(), 4, new ShortMessage[]{msg1On, msg2On, msg3On, msg4On}, new ShortMessage[]{msg1Off, msg2Off, msg3Off, msg4Off});
                        thread.start();
                        JsonThread jsonThread = new JsonThread(keyToClick.getSelectionModel().getSelectedItem(),new ShortMessage[]{msg1On,msg2On,msg3On,msg4On},new ShortMessage[]{msg1Off,msg2Off,msg3Off,msg4Off},false,false,4,false);
                        RootController.JsonThreads.add(jsonThread);
                        listOfThreads.getItems().add(new MidiTable(thread, jsonThread, keyToClick.getSelectionModel().getSelectedItem(), 4, new ShortMessage[]{msg1On, msg2On, msg3On, msg4On}));
                        addToUsedNotes((Integer)MidiCommands.getKeyFromValue(MidiCommands.getParam1(),keyToClick.getSelectionModel().getSelectedItem())-12);
                    } catch (InvalidMidiDataException e) {
                        new TextAlertGenerator(e, Alert.AlertType.ERROR);
                    }
                }
            }


            } else if (radioChord.isSelected()) {
                //TODO: make chords avaiable
            } else if (radioCC.isSelected()) {
                try {
                    ShortMessage ccMsgOn = new ShortMessage(ShortMessage.CONTROL_CHANGE, midiChannel.getValue(), ccNumber.getValue(), ccValue.getValue());
                    ShortMessage ccMsgOff = new ShortMessage(ShortMessage.CONTROL_CHANGE, midiChannel.getValue(), ccNumber.getValue(), 0);
                    MidiThread thread = new MidiThread(synchroObject,keyToClick.getSelectionModel().getSelectedItem(), returnCC.isSelected(), new ShortMessage[]{ccMsgOn}, new ShortMessage[]{ccMsgOff});
                    thread.start();
                    JsonThread jsonThread = new JsonThread(keyToClick.getSelectionModel().getSelectedItem(),new ShortMessage[]{ccMsgOn},new ShortMessage[]{ccMsgOff},true,false,1,returnCC.isSelected());
                    RootController.JsonThreads.add(jsonThread);
                    listOfThreads.getItems().add(new MidiTable(thread,jsonThread,keyToClick.getSelectionModel().getSelectedItem(), returnCC.isSelected(), new ShortMessage[]{ccMsgOn}));
                    addToUsedNotes((Integer)MidiCommands.getKeyFromValue(MidiCommands.getParam1(),keyToClick.getSelectionModel().getSelectedItem())-12);
                } catch (InvalidMidiDataException e) {
                    new TextAlertGenerator(e, Alert.AlertType.ERROR);
                }
            }

    }

    @FXML
    private void handleDeleteEvent(){
        if(listOfThreads.getSelectionModel().selectedItemProperty().get() == null){
            new AlertGenerator("Error","Select event to delete", Alert.AlertType.ERROR);
        }else {
            if(listOfThreads.getSelectionModel().selectedItemProperty().get().getJsonThread() != null){
                if (listOfThreads.getSelectionModel().selectedItemProperty().get().getThread().isAlive()) {
                    listOfThreads.getSelectionModel().selectedItemProperty().get().getThread().interrupt();
                    MidiConnect.usedNotes.remove(Integer.valueOf(listOfThreads.getSelectionModel().selectedItemProperty().get().getKeyToClick() - 12));
                    RootController.JsonThreads.remove(listOfThreads.getSelectionModel().selectedItemProperty().get().getJsonThread());
                    listOfThreads.getItems().remove(listOfThreads.getSelectionModel().getSelectedItem());
                }
            }
        }
    }

    private void checkInputDevices(){
        inputDevices.setItems(FXCollections.observableArrayList());
        outputDevices.setItems(FXCollections.observableArrayList());
        MidiDevice.Info[] deviceList = MidiSystem.getMidiDeviceInfo();
        for (int i = 0; i < deviceList.length; i++) {
            try {
                MidiDevice temp = MidiSystem.getMidiDevice(deviceList[i]);
                if(!temp.getTransmitter().toString().isEmpty()){
                   inputDevices.getItems().addAll(FXCollections.observableArrayList(deviceList[i]));
                }
            } catch (MidiUnavailableException e) {
                //new TextAlertGenerator(e, Alert.AlertType.WARNING);
            }

        }
        for (int i = 0; i < deviceList.length; i++) {
            try {
                MidiDevice temp = MidiSystem.getMidiDevice(deviceList[i]);
                if(!temp.getReceiver().toString().isEmpty()){
                    outputDevices.getItems().addAll(FXCollections.observableArrayList(deviceList[i]));
                }
            } catch (MidiUnavailableException e) {
                //new TextAlertGenerator(e, Alert.AlertType.WARNING);
            }

        }
    }

    private void addToUsedNotes(Integer note){
        if(!MidiConnect.usedNotes.contains(note)){
            MidiConnect.usedNotes.add(note);
        }
    }

    void loadPreset(List<JsonThread> jsonThreadList){
        for (int i = 0; i < jsonThreadList.size(); i++) {
            if(!jsonThreadList.get(i).isCcThread()){
                if(jsonThreadList.get(i).isVelocityThread()){
                        MidiVelocityThread thread = new MidiVelocityThread(synchroObject,jsonThreadList.get(i).getKeyToClick(), jsonThreadList.get(i).getHowManyNotes(), jsonThreadList.get(i).getMessagesOn(), jsonThreadList.get(i).getMessagesOff());
                        thread.start();
                        JsonThread jsonThread = jsonThreadList.get(i);
                        RootController.JsonThreads.add(jsonThread);
                        listOfThreads.getItems().add(new MidiTable(thread ,jsonThread, jsonThreadList.get(i).getKeyToClick(), jsonThreadList.get(i).getHowManyNotes(), jsonThreadList.get(i).getMessagesOn()));
                        addToUsedNotes((Integer)MidiCommands.getKeyFromValue(MidiCommands.getParam1(),jsonThreadList.get(i).getKeyToClick())-12);
                }else{
                            MidiThread thread = new MidiThread(synchroObject,jsonThreadList.get(i).getKeyToClick(), jsonThreadList.get(i).getHowManyNotes(), jsonThreadList.get(i).getMessagesOn(), jsonThreadList.get(i).getMessagesOff());
                            thread.start();
                            JsonThread jsonThread = jsonThreadList.get(i);
                            RootController.JsonThreads.add(jsonThread);
                            listOfThreads.getItems().add(new MidiTable(thread ,jsonThread, jsonThreadList.get(i).getKeyToClick(), jsonThreadList.get(i).getHowManyNotes(), jsonThreadList.get(i).getMessagesOn()));
                            addToUsedNotes((Integer)MidiCommands.getKeyFromValue(MidiCommands.getParam1(),jsonThreadList.get(i).getKeyToClick())-12);
                }
            }else{
                MidiThread thread = new MidiThread(synchroObject,jsonThreadList.get(i).getKeyToClick(), jsonThreadList.get(i).isBackMessage(), jsonThreadList.get(i).getMessagesOn(), jsonThreadList.get(i).getMessagesOff());
                thread.start();
                JsonThread jsonThread = jsonThreadList.get(i);
                RootController.JsonThreads.add(jsonThread);
                listOfThreads.getItems().add(new MidiTable(thread,jsonThread,jsonThreadList.get(i).getKeyToClick(), jsonThreadList.get(i).isBackMessage(),jsonThreadList.get(i).getMessagesOn()));
                addToUsedNotes((Integer)MidiCommands.getKeyFromValue(MidiCommands.getParam1(),jsonThreadList.get(i).getKeyToClick())-12);
            }
        }
    }

    private boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
}
