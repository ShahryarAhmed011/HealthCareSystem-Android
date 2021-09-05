package com.project.helthcaresystem.Models;

public class DoctorCategory {

    private String error;
    private String message;
    private String did;
    private String email;
    private String category;
    private String speciality;


    public DoctorCategory() {
    }

    public DoctorCategory(String did, String email, String category, String speciality) {
        this.did = did;
        this.email = email;
        this.category = category;
        this.speciality = speciality;
    }

    public DoctorCategory(String error, String message, String did, String email, String category, String speciality) {
        this.error = error;
        this.message = message;
        this.did = did;
        this.email = email;
        this.category = category;
        this.speciality = speciality;
    }

    public DoctorCategory(String error, String message) {
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

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
}
