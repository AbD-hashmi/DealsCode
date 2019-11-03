package com.hmi.dealsnxt.Adaptor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonIOException;
import com.hmi.dealsnxt.Activity.DetailNewActivity;
import com.hmi.dealsnxt.Activity.SingleOrderActivity;
import com.hmi.dealsnxt.HelperClass.Constaints;
import com.hmi.dealsnxt.HelperClass.SessionManager;
import com.hmi.dealsnxt.Model.DealDetailsModel;
import com.hmi.dealsnxt.Model.DealImagesModel;
import com.hmi.dealsnxt.Model.ListModel;
import com.hmi.dealsnxt.OnSwipeTouchListener;
import com.hmi.dealsnxt.R;
import com.hmi.dealsnxt.ScrollableTextView;
import com.hmi.dealsnxt.Utils.Common;
import com.hmi.dealsnxt.Utils.Customutils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.facebook.FacebookSdk.getApplicationContext;

public class OutletsDeallistAdaptorNew extends RecyclerView.Adapter<OutletsDeallistAdaptorNew.SimpleItemViewHolder> {
    private List<ListModel> items;
    public Activity activity;
    public static int count = 0;
    android.app.AlertDialog alert;
    public static int Dealid;
    com.nostra13.universalimageloader.core.ImageLoader imageLoader;
    DisplayImageOptions options;
    //static int clickcount = 0;
    //   public static String Itemposition;
    public int totaldiscount = 0;
    public int totalprice = 0;
    public int itemposition = 0;
    public int qty = 0;
    String getOutletId;
    View promptView;
    DetailNewActivity detailNewActivity;
    public static String Outletname = "", Outletadress = "", timein = "", timeout = "", Dealdesc = "";
    public Context context;
    public static String Dealimg = "";

    HashMap<Integer, Integer> dealMap = new HashMap<>();
    ArrayList<DealDetailsModel> orderarrayList = new ArrayList<>();
     EditText tvmob1 ;
     EditText tvmob2;

     EditText tvmob3;
     EditText tvmob4 ;
     EditText tvmob5 ;
     int pos;
     int numofdeals;
     int deal_count;

    // Provide a reference to the views for each data item
// Provide access to all the views for a data item in a view holder
    public final static class SimpleItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvdiscount, tvdealname, tvavaildate, tvactualprice, tvafterdisprice, tvcount, tvmore, tvdescription, ivgift;
        ImageView ivadd, ivminus;
        // Spinner spinnermore;
        View viewline;
        RelativeLayout RLdeal, RLdata;
        ImageView spinnermore,deal_image;
        LinearLayout LLcount;
        TextView textView;

        public SimpleItemViewHolder(View itemView) {
            super(itemView);
         //   RLdata = (RelativeLayout) itemView.findViewById(R.id.RLdata);
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
            tvdescription = (TextView) itemView.findViewById(R.id.tvdescription);
            ivgift=(TextView) itemView.findViewById(R.id.ivgift);
            deal_image=(ImageView) itemView.findViewById(R.id.deal_image);
            textView=(TextView)itemView.findViewById(R.id.tvSoldOut);

            //   spinnermore = (Spinner) itemView.findViewById(R.id.spinnermore);
            //spinnermore = (ImageView) itemView.findViewById(R.id.spinnermore);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public OutletsDeallistAdaptorNew(Context context, List<ListModel> items, Activity _activity, String outletId) {
        this.items = items;
        this.activity = _activity;
        this.context = context;
        this.getOutletId=outletId;
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
        viewHolder.tvdealname.setText("" + items.get(position).getDealname());
        //    viewHolder.tvavaildate.setText(items.get(position).getDealday());
        viewHolder.tvactualprice.setText("\u20B9" + items.get(position).getActualprice());
        viewHolder.tvafterdisprice.setText("\u20B9" + items.get(position).getAfterdiscountprice());
        final int actualprice = Integer.valueOf(items.get(position).getActualprice());
        final int discountedprice = Integer.valueOf(items.get(position).getAfterdiscountprice());
        viewHolder.tvactualprice.setPaintFlags(viewHolder.tvactualprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        Dealimg = (items.get(position).getDealimg());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            viewHolder.tvdescription.setText(Html.fromHtml(items.get(position).getDesciption(),Html.FROM_HTML_MODE_COMPACT));

        } else {
            viewHolder.tvdescription.setText(items.get(position).getDesciption());
           // viewHolder.tvdescription.setMovementMethod(new ScrollingMovementMethod());

        }

        if (items.get(position).getShowPercentage().equals("1") || items.get(position).getShowPercentage()=="1") {
            viewHolder.tvdealname.setText(items.get(position).getDiscountpercent()+" Off on " +items.get(position).getDealname());
        }else{
            viewHolder.tvdealname.setText(items.get(position).getDealname());
        }

        //loadData();
        if (items.get(position).getNumofOffers()==0){

            viewHolder.LLcount.setVisibility(View.GONE);
            viewHolder.textView.setVisibility(View.VISIBLE);
        }



       /* viewHolder.tvdescription.setMovementMethod(new ScrollingMovementMethod());
        viewHolder.tvdescription.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v.getId() == R.id.tvdescription) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });*/
      /*  ScrollableTextView.enableScroll(viewHolder.tvdescription);
        viewHolder.deal_image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ScrollableTextView.disableScroll(viewHolder.tvdescription);
                return false;
            }
        });*/
     /*   viewHolder.tvdescription.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                viewHolder.tvdescription.getParent().requestDisallowInterceptTouchEvent(true);

                return false;
            }
        });*/

        viewHolder.ivadd.setTag(position);
        viewHolder.ivminus.setTag(position);
        imageLoader.displayImage(items.get(position).getDealimg(),viewHolder.deal_image,options);


        final DealDetailsModel bm = new DealDetailsModel();


        viewHolder.tvmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                promptView = layoutInflater.inflate(R.layout.activity_detail_popup, null);
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(activity);
                alertDialogBuilder.setView(promptView);
                alertDialogBuilder.setCancelable(false);
                alert = alertDialogBuilder.create();
                alert.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                alert.show();
                //   final RelativeLayout view = (RelativeLayout) promptView.findViewById(R.id.view);
                final ImageView cross = (ImageView) promptView.findViewById(R.id.cross);
                final RelativeLayout Rl = (RelativeLayout) promptView.findViewById(R.id.Rl);
                final TextView tvdiscount = (TextView) promptView.findViewById(R.id.tvdiscount);
                final TextView tvdealname = (TextView) promptView.findViewById(R.id.tvdealname);
                final ImageView ivgift = (ImageView) promptView.findViewById(R.id.ivgift);
                final ImageView pop_deal_image = (ImageView) promptView.findViewById(R.id.pop_deal_image);
                imageLoader.displayImage(items.get(position).getDealimg(),pop_deal_image,options);
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

                DetailNewActivity detailNewActivity=new DetailNewActivity();


                 //items.get(position).getNumberofitem();
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
                    int dealCount = 0;
                    //  String calculate = "";


                    if (dealCount <= 0) {
                        //viewHolder.ivminus.setImageResource(R.drawable.minus_dark);

                    } else {
                        //viewHolder.ivminus.setImageResource(R.drawable.minus);
                    }

                    if (dealMap.containsKey(Integer.valueOf(items.get(position).getDealid())))
                        dealCount = dealMap.get(Integer.valueOf(items.get(position).getDealid()));

                    if (pos == position) {
                        if (dealCount > 29) {
                            Toast.makeText(activity, "You have choose max oder", Toast.LENGTH_LONG).show();
                        } else if (dealCount+1 <=items.get(position).getNumofOffers()){
                            dealCount = dealCount + 1;
                            viewHolder.tvcount.setText("" + dealCount);
                            totaldiscount = dealCount * discountedprice;
                            totalprice = dealCount * actualprice;
                            ListModel.setListModel(items.get(position));
                            items.get(position).setCount(dealCount + "");
                            itemposition = Integer.valueOf(items.get(position).getDealid());
                            Dealdesc = (Html.fromHtml(items.get(position).getDesciption())).toString();

                            Intent i = new Intent("ALERT_CHANGE");
                            i.putExtra("data", totaldiscount);
                            i.putExtra("dataprice", totalprice);
                            i.putExtra("itempos", itemposition);
                            i.putExtra("qty", dealCount);
                            i.putExtra("imgname", items.get(position).getDealimgname());
                            i.putExtra("start_time", items.get(position).getOutletintime());
                            i.putExtra("end_time", items.get(position).getOutletouttime());

                            //   i.putExtra("calculate", calculate);



                            bm.setDealname(items.get(position).getDealname());
                            bm.setDealid(items.get(position).getDealid());
                            bm.setDiscountpercent(items.get(position).getDiscountpercent());
                            bm.setActualprice(items.get(position).getActualprice());
                            bm.setAfterdiscountprice(items.get(position).getAfterdiscountprice());
                            bm.setDealQTY(dealCount + "");
                            bm.setDealImge(Constaints.BaseUrl +"application/public/uploads/deals/"+items.get(position).getDealimgname());
                            System.out.println("image outlet "+Constaints.BaseUrl +"application/public/uploads/deals/"+items.get(position).getDealimgname());
                            bm.setPercent(items.get(position).getDiscountpercent());
                            bm.setDescription(items.get(position).getDesciption());
                            bm.setTimeFrom(items.get(position).getOutletintime());
                            bm.setTimeTo(items.get(position).getOutletouttime());
                            bm.setDeal_date(items.get(position).getDealDate());
                            bm.setRefundable_policy(items.get(position).getRefundablePolicy());
                            bm.setShow_percentage(items.get(position).getShowPercentage());



                            if (dealCount > 0) {
                                orderarrayList.remove(bm);
                                orderarrayList.add(bm);
                            } else {
                                orderarrayList.remove(bm);
                            }
                            i.putExtra("order_array", SessionManager.setRecent1(orderarrayList, context));
                            activity.sendBroadcast(i);

                        } else {
                            viewHolder.LLcount.setVisibility(View.GONE);
                            viewHolder.textView.setVisibility(View.VISIBLE);
                            Toast.makeText(activity, " Can not add more deals into this order", Toast.LENGTH_SHORT).show();
                        }
                        dealMap.put(Integer.valueOf(items.get(position).getDealid()), dealCount);
                        //  int a = Integer.valueOf(calculateadd) + Integer.valueOf(DetailNewActivity.tvfinalamount.getText().toString());
                        //      DetailNewActivity.tvfinalamount.setText(a);
                    }
                }
            });

        viewHolder.ivminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                if (pos == position) {
                    int dealCount = Integer.parseInt(viewHolder.tvcount.getText().toString());
                    //   int dealCount = 0;
                    //  if (dealCount > 0) {
                    //  viewHolder.ivminus.setImageResource(R.drawable.minus_dark);
                    //  } else {
                    ////   viewHolder.ivminus.setImageResource(R.drawable.minus);
                    //   }
                    if (dealMap.containsKey(Integer.valueOf(items.get(position).getDealid())))
                        dealCount = dealMap.get(Integer.valueOf(items.get(position).getDealid()));
                    if (dealCount == 0) {
                    } else {
                        dealCount = dealCount - 1;
                        viewHolder.tvcount.setText("" + dealCount);
                        totalprice = dealCount * actualprice;
                        ListModel.setListModel(items.get(position));
                        totaldiscount = dealCount * discountedprice;
                        items.get(position).setCount(dealCount + "");
                        itemposition = Integer.valueOf(items.get(position).getDealid());
                        Dealdesc = (Html.fromHtml(items.get(position).getDesciption())).toString();

                        Intent i = new Intent("ALERT_CHANGE");
                        i.putExtra("data", totaldiscount);
                        i.putExtra("dataprice", totalprice);
                        i.putExtra("itempos", itemposition);
                        i.putExtra("qty", dealCount);
                        i.putExtra("imgname", items.get(position).getDealimgname());
                        i.putExtra("start_time", items.get(position).getOutletintime());
                        i.putExtra("end_time", items.get(position).getOutletouttime());
                        //    i.putExtra("calculate", calculate);


                        bm.setDealname(items.get(position).getDealname());
                        bm.setDealid(items.get(position).getDealid());
                        bm.setDiscountpercent(items.get(position).getDiscountpercent());
                        bm.setActualprice(items.get(position).getActualprice());
                        bm.setAfterdiscountprice(items.get(position).getAfterdiscountprice());
                        bm.setDealImge(items.get(position).getDealimgname());
                        bm.setPercent(items.get(position).getDiscountpercent());
                        bm.setDescription(items.get(position).getDesciption());
                        bm.setTimeFrom(items.get(position).getOutletintime());
                        bm.setTimeTo(items.get(position).getOutletouttime());
                        bm.setDeal_date(items.get(position).getDealDate());
                        bm.setRefundable_policy(items.get(position).getRefundablePolicy());
                        bm.setShow_percentage(items.get(position).getShowPercentage());

                        bm.setDealQTY(dealCount + "");

                        if (dealCount > 0) {
                          //  viewHolder.ivminus.setImageResource(R.drawable.minus_dark);
                        } else {
                            //viewHolder.ivminus.setImageResource(R.drawable.minus);
                        }
                        if (dealCount > 0) {
                            orderarrayList.remove(bm);
                            orderarrayList.add(bm);

                        } else {

                            orderarrayList.remove(bm);
                        }
                        i.putExtra("order_array", SessionManager.setRecent1(orderarrayList, context));
                        activity.sendBroadcast(i);
                    }
                    dealMap.put(Integer.valueOf(items.get(position).getDealid()), dealCount);
                    //   DetailNewActivity.tvfinalamount.setText(calculate);
                }
                //      DetailNewActivity.tvfinalamount.setText(Integer.valueOf(calculateminus));
            }
        });


        viewHolder.ivgift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater layoutInflater = LayoutInflater.from(context);
                promptView = layoutInflater.inflate(R.layout.activity_giftpopup, null);
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(activity);
                alertDialogBuilder.setView(promptView);
                alertDialogBuilder.setCancelable(true);
                alert = alertDialogBuilder.create();
                alert.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                alert.show();
                final ImageView ivsubstract = (ImageView) promptView.findViewById(R.id.ivsubstract);
                final ImageView ivplus = (ImageView) promptView.findViewById(R.id.ivplus);
                final ImageView pick_contact = (ImageView) promptView.findViewById(R.id.pick_contact);
                pick_contact.setVisibility(View.GONE);
                bm.setDealname(items.get(position).getDealname());
                bm.setDealid(items.get(position).getDealid());
                bm.setDiscountpercent(items.get(position).getDiscountpercent());
                bm.setActualprice(items.get(position).getActualprice());
                bm.setAfterdiscountprice(items.get(position).getAfterdiscountprice());
                bm.setDealImge(items.get(position).getDealimgname());
                bm.setPercent(items.get(position).getDiscountpercent());
                bm.setDescription(items.get(position).getDesciption());
                bm.setTimeFrom(items.get(position).getOutletintime());
                bm.setTimeTo(items.get(position).getOutletouttime());
                bm.setDeal_date(items.get(position).getDealDate());
                bm.setRefundable_policy(items.get(position).getRefundablePolicy());
                bm.setShow_percentage(items.get(position).getShowPercentage());
                bm.setDealQTY(1 + "");
                if (1 > 0) {
                    orderarrayList.remove(bm);
                    orderarrayList.add(bm);

                } else {

                    orderarrayList.remove(bm);
                }
                deal_count=1;
                SessionManager.setRecent1(orderarrayList, context);
                pick_contact.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri uri = Uri.parse("content://contacts");
                        Intent intent = new Intent(Intent.ACTION_PICK, uri);
                        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                        DetailNewActivity origin = (DetailNewActivity)context;
                        origin.startActivityForResult(intent, DetailNewActivity.REQUEST_CODE);
                    }
                });
                final TextView tvcountnumber = (TextView) promptView.findViewById(R.id.tvcountnumber);
                 tvmob1 = (EditText) promptView.findViewById(R.id.tvmob1);
                 tvmob2 = (EditText) promptView.findViewById(R.id.tvmob2);
                final TextView tvgift = (TextView) promptView.findViewById(R.id.tvgift);
                tvmob3 = (EditText) promptView.findViewById(R.id.tvmob3);
                tvmob4 = (EditText) promptView.findViewById(R.id.tvmob4);
                 tvmob5 = (EditText) promptView.findViewById(R.id.tvmob5);


                 ivplus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int giftcount = Integer.parseInt(tvcountnumber.getText().toString());
                        giftcount = giftcount + 1;
                        if (giftcount > 1) {
                            ivsubstract.setImageResource(R.drawable.minus);
                            tvcountnumber.setText(""+giftcount);
                        } else {
                            ivsubstract.setImageResource(R.drawable.minus);
                        }

                        if (giftcount > 5) {
                            Toast.makeText(activity, "You have choose max giftees", Toast.LENGTH_LONG).show();
                            ivplus.setEnabled(false);
                            return;
                        } else {
                            ivplus.setEnabled(true);
                           /* if (giftcount == 2) {
                                tvmob2.setVisibility(View.VISIBLE);
                                tvmob2.setFocusable(true);
                                tvcountnumber.setText("" + giftcount);
                            } else if (giftcount == 3) {
                                tvmob3.setVisibility(View.VISIBLE);
                                tvmob3.setFocusable(true);
                                tvcountnumber.setText("" + giftcount);
                            } else if (giftcount == 4) {
                                tvmob4.setVisibility(View.VISIBLE);
                                tvmob4.setFocusable(true);
                                tvcountnumber.setText("" + giftcount);
                            } else if (giftcount == 5) {
                                tvmob5.setVisibility(View.VISIBLE);
                                tvmob5.setFocusable(true);
                                tvcountnumber.setText("" + giftcount);
                            }*/

                            bm.setDealname(items.get(position).getDealname());
                            bm.setDealid(items.get(position).getDealid());
                            bm.setDiscountpercent(items.get(position).getDiscountpercent());
                            bm.setActualprice(items.get(position).getActualprice());
                            bm.setAfterdiscountprice(items.get(position).getAfterdiscountprice());
                            bm.setDealImge(items.get(position).getDealimgname());
                            bm.setPercent(items.get(position).getDiscountpercent());
                            bm.setDescription(items.get(position).getDesciption());
                            bm.setTimeFrom(items.get(position).getOutletintime());
                            bm.setTimeTo(items.get(position).getOutletouttime());
                            bm.setDeal_date(items.get(position).getDealDate());
                            bm.setRefundable_policy(items.get(position).getRefundablePolicy());
                            bm.setShow_percentage(items.get(position).getShowPercentage());
                            bm.setDealQTY(giftcount + "");


                            if (giftcount > 0) {
                                orderarrayList.remove(bm);
                                orderarrayList.add(bm);
                            } else {

                                orderarrayList.remove(bm);
                            }
                            deal_count=giftcount;
                            SessionManager.setRecent1(orderarrayList, context);
                        }
                    }
                });

                ivsubstract.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int giftcount = Integer.parseInt(tvcountnumber.getText().toString());
                        giftcount=giftcount-1;
                        if (giftcount > 1) {

                            tvcountnumber.setText(""+giftcount);
                          //  viewHolder.tvcount.setText("" + giftcount);
                            ivsubstract.setImageResource(R.drawable.minus);

                            bm.setDealname(items.get(position).getDealname());
                            bm.setDealid(items.get(position).getDealid());
                            bm.setDiscountpercent(items.get(position).getDiscountpercent());
                            bm.setActualprice(items.get(position).getActualprice());
                            bm.setAfterdiscountprice(items.get(position).getAfterdiscountprice());
                            bm.setDealImge(items.get(position).getDealimgname());
                            bm.setPercent(items.get(position).getDiscountpercent());
                            bm.setDescription(items.get(position).getDesciption());
                            bm.setTimeFrom(items.get(position).getOutletintime());
                            bm.setTimeTo(items.get(position).getOutletouttime());
                            bm.setDeal_date(items.get(position).getDealDate());
                            bm.setRefundable_policy(items.get(position).getRefundablePolicy());
                            bm.setShow_percentage(items.get(position).getShowPercentage());
                            bm.setDealQTY(giftcount + "");


                            if (giftcount > 0) {
                                orderarrayList.remove(bm);
                                orderarrayList.add(bm);

                            } else {

                                orderarrayList.remove(bm);
                            }
                            deal_count=giftcount;
                            SessionManager.setRecent1(orderarrayList, context);

                        } else {
                            ivsubstract.setImageResource(R.drawable.minus);
                          //  Toast.makeText(activity, "You have to choose min one giftee", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                tvgift.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        if (tvmob1.getText().toString().equals(""))
                        {
                            tvmob1.setError("Please enter mobile number");
                        }
                        else
                        {

                            tvcountnumber.getText().toString();

                            Intent i = new Intent(context, SingleOrderActivity.class);
                            i.putExtra("Dealid", Integer.parseInt(items.get(position).getDealid()));
                            i.putExtra("Qtycount", deal_count);
                            //todo gift apply
                            i.putExtra("Fromdiscount", Integer.parseInt(orderarrayList.get(0).getDealQTY()) * Integer.parseInt(orderarrayList.get(0).getAfterdiscountprice()));
                            i.putExtra("Dealimgname", orderarrayList.get(0).dealImge);
                            i.putExtra("Dealstarttime", items.get(position).getOutletintime());
                            i.putExtra("Dealendtime", items.get(position).getOutletouttime());
                            i.putExtra("order_list", SessionManager.setRecent1(orderarrayList, context));
                            context.startActivity(i);


                            alert.dismiss();
                        }

                    }
                });
            }
        });



    }

    public void loadData(){

        String url = Constaints.DealDetailbyOutlet;

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jSONObject = new JSONObject(new String(response));
                    int Status = jSONObject.optInt("status");
                    String Path = Constaints.BaseUrl + jSONObject.optString("outletCdnpath");
                    String Path_deal = Constaints.BaseUrl + jSONObject.optString("dealsCdnpath");
                    //       dealsModel.setDealsName(Constaints.BaseUrl + merchant.optString("dealsCdnpath"));
                    if (Status == 1) {
                        JSONArray infoArray = jSONObject.getJSONArray("info");
                        for (int i = 0; i < infoArray.length(); i++) {
                            //  ListModel listModel = new ListModel();
                            final JSONObject outlet = infoArray.getJSONObject(i);
                            JSONArray dealJsonArray = outlet.optJSONArray("image");
                            ArrayList<DealImagesModel> ImagesModels = new ArrayList<DealImagesModel>();
                            // DealImagesModel.deleteRecord("account" + accountJson.optInt("id"));
                            for (int j = 0; j < dealJsonArray.length(); j++) {
                                JSONObject review = dealJsonArray.optJSONObject(j);
                                DealImagesModel um = new DealImagesModel();
                                um.setImage_ID(review.optString("id"));
                                numofdeals=outlet.optInt("dealCount");


                                //       um.setOutlet_id(outlet.optString("id"));
                                um.setImage_name(review.optString("image_name"));
                                //  um.setImage_name(review.optString("image_name"));
                                um.setImages_url(Path + "/" + review.optString("image_name"));
                                ImagesModels.add(um);
                              //  dealimglist.add(um);
                            }
                            //       DealImagesModel.saveInTx(ImagesModels);

                        }

                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
              //  adapter = new OutletsDeallistAdaptorNew(DetailNewActivity.this, arrayList, DetailNewActivity.this,Qtycount);
                //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                // linearLayoutManager.setStackFromEnd(true);
                //viewlist.setLayoutManager(linearLayoutManager);

                //   viewlist.setLayoutManager(linearLayoutManager);
                //viewlist.setAdapter(adapter);
                //DetailNewActivity.CustomPagerAdapter mCustomPagerAdapter = new DetailNewActivity.CustomPagerAdapter(getApplicationContext(), dealimglist);
                //view_pager.setAdapter(mCustomPagerAdapter);
                /*After setting the adapter use the timer */


        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
           // progressBar.setVisibility(View.GONE);
            Log.e("error", "" + error);
            AlertDialog.Builder builder=new AlertDialog.Builder(getApplicationContext());
            builder.setTitle("Could Not Find Deal");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    //finish();
                }
            });
            builder.show();
        }
    }) {
        @Override
        protected Map<String, String> getParams() {
            Map<String, String> params = new HashMap<String, String>();
        //    Intent intent = getIntent();
            params.put("XAPIKEY", "XXXXX");
            //    params.put("user_id", SessionManager.getUserID(getApplicationContext()).toString());
            // params.put("id", HotDealsModel.getHotDealsModel().getOutletid());
            params.put("outlet_id", getOutletId);
            params.put("user_id", SessionManager.getUserID(getApplicationContext()));
            System.out.println("data details "+params);
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

    public  void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("MyAdapter", "onActivityResult");


        if (requestCode == DetailNewActivity.REQUEST_CODE) {
            if (resultCode == DetailNewActivity.RESULT_OK) {


                Uri uri = data.getData();
                String[] projection = { ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME };

                Cursor cursor = context.getContentResolver().query(uri, projection,
                        null, null, null);
                cursor.moveToFirst();

                int numberColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(numberColumnIndex);

                int nameColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                String name = cursor.getString(nameColumnIndex);

                Log.e("number is", "ZZZ number : " + number +" , name : "+name);
                if (pos==1)
                {
                    tvmob1.setText(""+number);
                }


            }
        }
    }

}