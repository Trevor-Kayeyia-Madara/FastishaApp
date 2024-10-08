package com.example.fastishaapp;

public class MainModel {
    String orderNumber, description, fromLocation, toLocation, price, status, item, receiverPhoneNo;

    public MainModel(String orderNumber, String description, String fromLocation, String toLocation, String price, String status, String item, String receiverPhoneNo) {
        this.orderNumber = orderNumber;
        this.description = description;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.price = price;
        this.status = status;
        this.item = item;
        this.receiverPhoneNo = receiverPhoneNo;
    }

    public MainModel() {
    }

    public String getOrderNumber() {
        return orderNumber;
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

    public String getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public String getItem() {
        return item;
    }

    public String getReceiverPhoneNo() {
        return receiverPhoneNo;
    }
}