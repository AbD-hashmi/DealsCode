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
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hmi.dealsnxt.HelperClass.SessionManager;
import com.hmi.dealsnxt.Model.DealDetailsModel;
import com.hmi.dealsnxt.Model.ListModel;
import com.hmi.dealsnxt.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class OutletsDeallistAdaptor extends RecyclerView.Adapter<OutletsDeallistAdaptor.SimpleItemViewHolder> {
    private List<ListModel> items;
    public Activity activity;
    public static int count = 0;
    android.app.AlertDialog alert;
    public static int Dealid;
    com.nostra13.universalimageloader.core.ImageLoader imageLoader;
    DisplayImageOptions options;
    static int clickcount = 0;
    //   public static String Itemposition;
    public int totaldiscount = 0;
    public int totalprice = 0;
    public int itemposition = 0;
    public int qty = 0;
    View promptView;
    public static String Outletname = "", Outletadress = "", timein = "", timeout = "", Dealdesc = "";
    public Context context;
    public static String Dealimg = "";
    ArrayList<DealDetailsModel> orderarrayList=new ArrayList<>();


    // Provide a reference to the views for each data item
// Provide access to all the views for a data item in a view holder
    public final static class SimpleItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvdiscount, tvdealname, tvavaildate, tvactualprice, tvafterdisprice, tvcount, tvmore, tvavailtime;
        ImageView ivadd, ivminus;
        // Spinner spinnermore;
        View viewline;
        RelativeLayout RLdeal, RLdata;
        ImageView spinnermore;
        LinearLayout LLcount;

        public SimpleItemViewHolder(View itemView) {
            super(itemView);
            RLdata = (RelativeLayout) itemView.findViewById(R.id.RLdata);
            RLdeal = (RelativeLayout) itemView.findViewById(R.id.Rldeal);
            tvdiscount = (TextView) itemView.findViewById(R.id.tvdiscount);
            tvdealname = (TextView) itemView.findViewById(R.id.tvdealname);
            tvavaildate = (TextView) itemView.findViewById(R.id.tvavaildate);
            viewline = (View) itemView.findViewById(R.id.viewline);
            tvactualprice = (TextView) itemView.findViewById(R.id.tvactualprice);
            tvafterdisprice = (TextView) itemView.findViewById(R.id.tvafterdisprice);
            ivminus = (ImageView) itemView.findViewById(R.id.ivminus);
            tvcount = (TextView) itemView.findViewById(R.id.tvcount);
            ivadd = (ImageView) itemView.findViewById(R.id.ivadd);
            tvmore = (TextView) itemView.findViewById(R.id.tvmore);
            LLcount = (LinearLayout) itemView.findViewById(R.id.LLcount);
            tvavailtime = (TextView) itemView.findViewById(R.id.tvavailtime);
            //   spinnermore = (Spinner) itemView.findViewById(R.id.spinnermore);
            //spinnermore = (ImageView) itemView.findViewById(R.id.spinnermore);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public OutletsDeallistAdaptor(Context context, List<ListModel> items, Activity _activity) {
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
        itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_dealmenu, viewGroup, false);
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
        viewHolder.tvdiscount.setText(items.get(position).getDiscountpercent() + " " + "off");
        viewHolder.tvdealname.setText("on" + " " + items.get(position).getDealname());
        //    viewHolder.tvavaildate.setText(items.get(position).getDealday());
        viewHolder.tvactualprice.setText("\u20B9" + items.get(position).getActualprice());
        viewHolder.tvafterdisprice.setText("\u20B9" + items.get(position).getAfterdiscountprice());
        final int actualprice = Integer.valueOf(items.get(position).getActualprice());
        final int discountedprice = Integer.valueOf(items.get(position).getAfterdiscountprice());
        viewHolder.tvactualprice.setPaintFlags(viewHolder.tvactualprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        Dealimg = (items.get(position).getDealimg());
        viewHolder.tvavailtime.setText(items.get(position).getOutletintime() + "-" + items.get(position).getOutletouttime());
        viewHolder.ivadd.setTag(position);
        viewHolder.ivminus.setTag(position);




        viewHolder.RLdeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                promptView = layoutInflater.inflate(R.layout.activity_detail_popup, null);
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(activity);
                alertDialogBuilder.setView(promptView);
                alert = alertDialogBuilder.create();
                alert.setCancelable(true);
                alert.show();

                //   final RelativeLayout view = (RelativeLayout) promptView.findViewById(R.id.view);
                final ImageView cross = (ImageView) promptView.findViewById(R.id.cross);
                final RelativeLayout Rl = (RelativeLayout) promptView.findViewById(R.id.Rl);
                final TextView tvdiscount = (TextView) promptView.findViewById(R.id.tvdiscount);
                final TextView tvdealname = (TextView) promptView.findViewById(R.id.tvdealname);
                final TextView tvactualprice = (TextView) promptView.findViewById(R.id.tvactualprice);
                final TextView tvafterdisprice = (TextView) promptView.findViewById(R.id.tvafterdisprice);
                final TextView tvsaveamt = (TextView) promptView.findViewById(R.id.tvsaveamt);
            /*    final TextView tvexpire = (TextView) promptView.findViewById(R.id.tvexpire);
                final TextView tvtimeout = (TextView) promptView.findViewById(R.id.tvtimeout);*/
                final TextView tvdesc = (TextView) promptView.findViewById(R.id.tvdesc);
                tvdesc.setMovementMethod(new ScrollingMovementMethod());
                tvactualprice.setPaintFlags(tvactualprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                tvdesc.setText(Html.fromHtml(items.get(position).getDesciption()));
                tvdiscount.setText(items.get(position).getDiscountpercent() + " " + "off");
                tvdealname.setText("" + items.get(position).getDealname());
                tvactualprice.setText("\u20B9" + items.get(position).getActualprice());
                tvafterdisprice.setText("\u20B9" + items.get(position).getAfterdiscountprice());
                int a = (Integer.valueOf(items.get(position).getActualprice().toString())) - Integer.valueOf(items.get(position).getAfterdiscountprice().toString());
                tvsaveamt.setText("\u20B9" + a);
                //     tvexpire.setText(items.get(position).getDesciption());
                //  tvtimeout.setText(items.get(position).getDesciption());
                cross.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.dismiss();
                    }
                });
            }
        });

        viewHolder.ivadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                if (pos == position) {


                    if (clickcount > 29) {
                        Toast.makeText(activity, "You have choose max oder", Toast.LENGTH_LONG).show();
                    } else {
                        clickcount = clickcount + 1;
                        viewHolder.tvcount.setText("" + clickcount);
                        totaldiscount = clickcount * discountedprice;
                        totalprice = clickcount * actualprice;
                        ListModel.setListModel(items.get(position));
                        //     ListModel.getListModel().save();
                        items.get(position).setCount(clickcount + "");
                        itemposition = Integer.valueOf(items.get(position).getDealid());
                        Intent i = new Intent("ALERT_CHANGE");
                        i.putExtra("data", totaldiscount);
                        i.putExtra("dataprice", totalprice);
                        i.putExtra("itempos", itemposition);
                        i.putExtra("qty", clickcount);
                      //  activity.sendBroadcast(i);
                        //  viewHolder.ivadd.setTag(1);
                        Dealdesc = (Html.fromHtml(items.get(position).getDesciption())).toString();
                        //    viewHolder.tvafterdisprice.setText("\u20B9" + totaldiscount);
                        //    ArrayList<DealDetailsModel> arrayList1 = new ArrayList<DealDetailsModel>();

                        DealDetailsModel bm = new DealDetailsModel();
                        bm.setDealname(items.get(position).getDealname());
                        bm.setDealid(items.get(position).getDealid());
                        bm.setDiscountpercent(items.get(position).getDiscountpercent());
                        bm.setActualprice(items.get(position).getActualprice());
                        bm.setAfterdiscountprice(items.get(position).getAfterdiscountprice());
                        bm.setDealQTY(clickcount + "");
                        if (clickcount>0)
                        {

                            orderarrayList.add(bm);

                        }
                        else {

                            orderarrayList.remove(bm);
                        }
                        i.putExtra("order_array", orderarrayList);
                        activity.sendBroadcast(i);
                    }
                }
            }
        });

        viewHolder.ivminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                if (pos == position) {
                    //do your stuff

                    if (clickcount == 0) {
                        //    viewHolder.tvafterdisprice.setText("\u20B9" + items.get(position).getAfterdiscountprice());
                    } else {
                        clickcount = clickcount - 1;
                        viewHolder.tvcount.setText("" + clickcount);
                        totalprice = clickcount * actualprice;
                        ListModel.setListModel(items.get(position));
                        //   totalcount = clickcount * Integer.valueOf(viewHolder.tvafterdisprice.getText().toString());
                        totaldiscount = clickcount * discountedprice;
                 /*   Intent i = new Intent("ALERT_CHANGE");
                    i.putExtra("DATA", "News");
                    .sendBroadcast(i);*/
                        items.get(position).setCount(clickcount + "");
                        itemposition = Integer.valueOf(items.get(position).getDealid());
                        Intent i = new Intent("ALERT_CHANGE");
                        i.putExtra("data", totaldiscount);
                        i.putExtra("dataprice", totalprice);
                        i.putExtra("itempos", itemposition);
                        i.putExtra("qty", clickcount);


                        //    viewHolder.ivminus.setTag(2);
                        Dealdesc = (Html.fromHtml(items.get(position).getDesciption())).toString();
                        DealDetailsModel bm = new DealDetailsModel();
                        bm.setDealname(items.get(position).getDealname());
                        bm.setDealid(items.get(position).getDealid());
                        bm.setDiscountpercent(items.get(position).getDiscountpercent());
                        bm.setActualprice(items.get(position).getActualprice());
                        bm.setAfterdiscountprice(items.get(position).getAfterdiscountprice());
                        bm.setDealQTY(clickcount + "");
                        if (clickcount>0)
                        {

                            orderarrayList.add(bm);

                        }
                        else {

                            orderarrayList.remove(bm);
                        }
                        i.putExtra("order_array", orderarrayList);
                        activity.sendBroadcast(i);

                    }
                }
            }
        });





    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 0;

      /*  if (Integer.valueOf(items.get(position).getEventScheduleType()) == 1) {
            viewType = 1;
        } else {
            viewType = 0;
        }*/

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


}