import java.util.ArrayList;

public class ShopChanges {
    private ArrayList<ShopEntry> shopEntries;
    private Double sum;

    public ShopChanges(ArrayList<ShopEntry> shopEntries, Double sum) {
        this.shopEntries = shopEntries;
        this.sum = sum;
    }

    public ArrayList<ShopEntry> getShopEntries() {
        return shopEntries;
    }

    public Double getSum() {
        return sum;
    }

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
