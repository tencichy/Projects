package com.damiskot.InventoryAPI;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class User {

    private Integer ID;
    private String login;
    private String password;
    private Integer accountType;
    private String name;
    private String surname;
    private Long PESEL;

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
