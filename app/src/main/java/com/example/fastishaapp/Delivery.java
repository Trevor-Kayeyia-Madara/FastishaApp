package com.example.fastishaapp;

public class Delivery {
    // Delivery class to represent the delivery data structure

    public String destination;
    public String productName;
    public String weight;

    public double getCalculatedTax() {
        return calculatedTax;
    }

    public void setCalculatedTax(double calculatedTax) {
        this.calculatedTax = calculatedTax;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(double finalAmount) {
        this.finalAmount = finalAmount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

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

