import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class EntryController {

    static ShopEntry shopEntry;
    static SController sController;

    @FXML
    private Spinner<Integer> quantitySpinner;

    @FXML
    private void initialize(){
        quantitySpinner.setEditable(true);
    }

    void afterShow(){
        quantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,Integer.MAX_VALUE,shopEntry.getQuantity()));
    }

    @FXML
    private void removeAction(){
        sController.removeEntry(shopEntry);
    }

    @FXML
    private void updateAction(){
        sController.updateEntry(shopEntry,new ShopEntry(shopEntry.getID(),shopEntry.getName(),shopEntry.getBrand(),shopEntry.getQuantityBefore(),quantitySpinner.getValue(),shopEntry.getPrice()));
    }
}
