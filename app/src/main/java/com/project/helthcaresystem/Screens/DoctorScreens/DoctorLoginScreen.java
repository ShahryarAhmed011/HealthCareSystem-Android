package com.project.helthcaresystem.Screens.DoctorScreens;

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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.project.helthcaresystem.Constants;
import com.project.helthcaresystem.DatabaseHelper.DatabaseHelper;
import com.project.helthcaresystem.Models.Doctor;
import com.project.helthcaresystem.Models.DoctorAppointment;
import com.project.helthcaresystem.Models.DoctorCategory;
import com.project.helthcaresystem.Models.Patient;
import com.project.helthcaresystem.Models.PatientAppointment;
import com.project.helthcaresystem.Models.PatientReport;
import com.project.helthcaresystem.R;
import com.project.helthcaresystem.Screens.Main.MainActivity;
import com.project.helthcaresystem.Screens.PatientScreens.PatientLoginScreen;
import com.project.helthcaresystem.Screens.PatientScreens.PatientMainScreen;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DoctorLoginScreen extends AppCompatActivity {

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
        setContentView(R.layout.activity_doctor_login_screen);

        bindViews();
        initialize();
    }

    private void bindViews(){

        mEmailEditText = findViewById(R.id.email_edit_text_doctor_login);
        mPasswordEditText = findViewById(R.id.password_edit_text_doctor_login);
        mCreateAccountTextView = findViewById(R.id.create_account_edit_text_doctor_login);
        mLoginButton = findViewById(R.id.login_edit_text_doctor_login);
        mProgressBar = findViewById(R.id.login_progressBar_doctor_login);
        mProgressBar.setVisibility(View.INVISIBLE);

    }

    private void initialize(){

        queue = Volley.newRequestQueue(this);

        mCreateAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DoctorLoginScreen.this, DoctorRegistrationScreen.class);
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
                    doctorLoginRequest(Constants.DOCTOR_LOGIN_URL, email, password);

                }else{

                }

            }
        });

    }


    @Override
    public void onBackPressed() { //when user press back button
        super.onBackPressed();

        Intent intent = new Intent(DoctorLoginScreen.this, MainActivity.class);
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

    //Below is the process repeating method every time to putting params getting Json rquest and converting int Pojo classes Strings or JsonArrays
    private void doctorLoginRequest(String url, final String email, final String password){


        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

                        Gson gson= new Gson();



                       try {
                            Doctor doctor = gson.fromJson(response.toString(), Doctor.class);

                            JsonObject jsonObject = gson.fromJson(response, JsonObject.class);

                            doctor.setId(String.valueOf(jsonObject.get("id")));//.replace("\"", ""));
                            doctor.setPhoneNumber(String.valueOf(jsonObject.get("phone_number")).replace("\"", ""));
                            doctor.setDateOfBirth(String.valueOf(jsonObject.get("date_of_birth")).replace("\"", ""));
                            String message = String.valueOf(jsonObject.get("message")).replace("\"", "");

                            JsonArray jsonObjectForAppointments = gson.fromJson(jsonObject.get("AppointmentsData"), JsonArray.class);

                            JsonObject monJsonObject = gson.fromJson(jsonObjectForAppointments.get(0), JsonObject.class);

                            String monDate = String.valueOf(monJsonObject.get("date")).replace("\"", "");
                            String monTime = String.valueOf(monJsonObject.get("time")).replace("\"", "");
                            String monAvailability = String.valueOf(monJsonObject.get("availablity")).replace("\"", "");



                            JsonObject tueJsonObject = gson.fromJson(jsonObjectForAppointments.get(1), JsonObject.class);
                            String tueDate = String.valueOf(tueJsonObject.get("date")).replace("\"", "");
                            String tueTime = String.valueOf(tueJsonObject.get("time")).replace("\"", "");
                            String tueAvailability = String.valueOf(tueJsonObject.get("availablity")).replace("\"", "");

                            JsonObject wedJsonObject = gson.fromJson(jsonObjectForAppointments.get(2), JsonObject.class);

                            String wedDate = String.valueOf(wedJsonObject.get("date")).replace("\"", "");
                            String wedTime = String.valueOf(wedJsonObject.get("time")).replace("\"", "");
                            String wedAvailability = String.valueOf(wedJsonObject.get("availablity")).replace("\"", "");

                            JsonObject thurJsonObject = gson.fromJson(jsonObjectForAppointments.get(3), JsonObject.class);
                            String thurDate = String.valueOf(thurJsonObject.get("date")).replace("\"", "");
                            String thurTime = String.valueOf(thurJsonObject.get("time")).replace("\"", "");
                            String thurAvailability = String.valueOf(thurJsonObject.get("availablity")).replace("\"", "");

                            JsonObject friJsonObject = gson.fromJson(jsonObjectForAppointments.get(4), JsonObject.class);
                            String friDate = String.valueOf(friJsonObject.get("date")).replace("\"", "");
                            String friTime = String.valueOf(friJsonObject.get("time")).replace("\"", "");
                            String friAvailability = String.valueOf(friJsonObject.get("availablity")).replace("\"", "");

                            JsonObject satJsonObject = gson.fromJson(jsonObjectForAppointments.get(5), JsonObject.class);
                            String satDate = String.valueOf(satJsonObject.get("date")).replace("\"", "");
                            String satTime = String.valueOf(satJsonObject.get("time")).replace("\"", "");
                            String satAvailability = String.valueOf(satJsonObject.get("availablity")).replace("\"", "");

                            JsonObject sunJsonObject = gson.fromJson(jsonObjectForAppointments.get(6), JsonObject.class);
                            String sunDate = String.valueOf(sunJsonObject.get("date")).replace("\"", "");
                            String sunTime = String.valueOf(sunJsonObject.get("time")).replace("\"", "");
                            String sunAvailability = String.valueOf(sunJsonObject.get("availablity")).replace("\"", "");


//                           Constants.printLog("Dates"+monDate+tueDate+thurDate+friDate+satDate+sunDate);

                        JsonArray jsonObjectForPatientAppointments = gson.fromJson(jsonObject.get("PatientAppointmentsData"),JsonArray.class);

                        Constants.printLog("Patients Appointment  ",String.valueOf(jsonObjectForPatientAppointments));

                        String patientID = "";
                        String doctorID = "";
                        String patientName = "";
                        String time = "";
                        String appointmentDate = "";
                        String day = "-";

                           Constants.printLog("patient Appointment",jsonObjectForPatientAppointments.toString());

                        if(jsonObjectForPatientAppointments.size()>=0){

                            for(int i = 0;i<jsonObjectForPatientAppointments.size();i++){

                                patientID = String.valueOf(jsonObjectForPatientAppointments.get(i).getAsJsonObject().get("pid")).replace("\"", "");;
                                doctorID = String.valueOf(jsonObjectForPatientAppointments.get(i).getAsJsonObject().get("did")).replace("\"", "");;
                                patientName = String.valueOf(jsonObjectForPatientAppointments.get(i).getAsJsonObject().get("patientName")).replace("\"", "");;
                                time = String.valueOf(jsonObjectForPatientAppointments.get(i).getAsJsonObject().get("time")).replace("\"", "");;
                                day = String.valueOf(jsonObjectForPatientAppointments.get(i).getAsJsonObject().get("appointmentDate")).replace("\"", "");;

                         //       Constants.printLog("patient id-->"+patientID);

                                PatientAppointment patientAppointment = new PatientAppointment();
                                patientAppointment.setmPatientId(patientID);
                                patientAppointment.setDid(doctorID);
                                patientAppointment.setmPatientName(patientName);
                                patientAppointment.setTime(time);
                                patientAppointment.setDay(day);

                                DatabaseHelper.getInstance(getBaseContext()).addPatientAppointment(patientAppointment);

                                for(int ii= 0;ii<DatabaseHelper.getInstance(getBaseContext()).getPatientAppointments().size();ii++){

                     //               Constants.printLog("Patient ID in login---"+DatabaseHelper.getInstance(getBaseContext()).getPatientAppointments().get(ii).getmPatientId());

                                }

                            }
                        }else{
                            Log.d(Constants.DEBUG_TAG,"Dont Do this-->");
                        }

                        JsonArray jsonObjectForPatientReports = gson.fromJson(jsonObject.get("PatientReportsData"),JsonArray.class);

                        String reportPatientID = "";
                        String reportDoctorID = "";
                        String reportPatientName = "";
                        String reportPrescription = "";
                        String reportDiagnosis = "";
                        String reportNote = "";
                        String reportDate = "";

                        if(jsonObjectForPatientReports.size()>=0){

                            for(int i = 0;i<jsonObjectForPatientReports.size();i++){

                                reportPatientID = String.valueOf(jsonObjectForPatientReports.get(i).getAsJsonObject().get("pid")).replace("\"", "");;
                                reportDoctorID = String.valueOf(jsonObjectForPatientReports.get(i).getAsJsonObject().get("did")).replace("\"", "");;
                                reportPatientName = String.valueOf(jsonObjectForPatientReports.get(i).getAsJsonObject().get("patientName")).replace("\"", "");;
                                reportPrescription = String.valueOf(jsonObjectForPatientReports.get(i).getAsJsonObject().get("prescription")).replace("\"", "");;
                                reportDiagnosis = String.valueOf(jsonObjectForPatientReports.get(i).getAsJsonObject().get("diagnosis")).replace("\"", "");;
                                reportNote = String.valueOf(jsonObjectForPatientReports.get(i).getAsJsonObject().get("note")).replace("\"", "");;
                                reportDate = String.valueOf(jsonObjectForPatientReports.get(i).getAsJsonObject().get("date")).replace("\"", "");;



                                DatabaseHelper.getInstance(getBaseContext()).addPatientReports(new PatientReport(reportPatientID,reportDoctorID,reportPatientName,
                                        reportPrescription,reportDiagnosis,reportNote,reportDate));
                            }
                        }else{
                            Log.d(Constants.DEBUG_TAG,"Dont Do this-->");
                        }

                    //    Log.d(Constants.DEBUG_TAG,"Dont Do this-->"+jsonObjectForPatientReports);


                        if(doctor.getMessage() != null && doctor.getMessage().equals("Required fields are missing")){

                            mProgressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getBaseContext(),doctor.getMessage(),Toast.LENGTH_LONG).show();


                        }else if(doctor.getMessage() != null && doctor.getMessage().equals("Invalid username or password")){

                            mProgressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getBaseContext(),doctor.getMessage(),Toast.LENGTH_LONG).show();

                        }else if(doctor.getError().equals("false")){


                            Toast.makeText(getBaseContext(),"Login Successful",Toast.LENGTH_LONG).show();



                            if( message.equals("Doctor Category Data Exist")){
                                mProgressBar.setVisibility(View.INVISIBLE);
                                DatabaseHelper.getInstance(getBaseContext()).addDoctor(doctor);

                                DatabaseHelper.getInstance(getBaseContext()).addDoctorCategory(new DoctorCategory(doctor.getId(),doctor.getEmail(),
                                        String.valueOf(jsonObject.get("category")).replace("\"", ""),String.valueOf(jsonObject.get("speciality")).replace("\"", "")));

                                saveDoctorAppointmentDataToSQLite(doctor, monDate,tueDate,wedDate,thurDate,friDate,satDate,sunDate,monTime, monAvailability, tueTime, tueAvailability, wedTime, wedAvailability, thurTime, thurAvailability, friTime, friAvailability, satTime, satAvailability, sunTime, sunAvailability);


                                Intent intent = new Intent(DoctorLoginScreen.this, DoctorMainScreen.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            }else if(message.equals("Doctor Category Data Not Exist")){
                                DatabaseHelper.getInstance(getBaseContext()).addDoctor(doctor);

                                saveDoctorAppointmentDataToSQLite(doctor, monDate,tueDate,wedDate,thurDate,friDate,satDate,sunDate,monTime, monAvailability, tueTime, tueAvailability, wedTime, wedAvailability, thurTime, thurAvailability, friTime, friAvailability, satTime, satAvailability, sunTime, sunAvailability);

                                Intent intent = new Intent(DoctorLoginScreen.this, DoctorMainScreen.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }



                        }

                        }catch (Exception e){
                            mProgressBar.setVisibility(View.GONE);
                            Toast.makeText(getBaseContext(),"email or password is wrong",Toast.LENGTH_LONG).show();
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

    //below is saving response to database
    private void saveDoctorAppointmentDataToSQLite(Doctor doctor, String monDate,String tueDate,String wedDate,String thurDate ,String friDate,String satDate,String sunDate ,
                                                   String monTime, String monAvailability, String tueTime, String tueAvailability,
                                                   String wedTime, String wedAvailability, String thurTime, String thurAvailability,
                                                   String friTime, String friAvailability, String satTime, String satAvailability,
                                                   String sunTime, String sunAvailability) {

       // Constants.printLog("time"+monDate+monTime);
       // Constants.printLog("time"+tueDate+tueTime);

        DatabaseHelper.getInstance(getBaseContext()).deleteAllDoctorAppointments();

        DoctorAppointment monAppointment=new DoctorAppointment();
        monAppointment.setDid(doctor.getId());
        monAppointment.setTime(monTime);
        monAppointment.setDate(monDate);
        monAppointment.setDay("Monday");
        monAppointment.setAvailablity(monAvailability);
        DatabaseHelper.getInstance(getBaseContext()).addDoctorAppointment(monAppointment);

        DoctorAppointment tueAppointment=new DoctorAppointment();
        tueAppointment.setDid(doctor.getId());
        tueAppointment.setTime(tueTime);
        tueAppointment.setDate(tueDate);
        tueAppointment.setDay("Tuesday");
        tueAppointment.setAvailablity(tueAvailability);
        DatabaseHelper.getInstance(getBaseContext()).addDoctorAppointment(tueAppointment);

        DoctorAppointment wedAppointment=new DoctorAppointment();
        wedAppointment.setDid(doctor.getId());
        wedAppointment.setTime(wedTime);
        wedAppointment.setDate(wedDate);
        wedAppointment.setDay("Wednesday");
        wedAppointment.setAvailablity(wedAvailability);
        DatabaseHelper.getInstance(getBaseContext()).addDoctorAppointment(wedAppointment);

        DoctorAppointment thurAppointment=new DoctorAppointment();
        thurAppointment.setDid(doctor.getId());
        thurAppointment.setTime(thurTime);
        thurAppointment.setDate(thurDate);
        thurAppointment.setDay("Thursday");
        thurAppointment.setAvailablity(thurAvailability);
        DatabaseHelper.getInstance(getBaseContext()).addDoctorAppointment(thurAppointment);



        DoctorAppointment friAppointment=new DoctorAppointment();
        friAppointment.setDid(doctor.getId());
        friAppointment.setTime(friTime);
        friAppointment.setDate(friDate);
        friAppointment.setDay("Friday");
        friAppointment.setAvailablity(friAvailability);
        DatabaseHelper.getInstance(getBaseContext()).addDoctorAppointment(friAppointment);




        DoctorAppointment satAppointment=new DoctorAppointment();
        satAppointment.setDid(doctor.getId());
        satAppointment.setTime(satTime);
        satAppointment.setDate(satDate);
        satAppointment.setDay("Saturday");
        satAppointment.setAvailablity(satAvailability);
        DatabaseHelper.getInstance(getBaseContext()).addDoctorAppointment(satAppointment);



        DoctorAppointment sunAppointment=new DoctorAppointment();
        sunAppointment.setDid(doctor.getId());
        sunAppointment.setTime(sunTime);
        sunAppointment.setDate(sunDate);
        sunAppointment.setDay("Sunday");
        sunAppointment.setAvailablity(sunAvailability);
        DatabaseHelper.getInstance(getBaseContext()).addDoctorAppointment(sunAppointment);




        // DatabaseHelper.getInstance(getBaseContext()).addDoctorAppointment(new DoctorAppointment(doctor.getId(), monTime,monDate, "Monday", monAvailability));
       // DatabaseHelper.getInstance(getBaseContext()).addDoctorAppointment(new DoctorAppointment(doctor.getId(), tueTime, tueDate,"Tuesday", tueAvailability));
      //  DatabaseHelper.getInstance(getBaseContext()).addDoctorAppointment(new DoctorAppointment(doctor.getId(), wedTime, wedDate,"Wednesday", wedAvailability));
      //  DatabaseHelper.getInstance(getBaseContext()).addDoctorAppointment(new DoctorAppointment(doctor.getId(), thurTime,thurDate, "Thursday", thurAvailability));
     //   DatabaseHelper.getInstance(getBaseContext()).addDoctorAppointment(new DoctorAppointment(doctor.getId(), friTime,friDate, "Friday", friAvailability));
       // DatabaseHelper.getInstance(getBaseContext()).addDoctorAppointment(new DoctorAppointment(doctor.getId(), satTime, satDate,"Saturday", satAvailability));
    //    DatabaseHelper.getInstance(getBaseContext()).addDoctorAppointment(new DoctorAppointment(doctor.getId(), sunTime,sunDate, "Sunday", sunAvailability));
    }


}
