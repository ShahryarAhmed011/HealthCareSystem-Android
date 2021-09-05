package com.project.helthcaresystem;

//signs =  +


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class Constants {

    public static String PATENT_REGISTRATION_URL = "https://healthcaresystemmobileapp.000webhostapp.com/Android/v1/PatientRegistration.php";
    public static String PATENT_LOGIN_URL = "https://healthcaresystemmobileapp.000webhostapp.com/Android/v1/PatientLogin.php";

    public static String DOCTOR_REGISTRATION_URL = "https://healthcaresystemmobileapp.000webhostapp.com/Android/v1/DoctorRegistration.php";
    public static String DOCTOR_LOGIN_URL = "https://healthcaresystemmobileapp.000webhostapp.com/Android/v1/DoctorLogin.php";

    public static String PHARMACIST_REGISTRATION_URL = "https://healthcaresystemmobileapp.000webhostapp.com/Android/v1/PharmacistRegistration.php";
    public static String PHARMACIST_LOGIN_URL = "https://healthcaresystemmobileapp.000webhostapp.com/Android/v1/PharmacistLogin.php";

    public static String DOCTOR_UPDATE_URL = "https://healthcaresystemmobileapp.000webhostapp.com/Android/v1/updateDoctor.php";

    public static String DOCTOR_ADD_CATEGORY_URL = "https://healthcaresystemmobileapp.000webhostapp.com/Android/v1/AddDoctorCategory.php";

    public static String DOCTOR_UPDATE_APPOINTMENT_URL = "https://healthcaresystemmobileapp.000webhostapp.com/Android/v1/UpdateDoctorAppointment.php";

    public static String DELETE_PATIENT_APPOINTMENT_URL = "https://healthcaresystemmobileapp.000webhostapp.com/Android/v1/DeletePatientAppointment.php";

    public static String UPDATE_PATIENT_REPORT_URL = "https://healthcaresystemmobileapp.000webhostapp.com/Android/v1/UpdatePatientReport.php";

    public static String PATIENT_REPORT_URL = "https://healthcaresystemmobileapp.000webhostapp.com/Android/v1/PatientReport.php";

    public static String GET_ALL_PHARMACIST_URL = "https://healthcaresystemmobileapp.000webhostapp.com/Android/v1/GetAllPharmacist.php";

    public static String PLACE_ORDER_TO_PHARMACY_URL = "https://healthcaresystemmobileapp.000webhostapp.com/Android/v1/PlacePharmacyOrder.php";

    public static String GET_ALL_DOCTORS_INFO_URL = "https://healthcaresystemmobileapp.000webhostapp.com/Android/v1/GetCompleteDoctorInfo.php";

    public static String PATIENT_UPDATE_URL = "https://healthcaresystemmobileapp.000webhostapp.com/Android/v1/UpdatePatient.php";

    public static String BOOK_APPOINTMENT_URL = "https://healthcaresystemmobileapp.000webhostapp.com/Android/v1/BookAppointment.php";

    public static String GET_PHARMACY_ORDERS_URL = "https://healthcaresystemmobileapp.000webhostapp.com/Android/v1/GetPharmacyOrders.php";

    public static String PHARMACIST_UPDATE_URL = "https://healthcaresystemmobileapp.000webhostapp.com/Android/v1/UpdatePharmacist.php";


    public static String GET_BILLING_INFO_URL = "https://healthcaresystemmobileapp.000webhostapp.com/Android/v1/GetAllBillingInfo.php";

    public static String UPDATE_BILLING_INFO_URL = "https://healthcaresystemmobileapp.000webhostapp.com/Android/v1/UpdateBilling.php";

    public static String GET_PHARMACIST_CUSTOMERS_URL = "https://healthcaresystemmobileapp.000webhostapp.com/Android/v1/GetPharmacistCustomers.php";


    public static String DEBUG_TAG = "HealthCareSystem";


    public static void showToast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

    public static void printLog(String message,String value){
        Log.d(Constants.DEBUG_TAG,message+value);

    }


     /* for(int i= 0;i<doctorAppointments.size();i++){

        }*/
}
