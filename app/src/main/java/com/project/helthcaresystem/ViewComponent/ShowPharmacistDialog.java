package com.project.helthcaresystem.ViewComponent;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.project.helthcaresystem.Screens.PatientReportScreen.PatientReportAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShowPharmacistDialog {
    private Dialog mDialog;

    private Button mSendButton;
    private Button mCancelButton;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;

    private PharmacistDialogAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RequestQueue queue;

    ArrayList<Pharmacist> pharmacistArrayList;

    public ShowPharmacistDialog(Context context) {
        mDialog = new Dialog(context);
        if (mDialog.getWindow() != null) {
            mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        mDialog.setContentView(R.layout.show_pharmacist_dialog);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.setCancelable(true);


        mSendButton = mDialog.findViewById(R.id.send_button_send_report_dialog);
        mCancelButton = mDialog.findViewById(R.id.close_button_send_report_dialog);

        mRecyclerView = mDialog.findViewById(R.id.view_pharmacist_dialog_recycler_view);
        mProgressBar = mDialog.findViewById(R.id.view_pharmacist_dialog_progress_bar);


    }


    private void initialize(PatientReport patientReport) {
        queue = Volley.newRequestQueue(mDialog.getContext());
        getAllPharmacies(Constants.GET_ALL_PHARMACIST_URL,patientReport);

    }


    public void show(PatientReport patientReport) {
        pharmacistArrayList= new ArrayList<>();
        initialize(patientReport);
        mDialog.show();
    }


    public void dismiss() {
        mDialog.dismiss();
    }

    private void initRecyclerView(PatientReport patientReport) {

        mProgressBar.setVisibility(View.GONE);

            mLayoutManager = new LinearLayoutManager(mDialog.getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);

            mAdapter = new PharmacistDialogAdapter(mDialog.getContext(), pharmacistArrayList,patientReport,mDialog);
            mRecyclerView.setAdapter(mAdapter);
            Log.d(Constants.DEBUG_TAG,"Patient Appointments Data Not Exist");
    }

    public void getAllPharmacies(String url, final PatientReport patientReport){

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
                            JsonArray jsonArray = gson.fromJson(jsonObject.get("message"), JsonArray.class);
                        if(jsonArray.size()>=0){

                            for(int i = 0;i<jsonArray.size();i++){

                                String id = String.valueOf(jsonArray.get(i).getAsJsonObject().get("id")).replace("\"", "");;
                                String name = String.valueOf(jsonArray.get(i).getAsJsonObject().get("user_name")).replace("\"", "");;

                                pharmacistArrayList.add(new Pharmacist(id,name));
                            }
                            initRecyclerView(patientReport);
                        }else{

                            Log.d(Constants.DEBUG_TAG,"Dont Do this-->");
                        }

                        String message  = String.valueOf(jsonObject.get("message")).replace("\"", "");;
                        Log.d(Constants.DEBUG_TAG, message);
                        }catch (Exception e){
                            Constants.showToast(mDialog.getContext(),"Pharmacy Not Exist");
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
                return params;
            }
        };
        queue.add(postRequest);
    }

}