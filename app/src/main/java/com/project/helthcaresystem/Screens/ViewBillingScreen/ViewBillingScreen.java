package com.project.helthcaresystem.Screens.ViewBillingScreen;

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
import com.project.helthcaresystem.Models.Patient;
import com.project.helthcaresystem.Models.PatientBill;
import com.project.helthcaresystem.Models.PatientReport;
import com.project.helthcaresystem.R;
import com.project.helthcaresystem.Screens.PatientViewRecordScreen.ViewRecordAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewBillingScreen extends AppCompatActivity {

    /**
     * Below I Initialized All required Variables
     */
    private RecyclerView mRecyclerView;
    private  ViewBillingAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar mProgressBar;
    private RequestQueue queue;
    ArrayList<PatientBill> patientBills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_billing_screen);
        bindViews();
        initialize();
    }

    private void bindViews() {
        mRecyclerView = findViewById(R.id.recycler_view_billing_screen);
        mProgressBar = findViewById(R.id.progressBar_view_billing_screen);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    private void initialize() {
        queue = Volley.newRequestQueue(this); // here we are creating request queue for network request
        patientBills  = new ArrayList<>(); //creating patientReports List

        mProgressBar.setVisibility(View.VISIBLE);
        getBillingInfoRequest(Constants.GET_BILLING_INFO_URL, DatabaseHelper.getInstance(getBaseContext()).getPatient().get(0).getId());
    }

    /**
     * Below I Initialized recyclerView
     */
    private void initRecyclerView() {

       /* patientBills.add(new PatientBill("123","sdf","sdfsd","sdfsdf","sdf","sdfsd","sdfsdf",
                "sdf","sdfsd","sdfsdf"));

        patientBills.add(new PatientBill("123","sdf","sdfsd","sdfsdf","sdf","sdfsd","sdfsdf",
                "sdf","sdfsd","sdfsdf"));

        patientBills.add(new PatientBill("123","sdf","sdfsd","sdfsdf","sdf","sdfsd","sdfsdf",
                "sdf","sdfsd","sdfsdf"));*/


        mLayoutManager = new LinearLayoutManager(getBaseContext()); //creating recyclerView layout
        mRecyclerView.setLayoutManager(mLayoutManager); // adding layout in recycler view
        mAdapter = new ViewBillingAdapter(this, patientBills); //creating adapter or calling it
        mRecyclerView.setAdapter(mAdapter); //adding adapter to recycler view

    }


    private void getBillingInfoRequest(String url, final String patient_id){

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url, //here we init string request with request method post so the volley know that this is string request their are many other request such as JSon object request JsonArray request
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Constants.printLog("Reponse-->",response);
                        Gson gson = new Gson(); //Gson Is the library which helped us to convert json to any other forms like json to pjo class, json to json array, json to string

                        try {  //try catch google exception handling in java or android
                            JsonObject jsonObject = gson.fromJson(response, JsonObject.class); //here we are converting json string to json object
                            jsonObject.get("error"); //get the data in json object with error tag;

                            JsonArray orders = gson.fromJson(jsonObject.get("Order"), JsonArray.class); //getting te json array from json object
                            JsonArray reports = gson.fromJson(jsonObject.get("reports"), JsonArray.class); //getting te json array from json object
                            JsonArray pharmacistName = gson.fromJson(jsonObject.get("PharmacistName"), JsonArray.class); //getting te json array from json object

                            for (int i = 0; i < reports.size(); i++) {
                   //             Constants.printLog(String.valueOf(reports.get(i).getAsJsonArray().get(0).getAsJsonObject().get("diagnosis")));
                            }


                                for (int i = 0; i < orders.size(); i++) {  // here below using loop we are getting the json array to get elements like note, diagnosis etc.

                                    String date = String.valueOf(orders.get(i).getAsJsonObject().get("date")).replace("\"", "");
                                    String doctor_fee = String.valueOf(orders.get(i).getAsJsonObject().get("doctor_fees")).replace("\"", "");
                                    String pharmacist_fee = String.valueOf(orders.get(i).getAsJsonObject().get("pharmacist_fees")).replace("\"", "");
                                    String payment_status = String.valueOf(orders.get(i).getAsJsonObject().get("payment_status")).replace("\"", "");
                                    String doctor_name = String.valueOf(orders.get(i).getAsJsonObject().get("doctor_name")).replace("\"", "");
                                    String pharmacist_name = String.valueOf(pharmacistName.get(i).getAsJsonObject().get("user_name")).replace("\"", "");

                                    String diagnosis = String.valueOf(reports.get(i).getAsJsonArray().get(0).getAsJsonObject().get("diagnosis")).replace("\"", "");
                                    String prescription = String.valueOf(reports.get(i).getAsJsonArray().get(0).getAsJsonObject().get("prescription")).replace("\"", "");
                                    String note = String.valueOf(reports.get(i).getAsJsonArray().get(0).getAsJsonObject().get("note")).replace("\"", "");

                                    PatientBill patientBill = new PatientBill();
                                    patientBill.setBill_date(date);
                                    patientBill.setDoctor_fee(doctor_fee);
                                    patientBill.setPharmacist_fee(pharmacist_fee);
                                    patientBill.setPayment_status(payment_status);
                                    patientBill.setDoctor_name(doctor_name);
                                    patientBill.setPharmacist_name(pharmacist_name);
                                    patientBill.setDiagnosis(diagnosis);
                                    patientBill.setPrescription(prescription);
                                    patientBill.setNote(note);
                                    patientBills.add(patientBill);
                                }





                           /*

                                if(mapArrayList.get(i).get("diagnosis")==null){

                                }else{
                                    Constants.printLog("mapArrayList--diagnosis"+i+"--"+mapArrayList.get(i).get("diagnosis"));
                                    Constants.printLog("mapArrayList--prescription"+i+"--"+mapArrayList.get(i).get("prescription"));
                                    Constants.printLog("mapArrayList--note"+i+"--"+mapArrayList.get(i).get("note"));
                                }
*/



                            initRecyclerView(); //after loading data we are initializing the recycler view so the results will be loaded
                            mProgressBar.setVisibility(View.INVISIBLE);

                        }catch (Exception e){ // catch we are using to catch  unknown error/exception
                            mProgressBar.setVisibility(View.INVISIBLE);
                        //    Constants.printLog(String.valueOf(e));
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
