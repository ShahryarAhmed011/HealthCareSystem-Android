package com.project.helthcaresystem.Screens.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.project.helthcaresystem.DatabaseHelper.DatabaseHelper;
import com.project.helthcaresystem.Models.Pharmacist;
import com.project.helthcaresystem.R;
import com.project.helthcaresystem.Screens.DoctorScreens.DoctorLoginScreen;
import com.project.helthcaresystem.Screens.DoctorScreens.DoctorMainScreen;
import com.project.helthcaresystem.Screens.PatientScreens.PatientLoginScreen;
import com.project.helthcaresystem.Screens.PatientScreens.PatientMainScreen;
import com.project.helthcaresystem.Screens.PharmacistScreens.PharmacistLoginScreen;
import com.project.helthcaresystem.Screens.PharmacistScreens.PharmacistMainScreen;

public class MainActivity extends AppCompatActivity {

    private Button mPatientButton; //button initialized
    private Button mDoctorButton; //button initialized
    private Button mPharmacistButton; //button initialized

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();
        initialize();

    }

    private void bindViews(){

        mPatientButton = findViewById(R.id.patient_button_main); //button cast with java file
        mDoctorButton = findViewById(R.id.doctor_button_main); //button cast with java file
        mPharmacistButton = findViewById(R.id.pharmacist_button_main); //button cast with java file

    }

    private void initialize(){

        checkUserOnlineStatus();

        mPatientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(MainActivity.this, PatientLoginScreen.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);


            }
        });


        mDoctorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, DoctorLoginScreen.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });

        mPharmacistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, PharmacistLoginScreen.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });

    }

    private void checkUserOnlineStatus() {
        if(DatabaseHelper.getInstance(getBaseContext()).checkIfPatientExist()){
            Log.v("DatabaseHelper"," Patient Data Exit");

            Intent intent = new Intent(MainActivity.this, PatientMainScreen.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);


        }else{
            Log.v("DatabaseHelper","Patient Data Not Exist ");
        }

        if(DatabaseHelper.getInstance(getBaseContext()).checkIfDoctorExist()){
            Log.v("DatabaseHelper"," Doctor Data Exit");

            Intent intent = new Intent(MainActivity.this, DoctorMainScreen.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }else{
            Log.v("DatabaseHelper","Doctor Data Not Exist ");
        }

        if(DatabaseHelper.getInstance(getBaseContext()).checkIfPharmacistExist()){
            Log.v("DatabaseHelper"," Pharmacist Data Exit");

            Intent intent = new Intent(MainActivity.this, PharmacistMainScreen.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }else{
            Log.v("DatabaseHelper","Pharmacist Data Not Exist ");
        }
    }
}
