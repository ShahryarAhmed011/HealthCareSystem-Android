package com.project.helthcaresystem.Models;

public class DoctorAppointment {

    private String did;
    private String time;
    private String day;
    private String availablity;
    private String doctorName;
    private String date;

    public DoctorAppointment() {
    }


    public DoctorAppointment( String day,String time) {
        this.day = day;
        this.time = time;
    }



    public DoctorAppointment( String doctorName, String day,String time) {
        this.time = time;
        this.day = day;
        this.doctorName = doctorName;
    }




    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

   /* public DoctorAppointment(String did, String time, String day, String availablity,String doctorName) {
        this.did = did;
        this.time = time;
        this.day = day;
        this.availablity = availablity;
        this.doctorName = doctorName;
    }*/




    public DoctorAppointment(String did,  String time,String date, String day, String availablity) {
        this.did = did;
        this.time = time;
        this.date =date;
        this.day = day;
        this.availablity = availablity;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getAvailablity() {
        return availablity;
    }

    public void setAvailablity(String availablity) {
        this.availablity = availablity;
    }
}