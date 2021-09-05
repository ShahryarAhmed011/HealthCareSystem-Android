package com.project.helthcaresystem.Screens.MakeAppointmentScreens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.project.helthcaresystem.Constants;
import com.project.helthcaresystem.DatabaseHelper.DatabaseHelper;
import com.project.helthcaresystem.Models.Doctor;
import com.project.helthcaresystem.Models.DoctorAppointment;
import com.project.helthcaresystem.Models.DoctorCategory;
import com.project.helthcaresystem.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;

public class MakeAppointmentScreen extends AppCompatActivity {

    private RequestQueue queue;
    private ArrayList<Doctor> doctorsList;
    private ArrayList<DoctorCategory> doctorCategoriesList;
    private ArrayList<DoctorAppointment> doctorAppointmentList;
    ArrayList<DoctorAppointment> doctorAppointment;
    private DatabaseHelper databaseHelper;
    private MaterialSpinner spinner;
    private RecyclerView mRecyclerView;
    private MakeAppointmentAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar mProgressBar;
    private HashSet<String> hashSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_appointment_screen);

        bindViews();
        initialize();
    }

    // same repeating
    private void bindViews(){
        mProgressBar  = findViewById(R.id.make_appointment_screen_progressBar);
        spinner = findViewById(R.id.spinner);
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerView = findViewById(R.id.make_appointment_recycler_view);

    }

    // same repeating no special logic
    private void initialize(){
        doctorAppointment = new ArrayList<>();
        databaseHelper = DatabaseHelper.getInstance(getBaseContext());
        doctorsList = new ArrayList<>();
        doctorCategoriesList = new ArrayList<>();
        doctorAppointmentList = new ArrayList<>();
        hashSet = new HashSet<String>();

        databaseHelper.deleteAllDoctors();
        databaseHelper.deleteAllDoctorCategories();
        databaseHelper.deleteAllDoctorAppointments();
        queue = Volley.newRequestQueue(getBaseContext());
        placePharmacyOrderRequest(Constants.GET_ALL_DOCTORS_INFO_URL);

    }

    //initializing spinner here here I used spinner library
    private void initializeSpinner() {


        //DoctorAppointment doctorAppointment123 =

        ArrayList<DoctorAppointment> doctorAppointment123 = DatabaseHelper.getInstance(getBaseContext()).getAppointments();

        for (int j = 0; j < doctorAppointment123.size(); j++){
     //       Constants.printLog("Date--->"+doctorAppointment123.get(j).getDate()+" doctorId "+doctorAppointment123.get(j).getDid());
        }

        for (int j = 0; j < databaseHelper.getDoctorCategory().size(); j++){
            hashSet.add(databaseHelper.getDoctorCategory().get(j).getSpeciality());
        }


        Object[] arr = hashSet.toArray();

        spinner.setItems(arr);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                    initRecyclerView(getDoctorAppointmentInfo(item)); // initializing recycler view on item changed of spinner
            }
        });

        initRecyclerView(getDoctorAppointmentInfo(spinner.getItems().get(0).toString())); //initializing recycler view first time with element on 0 index of spinner array

    }

    private ArrayList<DoctorAppointment> getDoctorAppointmentInfo(String item){
        ArrayList<DoctorAppointment> doctorAppointments  = new ArrayList<>();

        //here I am getting doctor id by speciality which selected through spinner
        for(int i= 0;i<databaseHelper.getDoctorIdBySpeciality(item).size();i++){
            //  Constants.printLog(databaseHelper.getDoctorAppointByDoctorId(item).get(i).toString());

            //here i am getting doctor appointment by doctor id
            for(int j= 0;j<databaseHelper.getDoctorAppointByDoctorId(databaseHelper.getDoctorIdBySpeciality(item).get(i)).size();j++){

                String doctorID = databaseHelper.getDoctorIdBySpeciality(item).get(i);

                DoctorAppointment doctorAppointment = databaseHelper.getDoctorAppointByDoctorId(doctorID).get(j);


                ArrayList<DoctorAppointment> doctorAppointmentArrayList  = getFilteredDates(doctorAppointment);

          //      Constants.printLog("Size--->"+doctorAppointmentArrayList.size());

                /*try {
                    for (int mk = 0; mk < doctorAppointmentArrayList.size(); mk++) {

                      Constants.printLog("Date--->"+doctorAppointmentArrayList.get(mk).getDate()+
                                      "  Time--->"+doctorAppointmentArrayList.get(mk).getTime()+
                                      "  Day--->"+ doctorAppointmentArrayList.get(mk).getDay()+
                                      "  UserName--->"+doctorsList.get(mk).getUser_name());
                    }
                }catch (Exception e){
                    Constants.showToast(getBaseContext(),"error");
                }*/



                //Constants.printLog("Appontment Date--->"+doctorAppointment.getDate());
                // then here i am geting doctor list bu doctor id
                for(int k= 0;k<doctorsList.size();k++) {

                    if (doctorsList.get(k).getId().equals(doctorID)) {

                      //  Constants.printLog(doctorsList.get(k).getUser_name());

                        // and the here i am adding data to the doctor appointment list



                        try {
                            for (int mk = 0; mk < doctorAppointmentArrayList.size(); mk++) {

                      /*          Constants.printLog("Date--->"+doctorAppointmentArrayList.get(mk).getDate()+
                                        "  Time--->"+doctorAppointmentArrayList.get(mk).getTime()+
                                        "  Day--->"+ doctorAppointmentArrayList.get(mk).getDay()+
                                        "  UserName--->"+doctorsList.get(mk).getUser_name());
*/
                                DoctorAppointment doctorAppointment1=new DoctorAppointment();
                                doctorAppointment1.setDid(doctorID);
                                doctorAppointment1.setTime(doctorAppointmentArrayList.get(mk).getTime());
                                doctorAppointment1.setDate(doctorAppointmentArrayList.get(mk).getDate());
                                doctorAppointment1.setDay(doctorAppointmentArrayList.get(mk).getDay());
                                doctorAppointment1.setDoctorName(doctorsList.get(k).getUser_name());

                                doctorAppointments.add(doctorAppointment1);

                            }
                        }catch (Exception e){
                            Constants.showToast(getBaseContext(),"error");
                        }






                      /*  DoctorAppointment doctorAppointment1=new DoctorAppointment();
                        doctorAppointment1.setDid(doctorID);
                        doctorAppointment1.setTime(doctorAppointment.getTime());
                        doctorAppointment1.setDate(doctorAppointment.getDate());
                        doctorAppointment1.setDay(doctorAppointment.getDay());
                        doctorAppointment1.setDoctorName(doctorsList.get(k).getUser_name());

                        doctorAppointments.add(doctorAppointment1);*/

                     /*   doctorAppointments.add(new DoctorAppointment(doctorID,doctorAppointment.getTime(),
                                doctorAppointment.getDay(),"",doctorsList.get(k).getUser_name()));*/
                        // returning the list to the
                    }
                }
            }
        }
        return doctorAppointments;
    }

    private ArrayList<DoctorAppointment> getFilteredDates(DoctorAppointment doctorAppointment) {
        ArrayList<DoctorAppointment> docAppointArrayList = new ArrayList<>();

        String[] appointmentDate  = doctorAppointment.getDate().split("-");


        try {
        //    Constants.printLog(appointmentDate[1]+"/"+appointmentDate[2]+"/"+appointmentDate[3]);
          //  String my_date = "31/12/2014";
            String my_date = appointmentDate[1]+"/"+appointmentDate[2]+"/"+appointmentDate[3];
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date strDate = null;
            try {
                strDate = sdf.parse(my_date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (new Date().after(strDate)) {
                //your_date_is_outdated = true;
               // Constants.printLog("Passed");
            }
            else{
               // Constants.printLog("Not Passed");

                DoctorAppointment dAppointment = new DoctorAppointment();
                dAppointment.setDate(doctorAppointment.getDate());
                dAppointment.setDay(doctorAppointment.getDay());
                dAppointment.setTime(doctorAppointment.getTime());

                docAppointArrayList.add(dAppointment);

                //your_date_is_outdated = false;
            }


        }catch (Exception e){
           // Constants.printLog(String.valueOf(e));
        }


        return docAppointArrayList;
    }

    private void initRecyclerView(ArrayList<DoctorAppointment> doctorAppointmentsList) {

            mLayoutManager = new LinearLayoutManager(getBaseContext());
            mRecyclerView.setLayoutManager(mLayoutManager);

            mAdapter = new MakeAppointmentAdapter(this, doctorAppointmentsList);
            mRecyclerView.setAdapter(mAdapter);

    }

    //Below is the process repeating method every time to putting params getting Json rquest and converting int Pojo classes Strings or JsonArrays
    public void placePharmacyOrderRequest(String url){

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                //        Constants.printLog(String.valueOf(response));

                        try {
                            Gson gson = new Gson();
                            JsonObject jsonObject = gson.fromJson(response.toString(), JsonObject.class);
                            JsonArray doctorsArray = gson.fromJson(jsonObject.get("doctors"), JsonArray.class);
                            JsonArray specialityArray = gson.fromJson(jsonObject.get("categories"), JsonArray.class);
                            JsonArray appointmentDaysArray = gson.fromJson(jsonObject.get("appointments"), JsonArray.class);


                            for (int i = 0; i < doctorsArray.size(); i++) {
                                Doctor doctor = gson.fromJson(doctorsArray.get(i), Doctor.class);
                                doctorsList.add(doctor);
                            }
                            for (int i = 0; i < specialityArray.size(); i++) {
                                DoctorCategory category = gson.fromJson(specialityArray.get(i), DoctorCategory.class);
                                databaseHelper.addDoctorCategory(category);
                            }
                            for (int i = 0; i < appointmentDaysArray.size(); i++) {

                                DoctorAppointment appointment = gson.fromJson(appointmentDaysArray.get(i), DoctorAppointment.class);
                            /* String did  = String.valueOf(appointmentDaysArray.get(i).getAsJsonObject().get("did")).replace("\"", "");
                             String time  = String.valueOf(appointmentDaysArray.get(i).getAsJsonObject().get("time")).replace("\"", "");
                             String day  = String.valueOf(appointmentDaysArray.get(i).getAsJsonObject().get("day")).replace("\"", "");
                             String availablity  = String.valueOf(appointmentDaysArray.get(i).getAsJsonObject().get("availablity")).replace("\"", "");
                             databaseHelper.addDoctorAppointment(new DoctorAppointment(did,time,day,availablity));
                             Constants.printLog(day);*/

                                String doctorId = String.valueOf(appointmentDaysArray.get(i).getAsJsonObject().get("did")).replace("\"", "");
                                String date = String.valueOf(appointmentDaysArray.get(i).getAsJsonObject().get("date")).replace("\"", "");
                                String time = String.valueOf(appointmentDaysArray.get(i).getAsJsonObject().get("time")).replace("\"", "");
                                String availability = String.valueOf(appointmentDaysArray.get(i).getAsJsonObject().get("availablity")).replace("\"", "");
                                String day = String.valueOf(appointmentDaysArray.get(i).getAsJsonObject().get("day")).replace("\"", "");


                                DoctorAppointment tueAppointment = new DoctorAppointment();
                                tueAppointment.setDid(doctorId);
                                tueAppointment.setTime(time);
                                tueAppointment.setDate(date);
                                tueAppointment.setDay(day);
                                tueAppointment.setAvailablity(availability);

                                //   Constants.printLog("Doctor Appointment Date request"+date);
                                databaseHelper.addDoctorAppointment(tueAppointment);
                            }

                            initializeSpinner();
                            mProgressBar.setVisibility(View.GONE);
                        }catch (Exception e){
                            Constants.showToast(getBaseContext(),"Data Not Exist Or Error");
                        }
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
                return params;
            }
        };
        queue.add(postRequest);
    }

    private String getDateTime() {

        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());

        Date date = new Date();

        return dateFormat.format(date);
    }

}
