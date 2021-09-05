package com.project.helthcaresystem.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.project.helthcaresystem.Models.Doctor;
import com.project.helthcaresystem.Models.DoctorAppointment;
import com.project.helthcaresystem.Models.DoctorCategory;
import com.project.helthcaresystem.Models.Patient;
import com.project.helthcaresystem.Models.PatientAppointment;
import com.project.helthcaresystem.Models.PatientReport;
import com.project.helthcaresystem.Models.Pharmacist;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME="HealthCareSystemDB"; // here is the database name when app is first created it will make .db file named with HealthCareSystemDB
    private static final int DATABASE_VERSION=5; //this is the db version we change this when we update our app on playstore

    //---------------Patients Table--------------------------------------------------
    private static final String PATIENT_TABLE="Patient_Table"; //here is the table name

    private static final String PATIENT_ID_COL="Patient_Id"; //column of patient table
    private static final String PATIENT_USER_NAME_COL = "Patient_User_Name"; //column of patient table
    private static final String PATIENT_EMAIL_COL = "Patient_Email"; //column of patient table
    private static final String PATIENT_PASSWORD_COL = "Patient_Password"; //column of patient table
    private static final String PATIENT_PHONE_NUMBER_COL = "Patient_Phone_Number"; //column name
    private static final String PATIENT_ADDRESS_COL = "Patient_Address"; //column name
    private static final String PATIENT_GENDER_COL = "Patient_Gender"; //column name
    private static final String PATIENT_DATE_OF_BIRTH_COL = "Patient_Date_Of_Birth"; //column name

    //---------------Doctors Table--------------------------------------------------
    private static final String DOCTOR_TABLE="Doctor_Table"; //table name

    private static final String DOCTOR_ID_COL="Doctor_Id"; //column name
    private static final String DOCTOR_USER_NAME_COL = "Doctor_User_Name"; //column name
    private static final String DOCTOR_EMAIL_COL = "Doctor_Email"; //column name
    private static final String DOCTOR_PASSWORD_COL = "Doctor_Password"; //column name
    private static final String DOCTOR_PHONE_NUMBER_COL = "Phone_Number"; //column name
    private static final String DOCTOR_ADDRESS_COL = "Address"; //column name
    private static final String DOCTOR_GENDER_COL = "Gender"; //column name
    private static final String DOCTOR_DATE_OF_BIRTH_COL = "Date_Of_Birth"; //column name

    //same as below we initialize table name and column name

    //---------------Pharmacist Table--------------------------------------------------
    private static final String PHARMACIST_TABLE="Pharmacist_Table";

    private static final String PHARMACIST_ID_COL="Pharmacist_Id";
    private static final String PHARMACIST_USER_NAME_COL = "Pharmacist_User_Name";
    private static final String PHARMACIST_EMAIL_COL = "Pharmacist_Email";
    private static final String PHARMACIST_PASSWORD_COL = "Pharmacist_Password";

    private static final String PHARMACIST_PHONE_NUMBER_COL = "Phone_Number"; //column name
    private static final String PHARMACIST_ADDRESS_COL = "Address"; //column name
    private static final String PHARMACIST_GENDER_COL = "Gender"; //column name
    private static final String PHARMACIST_DATE_OF_BIRTH_COL = "Date_Of_Birth"; //column name

    //---------------Doctors Category Table--------------------------------------------------
    private static final String DOCTOR_CATEGORY_TABLE="Doctor_Category_Table";
    private static final String DOCTOR_CATEGORY_ID_COL="Doctor_Category_Id";
    private static final String DOCTOR_CATEGORY_EMAIL_COL = "Doctor_Category_Email";
    private static final String DOCTOR_CATEGORY_CATEGORY_COL = "Doctor_Category";
    private static final String DOCTOR_CATEGORY_SPECIALITY_COL = "Doctor_Speciality";

    //---------------Doctors  Appointment Schedule Table--------------------------------------------------
    private static final String DOCTOR_APPOINTMENT_TABLE="Doctor_Appointment_Table";
    private static final String DOCTOR_APPOINTMENT_DID_COL="Doctor_Appointment_Did";
    private static final String DOCTOR_APPOINTMENT_TIME_COL = "Doctor_Appointment_Time";
    private static final String DOCTOR_APPOINTMENT_DATE_COL = "Doctor_Appointment_Date";
    private static final String DOCTOR_APPOINTMENT_DAY_COL = "Doctor_Appointment_Day";
    private static final String DOCTOR_APPOINTMENT_AVAILABILITY_COL = "Doctor_Appointment_Availability";

    //---------------Doctors  Patient Appointments  Table--------------------------------------------------
    private static final String PATIENT_APPOINTMENTS_TABLE="Patient_Appointment_Table";

    private static final String PATIENT_APPOINTMENT_PID_COL="Patient_Appointment_Pid";
    private static final String PATIENT_APPOINTMENT_DID_COL="Patient_Appointment_Did";
    private static final String PATIENT_APPOINTMENT_NAME_COL = "Patient_Name";
    private static final String PATIENT_APPOINTMENT_TIME_COL = "Patient_Appointment_Time";
    private static final String PATIENT_APPOINTMENT_DAY_COL = "Patient_Appointment_Day";

    //---------------Doctors  Patient Reports  Table--------------------------------------------------
    private static final String PATIENT_REPORTS_TABLE="Patient_Reports_Table";

    private static final String PATIENT_REPORTS_PID_COL="Patient_Reports_Pid";
    private static final String PATIENT_REPORTS_DID_COL="Patient_Reports_Did";
    private static final String PATIENT_REPORTS_NAME_COL = "Patient_Name";
    private static final String PATIENT_REPORTS_PRESCRIPTION_COL = "Patient_Reports_Prescription";
    private static final String PATIENT_REPORTS_DIAGNOSIS_COL = "Patient_Reports_Diagnosis";
    private static final String PATIENT_REPORTS_NOTE_COL = "Patient_Reports_Note";
    private static final String PATIENT_REPORTS_DATE_COL = "Patient_Reports_Date";

    public static DatabaseHelper mDatabaseHelper;

    public static synchronized DatabaseHelper getInstance(Context context){

        if(mDatabaseHelper == null){
            mDatabaseHelper= new DatabaseHelper(context);
        }
        return mDatabaseHelper;
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String mPatientTable = "CREATE TABLE " + PATIENT_TABLE + "(" + PATIENT_ID_COL + " TEXT,"
                + PATIENT_USER_NAME_COL + " TEXT,"
                + PATIENT_EMAIL_COL + " TEXT,"
                + PATIENT_PASSWORD_COL + " TEXT,"
                + PATIENT_PHONE_NUMBER_COL + " TEXT,"
                + PATIENT_ADDRESS_COL + " TEXT,"
                + PATIENT_GENDER_COL + " TEXT,"
                + PATIENT_DATE_OF_BIRTH_COL + " TEXT,"
                + " ''); ";

        String mDoctorTable = "CREATE TABLE " + DOCTOR_TABLE + "(" + DOCTOR_ID_COL + " TEXT,"
                + DOCTOR_USER_NAME_COL + " TEXT,"
                + DOCTOR_EMAIL_COL + " TEXT,"
                + DOCTOR_PASSWORD_COL + " TEXT,"
                + DOCTOR_PHONE_NUMBER_COL + " TEXT,"
                + DOCTOR_ADDRESS_COL + " TEXT,"
                + DOCTOR_GENDER_COL + " TEXT,"
                + DOCTOR_DATE_OF_BIRTH_COL + " TEXT,"
                + " ''); ";

        String mPharmacistTable = "CREATE TABLE " + PHARMACIST_TABLE + "(" + PHARMACIST_ID_COL + " TEXT,"
                + PHARMACIST_USER_NAME_COL + " TEXT,"
                + PHARMACIST_EMAIL_COL + " TEXT,"
                + PHARMACIST_PASSWORD_COL + " TEXT,"
                + PHARMACIST_PHONE_NUMBER_COL + " TEXT,"
                + PHARMACIST_ADDRESS_COL + " TEXT,"
                + PHARMACIST_GENDER_COL + " TEXT,"
                + PHARMACIST_DATE_OF_BIRTH_COL + " TEXT,"
                + " ''); ";

        String mDoctorCategoryTable = "CREATE TABLE " + DOCTOR_CATEGORY_TABLE + "(" + DOCTOR_CATEGORY_ID_COL + " TEXT,"
                + DOCTOR_CATEGORY_EMAIL_COL + " TEXT,"
                + DOCTOR_CATEGORY_CATEGORY_COL + " TEXT,"
                + DOCTOR_CATEGORY_SPECIALITY_COL + " TEXT,"
                + " ''); ";

        String mDoctorAppointmentTable = "CREATE TABLE " + DOCTOR_APPOINTMENT_TABLE + "(" + DOCTOR_APPOINTMENT_DID_COL + " TEXT,"
                + DOCTOR_APPOINTMENT_TIME_COL + " TEXT,"
                + DOCTOR_APPOINTMENT_DATE_COL + " TEXT,"
                + DOCTOR_APPOINTMENT_DAY_COL + " TEXT,"
                + DOCTOR_APPOINTMENT_AVAILABILITY_COL + " TEXT,"
                + " ''); ";

        String mPatientAppointmentsTable = "CREATE TABLE " + PATIENT_APPOINTMENTS_TABLE + "(" + PATIENT_APPOINTMENT_PID_COL + " TEXT,"
                + PATIENT_APPOINTMENT_DID_COL + " TEXT,"
                + PATIENT_APPOINTMENT_NAME_COL + " TEXT,"
                + PATIENT_APPOINTMENT_TIME_COL + " TEXT,"
                + PATIENT_APPOINTMENT_DAY_COL + " TEXT,"
                + " ''); ";


        String mPatientReportsTable = "CREATE TABLE " + PATIENT_REPORTS_TABLE + "(" + PATIENT_REPORTS_PID_COL + " TEXT,"
                + PATIENT_REPORTS_DID_COL + " TEXT,"
                + PATIENT_REPORTS_NAME_COL + " TEXT,"
                + PATIENT_REPORTS_PRESCRIPTION_COL + " TEXT,"
                + PATIENT_REPORTS_DIAGNOSIS_COL + " TEXT,"
                + PATIENT_REPORTS_NOTE_COL + " TEXT,"
                + PATIENT_REPORTS_DATE_COL + " TEXT,"
                + " ''); ";



        Log.v("DatabaseHelper","Table Created" + " ");
        db.execSQL(mPatientTable);
        db.execSQL(mDoctorTable);
        db.execSQL(mPharmacistTable);
        db.execSQL(mDoctorCategoryTable);
        db.execSQL(mDoctorAppointmentTable);
        db.execSQL(mPatientAppointmentsTable);
        db.execSQL(mPatientReportsTable);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+ PATIENT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ DOCTOR_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ PHARMACIST_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ DOCTOR_CATEGORY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ DOCTOR_APPOINTMENT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ PATIENT_APPOINTMENTS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ PATIENT_REPORTS_TABLE);
        onCreate(db);
    }

    public void addPatient(Patient patient){

        ContentValues values = new ContentValues();
        values.put(PATIENT_ID_COL,String.valueOf(patient.getId()));
        values.put(PATIENT_USER_NAME_COL,String.valueOf(patient.getUser_name()));
        values.put(PATIENT_EMAIL_COL,String.valueOf(patient.getEmail()));
        values.put(PATIENT_PASSWORD_COL,String.valueOf(patient.getPassword()));
        values.put(PATIENT_PHONE_NUMBER_COL,String.valueOf(patient.getPhone_number()));
        values.put(PATIENT_ADDRESS_COL,String.valueOf(patient.getAddress()));
        values.put(PATIENT_GENDER_COL,String.valueOf(patient.getGender()));
        values.put(PATIENT_DATE_OF_BIRTH_COL,String.valueOf(patient.getDate_of_birth()));

        SQLiteDatabase db = getWritableDatabase();
        db.insert(PATIENT_TABLE,null,values);
        db.close();

    }


    public void addDoctor(Doctor doctor){

        ContentValues values = new ContentValues();
        values.put(DOCTOR_ID_COL,String.valueOf(doctor.getId()));
        values.put(DOCTOR_USER_NAME_COL,String.valueOf(doctor.getUser_name()));
        values.put(DOCTOR_EMAIL_COL,String.valueOf(doctor.getEmail()));
        values.put(DOCTOR_PASSWORD_COL,String.valueOf(doctor.getPassword()));
        values.put(DOCTOR_PHONE_NUMBER_COL,String.valueOf(doctor.getPhoneNumber()));
        values.put(DOCTOR_ADDRESS_COL,String.valueOf(doctor.getAddress()));
        values.put(DOCTOR_GENDER_COL,String.valueOf(doctor.getGender()));
        values.put(DOCTOR_DATE_OF_BIRTH_COL,String.valueOf(doctor.getDateOfBirth()));

        SQLiteDatabase db = getWritableDatabase();
        db.insert(DOCTOR_TABLE,null,values);
        db.close();

    }


    public void addPharmacist(Pharmacist pharmacist){

        ContentValues values = new ContentValues();
        values.put(PHARMACIST_ID_COL,String.valueOf(pharmacist.getId()));
        values.put(PHARMACIST_USER_NAME_COL,String.valueOf(pharmacist.getUser_name()));
        values.put(PHARMACIST_EMAIL_COL,String.valueOf(pharmacist.getEmail()));
        values.put(PHARMACIST_PASSWORD_COL,String.valueOf(pharmacist.getPassword()));
        values.put(PHARMACIST_PHONE_NUMBER_COL,String.valueOf(pharmacist.getPhone_number()));
        values.put(PHARMACIST_ADDRESS_COL,String.valueOf(pharmacist.getAddress()));
        values.put(PHARMACIST_GENDER_COL,String.valueOf(pharmacist.getGender()));
        values.put(PHARMACIST_DATE_OF_BIRTH_COL,String.valueOf(pharmacist.getDate_of_birth()));

        SQLiteDatabase db = getWritableDatabase();
        db.insert(PHARMACIST_TABLE,null,values);
        db.close();

    }


    public void addDoctorCategory(DoctorCategory doctorCategory){

        ContentValues values = new ContentValues();
        values.put(DOCTOR_CATEGORY_ID_COL,String.valueOf(doctorCategory.getDid()));
        values.put(DOCTOR_CATEGORY_EMAIL_COL,String.valueOf(doctorCategory.getEmail()));
        values.put(DOCTOR_CATEGORY_CATEGORY_COL,String.valueOf(doctorCategory.getCategory()));
        values.put(DOCTOR_CATEGORY_SPECIALITY_COL,String.valueOf(doctorCategory.getSpeciality()));

        SQLiteDatabase db = getWritableDatabase();
        db.insert(DOCTOR_CATEGORY_TABLE,null,values);
        db.close();

    }

    public void addDoctorAppointment(DoctorAppointment doctorAppointment){

        ContentValues values = new ContentValues();
        values.put(DOCTOR_APPOINTMENT_DID_COL,String.valueOf(doctorAppointment.getDid()));
        values.put(DOCTOR_APPOINTMENT_TIME_COL,String.valueOf(doctorAppointment.getTime()));
        values.put(DOCTOR_APPOINTMENT_DATE_COL,String.valueOf(doctorAppointment.getDate()));
        values.put(DOCTOR_APPOINTMENT_DAY_COL,String.valueOf(doctorAppointment.getDay()));
        values.put(DOCTOR_APPOINTMENT_AVAILABILITY_COL,String.valueOf(doctorAppointment.getAvailablity()));

        SQLiteDatabase db = getWritableDatabase();
        db.insert(DOCTOR_APPOINTMENT_TABLE,null,values);
        db.close();

    }

    public void addPatientAppointment(PatientAppointment patientAppointment){

        ContentValues values = new ContentValues();
        values.put(PATIENT_APPOINTMENT_PID_COL,String.valueOf(patientAppointment.getmPatientId()));
        values.put(PATIENT_APPOINTMENT_DID_COL,String.valueOf(patientAppointment.getDid()));
        values.put(PATIENT_APPOINTMENT_NAME_COL,String.valueOf(patientAppointment.getmPatientName()));
        values.put(PATIENT_APPOINTMENT_TIME_COL,String.valueOf(patientAppointment.getTime()));
        values.put(PATIENT_APPOINTMENT_DAY_COL,String.valueOf(patientAppointment.getDay()));

        SQLiteDatabase db = getWritableDatabase();
        db.insert(PATIENT_APPOINTMENTS_TABLE,null,values);
        db.close();

    }

    public void addPatientReports(PatientReport patientReport){

        ContentValues values = new ContentValues();
        values.put(PATIENT_REPORTS_PID_COL,String.valueOf(patientReport.getmPatientID()));
        values.put(PATIENT_REPORTS_DID_COL,String.valueOf(patientReport.getmDoctorID()));
        values.put(PATIENT_REPORTS_NAME_COL,String.valueOf(patientReport.getmPatientName()));
        values.put(PATIENT_REPORTS_PRESCRIPTION_COL,String.valueOf(patientReport.getmPrescription()));
        values.put(PATIENT_REPORTS_DIAGNOSIS_COL,String.valueOf(patientReport.getmDiagnosis()));
        values.put(PATIENT_REPORTS_NOTE_COL,String.valueOf(patientReport.getmNote()));
        values.put(PATIENT_REPORTS_DATE_COL,String.valueOf(patientReport.getmDate()));

        SQLiteDatabase db = getWritableDatabase();
        db.insert(PATIENT_REPORTS_TABLE,null,values);
        db.close();

    }


    public ArrayList<Patient> getPatient(){

        ArrayList<Patient> patientsList= new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();

        String [] field={PATIENT_ID_COL,PATIENT_USER_NAME_COL,PATIENT_EMAIL_COL,PATIENT_PASSWORD_COL,
                         PATIENT_PHONE_NUMBER_COL,PATIENT_ADDRESS_COL,PATIENT_GENDER_COL,PATIENT_DATE_OF_BIRTH_COL};

        Cursor cursor = db.query(PATIENT_TABLE,field,null,null,null,null,null);

        int mPatientID = cursor.getColumnIndex(PATIENT_ID_COL);
        int mUserName = cursor.getColumnIndex(PATIENT_USER_NAME_COL);
        int mEmail = cursor.getColumnIndex(PATIENT_EMAIL_COL);
        int mPassword = cursor.getColumnIndex(PATIENT_PASSWORD_COL);
        int mPhoneNumber = cursor.getColumnIndex(PATIENT_PHONE_NUMBER_COL);
        int mAddress = cursor.getColumnIndex(PATIENT_ADDRESS_COL);
        int mGender = cursor.getColumnIndex(PATIENT_GENDER_COL);
        int mDateOfBirth = cursor.getColumnIndex(PATIENT_DATE_OF_BIRTH_COL);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){

            String patientID = cursor.getString(mPatientID);
            String userName = cursor.getString(mUserName);
            String email = cursor.getString(mEmail);
            String password= cursor.getString(mPassword);
            String phoneNumber= cursor.getString(mPhoneNumber);
            String address= cursor.getString(mAddress);
            String gender= cursor.getString(mGender);
            String dateOfBirth= cursor.getString(mDateOfBirth);

            patientsList.add(new Patient(patientID,userName,email,password,phoneNumber,address,gender,dateOfBirth));
        }

        cursor.close();
        return patientsList;

    }

    public ArrayList<Doctor> getDoctor(){

        ArrayList<Doctor> doctorsList= new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();

        String [] field={DOCTOR_ID_COL,DOCTOR_USER_NAME_COL,DOCTOR_EMAIL_COL,DOCTOR_PASSWORD_COL,DOCTOR_PHONE_NUMBER_COL,DOCTOR_ADDRESS_COL,DOCTOR_GENDER_COL,DOCTOR_DATE_OF_BIRTH_COL};

        Cursor cursor = db.query(DOCTOR_TABLE,field,null,null,null,null,null);

        int mID = cursor.getColumnIndex(DOCTOR_ID_COL);
        int mUserName = cursor.getColumnIndex(DOCTOR_USER_NAME_COL);
        int mEmail = cursor.getColumnIndex(DOCTOR_EMAIL_COL);
        int mPassword = cursor.getColumnIndex(DOCTOR_PASSWORD_COL);
        int mPhoneNumber = cursor.getColumnIndex(DOCTOR_PHONE_NUMBER_COL);
        int mAddress = cursor.getColumnIndex(DOCTOR_ADDRESS_COL);
        int mGender = cursor.getColumnIndex(DOCTOR_GENDER_COL);
        int mDateOfBirth = cursor.getColumnIndex(DOCTOR_DATE_OF_BIRTH_COL);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){

            String id = cursor.getString(mID);
            String userName = cursor.getString(mUserName);
            String email = cursor.getString(mEmail);
            String password= cursor.getString(mPassword);

            String phoneNumber= cursor.getString(mPhoneNumber);
            String address= cursor.getString(mAddress);
            String gender= cursor.getString(mGender);
            String dateOfBirth= cursor.getString(mDateOfBirth);

            doctorsList.add(new Doctor(id,userName,email,password,phoneNumber,address,gender,dateOfBirth));
        }

        cursor.close();
        return doctorsList;

    }

    public ArrayList<Pharmacist> getPharmacist(){

        ArrayList<Pharmacist> pharmacistsList= new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();

        String [] field={PHARMACIST_ID_COL,PHARMACIST_USER_NAME_COL,PHARMACIST_EMAIL_COL,PHARMACIST_PASSWORD_COL,
                PHARMACIST_PHONE_NUMBER_COL,PHARMACIST_ADDRESS_COL,PHARMACIST_GENDER_COL,PHARMACIST_DATE_OF_BIRTH_COL};

        Cursor cursor = db.query(PHARMACIST_TABLE,field,null,null,null,null,null);

        int mID = cursor.getColumnIndex(PHARMACIST_ID_COL);
        int mUserName = cursor.getColumnIndex(PHARMACIST_USER_NAME_COL);
        int mEmail = cursor.getColumnIndex(PHARMACIST_EMAIL_COL);
        int mPassword = cursor.getColumnIndex(PHARMACIST_PASSWORD_COL);

        int mPhoneNumber = cursor.getColumnIndex(PHARMACIST_PHONE_NUMBER_COL);
        int mAddress = cursor.getColumnIndex(PHARMACIST_ADDRESS_COL);
        int mGender = cursor.getColumnIndex(PHARMACIST_GENDER_COL);
        int mDateOfBirth = cursor.getColumnIndex(PHARMACIST_DATE_OF_BIRTH_COL);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){

            String id = cursor.getString(mID);
            String userName = cursor.getString(mUserName);
            String email = cursor.getString(mEmail);
            String password= cursor.getString(mPassword);

            String phoneNumber= cursor.getString(mPhoneNumber);
            String address= cursor.getString(mAddress);
            String gender= cursor.getString(mGender);
            String dateOfBirth= cursor.getString(mDateOfBirth);

            pharmacistsList.add(new Pharmacist(id,userName,email,password,phoneNumber,address,gender,dateOfBirth));
        }

        cursor.close();
        return pharmacistsList;

    }

    public ArrayList<DoctorCategory> getDoctorCategory(){

        ArrayList<DoctorCategory> doctorCategories= new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();

        String [] field={DOCTOR_CATEGORY_ID_COL,DOCTOR_CATEGORY_EMAIL_COL,DOCTOR_CATEGORY_CATEGORY_COL,DOCTOR_CATEGORY_SPECIALITY_COL};

        Cursor cursor = db.query(DOCTOR_CATEGORY_TABLE,field,null,null,null,null,null);

        int mDoctorID = cursor.getColumnIndex(DOCTOR_CATEGORY_ID_COL);
        int mDoctorEmail = cursor.getColumnIndex(DOCTOR_CATEGORY_EMAIL_COL);
        int mDoctorCategory = cursor.getColumnIndex(DOCTOR_CATEGORY_CATEGORY_COL);
        int mDoctorSpeciality = cursor.getColumnIndex(DOCTOR_CATEGORY_SPECIALITY_COL);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){

            String doctorID = cursor.getString(mDoctorID);
            String doctorEmail = cursor.getString(mDoctorEmail);
            String doctorCategory= cursor.getString(mDoctorCategory);
            String doctorSpeciality= cursor.getString(mDoctorSpeciality);


            doctorCategories.add(new DoctorCategory(doctorID,doctorEmail,doctorCategory,doctorSpeciality));
        }

        cursor.close();
        return doctorCategories;

    }


    public ArrayList<DoctorAppointment> getAppointments(){

        ArrayList<DoctorAppointment> appointmentList= new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();

        String [] field={DOCTOR_APPOINTMENT_DID_COL,DOCTOR_APPOINTMENT_TIME_COL,DOCTOR_APPOINTMENT_DATE_COL,DOCTOR_APPOINTMENT_DAY_COL,DOCTOR_APPOINTMENT_AVAILABILITY_COL};

        Cursor cursor = db.query(DOCTOR_APPOINTMENT_TABLE,field,null,null,null,null,null);

        int mDoctorID = cursor.getColumnIndex(DOCTOR_APPOINTMENT_DID_COL);
        int mTime = cursor.getColumnIndex(DOCTOR_APPOINTMENT_TIME_COL);
        int mDate = cursor.getColumnIndex(DOCTOR_APPOINTMENT_DATE_COL);
        int mDay = cursor.getColumnIndex(DOCTOR_APPOINTMENT_DAY_COL);
        int mAvailability = cursor.getColumnIndex(DOCTOR_APPOINTMENT_AVAILABILITY_COL);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){

            String doctorID = cursor.getString(mDoctorID);
            String time = cursor.getString(mTime);
            String date = cursor.getString(mDate);
            String day= cursor.getString(mDay);
            String availability= cursor.getString(mAvailability);

            DoctorAppointment doctorAppointment=new DoctorAppointment();
            doctorAppointment.setDid(doctorID);
            doctorAppointment.setTime(time);
            doctorAppointment.setDate(date);
            doctorAppointment.setDay(day);
            doctorAppointment.setAvailablity(availability);

            appointmentList.add(doctorAppointment);

        }

        cursor.close();
        return appointmentList;

    }

    public ArrayList<PatientAppointment> getPatientAppointments(){

        ArrayList<PatientAppointment> appointmentList= new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();

        String [] field={PATIENT_APPOINTMENT_PID_COL,PATIENT_APPOINTMENT_DID_COL,PATIENT_APPOINTMENT_NAME_COL,PATIENT_APPOINTMENT_TIME_COL,PATIENT_APPOINTMENT_DAY_COL};

        Cursor cursor = db.query(PATIENT_APPOINTMENTS_TABLE,field,null,null,null,null,null);

        int mPatientID = cursor.getColumnIndex(PATIENT_APPOINTMENT_PID_COL);
        int mDoctorID = cursor.getColumnIndex(PATIENT_APPOINTMENT_DID_COL);
        int mName = cursor.getColumnIndex(PATIENT_APPOINTMENT_NAME_COL);
        int mTime = cursor.getColumnIndex(PATIENT_APPOINTMENT_TIME_COL);
        int mDay = cursor.getColumnIndex(PATIENT_APPOINTMENT_DAY_COL);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){

            String patientID = cursor.getString(mPatientID);
            String doctorID = cursor.getString(mDoctorID);
            String name = cursor.getString(mName);
            String time= cursor.getString(mTime);
            String day= cursor.getString(mDay);

            PatientAppointment patientAppointment = new PatientAppointment();
            patientAppointment.setmPatientId(patientID);
            patientAppointment.setDid(doctorID);
            patientAppointment.setmPatientName(name);
            patientAppointment.setTime(time);
            patientAppointment.setDay(day);

            appointmentList.add(patientAppointment);

        }

        cursor.close();
        return appointmentList;

    }

    public ArrayList<PatientReport> getPatientReports(){

        ArrayList<PatientReport> patientReportList= new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();

        String [] field={PATIENT_REPORTS_PID_COL,PATIENT_REPORTS_DID_COL,PATIENT_REPORTS_NAME_COL,PATIENT_REPORTS_PRESCRIPTION_COL,PATIENT_REPORTS_DIAGNOSIS_COL,PATIENT_REPORTS_NOTE_COL,PATIENT_REPORTS_DATE_COL};

        Cursor cursor = db.query(PATIENT_REPORTS_TABLE,field,null,null,null,null,null);

        int mPatientID = cursor.getColumnIndex(PATIENT_REPORTS_PID_COL);
        int mDoctorID = cursor.getColumnIndex(PATIENT_REPORTS_DID_COL);
        int mName = cursor.getColumnIndex(PATIENT_REPORTS_NAME_COL);
        int mPrescription = cursor.getColumnIndex(PATIENT_REPORTS_PRESCRIPTION_COL);
        int mDiagnosis = cursor.getColumnIndex(PATIENT_REPORTS_DIAGNOSIS_COL);
        int mNote = cursor.getColumnIndex(PATIENT_REPORTS_NOTE_COL);
        int mDate = cursor.getColumnIndex(PATIENT_REPORTS_DATE_COL);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){

            String patientID = cursor.getString(mPatientID);
            String doctorID = cursor.getString(mDoctorID);
            String name = cursor.getString(mName);
            String prescription= cursor.getString(mPrescription);
            String diagnosis= cursor.getString(mDiagnosis);
            String note= cursor.getString(mNote);
            String date= cursor.getString(mDate);


            patientReportList.add(new PatientReport(patientID,doctorID,name,prescription,diagnosis,note,date));

        }

        cursor.close();
        return patientReportList;

    }


    public long getPatientProfilesCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, PATIENT_TABLE);

        db.close();
        return count;
    }

    public long getDoctorProfilesCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, DOCTOR_TABLE);

        db.close();
        return count;
    }

    public long getPharmacistProfilesCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, PHARMACIST_TABLE);

        db.close();
        return count;
    }

    public long getDoctorCategoryProfilesCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, DOCTOR_CATEGORY_TABLE);

        db.close();
        return count;
    }

    public long getDoctorAppointmentProfilesCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, DOCTOR_APPOINTMENT_TABLE);

        db.close();
        return count;
    }

    public long getPatientAppointmentProfilesCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, PATIENT_APPOINTMENTS_TABLE);

        db.close();
        return count;
    }

    public long getPatientReportsProfilesCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, PATIENT_REPORTS_TABLE);

        db.close();
        return count;
    }


    public boolean checkIfPatientExist() {

        SQLiteDatabase db = this.getWritableDatabase();

        String count = "SELECT count(*) FROM " + PATIENT_TABLE;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if (icount > 0) {
            return true;

        }
//leave
        else {
            return false;
//populate table

        }


    }

    public boolean checkIfDoctorExist() {

        SQLiteDatabase db = this.getWritableDatabase();

        String count = "SELECT count(*) FROM " + DOCTOR_TABLE;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if (icount > 0) {
            return true;

        }
//leave
        else {
            return false;
//populate table

        }


    }

    public boolean checkIfPharmacistExist() {

        SQLiteDatabase db = this.getWritableDatabase();

        String count = "SELECT count(*) FROM " + PHARMACIST_TABLE;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if (icount > 0) {
            return true;

        }
//leave
        else {
            return false;
//populate table

        }


    }

    public boolean checkIfDoctorCategoryExist() {

        SQLiteDatabase db = this.getWritableDatabase();

        String count = "SELECT count(*) FROM " + DOCTOR_CATEGORY_TABLE;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if (icount > 0) {
            return true;

        }
//leave
        else {
            return false;
//populate table

        }


    }


    public boolean checkIfDoctorAppointmentExist() {

        SQLiteDatabase db = this.getWritableDatabase();

        String count = "SELECT count(*) FROM " + DOCTOR_APPOINTMENT_TABLE;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if (icount > 0) {
            return true;

        }
//leave
        else {
            return false;
//populate table

        }


    }

    public boolean checkIfPatientAppointmentExist() {

        SQLiteDatabase db = this.getWritableDatabase();

        String count = "SELECT count(*) FROM " + PATIENT_APPOINTMENTS_TABLE;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if (icount > 0) {
            return true;

        }
//leave
        else {
            return false;
//populate table

        }


    }


    public boolean checkIfPatientReportsExist() {

        SQLiteDatabase db = this.getWritableDatabase();

        String count = "SELECT count(*) FROM " + PATIENT_REPORTS_TABLE;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if (icount > 0) {
            return true;

        }
//leave
        else {
            return false;
//populate table

        }


    }


    public void deleteAllPatients() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ PATIENT_TABLE);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + PATIENT_TABLE + "'");
        db.close();
    }

    public void deleteAllDoctors() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ DOCTOR_TABLE);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + DOCTOR_TABLE + "'");
        db.close();
    }

    public void deleteAllPharmacists() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ PHARMACIST_TABLE);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + PHARMACIST_TABLE + "'");
        db.close();
    }

    public void deleteAllDoctorCategories() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ DOCTOR_CATEGORY_TABLE);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + DOCTOR_CATEGORY_TABLE + "'");
        db.close();
    }

    public void deleteAllDoctorAppointments() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ DOCTOR_APPOINTMENT_TABLE);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + DOCTOR_APPOINTMENT_TABLE + "'");
        db.close();
    }

    public void deleteAllPatientAppointments() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ PATIENT_APPOINTMENTS_TABLE);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + PATIENT_APPOINTMENTS_TABLE + "'");
        db.close();
    }

    public void deleteAllPatientReports() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ PATIENT_REPORTS_TABLE);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + PATIENT_REPORTS_TABLE + "'");
        db.close();
    }

    public void deletePatientAppointment(String pid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String WHEREM = PATIENT_APPOINTMENT_PID_COL + " = '" + pid + "'";
        db.delete(PATIENT_APPOINTMENTS_TABLE,WHEREM,null);
        db.close();
    }

    public void updatePatientReport(String patientID, String prescription,String diagnosis, String note,String date){

        SQLiteDatabase db = getWritableDatabase();
        String WHEREM = PATIENT_REPORTS_PID_COL + " = '" + patientID + "'";

        ContentValues cv = new ContentValues();
        cv.put(PATIENT_REPORTS_PRESCRIPTION_COL,prescription); //These Fields should be your String values of actual column names
        cv.put(PATIENT_REPORTS_DIAGNOSIS_COL,diagnosis);
        cv.put(PATIENT_REPORTS_NOTE_COL,note);
        cv.put(PATIENT_REPORTS_DATE_COL,date);
        db.update(PATIENT_REPORTS_TABLE, cv, WHEREM, null);

    }

    public ArrayList<String> getDoctorIdBySpeciality(String speciality){

        ArrayList<String> doctorIdList= new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();

        String [] field={DOCTOR_CATEGORY_ID_COL};

        String WHEREM = DOCTOR_CATEGORY_SPECIALITY_COL + " = '" + speciality + "'";
        Cursor cursor = db.query(DOCTOR_CATEGORY_TABLE,field,WHEREM,null,null,null,null);


        int mDoctorID = cursor.getColumnIndex(DOCTOR_CATEGORY_ID_COL);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            String doctorID = cursor.getString(mDoctorID);
            doctorIdList.add(doctorID);
        }
        return doctorIdList;
    }

    public ArrayList<DoctorAppointment> getDoctorAppointByDoctorId(String doctorID){

        ArrayList<DoctorAppointment> appointments= new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String [] field={DOCTOR_APPOINTMENT_DAY_COL,DOCTOR_APPOINTMENT_TIME_COL,DOCTOR_APPOINTMENT_DATE_COL};

        String WHEREM = DOCTOR_APPOINTMENT_DID_COL + " = '" + doctorID + "'"+"AND "+DOCTOR_APPOINTMENT_AVAILABILITY_COL + " = '" + "Available" + "'";
        Cursor cursor = db.query(DOCTOR_APPOINTMENT_TABLE,field,WHEREM,null,null,null,null);

        int mAppointmentDate = cursor.getColumnIndex(DOCTOR_APPOINTMENT_DATE_COL);
        int mAppointmentDay = cursor.getColumnIndex(DOCTOR_APPOINTMENT_DAY_COL);
        int mAppointmentTime = cursor.getColumnIndex(DOCTOR_APPOINTMENT_TIME_COL);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){

            String appointmentDate = cursor.getString(mAppointmentDate);
            String appointmentDay = cursor.getString(mAppointmentDay);
            String appointmentTime = cursor.getString(mAppointmentTime);

            DoctorAppointment doctorAppointment = new DoctorAppointment();
            doctorAppointment.setDate(appointmentDate);
            doctorAppointment.setDay(appointmentDay);
            doctorAppointment.setTime(appointmentTime);

            appointments.add(doctorAppointment);
        }
        return appointments;
    }

}





