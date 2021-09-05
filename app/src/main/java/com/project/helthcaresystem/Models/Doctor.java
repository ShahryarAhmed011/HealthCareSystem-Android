package com.project.helthcaresystem.Models;

public class Doctor {

    private String error;
    private String message;
    private String id;
    private String user_name;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private String gender;
    private String dateOfBirth;

    public Doctor(String id, String user_name, String email, String password, String phoneNumber, String address, String gender, String dateOfBirth) {
        this.id = id;
        this.user_name = user_name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public Doctor(String user_name, String email, String password, String phoneNumber, String address, String gender, String dateOfBirth) {
        this.user_name = user_name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public Doctor(String error, String message, String id, String user_name, String email, String password, String phoneNumber, String address, String gender, String dateOfBirth) {
        this.error = error;
        this.message = message;
        this.id = id;
        this.user_name = user_name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public Doctor(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
