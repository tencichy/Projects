package com.damiskot.midi;

import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import java.util.Objects;

public class MidiThread extends Thread {

    private MidiCommands commands = new MidiCommands();

    static int[] inputValue = new int[3];
    public static Receiver output;

    private Object synchroObject;
    private String keyToClick;
    private int howManyNotes;
    private boolean backMessage;
    private ShortMessage[] msgOn;
    private ShortMessage[] msgOff;

    private volatile boolean run;


    public MidiThread(Object synchroObject,String keyToClick, int howManyNotes,ShortMessage[] msgOn, ShortMessage[] msgOff){
        this.synchroObject = synchroObject;
        this.keyToClick = keyToClick;
        this.howManyNotes = howManyNotes;
        this.msgOn = msgOn;
        this.msgOff = msgOff;
    }

    public MidiThread(Object synchroObject,String keyToClick,boolean backMessage,ShortMessage[] msgOn, ShortMessage[] msgOff){
        this.synchroObject = synchroObject;
        this.keyToClick = keyToClick;
        this.backMessage = backMessage;
        this.howManyNotes = 0;
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
        while (run){
            synchronized (synchroObject) {
                try {
                    synchroObject.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (run){
                    if (Objects.equals(commands.getParam1().get(inputValue[1] + 12), keyToClick)) {
                        if (inputValue[0] == 0) {
                            if (howManyNotes == 0) {
                                if (backMessage) {
                                    output.send(msgOff[0], timeStamp);
                                }
                            } else if (howManyNotes == 1) {
                                output.send(msgOff[0], timeStamp);
                            } else if (howManyNotes == 2) {
                                output.send(msgOff[0], timeStamp);
                                output.send(msgOff[1], timeStamp);
                            } else if (howManyNotes == 3) {
                                output.send(msgOff[0], timeStamp);
                                output.send(msgOff[1], timeStamp);
                                output.send(msgOff[2], timeStamp);
                            } else if (howManyNotes == 4) {
                                output.send(msgOff[0], timeStamp);
                                output.send(msgOff[1], timeStamp);
                                output.send(msgOff[2], timeStamp);
                                output.send(msgOff[3], timeStamp);
                            }
                            inputValue[1] = 0;
                        } else if (inputValue[0] == 1) {
                            if (howManyNotes == 1 || howManyNotes == 0) {
                                output.send(msgOn[0], timeStamp);
                            } else if (howManyNotes == 2) {
                                output.send(msgOn[0], timeStamp);
                                output.send(msgOn[1], timeStamp);
                            } else if (howManyNotes == 3) {
                                output.send(msgOn[0], timeStamp);
                                output.send(msgOn[1], timeStamp);
                                output.send(msgOn[2], timeStamp);
                            } else if (howManyNotes == 4) {
                                output.send(msgOn[0], timeStamp);
                                output.send(msgOn[1], timeStamp);
                                output.send(msgOn[2], timeStamp);
                                output.send(msgOn[3], timeStamp);
                            }
                            inputValue[1] = 0;

                        }
                    }
                }


            }
        }


    }
}
