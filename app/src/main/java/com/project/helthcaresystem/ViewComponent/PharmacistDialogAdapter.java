package com.project.helthcaresystem.ViewComponent;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.project.helthcaresystem.Constants;
import com.project.helthcaresystem.Models.Patient;
import com.project.helthcaresystem.Models.PatientReport;
import com.project.helthcaresystem.Models.Pharmacist;
import com.project.helthcaresystem.R;
import java.util.ArrayList;

public class PharmacistDialogAdapter extends RecyclerView.Adapter<PharmacistDialogAdapter.ViewHolder>{

        ArrayList<Pharmacist> mPharmacistList;
        PatientReport patientReport;
        public Context context;
        private Dialog mDialog;
        private RequestQueue queue;

        public PharmacistDialogAdapter (Context context, ArrayList<Pharmacist> mPharmacistList, PatientReport patientReport,Dialog mDialog) {

            this.context = context;
            this.mPharmacistList = mPharmacistList;
            this.patientReport = patientReport;
            this.mDialog = mDialog;

        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pharmacist_dialog_list_item, parent, false);
            ViewHolder holder = new PharmacistDialogAdapter.ViewHolder(view);

            return holder;
        }


        private int selectedPos = RecyclerView.NO_POSITION;
        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

            final Pharmacist mPharmacist = mPharmacistList.get(position);

            holder.mSN.setText(String.valueOf(position + 1));
            holder.mPharmacistName.setText(mPharmacist.getUser_name());

            holder.mParentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        mDialog.dismiss();
                        SendReportDialog  sendReportDialog=new SendReportDialog(context);
                        sendReportDialog.show(patientReport,mPharmacist.getId());
                      //  Constants.showToast(context, patientReport.getmDate());
                    }catch (Exception e){
                        Constants.showToast(context, "Error");
                    }
                }
            });


        }



        @Override
        public int getItemCount() {
            return mPharmacistList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder  {

            public TextView mSN;
            public TextView mPharmacistName;
            public LinearLayout mParentLayout;

            public ViewHolder(View itemView) {
                super(itemView);

                mSN = itemView.findViewById(R.id.sr_text_view_pharmacist_list_dialog);
                mPharmacistName = itemView.findViewById(R.id.pharmacist_name_text_view_send_report_dialog);
                mParentLayout = itemView.findViewById(R.id.pharmacist_dialog_list_item_layout);

            }
        }

    }