package com.damiskot.midi;

import com.damiskot.json.JsonThread;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.sound.midi.ShortMessage;

public class MidiTable {

    private MidiCommands commands = new MidiCommands();

    private StringProperty showMessage;
    private Integer keyToClick;
    private JsonThread jsonThread;
    private Thread thread;

    public MidiTable(Thread thread, JsonThread jsonThread, String keyToClick, int howManyKeys, ShortMessage[] notesOn) {
        this.thread = thread;
        this.jsonThread = jsonThread;
        this.keyToClick = (Integer)commands.getKeyFromValue(commands.getParam1(),keyToClick);
        if(notesOn[0].getData2() == 0){
            if (howManyKeys == 1) {
                this.showMessage = new SimpleStringProperty(keyToClick + " - " + "{ Ch: " + notesOn[0].getChannel() + ", Cmd: Note On, Note:  " + commands.getParam1().get(notesOn[0].getData1()+12) + ", Vel: From input }");
            } else if (howManyKeys == 2) {
                this.showMessage = new SimpleStringProperty(keyToClick + " - " + "{ Ch: " + notesOn[0].getChannel() + ", Cmd: Note On, Notes:  " + commands.getParam1().get(notesOn[0].getData1()+12) + ";" + commands.getParam1().get(notesOn[1].getData1()+12) + ", Vel: From input }");
            } else if (howManyKeys == 3) {
                this.showMessage = new SimpleStringProperty(keyToClick + " - " + "{ Ch: " + notesOn[0].getChannel() + ", Cmd: Note On, Notes:  " + commands.getParam1().get(notesOn[0].getData1()+12) + ";" + commands.getParam1().get(notesOn[1].getData1()+12) + ";" + commands.getParam1().get(notesOn[2].getData1()+12) + ", Vel: From input }");
            } else if (howManyKeys == 4) {
                this.showMessage = new SimpleStringProperty(keyToClick + " - " + "{ Ch: " + notesOn[0].getChannel() + ", Cmd: Note On, Notes:  " + commands.getParam1().get(notesOn[0].getData1()+12) + ";" + commands.getParam1().get(notesOn[1].getData1()+12) + ";" + commands.getParam1().get(notesOn[2].getData1()+12) + ";" + commands.getParam1().get(notesOn[3].getData1()+12) + ", Vel: From input }");
            }
        }else {
            if (howManyKeys == 1) {
                this.showMessage = new SimpleStringProperty(keyToClick + " - " + "{ Ch: " + notesOn[0].getChannel() + ", Cmd: Note On, Note:  " + commands.getParam1().get(notesOn[0].getData1()+12) + ", Vel: " + notesOn[0].getData2() + " }");
            } else if (howManyKeys == 2) {
                this.showMessage = new SimpleStringProperty(keyToClick + " - " + "{ Ch: " + notesOn[0].getChannel() + ", Cmd: Note On, Notes:  " + commands.getParam1().get(notesOn[0].getData1()+12)  + ";" + commands.getParam1().get(notesOn[1].getData1()+12) + ", Vel: " + notesOn[0].getData2() + " }");
            } else if (howManyKeys == 3) {
                this.showMessage = new SimpleStringProperty(keyToClick + " - " + "{ Ch: " + notesOn[0].getChannel() + ", Cmd: Note On, Notes:  " + commands.getParam1().get(notesOn[0].getData1()+12) + ";" + commands.getParam1().get(notesOn[1].getData1()+12) + ";" + commands.getParam1().get(notesOn[2].getData1()+12) + ", Vel: " + notesOn[0].getData2() + " }");
            } else if (howManyKeys == 4) {
                this.showMessage = new SimpleStringProperty(keyToClick + " - " + "{ Ch: " + notesOn[0].getChannel() + ", Cmd: Note On, Notes:  " + commands.getParam1().get(notesOn[0].getData1()+12) + ";" + commands.getParam1().get(notesOn[1].getData1()+12) + ";" + commands.getParam1().get(notesOn[2].getData1()+12) + ";" + commands.getParam1().get(notesOn[3].getData1()+12) + ", Vel: " + notesOn[0].getData2() + " }");
            }
        }
    }

    public MidiTable(Thread thread, JsonThread jsonThread, String keyToClick, boolean returnCC, ShortMessage[] notesOn){
        this.thread = thread;
        this.jsonThread = jsonThread;
        this.keyToClick = (Integer)commands.getKeyFromValue(commands.getParam1(),keyToClick);
        if(returnCC){
            this.showMessage = new SimpleStringProperty(keyToClick + " - " + "{ Ch: " + notesOn[0].getChannel() + ", Cmd: CC, CC Num:  " + notesOn[0].getData1() + ", CC Val: " + notesOn[0].getData2() + " Return CC: true }");
        }else {
            this.showMessage = new SimpleStringProperty(keyToClick + " - " + "{ Ch: " + notesOn[0].getChannel() + ", Cmd: CC, CC Num:  " + notesOn[0].getData1() + ", CC Val: " + notesOn[0].getData2() + " Return CC: false }");
        }
    }

    public StringProperty showMessageProperty() {
        return showMessage;
    }

    public Thread getThread() {
        return thread;
    }

    public Integer getKeyToClick() {
        return keyToClick;
        }

    public JsonThread getJsonThread() {
        return jsonThread;
    }

}
