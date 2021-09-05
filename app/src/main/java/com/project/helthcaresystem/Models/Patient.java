package com.project.helthcaresystem.Models;

public class Patient {

    private String error;
    private String message;
    private String id;
    private String user_name;
    private String email;
    private String password;
    private String phone_number;
    private String address;
    private String gender;
    private String date_of_birth;

    public Patient(String user_name, String email, String password, String phone_number, String address, String gender, String date_of_birth) {
        this.user_name = user_name;
        this.email = email;
        this.password = password;
        this.phone_number = phone_number;
        this.address = address;
        this.gender = gender;
        this.date_of_birth = date_of_birth;
    }

    public Patient(String id, String user_name, String email, String password) {
        this.id = id;
        this.user_name = user_name;
        this.email = email;
        this.password = password;
    }

    public Patient(String user_name, String email, String password) {
        this.user_name = user_name;
        this.email = email;
        this.password = password;
    }

    public Patient(String error, String id, String user_name, String email, String password) {
        this.error = error;
        this.id = id;
        this.user_name = user_name;
        this.email = email;
        this.password = password;
    }

    public Patient(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public Patient() {
    }

    public Patient(String error, String message, String id, String user_name, String email, String password, String phone_number, String address, String gender, String date_of_birth) {
        this.error = error;
        this.message = message;
        this.id = id;
        this.user_name = user_name;
        this.email = email;
        this.password = password;
        this.phone_number = phone_number;
        this.address = address;
        this.gender = gender;
        this.date_of_birth = date_of_birth;
    }

    public Patient(String id, String user_name, String email, String password, String phone_number, String address, String gender, String date_of_birth) {
        this.id = id;
        this.user_name = user_name;
        this.email = email;
        this.password = password;
        this.phone_number = phone_number;
        this.address = address;
        this.gender = gender;
        this.date_of_birth = date_of_birth;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
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

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
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
}