import com.google.gson.Gson;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.controlsfx.control.textfield.CustomPasswordField;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MainController {

    private ArrayList<LedColor> colorsToAdd = new ArrayList<>();
    private Integer noteToAdd = 0;
    private MidiCommands midiCommands = new MidiCommands();
    private MidiController midiController;
    private Stage midiStage = new Stage();
    private Gson gson = new Gson();
    static boolean save = false;
    static String lastFile = "";

    @FXML
    private Spinner<Integer> ledNum;

    @FXML
    private TableView<Commands> cmdTable;

    @FXML
    private TableColumn<Commands,String> noteColumn;

    @FXML
    private TableColumn<Commands,String> ledColumn;

    @FXML
    private TableView<LedColor> colorTable;

    @FXML
    private TableColumn<LedColor,Integer> colorLedColumn;

    @FXML
    private TableColumn<LedColor,Rectangle> ledColorColumn;

    @FXML
    private CustomTextField addNote;

    @FXML
    private Spinner<Integer> addLedNum;

    @FXML
    private ColorPicker addLedColor;

    @FXML
    private TableView<LedColor> addLedsTable;

    @FXML
    private TableColumn<LedColor,Integer> addLedsColumn;

    @FXML
    private TableColumn<LedColor,Rectangle> addColorColumn;

    @FXML
    private CustomTextField ip;

    @FXML
    private CustomTextField login;

    @FXML
    private CustomPasswordField password;

    @FXML
    private CustomTextField fileName;

    @FXML
    public void initialize(){
        try{
            FXMLLoader loaderMain = new FXMLLoader();
            loaderMain.setLocation(MainApp.class.getResource("midiLayout.fxml"));
            AnchorPane anchorPane = loaderMain.load();
            Scene scene = new Scene(anchorPane);
            midiStage.setScene(scene);
            midiController = loaderMain.getController();
            midiStage.setOnShowing(event -> midiController.onShow());
        }catch (Exception e){
            new TextAlertGenerator(e, Alert.AlertType.ERROR);
        }
        Glyph cross1 = new Glyph("FontAwesome", FontAwesome.Glyph.TIMES);
        cross1.setColor(javafx.scene.paint.Color.RED);
        Glyph cross2 = new Glyph("FontAwesome", FontAwesome.Glyph.TIMES);
        cross2.setColor(javafx.scene.paint.Color.RED);
        Glyph cross3 = new Glyph("FontAwesome", FontAwesome.Glyph.TIMES);
        cross3.setColor(javafx.scene.paint.Color.RED);
        Glyph cross4 = new Glyph("FontAwesome", FontAwesome.Glyph.QUESTION);
        cross4.setColor(javafx.scene.paint.Color.rgb(168, 135, 0));
        ip.setRight(cross1);
        login.setRight(cross2);
        password.setRight(cross3);
        fileName.setRight(cross4);

        ledNum.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,Integer.MAX_VALUE));
        noteColumn.setCellValueFactory(data -> new SimpleStringProperty(midiCommands.getParam1().get(data.getValue().getNote())));
        ledColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().ledsNumberToString()));

        colorLedColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getLed()));
        ledColorColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(new Rectangle(10,10,javafx.scene.paint.Color.rgb(data.getValue().getColor().getRed(),data.getValue().getColor().getGreen(),data.getValue().getColor().getBlue()))));

        addLedsColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getLed()));
        addColorColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(new Rectangle(10,10,javafx.scene.paint.Color.rgb(data.getValue().getColor().getRed(),data.getValue().getColor().getGreen(),data.getValue().getColor().getBlue()))));

        ip.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(InetAddressValidator.getInstance().isValidInet4Address(newValue)){
                ip.setRight(new Glyph());
            }else{
                ip.setRight(cross1);
            }
        }));
        login.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue.trim().isEmpty()){
                login.setRight(cross2);
            }else{
               login.setRight(new Glyph());
            }
        }));
        password.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue.trim().isEmpty()){
                password.setRight(cross3);
            }else{
                password.setRight(new Glyph());
            }
        }));
        fileName.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(!newValue.trim().isEmpty() && newValue.matches("([\\w\\d]+[.py]{3})")){
                fileName.setRight(new Glyph());
            }else{
                fileName.setRight(cross4);
            }
        }));

        cmdTable.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            colorTable.getItems().clear();
            ArrayList<LedColor> ledColors = new ArrayList<>();
            newValue.getLedsNumber().forEach((led,color)->ledColors.add(new LedColor(led,color)));
            ledColors.forEach(ledColor -> colorTable.getItems().add(ledColor));
        }));

        addLedsTable.setRowFactory(tv -> {
            TableRow<LedColor> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2 && !row.isEmpty() && event.getButton().equals(MouseButton.PRIMARY)){
                    colorsToAdd.remove(row.getItem());
                    addLedsTable.getItems().remove(row.getItem());
                }
            });
            return row;
        });
        cmdTable.setRowFactory(tv -> {
            TableRow<Commands> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2 && !row.isEmpty() && event.getButton().equals(MouseButton.PRIMARY)){
                    cmdTable.getItems().remove(row.getItem());
                }
            });
            return row;
        });

        ledNum.valueProperty().addListener(((observable, oldValue, newValue) -> addLedNum.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,newValue))));
        Glyph cross = new Glyph("FontAwesome", FontAwesome.Glyph.TIMES);
        cross.setColor(javafx.scene.paint.Color.RED);
        addNote.setRight(cross);

        addNote.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(midiCommands.getParam1().containsValue(newValue.toUpperCase()) && !newValue.trim().isEmpty()){
                addNote.setRight(new Glyph());
                noteToAdd = (int)midiCommands.getKeyFromValue(midiCommands.getParam1(),addNote.getText().toUpperCase());
            }else{
                addNote.setRight(cross);
                noteToAdd = 0;
            }
        }));
    }

    List<Commands> getCommands(){
        return cmdTable.getItems();
    }

    Integer getLeds(){
        return ledNum.getValue();
    }

    void setCommands(List<Commands> commands){
        cmdTable.getItems().clear();
        cmdTable.getItems().setAll(commands);
    }

    void setLeds(Integer led){
        ledNum.getValueFactory().setValue(led);
        addLedNum.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,led));
    }

    @FXML
    public void openMidi(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select MIDI file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MIDI files (*.mid)","*.mid"));
        File file = fileChooser.showOpenDialog(null);
        if(file != null){
            MidiController.midiFile = file;
            midiStage.show();
        }
    }

    @FXML
    public void addLed(){
        boolean contains = false;
        for (LedColor l: colorsToAdd) {
            if(Objects.equals(l.getLed(), addLedNum.getValue())){
                contains = true;
            }
        }
        if(!contains){
            if(addLedNum.getValue() != null && addLedNum.getValue() != 0) {
                Color color = new Color((int) (addLedColor.getValue().getRed() * 255), (int) (addLedColor.getValue().getGreen() * 255), (int) (addLedColor.getValue().getBlue() * 255));
                LedColor toAdd = new LedColor(addLedNum.getValue(), color);
                colorsToAdd.add(toAdd);
                addLedsTable.getItems().add(toAdd);
            }
        }
    }

    @FXML
    public void addToActive(){
        if(noteToAdd != 0){
            if(!colorsToAdd.isEmpty()){
                boolean contains = false;
                for (Commands c: cmdTable.getItems()) {
                    if(Objects.equals(noteToAdd, c.getNote())){
                        contains = true;
                    }
                }
                if(!contains) {
                    HashMap<Integer, Color> toAdd = new HashMap<>();
                    for (LedColor l : colorsToAdd) {
                        toAdd.put(l.getLed(), l.getColor());
                    }
                    cmdTable.getItems().add(new Commands(noteToAdd, toAdd));
                    colorsToAdd.clear();
                    addLedsTable.getItems().clear();
                }
            }
        }
    }

    @FXML
    public void generate() {
            if (!cmdTable.getItems().isEmpty()) {
                if (InetAddressValidator.getInstance().isValidInet4Address(ip.getText()) && !login.getText().trim().isEmpty() && !password.getText().trim().isEmpty() && fileName.getText().trim().isEmpty()) {
                    try {
                        new PythonGenerator(this, cmdTable.getItems(), ip.getText(), login.getText(), password.getText(), null, ledNum.getValue());
                        //TODO: Add spinner
                    } catch (Exception e) {
                        new TextAlertGenerator(e, Alert.AlertType.ERROR);
                    }
                } else if (InetAddressValidator.getInstance().isValidInet4Address(ip.getText()) && !login.getText().trim().isEmpty() && !password.getText().trim().isEmpty() &&
                        !fileName.getText().trim().isEmpty() && fileName.getText().matches("([\\w\\d]+[.py]{3})")) {
                    try {
                        new PythonGenerator(this, cmdTable.getItems(), ip.getText(), login.getText(), password.getText(), fileName.getText(), ledNum.getValue());
                        //TODO: Add spinner
                    } catch (Exception e) {
                        new TextAlertGenerator(e, Alert.AlertType.ERROR);
                    }
                }
            }
    }

}
