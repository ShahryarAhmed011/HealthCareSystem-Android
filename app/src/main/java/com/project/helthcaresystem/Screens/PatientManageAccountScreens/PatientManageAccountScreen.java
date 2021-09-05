package com.project.helthcaresystem.Screens.PatientManageAccountScreens;

import androidx.appcompat.app.AppCompatActivity;

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
import com.project.helthcaresystem.Models.Patient;
import com.project.helthcaresystem.R;
import com.project.helthcaresystem.ViewComponent.ManageAccountDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatientManageAccountScreen extends AppCompatActivity {


    private EditText mUserNameEditText;
    private EditText mPhoneNumberEditText;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private EditText mAddressEditText;
    private EditText mDateOfBirthEditText;
    private EditText mGenderEditText;
    private Button mUpdateButton;
    private ManageAccountDialog manageAccountDialog;
    private RequestQueue queue;
    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_manage_account_screen);

        getSupportActionBar().setTitle("Manage Account");

        bindViews();
        initialize();
    }

    private void bindViews(){

        mUserNameEditText = findViewById(R.id.user_name_edit_text_patient_manage_account_screen);
        mPhoneNumberEditText = findViewById(R.id.phone_number_edit_text_patient_manage_account_screen);
        mEmailEditText = findViewById(R.id.email_edit_text_patient_manage_account_screen);
        mPasswordEditText = findViewById(R.id.password_edit_text_patient_manage_account_screen);
        mAddressEditText = findViewById(R.id.address_edit_text_patient_manage_account_screen);
        mDateOfBirthEditText = findViewById(R.id.date_of_birth_edit_text_patient_manage_account_screen);
        mGenderEditText = findViewById(R.id.gender_edit_text_patient_manage_account_screen);
        mUpdateButton = findViewById(R.id.update_button_patient_manage_account_screen);

        mProgressBar = findViewById(R.id.progressBar_patient_manage_account_screen);
        mProgressBar.setVisibility(View.INVISIBLE);

    }

    private void initialize(){

        queue = Volley.newRequestQueue(this);

        manageAccountDialog = new ManageAccountDialog(this);

        ArrayList<Patient> patients = DatabaseHelper.getInstance(getBaseContext()).getPatient();

        mUserNameEditText.setText(patients.get(0).getUser_name());
        mPhoneNumberEditText.setText(patients.get(0).getPhone_number());
        mEmailEditText.setText(patients.get(0).getEmail());
        mPasswordEditText.setText(patients.get(0).getPassword());
        mAddressEditText.setText(patients.get(0).getAddress());
        mDateOfBirthEditText.setText(patients.get(0).getDate_of_birth());
        mGenderEditText.setText(patients.get(0).getGender());


        mUserNameEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                manageAccountDialog.show("UserName",new ManageAccountDialog.OnUpdateButtonClick() {
                    @Override
                    public void onSuccess(String string) {
                        //  Toast.makeText(getBaseContext(),string,Toast.LENGTH_LONG).show();
                        mUserNameEditText.setText(string);
                    }

                    @Override
                    public void onFailure() {
                        //  Toast.makeText(getBaseContext(),"CancelButton Pressed",Toast.LENGTH_LONG).show();
                    }
                });

            }

        });

        mPhoneNumberEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                manageAccountDialog.show("PhoneNumber",new ManageAccountDialog.OnUpdateButtonClick() {
                    @Override
                    public void onSuccess(String string) {
                        mPhoneNumberEditText.setText(string);
                        // Toast.makeText(getBaseContext(),string,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure() {
                        //  Toast.makeText(getBaseContext(),"CancelButton Pressed",Toast.LENGTH_LONG).show();
                    }
                });


            }
        });

        mEmailEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                manageAccountDialog.show("Email",new ManageAccountDialog.OnUpdateButtonClick() {
                    @Override
                    public void onSuccess(String string) {
                        mEmailEditText.setText(string);
                    }
                    @Override
                    public void onFailure() {
                        //  Toast.makeText(getBaseContext(),"CancelButton Pressed",Toast.LENGTH_LONG).show();
                    }
                });


            }
        });

        mPasswordEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                manageAccountDialog.show("Password",new ManageAccountDialog.OnUpdateButtonClick() {
                    @Override
                    public void onSuccess(String string) {
                        mPasswordEditText.setText(string);
                    }
                    @Override
                    public void onFailure() {
                        //  Toast.makeText(getBaseContext(),"CancelButton Pressed",Toast.LENGTH_LONG).show();
                    }
                });


            }
        });

        mAddressEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageAccountDialog.show("Address",new ManageAccountDialog.OnUpdateButtonClick() {
                    @Override
                    public void onSuccess(String string) {
                        mAddressEditText.setText(string);
                        //Toast.makeText(getBaseContext(),string,Toast.LENGTH_LONG).show();
                        }
                    @Override
                    public void onFailure() {
                        //  Toast.makeText(getBaseContext(),"CancelButton Pressed",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        mDateOfBirthEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageAccountDialog.show("DateOfBirth",new ManageAccountDialog.OnUpdateButtonClick() {
                    @Override
                    public void onSuccess(String string) {
                        mDateOfBirthEditText.setText(string);
                        //  Toast.makeText(getBaseContext(),string,Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onFailure() {
                        //  Toast.makeText(getBaseContext(),"CancelButton Pressed",Toast.LENGTH_LONG).show();
                    }
                });


            }
        });

        mGenderEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageAccountDialog.show("Gender",new ManageAccountDialog.OnUpdateButtonClick() {
                    @Override
                    public void onSuccess(String string) {
                        mGenderEditText.setText(string);
                        //   Toast.makeText(getBaseContext(),string,Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onFailure() {
                        //  Toast.makeText(getBaseContext(),"CancelButton Pressed",Toast.LENGTH_LONG).show();
                    }

                });
            }
        });


        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = mUserNameEditText.getText().toString().trim();
                String phone = mPhoneNumberEditText.getText().toString().trim();
                String email = mEmailEditText.getText().toString().trim();
                String password = mPasswordEditText.getText().toString().trim();
                String address = mAddressEditText.getText().toString().trim();
                String gender = mGenderEditText.getText().toString().trim();
                String dateOfBirth = mDateOfBirthEditText.getText().toString().trim();

                if(validateInputs(email)){
                    mProgressBar.setVisibility(View.VISIBLE);
                    updatePatientRequest(Constants.PATIENT_UPDATE_URL,DatabaseHelper.getInstance(getBaseContext()).getPatient().get(0).getId(),
                            userName,email,password,phone, address,gender,dateOfBirth);
                }else{
                }
            }
        });

    }
    //Below is the process repeating method every time to putting params getting Json rquest and converting int Pojo classes Strings or JsonArrays
    public void updatePatientRequest(String url,final String patientID,final String userName, final String email,
                                    final String password,final String phoneNumber, final String address,final String gender,
                                    final String dateOfBirth) {

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);

                        Gson gson= new Gson();

                        Patient patient =  gson.fromJson(response.toString(),Patient.class);
                        mProgressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getBaseContext(),patient.getMessage(),Toast.LENGTH_LONG).show();

                        Patient patient1= new Patient();
                        patient1.setId(patientID);
                        patient1.setUser_name(userName);
                        patient1.setPhone_number(phoneNumber);
                        patient1.setEmail(email);
                        patient1.setPassword(password);
                        patient1.setAddress(address);
                        patient1.setDate_of_birth(dateOfBirth);
                        patient1.setGender(gender);

                        DatabaseHelper.getInstance(getBaseContext()).deleteAllPatients();
                        DatabaseHelper.getInstance(getBaseContext()).addPatient(patient1);


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
                params.put("phone_number", phoneNumber);
                params.put("address", address);
                params.put("gender", gender);
                params.put("date_of_birth", dateOfBirth);
                return params;
            }
        };
        queue.add(postRequest);
    }

    private boolean validateInputs(String email){
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
        return true;
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}