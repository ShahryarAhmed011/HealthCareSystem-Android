package com.project.helthcaresystem.ViewComponent;

import android.app.Dialog;
import android.content.Context;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
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
import com.project.helthcaresystem.Models.Patient;
import com.project.helthcaresystem.Models.PatientReport;
import com.project.helthcaresystem.Models.Pharmacist;
import com.project.helthcaresystem.R;

import java.util.HashMap;
import java.util.Map;

public class SendReportDialog {
    private Dialog mDialog;

    private TextView mDoctor;
    private TextView mPrescription;
    private TextView mDiagnosis;
    private TextView mNote;
    private TextView mDate;
    private Button mSendButton;
    private Button mCancelButton;
    private ProgressBar mProgressBar;
    private String pharmacyID;
    private RequestQueue queue;
    private String doctorID;

    public SendReportDialog(Context context) {
        mDialog = new Dialog(context);
        if (mDialog.getWindow() != null) {
            mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        mDialog.setContentView(R.layout.send_report_dialog);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);



        mDoctor = mDialog.findViewById(R.id.doctor_text_view_send_report_dialog);
        mPrescription = mDialog.findViewById(R.id.prescription_text_view_send_report_dialog);
        mDiagnosis = mDialog.findViewById(R.id.diagnosis_text_view_send_report_dialog);
        mNote = mDialog.findViewById(R.id.note_text_view_send_report_dialog);
        mDate = mDialog.findViewById(R.id.date_text_view_send_report_dialog);

        mSendButton  = mDialog.findViewById(R.id.send_button_send_report_dialog);
        mCancelButton  = mDialog.findViewById(R.id.close_button_send_report_dialog);

        mProgressBar = mDialog.findViewById(R.id.progress_bar_send_report_dialog);
        mProgressBar.setVisibility(View.GONE);

        initialize();
    }


    private void initialize(){
        queue = Volley.newRequestQueue(mDialog.getContext());
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PatientReport patient=new PatientReport();
                patient.setmDoctorID(doctorID);
                patient.setmPrescription(mPrescription.getText().toString());
                patient.setmDiagnosis(mDiagnosis.getText().toString());
                patient.setmNote(mNote.getText().toString());
                patient.setmDate(mDate.getText().toString());
                placePharmacyOrderRequest(Constants.PLACE_ORDER_TO_PHARMACY_URL,patient,pharmacyID);
                mProgressBar.setVisibility(View.VISIBLE);

            }
        });
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });
    }



    public void show(PatientReport mPatientReport,String pharmacyID) {
        this.pharmacyID = pharmacyID;
        this.doctorID= mPatientReport.getmDoctorID();

        mDoctor.setText(mPatientReport.getmDoctorName());
        mPrescription.setText(mPatientReport.getmPrescription());
        mDiagnosis.setText(mPatientReport.getmDiagnosis());
        mNote.setText(mPatientReport.getmNote());
        mDate.setText(mPatientReport.getmDate());

     //   Constants.printLog("prescription"+mPrescription.getText());

        /*Constants.printLog("Diagnosis--"+mPatientReport.getmDiagnosis());
        Constants.printLog("prescription"+mPatientReport.getmPrescription());*/


        mDialog.show();
    }


    public void dismiss() {
        mDialog.dismiss();
    }

    public void placePharmacyOrderRequest(String url, final PatientReport patientReport, final String pharmacyID){

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                //        Constants.printLog(response);
                        // response
                        mProgressBar.setVisibility(View.INVISIBLE);
                        Gson gson= new Gson();
                        try {
                            JsonObject jsonObject = gson.fromJson(response.toString(), JsonObject.class);
                            Constants.showToast(mDialog.getContext(), String.valueOf(jsonObject.get("message")));
                            mProgressBar.setVisibility(View.GONE);
                            dismiss();
                        }catch (Exception e){
                            mProgressBar.setVisibility(View.GONE);
                            Constants.showToast(mDialog.getContext(),"Error");
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
              /*
                Constants.printLog("patient_id in Map-->"+DatabaseHelper.getInstance(mDialog.getContext()).getPatient().get(0).getId());
*/
               // Constants.printLog("diagnosis-->"+patientReport.getmDiagnosis());
                Map<String, String> params = new HashMap<String, String>();
                params.put("pharmacy_id",pharmacyID);
                params.put("doctor_id",patientReport.getmDoctorID());
                params.put("patient_id", DatabaseHelper.getInstance(mDialog.getContext()).getPatient().get(0).getId());
                params.put("prescription",patientReport.getmPrescription());
                params.put("diagnosis",patientReport.getmDiagnosis());
                params.put("note",patientReport.getmNote());
                params.put("date",patientReport.getmDate());
                return params;
            }
        };
        queue.add(postRequest);
    }

}