public class Item {

    private Integer ID;
    private Description description;
    private Integer quantity;
    private Double price;
    private Long EAN;

     Item(Integer ID, Description description, Integer quantity, Double price, Long EAN) {
        this.ID = ID;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.EAN = EAN;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getEAN() {
        return EAN;
    }

    public void setEAN(Long EAN) {
        this.EAN = EAN;
    }

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
