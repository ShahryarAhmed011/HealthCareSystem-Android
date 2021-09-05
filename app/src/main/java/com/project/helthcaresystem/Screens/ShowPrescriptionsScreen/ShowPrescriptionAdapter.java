package com.project.helthcaresystem.Screens.ShowPrescriptionsScreen;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.helthcaresystem.Constants;
import com.project.helthcaresystem.DatabaseHelper.DatabaseHelper;
import com.project.helthcaresystem.Models.PatientAppointment;
import com.project.helthcaresystem.Models.Prescription;
import com.project.helthcaresystem.R;
import com.project.helthcaresystem.Screens.UpdateScheduleScreen.RecyclerViewAdapter;
import com.project.helthcaresystem.ViewComponent.ProcessReportDialog;

import java.util.ArrayList;

public class ShowPrescriptionAdapter extends RecyclerView.Adapter<ShowPrescriptionAdapter.ViewHolder>{

    ArrayList<Prescription> mPrescriptionList;
    public Context context;
    private OnListItemChangeCallBack onListItemChangeCallBack;

    public ShowPrescriptionAdapter (Context context, ArrayList<Prescription> mPrescriptionList, OnListItemChangeCallBack onListItemChangeCallBack) {
        this.onListItemChangeCallBack=onListItemChangeCallBack;
        this.context = context;
        this.mPrescriptionList = mPrescriptionList;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_prescription_list_item, parent, false);
        ShowPrescriptionAdapter.ViewHolder holder = new ShowPrescriptionAdapter.ViewHolder(view);



        return holder;
    }


    private int selectedPos = RecyclerView.NO_POSITION;

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final Prescription mPrescription = mPrescriptionList.get(position);

        try {
            holder.mDoctorName.setText("Patient: "+mPrescription.getPatientName());
            holder.mDiagnosis.setText(mPrescription.getDiagnosis());
            holder.mPrescription.setText(mPrescription.getPrescription());
            holder.mNote.setText(mPrescription.getNote());
            holder.mDate.setText(mPrescription.getDate());
        }catch (Exception e){
            Log.d(Constants.DEBUG_TAG,String.valueOf(e));
        }

        holder.mProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                ProcessReportDialog processReportDialog = new ProcessReportDialog(context);
                processReportDialog.show(mPrescription, new ProcessReportDialog.OnProcessCallBack() {
                    @Override
                    public void onSuccess() {
                        refreshList(position, mPrescription);
                    }
                });


               /* ShowPharmacistDialog showPharmacistDialog  = new ShowPharmacistDialog(context);
                showPharmacistDialog.show(mPatientReport);*/
              //  Constants.showToast(context,"button Pressed");
            }
        });



    }


    private void refreshList(int position, Prescription mPrescription) {
        mPrescriptionList.remove(position);
        notifyDataSetChanged();
        onListItemChangeCallBack.onSuccess("");
    }
    @Override
    public int getItemCount() {
        return mPrescriptionList.size();
    }
    interface OnListItemChangeCallBack{
        void onSuccess(String patientID);
    }
    public class ViewHolder extends RecyclerView.ViewHolder  {

        public TextView mDoctorName;
        public TextView mDiagnosis;
        public TextView mPrescription;
        public TextView mNote;
        public TextView mDate;
        public Button mProcess;


        public ViewHolder(View itemView) {
            super(itemView);

            mDoctorName = itemView.findViewById(R.id.patient_name_text_view_show_prescription_list_item);
            mDiagnosis = itemView.findViewById(R.id.diagnosis_text_view_show_prescription_list_item);
            mPrescription = itemView.findViewById(R.id.prescription_text_view_show_prescription_list_item);
            mNote = itemView.findViewById(R.id.note_text_view_show_prescription_list_item);
            mDate = itemView.findViewById(R.id.date_text_view_show_prescription_list_item);
            mProcess = itemView.findViewById(R.id.send_report_button_show_prescription_list_item);

        }
    }

}



