package com.example.fastishaapp;

public class PackageData {
    private String item;
    private String description;
    private String fromLocation;
    private String toLocation;
    private String senderPhoneNo;
    private String receiverPhoneNo;
    private String date;
    private String time;
    private String transactionCode;

    public PackageData() {
        // Default constructor required for calls to DataSnapshot.getValue(PackageData.class)
    }

    public String getItem() {
        return item;
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

    public String getSenderPhoneNo() {
        return senderPhoneNo;
    }

    public String getReceiverPhoneNo() {
        return receiverPhoneNo;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getTransactionCode() {
        return transactionCode;
    }
}
