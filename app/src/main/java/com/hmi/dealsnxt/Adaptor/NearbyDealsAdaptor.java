package com.hmi.dealsnxt.Adaptor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.hmi.dealsnxt.Activity.DetailNewActivity;
import com.hmi.dealsnxt.Activity.NearByActivity;
import com.hmi.dealsnxt.Fragement.HotDealsFragment;
import com.hmi.dealsnxt.HelperClass.Constaints;
import com.hmi.dealsnxt.HelperClass.Global;
import com.hmi.dealsnxt.HelperClass.SessionManager;
import com.hmi.dealsnxt.Model.HotDealsModel;
import com.hmi.dealsnxt.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NearbyDealsAdaptor extends RecyclerView.Adapter<NearbyDealsAdaptor.SimpleItemViewHolder> {
    private List<HotDealsModel> items;
    public Activity activity;
    public int count = 0;
    public Context context;
    public static String Dealid;
    com.nostra13.universalimageloader.core.ImageLoader imageLoader;
    public static String OutlletName, OutlletAddress, OutletInTime, OutletOutTime, Dealdescription;
    DisplayImageOptions options;
    // public String Dealid="";


    // Provide a reference to the views for each data item
// Provide access to all the views for a data item in a view holder
    public final static class SimpleItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvdiscount, tvlikecount, tvdealname, tvlocation, tvstartime, tvactualprice, tvendtime;
        TextView tvwaiveoffrs, tvoptiontwo, tvoptionone, tvoptionthree, date;
        LinearLayout LLoffer;
        View viewline;
        ImageView dealimg, ivlike;
        RelativeLayout RLpercent, LLbanner;
        LinearLayout LLoption;
        LinearLayout LLView;
        public TextView tvcount;
        //  public TextView tvcount;

        public SimpleItemViewHolder(View itemView) {
            super(itemView);
            //   LLbanner = (RelativeLayout) itemView.findViewById(R.id.LLbanner);

            LLView = (LinearLayout) itemView.findViewById(R.id.LLView);
            RLpercent = (RelativeLayout) itemView.findViewById(R.id.RLpercent);
            tvdiscount = (TextView) itemView.findViewById(R.id.tvdiscount);
            tvlikecount = (TextView) itemView.findViewById(R.id.tvlikecount);
            tvdealname = (TextView) itemView.findViewById(R.id.tvdealname);
            tvlocation = (TextView) itemView.findViewById(R.id.tvlocation);
            tvstartime = (TextView) itemView.findViewById(R.id.tvstartime);
            tvendtime = (TextView) itemView.findViewById(R.id.tvendtime);
            tvactualprice = (TextView) itemView.findViewById(R.id.tvactualprice);
            viewline = (View) itemView.findViewById(R.id.viewline);
            tvwaiveoffrs = (TextView) itemView.findViewById(R.id.tvwaiveoffrs);
            LLoption = (LinearLayout) itemView.findViewById(R.id.LLoption);
            tvoptionone = (TextView) itemView.findViewById(R.id.tvoptionone);
        /*    tvoptiontwo = (TextView) itemView.findViewById(R.id.tvoptiontwo);
            tvoptionthree = (TextView) itemView.findViewById(R.id.tvoptionthree);*/
            tvcount = (TextView) itemView.findViewById(R.id.tvcount);
            LLoffer = (LinearLayout) itemView.findViewById(R.id.LLoffer);
            date = (TextView) itemView.findViewById(R.id.date);
            dealimg = (ImageView) itemView.findViewById(R.id.dealimg);
            ivlike = (ImageView) itemView.findViewById(R.id.ivlike);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public NearbyDealsAdaptor(List<HotDealsModel> items, Activity _activity, Context context) {
        this.items = items;
        this.activity = _activity;
        this.context = context;

        options = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .showImageOnLoading(R.drawable.banner).showImageForEmptyUri(R.drawable.banner).showImageOnFail(R.drawable.banner)
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
                inflate(R.layout.row_nearby_deals, viewGroup, false);
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

        viewHolder.tvdealname.setText(items.get(position).getOutletName());
        viewHolder.tvstartime.setText(items.get(position).getAvailibiltyTime());
        viewHolder.tvendtime.setText(items.get(position).getReminderTime());
        viewHolder.tvactualprice.setText("\u20B9" + items.get(position).getActualPrice());
        viewHolder.tvwaiveoffrs.setText(items.get(position).getAfterDiscountPrice());
        //     viewHolder.tvcount.setText("" + items.get(position).getNumofOffers());
        viewHolder.tvcount.setText("" + (Integer.parseInt(items.get(position).getNumofOffers()) - 1));
        if (Integer.parseInt(items.get(position).getNumofOffers() )== 1) {
            viewHolder.LLoffer.setVisibility(View.GONE);
        } else {
            viewHolder.tvcount.setText("" + (Integer.parseInt(items.get(position).getNumofOffers() )- 1));
        }
        viewHolder.tvoptionone.setText(items.get(position).getDealTitle());
        viewHolder.tvlocation.setText(items.get(position).getOutletAddress());
        viewHolder.tvdiscount.setText(items.get(position).getPercentage());

        viewHolder.tvlikecount.setText(items.get(position).getLikesCount());
        //     viewHolder.ivlike.setText(items.get(position).getLikes());
        if ((items.get(position).getLikes()).equals(1)) {
            viewHolder.ivlike.setImageResource(R.drawable.like);
            viewHolder.ivlike.setClickable(false);
        }

        OutlletName = items.get(position).getOutletName();
        OutlletAddress = (items.get(position).getOutletAddress() + "," + items.get(position).getOutletCity());
        OutletInTime = (items.get(position).getAvailibiltyTime());
        OutletOutTime = (items.get(position).getReminderTime());
        viewHolder.tvactualprice.setPaintFlags(viewHolder.tvactualprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        imageLoader.displayImage(items.get(position).getDealimage(), viewHolder.dealimg, options);

        viewHolder.LLView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dealid = String.valueOf(items.get(position).getDealid());
                //increasecount(viewHolder.tvlikecount);
                HotDealsModel.setHotDealsModel(items.get(position));
                Intent i = new Intent(activity, DetailNewActivity.class);
                i.putExtra("outletid", items.get(position).getOutletid());
                activity.startActivity(i);
            }
        });



    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 0;
        return viewType;
    }

    public Bitmap getBitmapFromURL(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void increasecount(final TextView textView) {
        if (Global.isInternetAvail(activity)) {
            String url = Constaints.DealLikeCount;
            HotDealsFragment.progressBar.setVisibility(View.VISIBLE);
            StringRequest request = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jSONObject = new JSONObject(new String(response));
                                String Status = jSONObject.optString("status");
                                if (Integer.parseInt(Status) == 1) {
                                    textView.setText(jSONObject.getString("likes"));
                                } else {
                                    Toast.makeText(context, jSONObject.getString("message").toString(), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Log.e("", e.getMessage());
                            }
                            HotDealsFragment.progressBar.setVisibility(View.GONE);
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    HotDealsFragment.progressBar.setVisibility(View.GONE);
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("XAPIKEY", "XXXXX");
                    params.put("deal_id", Dealid);
                    params.put("user_id", SessionManager.getUserID(context));
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
        } else {
            Toast.makeText(activity, R.string.ConnectionErrorResponse, Toast.LENGTH_LONG).show();
        }
    }
}

