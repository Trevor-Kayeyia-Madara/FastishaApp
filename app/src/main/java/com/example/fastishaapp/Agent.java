package com.example.fastishaapp;

public class Agent {
    public String firstName;
    public String lastName;
    public  String nationalID;
    public String email;
    public String phoneNo;
    public String imageUrl;
    public Agent(String firstName, String lastName, String nationalID, String email, String phoneNo, String imageUrl){
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalID = nationalID;
        this.email = email;
        this.phoneNo = phoneNo;
        this.imageUrl = imageUrl;
    }
}
