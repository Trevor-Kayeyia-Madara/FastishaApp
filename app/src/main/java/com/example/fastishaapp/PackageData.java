public class PackageData {
    private String item, description, fromLocation, toLocation, senderPhoneNo, receiverPhoneNo, date, time, transactionCode;

    public PackageData() {
        // Default constructor required for calls to DataSnapshot.getValue(PackageData.class)
    }

    public PackageData(String item, String description, String fromLocation, String toLocation,
                       String senderPhoneNo, String receiverPhoneNo, String date, String time, String transactionCode) {
        this.item = item;
        this.description = description;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.senderPhoneNo = senderPhoneNo;
        this.receiverPhoneNo = receiverPhoneNo;
        this.date = date;
        this.time = time;
        this.transactionCode = transactionCode;
    }

    // Getters and setters...
    public String getItem() { return item; }
    public void setItem(String item) { this.item = item; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getFromLocation() { return fromLocation; }
    public void setFromLocation(String fromLocation) { this.fromLocation = fromLocation; }

    public String getToLocation() { return toLocation; }
    public void setToLocation(String toLocation) { this.toLocation = toLocation; }

    public String getSenderPhoneNo() { return senderPhoneNo; }
    public void setSenderPhoneNo(String senderPhoneNo) { this.senderPhoneNo = senderPhoneNo; }

    public String getReceiverPhoneNo() { return receiverPhoneNo; }
    public void setReceiverPhoneNo(String receiverPhoneNo) { this.receiverPhoneNo = receiverPhoneNo; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getTransactionCode() { return transactionCode; }
    public void setTransactionCode(String transactionCode) { this.transactionCode = transactionCode; }
}
