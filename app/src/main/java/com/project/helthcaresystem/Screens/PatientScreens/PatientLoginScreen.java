package com.project.helthcaresystem.Screens.PatientScreens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.project.helthcaresystem.Constants;
import com.project.helthcaresystem.DatabaseHelper.DatabaseHelper;
import com.project.helthcaresystem.Models.Patient;
import com.project.helthcaresystem.R;
import com.project.helthcaresystem.Screens.DoctorScreens.DoctorLoginScreen;
import com.project.helthcaresystem.Screens.DoctorScreens.DoctorMainScreen;
import com.project.helthcaresystem.Screens.DoctorScreens.DoctorRegistrationScreen;
import com.project.helthcaresystem.Screens.Main.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPOutputStream;

public class PatientLoginScreen extends AppCompatActivity {


    //below is the same working as doctor login screen you can check comments their

    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private TextView mCreateAccountTextView;
    private Button mLoginButton;

    private ProgressBar mProgressBar;

    private RequestQueue queue;


    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_login_screen);

        bindViews();
        initialize();


    }

    private void bindViews(){

        mProgressBar = findViewById(R.id.login_progressBar_patient_login);
        mProgressBar.setVisibility(View.INVISIBLE);

        mEmailEditText = findViewById(R.id.email_edit_text_patient_login);
        mPasswordEditText = findViewById(R.id.password_edit_text_patient_login);
        mCreateAccountTextView = findViewById(R.id.create_account_edit_text_patient_login);
        mLoginButton = findViewById(R.id.login_edit_text_patient_login);

    }

    private void initialize(){

        queue = Volley.newRequestQueue(this);

        mCreateAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent = new Intent(PatientLoginScreen.this, PatientRegistrationScreen.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 email = mEmailEditText.getText().toString().trim();
                 password = mPasswordEditText.getText().toString().trim();

                if(validateInputs(email,password)) {

                    mProgressBar.setVisibility(View.VISIBLE);
                    patientLoginGetRequest(Constants.PATENT_LOGIN_URL, email, password);

                }else{

                }

            }
        });

    }

    @Override
    public void onBackPressed() { //when user press back button
        super.onBackPressed();

        Intent intent = new Intent(PatientLoginScreen.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }


    private void patientLoginGetRequest(String url, final String email, final String password){


        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Response", response.toString());
                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

                        Gson gson= new Gson();

                        Patient patient =  gson.fromJson(response.toString(),Patient.class);

                        if(patient.getMessage() != null && patient.getMessage().equals("Required fields are missing")){
                            mProgressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getBaseContext(),patient.getMessage(),Toast.LENGTH_LONG).show();
                            Log.d("Response", "error-"+patient.getError());
                            Log.d("Response", "message-"+patient.getMessage());

                        }else if(patient.getMessage() != null && patient.getMessage().equals("Invalid username or password")){

                            mProgressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getBaseContext(),patient.getMessage(),Toast.LENGTH_LONG).show();
                            Log.d("Response", "error-"+patient.getError());
                            Log.d("Response", "message-"+patient.getMessage());

                        }else if(patient.getError().equals("false")){

                            Toast.makeText(getBaseContext(),"Login Successful",Toast.LENGTH_LONG).show();
                            Log.d("Response", "message-"+patient.getPhone_number());
                            mProgressBar.setVisibility(View.INVISIBLE);

                            DatabaseHelper.getInstance(getBaseContext()).addPatient(patient);
                            Intent intent = new Intent(PatientLoginScreen.this, PatientMainScreen.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);


                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        queue.add(stringRequest);
    }


    private boolean validateInputs(String email, String password){

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
