package com.project.helthcaresystem.Models;

public class PatientAppointment {

    private String mPatientId;
    private String did;
    private String mPatientName;
    private String time;
    private String day;
    private String availability;


    public PatientAppointment() {
    }



    public PatientAppointment(String did, String time, String day, String availability) {
        this.did = did;
        this.time = time;
        this.day = day;
        this.availability = availability;
    }

    public PatientAppointment(String did, String mPatientName, String time, String day, String availability) {
        this.did = did;
        this.mPatientName = mPatientName;
        this.time = time;
        this.day = day;
        this.availability = availability;
    }

    public PatientAppointment(String mPatientId, String did, String mPatientName, String time, String day, String availability) {
        this.mPatientId = mPatientId;
        this.did = did;
        this.mPatientName = mPatientName;
        this.time = time;
        this.day = day;
        this.availability = availability;
    }

    public String getmPatientId() {
        return mPatientId;
    }

    public void setmPatientId(String mPatientId) {
        this.mPatientId = mPatientId;
    }

    public String getmPatientName() {
        return mPatientName;
    }

    public void setmPatientName(String mPatientName) {
        this.mPatientName = mPatientName;
    }


    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
}
