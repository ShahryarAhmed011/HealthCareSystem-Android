package com.project.helthcaresystem.Screens.ContactPharmacyScreen;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.helthcaresystem.Models.DoctorAppointment;
import com.project.helthcaresystem.Models.Pharmacist;
import com.project.helthcaresystem.R;

import java.util.ArrayList;

public class ContactPharmacyAdapter extends RecyclerView.Adapter<ContactPharmacyAdapter.ViewHolder>{

    ArrayList<Pharmacist> mPharmacistList;
    public Context context;

    public ContactPharmacyAdapter (Context context, ArrayList<Pharmacist> mPharmacistList) {

        this.context = context;
        this.mPharmacistList = mPharmacistList;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_pharmacy_list_item, parent, false);
        ContactPharmacyAdapter.ViewHolder holder = new ContactPharmacyAdapter.ViewHolder(view);



        return holder;
    }


    private int selectedPos = RecyclerView.NO_POSITION;

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final Pharmacist mPharmacist = mPharmacistList.get(position);

        holder.mPharmacistName.setText(mPharmacist.getUser_name());

        holder.mEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //below I am opening email service on button click
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { mPharmacist.getEmail()});
                context.startActivity(Intent.createChooser(intent, ""));
            }
        });


        holder.mCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // /below I am opening dialer pad on button click
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+mPharmacist.getPhone_number()));
                context.startActivity(intent);
            }
        });


    }



    @Override
    public int getItemCount() {
        return mPharmacistList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        public TextView mPharmacistName;
        public Button mEmailButton;
        public Button mCallButton;




        public ViewHolder(View itemView) {
            super(itemView);

            mPharmacistName = itemView.findViewById(R.id.contact_pharmacy_list_pharmacist__button);
            mEmailButton = itemView.findViewById(R.id.contact_pharmacy_list_email__button);
            mCallButton = itemView.findViewById(R.id.contact_pharmacy_list_call__button);

        }
    }

}



