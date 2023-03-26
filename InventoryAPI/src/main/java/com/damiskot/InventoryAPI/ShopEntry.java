package com.damiskot.InventoryAPI;

import lombok.Getter;

import java.util.Objects;

@Getter
public class ShopEntry {
    private Integer ID;
    private String name;
    private String brand;
    private Integer quantityBefore;
    private Integer quantity;
    private Double price;
    private Double sumPrice;

    public ShopEntry(Integer ID, String name, String brand, Integer quantityBefore, Integer quantity, Double price) {
        this.ID = ID;
        this.name = name;
        this.brand = brand;
        this.quantityBefore = quantityBefore;
        this.quantity = quantity;
        this.price = price;
        this.sumPrice = Math.round((quantity*price)*100)/100.0;
    }

    @Override
    public String toString() {
        return name + ", " + brand + " - " + quantity + " * " + price + " = " + sumPrice + " PLN";
    }

    public boolean equals(ShopEntry shopEntry){
        return name.equals(shopEntry.name) && brand.equals(shopEntry.brand) && Objects.equals(quantity, shopEntry.quantity) && Objects.equals(price,shopEntry.price) && Objects.equals(sumPrice,shopEntry.sumPrice);
    }
}
