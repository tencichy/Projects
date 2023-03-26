package com.damiskot.midi;

import com.damiskot.TextAlertGenerator;
//import com.damiskot.serial.SerialThread;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

import javax.sound.midi.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MidiConnect {

    public static List<Integer> usedNotes = new ArrayList<>();
    public static Receiver output;

    public void close(MidiDevice.Info devInfoIn, MidiDevice.Info devInfoOut, TextArea textArea, CheckBox checkBox) {

        MidiDevice deviceIn;
        MidiDevice deviceOut;
        try {
            deviceIn = MidiSystem.getMidiDevice(devInfoIn);
            deviceOut = MidiSystem.getMidiDevice(devInfoOut);

            deviceIn.close();
            deviceOut.close();

            if(checkBox.isSelected()){Platform.runLater(() -> textArea.appendText("Input: " + deviceIn.getDeviceInfo()+" Was Closed" + "\n"));}
            if(checkBox.isSelected()){Platform.runLater(() -> textArea.appendText("Output: " + deviceOut.getDeviceInfo()+" Was Closed" + "\n"));}


        } catch (MidiUnavailableException e) {
            new TextAlertGenerator(e, Alert.AlertType.ERROR);
        }

    }

    public void connect(MidiDevice.Info devInfoIn, MidiDevice.Info devInfoOut , TextArea textArea, CheckBox checkBox, List<ComboBox> noteComboBoxes, MidiCommands MidiCommands,Object synchroObject)
    {
        MidiDevice deviceIn;
        MidiDevice deviceOut;
            try {
                deviceIn = MidiSystem.getMidiDevice(devInfoIn);
                deviceOut = MidiSystem.getMidiDevice(devInfoOut);

                List<Transmitter> transmitters = deviceIn.getTransmitters();

                for(int j = 0; j<transmitters.size();j++) {

                    transmitters.get(j).setReceiver(

                            new MidiInputReceiver(synchroObject,textArea, checkBox, noteComboBoxes, MidiCommands)
                    );
                }

                deviceIn.open();
                deviceOut.open();

                if(checkBox.isSelected()){Platform.runLater(() -> textArea.appendText("Input: " + deviceIn.getDeviceInfo()+" Was Opened" + "\n"));}
                if(checkBox.isSelected()){Platform.runLater(() -> textArea.appendText("Output: " + deviceOut.getDeviceInfo()+" Was Opened" + "\n"));}

            } catch (MidiUnavailableException e) {
                new TextAlertGenerator(e,Alert.AlertType.ERROR);
                }

    }

    public class MidiInputReceiver implements Receiver {

        private Object synchroObject;
        private MidiCommands MidiCommands;
        private TextArea textArea;
        private CheckBox checkBox;
        private List<ComboBox> noteComboBoxes;
        private MidiInputReceiver(Object synchroObject,TextArea textArea, CheckBox checkBox, List<ComboBox> noteComboBoxes,MidiCommands MidiCommands) {
            this.synchroObject = synchroObject;
            this.textArea = textArea;
            this.MidiCommands = MidiCommands;
            this.checkBox = checkBox;
            this.noteComboBoxes = noteComboBoxes;
        }


        public void send(MidiMessage msg, long timeStamp) {

                if(checkBox.isSelected()){Platform.runLater(() -> textArea.appendText("[" + "\n"));}
                if(checkBox.isSelected()){Platform.runLater(() -> textArea.appendText("Midi received: Message: " + Arrays.toString(msg.getMessage()) + " Status: " + msg.getStatus() + " Length: " + msg.getLength() + " Time stamp: " + timeStamp + "\n"));}
                byte[] tempB = msg.getMessage();
                String[] msgInStrings = new String[tempB.length];
                for (int i = 0; i < tempB.length; i++) {
                    msgInStrings[i] = Byte.toString(tempB[i]);
                }

               if(checkBox.isSelected()){Platform.runLater(() -> textArea.appendText(MidiCommands.checkCom(msgInStrings) + "\n"));}
               if(checkBox.isSelected()){Platform.runLater(() -> textArea.appendText("]" + "\n"));}

               if(Integer.valueOf(msgInStrings[0]) >= -32 && Integer.valueOf(msgInStrings[0]) <= -17){
                   try {
                       output.send(new ShortMessage(ShortMessage.PITCH_BEND,MidiCommands.getChannel().get((int) msg.getMessage()[0]),Integer.valueOf(msgInStrings[1]),Integer.valueOf(msgInStrings[2])),timeStamp);
                   } catch (InvalidMidiDataException e) {
                       new TextAlertGenerator(e, Alert.AlertType.ERROR);
                   }
               }

               if(Integer.valueOf(msgInStrings[0]) >= -80 && Integer.valueOf(msgInStrings[0]) <= -65){
                   try {
                       output.send(new ShortMessage(ShortMessage.CONTROL_CHANGE,MidiCommands.getChannel().get((int) msg.getMessage()[0]),Integer.valueOf(msgInStrings[1]),Integer.valueOf(msgInStrings[2])),timeStamp);
                   } catch (InvalidMidiDataException e) {
                       new TextAlertGenerator(e, Alert.AlertType.ERROR);
                   }
               }

                if(Integer.valueOf(msgInStrings[0]) >= -128 && Integer.valueOf(msgInStrings[0]) <= -113){ //Notes off
                    if(!usedNotes.contains(Integer.valueOf(msgInStrings[1]))){
                        try {
                            output.send(new ShortMessage(ShortMessage.NOTE_OFF, MidiCommands.getChannel().get((int) msg.getMessage()[0]), Integer.valueOf(msgInStrings[1]), Integer.valueOf(msgInStrings[2])), timeStamp);
                        } catch (InvalidMidiDataException e) {
                            new TextAlertGenerator(e, Alert.AlertType.ERROR);
                        }
                    }else{

                        synchronized (synchroObject) {
                            MidiThread.inputValue[0] = 0;
                            MidiThread.inputValue[1] = Integer.valueOf(msgInStrings[1]);
                            MidiThread.inputValue[2] = Integer.valueOf(msgInStrings[2]);
                            MidiVelocityThread.inputValue[0] = 0;
                            MidiVelocityThread.inputValue[1] = Integer.valueOf(msgInStrings[1]);
                            MidiVelocityThread.inputValue[2] = Integer.valueOf(msgInStrings[2]);
                            synchroObject.notifyAll();
                        }
                    }
                }else if(Integer.valueOf(msgInStrings[0]) >= -112 && Integer.valueOf(msgInStrings[0]) <= -97){ //Notes on
                    if(!usedNotes.contains(Integer.valueOf(msgInStrings[1]))){
                        try {
                            checkForNote(noteComboBoxes, MidiCommands, Integer.valueOf(msgInStrings[1]));
                            output.send(new ShortMessage(ShortMessage.NOTE_ON, MidiCommands.getChannel().get((int) msg.getMessage()[0]), Integer.valueOf(msgInStrings[1]), Integer.valueOf(msgInStrings[2])), timeStamp);
                        } catch (InvalidMidiDataException e) {
                            new TextAlertGenerator(e, Alert.AlertType.ERROR);
                        }
                    }else{

                        synchronized (synchroObject){
                            MidiThread.inputValue[0] = 1;
                            MidiThread.inputValue[1] = Integer.valueOf(msgInStrings[1]);
                            MidiThread.inputValue[2] = Integer.valueOf(msgInStrings[2]);
                            MidiVelocityThread.inputValue[0] = 1;
                            MidiVelocityThread.inputValue[1] = Integer.valueOf(msgInStrings[1]);
                            MidiVelocityThread.inputValue[2] = Integer.valueOf(msgInStrings[2]);
                            synchroObject.notifyAll();
                        }

                    }

                }

        }
        public void close() {

        }

    }

    @SuppressWarnings("unchecked")
    private void checkForNote(List<ComboBox> noteComboBoxes,MidiCommands midiCommands, Integer note){
        if(noteComboBoxes.get(0).isShowing()){
            //TODO: Copy for chords section
        }else if(noteComboBoxes.get(1).isShowing()){
            Platform.runLater(()->noteComboBoxes.get(1).getSelectionModel().select(midiCommands.getNote(note)));
        }else if(noteComboBoxes.get(2).isShowing()){
            Platform.runLater(()->noteComboBoxes.get(2).getSelectionModel().select(midiCommands.getNote(note)));
        }else if(noteComboBoxes.get(3).isShowing()){
            Platform.runLater(()->noteComboBoxes.get(3).getSelectionModel().select(midiCommands.getNote(note)));
        }else if(noteComboBoxes.get(4).isShowing()){
            Platform.runLater(()->noteComboBoxes.get(4).getSelectionModel().select(midiCommands.getNote(note)));
        }else if(noteComboBoxes.get(5).isShowing()){
            Platform.runLater(()->noteComboBoxes.get(5).getSelectionModel().select(midiCommands.getNote(note)));
        }
    }

}
