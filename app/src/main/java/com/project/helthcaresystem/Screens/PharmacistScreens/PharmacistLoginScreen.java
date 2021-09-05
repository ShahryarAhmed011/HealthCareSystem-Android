package com.project.helthcaresystem.Screens.PharmacistScreens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.project.helthcaresystem.Constants;
import com.project.helthcaresystem.DatabaseHelper.DatabaseHelper;
import com.project.helthcaresystem.Models.Doctor;
import com.project.helthcaresystem.Models.Pharmacist;
import com.project.helthcaresystem.R;
import com.project.helthcaresystem.Screens.DoctorScreens.DoctorLoginScreen;
import com.project.helthcaresystem.Screens.DoctorScreens.DoctorMainScreen;
import com.project.helthcaresystem.Screens.DoctorScreens.DoctorRegistrationScreen;
import com.project.helthcaresystem.Screens.Main.MainActivity;
import com.project.helthcaresystem.Screens.PatientScreens.PatientLoginScreen;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PharmacistLoginScreen extends AppCompatActivity {


    //working is same as login main screen

    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private TextView mCreateAccountTextView;
    private Button mLoginButton;
    private ProgressBar mProgressBar;
    private String email;
    private String password;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacist_login_screen);

        bindViews();
        initialize();
    }

    private void bindViews(){

        mProgressBar = findViewById(R.id.login_progressBar_pharmacist_login);
        mProgressBar.setVisibility(View.INVISIBLE);

        mEmailEditText = findViewById(R.id.email_edit_text_pharmacist_login);
        mPasswordEditText = findViewById(R.id.password_edit_text_pharmacist_login);
        mCreateAccountTextView = findViewById(R.id.create_account_edit_text_pharmacist_login);
        mLoginButton = findViewById(R.id.login_edit_text_pharmacist_login);

    }

    private void initialize(){
        queue = Volley.newRequestQueue(this);
        mCreateAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PharmacistLoginScreen.this, PharmacistRegistrationScreen.class);
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
                    doctorLoginRequest(Constants.PHARMACIST_LOGIN_URL, email, password);

                }else{

                }

            }
        });

    }

    @Override
    public void onBackPressed() { //when user press back button
        super.onBackPressed();

        Intent intent = new Intent(PharmacistLoginScreen.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

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

    private void doctorLoginRequest(String url, final String email, final String password){


        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

                        Gson gson= new Gson();
                        Pharmacist pharmacist =  gson.fromJson(response.toString(),Pharmacist.class);

                        JsonObject jsonObject =  gson.fromJson(response.toString(),JsonObject.class);
                        Pharmacist pharmacistObj =  gson.fromJson(jsonObject.get("Pharmacist"),Pharmacist.class);

                        if(pharmacist.getMessage() != null && pharmacist.getMessage().equals("Required fields are missing")){

                            mProgressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getBaseContext(),pharmacist.getMessage(),Toast.LENGTH_LONG).show();


                        }else if(pharmacist.getMessage() != null && pharmacist.getMessage().equals("Invalid username or password")){

                            mProgressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getBaseContext(),pharmacist.getMessage(),Toast.LENGTH_LONG).show();

                        }else if(pharmacist.getError().equals("false")){

                            Toast.makeText(getBaseContext(),"Login Successful",Toast.LENGTH_LONG).show();
                            mProgressBar.setVisibility(View.INVISIBLE);
                            DatabaseHelper.getInstance(getBaseContext()).addPharmacist(pharmacistObj);
                            Intent intent = new Intent(PharmacistLoginScreen.this, PharmacistMainScreen.class);
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


}
