package com.damiskot.InventoryAPI;

import com.google.gson.Gson;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

@RestController
public class InventoryApiController {

    private Gson gson = new Gson();
    private ArrayList<User> users = new ArrayList<>();

    @PostConstruct
    private void getUsers(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/inventory?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","inventoryApp","JaVa!@#$App");
            Statement statement = connect.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from users");
            while (resultSet.next()){
                users.add(new User(resultSet.getInt("ID"),resultSet.getString("login"),resultSet.getString("pass"),resultSet.getInt("accountType"),resultSet.getString("name"),resultSet.getString("surname"),resultSet.getLong("PESEL")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    private String login(HttpServletRequest request, @RequestParam String mysqlUser, @RequestParam String mysqlPassword, @RequestParam String login, @RequestParam String password){
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(request.getRemoteAddr());
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        String decryptedMysqlUser = encryptor.decrypt(mysqlUser);
        String decryptedMysqlPassword = encryptor.decrypt(mysqlPassword);
        String decryptedLogin = encryptor.decrypt(login);
        String decryptedPassword = encryptor.decrypt(password);
        try{
            System.out.println("Not encrypted data: " + mysqlUser + " - " + mysqlPassword + ", " + login + " - " + password);
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/inventory?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",decryptedMysqlUser,decryptedMysqlPassword);
            Statement statement = connect.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from users");
            while (resultSet.next()){
                System.out.println(resultSet.toString());
                if(decryptedLogin.equals(resultSet.getString("login")) && decryptedPassword.equals(resultSet.getString("pass"))){
                    User user = new User(resultSet.getInt("ID"),resultSet.getString("login"),resultSet.getString("pass"),resultSet.getInt("accountType"),resultSet.getString("name"),resultSet.getString("surname"),resultSet.getLong("PESEL"));
                    return encryptor.encrypt(gson.toJson(user));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
        return "";
    }

    @RequestMapping(value = "getAllItems", method = RequestMethod.GET)
    private String getAllItems(HttpServletRequest request, @RequestParam String mysqlUser, @RequestParam String mysqlPassword, @RequestParam String login, @RequestParam String password){
        ArrayList<Item> items = new ArrayList<>();
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(request.getRemoteAddr());
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        String decryptedMysqlUser = encryptor.decrypt(mysqlUser);
        String decryptedMysqlPassword = encryptor.decrypt(mysqlPassword);
        String decryptedLogin = encryptor.decrypt(login);
        String decryptedPassword = encryptor.decrypt(password);
        for (User u:users) {
            if(decryptedLogin.equals(u.getLogin()) && decryptedPassword.equals(u.getPassword())){
                try{
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/inventory?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",decryptedMysqlUser,decryptedMysqlPassword);
                    Statement statement = connect.createStatement();
                    ResultSet resultSet = statement.executeQuery("select * from items");
                    while (resultSet.next()) {
                        items.add(new Item(resultSet.getInt("ID"),gson.fromJson(resultSet.getString("description"),Description.class),resultSet.getInt("quantity"),resultSet.getDouble("price"),resultSet.getLong("EAN")));
                    }
                    return encryptor.encrypt(gson.toJson(items));
                }catch (Exception e){
                    e.printStackTrace();
                    return "";
                }
            }
        }
        return "";

    }

    @RequestMapping(value = "getItemById", method = RequestMethod.GET)
    private String getItemById(HttpServletRequest request, @RequestParam String mysqlUser, @RequestParam String mysqlPassword, @RequestParam String login, @RequestParam String password, @RequestParam Integer id){
        ArrayList<Item> items = new ArrayList<>();
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(request.getRemoteAddr());
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        String decryptedMysqlUser = encryptor.decrypt(mysqlUser);
        String decryptedMysqlPassword = encryptor.decrypt(mysqlPassword);
        String decryptedLogin = encryptor.decrypt(login);
        String decryptedPassword = encryptor.decrypt(password);
        for (User u:users) {
            if(decryptedLogin.equals(u.getLogin()) && decryptedPassword.equals(u.getPassword())){
                try{
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/inventory?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",decryptedMysqlUser,decryptedMysqlPassword);
                    String query = "select * from items where ID = ?";
                    PreparedStatement pStatement = connect.prepareStatement(query);
                    pStatement.setInt(1,id);
                    ResultSet resultSet = pStatement.executeQuery();
                    return encryptor.encrypt(gson.toJson(new Item(resultSet.getInt("ID"),gson.fromJson(resultSet.getString("description"),Description.class),resultSet.getInt("quantity"),resultSet.getDouble("price"),resultSet.getLong("EAN"))));
                }catch (Exception e){
                    e.printStackTrace();
                    return "";
                }
            }
        }
        return "";

    }

    @RequestMapping(value = "deleteItemById", method = RequestMethod.GET)
    private String deleteItemById(HttpServletRequest request, @RequestParam String mysqlUser, @RequestParam String mysqlPassword,
    @RequestParam String login, @RequestParam String password,@RequestParam String name, @RequestParam String surname, @RequestParam String PESEL,
    @RequestParam Integer id, @RequestParam String changes){
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(request.getRemoteAddr());
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        String decryptedMysqlUser = encryptor.decrypt(mysqlUser);
        String decryptedMysqlPassword = encryptor.decrypt(mysqlPassword);
        String decryptedLogin = encryptor.decrypt(login);
        String decryptedPassword = encryptor.decrypt(password);
        String decryptedName = encryptor.decrypt(name);
        String decryptedSurname = encryptor.decrypt(surname);
        Long decryptedPESEL = Long.valueOf(encryptor.decrypt(PESEL));
        String decryptedChanges = encryptor.decrypt(changes);
        for (User u:users) {
            if(decryptedLogin.equals(u.getLogin()) && decryptedPassword.equals(u.getPassword()) && (u.getAccountType() == 0 || u.getAccountType() == 1)){
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/inventory?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",decryptedMysqlUser,decryptedMysqlPassword);
                    String query = "delete from items where ID = ?";
                    PreparedStatement pStatement = connect.prepareStatement(query);
                    pStatement.setInt(1, id);
                    pStatement.executeUpdate();
                    connect.close();
                } catch (Exception e) {
                    return "error";
                }
                try {
                    Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/inventory?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",decryptedMysqlUser,decryptedMysqlPassword);
                    String query = "insert changes(name,surname,PESEL,requestDate,requestTime,requestChange,requestType) values(?,?,?,?,?,?,?)";
                    PreparedStatement pStatement = connect.prepareStatement(query);
                    pStatement.setString(1, decryptedName);
                    pStatement.setString(2, decryptedSurname);
                    pStatement.setLong(3, decryptedPESEL);
                    pStatement.setDate(4, Date.valueOf(LocalDate.now().plusDays(1)));
                    pStatement.setTime(5, Time.valueOf(LocalTime.now()));
                    pStatement.setString(6, decryptedChanges);
                    pStatement.setInt(7, 0);
                    pStatement.executeUpdate();
                    connect.close();
                } catch (Exception e) {
                    return "error";
                }
                return "";
            }
        }
        return "forbidden";
    }

    @RequestMapping(value = "editItem", method = RequestMethod.GET)
    private String editItem(HttpServletRequest request, @RequestParam String mysqlUser, @RequestParam String mysqlPassword,
    @RequestParam String login, @RequestParam String password,@RequestParam String name, @RequestParam String surname, @RequestParam String PESEL, @RequestParam String itemToEditJson, @RequestParam String itemToSaveJson){
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(request.getRemoteAddr());
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        String decryptedMysqlUser = encryptor.decrypt(mysqlUser);
        String decryptedMysqlPassword = encryptor.decrypt(mysqlPassword);
        String decryptedLogin = encryptor.decrypt(login);
        String decryptedPassword = encryptor.decrypt(password);
        String decryptedName = encryptor.decrypt(name);
        String decryptedSurname = encryptor.decrypt(surname);
        Long decryptedPESEL = Long.valueOf(encryptor.decrypt(PESEL));
        Item itemToSave = gson.fromJson(encryptor.decrypt(itemToSaveJson),Item.class);
        Item itemToEdit = gson.fromJson(encryptor.decrypt(itemToEditJson),Item.class);
        for (User u:users) {
            if(decryptedLogin.equals(u.getLogin()) && decryptedPassword.equals(u.getPassword()) && (u.getAccountType() == 0 || u.getAccountType() == 1)){
             try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/inventory?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", decryptedMysqlUser, decryptedMysqlPassword);
                    String query = "update items set description = ?, quantity = ?, price = ?, EAN = ? where ID = ?";
                    PreparedStatement pStatement = connect.prepareStatement(query);
                    pStatement.setString(1, gson.toJson(itemToSave.getDescription()));
                    pStatement.setInt(2, itemToSave.getQuantity());
                    pStatement.setDouble(3, itemToSave.getPrice());
                    pStatement.setLong(4, itemToSave.getEAN());
                    pStatement.setInt(5, itemToSave.getID());
                    pStatement.executeUpdate();
                    connect.close();
                } catch (Exception e) {
                    return "error";
                }
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/inventory?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", decryptedMysqlUser, decryptedMysqlPassword);
                    String query = "insert changes(name,surname,PESEL,requestDate,requestTime,requestChange,requestType) values(?,?,?,?,?,?,?)";
                    PreparedStatement pStatement = connect.prepareStatement(query);
                    pStatement.setString(1, decryptedName);
                    pStatement.setString(2, decryptedSurname);
                    pStatement.setLong(3, decryptedPESEL);
                    pStatement.setDate(4, Date.valueOf(LocalDate.now().plusDays(1)));
                    pStatement.setTime(5, Time.valueOf(LocalTime.now()));
                    pStatement.setString(6, gson.toJson(new Changes(itemToEdit, itemToSave)));
                    pStatement.setInt(7, 0);
                    pStatement.executeUpdate();
                    connect.close();
                } catch (Exception e) {
                    return "error";
                }
                return "";
            }
        }
        return "forbidden";
    }

    @RequestMapping(value = "addItem", method = RequestMethod.GET)
    private String addItem(HttpServletRequest request, @RequestParam String mysqlUser, @RequestParam String mysqlPassword,
                           @RequestParam String login, @RequestParam String password,@RequestParam String name, @RequestParam String surname, @RequestParam String PESEL, @RequestParam String itemToSaveJson){
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(request.getRemoteAddr());
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        String decryptedMysqlUser = encryptor.decrypt(mysqlUser);
        String decryptedMysqlPassword = encryptor.decrypt(mysqlPassword);
        String decryptedLogin = encryptor.decrypt(login);
        String decryptedPassword = encryptor.decrypt(password);
        String decryptedName = encryptor.decrypt(name);
        String decryptedSurname = encryptor.decrypt(surname);
        Long decryptedPESEL = Long.valueOf(encryptor.decrypt(PESEL));
        Item itemToSave = gson.fromJson(encryptor.decrypt(itemToSaveJson),Item.class);
        for (User u:users) {
            if(decryptedLogin.equals(u.getLogin()) && decryptedPassword.equals(u.getPassword()) && (u.getAccountType() == 0 || u.getAccountType() == 1)){
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/inventory?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", decryptedMysqlUser, decryptedMysqlPassword);
                    String query = "insert items(description,quantity,price,EAN) values(?,?,?,?)";
                    PreparedStatement pStatement = connect.prepareStatement(query);
                    pStatement.setString(1, gson.toJson(itemToSave.getDescription()));
                    pStatement.setInt(2, itemToSave.getQuantity());
                    pStatement.setDouble(3, itemToSave.getPrice());
                    pStatement.setLong(4, itemToSave.getEAN());
                    pStatement.executeUpdate();
                    connect.close();
                } catch (Exception e) {
                    return "error";
                }
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/inventory?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", decryptedMysqlUser, decryptedMysqlPassword);
                    String query = "insert changes(name,surname,PESEL,requestDate,requestTime,requestChange,requestType) values(?,?,?,?,?,?,?)";
                    PreparedStatement pStatement = connect.prepareStatement(query);
                    pStatement.setString(1, decryptedName);
                    pStatement.setString(2, decryptedSurname);
                    pStatement.setLong(3, decryptedPESEL);
                    pStatement.setDate(4, Date.valueOf(LocalDate.now()));
                    pStatement.setTime(5, Time.valueOf(LocalTime.now()));
                    pStatement.setString(6, gson.toJson(new Changes(null, itemToSave)));
                    pStatement.setInt(7, 0);
                    pStatement.executeUpdate();
                    connect.close();
                } catch (Exception e) {
                    return "error";
                }
                return "";
            }
        }
        return "forbidden";
    }

}
