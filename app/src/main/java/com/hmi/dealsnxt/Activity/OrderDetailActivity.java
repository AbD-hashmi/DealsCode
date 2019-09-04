package com.hmi.dealsnxt.Activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
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
    public TextView tvvoucherid;
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
        RLAmount = (RelativeLayout) findViewById(R.id.RLAmount);
        tvoff = (TextView) findViewById(R.id.tvoff);
        LLloc.setVisibility(View.GONE);
        imBack.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);
        ivsearch.setVisibility(View.GONE);
        tvusername.setVisibility(View.GONE);
        tvTitle.setText("Order Details");
        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
        imageLoader.displayImage(OrderModel.getOrderModel().getDealimgurl(), ivdeal, options);

        orderdetail();
    }


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

                                tvtotal.setText("\u20B9" + " " + orderJson.optString("totalAmount"));
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

                                tvstartime.setText(Customutils.dateFormat(orderJson.optString("timeFrom")) + "-" + Customutils.dateFormat(orderJson.optString("timeTo")));
                                tvoff.setText(orderJson.optString("percent") + "%" + " " + "off");
                                tvdealname.setText("on" + " " + orderJson.optString("deal_title"));
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
                    //   params.put("user_id", "16");
                    params.put("deal_id", OrderModel.getOrderModel().getDealid());
                    //
                    //   params.put("user_id", SessionManager.getUserID(getApplicationContext()));
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

}
