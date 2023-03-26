package com.damiskot.InventoryAPI;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;

@Getter
@AllArgsConstructor
public class ShopChanges {
    private ArrayList<ShopEntry> shopEntries;
    private Double sum;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (ShopEntry e: shopEntries) {
            stringBuilder.append(e.getName()).append(", ").append(e.getBrand()).append(" - ").append(e.getQuantity()).append(" * ").append(e.getPrice()).append(" = ").append(e.getSumPrice()).append(" PLN \n");
        }
        Double sum = 0.0;
        for (ShopEntry e:shopEntries) {
            sum += e.getSumPrice();
        }
        stringBuilder.append("Sum: ").append(sum).append(" PLN");
        return stringBuilder.toString();
    }
}
