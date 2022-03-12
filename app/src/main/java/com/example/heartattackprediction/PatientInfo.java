package com.example.heartattackprediction;

public class PatientInfo {
    String id;
    String name;
    String mobile;
    String address;
    String email;

    public PatientInfo(String id, String name, String mobile, String address, String email) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.address = address;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }
}
