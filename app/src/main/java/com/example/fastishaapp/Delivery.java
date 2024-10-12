package com.example.fastishaapp;

public class Delivery {
    // Delivery class to represent the delivery data structure

    public String destination;
    public String productName;
    public String weight;
    public double calculatedTax;
    public double totalPrice;
    public double finalAmount;
    public String location;
    public String date;
        // Default constructor required for calls to DataSnapshot.getValue(Delivery.class)

    public Delivery(String productName, String destination, String weight, double totalPrice, double calculatedTax, double finalAmount, String date, String location) {
        this.productName = productName;
        this.destination = destination;
        this.weight = weight;
        this.totalPrice = totalPrice;
        this.calculatedTax = calculatedTax;
        this.finalAmount = finalAmount;
        this.date = date;
        this.location = location;
    }

    public Delivery() {
    }
}

