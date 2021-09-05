package com.project.helthcaresystem.Screens.PharmacyManagePaymentsScreen;

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
import com.project.helthcaresystem.Models.Prescription;
import com.project.helthcaresystem.R;
import com.project.helthcaresystem.Screens.ShowPrescriptionsScreen.ShowPrescriptionAdapter;
import com.project.helthcaresystem.ViewComponent.ProcessReportDialog;
import java.util.ArrayList;

public class PharmacyManagePaymentAdapter  extends RecyclerView.Adapter<PharmacyManagePaymentAdapter.ViewHolder>{

        ArrayList<Prescription> mPrescriptionList;
        public Context context;

        public PharmacyManagePaymentAdapter (Context context, ArrayList<Prescription> mPrescriptionList) {

            this.context = context;
            this.mPrescriptionList = mPrescriptionList;

        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pharmacy_manage_payment_screen_list_item, parent, false);
            PharmacyManagePaymentAdapter.ViewHolder holder = new PharmacyManagePaymentAdapter.ViewHolder(view);



            return holder;
        }


        private int selectedPos = RecyclerView.NO_POSITION;

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

            final Prescription mPrescription = mPrescriptionList.get(position);

            try {
                holder.mPatientName.setText("Patient: "+mPrescription.getPatientName());
                holder.mDiagnosis.setText(mPrescription.getDiagnosis());
                holder.mPrescription.setText(mPrescription.getPrescription());
                holder.mNote.setText(mPrescription.getNote());
                holder.mDate.setText(mPrescription.getDate());
            }catch (Exception e){
                Log.d(Constants.DEBUG_TAG,String.valueOf(e));
            }



        }



        @Override
        public int getItemCount() {
            return mPrescriptionList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder  {

            public TextView mPatientName;
            public TextView mDiagnosis;
            public TextView mPrescription;
            public TextView mNote;
            public TextView mDate;

            public ViewHolder(View itemView) {
                super(itemView);

                mPatientName = itemView.findViewById(R.id.patient_name_text_view__manage_payment_pharmacist_screen_list_item);
                mDiagnosis = itemView.findViewById(R.id.diagnosis_text_view__manage_payment_pharmacist_screen_list_item);
                mPrescription = itemView.findViewById(R.id.prescription_text_view__manage_payment_pharmacist_screen_list_item);
                mNote = itemView.findViewById(R.id.note_text_view_manage_payment_pharmacist_screen_list_item);
                mDate = itemView.findViewById(R.id.date_text_manage_payment_pharmacist_screen_list_item);

            }
        }

    }



