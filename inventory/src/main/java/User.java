import java.util.Objects;

public class User {

    private Integer ID;
    private String login;
    private String password;
    private Integer accountType;
    private String name;
    private String surname;
    private Long PESEL;

    public User(Integer ID, String login, String password, Integer accountType, String name, String surname, Long PESEL) {
        this.ID = ID;
        this.login = login;
        this.password = password;
        this.accountType = accountType;
        this.name = name;
        this.surname = surname;
        this.PESEL = PESEL;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Long getPESEL() {
        return PESEL;
    }

    public void setPESEL(Long PESEL) {
        this.PESEL = PESEL;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return name + " " + surname + " - " + accountType + " - " + PESEL;
    }

    String toFullString(){
        StringBuilder passInDots = new StringBuilder();
        for (int i = 0; i < password.length(); i++) {
            passInDots.append("*");
        }
        return "ID: " + ID + " - " + login + ", " + passInDots + " - Type: " + accountType + " - " + name + ", " + surname + " | " + PESEL;
    }

    boolean equals(User user){
        return Objects.equals(ID,user.ID) && login.equals(user.login) && password.equals(user.password) && Objects.equals(accountType,user.accountType) && name.equals(user.name) && surname.equals(user.surname) && Objects.equals(PESEL,user.PESEL);
    }
}
