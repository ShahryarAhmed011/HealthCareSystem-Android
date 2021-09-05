package com.project.helthcaresystem.Screens.PatientScreens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.project.helthcaresystem.DatabaseHelper.DatabaseHelper;
import com.project.helthcaresystem.Models.Patient;
import com.project.helthcaresystem.R;
import com.project.helthcaresystem.Screens.ContactPharmacyScreen.ContactPharmacyScreen;
import com.project.helthcaresystem.Screens.DoctorScreens.DoctorMainScreen;
import com.project.helthcaresystem.Screens.Main.MainActivity;
import com.project.helthcaresystem.Screens.MakeAppointmentScreens.MakeAppointmentScreen;
import com.project.helthcaresystem.Screens.PatientManageAccountScreens.PatientManageAccountScreen;
import com.project.helthcaresystem.Screens.PatientViewRecordScreen.PatientViewRecordScreen;
import com.project.helthcaresystem.Screens.PharmacistScreens.PharmacistLoginScreen;
import com.project.helthcaresystem.Screens.PharmacistScreens.PharmacistMainScreen;
import com.project.helthcaresystem.Screens.ViewBillingScreen.ViewBillingScreen;

import java.util.ArrayList;

public class PatientMainScreen extends AppCompatActivity {

    //below is the same working as doctor Main screen you can check comments their


    private TextView mUserNameTextView;
    private TextView mEmailTextView;
    private Button mManageAccountButton;
    private Button mViewRecordButton;
    private Button mMakeAnAppointmentButton;
    private Button mContactPharmacyButton;
    private Button mViewBillingButton;
    private Button mLogOutButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_main_screen);
        bindViews();
        initialize();
    }

    @Override
    public void onBackPressed() { //when user press back button
        super.onBackPressed();

    }

    private void bindViews(){
        mUserNameTextView = findViewById(R.id.user_name_text_view_patient_main_screen);
        mEmailTextView = findViewById(R.id.email_text_view_patient_main_screen);
        mManageAccountButton = findViewById(R.id.manage_account_button_patient_main_screen);
        mViewRecordButton = findViewById(R.id.view_record_button_patient_main_screen);
        mMakeAnAppointmentButton = findViewById(R.id.make_appointment_button_patient_main_screen);
        mContactPharmacyButton = findViewById(R.id.contact_pharmacy_button_patient_main_screen);
        mViewBillingButton = findViewById(R.id.view_billing_button_patient_main_screen);
        mLogOutButton = findViewById(R.id.logOut_button_patient_main_screen);
    }

    private void initialize(){

        checkUserOnlineStatus();

        mManageAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientMainScreen.this, PatientManageAccountScreen.class);
                // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        mViewRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientMainScreen.this, PatientViewRecordScreen.class);
               // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });

        mMakeAnAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientMainScreen.this, MakeAppointmentScreen.class);
                // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        mContactPharmacyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientMainScreen.this, ContactPharmacyScreen.class);
                // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });



        mViewBillingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientMainScreen.this, ViewBillingScreen.class);
                // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        mLogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"LogOut",Toast.LENGTH_LONG).show();

                DatabaseHelper.getInstance(getBaseContext()).deleteAllPatients();
                DatabaseHelper.getInstance(getBaseContext()).deleteAllDoctors();
                DatabaseHelper.getInstance(getBaseContext()).deleteAllPharmacists();

                Intent intent = new Intent(PatientMainScreen.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    private void checkUserOnlineStatus() {
        if(DatabaseHelper.getInstance(getBaseContext()).checkIfPatientExist()){
            //Log.v("DatabaseHelper"," Patient Data Exit");

            ArrayList<Patient> patients = DatabaseHelper.getInstance(getApplicationContext()).getPatient();

            mUserNameTextView.setText("Patient Name:--"+patients.get(0).getUser_name());
            mEmailTextView.setText("Patient Email:--"+patients.get(0).getEmail());

        }else{
            //Log.v("DatabaseHelper","Patient Data Not Exist ");
        }

        if(DatabaseHelper.getInstance(getBaseContext()).checkIfDoctorExist()){
            Log.v("DatabaseHelper"," Doctor Data Exit");

        }else{
            Log.v("DatabaseHelper","Doctor Data Not Exist ");
        }

        if(DatabaseHelper.getInstance(getBaseContext()).checkIfPharmacistExist()){
            Log.v("DatabaseHelper"," Pharmacist Data Exit");

        }else{
            Log.v("DatabaseHelper","Pharmacist Data Not Exist ");
        }
    }
}
