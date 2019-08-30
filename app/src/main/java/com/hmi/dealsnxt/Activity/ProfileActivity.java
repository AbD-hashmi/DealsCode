package com.hmi.dealsnxt.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.analytics.Tracker;
import com.hmi.dealsnxt.HelperClass.Constaints;
import com.hmi.dealsnxt.HelperClass.Global;
import com.hmi.dealsnxt.HelperClass.RegisValidation;
import com.hmi.dealsnxt.HelperClass.SessionManager;
import com.hmi.dealsnxt.R;
import com.hmi.dealsnxt.Utils.Common;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    ImageView ivProfileRankingBackground;
    ImageView ivProfileCountryFlag;
    TextView tvProfileRank, tvProfileName, tvProfileDesignation;
    EditText etUserOldPassword, etUserName, etUserContactNo, etUserEmail, etUserDesignation, etUserWorkPlace, etUserAcademic, etUserPassword, etconfirmPassword;
    ImageLoader imageLoader;
    boolean isProfileEdited = false;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    public Bitmap thumbnail_final;
    Boolean IsImageSelected = false;
    Tracker t;
    DisplayImageOptions options;
    public LinearLayout LLfooter, LLfooterHome, LLfooterOrder, LLfooterProfile, LLfootermore, LLfooterFavourite, LLloc;
    public TextView tvHome, tvOrder, tvProfile, tvFavourite, tvmore;
    public ImageView ivHome, ivOrder, ivProfile, ivFavourite, ivmore;
    public DrawerLayout drawer;
    public ImageView imback, ivsignout, ivEditIcon;
    public TextView save, etDOB;
    public ProgressBar progressBar;
    public EditText etname, etemail, etmobile;
    public de.hdodenhof.circleimageview.CircleImageView userimage;
    static String ReminderDateStart = "";
    static int myear;
    static int mmonth;
    static int mday;
    Spinner genderSpinner;
    public int getGenderId;
    public String getGender = "";
    public String SelectedGender = "";
    LinearLayout LLshare, LLNotification,LLrate, LLRefer, LLPrivacy, LLUse, LLAbout, LLcontact, LLlogout;
    LinearLayout LLpopulardeals, LLproximity, LLtrading, LLorder;
    ArrayList<String> list = new ArrayList<String>();


    String mo[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    String stringdob = "";
    TextView gender_text;
    public static ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_profile);


      /*  this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);*/
        imback = (ImageView) findViewById(R.id.imback);
        ivsignout = (ImageView) findViewById(R.id.ivsignout);
        ivEditIcon = (ImageView) findViewById(R.id.ivEditIcon);
        userimage = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.userimage);
        etname = (EditText) findViewById(R.id.etname);
        gender_text = (TextView) findViewById(R.id.gender_text);
        etemail = (EditText) findViewById(R.id.etemail);
        etmobile = (EditText) findViewById(R.id.etmobile);
        etDOB = (TextView) findViewById(R.id.etDOB);
        save = (TextView) findViewById(R.id.save);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        genderSpinner = (Spinner) findViewById(R.id.genderSpinner);
        progressDialog = Common.getProgressDialog(ProfileActivity.this);

        String ProfileImageURL = SessionManager.getUserImagePath(getApplicationContext());
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getApplicationContext()));
        options = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .showImageOnLoading(R.drawable.profileimages).showImageForEmptyUri(R.drawable.profileimages).showImageOnFail(R.drawable.profileimages)
                .build();
        etname.setText(SessionManager.getUserName(getApplicationContext()));
        etemail.setText(SessionManager.getUserEmail(getApplicationContext()));
        etmobile.setText(SessionManager.getMobileno(getApplicationContext()));
        getGender = SessionManager.getUserGender(getApplicationContext());

        if (SessionManager.getUserDOB(getApplicationContext()).equals("")) {
            etDOB.setText("DOB  ");
        } else {
            etDOB.setText(SessionManager.getUserDOB(getApplicationContext()));
        }

        stringdob = SessionManager.getUserDOB(getApplicationContext());
        if ((SessionManager.getUserImagePath(getApplicationContext()) != null)) {
            imageLoader.displayImage(ProfileImageURL, userimage, options);
        } else {
        }

        imback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ivsignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(ProfileActivity.this, R.style.AppCompatAlertDialogStyle);
                builder.setTitle("Alert");
                builder.setMessage("Are you sure you want to Logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        /*Toast.makeText(getApplicationContext(), "Successfully Logout", Toast.LENGTH_SHORT).show();
                        SessionManager.setIsRegistered(getApplicationContext(), false);
                        SharedPreferences preferences = getSharedPreferences("uid", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        SessionManager.setIsRegistered(getApplicationContext(), false);
                        SessionManager.setIssignup(getApplicationContext(), false);
                        SessionManager.setLatitude(getApplicationContext(),"");
                        SessionManager.setLongitude(getApplicationContext(),"");
                        SessionManager.setUserID(getApplicationContext(),"");
                        SessionManager.setIsloc(getApplicationContext(),false);
                        SessionManager.setIstut(getApplicationContext(),true);
                        SessionManager.setIsotp(getApplicationContext(),false);
                        editor.clear();
                        editor.commit();
                        finish();
                        Intent i = new Intent(LandingNewActivity.this, AftersplashActivity.class);
                        startActivity(i);*/
                        SessionManager.setIsRegistered(getApplicationContext(), false);
                        SessionManager.setIssignup(getApplicationContext(), false);
                        SharedPreferences preferences = getSharedPreferences("uid", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        SessionManager.setIsRegistered(getApplicationContext(), false);
                        SessionManager.setIssignup(getApplicationContext(), false);
                        SessionManager.setLatitude(getApplicationContext(), "");
                        SessionManager.setLongitude(getApplicationContext(), "");
                        SessionManager.setUserID(getApplicationContext(), "");
                        SessionManager.setIsloc(getApplicationContext(), false);
                        SessionManager.setIstut(getApplicationContext(), true);
                        SessionManager.setIsotp(getApplicationContext(), false);
                        Toast.makeText(getApplicationContext(), "Logout Sucessfully", Toast.LENGTH_SHORT).show();
                        editor.clear();
                        editor.commit();
                        finish();
                        Intent i = new Intent(ProfileActivity.this, OTPActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
                builder.setNegativeButton("No", null);
                builder.show();
              /*  Intent i = new Intent(ProfileActivity.this, OTPActivity.class);
                startActivity(i);*/
            }
        });
        genderSpinner.setEnabled(false);
        genderSpinner.setVisibility(View.GONE);
        gender_text.setVisibility(View.VISIBLE);
        save.setVisibility(View.INVISIBLE);
        userimage.setClickable(false);
        userimage.setEnabled(false);
        ivEditIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etname.setEnabled(true);
                etname.setFocusable(true);
                etemail.setEnabled(true);
                etemail.setFocusable(true);
                etmobile.setEnabled(true);
                etmobile.setFocusable(true);
                genderSpinner.setEnabled(true);
                save.setVisibility(View.VISIBLE);
                etDOB.setEnabled(true);
                etDOB.setFocusable(true);
                userimage.setClickable(true);
                userimage.setEnabled(true);
                genderSpinner.setVisibility(View.VISIBLE);
                gender_text.setVisibility(View.GONE);
            }
        });
        userimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        etDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                myear = c.get(Calendar.YEAR);
                mmonth = c.get(Calendar.MONTH);
                mday = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog currentDate = new DatePickerDialog(ProfileActivity.this, selectedCheckInDate, myear, mmonth, mday);
                //   currentDate.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                //  currentDate.getDatePicker().setMaxDate((System.currentTimeMillis() - 1000) + (1000 * 60 * 60 * 24 * 7));
                currentDate.getDatePicker().setMaxDate(c.getTimeInMillis());
                currentDate.show();
                //      currentDate.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                //    currentDate.getDatePicker().setMaxDate((System.currentTimeMillis() - 1000) + (1000 * 60 * 60 * 24 * 7));
            }
        });

        //  GenderSpiner(getGender);
        LLfooter = (LinearLayout) findViewById(R.id.LLfooter);
        LLfooterHome = (LinearLayout) LLfooter.findViewById(R.id.LLfooterHome);
        LLfooterOrder = (LinearLayout) LLfooter.findViewById(R.id.LLfooterOrder);
        LLfooterProfile = (LinearLayout) LLfooter.findViewById(R.id.LLfooterProfile);
        LLfooterFavourite = (LinearLayout) LLfooter.findViewById(R.id.LLfooterFavourite);
        LLfootermore = (LinearLayout) LLfooter.findViewById(R.id.LLfootermore);
        tvHome = (TextView) LLfooter.findViewById(R.id.tvHome);
        tvOrder = (TextView) LLfooter.findViewById(R.id.tvOrder);
        tvProfile = (TextView) LLfooter.findViewById(R.id.tvProfile);
        tvFavourite = (TextView) LLfooter.findViewById(R.id.tvFavourite);
        tvmore = (TextView) LLfooter.findViewById(R.id.tvmore);
        ivHome = (ImageView) LLfooter.findViewById(R.id.ivHome);
        ivOrder = (ImageView) LLfooter.findViewById(R.id.ivOrder);
        ivProfile = (ImageView) LLfooter.findViewById(R.id.ivProfile);
        ivFavourite = (ImageView) LLfooter.findViewById(R.id.ivFavourite);
        ivmore = (ImageView) LLfooter.findViewById(R.id.ivmore);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        ivHome.setImageDrawable(getResources().getDrawable(R.drawable.inactive_home));
        ivOrder.setImageDrawable(getResources().getDrawable(R.drawable.inactive_order));
        ivProfile.setImageDrawable(getResources().getDrawable(R.drawable.active_profile));
        ivFavourite.setImageDrawable(getResources().getDrawable(R.drawable.inactive_favorite));
        ivmore.setImageDrawable(getResources().getDrawable(R.drawable.inactive_more));
        tvHome.setTextColor(getResources().getColor(R.color.greyfontcol));
        tvOrder.setTextColor(getResources().getColor(R.color.greyfontcol));
        tvProfile.setTextColor(getResources().getColor(R.color.redcolor));
        tvFavourite.setTextColor(getResources().getColor(R.color.greyfontcol));
        tvmore.setTextColor(getResources().getColor(R.color.greyfontcol));

        LLfooterHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivHome.setImageDrawable(getResources().getDrawable(R.drawable.active_home));
                ivOrder.setImageDrawable(getResources().getDrawable(R.drawable.inactive_order));
                ivProfile.setImageDrawable(getResources().getDrawable(R.drawable.inactive_profile));
                ivFavourite.setImageDrawable(getResources().getDrawable(R.drawable.inactive_favorite));

                tvHome.setTextColor(getResources().getColor(R.color.redcolor));
                tvOrder.setTextColor(getResources().getColor(R.color.greyfontcol));
                tvProfile.setTextColor(getResources().getColor(R.color.greyfontcol));
                tvFavourite.setTextColor(getResources().getColor(R.color.greyfontcol));
                ivmore.setImageDrawable(getResources().getDrawable(R.drawable.inactive_more));
                tvmore.setTextColor(getResources().getColor(R.color.greyfontcol));
                Intent i = new Intent(ProfileActivity.this, LandingNewActivity.class);
                startActivity(i);
            }
        });
        LLfooterOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivHome.setImageDrawable(getResources().getDrawable(R.drawable.inactive_home));
                ivOrder.setImageDrawable(getResources().getDrawable(R.drawable.active_order));
                ivProfile.setImageDrawable(getResources().getDrawable(R.drawable.inactive_profile));
                ivFavourite.setImageDrawable(getResources().getDrawable(R.drawable.inactive_favorite));
                tvHome.setTextColor(getResources().getColor(R.color.greyfontcol));
                tvOrder.setTextColor(getResources().getColor(R.color.redcolor));
                tvProfile.setTextColor(getResources().getColor(R.color.greyfontcol));
                tvFavourite.setTextColor(getResources().getColor(R.color.greyfontcol));
                ivmore.setImageDrawable(getResources().getDrawable(R.drawable.inactive_more));
                tvmore.setTextColor(getResources().getColor(R.color.greyfontcol));

                Intent i = new Intent(ProfileActivity.this, OrderActivity.class);
                startActivity(i);
            }
        });

        LLfooterFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivHome.setImageDrawable(getResources().getDrawable(R.drawable.inactive_home));
                ivOrder.setImageDrawable(getResources().getDrawable(R.drawable.inactive_order));
                ivProfile.setImageDrawable(getResources().getDrawable(R.drawable.inactive_profile));
                ivFavourite.setImageDrawable(getResources().getDrawable(R.drawable.active_favorite));
                tvHome.setTextColor(getResources().getColor(R.color.greyfontcol));
                tvOrder.setTextColor(getResources().getColor(R.color.greyfontcol));
                tvProfile.setTextColor(getResources().getColor(R.color.greyfontcol));
                tvFavourite.setTextColor(getResources().getColor(R.color.redcolor));
                ivmore.setImageDrawable(getResources().getDrawable(R.drawable.inactive_more));
                tvmore.setTextColor(getResources().getColor(R.color.greyfontcol));
                // Intent i = new Intent(ProfileActivity.this, LandingNewActivity.class);
                // startActivity(i);
            }
        });
        LLfootermore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivHome.setImageDrawable(getResources().getDrawable(R.drawable.inactive_home));
                ivOrder.setImageDrawable(getResources().getDrawable(R.drawable.inactive_order));
                ivProfile.setImageDrawable(getResources().getDrawable(R.drawable.inactive_profile));
                ivFavourite.setImageDrawable(getResources().getDrawable(R.drawable.inactive_favorite));
                tvHome.setTextColor(getResources().getColor(R.color.greyfontcol));
                tvOrder.setTextColor(getResources().getColor(R.color.greyfontcol));
                tvProfile.setTextColor(getResources().getColor(R.color.greyfontcol));
                tvFavourite.setTextColor(getResources().getColor(R.color.greyfontcol));
                ivmore.setImageDrawable(getResources().getDrawable(R.drawable.active_more));
                tvmore.setTextColor(getResources().getColor(R.color.redcolor));
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                } else {
                    drawer.openDrawer(GravityCompat.END);
                }
            }
        });

   /*     ArrayList<String> list = new ArrayList<String>();
        list.add("Female");
        list.add("Male");
        genderSpinner.setAdapter(new CustomSpinnerAdapter(getApplicationContext(), list));*/
        //  genderSpinner.setSelected(SessionManager.getUserGender(getApplicationContext()));
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View v, int position, long id) {
                SelectedGender = adapter.getItemAtPosition(position).toString();
                //   Toast.makeText(getApplicationContext(), "Selected Gender :" + gender, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });






        setgender(getGender);
        findviewbyDrawer();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateUser();
            }
        });
    }


    public void setgender(final String getGender) {
        try {
         /*   ArrayList<String> arraylist = new ArrayList<>();
            DealTypeList = SpinnerDealTypeModel.getAllDeal();
            for (int i = 0; i < DealTypeList.size(); i++) {
                arraylist.add(DealTypeList.get(i).getDeal_StageValue());
            }*/
            list.add("Female");
            list.add("Male");
            genderSpinner.setAdapter(new CustomSpinnerAdapter(getApplicationContext(), list));
            //    SpinnerDealType.setAdapter(new DealTypeSpinnerAdapter(getActivity(), arraylist));
            for (int i = 0; i < list.size(); i++) {
                // int a = DealTypeList.get(i).getDeal_TypeId();
                if (list.get(i).equals(getGender)) {
                    genderSpinner.setSelection(i);
                    gender_text.setText(list.get(i));
                    break;
                }
            }
        } catch (Exception e) {
            Log.e("", "" + e.getMessage());
        }


    }

    private void findviewbyDrawer() {
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        tvmore = (TextView) findViewById(R.id.tvmore);
        LLorder = (LinearLayout) findViewById(R.id.LLorder);
        LLtrading = (LinearLayout) findViewById(R.id.LLtrading);
        LLproximity = (LinearLayout) findViewById(R.id.LLproximity);
        LLpopulardeals = (LinearLayout) findViewById(R.id.LLpopulardeals);
        LLshare = (LinearLayout) findViewById(R.id.LLshare);
        LLNotification = (LinearLayout) findViewById(R.id.LLNotification);
        LLRefer = (LinearLayout) findViewById(R.id.LLRefer);
        LLPrivacy = (LinearLayout) findViewById(R.id.LLPrivacy);
        LLUse = (LinearLayout) findViewById(R.id.LLUse);
        LLrate= (LinearLayout) findViewById(R.id.LLrate);
        LLAbout = (LinearLayout) findViewById(R.id.LLAbout);
        LLcontact = (LinearLayout) findViewById(R.id.LLcontact);
        LLlogout = (LinearLayout) findViewById(R.id.LLlogout);

        tvmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
            }
        });
        LLorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, OrderActivity.class);
                startActivity(i);

                drawer.closeDrawers();
            }
        });
        LLtrading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
            }
        });
        LLproximity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, NearByActivity.class);
                startActivity(i);
                drawer.closeDrawers();
            }
        });
        LLpopulardeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
            }
        });
        LLshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent("android.intent.action.SEND");
                i.setType("text/plain");
                i.putExtra("android.intent.extra.TEXT", "Enjoy deals around you. Download here+ App:- https://play.google.com/store/apps/details?id=com.mhi.dealnxt");
                startActivity(Intent.createChooser(i, "Share App"));
                drawer.closeDrawers();
            }
        });
        LLNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(ProfileActivity.this, NotificationActivity.class);
                startActivity(i);
                drawer.closeDrawers();
            }
        });
        LLrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.mhi.dealnxt"));
                    startActivity(i);
                } catch (ActivityNotFoundException error) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.mhi.dealnxt"));
                    startActivity(i);
                }
                drawer.closeDrawers();
            }
        });
        LLlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(ProfileActivity.this, R.style.AppCompatAlertDialogStyle);
                builder.setTitle("Alert");
                builder.setMessage("Are you sure you want to Logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Successfully Logout", Toast.LENGTH_SHORT).show();
                        SessionManager.setIsRegistered(getApplicationContext(), false);
                        SessionManager.setIssignup(getApplicationContext(), false);
                        SessionManager.setIsRegistered(getApplicationContext(), false);
                        SessionManager.setIssignup(getApplicationContext(), false);
                        SessionManager.setLatitude(getApplicationContext(), "");
                        SessionManager.setLongitude(getApplicationContext(), "");
                        SessionManager.setUserID(getApplicationContext(), "");
                        SessionManager.setIsloc(getApplicationContext(), false);
                        SessionManager.setIstut(getApplicationContext(), false);
                        SharedPreferences preferences = getSharedPreferences("uid", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.commit();
                        finish();
                        Intent i = new Intent(ProfileActivity.this, OTPActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
                builder.setNegativeButton("No", null);
                builder.show();
                drawer.closeDrawers();
                drawer.openDrawer(GravityCompat.END);
            }
        });
        LLRefer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, RefertofrndActivity.class);
                startActivity(i);
                drawer.closeDrawers();
            }
        });
        LLPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, PrivacypolicyActivity.class);
                startActivity(i);
                drawer.closeDrawers();
            }
        });
        LLUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, TncActivity.class);
                startActivity(i);
                drawer.closeDrawers();
            }
        });
        LLAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, AboutUsActivity.class);
                startActivity(i);
                drawer.closeDrawers();
            }
        });
        LLcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, ContactUsActivity.class);
                startActivity(i);
                drawer.closeDrawers();
            }
        });


    }


  /*  public void GenderSpiner(final String getGender) {
        try {
            ArrayList<String> arraylist = new ArrayList<>();
           *//* ProductList = Spinner_Product_model.getAllProduct();
            for (int i = 0; i < ProductList.size(); i++) {
                arraylist.add(ProductList.get(i).getProductName());
                    }*//*
            ArrayList<String> list = new ArrayList<String>();
            list.add("Female");
            list.add("Male");
            genderSpinner.setAdapter(new CustomSpinnerAdapter(getApplicationContext(), arraylist));
            for (int i = 0; i < arraylist.size(); i++) {
                if (arraylist.get(i).equals(getGender)) {
                    genderSpinner.setSelection(i);
                    break;
                }
            }
        } catch (Exception e) {
            Log.e("", "" + e.getMessage());
        }
    }*/


    public class CustomSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

        private final Context activity;
        private ArrayList<String> asr;

        public CustomSpinnerAdapter(Context context, ArrayList<String> asr) {
            this.asr = asr;
            activity = context;
        }


        public int getCount() {
            return asr.size();
        }

        public Object getItem(int i) {
            return asr.get(i);
        }

        public long getItemId(int i) {
            return (long) i;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView txt = new TextView(ProfileActivity.this);
            txt.setPadding(5, 5, 5, 5);
            txt.setGravity(Gravity.CENTER);
            txt.setText(asr.get(position));
            txt.setTextAppearance(activity, android.R.style.TextAppearance_Medium);
            txt.setTextColor(Color.parseColor("#000000"));
            txt.setBackgroundColor(Color.WHITE);
            //  Typeface custom_font = Typeface.createFromAsset(getAssets(),"assets/JosefinSans-Regular.ttf");
            //   txt.setTypeface(custom_font);
            //   txt.setTypeface(CustomFonts.GothamRegular(getApplicationContext()));
            return txt;
        }

        public View getView(int i, View view, ViewGroup viewgroup) {
            TextView txt = new TextView(ProfileActivity.this);
            txt.setGravity(Gravity.CENTER);
            txt.setPadding(5, 5, 5, 5);
            txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.down_arroww, 0);
            txt.setText(asr.get(i));
            txt.setTextAppearance(activity, android.R.style.TextAppearance_Medium);
            txt.setTextColor(Color.parseColor("#000000"));
            txt.setBackgroundColor(Color.WHITE);
            // Typeface custom_font = Typeface.createFromAsset(getAssets(),"assets/JosefinSans-Regular.ttf");
            // txt.setTypeface(custom_font);

            //     txt.setTypeface(CustomFonts.GothamRegular(getApplicationContext()));
            return txt;
        }
    }


    DatePickerDialog.OnDateSetListener selectedCheckInDate = new DatePickerDialog.OnDateSetListener() {
        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            view.setMaxDate(System.currentTimeMillis());
            myear = selectedYear;
            mmonth = selectedMonth;
            mday = selectedDay;
            updateLabel(myear, mmonth, mday);
        }
    };

    private void updateLabel(int year, int month, int day) {
        String newdateform = String.valueOf(day) + "/" + String.valueOf(mo[month]) + "/" + String.valueOf(year);
        etDOB.setText(newdateform.toString());
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd MMM yyyy");
        Date date = null;


        String year1 = String.valueOf(year);
        String month1 = String.valueOf(month + 1);
        String monthfina;
        if (month1.length() == 1) {
            monthfina = 0 + month1;
        } else {
            monthfina = month1;
        }

        String finalday;

        String day1 = String.valueOf(day);
        if (day1.length() == 1) {
            finalday = 0 + day1;
        } else {
            finalday = day1;
        }

        stringdob = String.valueOf(year) + "-" + String.valueOf(monthfina) + "-" + String.valueOf(finalday);
        try {
            date = inputFormat.parse(newdateform);
            ReminderDateStart = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void UpdateUser() {
        if (Global.isInternetAvail(ProfileActivity.this)) {
            if (checkValidation()) {
                progressDialog.show();
                String url = Constaints.ProfileUpdate;
                progressBar.setVisibility(View.VISIBLE);
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jSONObject = new JSONObject(new String(response));
                            String Status = jSONObject.optString("status");
                            String Path = Constaints.BaseUrl + jSONObject.optString("bannerCdnpath");
                            //  JSONObject jSONinfo = jSONObject.getJSONObject("info");
                            if (Integer.parseInt(Status) == 1) {
                                IsImageSelected = false;
                                JSONObject ProfileInfo = jSONObject.getJSONObject("info");
                                SessionManager.setUserID(getApplicationContext(), ProfileInfo.optString("id").toString());
                                SessionManager.setUserName(getApplicationContext(), ProfileInfo.optString("name").toString());
                                SessionManager.setMobileno(getApplicationContext(), ProfileInfo.optString("phone").toString());
                                SessionManager.setUserEmail(getApplicationContext(), ProfileInfo.optString("email").toString());
                                SessionManager.setUserDOB(getApplicationContext(), ProfileInfo.optString("dob").toString());
                                SessionManager.setUserGender(getApplicationContext(), ProfileInfo.optString("gender").toString());
                                //getGender = ProfileInfo.getJSONObject("gender").toString();
                                imageLoader.displayImage(SessionManager.getImage(getApplicationContext()), userimage);
                                SessionManager.setImage(getApplicationContext(), ProfileInfo.optString("user_dp"));
                                SessionManager.setUserImagePath(getApplicationContext(), Path + "/" + ProfileInfo.optString("user_dp"));
                                Toast.makeText(getApplicationContext(), jSONObject.optString("message").toString(), Toast.LENGTH_LONG).show();
                                progressDialog.show();
                                Intent i = new Intent(ProfileActivity.this, LandingNewActivity.class);
                                startActivity(i);
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), jSONObject.optString("message").toString(), Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Log.e("", e.getMessage());
                            progressBar.setVisibility(View.GONE);
                            progressDialog.dismiss();
                        }
                        progressBar.setVisibility(View.GONE);
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        progressDialog.dismiss();
                        Log.d("", "" + error.getMessage());
                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("XAPIKEY", "XXXXX");
                        params.put("name", etname.getText().toString());
                        params.put("id", SessionManager.getUserID(getApplicationContext()).toString());
                        params.put("email", etemail.getText().toString());
                        params.put("dob", stringdob);
                        params.put("gender", SelectedGender.toString());


                        if (IsImageSelected) {
                            params.put("user_dp", Global.BitmapTobase64(thumbnail_final));
                        } else {
                            userimage.buildDrawingCache();
                            thumbnail_final = userimage.getDrawingCache();
                            params.put("user_dp", Global.BitmapTobase64(thumbnail_final));
                        }
                        return params;
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Content-Type", "application/x-www-form-urlencoded");
                        return params;
                    }
                };
                int socketTimeout = 30000;
                Volley.newRequestQueue(getApplicationContext()).add(request);
                request.setRetryPolicy(new DefaultRetryPolicy(
                        socketTimeout,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            }
        }
    }


    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        IsImageSelected = false;
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        userimage.setImageBitmap(thumbnail);
        // SessionManager.setImage(getApplicationContext(), thumbnail.toString());
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        thumbnail_final = thumbnail;
        File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;

        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        userimage.setImageBitmap(null);
        userimage.setImageBitmap(thumbnail);
        thumbnail_final = thumbnail;
        IsImageSelected = true;

    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        IsImageSelected = false;
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        Bitmap bmp = null;
        try {
            bmp = getBitmapFromUri(selectedImage);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Bitmap resized = Bitmap.createScaledBitmap(bmp, 150, 150, true);
        userimage.setImageBitmap(resized);
        thumbnail_final = resized;
        IsImageSelected = true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //   overridePendingTransition(R.anim.hold, R.anim.fade_in);
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    private boolean checkValidation() {
        boolean ret = true;
        /* etname.setError(null);
        etemail.setError(null);
        etmobile.setError(null);
        etDOB.setError(null);
        if (!RegisValidation.hasText(etUserName)) {
            ret = false;
        } else if (!(Pattern.matches("^[\\p{L} .'-]+$", etUserName.getText().toString()))) {
            etUserName.setError("Please enter valid name");
            ret = false;
        }*/

        if (!RegisValidation.hasText(etname)) {
            //  etCompanyName.setError("Please enter valid Name");
            etname.requestFocus();
            ret = false;
        } else if (!RegisValidation.hasMobile(etmobile)) {
            //   etMobileNumber.setError("Please enter valid mobile number");
            etmobile.requestFocus();
            ret = false;
            return ret;
        } else if (!RegisValidation.isEmailAddress(etemail, true)) {
            //   etMobileNumber.setError("Please enter valid mobile number");
            etemail.requestFocus();
            ret = false;
            return ret;
        }
        return ret;
    }

    @Override
    protected void onResume() {
        super.onResume();
     /*   ivHome.setImageDrawable(getResources().getDrawable(R.drawable.inactive_home));
        ivOrder.setImageDrawable(getResources().getDrawable(R.drawable.inactive_order));
        ivProfile.setImageDrawable(getResources().getDrawable(R.drawable.active_profile));
        ivFavourite.setImageDrawable(getResources().getDrawable(R.drawable.inactive_favorite));
        ivmore.setImageDrawable(getResources().getDrawable(R.drawable.inactive_more));
        tvHome.setTextColor(getResources().getColor(R.color.greyfontcol));
        tvOrder.setTextColor(getResources().getColor(R.color.greyfontcol));
        tvProfile.setTextColor(getResources().getColor(R.color.redcolor));
        tvFavourite.setTextColor(getResources().getColor(R.color.greyfontcol));
        tvmore.setTextColor(getResources().getColor(R.color.greyfontcol));*/

        ivHome.setImageDrawable(getResources().getDrawable(R.drawable.inactive_home));
        ivOrder.setImageDrawable(getResources().getDrawable(R.drawable.inactive_order));
        ivProfile.setImageDrawable(getResources().getDrawable(R.drawable.active_profile));
        ivFavourite.setImageDrawable(getResources().getDrawable(R.drawable.inactive_favorite));
        ivmore.setImageDrawable(getResources().getDrawable(R.drawable.inactive_more));
        tvHome.setTextColor(getResources().getColor(R.color.greyfontcol));
        tvOrder.setTextColor(getResources().getColor(R.color.greyfontcol));
        tvProfile.setTextColor(getResources().getColor(R.color.redcolor));
        tvFavourite.setTextColor(getResources().getColor(R.color.greyfontcol));
        tvmore.setTextColor(getResources().getColor(R.color.greyfontcol));
    }

}