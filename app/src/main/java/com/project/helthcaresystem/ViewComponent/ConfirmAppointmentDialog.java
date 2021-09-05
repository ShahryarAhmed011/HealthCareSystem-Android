package com.project.helthcaresystem.ViewComponent;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.project.helthcaresystem.Constants;
import com.project.helthcaresystem.DatabaseHelper.DatabaseHelper;
import com.project.helthcaresystem.Models.DoctorAppointment;
import com.project.helthcaresystem.Models.Patient;
import com.project.helthcaresystem.R;
import com.project.helthcaresystem.Screens.ShowAppointmentsScreen.ShowAppointmentScreen;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ConfirmAppointmentDialog{

    private Dialog mDialog;

    private TextView mDate;
    private TextView mDayTime;
    private TextView mDcotorName;
    private Button mCancelButton;
    private Button mConfirmButton;
    private TimePickerDialog timePickerDialog;
    private Calendar calendar;
    private String format = "";
    DoctorAppointment doctorAppointment;

    private ProgressBar mProgressBar;

    private RequestQueue queue;

    public ConfirmAppointmentDialog(Context context) {
        mDialog = new Dialog(context);
        if (mDialog.getWindow() != null) {
            mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        mDialog.setContentView(R.layout.book_appointment_dialog);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);

        mProgressBar = mDialog.findViewById(R.id.book_appointment_dialog_progressBar);
        mProgressBar.setVisibility(View.GONE);

        mDate  = mDialog.findViewById(R.id.book_appointment_dialog_date_text_view);
        mDayTime  = mDialog.findViewById(R.id.book_appointment_dialog_day_time_textView);
        mDcotorName  = mDialog.findViewById(R.id.book_appointment_dialog_doctor_name_textView);
        mCancelButton  = mDialog.findViewById(R.id.book_appointment_dialog_close_button);
        mConfirmButton = mDialog.findViewById(R.id.book_appointment_dialog_confirm_button);

        initialize();
    }


    private void initialize(){
        queue = Volley.newRequestQueue(mDialog.getContext());
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.showToast(mDialog.getContext(),"Confirmed");
                mProgressBar.setVisibility(View.VISIBLE);

              /*  Constants.printLog("pid   -",DatabaseHelper.getInstance(mDialog.getContext()).getPatient().get(0).getId());
                Constants.printLog("doctorId   -",doctorAppointment.getDid());
                Constants.printLog("patientName   -",DatabaseHelper.getInstance(mDialog.getContext()).getPatient().get(0).getUser_name());
                Constants.printLog("time   -",doctorAppointment.getTime());
                Constants.printLog("day   -",doctorAppointment.getDay());
                Constants.printLog("Date   -",doctorAppointment.getDate());
*/

              if(mDayTime.getText().toString().equals("")){
                  Constants.showToast(mDialog.getContext(),"Select Time");
                  mProgressBar.setVisibility(View.GONE);
              }else{


                  String [] appointmentTime = doctorAppointment.getTime().toString().split("To");
                  String startTime =appointmentTime[0];
                  String endTime =appointmentTime[1];
                  String selectedTime  =mDayTime.getText().toString().trim();

                   if(comparetime(startTime,endTime,selectedTime)){


                      // Constants.showToast(mDialog.getContext(),"Time Is perfect");
                       doctorAppointment.setTime(selectedTime);
                       bookAppointmentRequest(Constants.BOOK_APPOINTMENT_URL,doctorAppointment);
                   }else {

                       Constants.showToast(mDialog.getContext(),"Select Time Between "+startTime+" And "+ endTime);
                       mProgressBar.setVisibility(View.GONE);
                   }
/*
                  Constants.printLog("Start Time:  ",appointmentTime[0]);
                  Constants.printLog("End Time:  ",appointmentTime[1]);
                  Constants.printLog("Selected Time:  ",mDayTime.getText().toString().trim());
*/

              }




              //  checktimings(mDayTime.getText().toString().trim(),doctorAppointment.getTime());

              //
            }
        });
    }



    public void show(DoctorAppointment doctorAppointment) {
        calendar  = Calendar.getInstance();
        this.doctorAppointment = doctorAppointment;
        mDate.setText(doctorAppointment.getDate());

        //mDayTime.setText(doctorAppointment.getDay()+" "+doctorAppointment.getTime());

        mDcotorName.setText("Doctor: "+doctorAppointment.getDoctorName());

        mDayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int mHour = calendar.get(Calendar.HOUR_OF_DAY);
                int mMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(mDialog.getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                mDayTime.setText(showTime(hourOfDay,minute));

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();



            }

        });

        mDialog.show();
    }


    public void dismiss() {

        mDialog.dismiss();
    }

    public void bookAppointmentRequest(String url, final DoctorAppointment doctorAppointment) {

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);

                        Constants.printLog("Response  ",response);
                        Gson gson= new Gson();

                        JsonObject json = gson.fromJson(response,JsonObject.class);

                        Constants.showToast(mDialog.getContext(),json.get("message").getAsString());
                        mProgressBar.setVisibility(View.INVISIBLE);
                        mDialog.dismiss();
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
                params.put("pid",  DatabaseHelper.getInstance(mDialog.getContext()).getPatient().get(0).getId());
                params.put("did", doctorAppointment.getDid());
                params.put("patientName", DatabaseHelper.getInstance(mDialog.getContext()).getPatient().get(0).getUser_name());
                params.put("time", doctorAppointment.getTime());
                params.put("apointmentDate", doctorAppointment.getDate());
                params.put("day", doctorAppointment.getDay());
                params.put("prescription","Pending");
                params.put("diagnosis", "Pending");
                params.put("note",  "Pending");
                params.put("date",  "Pending");

                return params;
            }
        };
        queue.add(postRequest);
    }


    public StringBuilder showTime(int hour, int min) {
        StringBuilder time;
        if (hour == 0) {
            hour += 12;
            format = ":AM";
        } else if (hour == 12) {
            format = ":PM";
        } else if (hour > 12) {
            hour -= 12;
            format = ":PM";
        } else {
            format = ":AM";
        }


        time = new StringBuilder().append(hour).append(":").append(min)
                .append("").append(format);

        // Log.d(Constants.DEBUG_TAG,"Time-->"+time);
        return time;
    }

    private boolean checktimings(String time, String endtime) {

        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
            Date date1 = sdf.parse(time);
            Date date2 = sdf.parse(endtime);

            if(date1.before(date2)) {
                Constants.printLog("Time ","Has Passed");
                return true;

            } else {
                Constants.printLog("Time ","Has Not Passed");
                return false;
            }
        } catch (ParseException e){
            e.printStackTrace();
        }
        return false;
    }




    private boolean comparetime(String startTime, String endTime, String selectedtime){
        try {
           // String string1 = "02:00:PM";
            String string1 = startTime;
            Date time1 = new SimpleDateFormat("HH:mm:aa").parse(string1);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time1);
            calendar1.add(Calendar.DATE, 1);

            String string2 = endTime;
            //String string2 = "04:00:PM";
            Date time2 = new SimpleDateFormat("HH:mm:aa").parse(string2);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(time2);
            calendar2.add(Calendar.DATE, 1);

            String someRandomTime = selectedtime;
            //String someRandomTime = "3:00:PM";
            Date d = new SimpleDateFormat("HH:mm:aa").parse(someRandomTime);
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(d);
            calendar3.add(Calendar.DATE, 1);

            Date x = calendar3.getTime();
            if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                //checkes whether the current time is between 14:49:00 and 20:11:13.
                System.out.println(true);
                Constants.printLog("Time is between ",String.valueOf(true));
                return true;
            }else{
                Constants.printLog("Time is Not between ",String.valueOf(true));
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}