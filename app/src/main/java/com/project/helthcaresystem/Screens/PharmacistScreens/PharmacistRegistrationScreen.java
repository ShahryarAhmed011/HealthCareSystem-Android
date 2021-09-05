package com.project.helthcaresystem.Screens.PharmacistScreens;

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
import com.google.gson.JsonObject;
import com.project.helthcaresystem.Constants;
import com.project.helthcaresystem.DatabaseHelper.DatabaseHelper;
import com.project.helthcaresystem.Models.Doctor;
import com.project.helthcaresystem.Models.Patient;
import com.project.helthcaresystem.Models.Pharmacist;
import com.project.helthcaresystem.R;
import com.project.helthcaresystem.Screens.DoctorScreens.DoctorMainScreen;
import com.project.helthcaresystem.Screens.PatientScreens.PatientMainScreen;
import com.project.helthcaresystem.Screens.PatientScreens.PatientRegistrationScreen;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PharmacistRegistrationScreen extends AppCompatActivity {

    //working is same as doctor registration screen

    private EditText mUserNameEditText;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Button mCreateAccountButton;
    private ProgressBar mProgressBar;

    private RequestQueue queue;

    private String userName;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacist_registration_screen);

        bindViews();
        initialize();
    }

    private void bindViews(){

        mProgressBar = findViewById(R.id.registration_progressBar_pharmacist_registration);
        mProgressBar.setVisibility(View.INVISIBLE);

        mUserNameEditText = findViewById(R.id.user_name_edit_text_pharmacist_registration);
        mEmailEditText = findViewById(R.id.email_edit_text_pharmacist_registration);
        mPasswordEditText = findViewById(R.id.password_edit_text_pharmacist_registration);
        mCreateAccountButton = findViewById(R.id.create_account_edit_text_pharmacist_registration);

    }

    private void initialize(){

        queue = Volley.newRequestQueue(this);

        mCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userName = mUserNameEditText.getText().toString().trim();
                email = mEmailEditText.getText().toString().trim();
                password = mPasswordEditText.getText().toString().trim();

                if(validateInputs(userName,email,password)) {

                    mProgressBar.setVisibility(View.VISIBLE);
                    registrationRequest(Constants.PHARMACIST_REGISTRATION_URL, userName,email, password);

                }else{

                }

            }
        });

    }

    public void registrationRequest(String url,final String userName, final String email, final String password) {

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);

                        Gson gson= new Gson();

                        Pharmacist pharmacist =  gson.fromJson(response.toString(),Pharmacist.class);
                        JsonObject jsonObject =  gson.fromJson(response.toString(),JsonObject.class);
                        Pharmacist pharmacistObj =  gson.fromJson(jsonObject.get("Pharmacist"),Pharmacist.class);
                        if(pharmacist.getMessage() != null && pharmacist.getMessage().equals("Required fields are missing")){
                            mProgressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getBaseContext(),pharmacist.getMessage(),Toast.LENGTH_LONG).show();

                        }else if(pharmacist.getMessage() != null && pharmacist.getMessage().equals("It seems you are already registered, please choose a different email and username")){

                            mProgressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getBaseContext(),pharmacist.getMessage(),Toast.LENGTH_LONG).show();


                        }else if(pharmacist.getMessage() != null && pharmacist.getMessage().equals("Pharmacist registered successfully")) {

                            mProgressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getBaseContext(),pharmacist.getMessage(),Toast.LENGTH_LONG).show();
                            DatabaseHelper.getInstance(getBaseContext()).addPharmacist(new Pharmacist(pharmacistObj.getId(),userName,email,password
                            ,"Click To Add","Click To Add","Click To Add","Click To Add"));


                            Intent intent = new Intent(PharmacistRegistrationScreen.this, PharmacistMainScreen.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

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
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_name", userName);
                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };
        queue.add(postRequest);
    }


    private boolean validateInputs(String userName,String email, String password){

        if(userName.isEmpty()){
            mUserNameEditText.setError("user name is required");
            mUserNameEditText.requestFocus();
            return false;
        }

        if(email.isEmpty()){
            mEmailEditText.setError("email is required");
            mEmailEditText.requestFocus();
            return false;
        }

        if (isEmailValid(email)){
            // mEmailEditText.setError("email is required");
        }else{
            mEmailEditText.setError("enter valid email");
            return false;
        }

        if(password.isEmpty()){
            mPasswordEditText.setError("password is required");
            mPasswordEditText.requestFocus();
            return false;
        }



        return true;
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
