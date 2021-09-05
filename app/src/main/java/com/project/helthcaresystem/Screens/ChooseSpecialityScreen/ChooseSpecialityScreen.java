package com.project.helthcaresystem.Screens.ChooseSpecialityScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.project.helthcaresystem.Constants;
import com.project.helthcaresystem.DatabaseHelper.DatabaseHelper;
import com.project.helthcaresystem.Models.Doctor;
import com.project.helthcaresystem.Models.DoctorCategory;
import com.project.helthcaresystem.R;
import com.project.helthcaresystem.Screens.DoctorScreens.DoctorMainScreen;
import com.project.helthcaresystem.Screens.DoctorScreens.DoctorRegistrationScreen;
import com.project.helthcaresystem.Screens.Main.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChooseSpecialityScreen extends AppCompatActivity {

    /**
     * Below initialize variables
     */
    private EditText mCategoryEditText;
    private EditText mSpecialityEditText;
    private Button mSaveButton;
    private ProgressBar mProgressBar;

    private String mCategory;
    private String mSpeciality;
    private RequestQueue queue;

    private ArrayList<Doctor> doctors;
    private String doctorID;
    private String doctorEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_speciality_screen);

        getSupportActionBar().setTitle("Choose Speciality");

        bindViews();
        initialize();
    }

    /**
     * Below views are attached
     */
    private void bindViews(){
        mCategoryEditText  = findViewById(R.id.category_edit_text_choose_speciality);
        mSpecialityEditText  = findViewById(R.id.speciality_edit_text_choose_speciality);
        mSaveButton  = findViewById(R.id.save_button_choose_speciality);
        mProgressBar  = findViewById(R.id.progress_bar_choose_speciality);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    private void initialize(){
        queue = Volley.newRequestQueue(this); //voley requst created here

        doctors = DatabaseHelper.getInstance(getBaseContext()).getDoctor();
        doctorID = doctors.get(0).getId();
        doctorEmail = doctors.get(0).getEmail();

        if(DatabaseHelper.getInstance(getBaseContext()).checkIfDoctorCategoryExist()){ // here I am check if doctor category exist don't do any thing if not exist if exist display it
            ArrayList<DoctorCategory>  doctorCategory= DatabaseHelper.getInstance(getBaseContext()).getDoctorCategory();

            //Log.d(Constants.DEBUG_TAG,"---email--->"+doctorCategory.get(0).getmCategory());

            mCategoryEditText.setText(doctorCategory.get(0).getCategory()); //data exist show it on text field on speciality screen
            mSpecialityEditText.setText(doctorCategory.get(0).getSpeciality());//data exist show it on text field on speciality screen

        }else{

        }


        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCategory = mCategoryEditText.getText().toString().trim();
                mSpeciality = mSpecialityEditText.getText().toString().trim();

                if(validateInputs(mCategory,mSpeciality)){ //validating textfields
                    mProgressBar.setVisibility(View.VISIBLE);
                    addOrUpdateSpeciality(Constants.DOCTOR_ADD_CATEGORY_URL,doctorID,doctorEmail,mCategory,mSpeciality);// updating data to the server

                }

            }
        });
    }


    @Override
    public void onBackPressed() { //when user press back button
        super.onBackPressed();

        Intent intent = new Intent( ChooseSpecialityScreen.this, DoctorMainScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    private boolean validateInputs(String category, String speciality){

        if(category.isEmpty()){
            mCategoryEditText.setError("category is required");
            mCategoryEditText.requestFocus();
            return false;
        }

        if(speciality.isEmpty()){
            mSpecialityEditText.setError("speciality is required");
            mSpecialityEditText.requestFocus();
            return false;
        }



        return true;
    }


    public void addOrUpdateSpeciality(String url,final String id, final String email, final String category,final String speciality) {

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d(Constants.DEBUG_TAG, response);

                      Gson gson= new Gson();

                        DoctorCategory doctorCategory =  gson.fromJson(response.toString(),DoctorCategory.class);//converting response to json to pojo

                        /*
                        * below filtring the doctors respons messages
                         */

                        if(doctorCategory.getMessage() != null && doctorCategory.getMessage().equals("Update Successful")){

                             mProgressBar.setVisibility(View.INVISIBLE);
                             DatabaseHelper.getInstance(getBaseContext()).deleteAllDoctorCategories(); //deleting previous entries on local db

                            Log.d(Constants.DEBUG_TAG,"---update Successful called--->"+category);

                            //adding updated categories here
                             DatabaseHelper.getInstance(getBaseContext()).addDoctorCategory(new DoctorCategory(id,email,category,speciality));
                             Toast.makeText(getBaseContext(),doctorCategory.getMessage(),Toast.LENGTH_LONG).show();

                        }else if(doctorCategory.getMessage() != null && doctorCategory.getMessage().equals("Doctor Category Added successfully")) {

                            mProgressBar.setVisibility(View.INVISIBLE);
                            DatabaseHelper.getInstance(getBaseContext()).deleteAllDoctorCategories();
                            //adding updated categories here
                            DatabaseHelper.getInstance(getBaseContext()).addDoctorCategory(new DoctorCategory(id,email,category,speciality));
                            Toast.makeText(getBaseContext(),doctorCategory.getMessage(),Toast.LENGTH_LONG).show();

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

                //below we are puting parameters of webservice
                Map<String, String> params = new HashMap<String, String>();
                params.put("did", id);
                params.put("email", email);
                params.put("category", category);
                params.put("speciality", speciality);

                return params;
            }
        };
        queue.add(postRequest); //adding request to the que and initiate
    }
}
