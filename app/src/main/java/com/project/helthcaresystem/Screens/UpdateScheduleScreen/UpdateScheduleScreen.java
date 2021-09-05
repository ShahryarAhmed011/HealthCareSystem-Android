package com.project.helthcaresystem.Screens.UpdateScheduleScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.project.helthcaresystem.Constants;
import com.project.helthcaresystem.DatabaseHelper.DatabaseHelper;
import com.project.helthcaresystem.Models.PatientAppointment;
import com.project.helthcaresystem.R;

import java.util.ArrayList;

public class UpdateScheduleScreen extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_schedule_screen);
        getSupportActionBar().setTitle("Update Schedule");
        bindViews();
        initialize();
    }

    private void bindViews(){
        mRecyclerView = findViewById(R.id.recycler_view_update_schedule_screen);
        mProgressBar = findViewById(R.id.update_schedule_progressBar);
        mProgressBar.setVisibility(View.INVISIBLE);

    }

    private void initialize(){
        initRecyclerView();
    }

    private void initRecyclerView() {

        if(DatabaseHelper.getInstance(getBaseContext()).checkIfPatientAppointmentExist()){
            ArrayList<PatientAppointment> patientAppointments = DatabaseHelper.getInstance(getBaseContext()).getPatientAppointments();

            mLayoutManager = new LinearLayoutManager(getBaseContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new RecyclerViewAdapter(getBaseContext(),mProgressBar, patientAppointments, new RecyclerViewAdapter.OnItemChangeCallBack() {
                @Override
                public void onSuccess(String patientID) {
                    Log.d(Constants.DEBUG_TAG,"Response From Cancel Button On Recycler View Pressed");
                    mRecyclerView.refreshDrawableState();
                }
            });
            mRecyclerView.setAdapter(mAdapter);
            Log.d(Constants.DEBUG_TAG,"Patient Appointments Data Not Exist");
        }else{

            Log.d(Constants.DEBUG_TAG,"Patient Appointments Data Not Exist");
            //show error here
        }

    }
}
