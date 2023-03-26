package com.damiskot.InventoryAPI;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Item {

    private Integer ID;
    private Description description;
    private Integer quantity;
    private Double price;
    private Long EAN;

    @Override
    public String toString() {
        return "ID: " + ID + " - " + description.getName() + ", " + description.getExtendedName() + ", " + description.getBrand() + " - " + price + " PLN - In stock: " + quantity + " | " + EAN;
    }

    boolean equals(Item o) {
        return ID.equals(o.ID) && description.getName().equals(o.description.getName()) && description.getExtendedName().equals(o.description.getExtendedName()) && description.getBrand().equals(o.description.getBrand()) && quantity.equals(o.quantity) && price.equals(o.price) && EAN.equals(o.EAN);
    }

    String toSimpleString(){
        return description.getName() + ", " + description.getBrand() + ", " + price + " PLN";
    }

}
