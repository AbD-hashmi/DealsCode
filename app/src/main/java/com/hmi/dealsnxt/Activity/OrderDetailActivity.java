package com.hmi.dealsnxt.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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
import com.hmi.dealsnxt.Adaptor.OrderBookedDetailAdaptor;
import com.hmi.dealsnxt.HelperClass.Constaints;
import com.hmi.dealsnxt.HelperClass.Global;
import com.hmi.dealsnxt.HelperClass.SessionManager;
import com.hmi.dealsnxt.Model.OrderModel;
import com.hmi.dealsnxt.R;
import com.hmi.dealsnxt.Utils.Customutils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import net.glxn.qrgen.android.QRCode;
import net.glxn.qrgen.core.scheme.VCard;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDetailActivity extends AppCompatActivity {
    public ImageView ivdeal;
    public TextView tvorderid, tvoutletname, tvoutletaddress, tvstartime, tvendtime, tvdealdate, tvtransactionId, tvtotal;
    public TextView tvvoucherid,total_amount,tv_offerText;
    public ProgressBar progressBar;
    public RecyclerView viewlist;
    com.nostra13.universalimageloader.core.ImageLoader imageLoader;
    DisplayImageOptions options;
    private List<OrderModel> arrayList = new ArrayList<>();
    public TextView tvusername, tvTitle, tvdealname, tvoff;
    public LinearLayout newtoolbar;
    public ImageView imBack;
    public ImageView ivsearch;
    public LinearLayout LLloc;
    public RelativeLayout RLAmount;
    public ImageView ivfilter, ivQR;
    public Button btnCancel;
    public String val;
    public Dialog alertDialogBuilder;
    View promptView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetail);
        ivdeal = (ImageView) findViewById(R.id.ivdeal);
        tvvoucherid = (TextView) findViewById(R.id.tvvoucherid);
        tvoutletname = (TextView) findViewById(R.id.tvoutletname);
        tvoutletaddress = (TextView) findViewById(R.id.tvoutletaddress);
        tvstartime = (TextView) findViewById(R.id.tvstartime);
        tvendtime = (TextView) findViewById(R.id.tvendtime);
        tvdealdate = (TextView) findViewById(R.id.tvdealdate);
        viewlist = (RecyclerView) findViewById(R.id.viewlist);
        tvtransactionId = (TextView) findViewById(R.id.tvtransactionId);
        tvtotal = (TextView) findViewById(R.id.tvtotal);
        ivQR = (ImageView) findViewById(R.id.ivQR);
        tvdealname = (TextView) findViewById(R.id.tvdealname);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        newtoolbar = (LinearLayout) findViewById(R.id.toolbarnew);
        imBack = (ImageView) newtoolbar.findViewById(R.id.imBack);
        tvTitle = (TextView) newtoolbar.findViewById(R.id.tvTitle);
        LLloc = (LinearLayout) newtoolbar.findViewById(R.id.LLloc);
        ivfilter = (ImageView) newtoolbar.findViewById(R.id.ivfilter);
        ivsearch = (ImageView) newtoolbar.findViewById(R.id.ivsearch);
        tvusername = (TextView) newtoolbar.findViewById(R.id.tvusername);
        tvoff = (TextView) findViewById(R.id.tvoff);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        total_amount=(TextView)findViewById(R.id.total_amount);
        tv_offerText=(TextView)findViewById(R.id.tv_offerText);
        LLloc.setVisibility(View.GONE);
        imBack.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);
        ivsearch.setVisibility(View.GONE);
        tvusername.setVisibility(View.GONE);
        tvTitle.setText("Order Details");
        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(OrderDetailActivity.this,OrderActivity.class));
            }
        });
        options = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .showImageForEmptyUri(R.drawable.deal1)
                .showImageOnLoading(R.drawable.banner).showImageForEmptyUri(R.drawable.banner).showImageOnFail(R.drawable.banner)
                .build();
        imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(OrderDetailActivity.this));
        System.out.println("img url "+ OrderModel.getOrderModel().getDealimgurl());
        imageLoader.displayImage(OrderModel.getOrderModel().getDealimgurl().replace("http://dealsnxt.nuagedigitech.com/application/public/uploads/deals/http://dealsnxt.nuagedigitech.com/application/public/uploads/deals/",
                "http://dealsnxt.nuagedigitech.com/application/public/uploads/deals/"), ivdeal, options);

        Bundle bundle=getIntent().getExtras();

      /*  if (bundle.getInt("cancelVisible") == 1){
            //    Toast.makeText(this, "Cancel Visible" + bundle.getInt("cancelVisible"), Toast.LENGTH_SHORT).show();
            btnCancel.setVisibility(View.VISIBLE);
        }else{
            btnCancel.setVisibility(View.GONE);
        }

*/
      btnCancel.setVisibility(View.GONE);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelOrder();
            }
        });
        orderdetail();
    }


    public void cancelOrder(){

        alertDialogBuilder = new Dialog(OrderDetailActivity.this);
        LayoutInflater layoutInflater = LayoutInflater.from(OrderDetailActivity.this);
        promptView = layoutInflater.inflate(R.layout.activity_cancelpopup, null);
        alertDialogBuilder.setContentView(promptView);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        final CheckBox chkselect1 = (CheckBox) promptView.findViewById(R.id.chkselect1);
        final CheckBox chkselect2 = (CheckBox) promptView.findViewById(R.id.chkselect2);
        final CheckBox chkselect3 = (CheckBox) promptView.findViewById(R.id.chkselect3);
        final CheckBox chkselect4 = (CheckBox) promptView.findViewById(R.id.chkselect4);

        final EditText othertext = (EditText) promptView.findViewById(R.id.othertext);
        final TextView cancel = (TextView) promptView.findViewById(R.id.cancel);
        final TextView ok = (TextView) promptView.findViewById(R.id.ok);

        othertext.setVisibility(View.GONE);

        chkselect4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                othertext.setVisibility(View.VISIBLE);
                chkselect3.setChecked(false);
                chkselect2.setChecked(false);
                chkselect1.setChecked(false);
                val = othertext.getText().toString();
            }
        });
        chkselect1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                othertext.setVisibility(View.GONE);
                chkselect3.setChecked(false);
                chkselect2.setChecked(false);
                chkselect4.setChecked(false);
                val = "Plan Changed";
            }
        });
        chkselect2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                othertext.setVisibility(View.GONE);
                chkselect3.setChecked(false);
                chkselect4.setChecked(false);
                chkselect1.setChecked(false);
                val = "Purchease other deal";
            }
        });
        chkselect3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                othertext.setVisibility(View.GONE);
                chkselect1.setChecked(false);
                chkselect2.setChecked(false);
                chkselect4.setChecked(false);
                val = "No more Interested";
            }
        });

                /*   if (chkselect1.isChecked()) {
                    chkselect3.setChecked(false);
                    chkselect2.setChecked(false);
                    chkselect4.setChecked(false);
                    val = "Plan Changed";
                } else if (chkselect2.isChecked()) {
                    chkselect3.setChecked(false);
                    chkselect4.setChecked(false);
                    chkselect1.setChecked(false);
                    val = "Purchease other deal";
                } else if (chkselect3.isChecked()) {
                    chkselect1.setChecked(false);
                    chkselect2.setChecked(false);
                    chkselect4.setChecked(false);
                    val = "Not more Intrested";
                } else if (chkselect4.isChecked()) {
                    chkselect3.setChecked(false);
                    chkselect2.setChecked(false);
                    chkselect1.setChecked(false);
                    othertext.setVisibility(View.VISIBLE);
                    val = othertext.getText().toString();
                }*/

               /* if (chkselect4.isChecked()) {
                    othertext.setVisibility(View.VISIBLE);
                } else {
                    othertext.setVisibility(View.GONE);
                }*/

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Global.isInternetAvail(OrderDetailActivity.this)) {
                    String url = Constaints.OrderedCancel;
                    OrderActivity.progressBar.setVisibility(View.VISIBLE);
                    StringRequest request = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        Log.e("profile update resp", response);
                                        JSONObject jSONObject = new JSONObject(new String(response));
                                        String Status = jSONObject.optString("status");
                                        if (Integer.parseInt(Status) == 1) {
                                            // loadDetailist("");
                                            //    Toast.makeText(context, jSONObject.getString("message").toString(), Toast.LENGTH_SHORT).show();
                                            alertDialogBuilder.dismiss();
                                            finish();
                                            Toast.makeText(getApplicationContext(), ""+jSONObject.getString("message"), Toast.LENGTH_SHORT).show();
                                        } else {
                                            alertDialogBuilder.dismiss();
                                            Toast.makeText(getApplicationContext(), jSONObject.getString("message").toString(), Toast.LENGTH_SHORT).show();
                                            alertDialogBuilder.cancel();
                                            finish();
                                        }
                                    } catch (Exception e) {
                                        Log.e("", e.getMessage());
                                        alertDialogBuilder.dismiss();
                                        alertDialogBuilder.cancel();
                                        finish();
                                    }
                                    OrderActivity.progressBar.setVisibility(View.GONE);
                                }
                            }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            OrderActivity.progressBar.setVisibility(View.GONE);
                            Toast.makeText(OrderDetailActivity.this, "Can't cancel deal", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("XAPIKEY", "XXXXX");
                            params.put("deal_id", getIntent().getExtras().getString("id"));
                            params.put("status", "2");
                            params.put("user_id", SessionManager.getUserID(getApplicationContext()));
                          //  params.put("status", val);
                            params.put("reason",val);

                            System.out.println("data "+params);
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
                } else{
                    Toast.makeText(getApplicationContext(), R.string.ConnectionErrorResponse, Toast.LENGTH_LONG).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogBuilder.dismiss();
            }
        });
        if (!isFinishing())
            alertDialogBuilder.show();

    }
String totalAmount,offerApplied="90000";
    String gift_applied,refundable,deal_status;

    public void orderdetail() {

        if (Global.isInternetAvail(OrderDetailActivity.this)) {
            String url = Constaints.OrderedDetailbyDealid;
            progressBar.setVisibility(View.VISIBLE);
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jSONObject = new JSONObject(new String(response));
                        int Status = jSONObject.optInt("status");
                        //     String Path = Constaints.BaseUrl + jSONObject.optString("dealsCdnpath");
                        if (Status == 1) {
                            JSONArray orderArray = jSONObject.getJSONArray("order");
                            for (int i = 0; i < orderArray.length(); i++) {
                                JSONObject orderJson = orderArray.getJSONObject(i);
                                System.out.println("data = "+orderArray.getJSONObject(i));
                                gift_applied=orderJson.optString("gift_applied");

                              //  tvtotal.setText("\u20B9" + " " + orderJson.optString("total_amount"));
                                totalAmount=" " + orderJson.optString("total_amount");
                                offerApplied=orderJson.optString("offer_applied");

                                tvtransactionId.setText(orderJson.optString("transactions_no"));
                                try {
                                    String date = orderJson.optString("order_date");
                                    SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy HH:mm aaa");
                                    Date newDate = sf.parse(date);
                                    long startDate = newDate.getTime();
                                    String dateString = new SimpleDateFormat("dd/MMM/yyyy").format(startDate);
                                    tvdealdate.setText(dateString);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                if (orderJson.optString("transactions_no").equals("")) {
                                   // RLAmount.setVisibility(View.GONE);
                                }
                            }

                            JSONArray outletArray = jSONObject.getJSONArray("outlet");
                            for (int i = 0; i < outletArray.length(); i++) {
                                JSONObject outletJson = outletArray.getJSONObject(i);
                                tvoutletname.setText(outletJson.optString("name"));
                                tvoutletaddress.setText((outletJson.optString("outlet_address") + "," + (outletJson.optString("city") + "," + (outletJson.optString("Haryana")))));
                            }

                            JSONArray dealArray = jSONObject.getJSONArray("dealDetail");
                            for (int i = 0; i < dealArray.length(); i++) {
                                JSONObject orderJson = dealArray.getJSONObject(i);

                                refundable=orderJson.optString("refundable_policy");
                                deal_status=orderJson.optString("status");
                                if(orderJson.optString("final_price").equals("")){
                                 tvtotal.setText("\u20B9" + totalAmount);
                                 total_amount.setText("\u20B9" + totalAmount);
                                 tvoff.setText("\u20B9" + " " +"0");
                                }else {
                                    if (offerApplied.equals("1")){
                                        tv_offerText.setText("Coupon Applied");
                                    }else {
                                        tv_offerText.setText("Offer Applied");
                                    }
                                   // totalAmount=orderJson.optString("total_amount");

                                    tvtotal.setText("\u20B9" + " " + totalAmount);

                                    total_amount.setText("\u20B9" + " " +orderJson.optString("price"));

                                    Double offer;
                                    int price=Integer.parseInt(orderJson.optString("price"));
                                    if (price<Double.valueOf(totalAmount)){
                                        offer=Double.valueOf(totalAmount)-price;
                                    }else{
                                        offer = price - Double.valueOf(totalAmount);
                                    }System.out.println("werfwef "+Double.valueOf(totalAmount));

                                    tvoff.setText("- \u20B9" + " " +String.format("%.0f", offer));
                                }

                                tvstartime.setText(" "+Customutils.dateFormat(orderJson.optString("timeFrom")) + "-" + Customutils.dateFormat(orderJson.optString("timeTo")));
                               // tvoff.setText(orderJson.optString("offer_applied"));

                               // tvdealname.setText(" " + orderJson.optString("deal_title"));
                                if (!orderJson.optString("show_percentage").equals("0") || orderJson.optString("show_percentage")!="0") {
                                    tvdealname.setText(orderJson.getString("percent")+"% Off on " +orderJson.optString("deal_title"));

                                }else{
                                    tvdealname.setText(orderJson.optString("deal_title"));
                                }
                                tvvoucherid.setText(orderJson.optString("voucherCode"));
                                       /* if (Integer.valueOf(OrderModel.getOrderModel().getOutletorderstatus()) == 0) {
                                            ivQR.setVisibility(View.GONE);
                                        } else if (Integer.valueOf(OrderModel.getOrderModel().getOutletorderstatus()) == 2) {
                                            ivQR.setVisibility(View.GONE);
                                        } else if (Integer.valueOf(OrderModel.getOrderModel().getOutletorderstatus()) == 3) {
                                            ivQR.setVisibility(View.GONE);
                                        } else {*/
                                // VCard lVCard = new VCard(OrderModel.getOrderModel().getDealorderid());
                                VCard lVCard = new VCard(orderJson.optString("voucherCode"));
                                Bitmap Bitmap1 = QRCode.from(lVCard).bitmap();
                                ivQR.setImageBitmap(Bitmap1);

                                if ((orderJson.optString("voucherCode").isEmpty()) || ((orderJson.optString("voucherCode").equals("")))) {
                             //       RLAmount.setVisibility(View.GONE);
                                }
                            }

                            JSONArray infoArray = jSONObject.getJSONArray("otherOrderDetails");
                            for (int i = 0; i < infoArray.length(); i++) {
                                OrderModel orderModel = new OrderModel();
                                JSONObject orderJson = infoArray.getJSONObject(i);
                                orderModel.setDealname(orderJson.optString("title"));
                                orderModel.setDealQty(orderJson.optString("qty"));
                                orderModel.setDealstatus(orderJson.optString("status"));
                                orderModel.setDealdiscountpernt(orderJson.optString("percent"));
                                orderModel.setDealpurchaseamount(orderJson.optString("price"));
                                arrayList.add(orderModel);
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), jSONObject.optString("msg"), Toast.LENGTH_LONG).show();
                        }
                        OrderBookedDetailAdaptor adapter = new OrderBookedDetailAdaptor(arrayList, OrderDetailActivity.this);
                        viewlist.setLayoutManager(new LinearLayoutManager(OrderDetailActivity.this));
                        viewlist.setAdapter(adapter);

                    } catch (Exception e) {
                        Log.e("", e.getMessage());
                    }

                    if (!gift_applied.equals("1")) {
                        //   cancelVisible=1;
                        if (refundable.equals("1")) {
                            //     cancelVisible=1;
                            if (deal_status.equals("1")) {
                                btnCancel.setVisibility(View.VISIBLE);
                            } else {
                                btnCancel.setVisibility(View.GONE);
                            }
                        } else {
                            btnCancel.setVisibility(View.GONE);
                        }
                    }else {
                        btnCancel.setVisibility(View.GONE);

                    }
                    progressBar.setVisibility(View.GONE);
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {


                    Toast.makeText(OrderDetailActivity.this, R.string.ConnectionErrorResponse, Toast.LENGTH_SHORT).show();

                    progressBar.setVisibility(View.GONE);
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("XAPIKEY", "XXXXX");
                    params.put("user_id", SessionManager.getUserID(getApplicationContext()));
                    params.put("deal_id", OrderModel.getOrderModel().getDealid());
                    //
                    //   params.put("user_id", SessionManager.getUserID(getApplicationContext()));
                    System.out.println(params);
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
            Toast.makeText(OrderDetailActivity.this, R.string.ConnectionErrorResponse, Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
