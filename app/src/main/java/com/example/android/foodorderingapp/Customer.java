package com.example.android.foodorderingapp;

public class Customer {
    String name,email,phone,seatNo,password;

    public Customer() {
    }

    public Customer(String name, String email, String phone, String seatNo, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.seatNo= seatNo;
        this.password = password;


    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }
}
