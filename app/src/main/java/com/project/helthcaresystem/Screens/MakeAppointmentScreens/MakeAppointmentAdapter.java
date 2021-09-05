package com.project.helthcaresystem.Screens.MakeAppointmentScreens;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.helthcaresystem.Constants;
import com.project.helthcaresystem.Models.DoctorAppointment;
import com.project.helthcaresystem.R;
import com.project.helthcaresystem.ViewComponent.ConfirmAppointmentDialog;

import java.util.ArrayList;

public class MakeAppointmentAdapter extends RecyclerView.Adapter<MakeAppointmentAdapter.ViewHolder>{

    ArrayList<DoctorAppointment> mDoctorAppointmentList;
    public Context context;

    public MakeAppointmentAdapter (Context context, ArrayList<DoctorAppointment> mDoctorAppointmentList) {
        this.context = context;
        this.mDoctorAppointmentList = mDoctorAppointmentList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.make_appointment_list_item, parent, false);
        ViewHolder holder = new MakeAppointmentAdapter.ViewHolder(view);
        return holder;
    }


    private int selectedPos = RecyclerView.NO_POSITION;
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final DoctorAppointment mDoctorAppointment = mDoctorAppointmentList.get(position);

      //  Constants.printLog("Doctor Appointment Date"+mDoctorAppointment.getDate());

        holder.mDoctorName.setText(mDoctorAppointment.getDoctorName());
        holder.mDate.setText(mDoctorAppointment.getDate());
        holder.mTime.setText(mDoctorAppointment.getDay()+" "+mDoctorAppointment.getTime());
        holder.mBookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.showToast(context,mDoctorAppointment.getDoctorName());
                ConfirmAppointmentDialog confirmAppointmentDialog=new ConfirmAppointmentDialog(context);
                confirmAppointmentDialog.show(mDoctorAppointment);
            }
        });

    }



    @Override
    public int getItemCount() {
        return mDoctorAppointmentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        public TextView mDate;
        public TextView mDoctorName;
        public TextView mTime;
        private Button mBookAppointment;

        public ViewHolder(View itemView) {
            super(itemView);
            mDoctorName = itemView.findViewById(R.id.make_appointment__text_view_doctor_name_list);
            mDate = itemView.findViewById(R.id.make_appointment__text_view_date_list);
            mTime = itemView.findViewById(R.id.make_appointment__text_view_date_time_list);
            mBookAppointment = itemView.findViewById(R.id.make_appointment_button_list);

        }
    }

}



