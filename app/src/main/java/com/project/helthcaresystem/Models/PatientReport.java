package com.project.helthcaresystem.Models;

public class PatientReport {

    private String mPatientID;
    private String mDoctorID;
    private String mDoctorName;
    private String mPatientName;
    private String mPrescription;
    private String mDiagnosis;
    private String mNote;
    private String mDate;

    public PatientReport() {
    }



    public PatientReport(String mDoctorID, String mDoctorName, String mPrescription, String mDiagnosis, String mNote, String mDate) {
        this.mDoctorID = mDoctorID;
        this.mDoctorName = mDoctorName;
        this.mPrescription = mPrescription;
        this.mDiagnosis = mDiagnosis;
        this.mNote = mNote;
        this.mDate = mDate;
    }



    public PatientReport(String mPrescription, String mDiagnosis, String mNote, String mDate) {
        this.mPrescription = mPrescription;
        this.mDiagnosis = mDiagnosis;
        this.mNote = mNote;
        this.mDate = mDate;
    }

    public PatientReport(String mPatientID, String mDoctorID, String mPatientName, String mPrescription, String mDiagnosis, String mNote, String mDate) {
        this.mPatientID = mPatientID;
        this.mDoctorID = mDoctorID;
        this.mPatientName = mPatientName;
        this.mPrescription = mPrescription;
        this.mDiagnosis = mDiagnosis;
        this.mNote = mNote;
        this.mDate = mDate;
    }

    public PatientReport(String mDoctorName, String mPrescription, String mDiagnosis, String mNote, String mDate) {
        this.mDoctorName = mDoctorName;
        this.mPrescription = mPrescription;
        this.mDiagnosis = mDiagnosis;
        this.mNote = mNote;
        this.mDate = mDate;
    }


    public String getmDoctorName() {
        return mDoctorName;
    }

    public void setmDoctorName(String mDoctorName) {
        this.mDoctorName = mDoctorName;
    }

    public String getmPatientID() {
        return mPatientID;
    }

    public void setmPatientID(String mPatientID) {
        this.mPatientID = mPatientID;
    }

    public String getmDoctorID() {
        return mDoctorID;
    }

    public void setmDoctorID(String mDoctorID) {
        this.mDoctorID = mDoctorID;
    }

    public String getmPatientName() {
        return mPatientName;
    }

    public void setmPatientName(String mPatientName) {
        this.mPatientName = mPatientName;
    }

    public String getmPrescription() {
        return mPrescription;
    }

    public void setmPrescription(String mPrescription) {
        this.mPrescription = mPrescription;
    }

    public String getmDiagnosis() {
        return mDiagnosis;
    }

    public void setmDiagnosis(String mDiagnosis) {
        this.mDiagnosis = mDiagnosis;
    }

    public String getmNote() {
        return mNote;
    }

    public void setmNote(String mNote) {
        this.mNote = mNote;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }
}
