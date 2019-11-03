package com.hmi.dealsnxt.Adaptor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hmi.dealsnxt.Activity.OrderActivity;
import com.hmi.dealsnxt.Activity.OrderDetailActivity;
import com.hmi.dealsnxt.Fragement.RedeemCancel;
import com.hmi.dealsnxt.HelperClass.Constaints;
import com.hmi.dealsnxt.HelperClass.Global;
import com.hmi.dealsnxt.HelperClass.SessionManager;
import com.hmi.dealsnxt.Model.OrderModel;
import com.hmi.dealsnxt.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class RedeemorCancelOrder extends RecyclerView.Adapter<RedeemorCancelOrder.SimpleItemViewHolder> {
    private List<OrderModel> items;
    public Activity activity;
    public int count = 0;
    public Context context;
    public static String Dealid;
    com.nostra13.universalimageloader.core.ImageLoader imageLoader;
    public static String OutlletName, OutlletAddress, OutletInTime, OutletOutTime, Dealdescription;
    DisplayImageOptions options;
    TextView tvoutletname;
    public RedeemorCancelOrder RedeemorCancelOrder;
    List<CharSequence> list = new ArrayList<CharSequence>();
    View promptView;
    android.app.AlertDialog alert;
    int cancelVisible=0;
    public String val;
    RedeemCancel redeemOrder;
    // Provide a reference to the views for each data item
// Provide access to all the views for a data item in a view holder
    public final static class SimpleItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvdiscount, tvdealdate, tvdealname, tvlocation, tvstartime, tvactualprice, tvdealstatus, tvwaiveoffrs;
        ImageView dealimg;
        //  LinearLayout LLoption;
        LinearLayout LLView;
        TextView tvoutletname, tvcancel,orderId;
        com.nostra13.universalimageloader.core.ImageLoader imageLoader;

        DisplayImageOptions options;

        public SimpleItemViewHolder(View itemView) {
            super(itemView);
            //   LLbanner = (RelativeLayout) itemView.findViewById(R.id.LLbanner);

            LLView = (LinearLayout) itemView.findViewById(R.id.LLView);
            dealimg = (ImageView) itemView.findViewById(R.id.dealimg);
            tvoutletname = (TextView) itemView.findViewById(R.id.tvoutletname);
            tvlocation = (TextView) itemView.findViewById(R.id.tvlocation);
            tvdealdate = (TextView) itemView.findViewById(R.id.tvdealdate);
            tvwaiveoffrs = (TextView) itemView.findViewById(R.id.tvwaiveoffrs);
            tvdealstatus = (TextView) itemView.findViewById(R.id.tvdealstatus);
            tvcancel = (TextView) itemView.findViewById(R.id.tvcancel);
            orderId =(TextView)itemView.findViewById(R.id.orderId);


        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RedeemorCancelOrder(List<OrderModel> items, Activity _activity, Context context, RedeemCancel redeemOrder) {
        this.items = items;
        this.activity = _activity;
        this.context = context;
        this.redeemOrder=redeemOrder;

        options = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .showImageOnLoading(R.mipmap.ic_launcher).showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .build();
        imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(activity));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.items.size();
    }

    // Create new items (invoked by the layout manager)
    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public SimpleItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView;
        // if (viewType ==  1) {
        itemView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.row_order, viewGroup, false);
        //  } else {
        //      itemView = LayoutInflater.from(viewGroup.getContext()).
        //               inflate(R.layout.row_schedule_lunch, viewGroup, false);
        //    }
        return new SimpleItemViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    // Involves populating data into the item through holder
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final SimpleItemViewHolder viewHolder, final int position) {

        String path=items.get(position).getDealimgurl().replace("http://dealsnxt.nuagedigitech.com/application/public/uploads/deals/http://dealsnxt.nuagedigitech.com/application/public/uploads/deals/",
                "http://dealsnxt.nuagedigitech.com/application/public/uploads/deals/");
        imageLoader.displayImage(path, viewHolder.dealimg, options);
        System.out.println("imgLink " + items.get(position).getDealimgurl());
        try {
            String date = items.get(position).getDealpurchasedate();
            // SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy HH:mm aaa", Locale.US);
            Date newDate = sf.parse(date);
            long startDate = newDate.getTime();
            String dateString = new SimpleDateFormat("dd MMM yyyy").format(startDate);
            viewHolder.tvdealdate.setText(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // viewHolder.tvdealdate.setText(items.get(position).getDealpurchasedate());

        viewHolder.tvoutletname.setText(items.get(position).getDealtitle());
        viewHolder.tvlocation.setText(items.get(position).getOutletaddress());
        viewHolder.tvwaiveoffrs.setText("\u20B9" + " " + items.get(position).getDealpurchaseamount());
        //      orderModel.setDealimgurl(Path + "/" + dealobj.optString("dealImge"));
        viewHolder.orderId.setText("Order ID: "+items.get(position).getDealorderid());
        if (items.get(position).getRefundable_policy().equals("1")) {
            if (!items.get(position).getGift_applied().equals("1")) {
                if (items.get(position).getDealstatus().equals("1")) {
                    cancelVisible = 1;
                    try {
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm aa");
                        String getCurrentDateTime = sdf.format(c.getTime());
                        String getMyTime=items.get(position).getDealavailtime_to();
                        Log.d("getCurrentDateTime",getCurrentDateTime);
                        // getCurrentDateTime: 05/23/2016 18:49 PM

                        if (getCurrentDateTime.compareTo(getMyTime) > 0)
                        {
                            cancelVisible=1;
                           // Toast.makeText(activity, "greater than 0", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            cancelVisible=0;
                            Log.d("Return","getMyTime older than getCurrentDateTime ");
                        }
                    } catch (Exception e) {

                    }

                } else {
                    cancelVisible = 0;
                }
            } else {
                cancelVisible = 0;
            }
        }else {
            cancelVisible = 0;
        }

        if (Integer.valueOf(items.get(position).getOutletorderstatus()) == 0) {
            // QR not SHown
            viewHolder.tvdealstatus.setText("Failed");
            //  viewHolder.tvdealstatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cancelled, 0, 0, 0);
            viewHolder.tvcancel.setVisibility(View.INVISIBLE);
        } else if (Integer.valueOf(items.get(position).getOutletorderstatus()) == 1) {
           /* if (Integer.valueOf(items.get(position).getDealstatus()) == 0) {
                viewHolder.tvdealstatus.setVisibility(View.VISIBLE);
                viewHolder.tvdealstatus.setText("Cancelled");
                viewHolder.tvdealstatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cancelled, 0, 0, 0);
                viewHolder.tvcancel.setVisibility(View.INVISIBLE);
            } else {*/
            if (Integer.valueOf(items.get(position).getDealstatus()) == 1) {
                //viewHolder.tvdealstatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cancelled, 0, 0, 0);
                //viewHolder.tvcancel.setVisibility(View.VISIBLE);
                viewHolder.tvdealstatus.setVisibility(View.VISIBLE);
                viewHolder.tvdealstatus.setText("Paid");
                viewHolder.tvdealstatus.setTextColor(context.getResources().getColor(R.color.green));
                //  viewHolder.tvdealstatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.delivered, 0, 0, 0);
            } else if (Integer.valueOf(items.get(position).getDealstatus()) == 2) {
                //  viewHolder.tvdealstatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cancelled, 0, 0, 0);
                viewHolder.tvcancel.setVisibility(View.INVISIBLE);
                viewHolder.tvdealstatus.setVisibility(View.VISIBLE);
                viewHolder.tvdealstatus.setText("Cancelled");
                viewHolder.tvdealstatus.setTextColor(context.getResources().getColor(R.color.red));

                //   viewHolder.tvdealstatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cancelled, 0, 0, 0);
            } else if (Integer.valueOf(items.get(position).getDealstatus()) == 3) {
                //  viewHolder.tvdealstatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cancelled, 0, 0, 0);
                viewHolder.tvcancel.setVisibility(View.INVISIBLE);
                viewHolder.tvdealstatus.setVisibility(View.VISIBLE);
                viewHolder.tvdealstatus.setText("Redeemed");
                viewHolder.tvdealstatus.setTextColor(context.getResources().getColor(R.color.green));

                //viewHolder.tvdealstatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.delivered, 0, 0, 0);
            } else if (Integer.valueOf(items.get(position).getDealstatus()) == 4) {
                //  viewHolder.tvdealstatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cancelled, 0, 0, 0);
                viewHolder.tvcancel.setVisibility(View.INVISIBLE);
                viewHolder.tvdealstatus.setVisibility(View.VISIBLE);
                viewHolder.tvdealstatus.setText("Expired");
                viewHolder.tvdealstatus.setTextColor(context.getResources().getColor(R.color.red));

                //viewHolder.tvdealstatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cancelled, 0, 0, 0);
            }
        }
         else if (Integer.valueOf(items.get(position).getOutletorderstatus()) == 2) {
            // QR not SHown
            viewHolder.tvdealstatus.setText("Redeemed");
            viewHolder.tvdealstatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.delivered, 0, 0, 0);
            viewHolder.tvcancel.setVisibility(View.INVISIBLE);
        } else if (Integer.valueOf(items.get(position).getOutletorderstatus()) == 3) {
            // QR not SHown
            viewHolder.tvcancel.setVisibility(View.INVISIBLE);
            viewHolder.tvdealstatus.setText("Expired");
            viewHolder.tvdealstatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cancelled, 0, 0, 0);
            viewHolder.tvcancel.setVisibility(View.INVISIBLE);
        }


        viewHolder.LLView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderModel.setOrderModel(items.get(position));
                Intent i = new Intent(activity, OrderDetailActivity.class)
                        .putExtra("cancelVisible", cancelVisible);
                activity.startActivity(i);
            }
        });

        list.add("Not Intrested");
        list.add("Wrong Place");
        list.add("Others");

        viewHolder.tvcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  items.get(position).getDealavailtime_from().toString();
                try {
                    Calendar date = Calendar.getInstance();
                    date.setTime(new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH).parse(items.get(position).getDealavailtime_from().toString()));
                    // Parse into Date object
                    Calendar now = Calendar.getInstance(); // Get time now
                    long differenceInMillis = now.getTimeInMillis() - date.getTimeInMillis();
                    long differenceInHours = (differenceInMillis) / 1000L / 60L / 60L; // Divide by millis/sec, secs/min, mins/hr
                    //  return (int) differenceInHours;

                    if (differenceInHours > 1) {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }*/

                LayoutInflater layoutInflater = LayoutInflater.from(context);
                promptView = layoutInflater.inflate(R.layout.activity_cancelpopup, null);
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(activity);
                alertDialogBuilder.setView(promptView);
                alertDialogBuilder.setCancelable(false);
                alert = alertDialogBuilder.create();
                alert.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                alert.show();
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
                        val = "Not more Intrested";
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
                        if (Global.isInternetAvail(activity)) {
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
                                                    redeemOrder.loadDetailist("");
                                                    //    Toast.makeText(context, jSONObject.getString("message").toString(), Toast.LENGTH_SHORT).show();
                                                    viewHolder.tvdealstatus.setText("Cancelled");
                                                    viewHolder.tvcancel.setVisibility(View.INVISIBLE);
                                                    viewHolder.tvdealstatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cancelled, 0, 0, 0);
                                                    alert.dismiss();
                                                    Toast.makeText(context, "Order has been Cancelled", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    alert.dismiss();
                                                    Toast.makeText(context, jSONObject.getString("message").toString(), Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (Exception e) {
                                                Log.e("", e.getMessage());
                                                alert.dismiss();
                                            }
                                            OrderActivity.progressBar.setVisibility(View.GONE);
                                        }
                                    }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    OrderActivity.progressBar.setVisibility(View.GONE);
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("XAPIKEY", "XXXXX");
                                    params.put("deal_id", items.get(position).getDealid());
                                    params.put("status", 2 + "");
                                    params.put("user_id", SessionManager.getUserID(context));
                                    // params.put("status", val);
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
                            Volley.newRequestQueue(context).add(request);
                            request.setRetryPolicy(new DefaultRetryPolicy(
                                    socketTimeout,
                                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        } else

                        {
                            Toast.makeText(activity, R.string.ConnectionErrorResponse, Toast.LENGTH_LONG).show();
                        }


                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.dismiss();
                    }
                });

            }
        });


    }




    @Override
    public int getItemViewType(int position) {
        int viewType = 0;
        return viewType;
    }


}