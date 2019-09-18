package com.hmi.dealsnxt.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hmi.dealsnxt.HelperClass.Constaints;
import com.hmi.dealsnxt.HelperClass.Global;
import com.hmi.dealsnxt.HelperClass.RegisValidation;
import com.hmi.dealsnxt.HelperClass.SessionManager;
import com.hmi.dealsnxt.R;
import com.hmi.dealsnxt.Services.OTPInboxMessageReceiver;
import com.hmi.dealsnxt.Utils.Common;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.ACCESS_WIFI_STATE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.RECEIVE_SMS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class OTPActivity extends AppCompatActivity {

    FloatingActionButton tvagree;
    public EditText etmobno, etcode, etotp;
    CheckBox chkselect;
    LinearLayout linearLayout;
    android.app.AlertDialog alert;
    public String DeviceOSVersion = null;
    RelativeLayout relativeLayout;
    Spinner spinner;
    TextView tvtnc, tvprivacy;
    public ResponseReceiver receiver;
    public static final String MainPP_SP = "MainPP_data";
    public static final int R_PERM = 2822;
    private static final int REQUEST = 112;

    Context mContext = this;
    ProgressBar progress_bar;
    private static final int PERMISSION_REQUEST_CODE = 200;
    public static ProgressDialog progressDialog;

    public class ResponseReceiver extends BroadcastReceiver {
        public static final String ACTION_RESP = "com.starstar.dial.OTP_MESSAGE";
        @Override
        public void onReceive(Context context, Intent intent) {

            String OTP = OTPInboxMessageReceiver.OTP;
            if (OTP != null) {
                etotp.setText(OTP);
                otpverification();
            }
        }
    }

    String[] permissionsRequired = new String[]{RECEIVE_SMS};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_otp);
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        progress_bar.setVisibility(View.GONE);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_top);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        linearLayout=(LinearLayout)findViewById(R.id.linearLayout);
        relativeLayout=(RelativeLayout) findViewById(R.id.relative_layout);
        spinner=(Spinner)findViewById(R.id.spinner);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        if (!checkPermission()) {
            requestPermission();
        } else {
        }
        progressDialog = Common.getProgressDialog(OTPActivity.this);
        DeviceOSVersion = android.os.Build.VERSION.RELEASE;
        tvagree = (FloatingActionButton) findViewById(R.id.tvagree);
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        //  progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        etmobno = (EditText) findViewById(R.id.etmobno);
        etcode = (EditText) findViewById(R.id.etcode);
        chkselect = (CheckBox) findViewById(R.id.chkselect);
        chkselect.setChecked(false);
        tvtnc = (TextView) findViewById(R.id.tvtnc);
        tvprivacy = (TextView) findViewById(R.id.tvprivacy);


        tvprivacy.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "Privacy Policy";
        tvprivacy.setText(Html.fromHtml(text));
        tvprivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OTPActivity.this,PrivacypolicyActivity.class));
            }
        });
        tvtnc.setMovementMethod(LinkMovementMethod.getInstance());
        String text2 = "Terms of Use";
        tvtnc.setText(Html.fromHtml(text2));

        tvtnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OTPActivity.this,TncActivity.class));
            }
        });

        tvagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidation()) {
                    if (Global.isInternetAvail(OTPActivity.this)) {
                        Common.hideKeyboard(v, OTPActivity.this);

                        UserRegistration();
                    } else {
                        Toast.makeText(OTPActivity.this, R.string.ConnectionErrorResponse, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public void UserRegistration() {
        String url = Constaints.UserRegistrationURL;

        linearLayout.setVisibility(View.GONE);

        progressDialog.show();
        relativeLayout.setVisibility(View.VISIBLE);

        final Animation bottomUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bottom_down);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jSONObject = new JSONObject(new String(response));
                    int Status = jSONObject.optInt("status");
                    progress_bar.setVisibility(View.GONE);
                    progressDialog.dismiss();
                    if (Status == 1) {
                        SessionManager.setUserID(getApplicationContext(), jSONObject.optString("id"));
                        SessionManager.setMobileno(getApplicationContext(), jSONObject.optString("phone"));

                        final Handler handler = new Handler();
                        final TextView tvnmobile = (TextView) findViewById(R.id.tvnmobile);
                        etotp = (EditText) findViewById(R.id.etotp);
                        final FloatingActionButton tverify = (FloatingActionButton) findViewById(R.id.tverify);
                        final ImageView image = (ImageView) findViewById(R.id.image);
                        tvnmobile.setText("+91" + " " + etmobno.getText().toString());

                        //  etotp.setText(OTP.toString());


                        final Runnable r = new Runnable() {
                            public void run() {
                                image.startAnimation(bottomUp);
                                bottomUp.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {}
                                    @Override
                                    public void onAnimationEnd(Animation animation) { }
                                    @Override
                                    public void onAnimationRepeat(Animation animation) { }
                                });
                            }
                        };
                        handler.postDelayed(r, 0);

                        tverify.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // alert.dismiss();
                               /* if (alert.isShowing()) {
                                    alert.dismiss();
                                    finish();
                                    SessionManager.setIsRegistered(getApplicationContext(), true);
                                    Intent i = new Intent(OTPActivity.this, Dashboard.class);
                                    startActivity(i);
                                }*/
                                if (checkValidation_new()) {
                                    if (Global.isInternetAvail(OTPActivity.this)) {
                                        otpverification();
                                    } else {
                                        Toast.makeText(getApplicationContext(), R.string.ConnectionErrorResponse, Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        });
                    }

                } catch (Exception e) {
                    Log.e("", e.getMessage());

                    System.out.println("   ooppop"+e.getMessage());
                    Toast.makeText(OTPActivity.this, R.string.ConnectionErrorResponse, Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
                progress_bar.setVisibility(View.GONE);
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                   Log.e("",""+error);
                progress_bar.setVisibility(View.GONE);
                progressDialog.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("XAPIKEY", "XXXXX");
                params.put("phone", etmobno.getText().toString());
                params.put("userType", "User");

                // params.put("device_imei", SessionManager.getDeviceIMEI(RegistrationActivity.this));
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

    public void otpverification() {
        String url = Constaints.OTPverify;
        progressDialog.show();
        //    final Animation bottomUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bottom_down);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jSONObject = new JSONObject(new String(response));
                    int Status = jSONObject.optInt("status");
                    if (Status == 1) {
                        //{"status":1,"message":"","info":[{"id":29,"role_id":"3","name":"Hh","email":"sd@gmail.com","phone":"9812273583","merchant_commission_percentage":"","status":"1","otp":"0","user_dp":null,"created_at":"2017-11-28 13:44:25","updated_at":"2017-12-11 12:06:28","dob":"0000-00-00","gender":"Male"}]}
                        //{"status":1,"message":"","info":[{"id":41,"role_id":"3","name":"","email":"","phone":"9812273583","merchant_commission_percentage":"","status":"1","otp":"0","user_dp":null,"created_at":"2017-12-13 18:48:53","updated_at":"2017-12-13 18:49:03","dob":null,"gender":""}]}
                       // alert.dismiss();
                        JSONArray jsonArray = jSONObject.optJSONArray("info");
                        JSONObject jsonObject = jsonArray.optJSONObject(0);
                        String email = jsonObject.optString("email");
                        String uid = jsonObject.optString("id");
                        String name = jsonObject.optString("name");
                        String mobile = jsonObject.optString("phone");
                        SessionManager.setMobileno(getApplicationContext(), mobile);
                        SessionManager.setUserID(getApplicationContext(), uid);
                        SessionManager.setUserName(getApplicationContext(), name);
                        SessionManager.setUserImagePath(getApplicationContext(), "http://logiquebrainexaminer.com/deals_nxt/application/public/uploads/users" + "/" + jsonObject.optString("user_dp"));
                        SessionManager.setUserEmail(getApplicationContext(), jsonObject.optString("email").toString());
                        SessionManager.setUserDOB(getApplicationContext(), jsonObject.optString("dob").toString());
                        SessionManager.setUserGender(getApplicationContext(), jsonObject.optString("gender").toString());
                        SessionManager.setIsotp(getApplicationContext(), true);

                        if (email.equals("")) {
                            SessionManager.setIsotp(getApplicationContext(), true);
                            SessionManager.setMobileno(getApplicationContext(), etotp.getText().toString());
                            Intent i = new Intent(OTPActivity.this, SignInActivity.class);
                            startActivity(i);
                            finish();
                        } else {

                            SessionManager.setIssignup(getApplicationContext(), true);
                            Intent i = new Intent(OTPActivity.this, LocationActivity.class);
                            startActivity(i);
                            finish();
                        }
                        progressDialog.dismiss();
                    } else {
                        Intent i = new Intent(OTPActivity.this, SignInActivity.class);
                        startActivity(i);
                        finish();

                      //  Toast.makeText(OTPActivity.this, jSONObject.optString("message"), Toast.LENGTH_LONG).show();
                        progressDialog.show();
                    }
                } catch (Exception e) {
                    Log.e("", e.getMessage());
                    Toast.makeText(OTPActivity.this, R.string.ConnectionErrorResponse, Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
                progress_bar.setVisibility(View.GONE);
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress_bar.setVisibility(View.GONE);

                System.out.println("   ooppop"+error.getMessage());
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("XAPIKEY", "XXXXX");
                params.put("phone", SessionManager.getMobileno(getApplicationContext()));
                params.put("id", SessionManager.getUserID(getApplicationContext()));
                params.put("otp", etotp.getText().toString());
                params.put("device_fcm_token", "123333");
                params.put("os_version", DeviceOSVersion);
                params.put("device_os", "ANDROID");
                params.put("device_imei", SessionManager.getDeviceIMEI(OTPActivity.this));
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


    public boolean checkValidation() {
        boolean ret = true;

        etcode.setError(null);
        if (!etmobno.getText().toString().equals("")) {
            if (!RegisValidation.hasMobile(etmobno)) {
                ret = false;
            }
        } else {
            //   etmobno.setError(null);
            etmobno.setError("Please enter your mobile number");
            ret = false;
        }/*else if (!RegisValidation.hasText(etcode)) {
            etcode.requestFocus();
            ret = false;
        }*/
        return ret;
    }

    public boolean checkValidation_new() {
        boolean ret = true;


        if (!etotp.getText().toString().equals("")) {
            if (!RegisValidation.hasNumber(etotp)) {
                ret = false;
            }
        } else {
            etotp.setError("Please provide OTP number");
            ret = false;
        }
        return ret;
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(ResponseReceiver.ACTION_RESP);
        receiver = new ResponseReceiver();
        registerReceiver(receiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private boolean checkPermission() {

        int result9 = ContextCompat.checkSelfPermission(getApplicationContext(), RECEIVE_SMS);

        return result9 == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean readphoneAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;


                    if (readphoneAccepted) {
                    } else {

                    }
                }
                break;
        }
    }

    private void requestPermission() {

       /* ActivityCompat.requestPermissions(this, new String[]{ READ_PHONE_STATE
               }, PERMISSION_REQUEST_CODE);*/


        ActivityCompat.requestPermissions(this, permissionsRequired, PERMISSION_REQUEST_CODE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        linearLayout.setVisibility(View.VISIBLE);
        relativeLayout.setVisibility(View.GONE);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (linearLayout.getVisibility()==View.VISIBLE){
            super.onBackPressed();
        }
        else {
            linearLayout.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.GONE);
            return;
        }
    }

    /**
     * Making notification bar transparent
     */


    private void changeStatusBarColor() {

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
    }


}
