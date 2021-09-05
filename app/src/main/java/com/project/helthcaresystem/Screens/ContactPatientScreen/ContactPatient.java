package com.project.helthcaresystem.Screens.ContactPatientScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

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
import com.project.helthcaresystem.Models.Pharmacist;
import com.project.helthcaresystem.R;
import com.project.helthcaresystem.Screens.ContactPharmacyScreen.ContactPharmacyAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ContactPatient extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ContactPatientAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar mProgressBar;
    private RequestQueue queue;
    ArrayList<Patient> patientList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_patient);
        bindViews();
        initialize();
        mProgressBar.setVisibility(View.VISIBLE);
    }

    //below method functionality or comment on line is same as choose speciality screen
    private void bindViews(){
        mRecyclerView = findViewById(R.id.contact_patient_screen_recycler_view);
        mProgressBar = findViewById(R.id.contact_patient_screen_progressBar);
        try {
            mProgressBar.setVisibility(View.VISIBLE);
        }catch (Exception e){
            Constants.printLog("Exception  ",String.valueOf(e));
        }
    }

    //below method functionality or comment on line is same as choose speciality screen
    private void initialize() {
        queue = Volley.newRequestQueue(this);
        patientList = new ArrayList<>();
        mProgressBar.setVisibility(View.VISIBLE);


        initRecyclerView(patientList);

        getAllPharmacyCustomers(Constants.GET_PHARMACIST_CUSTOMERS_URL); //patients

    }

    private void initRecyclerView(ArrayList<Patient> patients) {

        /*ArrayList<Patient> patients1 = new ArrayList<>();


        Patient patient=new Patient();
        patient.setUser_name("A");
        patient.setEmail("Email");
        patient.setPhone_number("03333");

        Patient patient2=new Patient();
        patient2.setUser_name("A");
        patient2.setEmail("Email");
        patient2.setPhone_number("03333");

        patients1.add(patient);
        patients1.add(patient2);*/




        mLayoutManager = new LinearLayoutManager(getBaseContext()); //creating recyclerView layout
        mRecyclerView.setLayoutManager(mLayoutManager); // adding layout in recycler view
        mAdapter = new ContactPatientAdapter(this, patientList); //creating adapter or calling it
        mRecyclerView.setAdapter(mAdapter); //adding adapter to recycler view

        mProgressBar.setVisibility(View.GONE);
    }



    public void getAllPharmacyCustomers(String url){

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response

                        Constants.printLog("response: ",response);

                        Gson gson= new Gson();
                        try {
                            JsonObject jsonObject = gson.fromJson(response.toString(), JsonObject.class);//here converting response to json objec
                            JsonArray jsonArray = gson.fromJson(jsonObject.get("Patients"), JsonArray.class); //here converting response to json object to json array because we are receiving array

                            if(jsonArray.size()>=0){
                                //below simply populating the array
                                for(int i = 0;i<jsonArray.size();i++){
                                    String patientName= String.valueOf(jsonArray.get(i).getAsJsonObject().get("user_name")).replace("\"", "");;
                                    String email = String.valueOf(jsonArray.get(i).getAsJsonObject().get("email")).replace("\"", "");;
                                    String phoneNumber = String.valueOf(jsonArray.get(i).getAsJsonObject().get("phone_number")).replace("\"", "");;

                                 /*   Constants.printLog("Name  ",patientName);
                                    Constants.printLog("phone  ",phoneNumber);
                                    Constants.printLog("email  ",email);
*/
                                    Patient patient=new Patient();
                                    patient.setUser_name(patientName);
                                    patient.setEmail(email);
                                    patient.setPhone_number(phoneNumber);

                                    patientList.add(patient);

                         //           Pharmacist pharmacist = gson.fromJson(jsonArray.get(i),Pharmacist.class);//converting respons to pjo

                                   // patientList.add(pharmacist);
                                }
                                initRecyclerView(patientList);//after getting respons initializing recyvler list view if data exist it will initialize other wise it will not call
                            }else{
                                //some error
                                Constants.showToast(getBaseContext(),"Data Not Exist");
                            }

                        }catch (Exception e){
                            Constants.showToast(getBaseContext(),"Error");
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                //perams are empty because this webservice not required any input daata
                Map<String, String> params = new HashMap<String, String>();
                params.put("pharmacist_id", DatabaseHelper.getInstance(getBaseContext()).getPharmacist().get(0).getId());
                return params;
            }
        };
        queue.add(postRequest);
    }



}
