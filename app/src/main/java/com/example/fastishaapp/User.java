package com.example.fastishaapp;

public class User {
    public String FirstName;
    public String LastName;
    public String Email;
    public String Phone;
    public String DOB;

    public User(){

    }

    public User(String firstName, String lastName, String email, String phone, String DOB) {
        FirstName = firstName;
        LastName = lastName;
        Email = email;
        Phone = phone;
        this.DOB = DOB;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }
}
