package com.hmi.dealsnxt.HelperClass;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.hmi.dealsnxt.Model.DealDetailsModel;

import java.lang.reflect.Type;
import java.util.ArrayList;

@SuppressLint("WorldWriteableFiles")
public class SessionManager {

    private static String DeviceIMEI = "";
    private static String UserName = "";
    private static String UserEmail = "";
    private static String LocationID = "0";
    private static String DeviceGCMNotificationID = "";
    private static String verificationMessage = "This is with invitation only kindly fill the info and Xclusify will contact you";
    private static Boolean IsOnlineAccess = false;

    /*   private static String EventCityID = "1";
       private static String FacultyUpdateTime = "";
       private static String ScheduleUpdateTime = "";
       private static String FacultyList = "";
       private static String QuestionList = "";
       private static String FeedbackList = "";
       private static String ScheduleList = "";
       private static String Schedule1List = "";
       private static String Schedule2List = "";*/
    private static String Mobileno = "";
    private static Boolean IsRegistered = false;
    private static String UserID = "";
    private static String Latitude = "28.4358";
    private static String Longitude = "77.1117154";
    private static String is_verified ="0";

    // private static String Latitude = "";
    //  private static String Longitude = "";
    private static Boolean Istut = false;
    private static Boolean Isotp = false;
    private static Boolean Isloc = false;
    private static Boolean signup = false;
    private static String city_id = "";
    private static String location_name = "Select Location";
    private static String Image = "";
    private static String UserGender = "";
    private static String UserImagePath = "";
    private static String UserDOB = "";

    public static String getUserDOB(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                UserDOB, Context.MODE_PRIVATE);
        String _UserDOB = sharedPreferences.getString("_UserDOB",
                UserDOB);
        return _UserDOB;
    }

    public static void setUserDOB(Context context, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                UserDOB, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("_UserDOB", value);
        editor.commit();
    }

    public static String getLongitude(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                Longitude, Context.MODE_PRIVATE);
        String _Longitude = sharedPreferences.getString("_Longitude",
                Longitude);
        return _Longitude;
    }


    public static String getVerificationMessage(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                verificationMessage, Context.MODE_PRIVATE);
        String _verification = sharedPreferences.getString("_verification",
                verificationMessage);
        return _verification;
    }

    public static void setVerificationMessage(Context context, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                verificationMessage, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("_verification", value);
        editor.commit();
    }

    public static void setLongitude(Context context, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                Longitude, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("_Longitude", value);
        editor.commit();
    }


    public static String getLatitude(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                Latitude, Context.MODE_PRIVATE);
        String _Latitude = sharedPreferences.getString("_Latitude",
                Latitude);
        return _Latitude;
    }

    public static void setLatitude(Context context, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                Latitude, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("_Latitude", value);
        editor.commit();
    }


    public static String  getIs_verified(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                is_verified, Context.MODE_PRIVATE);
        String  _is_verified = sharedPreferences.getString("_is_verified",
                is_verified);
        return _is_verified;
    }

    public static void setIs_verified(Context context, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                is_verified, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("_is_verified", value);
        editor.commit();
    }


    public static void setRecent(ArrayList<DealDetailsModel> arraylis, Context context) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(arraylis);
        editor.putString("dealdetail", json);
        editor.commit();
    }

    public static ArrayList<DealDetailsModel> getRecent(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("dealdetail", null);
        Type type = new TypeToken<ArrayList<DealDetailsModel>>() {
        }.getType();
        ArrayList<DealDetailsModel> arrayList = gson.fromJson(json, type);
        return arrayList;
    }


    public static String setRecent1(ArrayList<DealDetailsModel> arraylis, Context context) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(arraylis);
        editor.putString("dealdetail", json);
        editor.commit();
        return json;
    }

    public static ArrayList<DealDetailsModel> getRecent1(Context context, String json) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        // String json = sharedPrefs.getString("dealdetail", null);
        Type type = new TypeToken<ArrayList<DealDetailsModel>>() {
        }.getType();
        ArrayList<DealDetailsModel> arrayList = gson.fromJson(json, type);
        return arrayList;
    }


    public static String getMobileno(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                Mobileno, Context.MODE_PRIVATE);
        String _Mobileno = sharedPreferences.getString("_Mobileno",
                Mobileno);
        return _Mobileno;
    }

    public static void setMobileno(Context context, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                Mobileno, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("_Mobileno", value);
        editor.commit();
    }

    public static String getUserID(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                UserID, Context.MODE_PRIVATE);
        String _UserID = sharedPreferences.getString("_UserID",
                UserID);
        return _UserID;
    }

    public static void setUserID(Context context, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                UserID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("_UserID", value);
        editor.commit();
    }


    public static String getUserName(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                UserName, Context.MODE_PRIVATE);
        String _UserName = sharedPreferences.getString("_UserName",
                UserName);
        return _UserName;
    }

    public static void setUserName(Context context, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                UserName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("_UserName", value);
        editor.commit();
    }

    public static String getUserEmail(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                UserEmail, Context.MODE_PRIVATE);
        String _UserEmail = sharedPreferences.getString("_UserEmail",
                UserEmail);
        return _UserEmail;
    }

    public static void setUserEmail(Context context, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                UserEmail, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("_UserEmail", value);
        editor.commit();
    }

    public static String getLocationID(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                LocationID, Context.MODE_PRIVATE);
        String _LocationID = sharedPreferences.getString("_LocationID",
                LocationID);
        return _LocationID;
    }

    public static void setLocationID(Context context, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                LocationID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("_LocationID", value);
        editor.commit();
    }


    public static String getDeviceIMEI(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                DeviceIMEI, Context.MODE_PRIVATE);
        String device_IMEI = sharedPreferences.getString("device_IMEI",
                DeviceIMEI);
        return device_IMEI;
    }

    public static void setDeviceIMEI(Context context, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                DeviceIMEI, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("device_IMEI", value);
        editor.commit();
    }


    public static String getCityid(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                city_id, Context.MODE_PRIVATE);
        String device_IMEI = sharedPreferences.getString("city_id",
                city_id);
        return device_IMEI;
    }

    public static void setCityid(Context context, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                city_id, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("city_id", value);
        editor.commit();
    }

    public static String getLocation_name(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                location_name, Context.MODE_PRIVATE);
        String device_IMEI = sharedPreferences.getString("location_name",
                location_name);
        return device_IMEI;
    }

    public static void setlocation_name(Context context, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                location_name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("location_name", value);
        editor.commit();
    }


    public static String getDeviceGCMNotificationID(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                DeviceGCMNotificationID, Context.MODE_PRIVATE);
        String _DeviceGCMNotificationID = sharedPreferences.getString("_DeviceGCMNotificationID",
                DeviceGCMNotificationID);
        return _DeviceGCMNotificationID;
    }

    public static void setDeviceGCMNotificationID(Context context, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                DeviceGCMNotificationID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("_DeviceGCMNotificationID", value);
        editor.commit();
    }

    public static Boolean getIsRegistered(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                String.valueOf(IsRegistered), Context.MODE_PRIVATE);
        Boolean is_IsRegistered = sharedPreferences.getBoolean("IsRegistered",
                IsRegistered);
        return is_IsRegistered;
    }

    public static void setIsRegistered(Context context, Boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                String.valueOf(IsRegistered), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("IsRegistered", value);
        editor.commit();
    }

    public static Boolean getIsTut(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                String.valueOf(Istut), Context.MODE_PRIVATE);
        Boolean is_IsRegistered = sharedPreferences.getBoolean("Istut",
                Istut);
        return is_IsRegistered;
    }

    public static void setIstut(Context context, Boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                String.valueOf(Istut), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Istut", value);
        editor.commit();
    }

    public static Boolean getIsotp(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                String.valueOf(Isotp), Context.MODE_PRIVATE);
        Boolean is_IsRegistered = sharedPreferences.getBoolean("Isotp",
                Isotp);
        return is_IsRegistered;
    }

    public static void setIsotp(Context context, Boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                String.valueOf(Isotp), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Isotp", value);
        editor.commit();
    }

    public static Boolean getIssignup(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                String.valueOf(signup), Context.MODE_PRIVATE);
        Boolean is_IsRegistered = sharedPreferences.getBoolean("signup",
                signup);
        return is_IsRegistered;
    }

    public static void setIssignup(Context context, Boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                String.valueOf(signup), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("signup", value);
        editor.commit();
    }


    public static Boolean refercode = false;

    public static Boolean getrefercode(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                String.valueOf(refercode), Context.MODE_PRIVATE);
        Boolean is_refercode = sharedPreferences.getBoolean("refercode",
                refercode);
        return is_refercode;
    }

    public static void setrefercode(Context context, Boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                String.valueOf(refercode), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("refercode", value);
        editor.commit();
    }


    public static Boolean getIsloc(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                String.valueOf(Isloc), Context.MODE_PRIVATE);
        Boolean is_IsRegistered = sharedPreferences.getBoolean("Isloc",
                Isloc);
        return is_IsRegistered;
    }

    public static void setIsloc(Context context, Boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                String.valueOf(Isloc), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Isloc", value);
        editor.commit();
    }

    public static String getUserGender(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                UserGender, Context.MODE_PRIVATE);
        String _UserGender = sharedPreferences.getString("_UserGender",
                UserGender);
        return _UserGender;
    }

    public static void setUserGender(Context context, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                UserGender, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("_UserGender", value);
        editor.commit();
    }

    public static String getUserImagePath(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                UserImagePath, Context.MODE_PRIVATE);
        String user_ImagePath = sharedPreferences.getString("user_ImagePath",
                UserImagePath);
        return user_ImagePath;
    }

    public static void setUserImagePath(Context context, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                UserImagePath, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_ImagePath", value);
        editor.commit();
    }

    public static String getImage(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                Image, Context.MODE_PRIVATE);
        String image = sharedPreferences.getString("image",
                Image);
        return image;
    }

    public static void setImage(Context context, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                Image, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("image", value);
        editor.commit();
    }


    public static Boolean getIsOnlineAccess(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                String.valueOf(IsOnlineAccess), Context.MODE_PRIVATE);
        Boolean is_IsOnlineAccess = sharedPreferences.getBoolean("is_IsOnlineAccess",
                IsOnlineAccess);
        return is_IsOnlineAccess;
    }

    public static void setIsOnlineAccess(Context context, Boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                String.valueOf(IsOnlineAccess), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("is_IsOnlineAccess", value);
        editor.commit();
    }

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "androidhive-welcome";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

}