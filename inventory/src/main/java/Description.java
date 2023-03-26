public class Description {

    private String name;
    private String extendedName;
    private String brand;

     Description(String name, String extendedName, String brand) {
        this.name = name;
        this.extendedName = extendedName;
        this.brand = brand;
    }

     String getName() {
        return name;
    }

     String getExtendedName() {
        return extendedName;
    }

     String getBrand() {
        return brand;
    }

     void setName(String name) {
        this.name = name;
    }

     void setExtendedName(String extendedName) {
        this.extendedName = extendedName;
    }

     void setBrand(String brand) {
        this.brand = brand;
    }
}
