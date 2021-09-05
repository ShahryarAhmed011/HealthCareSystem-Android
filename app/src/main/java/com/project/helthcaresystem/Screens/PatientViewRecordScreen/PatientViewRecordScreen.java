package com.project.helthcaresystem.Screens.PatientViewRecordScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.project.helthcaresystem.Constants;
import com.project.helthcaresystem.DatabaseHelper.DatabaseHelper;
import com.project.helthcaresystem.Models.PatientAppointment;
import com.project.helthcaresystem.Models.PatientReport;
import com.project.helthcaresystem.R;
import com.project.helthcaresystem.Screens.UpdateScheduleScreen.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PatientViewRecordScreen extends AppCompatActivity {

    /**
     * Below I Initialized All required Variables
     */
    private RecyclerView mRecyclerView;
    private ViewRecordAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar mProgressBar;
    private RequestQueue queue;
    ArrayList<PatientReport> patientReports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_view_record_screen);
        getSupportActionBar().setTitle("View Records");
        bindViews();
        initialize();

        String patient_id =DatabaseHelper.getInstance(getBaseContext()).getPatient().get(0).getId();
        Log.d(Constants.DEBUG_TAG, String.valueOf(patient_id));
        getReportRequest(Constants.PATIENT_REPORT_URL,patient_id);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    /**
     * Below we are binding views with class
     */
    private void bindViews() {
        mRecyclerView = findViewById(R.id.recycler_view_patient_record_screen);
        mProgressBar = findViewById(R.id.progressBar_patient_view_record_screen);
        mProgressBar.setVisibility(View.INVISIBLE);

    }

    /**
     * Initializing
     */
    private void initialize() {
        queue = Volley.newRequestQueue(this); // here we are creating request queue for network request
        patientReports  = new ArrayList<>(); //creating patientReports List
 //       initRecyclerView();
    }

    /**
     * Below I Initialized recyclerView
     */
    private void initRecyclerView() {

        mLayoutManager = new LinearLayoutManager(getBaseContext()); //creating recyclerView layout
        mRecyclerView.setLayoutManager(mLayoutManager); // adding layout in recycler view

        mAdapter = new ViewRecordAdapter(this, patientReports); //creating adapter or calling it
        mRecyclerView.setAdapter(mAdapter); //adding adapter to recycler view

    }


    /**
     * Below we are making network request to get reports from server
     */
    private void getReportRequest(String url, final String patient_id){


        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url, //here we init string request with request method post so the volley know that this is string request their are many other request such as JSon object request JsonArray request
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                       Gson gson = new Gson(); //Gson Is the library which helped us to convert json to any other forms like json to pjo class, json to json array, json to string

                        try {  //try catch google exception handling in java or android

                            JsonObject jsonObject = gson.fromJson(response, JsonObject.class); //here we are converting json string to json object
                            jsonObject.get("error"); //get the data in json object with error tag;

                            JsonArray jsonElements = gson.fromJson(jsonObject.get("reports"), JsonArray.class); //getting te json array from json object
                            JsonArray jsonElementDoctor = gson.fromJson(jsonObject.get("doctors"), JsonArray.class); //getting te json array from json object

                            for (int i = 0; i < jsonElements.size(); i++) {  // here below using loop we are getting the json array to get elements like note, diagnosis etc.

                                String doctor_id = String.valueOf(jsonElements.get(i).getAsJsonObject().get("did")).replace("\"", "");
                                String doctor = String.valueOf(jsonElementDoctor.get(i).getAsJsonObject().get("user_name")).replace("\"", "");
                                String diagnosis = String.valueOf(jsonElements.get(i).getAsJsonObject().get("diagnosis")).replace("\"", "");
                                String prescription = String.valueOf(jsonElements.get(i).getAsJsonObject().get("prescription")).replace("\"", "");
                                String note = String.valueOf(jsonElements.get(i).getAsJsonObject().get("note")).replace("\"", "");
                                String date = String.valueOf(jsonElements.get(i).getAsJsonObject().get("date")).replace("\"", "");

                                PatientReport patient=new PatientReport();
                                patient.setmDoctorID(doctor_id);
                                patient.setmDoctorName(doctor);
                                patient.setmPrescription(prescription);
                                patient.setmDiagnosis(diagnosis);
                                patient.setmNote(note);
                                patient.setmDate(date);

                                patientReports.add(patient);

                              //  patientReports.add(new PatientReport(doctor_id,doctor, prescription,diagnosis,  note, date)); //here we are adding reports to the patientReports ArrayList which we created in initialize method

                            }

                            initRecyclerView(); //after loading data we are initializing the recycler view so the results will be loaded
                            mProgressBar.setVisibility(View.INVISIBLE);

                        }catch (Exception e){ // catch we are using to catch  unknown error/exception
                            mProgressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getBaseContext(),"No Data Exist",Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //this method will display error if their is any error from server side
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.v(Constants.DEBUG_TAG,error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //in this method we are put Post request parameter as per our webservice required
                Map<String, String> params = new HashMap<>();
                params.put("patient_id", patient_id);
                return params;
            }
        };

        queue.add(stringRequest);
    }

}