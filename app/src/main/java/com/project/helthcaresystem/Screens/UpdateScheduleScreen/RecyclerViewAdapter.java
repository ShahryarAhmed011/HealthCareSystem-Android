package com.project.helthcaresystem.Screens.UpdateScheduleScreen;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.project.helthcaresystem.Constants;
import com.project.helthcaresystem.DatabaseHelper.DatabaseHelper;
import com.project.helthcaresystem.Models.PatientAppointment;
import com.project.helthcaresystem.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    ArrayList<PatientAppointment> mPatientAppointmentList;
    public Context context;
    private RequestQueue queue;

    private ProgressBar mProgressbar;

    private OnItemChangeCallBack onItemChangeCallBack;
    public RecyclerViewAdapter(Context context,ProgressBar mProgressbar,ArrayList<PatientAppointment> mPatientAppointmentList,OnItemChangeCallBack onItemChangeCallBack) {
        this.onItemChangeCallBack=onItemChangeCallBack;
        this.context = context;
        this.mPatientAppointmentList = mPatientAppointmentList;
        this.mProgressbar = mProgressbar;

        queue = Volley.newRequestQueue(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_appointments_list_item, parent, false);
        RecyclerViewAdapter.ViewHolder holder = new RecyclerViewAdapter.ViewHolder(view);

        return holder;
    }


    private int selectedPos = RecyclerView.NO_POSITION;
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final PatientAppointment mPatientAppointment = mPatientAppointmentList.get(position);
         mProgressbar.setVisibility(View.INVISIBLE);

        holder.mName.setText(mPatientAppointment.getmPatientName());
        holder.mTime.setText(mPatientAppointment.getTime());
        holder.mDay.setText(mPatientAppointment.getDay());

        holder.mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 mProgressbar.setVisibility(View.VISIBLE);

                deletePatientAppointmentRequest(Constants.DELETE_PATIENT_APPOINTMENT_URL,mPatientAppointment.getmPatientId(),position,mPatientAppointment);

            }
        });





    }

    private void refreshList(int position, PatientAppointment mPatientAppointment) {
        mProgressbar.setVisibility(View.INVISIBLE);
        mPatientAppointmentList.remove(position);
        notifyDataSetChanged();
        DatabaseHelper.getInstance(context).deletePatientAppointment(mPatientAppointment.getmPatientId());
        onItemChangeCallBack.onSuccess(mPatientAppointment.getmPatientId());
    }

    @Override
    public int getItemCount() {
        return mPatientAppointmentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        public TextView mName;
        public TextView mTime;
        public TextView mDay;
        private Button mCancelButton;

        public ViewHolder(View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.name_text_view_patients_appointment);
            mTime = itemView.findViewById(R.id.time_text_view_patients_appointment);
            mDay = itemView.findViewById(R.id.day_text_view_patients_appointment);

            mCancelButton = itemView.findViewById(R.id.cancel_button_patients_appointment);

        }
    }

    interface OnItemChangeCallBack{
        void onSuccess(String patientID);
    }

    private void deletePatientAppointmentRequest(String url, final String pid, final int position, final PatientAppointment mPatientAppointment){


        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();

                        Gson gson= new Gson();

                        refreshList(position, mPatientAppointment);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("pid", pid);
              //  params.put("password", password);
                return params;
            }
        };

        queue.add(stringRequest);
    }



}
