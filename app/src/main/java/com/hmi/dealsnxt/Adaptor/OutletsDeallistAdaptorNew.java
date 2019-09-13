package com.hmi.dealsnxt.Adaptor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
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
import android.widget.TextView;
import android.widget.Toast;

import com.hmi.dealsnxt.Activity.DetailNewActivity;
import com.hmi.dealsnxt.Activity.SingleOrderActivity;
import com.hmi.dealsnxt.HelperClass.SessionManager;
import com.hmi.dealsnxt.Model.DealDetailsModel;
import com.hmi.dealsnxt.Model.ListModel;
import com.hmi.dealsnxt.R;
import com.hmi.dealsnxt.Utils.Customutils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    View promptView;
    public static String Outletname = "", Outletadress = "", timein = "", timeout = "", Dealdesc = "";
    public Context context;
    public static String Dealimg = "";
    public String calculateadd;
    public String calculateminus;

    HashMap<Integer, Integer> dealMap = new HashMap<>();
    ArrayList<DealDetailsModel> orderarrayList = new ArrayList<>();
     EditText tvmob1 ;
     EditText tvmob2;

     EditText tvmob3;
     EditText tvmob4 ;
     EditText tvmob5 ;
     int pos;
     int deal_count;

    // Provide a reference to the views for each data item
// Provide access to all the views for a data item in a view holder
    public final static class SimpleItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvdiscount, tvdealname, tvavaildate, tvactualprice, tvafterdisprice, tvcount, tvmore, tvavailtime, ivgift;
        ImageView ivadd, ivminus;
        // Spinner spinnermore;
        View viewline;
        RelativeLayout RLdeal, RLdata;
        ImageView spinnermore,deal_image;
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
            ivgift=(TextView) itemView.findViewById(R.id.ivgift);
            deal_image=(ImageView) itemView.findViewById(R.id.deal_image);
            //   spinnermore = (Spinner) itemView.findViewById(R.id.spinnermore);
            //spinnermore = (ImageView) itemView.findViewById(R.id.spinnermore);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public OutletsDeallistAdaptorNew(Context context, List<ListModel> items, Activity _activity) {
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
        viewHolder.tvdealname.setText("" + items.get(position).getDealname());
        //    viewHolder.tvavaildate.setText(items.get(position).getDealday());
        viewHolder.tvactualprice.setText("\u20B9" + items.get(position).getActualprice());
        viewHolder.tvafterdisprice.setText("\u20B9" + items.get(position).getAfterdiscountprice());
        final int actualprice = Integer.valueOf(items.get(position).getActualprice());
        final int discountedprice = Integer.valueOf(items.get(position).getAfterdiscountprice());
        viewHolder.tvactualprice.setPaintFlags(viewHolder.tvactualprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        Dealimg = (items.get(position).getDealimg());
        viewHolder.tvavailtime.setText("Avail from: "+Customutils.dateFormat(items.get(position).getOutletintime()) + "-" + Customutils.dateFormat(items.get(position).getOutletouttime())
                +"  ("+items.get(position).getDealday()+")");
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
                    } else {
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
                        bm.setDealImge(items.get(position).getDealimgname());
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


                tvmob1.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        final int DRAWABLE_LEFT = 0;
                        final int DRAWABLE_TOP = 1;

                        final int DRAWABLE_RIGHT = 2;
                        final int DRAWABLE_BOTTOM = 3;

                        if(event.getAction() == MotionEvent.ACTION_UP) {
                            if(event.getRawX() >= (tvmob1.getRight() - tvmob1.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                                // your action here
                                Log.e("drwable click","yes");

                                Uri uri = Uri.parse("content://contacts");
                                Intent intent = new Intent(Intent.ACTION_PICK, uri);
                                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                                intent.putExtra("position",""+position);
                                DetailNewActivity origin = (DetailNewActivity)context;

                                origin.startActivityForResult(intent, DetailNewActivity.REQUEST_CODE);
                                pos=1;
                                return true;
                            }
                        }
                        return false;
                    }
                });

                tvmob2.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        final int DRAWABLE_LEFT = 0;
                        final int DRAWABLE_TOP = 1;

                        final int DRAWABLE_RIGHT = 2;
                        final int DRAWABLE_BOTTOM = 3;

                        if(event.getAction() == MotionEvent.ACTION_UP) {
                            if(event.getRawX() >= (tvmob1.getRight() - tvmob1.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                                // your action here
                                Log.e("drwable click","yes");

                                Uri uri = Uri.parse("content://contacts");
                                Intent intent = new Intent(Intent.ACTION_PICK, uri);
                                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                                intent.putExtra("position",""+position);
                                DetailNewActivity origin = (DetailNewActivity)context;

                                origin.startActivityForResult(intent, DetailNewActivity.REQUEST_CODE);
                                pos=2;
                                return true;
                            }
                        }
                        return false;
                    }
                });
                tvmob3.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        final int DRAWABLE_LEFT = 0;
                        final int DRAWABLE_TOP = 1;

                        final int DRAWABLE_RIGHT = 2;
                        final int DRAWABLE_BOTTOM = 3;

                        if(event.getAction() == MotionEvent.ACTION_UP) {
                            if(event.getRawX() >= (tvmob1.getRight() - tvmob1.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                                // your action here
                                Log.e("drwable click","yes");

                                Uri uri = Uri.parse("content://contacts");
                                Intent intent = new Intent(Intent.ACTION_PICK, uri);
                                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                                intent.putExtra("position",""+position);
                                DetailNewActivity origin = (DetailNewActivity)context;

                                origin.startActivityForResult(intent, DetailNewActivity.REQUEST_CODE);
                                pos=3;
                                return true;
                            }
                        }
                        return false;
                    }
                });
                tvmob4.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        final int DRAWABLE_LEFT = 0;
                        final int DRAWABLE_TOP = 1;

                        final int DRAWABLE_RIGHT = 2;
                        final int DRAWABLE_BOTTOM = 3;

                        if(event.getAction() == MotionEvent.ACTION_UP) {
                            if(event.getRawX() >= (tvmob1.getRight() - tvmob1.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                                // your action here
                                Log.e("drwable click","yes");

                                Uri uri = Uri.parse("content://contacts");
                                Intent intent = new Intent(Intent.ACTION_PICK, uri);
                                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                                intent.putExtra("position",""+position);
                                DetailNewActivity origin = (DetailNewActivity)context;

                                origin.startActivityForResult(intent, DetailNewActivity.REQUEST_CODE);
                                pos=4;
                                return true;
                            }
                        }
                        return false;
                    }
                });
                tvmob5.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        final int DRAWABLE_LEFT = 0;
                        final int DRAWABLE_TOP = 1;

                        final int DRAWABLE_RIGHT = 2;
                        final int DRAWABLE_BOTTOM = 3;

                        if(event.getAction() == MotionEvent.ACTION_UP) {
                            if(event.getRawX() >= (tvmob1.getRight() - tvmob1.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                                // your action here
                                Log.e("drwable click","yes");

                                Uri uri = Uri.parse("content://contacts");
                                Intent intent = new Intent(Intent.ACTION_PICK, uri);
                                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                                intent.putExtra("position",""+position);
                                DetailNewActivity origin = (DetailNewActivity)context;

                                origin.startActivityForResult(intent, DetailNewActivity.REQUEST_CODE);
                                pos=5;
                                return true;
                            }
                        }
                        return false;
                    }
                });


                ivplus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int giftcount = Integer.parseInt(tvcountnumber.getText().toString());
                        giftcount = giftcount + 1;
                        if (giftcount > 1) {
                            ivsubstract.setImageResource(R.drawable.minus_dark);
                        } else {
                            ivsubstract.setImageResource(R.drawable.minus);
                        }

                        if (giftcount > 5) {
                            Toast.makeText(activity, "You have choose max giftee", Toast.LENGTH_LONG).show();
                        } else {
                            if (giftcount == 2) {
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
                            }

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

                        if (giftcount > 1) {

                          //  viewHolder.tvcount.setText("" + giftcount);
                            ivsubstract.setImageResource(R.drawable.minus_dark);
                            if (giftcount == 2) {
                                giftcount = giftcount - 1;
                                tvmob2.setVisibility(View.INVISIBLE);
                                tvmob2.setFocusable(true);
                                tvcountnumber.setText("" + giftcount);
                            } else if (giftcount == 3) {
                                giftcount = giftcount - 1;
                                tvmob3.setVisibility(View.INVISIBLE);
                                tvmob3.setFocusable(true);
                                tvcountnumber.setText("" + giftcount);
                            } else if (giftcount == 4) {
                                giftcount = giftcount - 1;
                                tvmob4.setVisibility(View.INVISIBLE);
                                tvmob4.setFocusable(true);
                                tvcountnumber.setText("" + giftcount);
                            } else if (giftcount == 5) {
                                giftcount = giftcount - 1;
                                tvmob5.setVisibility(View.INVISIBLE);
                                tvmob5.setFocusable(true);
                                tvcountnumber.setText("" + giftcount);
                            }
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
                else if (pos==2)
                {
                    tvmob2.setText(""+number);
                }
                else if (pos==3)
                {
                    tvmob3.setText(""+number);
                }
                else if (pos==4)
                {
                    tvmob4.setText(""+number);
                }
                else if (pos==5)
                {
                    tvmob5.setText(""+number);
                }


            }
        }
    }

}