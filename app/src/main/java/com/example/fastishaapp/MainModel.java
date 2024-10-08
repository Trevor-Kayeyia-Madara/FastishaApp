package com.example.fastishaapp;

public class MainModel {
    private String date, description, fromLocation, item, receiverPhoneNo, senderPhoneNo;
    private String time,toLocation, transactionCode, email;

    public MainModel() {
        // Default constructor required for Firebase
    }

    public MainModel(String date, String description, String fromLocation, String item, String receiverPhoneNo, String senderPhoneNo, String time, String toLocation, String transactionCode, String email) {
        this.date = date;
        this.description = description;
        this.fromLocation = fromLocation;
        this.item = item;
        this.receiverPhoneNo = receiverPhoneNo;
        this.senderPhoneNo = senderPhoneNo;
        this.time = time;
        this.toLocation = toLocation;
        this.transactionCode = transactionCode;
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getReceiverPhoneNo() {
        return receiverPhoneNo;
    }

    public void setReceiverPhoneNo(String receiverPhoneNo) {
        this.receiverPhoneNo = receiverPhoneNo;
    }

    public String getSenderPhoneNo() {
        return senderPhoneNo;
    }

    public void setSenderPhoneNo(String senderPhoneNo) {
        this.senderPhoneNo = senderPhoneNo;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
