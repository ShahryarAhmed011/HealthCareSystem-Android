package com.project.helthcaresystem.Models;

public class Prescription {

    private String order_id;
    private String patient_id;
    private String doctorName;
    private String patientName;
    private String prescription;
    private String diagnosis;
    private String note;
    private String date;

    public Prescription() {
    }

    public Prescription(String order_id, String patient_id, String doctorName, String patientName, String prescription, String diagnosis, String note, String date) {
        this.order_id = order_id;
        this.patient_id = patient_id;
        this.doctorName = doctorName;
        this.patientName = patientName;
        this.prescription = prescription;
        this.diagnosis = diagnosis;
        this.note = note;
        this.date = date;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
