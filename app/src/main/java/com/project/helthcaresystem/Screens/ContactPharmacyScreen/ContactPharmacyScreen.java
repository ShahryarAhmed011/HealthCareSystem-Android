package com.project.helthcaresystem.Screens.ContactPharmacyScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

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
import com.project.helthcaresystem.Models.Doctor;
import com.project.helthcaresystem.Models.PatientReport;
import com.project.helthcaresystem.Models.Pharmacist;
import com.project.helthcaresystem.R;
import com.project.helthcaresystem.Screens.PatientViewRecordScreen.ViewRecordAdapter;
import com.project.helthcaresystem.ViewComponent.PharmacistDialogAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ContactPharmacyScreen extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private ContactPharmacyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar mProgressBar;
    private RequestQueue queue;
    ArrayList<Pharmacist> pharmacistsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_pharmacy_screen);

        bindViews();
        initialize();
    }

    //below method functionality or comment on line is same as choose speciality screen
    private void bindViews(){
        mRecyclerView = findViewById(R.id.contact_pharmacy_screen_recycler_view);
        mProgressBar = findViewById(R.id.contact_pharmacy_screen_progressBar);
    }

    //below method functionality or comment on line is same as choose speciality screen
    private void initialize() {
        queue = Volley.newRequestQueue(this);
        pharmacistsList = new ArrayList<>();
        mProgressBar.setVisibility(View.VISIBLE);
        getAllPharmacies(Constants.GET_ALL_PHARMACIST_URL);

    }

    private void initRecyclerView(ArrayList<Pharmacist> pharmacists) {

        mLayoutManager = new LinearLayoutManager(getBaseContext()); //creating recyclerView layout
        mRecyclerView.setLayoutManager(mLayoutManager); // adding layout in recycler view
        mAdapter = new ContactPharmacyAdapter(this, pharmacists); //creating adapter or calling it
        mRecyclerView.setAdapter(mAdapter); //adding adapter to recycler view

        mProgressBar.setVisibility(View.GONE);
    }



    public void getAllPharmacies(String url){

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Gson gson= new Gson();
                        try {
                            JsonObject jsonObject = gson.fromJson(response.toString(), JsonObject.class);//here converting response to json objec
                            JsonArray jsonArray = gson.fromJson(jsonObject.get("message"), JsonArray.class); //here converting response to json object to json array because we are receiving array

                            if(jsonArray.size()>=0){
                                //below simply populating the array
                                for(int i = 0;i<jsonArray.size();i++){
                                   /* String id = String.valueOf(jsonArray.get(i).getAsJsonObject().get("id")).replace("\"", "");;
                                    String name = String.valueOf(jsonArray.get(i).getAsJsonObject().get("user_name")).replace("\"", "");;
*/
                                   Pharmacist pharmacist = gson.fromJson(jsonArray.get(i),Pharmacist.class);//converting respons to pjo

                                   pharmacistsList.add(pharmacist);
                                }
                                initRecyclerView(pharmacistsList);//after getting respons initializing recyvler list view if data exist it will initialize other wise it will not call
                            }else{
                                //some error
                            }

                            String message  = String.valueOf(jsonObject.get("message")).replace("\"", "");;//json respons msg converting json object response to string
                            Log.d(Constants.DEBUG_TAG, message);

                        }catch (Exception e){
                            Constants.showToast(getBaseContext(),"Pharmacy Not Exist");
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
                return params;
            }
        };
        queue.add(postRequest);
    }


  }
