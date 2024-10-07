public class Receipt {
    private String itemType;
    private String description;
    private String fromLocation;
    private String toLocation;
    private double price;

    // Constructor, getters, and setters
    public Receipt(String itemType, String description, String fromLocation, String toLocation, double price) {
        this.itemType = itemType;
        this.description = description;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.price = price;
    }

    public String getItemType() {
        return itemType;
    }

    public String getDescription() {
        return description;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public double getPrice() {
        return price;
    }
}
