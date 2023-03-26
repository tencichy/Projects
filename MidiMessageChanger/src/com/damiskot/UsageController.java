package com.damiskot;

import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;

public class UsageController {

    static HostServices hostServices;

    @FXML
    private Hyperlink loopMIDI;

    @FXML
    private Hyperlink loopBe;

    @FXML
    private ImageView mmcSettingsTabNC;

    @FXML
    private Label howToUse;

    @FXML
    private Label settingsTab;

    @FXML
    private Label chooseInputDevice;

    @FXML
    private Label chooseInputDeviceCaption;

    @FXML
    private Label chooseOutputDevice;

    @FXML
    private Label chooseOutputDeviceCaption;

    @FXML
    private Label toScanDevices;

    @FXML
    private Label toConnectToDevices;

    @FXML
    private Label toConnectToDevicesCaption;

    @FXML
    private ImageView mmcSettingsTabC;

    @FXML
    private Label enableDebug;

    @FXML
    private Label enableDebugCaption;

    @FXML
    private Label panicButton;

    @FXML
    private Label panicButtonCaption;

    @FXML
    private Label toSerialDevice;

    @FXML
    private Label likeArduino;

    @FXML
    private Label toDisconnect;

    @FXML
    private Label setMessagesTab;

    @FXML
    private ImageView mmcKeyToClick;

    @FXML
    private Label keyToClick;

    @FXML
    private Label keyToClickCaption;

    @FXML
    private ImageView mmcMidiChannel;

    @FXML
    private Label midiChannel;

    @FXML
    private Label midiChannelCaption;

    @FXML
    private ImageView mmcSetMessageKeys;

    @FXML
    private Label keysToSend;

    @FXML
    private Label keysToSendCaption;

    @FXML
    private Label howManyNotes;

    @FXML
    private Label howManyNotesCaption;

    @FXML
    private Label velocityFromInput;

    @FXML
    private Label velocityFromInputCaption;

    @FXML
    private Label velocityFromNumber;

    @FXML
    private Label velocityFromNumberCaption;

    @FXML
    private ImageView mmcSetMessageCC;

    @FXML
    private Label selectCCNumber;

    @FXML
    private Label selectCCNumberCaption;

    @FXML
    private Hyperlink midiCCList;

    @FXML
    private Label selectValue;

    @FXML
    private Label selectValueCaption;

    @FXML
    private Label returnCC;

    @FXML
    private Label returnCCCaption;

    @FXML
    private Label addEvent;

    @FXML
    private Label addEventCaption;

    @FXML
    private Label deleteEvent;

    @FXML
    private Label deleteEventCaption;

    @FXML
    private Label messageEvent;

    @FXML
    private TextArea messageEventCaption;

    @FXML
    private ImageView mmcFile;

    @FXML
    private Label open;

    @FXML
    private Label openCaption;

    @FXML
    private Label save;

    @FXML
    private Label saveCaption;

    @FXML
    private ImageView mmcAddEvent;

    @FXML
    private Label eventsListTab;

    @FXML
    private ImageView mmcEventsListTabList;

    @FXML
    private ImageView mmcEventsListTabDelete;

    @FXML
    private Label showSerialJson;

    @FXML
    private Label showSerialJsonCaption;

    @FXML
    private ImageView mmcSetMessageSerial;

    @FXML
    private Label key;

    @FXML
    private Label keyCaption1;

    @FXML
    private Label keyCaptionKey;

    @FXML
    private Label keyCaption2;

    @FXML
    private Label keyValue;

    @FXML
    private Label keyValueCaption1;

    @FXML
    private Label keyValueValue;

    @FXML
    private Label keyValueCaption2;

    public UsageController(){

    }

    @FXML
    private void initialize(){

        loopMIDI.setBorder(Border.EMPTY);
        loopBe.setBorder(Border.EMPTY);
        midiCCList.setBorder(Border.EMPTY);

        mmcSettingsTabNC.setImage(new Image(MainApp.class.getResourceAsStream("helpImg/mmcSettingsNC.png")));
        mmcSettingsTabNC.setFitWidth(421);
        mmcSettingsTabNC.setFitHeight(203);

        mmcSettingsTabC.setImage(new Image(MainApp.class.getResourceAsStream("helpImg/mmcSettingsC.png")));
        mmcSettingsTabC.setFitWidth(442);
        mmcSettingsTabC.setFitHeight(238);

        mmcKeyToClick.setImage(new Image(MainApp.class.getResourceAsStream("helpImg/mmcNoteToClick.png")));
        mmcKeyToClick.setFitWidth(148);
        mmcKeyToClick.setFitHeight(36);

        mmcMidiChannel.setImage(new Image(MainApp.class.getResourceAsStream("helpImg/mmcMidiChannel.png")));
        mmcMidiChannel.setFitWidth(158);
        mmcMidiChannel.setFitHeight(34);

        mmcSetMessageKeys.setImage(new Image(MainApp.class.getResourceAsStream("helpImg/mmcSetMessageKeys.png")));
        mmcSetMessageKeys.setFitWidth(543);
        mmcSetMessageKeys.setFitHeight(164);

        mmcSetMessageCC.setImage(new Image(MainApp.class.getResourceAsStream("helpImg/mmcSetMessageCC.png")));
        mmcSetMessageCC.setFitWidth(405);
        mmcSetMessageCC.setFitHeight(118);

        mmcAddEvent.setImage(new Image(MainApp.class.getResourceAsStream("helpImg/mmcSetMessageAddEvent.png")));
        mmcAddEvent.setFitHeight(117);
        mmcAddEvent.setFitHeight(54);

        mmcFile.setImage(new Image(MainApp.class.getResourceAsStream("helpImg/mmcFile.png")));
        mmcFile.setFitWidth(89);
        mmcFile.setFitHeight(119);

        mmcEventsListTabList.setImage(new Image(MainApp.class.getResourceAsStream("helpImg/mmcEventsListList.png")));
        mmcEventsListTabList.setFitWidth(351);
        mmcEventsListTabList.setFitHeight(206);

        mmcEventsListTabDelete.setImage(new Image(MainApp.class.getResourceAsStream("helpImg/mmcEventsListButtons.png")));
        mmcEventsListTabDelete.setFitWidth(131);
        mmcEventsListTabDelete.setFitHeight(77);

        mmcSetMessageSerial.setImage(new Image(MainApp.class.getResourceAsStream("helpImg/mmcSetMessageSerial.png")));
        mmcSetMessageSerial.setFitWidth(250);
        mmcSetMessageSerial.setFitHeight(136);

    }

    @FXML
    private void handleLoopMIDI(){
        hostServices.showDocument("https://www.tobias-erichsen.de/software/loopmidi.html");
        loopMIDI.setVisited(false);
    }

    @FXML
    private void handleLoopBe(){
        hostServices.showDocument("https://www.nerds.de/en/loopbe1.html");
        loopBe.setVisited(false);
    }

    @FXML
    private void handleMidiCCList(){
        hostServices.showDocument("https://www.midi.org/specifications-old/item/table-3-control-change-messages-data-bytes-2");
        midiCCList.setVisited(false);
    }
}
