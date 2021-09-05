package com.project.helthcaresystem.Screens.PharmacyManagePaymentsScreen;

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
import com.project.helthcaresystem.Models.PatientBill;
import com.project.helthcaresystem.Models.Prescription;
import com.project.helthcaresystem.R;
import com.project.helthcaresystem.Screens.ShowPrescriptionsScreen.ShowPrescriptionAdapter;
import com.project.helthcaresystem.Screens.ViewBillingScreen.ViewBillingAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PharmacyManagePaymentsScreen extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private PharmacyManagePaymentAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar mProgressBar;
    private RequestQueue queue;
    ArrayList<Prescription> prescriptionArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_manage_payments_screen);

        bindViews();
        initialize();
    }

    private void bindViews() {
        mRecyclerView = findViewById(R.id.recycler_manage_payment_pharmacist_screen);
        mProgressBar = findViewById(R.id.progressBar_manage_payment_pharmacist_screen);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    /**
     * Initializing
     */
    private void initialize() {
        queue = Volley.newRequestQueue(this); // here we are creating request queue for network request
        prescriptionArrayList  = new ArrayList<>(); //creating patientReports List
        mProgressBar.setVisibility(View.VISIBLE);
        getOrdersRequest(Constants.GET_PHARMACY_ORDERS_URL,DatabaseHelper.getInstance(getBaseContext()).getPharmacist().get(0).getId());

    }

    /**
     * Below I Initialized recyclerView
     */
    private void initRecyclerView(ArrayList<Prescription> prescriptionArrayList) {

        mProgressBar.setVisibility(View.INVISIBLE);
       mLayoutManager = new LinearLayoutManager(getBaseContext()); //creating recyclerView layout
        mRecyclerView.setLayoutManager(mLayoutManager); // adding layout in recycler view

        mAdapter = new PharmacyManagePaymentAdapter(this, prescriptionArrayList); //creating adapter or calling it
        mRecyclerView.setAdapter(mAdapter); //adding adapter to recycler view

    }


    /**
     * Below we are making network request to get reports from server
     */
    private void getOrdersRequest(String url, final String pharmacy_id){


        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url, //here we init string request with request method post so the volley know that this is string request their are many other request such as JSon object request JsonArray request
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson(); //Gson Is the library which helped us to convert json to any other forms like json to pjo class, json to json array, json to string
                        try {  //try catch google exception handling in java or android
                            JsonObject jsonObject = gson.fromJson(response, JsonObject.class); //here we are converting json string to json object
                            jsonObject.get("error");

                            JsonArray jsonMessage = gson.fromJson(jsonObject.get("message"), JsonArray.class);
                            JsonArray patientNameArray = gson.fromJson(jsonObject.get("patientName"), JsonArray.class); //getting te json array from json object
                            JsonArray jsonElementDoctor = gson.fromJson(jsonObject.get("doctors"), JsonArray.class); //getting te json array from json object

                            for (int i = 0; i < jsonMessage.size(); i++) {  // here below using loop we are getting the json array to get elements like note, diagnosis etc.
/*

                                Constants.printLog("Message--"+i+"---"+jsonMessage.get(i));
                                Constants.printLog("Patient Name--"+i+"---"+patientNameArray.get(i));
*/

                                String patientName = String.valueOf(patientNameArray.get(i).getAsJsonObject().get("user_name")).replace("\"", "");
                                String diagnosis = String.valueOf(jsonMessage.get(i).getAsJsonObject().get("diagnosis")).replace("\"", "");
                                String orderID = String.valueOf(jsonMessage.get(i).getAsJsonObject().get("order_id")).replace("\"", "");
                                String pharmacyID = String.valueOf(jsonMessage.get(i).getAsJsonObject().get("pharmacy_id")).replace("\"", "");
                                String patientID = String.valueOf(jsonMessage.get(i).getAsJsonObject().get("patient_id")).replace("\"", "");
                                String prescription = String.valueOf(jsonMessage.get(i).getAsJsonObject().get("prescription")).replace("\"", "");
                                String note = String.valueOf(jsonMessage.get(i).getAsJsonObject().get("note")).replace("\"", "");
                                String date = String.valueOf(jsonMessage.get(i).getAsJsonObject().get("date")).replace("\"", "");


                                Prescription prescriptionData = new Prescription();
                                prescriptionData.setOrder_id(orderID);
                                prescriptionData.setPatient_id(patientID);
                                prescriptionData.setPatientName(patientName);
                                prescriptionData.setDiagnosis(diagnosis);
                                prescriptionData.setPrescription(prescription);
                                prescriptionData.setNote(note);
                                prescriptionData.setDate(date);
                                prescriptionArrayList.add(prescriptionData); //here we are adding reports to the patientReports ArrayList which we created in initialize method

                            }

                            initRecyclerView(prescriptionArrayList); //after loading data we are initializing the recycler view so the results will be loaded
                            mProgressBar.setVisibility(View.INVISIBLE);

                        }catch (Exception e){ // catch we are using to catch  unknown error/exception
                            mProgressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getBaseContext(),"Some Problem Try Again",Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //this method will display error if their is any error from server side
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //in this method we are put Post request parameter as per our webservice required
                Map<String, String> params = new HashMap<>();
                params.put("pharmacy_id", pharmacy_id);
                return params;
            }
        };

        queue.add(stringRequest);
    }
}
