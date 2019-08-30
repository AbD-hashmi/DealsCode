package com.hmi.dealsnxt.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.hmi.dealsnxt.Activity.LandingNewActivity;
import com.hmi.dealsnxt.Activity.LocationActivity;
import com.hmi.dealsnxt.HelperClass.SessionManager;
import com.hmi.dealsnxt.Model.CityModel;
import com.hmi.dealsnxt.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MultiSelectSearchCity extends RecyclerView.Adapter<MultiSelectSearchCity.MyViewHolder> {

    public static ArrayList<CityModel> usersList=new ArrayList<>();
    public ArrayList<CityModel> selected_usersList=new ArrayList<>();
    public static String searchText = "";
    private static List<CityModel> filterList;
    public static int count;
    static Context mContext;
    private static Activity activity;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView posting, name,type;
        public LinearLayout ll_listitem;

        public MyViewHolder(View view) {
            super(view);
            posting = (TextView) view.findViewById(R.id.tv_posting);
            name = (TextView) view.findViewById(R.id.tv_user_name);
            type = (TextView) view.findViewById(R.id.type);
            ll_listitem=(LinearLayout)view.findViewById(R.id.ll_listitem);

        }
    }


    public MultiSelectSearchCity(Context context, ArrayList<CityModel> userList, ArrayList<CityModel> selectedList, Activity activity1) {
        this.mContext=context;
        this.usersList = userList;
        this.selected_usersList = selectedList;

        this.filterList = new ArrayList<CityModel>();
        activity=activity1;
        // we copy the original list to the filter list and use it for setting row values
        this.filterList.addAll(this.usersList);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final CityModel movie = filterList.get(position);
        holder.name.setText(movie.getName());
        holder.posting.setText(movie.getcityid());
        holder.type.setText(movie.getType());

        String tti = String.valueOf(Html.fromHtml(movie.getName()));
        SpannableStringBuilder sb = new SpannableStringBuilder(tti);
        if (searchText != null) {
            if (searchText.length() > 0) {
                //color your text here

                int index = tti.indexOf(searchText);
                Pattern pattern = Pattern.compile(searchText,
                        Pattern.CASE_INSENSITIVE);

                //giving the compliled pattern to matcher to find matching pattern in cursor text
                Matcher matcher = pattern.matcher(tti);
                sb.setSpan(new BackgroundColorSpan(
                                Color.TRANSPARENT), 0, sb.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                while (matcher.find()) {
                       /* ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(158, 158, 158)); //specify color here
                        sb.setSpan(fcs, index, index + searchText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        index = tti.indexOf(searchText, index + 1);*/
                    try {
                        sb.setSpan(new BackgroundColorSpan(
                                        Color.YELLOW), matcher.start(), matcher.end(),
                                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                holder.name.setText(sb);
            } else {


                holder.name.setText(Html.fromHtml(movie.getName()));
            }

            holder.ll_listitem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  //  Toast.makeText(activity,movie.getName(),Toast.LENGTH_LONG).show();

                    if (movie.getType().equals("city"))
                    {
                        SessionManager.setCityid(activity,movie.getcityid());
                        SessionManager.setlocation_name(activity, movie.getName());
                        SessionManager.setIsloc(activity, true);
                        LocationActivity.location_nameis = "";
                        if (SessionManager.getIsloc(activity) == false) {
                            Intent i = new Intent(activity, LandingNewActivity.class);
                            activity.startActivity(i);
                            activity.finish();
                        } else {
                            Intent i = new Intent(activity, LandingNewActivity.class);
                            activity.startActivity(i);
                            activity.finish();
                        }
                    }
                    else
                    {
                        SessionManager.setCityid(activity, "");
                        SessionManager.setLatitude(activity, movie.getLat());
                        SessionManager.setLongitude(activity, movie.getLang());
                        SessionManager.setIsloc(activity, true);
                        SessionManager.setlocation_name(activity, movie.getName());
                        LocationActivity.location_nameis = movie.getName();
                        if (SessionManager.getIsloc(activity) == false) {
                            Intent i = new Intent(activity, LandingNewActivity.class);
                            activity.startActivity(i);
                            activity.finish();
                        } else {
                            Intent i = new Intent(activity, LandingNewActivity.class);
                            activity.startActivity(i);
                            activity.finish();
                        }

                    }
                }
            });

        }

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
                    for (CityModel item : usersList) {
                        if (item.getName().toLowerCase().contains(text.toLowerCase())) {
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

                            LocationActivity.seacrhcityAdpater.notifyDataSetChanged();


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

