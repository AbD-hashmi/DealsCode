package com.hmi.dealsnxt.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hmi.dealsnxt.Adaptor.DealDetailsAdaptor;
import com.hmi.dealsnxt.Adaptor.OutletsDeallistAdaptor;
import com.hmi.dealsnxt.Adaptor.SearchOutletAdapter;
import com.hmi.dealsnxt.HelperClass.Constaints;
import com.hmi.dealsnxt.HelperClass.SessionManager;
import com.hmi.dealsnxt.Model.DealDetailsModel;
import com.hmi.dealsnxt.Model.HotDealsModel;
import com.hmi.dealsnxt.Model.SearchModel;
import com.hmi.dealsnxt.R;
import com.hmi.dealsnxt.Utils.Common;
import com.hmi.dealsnxt.Utils.Customutils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import net.glxn.qrgen.android.QRCode;
import net.glxn.qrgen.core.scheme.VCard;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SingleOrderActivity extends AppCompatActivity implements PaymentResultListener {
    TextView tvlike, tvoutletname, tvoutletdistncekm, tvoutletaddress, tvstartime, tvendtime, tvtncdetail, tvdealdetail, tvshowextra;
    TextView tvdealaddress, tvactualprice, tvfinalamount, tvbuy, tvTitle, tvdetail;
    LinearLayout LLlist, LLtnc, LLdeal;
    RelativeLayout RLdealdata, LLrember, RLlocation, RLAmount, RLtoolbar_new;
    ImageView ivdeal, ivshare, ivlikecount, ivgift, imBack;
    View viewline;
    RecyclerView viewlist;
    ProgressBar progressBar;
    ViewPager view_pager;
    TextView tvtotalamount, tvdesc, tvaddcart, tvpayment;
    RelativeLayout LLpayment;
    CheckBox checkBox;

    ArrayList<DealDetailsModel> arrayList = new ArrayList<DealDetailsModel>();
    public String Qtycount = "";
    com.nostra13.universalimageloader.core.ImageLoader imageLoader;
    DisplayImageOptions options;
    public String SelectedDealId = "";
    public String Dealprice = "";
    public String Outlet_Id = "";
    public String Merhant_Id = "";
    public String Deal_Title = "";
    public String path = "";
    public String Dealimgname = "";
    public String Dealstarttime = "";
    public String Dealendtime = "";
    public static RecyclerView.Adapter recyleaAdpter;
    public String order_id = "";
    android.app.AlertDialog alert;
    View promptView;
    JSONObject parent;
    ProgressDialog progressDialog;
    EditText coupen_edit;
    TextView apply_now;
    int coupen_id;
    double max_discount;
    String coupon="0";
    TextView tnc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singledealorder);
        RLdealdata = (RelativeLayout) findViewById(R.id.RLdealdata);
        tvlike = (TextView) findViewById(R.id.tvlike);
        coupen_edit= (EditText) findViewById(R.id.coupen_edit);
        apply_now= (TextView) findViewById(R.id.apply_now);
        ivdeal = (ImageView) findViewById(R.id.ivdeal);
        ivshare = (ImageView) findViewById(R.id.ivshare);
        ivlikecount = (ImageView) findViewById(R.id.ivlikecount);
        tvoutletname = (TextView) findViewById(R.id.tvoutletname);
        tvoutletdistncekm = (TextView) findViewById(R.id.tvoutletdistncekm);
        ivgift = (ImageView) findViewById(R.id.ivgift);
        tvoutletaddress = (TextView) findViewById(R.id.tvoutletaddress);
        tvstartime = (TextView) findViewById(R.id.tvstartime);
        tvendtime = (TextView) findViewById(R.id.tvendtime);
        LLlist = (LinearLayout) findViewById(R.id.LLlist);
        viewlist = (RecyclerView) findViewById(R.id.viewlist);
        LLtnc = (LinearLayout) findViewById(R.id.LLtnc);
        LLdeal = (LinearLayout) findViewById(R.id.LLdeal);
        tvdealdetail = (TextView) findViewById(R.id.tvdealdetail);
        LLrember = (RelativeLayout) findViewById(R.id.LLrember);
        tvshowextra = (TextView) findViewById(R.id.tvshowextra);
        RLlocation = (RelativeLayout) findViewById(R.id.RLlocation);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        imBack = (ImageView) findViewById(R.id.imBack);
        RLAmount = (RelativeLayout) findViewById(R.id.RLlocation);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tvtotalamount = (TextView) findViewById(R.id.tvtotalamount);
        LLpayment = (RelativeLayout) findViewById(R.id.LLpayment);
        tvaddcart = (TextView) findViewById(R.id.tvaddcart);
        tnc=(TextView)findViewById(R.id.tnc);
        checkBox=(CheckBox)findViewById(R.id.checkbox);
        tvpayment = (TextView) findViewById(R.id.tvpayment);
        progressDialog= Common.getProgressDialog(SingleOrderActivity.this);

        options = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .showImageOnLoading(R.drawable.banner).showImageForEmptyUri(R.drawable.banner).showImageOnFail(R.drawable.banner)
                .build();
        imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(SingleOrderActivity.this));

        tvoutletname.setText(HotDealsModel.getHotDealsModel().getOutletName());
        Outlet_Id = HotDealsModel.getHotDealsModel().getOutletid();

        Merhant_Id = HotDealsModel.getHotDealsModel().getMerchantid();
        Deal_Title = HotDealsModel.getHotDealsModel().getDealTitle();
        tvoutletaddress.setText(HotDealsModel.getHotDealsModel().getOutletAddress() + ", " + HotDealsModel.getHotDealsModel().getOutletCity() + ", " + HotDealsModel.getHotDealsModel().getOutletzipcode());
        tvTitle.setText(HotDealsModel.getHotDealsModel().getOutletName());
        tnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SingleOrderActivity.this,TncActivity.class));
            }
        });

        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Dealprice = String.valueOf(getIntent().getIntExtra("Fromdiscount", 0));
        Qtycount = String.valueOf(getIntent().getIntExtra("Qtycount", 0));
        SelectedDealId = String.valueOf(getIntent().getIntExtra("Dealid", 0));
        Dealimgname = String.valueOf(getIntent().getStringExtra("Dealimgname"));
        Dealstarttime = String.valueOf(getIntent().getStringExtra("Dealstarttime"));
        Dealendtime = String.valueOf(getIntent().getStringExtra("Dealendtime"));
        path=String.valueOf(getIntent().getStringExtra("path"));


        tvstartime.setText(Customutils.dateFormat(Dealstarttime));
        tvendtime.setText(Customutils.dateFormat(Dealendtime));
        tvtotalamount.setText("\u20B9" + Dealprice);
        imageLoader.displayImage(OutletsDeallistAdaptor.Dealimg, ivdeal, options);
        arrayList=SessionManager.getRecent1(SingleOrderActivity.this, getIntent().getStringExtra("order_list"));

        System.out.println("data dealsmodel " + getIntent().getStringExtra("order_list"));
        recyleaAdpter = new DealDetailsAdaptor(arrayList, SingleOrderActivity.this, Qtycount,Dealimgname);
        viewlist.setLayoutManager(new LinearLayoutManager(SingleOrderActivity.this));
        viewlist.setAdapter(recyleaAdpter);

        LLpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    sendtoserver();
                }else {
                    Toast.makeText(SingleOrderActivity.this, "Accept the terms and conditions to continue", Toast.LENGTH_SHORT).show();
                }
            }
        });
        apply_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(coupen_edit.getText().toString().equals(""))
                {
                    coupen_edit.setError("Please enter coupon code");
                }
                else {
                    Common.hideKeyboard(apply_now,SingleOrderActivity.this);
                    loadOfflineDeals();
                    arrayList=SessionManager.getRecent1(SingleOrderActivity.this, getIntent().getStringExtra("order_list"));

                    /*System.out.println("data dealsmodel " + getIntent().getStringExtra("order_list"));
                    recyleaAdpter = new DealDetailsAdaptor(arrayList, SingleOrderActivity.this, Qtycount,Dealimgname);
                    viewlist.setLayoutManager(new LinearLayoutManager(SingleOrderActivity.this));
                    viewlist.setLayoutManager(new LinearLayoutManager(SingleOrderActivity.this));
                    viewlist.setAdapter(recyleaAdpter);*/
                }
            }
        });
    }


    public void sendtoserver() {
        JSONObject student1 = new JSONObject();
        try {
            student1.put("deal_id", SelectedDealId);//
            student1.put("qty", Qtycount);
            student1.put("title", Deal_Title);//
            student1.put("price", Dealprice);//
            student1.put("dealImge", Dealimgname.replace(path, ""));
            student1.put("timeFrom", Dealstarttime);
            student1.put("timeTo", Dealendtime);

            final JSONArray jsonArray = new JSONArray();
            jsonArray.put(student1);

            JSONArray jsonArray1=new JSONArray(getIntent().getStringExtra("order_list"));

            parent = new JSONObject();
//            parent.put("info", jsonArray);
            parent.put("info", jsonArray1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String url = Constaints.OrderBookbforePay;
        progressBar.setVisibility(View.VISIBLE);
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jSONObject = new JSONObject(new String(response));
                    int Status = jSONObject.optInt("status");
                    order_id = String.valueOf(jSONObject.optInt("order_id"));
                    progressDialog.dismiss();
                    if (Status == 1) {
                        startPayment();
                    } else {
                        Toast.makeText(getApplicationContext(), jSONObject.optString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Log.e("", e.getMessage());
                    progressDialog.dismiss();
                }
                progressBar.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Log.e("error", "" + error);
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("XAPIKEY", "XXXXX");
                //    params.put("item_info", SelectedDealId + "-" + Qtycount);
                params.put("item_info", parent + "");
                params.put("user_id", SessionManager.getUserID(getApplication()).toString());
                params.put("outlet_id", Outlet_Id);
                params.put("total_amount", Dealprice);
                params.put("merchant_id", Merhant_Id);

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

    public void startPayment() {
        /**
         * You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;
        final Checkout co = new Checkout();
        try {
            JSONObject options = new JSONObject();
            options.put("name", "Xclusify");
            options.put("description", "Total amount to be paid");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://rzp-mobile.s3.amazonaws.com/images/rzp.png");
            Double payment = Double.valueOf(Dealprice);
            options.put("currency", "INR");
            options.put("color", "#333333");
           // options.put("background",R.color.blackfontcolor);
            //   double total = Double.parseDouble(payment);
            double total = payment * 100;
            options.put("amount", total);
            JSONObject preFill = new JSONObject();
            preFill.put("email", SessionManager.getUserEmail(getApplicationContext()));
            preFill.put("contact", SessionManager.getMobileno(getApplicationContext()));
            options.put("prefill", preFill);
            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        returntoApi(razorpayPaymentID);
    }

    public void returntoApi(final String razorpayPaymentID) {
        final String TransactionId = razorpayPaymentID;
        String url = Constaints.OrderpaidafterPay;
        progressBar.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jSONObject = new JSONObject(new String(response));
                    int Status = jSONObject.optInt("status");
                    if (Status == 1) {

/*
                        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
                        promptView = layoutInflater.inflate(R.layout.dialouge_thanks, null);
                        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(SingleOrderActivity.this);
                        alertDialogBuilder.setView(promptView);
                        alertDialogBuilder.setCancelable(true);
                        alert = alertDialogBuilder.create();
                        final TextView tvtransaction = (TextView) promptView.findViewById(R.id.tvtransaction);
                        final TextView tvQRcode = (TextView) promptView.findViewById(R.id.tvQRcode);
                        final ImageView ivQRnew = (ImageView) promptView.findViewById(R.id.ivQRnew);
                        final TextView tvsubmit = (TextView) promptView.findViewById(R.id.tvsubmit);
                        final TextView ins = (TextView) promptView.findViewById(R.id.ins);

                        tvtransaction.setText(TransactionId);
                       // VCard QRdealdeatil = new VCard(order_id);
                        ins.setText("Scan the QR code from My Orders section to redeem any deal and please refer to T&C for refund process");
                        ivQRnew.setImageBitmap(myBitmap);
                        // Hide after some seconds
                        final Handler handler = new Handler();
                        final Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                tvsubmit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        alert.dismiss();
                                        Intent i = new Intent(SingleOrderActivity.this, OrderActivity.class);
                                        startActivity(i);
                                    }
                                });
                            }
                        };
                        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                handler.removeCallbacks(runnable);
                            }
                        });
                        alert.show();
                        handler.postDelayed(runnable, 1000);*/
                        VCard QRdealdeatil = new VCard(TransactionId);

                        Bitmap myBitmap = QRCode.from(QRdealdeatil).withSize(300, 300).bitmap();
                        finish();

                        startActivity(new Intent(SingleOrderActivity.this,ThankyouActivity.class)
                        .putExtra("trans_id", TransactionId).putExtra("qrCode", myBitmap));
                    } else {
                        Toast.makeText(getApplicationContext(), jSONObject.optString("message"), Toast.LENGTH_LONG).show();
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
                Log.e("error", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("XAPIKEY", "XXXXX");
                params.put("transactions_no", TransactionId);
                params.put("order_id", order_id);
                params.put("status", "1");
                params.put("coupon_id",""+coupen_id);
                params.put("final_amout",Dealprice);
                params.put("max_disocunt",""+max_discount);
                params.put("coupon_status",coupon);
                System.out.println("server "+params);
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
        Volley.newRequestQueue(
                getApplicationContext()).
                add(request);
        request.setRetryPolicy(new
                DefaultRetryPolicy(
                socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    public void onPaymentError(int code, String response) {
        try {
            Toast.makeText(this, "Payment error please try again", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("OnPaymentError", "Exception in onPaymentError", e);
        }
    }


    class Reverse extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
               // arrayList = SessionManager.getRecent(getApplicationContext());
                //   orderarrayList=SessionManager.getRecent1(DetailNewActivity.this, intent.getStringExtra("order_array"));

                // Collections.reverse(arrayList);
            } catch (Exception e) {
                e.printStackTrace();

            }
            return null;
        }
    }




    public void loadOfflineDeals() {
        // if (From.equals("PullToRefresh")) {

        String url = Constaints.applyCoupen;
        progressBar.setVisibility(View.VISIBLE);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jSONObject = new JSONObject(new String(response));
                    //   JSONObject jSONObject = new JSONObject(new String(""));
                    arrayList.clear();


                    int Status = jSONObject.optInt("status");

                    if (Status == 1) {
                        JSONArray infoArray = jSONObject.getJSONArray("info");

                        for (int i = 0; i < infoArray.length(); i++) {

                            JSONObject searchObject = infoArray.optJSONObject(i);

                           coupen_id= searchObject.optInt("id");
                           int percent=searchObject.optInt("coupon_percentage");
                           max_discount= Double.parseDouble(searchObject.optString("max_discount"));

                            double amount = Double.parseDouble(Dealprice);
                            double amountMain = Double.parseDouble(Dealprice);
                            double per=Double.parseDouble(String.valueOf(percent));
                            double res = (amount / 100.0f) * per;

                            if (res>max_discount)
                            {
                                amount=amount-max_discount;
                            }
                            else {
                                amount=amount-res;
                                max_discount=res;
                            }

                            Dealprice=String.valueOf(amount);

                            coupon="1";
                            TextView textView=(TextView)findViewById(R.id.dealAmount);
                            TextView amountAfterCouponApplied=(TextView)findViewById(R.id.amountAfterCouponApplied);
                            textView.setText("\u20B9" +amountMain+"");
                            amountAfterCouponApplied.setText("- "+"\u20B9" +max_discount);
                            RelativeLayout relativeLayout=(RelativeLayout)findViewById(R.id.totalAmount);
                            RelativeLayout couponAmount=(RelativeLayout)findViewById(R.id.couponAmount);
                            relativeLayout.setVisibility(View.VISIBLE);
                            couponAmount.setVisibility(View.VISIBLE);


                            tvtotalamount.setText("\u20B9" +Dealprice);
                            apply_now.setText("Coupon applied."+" Discount ammount is "+"\u20B9" +max_discount);
                            apply_now.setVisibility(View.GONE);
                            coupen_edit.setVisibility(View.GONE);
                            apply_now.setClickable(false);
                        }
                    } else {
                        Toast.makeText(SingleOrderActivity.this, jSONObject.optString("message"), Toast.LENGTH_LONG).show();

                        coupon="0";
                    }

                    progressBar.setVisibility(View.INVISIBLE);
                } catch (Exception e) {
                    Log.e("", e.getMessage());

                }
                progressBar.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);

                Log.e("error", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("XAPIKEY", "XXXXX");
                params.put("user_id", SessionManager.getUserID(SingleOrderActivity.this));
                params.put("coupon_code", coupen_edit.getText().toString().trim());
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
        Volley.newRequestQueue(SingleOrderActivity.this).add(request);
        request.setRetryPolicy(new DefaultRetryPolicy(
                socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
}
