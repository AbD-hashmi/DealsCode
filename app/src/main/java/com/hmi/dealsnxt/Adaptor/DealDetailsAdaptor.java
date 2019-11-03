package com.hmi.dealsnxt.Adaptor;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hmi.dealsnxt.Activity.DetailNewActivity;
import com.hmi.dealsnxt.Activity.SingleOrderActivity;
import com.hmi.dealsnxt.Model.DealDetailsModel;
import com.hmi.dealsnxt.Model.ListModel;
import com.hmi.dealsnxt.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DealDetailsAdaptor extends RecyclerView.Adapter<DealDetailsAdaptor.SimpleItemViewHolder> {
   // private List<DealDetailsModel> items;
   public  ArrayList<DealDetailsModel> items;
    public Activity activity;
    public String Qtycount;
    public int count = 0;
    public static int Dealid;
    com.nostra13.universalimageloader.core.ImageLoader imageLoader;
    DisplayImageOptions options;
    int clickcount = 0;
    public String img;


    // Provide a reference to the views for each data item
// Provide access to all the views for a data item in a view holder
    public final static class SimpleItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvdiscount, tvdealname, tvavaildate, tvactualprice, tvafterdisprice, tvcount, tvmore;
        ImageView ivadd, ivminus;
        // Spinner spinnermore;
        View viewline;
        RelativeLayout RLdeal;
        ImageView spinnermore;
        TextView tv_orderno;
        ImageView image;
        public SimpleItemViewHolder(View itemView) {
            super(itemView);
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
            //   spinnermore = (Spinner) itemView.findViewById(R.id.spinnermore);
            //   spinnermore = (ImageView) itemView.findViewById(R.id.spinnermore);
            tv_orderno = (TextView) itemView.findViewById(R.id.tv_orderno);
            image=(ImageView)itemView.findViewById(R.id.Dealimage);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public DealDetailsAdaptor(ArrayList<DealDetailsModel> items, Activity _activity, String Qtycount, String dealimgname) {
        this.items = items;
        this.activity = _activity;
        this.Qtycount = Qtycount;
        this.img=dealimgname;
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
        itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_deal_order, viewGroup, false);
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

        System.out.println("data adapter "+ items.get(position).getDealname());
        viewHolder.tvdiscount.setText(items.get(position).getDiscountpercent() + " " + "off");

        if (items.get(position).getShow_percentage().equals("1") || items.get(position).getShow_percentage()=="1") {
            viewHolder.tvdealname.setText(items.get(position).getDiscountpercent()+" Off on " +items.get(position).getDealname());

        }else{
            viewHolder.tvdealname.setText(items.get(position).getDealname());
        }

       // viewHolder.tvdealname.setText("" + items.get(position).getDealname());
        //    viewHolder.tvavaildate.setText(items.get(position).getDealday());
        viewHolder.tvactualprice.setText("\u20B9" + items.get(position).getAfterdiscountprice());
        viewHolder.tvafterdisprice.setText("( "+"\u20B9" + items.get(position).getAfterdiscountprice()+" x"
                +items.get(position).getDealQTY()+" )");
        viewHolder.tv_orderno.setText("Qty " + items.get(position).getDealQTY());
       // viewHolder.tvactualprice.setPaintFlags(viewHolder.tvactualprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        DetailNewActivity detailNewActivity=new DetailNewActivity();

        System.out.println("img "+items.get(position).dealImge);
        imageLoader.displayImage(items.get(position).dealImge,viewHolder.image,options);


        if (!items.get(position).dealImge.isEmpty()||!items.get(position).dealImge.equals("")) {
            imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
            options = new DisplayImageOptions.Builder()
                    .cacheOnDisc(true).cacheInMemory(true)
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .showImageOnLoading(R.drawable.banner).showImageForEmptyUri(R.drawable.banner).showImageOnFail(R.drawable.banner)
                    .build();
            imageLoader.init(ImageLoaderConfiguration.createDefault(activity));

        }else viewHolder.image.setImageAlpha(R.drawable.banner);
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


}