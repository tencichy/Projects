import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.sound.midi.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MidiController {

    static File midiFile;
    private MidiCommands midiCommands = new MidiCommands();

    @FXML
    private TableView<note> notes;

    @FXML
    private TableColumn<note,String> noteColumn;

    @FXML
    private TableColumn<note,Integer> numberColumn;

    @FXML
    private Label totalNum;

    @FXML
    public void initialize(){
        noteColumn.setCellValueFactory(val -> new SimpleObjectProperty<>(midiCommands.getParam1().get(val.getValue().note)));
        numberColumn.setCellValueFactory(val -> new SimpleObjectProperty<>(val.getValue().uses));
    }

     void onShow(){
        if(midiFile != null){
            HashMap<Integer,Integer> midiNotes = new HashMap<>();
            try {
                final int NOTE_ON = 0x90;
                Sequence sequence = MidiSystem.getSequence(midiFile);
                for (Track track :  sequence.getTracks()) {
                    for (int i=0; i < track.size(); i++) {
                        MidiEvent event = track.get(i);
                        MidiMessage message = event.getMessage();
                        if (message instanceof ShortMessage) {
                            ShortMessage sm = (ShortMessage) message;
                            if (sm.getCommand() == NOTE_ON) {
                                if(sm.getData2() > 0){
                                    if(midiNotes.containsKey(sm.getData1())){
                                        midiNotes.merge(sm.getData1(),1,Integer::sum);
                                    }else {
                                        midiNotes.put(sm.getData1(),1);
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                new TextAlertGenerator(e, Alert.AlertType.ERROR);
            }
            for (Map.Entry<Integer,Integer> e: midiNotes.entrySet()) {
                notes.getItems().add(new note(e.getKey(),e.getValue()));
            }
            totalNum.setText("Total number of notes: " + (midiNotes.size()+1));
        }
    }

    @AllArgsConstructor
    @Getter
    @Setter
    private class note{
        private Integer note;
        private Integer uses;
    }

}
