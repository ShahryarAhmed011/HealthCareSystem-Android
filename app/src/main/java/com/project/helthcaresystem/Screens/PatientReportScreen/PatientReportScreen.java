package com.project.helthcaresystem.Screens.PatientReportScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.project.helthcaresystem.Constants;
import com.project.helthcaresystem.DatabaseHelper.DatabaseHelper;
import com.project.helthcaresystem.Models.PatientAppointment;
import com.project.helthcaresystem.Models.PatientReport;
import com.project.helthcaresystem.R;
import com.project.helthcaresystem.Screens.UpdateScheduleScreen.RecyclerViewAdapter;

import java.util.ArrayList;

public class PatientReportScreen extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private PatientReportAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_report_screen);

        getSupportActionBar().setTitle("Patient Reports");
        bindViews();
        initialize();

    }
        private void bindViews(){
            mRecyclerView = findViewById(R.id.recycler_view_patient_reports_screen);

        }

        private void initialize(){
            initRecyclerView();
        }

        private void initRecyclerView() {

            if(DatabaseHelper.getInstance(getBaseContext()).checkIfPatientReportsExist()){

                ArrayList<PatientReport> patientReports = DatabaseHelper.getInstance(getBaseContext()).getPatientReports(); // DatabaseHelper.getInstance(getBaseContext()).getPatientAppointments();
                //patientAppointments.add(new PatientReport("ASD","FSDF","SDAS","DFSDF","ASDFAS","SDF","SDA"));

                mLayoutManager = new LinearLayoutManager(getBaseContext());
                mRecyclerView.setLayoutManager(mLayoutManager);

                mAdapter = new PatientReportAdapter(this, patientReports);
                mRecyclerView.setAdapter(mAdapter);
                Log.d(Constants.DEBUG_TAG,"Patient Appointments Data Not Exist");

            }else{

                Log.d(Constants.DEBUG_TAG,"Patient Appointments Data Not Exist");
                //show error here
            }

        }
        }