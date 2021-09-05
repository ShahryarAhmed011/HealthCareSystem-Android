package com.project.helthcaresystem.Models;

public class PatientBill {
    private String billId;
    private String bill_date;
    private String diagnosis;
    private String prescription;
    private String note;
    private String doctor_name;
    private String pharmacist_name;
    private String doctor_fee;
    private String pharmacist_fee;
    private String payment_status;

    public PatientBill() {
    }

    public PatientBill(String billId, String bill_date, String diagnosis, String prescription, String note, String doctor_name, String pharmacist_name, String doctor_fee, String pharmacist_fee, String payment_status) {
        this.billId = billId;
        this.bill_date = bill_date;
        this.diagnosis = diagnosis;
        this.prescription = prescription;
        this.note = note;
        this.doctor_name = doctor_name;
        this.pharmacist_name = pharmacist_name;
        this.doctor_fee = doctor_fee;
        this.pharmacist_fee = pharmacist_fee;
        this.payment_status = payment_status;
    }



    public PatientBill(String bill_date, String diagnosis, String prescription, String note, String doctor_name, String pharmacist_name, String doctor_fee, String pharmacist_fee, String payment_status) {
        this.bill_date = bill_date;
        this.diagnosis = diagnosis;
        this.prescription = prescription;
        this.note = note;
        this.doctor_name = doctor_name;
        this.pharmacist_name = pharmacist_name;
        this.doctor_fee = doctor_fee;
        this.pharmacist_fee = pharmacist_fee;
        this.payment_status = payment_status;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getBill_date() {
        return bill_date;
    }

    public void setBill_date(String bill_date) {
        this.bill_date = bill_date;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getPharmacist_name() {
        return pharmacist_name;
    }

    public void setPharmacist_name(String pharmacist_name) {
        this.pharmacist_name = pharmacist_name;
    }

    public String getDoctor_fee() {
        return doctor_fee;
    }

    public void setDoctor_fee(String doctor_fee) {
        this.doctor_fee = doctor_fee;
    }

    public String getPharmacist_fee() {
        return pharmacist_fee;
    }

    public void setPharmacist_fee(String pharmacist_fee) {
        this.pharmacist_fee = pharmacist_fee;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }
}
