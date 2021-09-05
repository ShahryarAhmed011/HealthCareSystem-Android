package com.project.helthcaresystem.Screens.PatientViewRecordScreen;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.project.helthcaresystem.Constants;
import com.project.helthcaresystem.Models.PatientReport;
import com.project.helthcaresystem.R;
import com.project.helthcaresystem.ViewComponent.ShowPharmacistDialog;
import com.project.helthcaresystem.ViewComponent.ViewReportDialog;
import java.util.ArrayList;


public class ViewRecordAdapter extends RecyclerView.Adapter<ViewRecordAdapter.ViewHolder>{

    ArrayList<PatientReport> mPatientReportList;
    public Context context;

    public ViewRecordAdapter (Context context, ArrayList<PatientReport> mPatientReportList) {

        this.context = context;
        this.mPatientReportList = mPatientReportList;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_pateint_record_item, parent, false);
        ViewRecordAdapter.ViewHolder holder = new ViewRecordAdapter.ViewHolder(view);



        return holder;
    }


    private int selectedPos = RecyclerView.NO_POSITION;

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final PatientReport mPatientReport = mPatientReportList.get(position);

        try {
         //   Constants.printLog("doctor I-->"+mPatientReport.getmDoctorID());

            holder.mDoctorName.setText("Doctor: "+mPatientReport.getmDoctorName());
            holder.mDiagnosis.setText(mPatientReport.getmDiagnosis());
            holder.mPrescription.setText(mPatientReport.getmPrescription());
            holder.mNote.setText(mPatientReport.getmNote());
            holder.mDate.setText(mPatientReport.getmDate());

        }catch (Exception e){
            Log.d(Constants.DEBUG_TAG,String.valueOf(e));
        }

        holder.mSendReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPharmacistDialog showPharmacistDialog  = new ShowPharmacistDialog(context);
                showPharmacistDialog.show(mPatientReport);
            }
        });



    }



    @Override
    public int getItemCount() {
        return mPatientReportList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        public TextView mDoctorName;
        public TextView mDiagnosis;
        public TextView mPrescription;
        public TextView mNote;
        public TextView mDate;
        public Button mSendReportButton;


        public ViewHolder(View itemView) {
            super(itemView);

            mDoctorName = itemView.findViewById(R.id.doctor_name_text_view_view_patient_record_item);
            mDiagnosis = itemView.findViewById(R.id.diagnosis_text_view_view_patient_record_item);
            mPrescription = itemView.findViewById(R.id.prescription_text_view_view_patient_record_item);
            mNote = itemView.findViewById(R.id.note_text_view_view_patient_record_item);
            mDate = itemView.findViewById(R.id.date_text_view_view_patient_record_item);
            mSendReportButton = itemView.findViewById(R.id.send_report_button_view_patient_record_item);

        }
    }

}



