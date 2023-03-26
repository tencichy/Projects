import com.google.gson.Gson;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class IncomeController {

    static Date from = null;
    static Date to = null;

    private ArrayList<ChangeEntry> changeEntries = new ArrayList<>();
    private Gson gson = new Gson();

    @FXML
    private TableView<dayEntry> daysTable;

    @FXML
    private TableView<ChangeEntry> dayIncomeTable;

    @FXML
    private TableColumn<dayEntry,String> daysColumn;

    @FXML
    private TableColumn<ChangeEntry, String> dayIncomeColumn;

    @FXML
    private TextArea dayIncomeDetail;

    @FXML
    private void initialize(){
        getData();
        daysColumn.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getDate().toString() + " - " + value.getValue().getEntriesSum() + " PLN"));
        dayIncomeColumn.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getName() + " " + value.getValue().getSurname() + " - " + value.getValue().getRequestTime() + " - " + value.getValue().getRequestChangeShop().getSum() + " PLN"));
        daysTable.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue != null) {
                dayIncomeTable.getItems().clear();
                for (ChangeEntry c: newValue.changeEntries) {
                    dayIncomeTable.getItems().add(c);
                }
            }
        }));
        dayIncomeTable.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue != null){
                dayIncomeDetail.setText(newValue.getRequestChangeShop().toString());
            }
        }));
    }

    void afterShow(){
        if((from != null && to == null) || ((from != null ? from.toLocalDate().compareTo(to.toLocalDate()) : 0) == 0)){
            ArrayList<ChangeEntry> data= new ArrayList<>();
            for (ChangeEntry c: changeEntries) {
                if(c.getRequestDate().toLocalDate().compareTo(from.toLocalDate()) == 0){
                    data.add(c);
                }
            }
            daysTable.getItems().clear();
            daysTable.getItems().add(new dayEntry(from,data));
        }
        if(from != null && to != null){
            daysTable.getItems().clear();
            List<LocalDate> totalDates = new ArrayList<>();
            List<dayEntry> dayEntries = new ArrayList<>();
            while (!from.toLocalDate().isAfter(to.toLocalDate())) {
                totalDates.add(from.toLocalDate());
                from = Date.valueOf(from.toLocalDate().plusDays(1));
            }
            for (LocalDate d: totalDates) {
                List<ChangeEntry> data = new ArrayList<>();
                for (ChangeEntry c: changeEntries) {
                    if(c.getRequestDate().toLocalDate().compareTo(d) == 0){
                        data.add(c);
                    }
                }
                dayEntries.add(new dayEntry(Date.valueOf(d),data));
            }
            for (dayEntry d: dayEntries) {
                daysTable.getItems().add(d);
            }

        }
    }

    private void getData(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/inventory?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "inventoryApp", "JaVa!@#$App");
            Statement statement = connect.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from changes");
            while (resultSet.next()) {
                if(resultSet.getInt("requestType") == 1){
                    changeEntries.add(new ChangeEntry(resultSet.getInt("ID"),resultSet.getString("name"),resultSet.getString("surname"),resultSet.getInt("PESEL"),resultSet.getDate("requestDate"),resultSet.getTime("requestTime"),gson.fromJson(resultSet.getString("requestChange"),ShopChanges.class),resultSet.getInt("requestType")));
                }
            }
        }catch (Exception e){
            new TextAlertGenerator(e, Alert.AlertType.ERROR);
        }
    }

    class dayEntry{
        private Date date;
        private List<ChangeEntry> changeEntries;

        public dayEntry(Date date, List<ChangeEntry> changeEntries) {
            this.date = date;
            this.changeEntries = changeEntries;
        }

        public Date getDate() {
            return date;
        }

        public Double getEntriesSum(){
            Double returnSum = 0.0;
            for (ChangeEntry c: changeEntries) {
                returnSum += c.getRequestChangeShop().getSum();
            }
            return Math.round((returnSum)*100)/100.0;
        }

        public List<ChangeEntry> getChangeEntries() {
            return changeEntries;
        }
    }

}
