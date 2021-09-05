package com.project.helthcaresystem.Screens.DoctorScreens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import com.project.helthcaresystem.Models.DoctorAppointment;
import com.project.helthcaresystem.Models.DoctorCategory;
import com.project.helthcaresystem.Models.Patient;
import com.project.helthcaresystem.R;
import com.project.helthcaresystem.Screens.PatientScreens.PatientMainScreen;
import com.project.helthcaresystem.Screens.PatientScreens.PatientRegistrationScreen;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DoctorRegistrationScreen extends AppCompatActivity {

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
        setContentView(R.layout.activity_doctor_registration_screen);

        bindViews();
        initialize();
    }

    //same repeating every time
    private void bindViews(){

        mProgressBar = findViewById(R.id.registration_progressBar_doctor_registration);
        mProgressBar.setVisibility(View.INVISIBLE);

        mUserNameEditText = findViewById(R.id.user_name_edit_text_doctort_registration);
        mEmailEditText = findViewById(R.id.email_edit_text_doctort_registration);
        mPasswordEditText = findViewById(R.id.password_edit_text_doctor_registration);
        mCreateAccountButton = findViewById(R.id.create_account_edit_text_doctor_registration);

    }


    //same repeating every time
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
                    registrationRequest(Constants.DOCTOR_REGISTRATION_URL, userName,email, password);

                }else{

                }

            }
        });

    }


    //Below is the process repeating method every time to putting params getting Json rquest and converting int Pojo classes Strings or JsonArrays

    public void registrationRequest(String url,final String userName, final String email, final String password) {

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);

                        Gson gson= new Gson();

                        Doctor doctor =  gson.fromJson(response.toString(),Doctor.class);

                        if(doctor.getMessage() != null && doctor.getMessage().equals("Required fields are missing")){
                            mProgressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getBaseContext(),doctor.getMessage(),Toast.LENGTH_LONG).show();

                        }else if(doctor.getMessage() != null && doctor.getMessage().equals("It seems you are already registered, please choose a different email and username")){

                            mProgressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getBaseContext(),doctor.getMessage(),Toast.LENGTH_LONG).show();


                        }else if(doctor.getMessage() != null && doctor.getMessage().equals("Doctor registered successfully")) {

                            mProgressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getBaseContext(),doctor.getMessage(),Toast.LENGTH_LONG).show();

                            DatabaseHelper.getInstance(getBaseContext()).addDoctor(new Doctor(doctor.getId(),userName,email,password,"Click To Add","Click To Add","Click To Add","Click To Add"));
                            DatabaseHelper.getInstance(getBaseContext()).addDoctorCategory(new DoctorCategory(doctor.getId(),email,"Click To Add","Click To Add"));

                            DatabaseHelper.getInstance(getBaseContext()).addDoctorAppointment(new DoctorAppointment(doctor.getId(),"12:00PM To 04:00PM","Mon-00-00-0000","Monday","Not Available"));
                            DatabaseHelper.getInstance(getBaseContext()).addDoctorAppointment(new DoctorAppointment(doctor.getId(),"12:00PM To 04:00PM","Tue-00-00-0000","Tuesday","Not Available"));
                            DatabaseHelper.getInstance(getBaseContext()).addDoctorAppointment(new DoctorAppointment(doctor.getId(),"12:00PM To 04:00PM","Wed-00-00-0000","Wednesday","Not Available"));
                            DatabaseHelper.getInstance(getBaseContext()).addDoctorAppointment(new DoctorAppointment(doctor.getId(),"12:00PM To 04:00PM","Thur-00-00-0000","Thursday","Not Available"));
                            DatabaseHelper.getInstance(getBaseContext()).addDoctorAppointment(new DoctorAppointment(doctor.getId(),"12:00PM To 04:00PM","Fri-00-00-0000","Friday","Not Available"));
                            DatabaseHelper.getInstance(getBaseContext()).addDoctorAppointment(new DoctorAppointment(doctor.getId(),"12:00PM To 04:00PM","Sat-00-00-0000","Saturday","Not Available"));
                            DatabaseHelper.getInstance(getBaseContext()).addDoctorAppointment(new DoctorAppointment(doctor.getId(),"12:00PM To 04:00PM","Sun-00-00-0000","Sunday","Not Available"));

                            Intent intent = new Intent(DoctorRegistrationScreen.this, DoctorMainScreen.class);
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
                params.put("phone_number", "Click To Add");
                params.put("address", "Click To Add");
                params.put("gender", "Click To Add");
                params.put("date_of_birth", "Click To Add");

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


    //below checking if email string is valid or not
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }



}
