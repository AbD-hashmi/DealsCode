package com.hmi.dealsnxt.Adaptor;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hmi.dealsnxt.Model.OrderModel;
import com.hmi.dealsnxt.R;

import java.util.List;

public class OrderBookedDetailAdaptor extends RecyclerView.Adapter<OrderBookedDetailAdaptor.SimpleItemViewHolder> {
    private List<OrderModel> items;
    public Activity activity;

    // Provide a reference to the views for each data item
// Provide access to all the views for a data item in a view holder
    public final static class SimpleItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvdiscount, tvdealname, tvactualprice, tvafterdisprice, tv_orderno;
        RelativeLayout Rldeal;

        public SimpleItemViewHolder(View itemView) {
            super(itemView);
            //   LLbanner = (RelativeLayout) itemView.findViewById(R.id.LLbanner);

            Rldeal = (RelativeLayout) itemView.findViewById(R.id.Rldeal);
            tvdiscount = (TextView) itemView.findViewById(R.id.tvdiscount);
            tvdealname = (TextView) itemView.findViewById(R.id.tvdealname);
            tvactualprice = (TextView) itemView.findViewById(R.id.tvactualprice);
            tvafterdisprice = (TextView) itemView.findViewById(R.id.tvafterdisprice);
            tv_orderno = (TextView) itemView.findViewById(R.id.tv_orderno);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public OrderBookedDetailAdaptor(List<OrderModel> items, Activity _activity) {
        this.items = items;
        this.activity = _activity;
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
        itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_booked_order, viewGroup, false);
        return new SimpleItemViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    // Involves populating data into the item through holder
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final SimpleItemViewHolder viewHolder, final int position) {

        viewHolder.tvdealname.setText("on" + "" + items.get(position).getDealname());
        viewHolder.tv_orderno.setText("Qty" + items.get(position).getDealQty());
        viewHolder.tvdiscount.setText(items.get(position).getDealdiscountpernt() + " " + "%");
        viewHolder.tvactualprice.setText("\u20B9" + items.get(position).getDealpurchaseamount());
        viewHolder.tvafterdisprice.setText("\u20B9" + items.get(position).getDealpurchaseamount());
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 0;
        return viewType;
    }


}