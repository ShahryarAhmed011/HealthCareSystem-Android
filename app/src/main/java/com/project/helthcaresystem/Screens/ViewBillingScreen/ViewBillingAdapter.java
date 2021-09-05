package com.project.helthcaresystem.Screens.ViewBillingScreen;

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
import com.project.helthcaresystem.Models.PatientBill;
import com.project.helthcaresystem.Models.PatientReport;
import com.project.helthcaresystem.R;

import java.util.ArrayList;

public class ViewBillingAdapter extends RecyclerView.Adapter<ViewBillingAdapter.ViewHolder>{

    ArrayList<PatientBill> mPatientBillList;
    public Context context;

    public ViewBillingAdapter (Context context, ArrayList<PatientBill> mPatientBillList) {

        this.context = context;
        this.mPatientBillList = mPatientBillList;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_billing_list_item, parent, false);
        ViewBillingAdapter.ViewHolder holder = new ViewBillingAdapter.ViewHolder(view);



        return holder;
    }


    private int selectedPos = RecyclerView.NO_POSITION;

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final PatientBill mPatientBill = mPatientBillList.get(position);

        try {

            int charges  = Integer.valueOf(mPatientBill.getPharmacist_fee()) + Integer.valueOf(mPatientBill.getDoctor_fee());

                    holder.mBillDate.setText("Date: "+mPatientBill.getBill_date());
            holder.mDiagnosis.setText(mPatientBill.getDiagnosis());
            holder.mPrescription.setText(mPatientBill.getPrescription());
            holder.mNote.setText(mPatientBill.getNote());
            holder.mDoctorName.setText(mPatientBill.getDoctor_name());
            holder.mPharmacistName.setText(mPatientBill.getPharmacist_name());
           // holder.mDoctorfees.setText(mPatientBill.getDoctor_fee());
            holder.mPharmacistfees.setText(String.valueOf(charges));
            holder.mPaymentStatus.setText(mPatientBill.getPayment_status());

        }catch (Exception e){
            Log.d(Constants.DEBUG_TAG,String.valueOf(e));
        }

    }



    @Override
    public int getItemCount() {
        return mPatientBillList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        public TextView mBillDate;
        public TextView mDiagnosis;
        public TextView mPrescription;
        public TextView mNote;
        public TextView mDoctorName;
        public TextView mPharmacistName;
        public TextView mDoctorfees;
        public TextView mPharmacistfees;
        public TextView mPaymentStatus;


      //  public Button mSendReportButton;


        public ViewHolder(View itemView) {
            super(itemView);

            mBillDate = itemView.findViewById(R.id.date_text_view_view_billing_list_item);
            mDiagnosis = itemView.findViewById(R.id.diagnosis_text_view_view_billing_list_item);
            mPrescription = itemView.findViewById(R.id.prescription_text_view_view_billing_list_item);
            mNote = itemView.findViewById(R.id.note_text_view_view_billing_list_item);
            mDoctorName = itemView.findViewById(R.id.doctor_name_text_view_view_billing_list_item);
            mPharmacistName = itemView.findViewById(R.id.pharmacist_name_text_view_view_billing_list_item);
            //mDoctorfees = itemView.findViewById(R.id.doctor_fee_text_view_view_billing_list_item);
            mPharmacistfees = itemView.findViewById(R.id.pharmacist_fee_text_view_view_billing_list_item);
            mPaymentStatus = itemView.findViewById(R.id.payment_status_text_view_view_billing_list_item);
          //  mSendReportButton = itemView.findViewById(R.id.send_report_button_view_patient_record_item);

        }
    }

}



