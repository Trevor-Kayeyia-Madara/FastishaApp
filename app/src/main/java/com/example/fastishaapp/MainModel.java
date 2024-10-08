package com.example.fastishaapp;

public class MainModel {
    private String itemName;
    private String itemDescription;
    private double itemPrice;

    // Empty constructor for Firebase
    public MainModel() {}

    // Getter and Setter methods
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }
}
