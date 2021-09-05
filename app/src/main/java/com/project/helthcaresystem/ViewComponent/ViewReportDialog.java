package com.project.helthcaresystem.ViewComponent;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.project.helthcaresystem.Constants;
import com.project.helthcaresystem.DatabaseHelper.DatabaseHelper;
import com.project.helthcaresystem.Models.Doctor;
import com.project.helthcaresystem.Models.DoctorAppointment;
import com.project.helthcaresystem.Models.DoctorCategory;
import com.project.helthcaresystem.Models.Patient;
import com.project.helthcaresystem.Models.PatientReport;
import com.project.helthcaresystem.R;
import com.project.helthcaresystem.Screens.DoctorScreens.DoctorMainScreen;
import com.project.helthcaresystem.Screens.DoctorScreens.DoctorRegistrationScreen;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ViewReportDialog implements DatePickerDialog.OnDateSetListener{

    private Dialog mDialog;
  //  private OnUpdateButtonClick onUpdateButtonClick;

    private EditText mPrescriptionEditText;
    private EditText mDiagnosisEditText;
    private EditText mNoteEditText;
    private EditText mDateEditText;
    private Button mUpdateButton;
    private Button mCloseButton;
    private Context context;

    private EditText datePicker;
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private PatientReport patientReport;
    private DatabaseHelper databaseHelper;
    private RequestQueue queue;

    private ProgressBar mProgressBar;


    public ViewReportDialog(Context context) {
        this.context=context;
        mDialog = new Dialog(context);
        if (mDialog.getWindow() != null) {
            mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        mDialog.setContentView(R.layout.view_reports);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);


        mPrescriptionEditText  = mDialog.findViewById(R.id.prescription_edit_text_view_reports_dialog);
        mDiagnosisEditText  = mDialog.findViewById(R.id.diagnosis_edit_text_view_reports_dialog);
        mNoteEditText  = mDialog.findViewById(R.id.note_edit_text_view_reports_dialog);
        mDateEditText  = mDialog.findViewById(R.id.date_edit_text_view_reports_dialog);
        mProgressBar = mDialog.findViewById(R.id.progress_bar_reports_dialog);
        mProgressBar.setVisibility(View.INVISIBLE);

        mUpdateButton  = mDialog.findViewById(R.id.update_button_view_reports_dialog);
        mCloseButton  = mDialog.findViewById(R.id.close_button_view_reports_dialog);

        initialize();
    }


    private void initialize(){

        calendar = Calendar.getInstance();
        queue = Volley.newRequestQueue(context);

        datePickerDialog = new DatePickerDialog(
                context, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));


        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String prescription=mPrescriptionEditText.getText().toString().trim();
                String diagnosis=mDiagnosisEditText.getText().toString().trim();
                String note= mNoteEditText.getText().toString().trim();
                String date=mDateEditText.getText().toString().trim();



                if(validateInputs(prescription,diagnosis,note,date)){
                    //Log.d(Constants.DEBUG_TAG,"Validated");
                    mProgressBar.setVisibility(View.VISIBLE);
                    updateReportRequest(Constants.UPDATE_PATIENT_REPORT_URL,patientReport.getmPatientID(),patientReport.getmDoctorID(),prescription,diagnosis,note,date);




                }else{

                }


            }
        });


       mCloseButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               dismiss();
           }
       });

       mDateEditText.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               datePickerDialog.show();





           }
       });
    }


    public void show(String mPosition) {
        databaseHelper=DatabaseHelper.getInstance(context);
        patientReport = databaseHelper.getPatientReports().get(Integer.parseInt(mPosition));

        mPrescriptionEditText.setText(patientReport.getmPrescription());
        mDiagnosisEditText.setText(patientReport.getmDiagnosis());
        mNoteEditText.setText(patientReport.getmNote());
        mDateEditText.setText(patientReport.getmDate());
        mDialog.show();
    }


    public void dismiss() {

        mDialog.dismiss();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


        String date = String.valueOf(dayOfMonth)+"-"+String.valueOf(Integer.valueOf(month + 1))+"-"+String.valueOf(year);

        Constants.printLog("Date-->",String.valueOf(year));

        try {
            mDateEditText.setText(String.valueOf(date));
        }catch (Exception e){
            Constants.showToast(mDialog.getContext(),"Some Error");
            Constants.printLog("Error Here-->",String.valueOf(e));
        }

    }


    private boolean validateInputs(String prescription,String diagnosis, String note,String date){

        if(prescription.isEmpty()){
            mPrescriptionEditText.setError("required");
            mPrescriptionEditText.requestFocus();
            return false;
        }

        if(diagnosis.isEmpty()){
            mDiagnosisEditText.setError("required");
            mDiagnosisEditText.requestFocus();
            return false;
        }

        if(note.isEmpty()){
            mNoteEditText.setError("required");
            mNoteEditText.requestFocus();
            return false;
        }
        if(date.isEmpty()){
            mDateEditText.setError("required");
            mDateEditText.requestFocus();
            return false;
        }

        return true;
    }


    public void updateReportRequest(String url, final String patientID, final String doctorID, final String prescription, final String diagnosis, final String note, final String date) {

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        mProgressBar.setVisibility(View.INVISIBLE);
                        Gson gson= new Gson();
                        JsonObject jsonObject =  gson.fromJson(response.toString(),JsonObject.class);
                        String message  = String.valueOf(jsonObject.get("message")).replace("\"", "");;

                        if(message.equals("Request Successful")){
                            Toast.makeText(context,message,Toast.LENGTH_LONG).show();
                            databaseHelper.updatePatientReport(patientReport.getmPatientID(),prescription,diagnosis,note,date);
                            dismiss();
                        }else{
                            Toast.makeText(context,message,Toast.LENGTH_LONG).show();
                            dismiss();
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
                params.put("pid", patientID);
                params.put("did", doctorID);
                params.put("prescription", prescription);
                params.put("diagnosis",diagnosis);
                params.put("note", note);
                params.put("date", date);

                return params;
            }
        };
        queue.add(postRequest);
    }

}