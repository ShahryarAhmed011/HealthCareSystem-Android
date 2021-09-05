package com.project.helthcaresystem.Screens.PharmacistScreens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.project.helthcaresystem.Constants;
import com.project.helthcaresystem.DatabaseHelper.DatabaseHelper;
import com.project.helthcaresystem.Models.Patient;
import com.project.helthcaresystem.Models.Pharmacist;
import com.project.helthcaresystem.R;
import com.project.helthcaresystem.Screens.ChooseSpecialityScreen.ChooseSpecialityScreen;
import com.project.helthcaresystem.Screens.ContactPatientScreen.ContactPatient;
import com.project.helthcaresystem.Screens.DoctorScreens.DoctorMainScreen;
import com.project.helthcaresystem.Screens.Main.MainActivity;
import com.project.helthcaresystem.Screens.PatientScreens.PatientMainScreen;
import com.project.helthcaresystem.Screens.PharmacistManageAccountScreen.PharmacistManageAccountScreen;
import com.project.helthcaresystem.Screens.PharmacyManagePaymentsScreen.PharmacyManagePaymentsScreen;
import com.project.helthcaresystem.Screens.ShowPrescriptionsScreen.ShowPrescriptionAdapter;
import com.project.helthcaresystem.Screens.ShowPrescriptionsScreen.ShowPrescriptionsScreen;

import java.util.ArrayList;

public class PharmacistMainScreen extends AppCompatActivity {


    //working is same as doctor main screen

    private TextView mUserNameTextView;
    private TextView mEmailTextView;
    private Button mManageAccountButton;
    private Button mViewPrescription;
    private Button mManagePayments;
    private Button mLogOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacist_main);

        bindViews();
        initialize();

    }

    private void bindViews(){
        mUserNameTextView = findViewById(R.id.user_name_text_view_pharmacist_main_screen);
        mEmailTextView = findViewById(R.id.email_text_view_pharmacist_main_screen);
        mManageAccountButton = findViewById(R.id.manage_account_button_pharmacist_main_screen);
        mViewPrescription = findViewById(R.id.show_prescriptions_pharmacist_main_screen);
        mManagePayments = findViewById(R.id.manage_payments_pharmacist_main_screen);
        mLogOutButton = findViewById(R.id.logOut_button_pharmacist_main_screen);

      //  Constants.printLog("pharmacist id"+DatabaseHelper.getInstance(getBaseContext()).getPharmacist().get(0).getId());
    }


    private void initialize(){

        checkUserOnlineStatus();

        mManageAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PharmacistMainScreen.this, PharmacistManageAccountScreen.class);
                // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        mViewPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PharmacistMainScreen.this, ShowPrescriptionsScreen.class);
                // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


        mManagePayments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PharmacistMainScreen.this, ContactPatient.class);
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

                Intent intent = new Intent(PharmacistMainScreen.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    private void checkUserOnlineStatus() {
        if(DatabaseHelper.getInstance(getBaseContext()).checkIfPatientExist()){
            //Log.v("DatabaseHelper"," Patient Data Exit");

        }else{
            //Log.v("DatabaseHelper","Patient Data Not Exist ");
        }

        if(DatabaseHelper.getInstance(getBaseContext()).checkIfDoctorExist()){
          //  Log.v("DatabaseHelper"," Doctor Data Exit");

        }else{
           // Log.v("DatabaseHelper","Doctor Data Not Exist ");
        }

        if(DatabaseHelper.getInstance(getBaseContext()).checkIfPharmacistExist()){
            Log.v("DatabaseHelper"," Pharmacist Data Exit");

            ArrayList<Pharmacist> pharmacists = DatabaseHelper.getInstance(getApplicationContext()).getPharmacist();

            mUserNameTextView.setText("Pharmacists Name:--"+pharmacists.get(0).getUser_name());
            mEmailTextView.setText("Pharmacists Email:--"+pharmacists.get(0).getEmail());

        }else{
            Log.v("DatabaseHelper","Pharmacist Data Not Exist ");
        }
    }

    @Override
    public void onBackPressed() { //when user press back button
        super.onBackPressed();
    }
}
