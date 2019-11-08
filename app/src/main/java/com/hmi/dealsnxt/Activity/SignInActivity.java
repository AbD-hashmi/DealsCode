package com.hmi.dealsnxt.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.location.LocationServices;
import com.hmi.dealsnxt.CustomControl.RegexValiations;
import com.hmi.dealsnxt.HelperClass.Constaints;
import com.hmi.dealsnxt.HelperClass.Global;
import com.hmi.dealsnxt.HelperClass.SessionManager;
import com.hmi.dealsnxt.Model.ProfileModel;
import com.hmi.dealsnxt.R;
import com.hmi.dealsnxt.Utils.Common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static com.hmi.dealsnxt.HelperClass.SessionManager.setUserName;

public class SignInActivity extends AppCompatActivity {
    public TextView tvusername, tvTitle;
    public LinearLayout newtoolbar, LLcalender;
    public ImageView imBack;
    public ImageView ivsearch;
    public LinearLayout LLloc;
    public ImageView ivfilter;
    View promptView;
    android.app.AlertDialog alert;
    public TextView tvsignin, tvfblogin;
    public EditText etname, etemail, etgender, etage;
    private CallbackManager callbackManager;
    private TextView textView;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    public LoginButton loginButton;
    public String accessToken;
    private List<ProfileModel> arrayList = new ArrayList<>();
    public static ProgressBar progressBar;
    public static String username = "", useremail = "", usergender = "", dob = "";
    public static int id;
    public EditText etdob;
    public TextView tvcalender;
    static int myear;
    static int mmonth;
    static int mday;
    Spinner spinnergender;
    public String SelectedGender = "";
    public TextView fbbutton;
    static String ReminderDateStart = "";
    String mo[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    public static ProgressDialog progressDialog;
    public String getGender = "";
    String stringdob = "";
    public EditText etcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_sign_in);
        progressDialog = Common.getProgressDialog(SignInActivity.this);
        newtoolbar = (LinearLayout) findViewById(R.id.toolbarnew);
        imBack = (ImageView) newtoolbar.findViewById(R.id.imBack);
        imBack.setVisibility(View.INVISIBLE);
        tvTitle = (TextView) newtoolbar.findViewById(R.id.tvTitle);
        LLloc = (LinearLayout) newtoolbar.findViewById(R.id.LLloc);
        ivfilter = (ImageView) newtoolbar.findViewById(R.id.ivfilter);
        ivsearch = (ImageView) newtoolbar.findViewById(R.id.ivsearch);
        tvusername = (TextView) newtoolbar.findViewById(R.id.tvusername);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        etname = (EditText) findViewById(R.id.etname);
        etemail = (EditText) findViewById(R.id.etemail);
        etgender = (EditText) findViewById(R.id.etgender);
        tvcalender = (TextView) findViewById(R.id.tvcalender);
     //   LLcalender = (LinearLayout) findViewById(R.id.LLcalender);
        etdob = (EditText) findViewById(R.id.etdob);
        tvsignin = (TextView) findViewById(R.id.tvsignin);
        // tvfblogin = (TextView) findViewById(R.id.tvfblogin);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        fbbutton = (TextView) findViewById(R.id.fbbutton);
        spinnergender = (Spinner) findViewById(R.id.spinnergender);

        onload();


        etname.setText(SessionManager.getUserName(getApplicationContext()));
        etemail.setText(SessionManager.getUserEmail(getApplicationContext()));
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {

            }
        };
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                // displayMessage(newProfile);
            }
        };
        accessTokenTracker.startTracking();
        profileTracker.startTracking();
        // loginButton.setReadPermissions(Arrays.asList("public_profile", "user_birthday"));
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_status", "user_likes", "user_location", "user_birthday", "user_friends"));
        // "public_profile", "email", "user_status", "user_likes", "user_location", "user_birthday", "user_friends"



        fbbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginButton.performClick();

            }
        });
        loginButton.registerCallback(callbackManager, callback);

        ArrayList<String> list = new ArrayList<String>();
        list.add("Male");
        list.add("Female");

        spinnergender.setAdapter(new ArrayAdapter<String >(SignInActivity.this,R.layout.list_items_gender, list));
        spinnergender.setSelection(0);

        spinnergender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View v, int position, long id) {
                SelectedGender = adapter.getItemAtPosition(position).toString();
                //   Toast.makeText(getApplicationContext(), "Selected Gender :" + gender, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
    }


    public void onload() {
        LLloc.setVisibility(View.GONE);
        imBack.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);
        ivsearch.setVisibility(View.GONE);
        tvusername.setVisibility(View.GONE);
        tvTitle.setText("Sign Up");
        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        etname.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i("Log_tag", "Input Value" + s.toString());
                String first = "";
                if (s.length() > 0) {
                    if (!(Pattern.matches("^[\\p{L} .'-]+$", etname.getText().toString()))) {
                        etname.setError("Special Characters and Numerical values are not allowed");

                    }


                } else {
                }
                SessionManager.setUserName(getApplicationContext(), etname.getText().toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        etgender.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        etemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i("Log_tag", "Input Value" + s.toString());
                String first = "";
                if (s.length() > 0) {
                  /* if (!RegexValiations.isEmailAddress(etemail, true)) {
                      //  etemail.setError("Please enter valid e-mail id");
                        }*/
                } else {
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        etdob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                myear = c.get(Calendar.YEAR);
                mmonth = c.get(Calendar.MONTH);
                mday = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog currentDate = new DatePickerDialog(SignInActivity.this, selectedCheckInDate, myear, mmonth, mday);
                //   currentDate.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                //  currentDate.getDatePicker().setMaxDate((System.currentTimeMillis() - 1000) + (1000 * 60 * 60 * 24 * 7));
                currentDate.getDatePicker().setMaxDate(c.getTimeInMillis());
                currentDate.show();
                //      currentDate.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                //    currentDate.getDatePicker().setMaxDate((System.currentTimeMillis() - 1000) + (1000 * 60 * 60 * 24 * 7));
            }
        });

        tvsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidation()) {
                    progressDialog.show();
                    Common.hideKeyboard(v, SignInActivity.this);
                    if (Global.isInternetAvail(SignInActivity.this)) {
                        String url = Constaints.ProfileUpdate;
                        //   progressBar.setVisibility(View.VISIBLE);
                        StringRequest request = new StringRequest(Request.Method.POST,
                                url,
                                new Response.Listener<String>() {

                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            Log.e("profile update resp", response);
                                            JSONObject jSONObject = new JSONObject(new String(response));
                                            // JSONObject jSONObject = new JSONObject("{\"status\": 1,\"cdnpath\": \"uploads/usersdp/\",\"message\": \"Your account has been updated successfully !\",\"info\": {\"id\": \"4\",\"user_type\": \"Doctor\",\"user_name\": \"Dr. Richard\",\"account_key\": \"\", \"doctor_reference_key\": \"2\",\"user_mobile\": \"9999999999\",\"user_dp\": \"https://s3.amazonaws.com/uploads.hipchat.com/57414/3406939/ZOVhui1QUme3G5l/c1f3.png\",\"user_wallet_points\": \"0\",\"about_us\": \"Dr.Richard, senior consultant Pediatrics and Neonatology. Dr. Mangala Pawar personally sees all pediatric cases and has a good team of super specialties for patients with complex diseases. Dr. Mangala Pawar is a senior consultant Paediatrics and Neonatology at Fortis Memorial Research Institute, Gurgaon (FMRI). Dr. Mangala Pawar is a senior consultant at leading Hospitals in Washington DC and Maryland, USA. She has also practiced as a senior consultant at Apollo Hospital, Chennai.\",\"qualification\": \"MBBS , MD\",\"work_area\": \"Paediatrics\",\"pin\": \"110001\",\"status\": \"0\",\"created_at\": \"2017-04-21 12:11:11\",\"updated_at\": \"2017-05-03 10:49:41\",\"in_notifications\": \"0\",\"is_confirmed\": \"1\",\"is_deleted\": \"1\",\"user_email\":\"dr_rechard@gmail.com\",\"address1\": \"Gurgaon Sector 50, Gurgaon\",\"address2\": \"The Close South ,Tower 12, Flat 801, Landmark : Near Unitech Business Zone, Gurgaon\",\"kids\":\"2\"}}");
                                            //{"status":1,"bannerCdnpath":"application\/public\/uploads\/users","message":"Your Account Has Been Updated ","info":{"id":59,"role_id":"3","name":"Kul","email":"g@gmail.com","phone":"9812273583","merchant_commission_percentage":"","status":"1","otp":"0","user_dp":null,"created_at":"2017-12-14 15:12:58","updated_at":"2017-12-14 15:25:15","dob":"2017-12-06","gender":"Female"}}
                                            String Status = jSONObject.optString("status");
                                            //  String msg = jSONObject.optString("message");
                                            if (Integer.parseInt(Status) == 1) {
                                                //     Toast.makeText(getApplicationContext(), jSONObject.getString("You are successfully registered").toString(), Toast.LENGTH_SHORT).show();
                                                SessionManager.setIsRegistered(getApplicationContext(), true);
                                                SessionManager.setIssignup(getApplicationContext(), true);
                                                //  etname
                                                //       JSONArray jsonArray=jSONObject.optJSONArray("info");
                                                JSONObject jsonOinfo = jSONObject.optJSONObject("info");
                                                String email = jsonOinfo.optString("email");
                                                String uid = jsonOinfo.optString("id");
                                                String name = jsonOinfo.optString("name");
                                                String mobile = jsonOinfo.optString("phone");
                                                SessionManager.setMobileno(getApplicationContext(), mobile);
                                                SessionManager.setUserID(getApplicationContext(), uid);
                                                SessionManager.setUserName(getApplicationContext(), name);
                                                SessionManager.setUserEmail(getApplicationContext(), jsonOinfo.optString("email").toString());
                                                SessionManager.setUserDOB(getApplicationContext(), jsonOinfo.optString("dob").toString());
                                                SessionManager.setUserGender(getApplicationContext(), jsonOinfo.optString("gender").toString());
                                                SessionManager.setIssignup(getApplicationContext(), true);

                                               /* LayoutInflater layoutInflater = LayoutInflater.from(SignInActivity.this);
                                                promptView = layoutInflater.inflate(R.layout.activity_share, null);
                                                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(SignInActivity.this);
                                                alertDialogBuilder.setView(promptView);
                                                alertDialogBuilder.setCancelable(false);
                                                alert = alertDialogBuilder.create();
                                                alert.show();
                                                etcode = (EditText) promptView.findViewById(R.id.etcode);
                                                final TextView tvagree = (TextView) promptView.findViewById(R.id.tvagree);
                                                final TextView Skip = (TextView) promptView.findViewById(R.id.Skip);
                                                Skip.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        finish();
                                                    }
                                                });
                                                tvagree.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        if (checkValidation_share()) {
                                                            if (Global.isInternetAvail(SignInActivity.this)) {
                                                                SessionManager.setIssignup(getApplicationContext(), true);
                                                                Intent i = new Intent(SignInActivity.this, LocationActivity.class);
                                                                startActivity(i);
                                                                finish();
                                                            } else {
                                                                Toast.makeText(getApplicationContext(), R.string.ConnectionErrorResponse, Toast.LENGTH_LONG).show();
                                                            }
                                                        }
                                                    }
                                                });
*/
                                                Intent i = new Intent(SignInActivity.this, ReferCodeActivity.class);
                                                startActivity(i);
                                                finish();

                                            } else {
                                                Toast.makeText(getApplicationContext(), jSONObject.getString("message").toString(), Toast.LENGTH_SHORT).show();
                                            }
                                            progressDialog.dismiss();
                                        } catch (Exception e) {
                                            Log.e("", e.getMessage());
                                            progressDialog.dismiss();
                                        }
                                        //    progressBar.setVisibility(View.GONE);
                                        progressDialog.dismiss();
                                    }
                                }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressBar.setVisibility(View.GONE);
                                progressDialog.dismiss();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<String, String>();
                                SessionManager.setUserName(getApplicationContext(), etname.getText().toString());
                                params.put("XAPIKEY", "XXXXX");
                                params.put("name", etname.getText().toString());
                                params.put("id", SessionManager.getUserID(getApplicationContext()));
                                params.put("email", etemail.getText().toString());
                                params.put("dob", etdob.getText().toString());
                                params.put("gender", spinnergender.getSelectedItem().toString());
                                System.out.println("params "+params);
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
                    } else {
                        Toast.makeText(SignInActivity.this, R.string.ConnectionErrorResponse, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
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
        etdob.setText(newdateform.toString());
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
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd MMM yyyy");
        Date date = null;
        try {
            date = inputFormat.parse(newdateform);
            ReminderDateStart = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


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
            TextView txt = new TextView(SignInActivity.this);
            txt.setPadding(20, 5, 0, 20);
            txt.setGravity(Gravity.LEFT);
            txt.setText(asr.get(position));
            //  txt.setTextAppearance(activity, android.R.style.TextAppearance_Medium);
            txt.setTextColor(Color.parseColor("#000000"));
            txt.setTextSize(16);
            txt.setBackgroundColor(Color.WHITE);
            //  Typeface custom_font = Typeface.createFromAsset(getAssets(),"assets/JosefinSans-Regular.ttf");
            //   txt.setTypeface(custom_font);
            //   txt.setTypeface(CustomFonts.GothamRegular(getApplicationContext()));
            return txt;
        }

        public View getView(int i, View view, ViewGroup viewgroup) {
            TextView txt = new TextView(SignInActivity.this);
            txt.setGravity(Gravity.LEFT);
            txt.setPadding(20, 5, 0, 20);
            txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.down_arroww, 0);
            txt.setText(asr.get(i));
            // txt.setTextAppearance(activity, android.R.style.TextAppearance_Medium);
            txt.setTextSize(16);
            txt.setTextColor(Color.parseColor("#000000"));
            txt.setBackgroundColor(Color.WHITE);
            // Typeface custom_font = Typeface.createFromAsset(getAssets(),"assets/JosefinSans-Regular.ttf");
            // txt.setTypeface(custom_font);

            //     txt.setTypeface(CustomFonts.GothamRegular(getApplicationContext()));
            return txt;
        }
    }


    public boolean checkValidation() {
        boolean ret = true;
        etname.setError(null);
        etemail.setError(null);
        //   etgender.setError(null);
        etdob.setError(null);
        if (etname.getText().toString().isEmpty()) {
            etname.setError("Please enter your full name");
            ret = false;
        } else if (!(Pattern.matches("^[\\p{L} .'-]+$", etname.getText().toString()))) {
            etname.setError("Special Chracters and Numerical values are not allowed");
            ret = false;
        } else if (etemail.getText().toString().isEmpty()) {
            etemail.setError("Please enter your valid Email Address");
            ret = false;
        } else if (!RegexValiations.isEmailAddress(etemail, true)) {
            etemail.setError("Please enter your valid Email Address");
            ret = false;
        } else if (SelectedGender.toString().equals("Select Gender")) {
            Toast.makeText(getApplicationContext(), "Please select Gender", Toast.LENGTH_LONG).show();
            ret = false;
        } else if (etdob.getText().toString().isEmpty()) {
            etdob.setError("Please enter your Date of Birth");

            ret = false;
        }

        return ret;
    }

    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            //  AccessToken accessToken = loginResult.getAccessToken();
            accessToken = loginResult.getAccessToken().getToken();
            Log.i("accessToken", accessToken);
            //   Profile profile = Profile.getCurrentProfile();
            //    displayMessage(profile);
            GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    Log.i("LoginActivity", response.toString());
                    // Get facebook data from login
                    //  Bundle bFacebookData = getFacebookData(object);
                    // ProfileUpdateforfb();
                    try {
                        String username = object.getString("first_name") + " " + object.getString("last_name");
                        String useremail = object.optString("email");
                        String usergender = object.getString("gender");
                        setUserName(getApplicationContext(), object.getString("first_name"));
                        ProfileUpdateforfb(username, useremail, usergender);
                    } catch (JSONException e) {
                        // Log.d(TAG,"Error parsing JSON");
                        e.printStackTrace();
                    }
                }
            });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
            request.setParameters(parameters);
            request.executeAsync();
            //     ProfileUpdateforfb();
        }

        @Override
        public void onCancel() {
            //  Log.d(TAG, "Login attempt cancelled.");
        }

        @Override
        public void onError(FacebookException e) {
            e.printStackTrace();
            //   Log.d(TAG, "Login attempt failed.");
            //    deleteAccessToken();
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    private void displayMessage(Profile profile) {
        if (profile != null) {
            //   textView.setText(profile.getName());
            //  Toast.makeText(getApplicationContext(), profile.getName() + "", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

    @Override
    public void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
        //  displayMessage(profile);
    }

    private Bundle getFacebookData(JSONObject object) {

        try {
            Bundle bundle = new Bundle();
            String id = object.getString("id");
            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            //   Toast.makeText(getApplicationContext(), object.getString("first_name"), Toast.LENGTH_LONG).show();
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            //   Toast.makeText(getApplicationContext(), object.getString("last_name") + "", Toast.LENGTH_LONG).show();
            String username = object.getString("first_name") + " " + object.getString("last_name");
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            //   Toast.makeText(getApplicationContext(), object.getString("email") + "", Toast.LENGTH_LONG).show();
            String useremail = object.getString("email");
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));
            //   Toast.makeText(getApplicationContext(), object.getString("gender"), Toast.LENGTH_LONG).show();
            String usergender = object.getString("gender");
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));
            //    Toast.makeText(getApplicationContext(), object.getString("birthday"), Toast.LENGTH_LONG).show();

            ProfileUpdateforfb(username, useremail, usergender);
            return bundle;
        } catch (JSONException e) {
            // Log.d(TAG,"Error parsing JSON");
            e.printStackTrace();
        }
        return null;
    }

    public void ProfileUpdateforfb(final String name, final String email, final String gender) {
        String url = Constaints.ProfileUpdate;
        //  progressBar.setVisibility(View.VISIBLE);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    Log.e("profile update resp", response);
                    JSONObject jSONObject = new JSONObject(new String(response));
                    // JSONObject jSONObject = new JSONObject("{\"status\": 1,\"cdnpath\": \"uploads/usersdp/\",\"message\": \"Your account has been updated successfully !\",\"info\": {\"id\": \"4\",\"user_type\": \"Doctor\",\"user_name\": \"Dr. Richard\",\"account_key\": \"\", \"doctor_reference_key\": \"2\",\"user_mobile\": \"9999999999\",\"user_dp\": \"https://s3.amazonaws.com/uploads.hipchat.com/57414/3406939/ZOVhui1QUme3G5l/c1f3.png\",\"user_wallet_points\": \"0\",\"about_us\": \"Dr.Richard, senior consultant Pediatrics and Neonatology. Dr. Mangala Pawar personally sees all pediatric cases and has a good team of super specialties for patients with complex diseases. Dr. Mangala Pawar is a senior consultant Paediatrics and Neonatology at Fortis Memorial Research Institute, Gurgaon (FMRI). Dr. Mangala Pawar is a senior consultant at leading Hospitals in Washington DC and Maryland, USA. She has also practiced as a senior consultant at Apollo Hospital, Chennai.\",\"qualification\": \"MBBS , MD\",\"work_area\": \"Paediatrics\",\"pin\": \"110001\",\"status\": \"0\",\"created_at\": \"2017-04-21 12:11:11\",\"updated_at\": \"2017-05-03 10:49:41\",\"in_notifications\": \"0\",\"is_confirmed\": \"1\",\"is_deleted\": \"1\",\"user_email\":\"dr_rechard@gmail.com\",\"address1\": \"Gurgaon Sector 50, Gurgaon\",\"address2\": \"The Close South ,Tower 12, Flat 801, Landmark : Near Unitech Business Zone, Gurgaon\",\"kids\":\"2\"}}");
                    String Status = jSONObject.optString("status");
                    String msg = jSONObject.optString("message");
                    if (Integer.parseInt(Status) == 1) {
                        SessionManager.setIsRegistered(getApplicationContext(), true);
                        //           Toast.makeText(getApplicationContext(), jSONObject.getString("You are successfully registered").toString(), Toast.LENGTH_SHORT).show();
                        //
                        //       JSONArray jsonArray=jSONObject.optJSONArray("info");
                        JSONObject jsonOinfo = jSONObject.optJSONObject("info");
                        String email = jsonOinfo.optString("email");
                        String uid = jsonOinfo.optString("id");
                        String name = jsonOinfo.optString("name");
                        String mobile = jsonOinfo.optString("phone");
                        SessionManager.setMobileno(getApplicationContext(), mobile);
                        SessionManager.setUserID(getApplicationContext(), uid);
                        SessionManager.setUserName(getApplicationContext(), name);
                        SessionManager.setUserEmail(getApplicationContext(), jsonOinfo.optString("email").toString());
                        SessionManager.setUserDOB(getApplicationContext(), jsonOinfo.optString("dob").toString());
                        SessionManager.setUserGender(getApplicationContext(), jsonOinfo.optString("gender").toString());
                        SessionManager.setIssignup(getApplicationContext(), true);
                        Intent i = new Intent(SignInActivity.this, ReferCodeActivity.class);
                        startActivity(i);

                     /*   LayoutInflater layoutInflater = LayoutInflater.from(SignInActivity.this);
                        promptView = layoutInflater.inflate(R.layout.activity_share, null);
                        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(SignInActivity.this);
                        alertDialogBuilder.setView(promptView);
                        alertDialogBuilder.setCancelable(false);
                        alert = alertDialogBuilder.create();
                        alert.show();
                        etcode = (EditText) promptView.findViewById(R.id.etcode);
                        final TextView tvagree = (TextView) promptView.findViewById(R.id.tvagree);
                        final TextView Skip = (TextView) promptView.findViewById(R.id.Skip);
                        Skip.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        });
                        tvagree.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (checkValidation_share()) {
                                    if (Global.isInternetAvail(SignInActivity.this)) {
                                        Intent i = new Intent(SignInActivity.this, LocationActivity.class);
                                        startActivity(i);
                                        SessionManager.setIssignup(getApplicationContext(), true);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), R.string.ConnectionErrorResponse, Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        });*/

                    } else {
                        Toast.makeText(getApplicationContext(), jSONObject.getString("message").toString(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e("", e.getMessage());
                }
                progressBar.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("XAPIKEY", "XXXXX");
                params.put("name", name);
                params.put("id", SessionManager.getUserID(getApplicationContext()));
                // params.put("id", SessionManager.getUserID(getApplicationContext()));
                params.put("email", email);
                params.put("dob", "");
                params.put("gender", gender);
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

    public boolean checkValidation_share() {
        boolean ret = true;
        etcode.setError(null);
        if (etcode.getText().toString().equals("")) {
            etcode.setError("Please provide Refer Code");
            ret = false;
        }
        return ret;
    }


}
