import java.sql.Date;
import java.sql.Time;

public class ChangeEntry {

    private Integer ID;
    private String name;
    private String surname;
    private Integer PESEL;
    private Date requestDate;
    private Time requestTime;
    private Changes requestChangeChanges;
    private ShopChanges requestChangeShop;
    private Integer requestType;

    public ChangeEntry(Integer ID,String name, String surname, Integer PESEL, Date requestDate, Time requestTime, Changes requestChangeChanges, Integer requestType) {
        this.ID = ID;
        this.name = name;
        this.surname = surname;
        this.PESEL = PESEL;
        this.requestDate = requestDate;
        this.requestTime = requestTime;
        this.requestChangeChanges = requestChangeChanges;
        this.requestType = requestType;
    }

    public ChangeEntry(Integer ID, String name, String surname, Integer PESEL, Date requestDate, Time requestTime, ShopChanges requestChangeShop, Integer requestType) {
        this.ID = ID;
        this.name = name;
        this.surname = surname;
        this.PESEL = PESEL;
        this.requestDate = requestDate;
        this.requestTime = requestTime;
        this.requestChangeShop = requestChangeShop;
        this.requestType = requestType;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Integer getPESEL() {
        return PESEL;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public Time getRequestTime() {
        return requestTime;
    }

    public Changes getRequestChangeChanges() {
        return requestChangeChanges;
    }

    public ShopChanges getRequestChangeShop() {
        return requestChangeShop;
    }

    public Integer getRequestType() {
        return requestType;
    }

    public Integer getID() {
        return ID;
    }
}
