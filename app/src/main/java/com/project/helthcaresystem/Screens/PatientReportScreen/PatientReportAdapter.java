package com.project.helthcaresystem.Screens.PatientReportScreen;

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

import com.project.helthcaresystem.Constants;
import com.project.helthcaresystem.Models.PatientAppointment;
import com.project.helthcaresystem.Models.PatientReport;
import com.project.helthcaresystem.R;
import com.project.helthcaresystem.ViewComponent.ViewReportDialog;

import java.util.ArrayList;

public class PatientReportAdapter extends RecyclerView.Adapter<PatientReportAdapter.ViewHolder>{

    ArrayList<PatientReport> mPatientReportList;
    public Context context;

    public PatientReportAdapter (Context context, ArrayList<PatientReport> mPatientReportList) {

        this.context = context;
        this.mPatientReportList = mPatientReportList;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_report_list_item, parent, false);
        ViewHolder holder = new PatientReportAdapter.ViewHolder(view);

        return holder;
    }


    private int selectedPos = RecyclerView.NO_POSITION;
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final PatientReport mPatientReport = mPatientReportList.get(position);
        holder.mName.setText(mPatientReport.getmPatientName());

        holder.mViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(Constants.DEBUG_TAG,"ViewButtonPressed");

                ViewReportDialog viewReportDialog = new ViewReportDialog(context);
                viewReportDialog.show(String.valueOf(position));

            }
        });


    }



    @Override
    public int getItemCount() {
        return mPatientReportList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        public TextView mName;
        private Button mViewButton;

        public ViewHolder(View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.patient_Name_text_view_patients_report);
            mViewButton = itemView.findViewById(R.id.view_button_patients_report);

        }
    }

    }



