package com.hmi.dealsnxt.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hmi.dealsnxt.Activity.LocationActivity;
import com.hmi.dealsnxt.Model.CityModel;
import com.hmi.dealsnxt.Model.TimeModel;
import com.hmi.dealsnxt.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TimeselsectionAdapter extends RecyclerView.Adapter<TimeselsectionAdapter.MyViewHolder> {

    public static ArrayList<TimeModel> usersList = new ArrayList<>();
    public ArrayList<TimeModel> selected_usersList = new ArrayList<>();
    public static String searchText = "";
    private static List<TimeModel> filterList;
    public static int count;
    static Context mContext;
    private static Activity activity;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView posting, name;
        public LinearLayout ll_listitem;

        public MyViewHolder(View view) {
            super(view);
            posting = (TextView) view.findViewById(R.id.tv_posting);
            name = (TextView) view.findViewById(R.id.tv_user_name);
            ll_listitem = (LinearLayout) view.findViewById(R.id.ll_listitem);
        }
    }

    public TimeselsectionAdapter(Context context, ArrayList<TimeModel> userList, ArrayList<TimeModel> selectedList, Activity activity1) {
        this.mContext = context;
        this.usersList = userList;
        this.selected_usersList = selectedList;

        this.filterList = new ArrayList<TimeModel>();
        activity = activity1;
        // we copy the original list to the filter list and use it for setting row values
        this.filterList.addAll(this.usersList);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_timelist, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        
        TimeModel movie = filterList.get(position);
        holder.name.setText(movie.getStart_time() + "-" + movie.getEnd_time());
        holder.posting.setText(movie.getId());

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.name.setBackgroundColor(mContext.getResources().getColor(R.color.yellowcol));
            }
        });


    }


    @Override
    public int getItemCount() {
        return (null != filterList ? filterList.size() : 0);
    }


    public static void filter(final String text) {
        // Searching could be complex..so we will dispatch it to a different thread...
        searchText = text;
        new Thread(new Runnable() {
            @Override
            public void run() {

                // Clear the filter list
                filterList.clear();

                // If there is no search value, then add all original list items to filter list
                if (TextUtils.isEmpty(text)) {

                    filterList.addAll(usersList);
                    count = 0;


                } else {
                    // Iterate in the original List and add it to filter list...
                    count++;
                    for (TimeModel item : usersList) {
                        if (item.getStart_time().toLowerCase().contains(text.toLowerCase())) {
                            // Adding Matched items
                            filterList.add(item);
                        }
                    }
                }

                // Set on UI Thread

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Notify the List that the DataSet has changed...

                        try {

                            if (filterList.size() == 0) {

                                // Toast.makeText(activity,"Search result not found",Toast.LENGTH_SHORT).show();
                                // RepeatSafeToast.show(activity, "Search result not found");
                            }
                        } catch (Exception e) {

                        }
                    }
                });

            }
        }).start();

    }
}

