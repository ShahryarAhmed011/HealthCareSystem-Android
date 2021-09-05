package com.project.helthcaresystem.Screens.DoctorScreens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.project.helthcaresystem.Constants;
import com.project.helthcaresystem.DatabaseHelper.DatabaseHelper;
import com.project.helthcaresystem.Models.Doctor;
import com.project.helthcaresystem.Models.Patient;
import com.project.helthcaresystem.Models.PatientReport;
import com.project.helthcaresystem.R;
import com.project.helthcaresystem.Screens.ChooseSpecialityScreen.ChooseSpecialityScreen;
import com.project.helthcaresystem.Screens.Main.MainActivity;
import com.project.helthcaresystem.Screens.ManageAccountScreen.ManageAccountScreen;
import com.project.helthcaresystem.Screens.PatientReportScreen.PatientReportScreen;
import com.project.helthcaresystem.Screens.PatientScreens.PatientLoginScreen;
import com.project.helthcaresystem.Screens.PatientScreens.PatientMainScreen;
import com.project.helthcaresystem.Screens.PharmacistScreens.PharmacistLoginScreen;
import com.project.helthcaresystem.Screens.ShowAppointmentsScreen.ShowAppointmentScreen;
import com.project.helthcaresystem.Screens.UpdateScheduleScreen.UpdateScheduleScreen;

import java.util.ArrayList;

public class DoctorMainScreen extends AppCompatActivity {

    private TextView mUserNameTextView;
    private TextView mEmailTextView;
    private Button mManageAccountButton;
    private Button mChooseSpeciality;
    private Button mShowAppointments;
    private Button mUpdateSchedule;
    private Button mPatientReport;
    private Button mLogOutButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_main_screen);

        bindViews();
        initialize();

        Log.d(Constants.DEBUG_TAG,String.valueOf(DatabaseHelper.getInstance(getBaseContext()).getDoctor().get(0).getId()));



    }

    //same repeating every time
    private void bindViews() {
        mUserNameTextView = findViewById(R.id.user_name_text_view_doctor_main_screen);
        mEmailTextView = findViewById(R.id.email_text_view_doctor_main_screen);
        mManageAccountButton = findViewById(R.id.manage_account_button_doctor_main_screen);
        mChooseSpeciality = findViewById(R.id.choose_speciality_button_doctor_main_screen);
        mShowAppointments = findViewById(R.id.show_appointments_button_doctor_main_screen);
        mUpdateSchedule = findViewById(R.id.update_schedule_button_doctor_main_screen);
        mPatientReport = findViewById(R.id.patient_report_button_doctor_main_screen);
        mLogOutButton = findViewById(R.id.logOut_button_doctor_main_screen);


    }

    //same repeating every time
    private void initialize() {

        checkUserOnlineStatus();

        mChooseSpeciality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorMainScreen.this, ChooseSpecialityScreen.class);
               // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });



        //*Manage Account Button
        mManageAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DoctorMainScreen.this, ManageAccountScreen.class);
               // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });

        mShowAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorMainScreen.this, ShowAppointmentScreen.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        mUpdateSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorMainScreen.this, UpdateScheduleScreen.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        mPatientReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorMainScreen.this, PatientReportScreen.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        //*logout button
        mLogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "LogOut", Toast.LENGTH_LONG).show();

                DatabaseHelper.getInstance(getBaseContext()).deleteAllPatients();
                DatabaseHelper.getInstance(getBaseContext()).deleteAllDoctors();
                DatabaseHelper.getInstance(getBaseContext()).deleteAllPharmacists();
                DatabaseHelper.getInstance(getBaseContext()).deleteAllDoctorCategories();
                DatabaseHelper.getInstance(getBaseContext()).deleteAllDoctorAppointments();
                DatabaseHelper.getInstance(getBaseContext()).deleteAllPatientAppointments();
                DatabaseHelper.getInstance(getBaseContext()).deleteAllPatientReports();

                Intent intent = new Intent(DoctorMainScreen.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }

    private void checkUserOnlineStatus() {
        if (DatabaseHelper.getInstance(getBaseContext()).checkIfPatientExist()) {
            //Log.v("DatabaseHelper"," Patient Data Exit");

        } else {
            //Log.v("DatabaseHelper","Patient Data Not Exist ");
        }

        if (DatabaseHelper.getInstance(getBaseContext()).checkIfDoctorExist()) {
            Log.v("DatabaseHelper", " Doctor Data Exit");

            ArrayList<Doctor> doctors = DatabaseHelper.getInstance(getApplicationContext()).getDoctor();

            mUserNameTextView.setText("Doctor Name: " + doctors.get(0).getUser_name());
            mEmailTextView.setText("Doctor Email: " + doctors.get(0).getEmail());

        } else {
            Log.v("DatabaseHelper", "Doctor Data Not Exist ");
        }

        if (DatabaseHelper.getInstance(getBaseContext()).checkIfPharmacistExist()) {
            Log.v("DatabaseHelper", " Pharmacist Data Exit");

        } else {
            Log.v("DatabaseHelper", "Pharmacist Data Not Exist ");
        }
    }

    @Override
    public void onBackPressed() { //when user press back button
        super.onBackPressed();


    }

}
