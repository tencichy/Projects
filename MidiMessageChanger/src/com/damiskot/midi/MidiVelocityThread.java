package com.damiskot.midi;

import com.damiskot.TextAlertGenerator;
import javafx.scene.control.Alert;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import java.util.Objects;

public class MidiVelocityThread extends Thread{
    private MidiCommands commands = new MidiCommands();

    static int[] inputValue = new int[3];
    public static Receiver output;

    private Object synchroObject;
    private String keyToClick;
    private int howManyNotes;
    private ShortMessage[] msgOn;
    private ShortMessage[] msgOff;

    private volatile boolean run;


    public MidiVelocityThread(Object synchroObject,String keyToClick, int howManyNotes,ShortMessage[] msgOn, ShortMessage[] msgOff){
        this.synchroObject = synchroObject;
        this.keyToClick = keyToClick;
        this.howManyNotes = howManyNotes;
        this.msgOn = msgOn;
        this.msgOff = msgOff;
    }

    @Override
    public void interrupt(){
        run = false;
        synchronized (synchroObject){
            synchroObject.notify();
        }
    }

    @Override
    public void run() {
        long timeStamp = -1;
        run = true;
        while (run) {
            synchronized (synchroObject) {
                try {
                    synchroObject.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (run){
                    if (Objects.equals(commands.getParam1().get(inputValue[1] + 12), keyToClick)) {
                        try {
                            if (inputValue[0] == 0) {
                                if (howManyNotes == 1) {
                                    output.send(new ShortMessage(ShortMessage.NOTE_OFF, msgOff[0].getChannel(), msgOff[0].getData1(), inputValue[2]), timeStamp);
                                } else if (howManyNotes == 2) {
                                    output.send(new ShortMessage(ShortMessage.NOTE_OFF, msgOff[0].getChannel(), msgOff[0].getData1(), inputValue[2]), timeStamp);
                                    output.send(new ShortMessage(ShortMessage.NOTE_OFF, msgOff[1].getChannel(), msgOff[1].getData1(), inputValue[2]), timeStamp);
                                } else if (howManyNotes == 3) {
                                    output.send(new ShortMessage(ShortMessage.NOTE_OFF, msgOff[0].getChannel(), msgOff[0].getData1(), inputValue[2]), timeStamp);
                                    output.send(new ShortMessage(ShortMessage.NOTE_OFF, msgOff[1].getChannel(), msgOff[1].getData1(), inputValue[2]), timeStamp);
                                    output.send(new ShortMessage(ShortMessage.NOTE_OFF, msgOff[2].getChannel(), msgOff[2].getData1(), inputValue[2]), timeStamp);
                                } else if (howManyNotes == 4) {
                                    output.send(new ShortMessage(ShortMessage.NOTE_OFF, msgOff[0].getChannel(), msgOff[0].getData1(), inputValue[2]), timeStamp);
                                    output.send(new ShortMessage(ShortMessage.NOTE_OFF, msgOff[1].getChannel(), msgOff[1].getData1(), inputValue[2]), timeStamp);
                                    output.send(new ShortMessage(ShortMessage.NOTE_OFF, msgOff[2].getChannel(), msgOff[2].getData1(), inputValue[2]), timeStamp);
                                    output.send(new ShortMessage(ShortMessage.NOTE_OFF, msgOff[3].getChannel(), msgOff[3].getData1(), inputValue[2]), timeStamp);
                                }
                                inputValue[1] = 0;
                            } else if (inputValue[0] == 1) {
                                if (howManyNotes == 1) {
                                    output.send(new ShortMessage(ShortMessage.NOTE_ON, msgOn[0].getChannel(), msgOn[0].getData1(), inputValue[2]), timeStamp);
                                } else if (howManyNotes == 2) {
                                    output.send(new ShortMessage(ShortMessage.NOTE_ON, msgOn[0].getChannel(), msgOn[0].getData1(), inputValue[2]), timeStamp);
                                    output.send(new ShortMessage(ShortMessage.NOTE_ON, msgOn[1].getChannel(), msgOn[1].getData1(), inputValue[2]), timeStamp);
                                } else if (howManyNotes == 3) {
                                    output.send(new ShortMessage(ShortMessage.NOTE_ON, msgOn[0].getChannel(), msgOn[0].getData1(), inputValue[2]), timeStamp);
                                    output.send(new ShortMessage(ShortMessage.NOTE_ON, msgOn[1].getChannel(), msgOn[1].getData1(), inputValue[2]), timeStamp);
                                    output.send(new ShortMessage(ShortMessage.NOTE_ON, msgOn[2].getChannel(), msgOn[2].getData1(), inputValue[2]), timeStamp);
                                } else if (howManyNotes == 4) {
                                    output.send(new ShortMessage(ShortMessage.NOTE_ON, msgOn[0].getChannel(), msgOn[0].getData1(), inputValue[2]), timeStamp);
                                    output.send(new ShortMessage(ShortMessage.NOTE_ON, msgOn[1].getChannel(), msgOn[1].getData1(), inputValue[2]), timeStamp);
                                    output.send(new ShortMessage(ShortMessage.NOTE_ON, msgOn[2].getChannel(), msgOn[2].getData1(), inputValue[2]), timeStamp);
                                    output.send(new ShortMessage(ShortMessage.NOTE_ON, msgOn[3].getChannel(), msgOn[3].getData1(), inputValue[2]), timeStamp);
                                }
                                inputValue[1] = 0;

                            }

                        } catch (InvalidMidiDataException e) {
                            new TextAlertGenerator(e, Alert.AlertType.ERROR);
                        }
                    }
                }

            }

        }
    }

}
