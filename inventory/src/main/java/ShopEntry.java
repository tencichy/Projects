import java.util.Objects;

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

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public Double getSumPrice() {
        return sumPrice;
    }

    public Integer getID() {
        return ID;
    }

    public Integer getQuantityBefore() {
        return quantityBefore;
    }

    @Override
    public String toString() {
        return name + ", " + brand + " - " + quantity + " * " + price + " = " + sumPrice + " PLN";
    }

    public boolean equals(ShopEntry shopEntry){
        return name.equals(shopEntry.name) && brand.equals(shopEntry.brand) && Objects.equals(quantity, shopEntry.quantity) && Objects.equals(price,shopEntry.price) && Objects.equals(sumPrice,shopEntry.sumPrice);
    }
}
