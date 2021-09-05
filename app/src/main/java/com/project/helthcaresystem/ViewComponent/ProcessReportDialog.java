package com.project.helthcaresystem.ViewComponent;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TimePicker;

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
import com.project.helthcaresystem.Models.PatientReport;
import com.project.helthcaresystem.Models.Pharmacist;
import com.project.helthcaresystem.Models.Prescription;
import com.project.helthcaresystem.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ProcessReportDialog {

    private Dialog mDialog;

    private EditText mDateEditText;
    private EditText mAmountEditText;
    private Button mCompleteButton;
    private Button mCancelButton;
    private ProgressBar mProgressBar;
    private RequestQueue queue;
    private OnProcessCallBack onProcessCallBack;
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;


    ArrayList<Pharmacist> pharmacistArrayList;

    public ProcessReportDialog(Context context) {
        mDialog = new Dialog(context);
        if (mDialog.getWindow() != null) {
            mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        mDialog.setContentView(R.layout.process_report_pharmacist_dialog);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.setCancelable(true);


        mDateEditText = mDialog.findViewById(R.id.process_report_pharmacist_dialog_date_edit_text);
        mAmountEditText = mDialog.findViewById(R.id.process_report_pharmacist_dialog_amount_edit_text);
        mCompleteButton = mDialog.findViewById(R.id.process_report_pharmacist_dialog_complete_button);
        mCancelButton = mDialog.findViewById(R.id.process_report_pharmacist_dialog_close_button);
        mProgressBar = mDialog.findViewById(R.id.process_report_pharmacist_dialog_progress_bar);


    }


    private void initialize(final Prescription prescription) {
        calendar = Calendar.getInstance();
        queue = Volley.newRequestQueue(mDialog.getContext());


        mCompleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mDate  = mDateEditText.getText().toString().trim();
                String mAmount  = mAmountEditText.getText().toString().trim();


                if(validateInputs(mDate,mAmount)){
                   // Constants.showToast(mDialog.getContext(),"Complete Button");
                    updateBilling(Constants.UPDATE_BILLING_INFO_URL,prescription,mDate,mAmount);
                    mProgressBar.setVisibility(View.VISIBLE);
                }

            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


    public void show(Prescription prescription,OnProcessCallBack onProcessCallBack) {
        this.onProcessCallBack = onProcessCallBack;
        pharmacistArrayList = new ArrayList<>();
        initialize(prescription);
        mDialog.show();
    }


    public void dismiss() {
        mDialog.dismiss();
    }

    public void updateBilling(String url, final Prescription prescription, final String date, final String pharmacistFees){

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        mProgressBar.setVisibility(View.INVISIBLE);
                        Gson gson= new Gson();
                        try {

                            JsonObject jsonObject = gson.fromJson(response.toString(), JsonObject.class);

                            Constants.showToast(mDialog.getContext(), String.valueOf(jsonObject.get("message")));

                            mProgressBar.setVisibility(View.INVISIBLE);
                            onProcessCallBack.onSuccess();
                            dismiss();
                        }catch (Exception e){
                            mProgressBar.setVisibility(View.INVISIBLE);
                            Constants.showToast(mDialog.getContext(),"Pharmacy Not Exist");
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        mProgressBar.setVisibility(View.INVISIBLE);
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();

               /* Constants.printLog("pharmacist Id-->"+DatabaseHelper.getInstance(mDialog.getContext()).getPharmacist().get(0).getId());
                Constants.printLog("patient ID--->"+prescription.getPatient_id());
                Constants.printLog("order ID--->"+prescription.getOrder_id());
*/

                params.put("pharmacist_id", DatabaseHelper.getInstance(mDialog.getContext()).getPharmacist().get(0).getId());
                params.put("patient_id", prescription.getPatient_id());
                params.put("order_id", prescription.getOrder_id());
                params.put("doctor_fees", "1200");
                params.put("pharmacist_fees", pharmacistFees);
                params.put("payment_status", "Paid");
                params.put("date", date);


                return params;
            }
        };
        queue.add(postRequest);
    }


    private boolean validateInputs(String mDate, String mAmount){

        if(mDate.isEmpty()){
            mDateEditText.setError("date is required");
            mDateEditText.requestFocus();
            return false;
        }


        if(mAmount.isEmpty()){
            mAmountEditText.setError("password is required");
            mAmountEditText.requestFocus();
            return false;
        }



        return true;
    }


    public interface OnProcessCallBack{
        void onSuccess();
    }
}