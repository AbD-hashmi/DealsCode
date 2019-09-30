package com.hmi.dealsnxt.Adaptor;

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

import com.hmi.dealsnxt.Activity.DetailNewActivity;
import com.hmi.dealsnxt.Model.CityModel;
import com.hmi.dealsnxt.Model.HotDealsModel;
import com.hmi.dealsnxt.Model.SearchModel;
import com.hmi.dealsnxt.R;
import com.hmi.dealsnxt.Utils.Common;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SearchOutletAdapter extends RecyclerView.Adapter<SearchOutletAdapter.MyViewHolder> {

    public static ArrayList<SearchModel> usersList=new ArrayList<>();
    public ArrayList<SearchModel> selected_usersList=new ArrayList<>();
    public static String searchText = "";
    private static List<SearchModel> filterList;
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
            ll_listitem=(LinearLayout)view.findViewById(R.id.ll_listitem);

        }
    }


    public SearchOutletAdapter(Context context, ArrayList<SearchModel> userList, ArrayList<SearchModel> selectedList, Activity activity1) {
        this.mContext=context;
        this.usersList = userList;
        this.selected_usersList = selectedList;

        this.filterList = new ArrayList<SearchModel>();
        activity=activity1;
        // we copy the original list to the filter list and use it for setting row values
        this.filterList.addAll(this.usersList);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_searchlist, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        SearchModel movie = filterList.get(position);
        holder.name.setText(movie.getOutlet_name());
        holder.posting.setText(movie.getCity_id());

        HotDealsModel hotDealsModel=new HotDealsModel();
        hotDealsModel.setOutletName(movie.getOutlet_name());
        hotDealsModel.setOutletid(movie.getCity_id());

        String tti = String.valueOf(Html.fromHtml(filterList.get(position).getOutlet_name()));
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


                holder.name.setText(Html.fromHtml(filterList.get(position).getOutlet_name()));
            }

        }
        holder.ll_listitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.hideKeyboard(holder.ll_listitem,activity);
                Intent i = new Intent(activity, DetailNewActivity.class);
                i.putExtra("outletid",""+ movie.getOutletid());
                activity.startActivity(i);

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
                    for (SearchModel item : usersList) {
                        if (item.getOutlet_name().toLowerCase().contains(text.toLowerCase())) {
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

