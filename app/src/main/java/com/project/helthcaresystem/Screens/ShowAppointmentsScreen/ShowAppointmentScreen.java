package com.project.helthcaresystem.Screens.ShowAppointmentsScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.project.helthcaresystem.Constants;
import com.project.helthcaresystem.DatabaseHelper.DatabaseHelper;
import com.project.helthcaresystem.Models.Doctor;
import com.project.helthcaresystem.Models.DoctorAppointment;
import com.project.helthcaresystem.Models.DoctorCategory;
import com.project.helthcaresystem.R;
import com.project.helthcaresystem.Screens.DoctorScreens.DoctorMainScreen;
import com.project.helthcaresystem.Screens.DoctorScreens.DoctorRegistrationScreen;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ShowAppointmentScreen extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private EditText mMondayDateEditText;
    private EditText mMonStartTimeEditText;
    private EditText mMonEndTimeEditText;
    private  MaterialSpinner mMonSpinner;

    private EditText mTueDateEditText;
    private EditText mTueStartTimeEditText;
    private EditText mTueEndTimeEditText;
    private  MaterialSpinner mTueSpinner;

    private EditText mWedDateEditText;
    private EditText mWedStartTimeEditText;
    private EditText mWedEndTimeEditText;
    private  MaterialSpinner mWedSpinner;

    private EditText mThurDateEditText;
    private EditText mThurStartTimeEditText;
    private EditText mThurEndTimeEditText;
    private  MaterialSpinner mThurSpinner;

    private EditText mFriDateEditText;
    private EditText mFriStartTimeEditText;
    private EditText mFriEndTimeEditText;
    private  MaterialSpinner mFriSpinner;

    private EditText mSatDateEditText;
    private EditText mSatStartTimeEditText;
    private EditText mSatEndTimeEditText;
    private  MaterialSpinner mSatSpinner;

    private EditText mSunDateEditText;
    private EditText mSunStartTimeEditText;
    private EditText mSunEndTimeEditText;
    private  MaterialSpinner mSunSpinner;

    private Button mSaveUpdateButton;


    private String monStartTime;
    private String monEndTime;

    private String tueStartTime;
    private String tueEndTimeEdit;

    private String wedStartTime;
    private String wedEndTime;

    private String thurStartTime;
    private String thurEndTime;

    private String friStartTime;
    private String friEndTime;

    private String satStartTime;
    private String satEndTime;


    private String sunStartTime;
    private String sunEndTime;

    private  String monSpinner;
    private  String tueSpinner;
    private  String wedSpinner;
    private  String thurSpinner;
    private  String friSpinner;
    private  String satSpinner;
    private  String sunSpinner;

    private String doctorID;
    private  String monTime;
    private  String tueTime;
    private  String wedTime;
    private  String thurTime;
    private  String friTime;
    private  String satTime;
    private  String sunTime;

    private String dayFlag = "";

    private ArrayList<String> itemList;
    private RequestQueue queue;

    private TimePickerDialog timePickerDialog;
    private Calendar calendar;

    private DatePickerDialog datePickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_appointment_screen);

        bindViews();
        initialize();

        getSupportActionBar().setTitle("Appointments");

       // Log.d(Constants.DEBUG_TAG,"Response-->"+String.valueOf(DatabaseHelper.getInstance(getBaseContext()).getDoctorAppointmentProfilesCount()));


    }

    private void bindViews(){
        mMondayDateEditText = findViewById(R.id.monday_date_edit_text);
        mTueDateEditText = findViewById(R.id.tue_date_edit_text);
        mWedDateEditText = findViewById(R.id.wed_date_edit_text);
        mThurDateEditText = findViewById(R.id.thur_date_edit_text);
        mFriDateEditText = findViewById(R.id.fri_date_edit_text);
        mSatDateEditText = findViewById(R.id.sat_date_edit_text);
        mSunDateEditText = findViewById(R.id.sun_date_edit_text);


        mMonStartTimeEditText = findViewById(R.id.monday_start_time_edit_text);
        mMonEndTimeEditText = findViewById(R.id.monday_end_time_edit_text);
        mMonSpinner = findViewById(R.id.monday_spinner);


        mTueStartTimeEditText = findViewById(R.id.tuesday_start_time_edit_text);
        mTueEndTimeEditText = findViewById(R.id.tuesday_end_time_edit_text);
        mTueSpinner = findViewById(R.id.tuesday_spinner);

        mWedStartTimeEditText = findViewById(R.id.wednesday_start_time_edit_text);
        mWedEndTimeEditText = findViewById(R.id.wednesday_end_time_edit_text);
        mWedSpinner = findViewById(R.id.wednesday_spinner);

        mThurStartTimeEditText = findViewById(R.id.thursday_start_time_edit_text);
        mThurEndTimeEditText = findViewById(R.id.thursday_end_time_edit_text);
        mThurSpinner = findViewById(R.id.thursday_spinner);

        mFriStartTimeEditText = findViewById(R.id.friday_start_time_edit_text);
        mFriEndTimeEditText = findViewById(R.id.friday_end_time_edit_text);
        mFriSpinner = findViewById(R.id.friday_spinner);

        mSatStartTimeEditText = findViewById(R.id.saturday_start_time_edit_text);
        mSatEndTimeEditText = findViewById(R.id.saturday_end_time_edit_text);
        mSatSpinner = findViewById(R.id.saturday_spinner);

        mSunStartTimeEditText = findViewById(R.id.sunday_start_time_edit_text);
        mSunEndTimeEditText = findViewById(R.id.sunday_end_time_edit_text);
        mSunSpinner = findViewById(R.id.sunday_spinner);

        mSaveUpdateButton = findViewById(R.id.save_update_button_appointment_button);

    }
    private TimePicker timePicker1;
    private String format = "";

    private void initialize(){
        queue = Volley.newRequestQueue(this);
        calendar = Calendar.getInstance();
        initializeSpinners();
        initialInputs();

        initializeDatePicker();


        doctorID = DatabaseHelper.getInstance(getBaseContext()).getDoctor().get(0).getId();


        mSaveUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* monTime = monStartTime+" To "+monEndTime;
                tueTime = tueStartTime+" To "+tueEndTimeEdit;
                wedTime = wedStartTime+" To "+wedEndTime;
                thurTime = thurStartTime+" To "+thurEndTime;
                friTime = friStartTime+" To "+friEndTime;
                satTime = satStartTime+" To "+satEndTime;
                sunTime = sunStartTime+" To "+sunEndTime;*/

                monTime = mMonStartTimeEditText.getText().toString().trim()+" To "+mMonEndTimeEditText.getText().toString().trim();
                tueTime = mTueStartTimeEditText.getText().toString().trim()+" To "+mTueEndTimeEditText.getText().toString().trim();
                wedTime = mWedStartTimeEditText.getText().toString().trim()+" To "+mWedEndTimeEditText.getText().toString().trim();
                thurTime = mThurStartTimeEditText.getText().toString().trim()+" To "+mThurEndTimeEditText.getText().toString().trim();
                friTime = mFriStartTimeEditText.getText().toString().trim()+" To "+mFriEndTimeEditText.getText().toString().trim();
                satTime = mSatStartTimeEditText.getText().toString().trim()+" To "+mSatEndTimeEditText.getText().toString().trim();
                sunTime = mSunStartTimeEditText.getText().toString().trim()+" To "+mSunEndTimeEditText.getText().toString().trim();

                String monDate = mMondayDateEditText.getText().toString().trim();
                String tueDate = mTueDateEditText.getText().toString().trim();
                String wedDate = mWedDateEditText.getText().toString().trim();
                String thurDate = mThurDateEditText.getText().toString().trim();
                String friDate = mFriDateEditText.getText().toString().trim();
                String satDate = mSatDateEditText.getText().toString().trim();
                String sunDate = mSunDateEditText.getText().toString().trim();

                updateAppointmentRequest(Constants.DOCTOR_UPDATE_APPOINTMENT_URL,doctorID,monDate,monTime,monSpinner,
                        tueDate,tueTime,tueSpinner,
                        wedDate,wedTime,wedSpinner,
                        thurDate,thurTime,thurSpinner,
                        friDate,friTime,friSpinner,
                        satDate,satTime, satSpinner,
                        sunDate,sunTime,sunSpinner);




               /* Log.d(Constants.DEBUG_TAG,"Monday 1-->"+monSpinner+"---Time--"+monTime);
                Log.d(Constants.DEBUG_TAG,"Tuesday 2-->"+tueSpinner+"---Time--"+tueTime);
                Log.d(Constants.DEBUG_TAG,"Wednesday 3-->"+wedSpinner+"---Time--"+wedTime);
                Log.d(Constants.DEBUG_TAG,"Thursday 4-->"+thurSpinner+"---Time--"+thurTime);
                Log.d(Constants.DEBUG_TAG,"Friday 5-->"+friSpinner+"---Time--"+friTime);
                Log.d(Constants.DEBUG_TAG,"Saturday 6-->"+satSpinner+"---Time--"+satTime);
                Log.d(Constants.DEBUG_TAG,"Sunday 7-->"+sunSpinner+"---Time--"+sunTime);*/
            }
        });


        initializeEditText();

    }

    private void initializeDatePicker() {
        calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(
                this, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

       datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
       /* int daysInMonth = calendar.getActualMaximum(calendar.DAY_OF_MONTH);
        datePickerDialog.getDatePicker().setMaxDate(daysInMonth);*/
        mMondayDateEditText.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                dayFlag = "Monday";
               datePickerDialog.show();
           }
       });

        mTueDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dayFlag = "Tuesday";
                datePickerDialog.show();
            }
        });

        mWedDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dayFlag = "Wednesday";
                datePickerDialog.show();
            }
        });


        mThurDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dayFlag = "Thursday";
                datePickerDialog.show();
            }
        });

        mFriDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dayFlag = "Friday";
                datePickerDialog.show();
            }
        });


        mSatDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dayFlag = "";
                datePickerDialog.show();
            }
        });


        mSunDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dayFlag = "Sunday";
                datePickerDialog.show();
            }
        });

    }

    private void initializeEditText() {
        mMonStartTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int mHour = calendar.get(Calendar.HOUR_OF_DAY);
                int mMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(ShowAppointmentScreen.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                mMonStartTimeEditText.setText(showTime(hourOfDay,minute));

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }

        });

        mMonEndTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int mHour = calendar.get(Calendar.HOUR_OF_DAY);
                int mMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(ShowAppointmentScreen.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                mMonEndTimeEditText.setText(showTime(hourOfDay,minute));

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }

        });

        mTueStartTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int mHour = calendar.get(Calendar.HOUR_OF_DAY);
                int mMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(ShowAppointmentScreen.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                mTueStartTimeEditText.setText(showTime(hourOfDay,minute));

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }

        });

        mTueEndTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int mHour = calendar.get(Calendar.HOUR_OF_DAY);
                int mMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(ShowAppointmentScreen.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                mTueEndTimeEditText.setText(showTime(hourOfDay,minute));

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }

        });

        mWedStartTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int mHour = calendar.get(Calendar.HOUR_OF_DAY);
                int mMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(ShowAppointmentScreen.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                mWedStartTimeEditText.setText(showTime(hourOfDay,minute));

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }

        });

        mWedEndTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int mHour = calendar.get(Calendar.HOUR_OF_DAY);
                int mMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(ShowAppointmentScreen.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                mWedEndTimeEditText.setText(showTime(hourOfDay,minute));

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }

        });


        mThurStartTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int mHour = calendar.get(Calendar.HOUR_OF_DAY);
                int mMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(ShowAppointmentScreen.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                mThurStartTimeEditText.setText(showTime(hourOfDay,minute));

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }

        });

        mThurEndTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int mHour = calendar.get(Calendar.HOUR_OF_DAY);
                int mMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(ShowAppointmentScreen.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                mThurEndTimeEditText.setText(showTime(hourOfDay,minute));

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }

        });

        mFriStartTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int mHour = calendar.get(Calendar.HOUR_OF_DAY);
                int mMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(ShowAppointmentScreen.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                mFriStartTimeEditText.setText(showTime(hourOfDay,minute));

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }

        });

        mFriEndTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int mHour = calendar.get(Calendar.HOUR_OF_DAY);
                int mMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(ShowAppointmentScreen.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                mFriEndTimeEditText.setText(showTime(hourOfDay,minute));

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }

        });


        mSatStartTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int mHour = calendar.get(Calendar.HOUR_OF_DAY);
                int mMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(ShowAppointmentScreen.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                mSatStartTimeEditText.setText(showTime(hourOfDay,minute));

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }

        });

        mSatEndTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int mHour = calendar.get(Calendar.HOUR_OF_DAY);
                int mMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(ShowAppointmentScreen.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                mSatEndTimeEditText.setText(showTime(hourOfDay,minute));

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }

        });

        mSunStartTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int mHour = calendar.get(Calendar.HOUR_OF_DAY);
                int mMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(ShowAppointmentScreen.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                mSunStartTimeEditText.setText(showTime(hourOfDay,minute));

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }

        });

        mSunEndTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int mHour = calendar.get(Calendar.HOUR_OF_DAY);
                int mMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(ShowAppointmentScreen.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                mSunEndTimeEditText.setText(showTime(hourOfDay,minute));

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }

        });
    }

    private void initializeSpinners() {
        itemList = new ArrayList<>();
        itemList.add("Not Available");
        itemList.add("Available");

        mMonSpinner.setItems(itemList);
        mTueSpinner.setItems(itemList);
        mWedSpinner.setItems(itemList);
        mThurSpinner.setItems(itemList);
        mFriSpinner.setItems(itemList);
        mSatSpinner.setItems(itemList);
        mSunSpinner.setItems(itemList);

        monSpinner = (String) mMonSpinner.getItems().get(mMonSpinner.getSelectedIndex());
        tueSpinner = (String) mTueSpinner.getItems().get(mTueSpinner.getSelectedIndex());;
        wedSpinner = (String) mWedSpinner.getItems().get(mWedSpinner.getSelectedIndex());;
        thurSpinner = (String) mThurSpinner.getItems().get(mThurSpinner.getSelectedIndex());;
        friSpinner = (String) mFriSpinner.getItems().get(mFriSpinner.getSelectedIndex());;
        satSpinner = (String) mSatSpinner.getItems().get(mSatSpinner.getSelectedIndex());;
        sunSpinner = (String) mSunSpinner.getItems().get(mSunSpinner.getSelectedIndex());;

        mMonSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                monSpinner = item;
               // Toast.makeText(getBaseContext(), "Clicked " + monSpinner, Toast.LENGTH_LONG).show();
            }
        });

        mTueSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                tueSpinner = item;
               // Toast.makeText(getBaseContext(), "Clicked " + monSpinner, Toast.LENGTH_LONG).show();
            }
        });

        mWedSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                wedSpinner = item;
              //  Toast.makeText(getBaseContext(), "Clicked " + monSpinner, Toast.LENGTH_LONG).show();
            }
        });

        mThurSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                thurSpinner = item;
               // Toast.makeText(getBaseContext(), "Clicked " + monSpinner, Toast.LENGTH_LONG).show();
            }
        });

        mFriSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                friSpinner = item;
            //    Toast.makeText(getBaseContext(), "Clicked " + monSpinner, Toast.LENGTH_LONG).show();
            }
        });

        mSatSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                satSpinner = item;
              //  Toast.makeText(getBaseContext(), "Clicked " + monSpinner, Toast.LENGTH_LONG).show();
            }
        });


        mSunSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                sunSpinner = item;
              //  Toast.makeText(getBaseContext(), "Clicked " + monSpinner, Toast.LENGTH_LONG).show();
            }
        });

    }

    private void initialInputs() {

        ArrayList<DoctorAppointment> doctorAppointments  = DatabaseHelper.getInstance(getBaseContext()).getAppointments();

       // Log.d(Constants.DEBUG_TAG,"monday time--->"+doctorAppointments.get(0).getmTime());

    String[] mondayTime = doctorAppointments.get(0).getTime().trim().split("To");
    String[] tuesdayTime = doctorAppointments.get(1).getTime().trim().split("To");
    String[] wednesdayTime = doctorAppointments.get(2).getTime().trim().split("To");
    String[] thursdayTime = doctorAppointments.get(3).getTime().trim().split("To");
    String[] fridayTime = doctorAppointments.get(4).getTime().trim().split("To");
    String[] saturdayTime = doctorAppointments.get(5).getTime().trim().split("To");
    String[] sundayTime = doctorAppointments.get(6).getTime().trim().split("To");
    // Log.d(Constants.DEBUG_TAG," String af");

        String monDate = doctorAppointments.get(0).getDate();
        String tueDate = doctorAppointments.get(1).getDate();
        String wedDate = doctorAppointments.get(2).getDate();
        String thurDate = doctorAppointments.get(3).getDate();
        String friDate = doctorAppointments.get(4).getDate();
        String satDate = doctorAppointments.get(5).getDate();
        String sunDate = doctorAppointments.get(6).getDate();


        int availabilityMon = getAvailabilityStatus(doctorAppointments.get(0).getAvailablity());
        int availabilityTue = getAvailabilityStatus(doctorAppointments.get(1).getAvailablity());
        int availabilityWed = getAvailabilityStatus(doctorAppointments.get(2).getAvailablity());
        int availabilityThur = getAvailabilityStatus(doctorAppointments.get(3).getAvailablity());
        int availabilityFri = getAvailabilityStatus(doctorAppointments.get(4).getAvailablity());
        int availabilitySat = getAvailabilityStatus(doctorAppointments.get(5).getAvailablity());
        int availabilitySun = getAvailabilityStatus(doctorAppointments.get(6).getAvailablity());


        monStartTime = mondayTime[0];
        monEndTime = mondayTime[1];

        tueStartTime = tuesdayTime[0];
        tueEndTimeEdit = tuesdayTime[1];

        wedStartTime = wednesdayTime[0];
        wedEndTime = wednesdayTime[1];

        thurStartTime = thursdayTime[0];
        thurEndTime = thursdayTime[1];

        friStartTime = fridayTime[0];
        friEndTime = fridayTime[1];

        satStartTime = saturdayTime[0];
        satEndTime = saturdayTime[1];

        sunStartTime = sundayTime[0];
        sunEndTime = sundayTime[1];



        if (DatabaseHelper.getInstance(getBaseContext()).checkIfDoctorAppointmentExist()) {
            mMonStartTimeEditText.setText(mondayTime[0]);
            mMonEndTimeEditText.setText(mondayTime[1]);
            mMonSpinner.setSelectedIndex(availabilityMon);

            mTueStartTimeEditText.setText(tuesdayTime[0]);
            mTueEndTimeEditText.setText(tuesdayTime[1]);
            mTueSpinner.setSelectedIndex(availabilityTue);

            mWedStartTimeEditText.setText(wednesdayTime[0]);
            mWedEndTimeEditText.setText(wednesdayTime[1]);
            mWedSpinner.setSelectedIndex(availabilityWed);

            mThurStartTimeEditText.setText(thursdayTime[0]);
            mThurEndTimeEditText.setText(thursdayTime[1]);
            mThurSpinner.setSelectedIndex(availabilityThur);

            mFriStartTimeEditText.setText(fridayTime[0]);
            mFriEndTimeEditText.setText(fridayTime[1]);
            mFriSpinner.setSelectedIndex(availabilityFri);

            mSatStartTimeEditText.setText(saturdayTime[0]);
            mSatEndTimeEditText.setText(saturdayTime[1]);
            mSatSpinner.setSelectedIndex(availabilitySat);

            mSunStartTimeEditText.setText(mondayTime[0]);
            mSunEndTimeEditText.setText(sundayTime[1]);
            mSunSpinner.setSelectedIndex(availabilitySun);


            mMondayDateEditText.setText(monDate);
            mTueDateEditText.setText(tueDate);
            mWedDateEditText.setText(wedDate);
            mThurDateEditText.setText(thurDate);
            mFriDateEditText.setText(friDate);
            mSatDateEditText.setText(satDate);
            mSunDateEditText.setText(sunDate);

        } else {
            Log.d(Constants.DEBUG_TAG," Doctor Appointment Data Not Exit");
        }


/*}catch (Exception e){
    Constants.printLog(String.valueOf(e));
}*/
    }

    private int getAvailabilityStatus(String mAvailability) {
        int status = 0;
        if(mAvailability.equals("Available")){
            status = 1;
        }else if(mAvailability.equals("Not Available")){
            status = 0;
        }

        return status;
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


    public void updateAppointmentRequest(String url,final String doctorID, final String monDate,final String monTime, final String monAvailability,
                                         final String tueDate,final String tueTime, final String tueAvailability,
                                         final String wedDate,final String wedTime, final String wedAvailability,
                                         final String thurDate,final String thurTime, final String thurAvailability,
                                         final String friDate,final String friTime, final String friAvailability,
                                         final String satDate,final String satTime, final String satAvailability,
                                         final String sunDate,final String sunTime, final String sunAvailability) {

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);

                        Gson gson= new Gson();

                        Log.d(Constants.DEBUG_TAG,"Response-->"+response);
                        Toast.makeText(getBaseContext(),"Save/Update Successful",Toast.LENGTH_LONG).show();

                        DatabaseHelper.getInstance(getBaseContext()).deleteAllDoctorAppointments();

                        DatabaseHelper.getInstance(getBaseContext()).addDoctorAppointment(new DoctorAppointment(doctorID,monTime,monDate,"Monday",monAvailability));
                        DatabaseHelper.getInstance(getBaseContext()).addDoctorAppointment(new DoctorAppointment(doctorID,tueTime,tueDate,"Tuesday",tueAvailability));
                        DatabaseHelper.getInstance(getBaseContext()).addDoctorAppointment(new DoctorAppointment(doctorID,wedTime,wedDate,"Wednesday",wedAvailability));
                        DatabaseHelper.getInstance(getBaseContext()).addDoctorAppointment(new DoctorAppointment(doctorID,thurTime,thurDate,"Thursday",thurAvailability));
                        DatabaseHelper.getInstance(getBaseContext()).addDoctorAppointment(new DoctorAppointment(doctorID,friTime,friDate,"Friday",friAvailability));
                        DatabaseHelper.getInstance(getBaseContext()).addDoctorAppointment(new DoctorAppointment(doctorID,satTime,satDate,"Saturday",satAvailability));
                        DatabaseHelper.getInstance(getBaseContext()).addDoctorAppointment(new DoctorAppointment(doctorID,sunTime,sunDate,"Sunday",sunAvailability));



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

                params.put("did", doctorID);

                params.put("monTime", monTime);
                params.put("monDate", monDate);
                params.put("monAvailability", monAvailability);

                params.put("tueTime", tueTime);
                params.put("tueDate", tueDate);
                params.put("tueAvailability", tueAvailability);

                params.put("wedTime", wedTime);
                params.put("wedDate", wedDate);
                params.put("wedAvailability", wedAvailability);

                params.put("thurTime", thurTime);
                params.put("thurDate", thurDate);
                params.put("thurAvailability", thurAvailability);

                params.put("friTime", friTime);
                params.put("friDate", friDate);
                params.put("friAvailability", friAvailability);

                params.put("satTime", satTime);
                params.put("satDate", satDate);
                params.put("satAvailability", satAvailability);

                params.put("sunTime", sunTime);
                params.put("sunDate", sunDate);
                params.put("sunAvailability", sunAvailability);

                return params;
            }
        };
        queue.add(postRequest);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Date date = new Date();
        date.setDate(dayOfMonth);
        date.setMonth(Integer.valueOf(month));
        date.setYear(year);


        String[] weekdays = new DateFormatSymbols().getWeekdays();
        String[] months = new DateFormatSymbols().getMonths();

      //  Constants.printLog("Day Of Month---> "+dayOfMonth+"  Month-->"+Integer.valueOf(month+1));


     //   Constants.printLog("You Select---> "+String.valueOf(weekdays[date.getDay()]));
        if(!dayFlag.equals(weekdays[date.getDay()])){
       //     Constants.printLog("You Select "+(weekdays[date.getDay()])+" Please Select "+dayFlag+" Dates");
            Constants.showToast(getBaseContext(),"You Selected "+(weekdays[date.getDay()])+" Please Select "+dayFlag+" Dates");
        }else{

            if(dayFlag.equals("Monday")){
                mMondayDateEditText.setText("Mon"+"-"+dayOfMonth+"-"+Integer.valueOf(month+1)+"-"+year);
            }

            if(dayFlag.equals("Tuesday")){
                mTueDateEditText.setText("Tue"+"-"+dayOfMonth+"-"+Integer.valueOf(month+1)+"-"+year);
            }

            if(dayFlag.equals("Wednesday")){
                mWedDateEditText.setText("Wed"+"-"+dayOfMonth+"-"+Integer.valueOf(month+1)+"-"+year);
            }

            if(dayFlag.equals("Thursday")){
                mThurDateEditText.setText("Thur"+"-"+dayOfMonth+"-"+Integer.valueOf(month+1)+"-"+year);
            }

            if(dayFlag.equals("Friday")){
                mFriDateEditText.setText("Fri"+"-"+dayOfMonth+"-"+Integer.valueOf(month+1)+"-"+year);
            }

            if(dayFlag.equals("")){
                mSatDateEditText.setText("Sat"+"-"+dayOfMonth+"-"+Integer.valueOf(month+1)+"-"+year);
            }

            if(dayFlag.equals("Sunday")){
                mSunDateEditText.setText("Sun"+"-"+dayOfMonth+"-"+Integer.valueOf(month+1)+"-"+year);
            }
           // Constants.printLog("Selected Day"+months[date.getMonth()]+" Dates");
        }

    }


    private  String getNameOfTheDay(int day){
        String weekDay = "";
        return weekDay;
    }
}
