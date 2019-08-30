package com.hmi.dealsnxt.Utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;


/**
 * Created by Kuldeep Saini  on 29-05-2017.
 */

public class Customutils {

    private static AtomicLong idCounter = new AtomicLong();
    public static int createID()
    {
        List<Integer> numbers = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            numbers.add(i);
        }

        Collections.shuffle(numbers);

        String result = "";
        for(int i = 0; i < 4; i++){
            result += numbers.get(i).toString();
        }
        System.out.println(result);


        return Integer.valueOf(result);
       // return (int)(Math.random()*9000)+1000;
        //return Integer.parseInt(String.valueOf(idCounter.getAndIncrement()));
    }

    public static String longToDate(Long TimeinMilliSeccond)
    {
      //
      //  return new SimpleDateFormat("d MMM yyyy").format(new Date(TimeinMilliSeccond));

        return new SimpleDateFormat("dd MMM yyyy").format(new Date(TimeinMilliSeccond));
    }
    public static String longToDate1(Long TimeinMilliSeccond)
    {
        //
        //  return new SimpleDateFormat("d MMM yyyy").format(new Date(TimeinMilliSeccond));

        return new SimpleDateFormat("dd MMM yyyy hh:mm a").format(new Date(TimeinMilliSeccond));
    }
    public  static Long dateToLong()
    {

        /*long yourmilliseconds = 1274883865399L;
        long droppedMillis = 1000 * (yourmilliseconds/ 1000);
        System.out.println(droppedMillis)*/;

        Date date = new Date(System.currentTimeMillis());
        return date.getTime();
    }
    public  static Long dateToLongCus(String date1)
    {



        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        TimeZone timeZone = TimeZone.getDefault();
        sdf.setTimeZone(timeZone);
        Date date = null;
        try {
            date = sdf.parse(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long startDate = date.getTime();
        //Date date = new Date(date1);
        return startDate;
    }
    public  static Long dateToLongCuscal(String date1)
    {



        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy h:mm a", Locale.getDefault());
        TimeZone timeZone = TimeZone.getDefault();
        sdf.setTimeZone(timeZone);
        Date date = null;
        try {
            date = sdf.parse(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long startDate = date.getTime();
        //Date date = new Date(date1);
        return startDate;
    }
    public  static Long dateToLongclub(String date1)
    {



        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss aaa", Locale.getDefault());
        TimeZone timeZone = TimeZone.getDefault();
        sdf.setTimeZone(timeZone);
        Date date = null;
        try {
            date = sdf.parse(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long startDate = date.getTime();
        //Date date = new Date(date1);
        return startDate;
    }

    public static boolean checktimings(String starttime1, String endtime1, String asktime)  {
        try {

            StringBuilder sb = new StringBuilder(starttime1);

            sb.delete(5, 8);
            StringBuilder sb1 = new StringBuilder(endtime1);

            sb1.delete(5, 8);

           // String result = sb.toString();

            String starttime=sb.toString();
            String endtime=sb1.toString();
           // starttime = starttime.substring(0, 5) + " " + starttime.substring(5, starttime.length());
           // endtime = endtime.substring(0, 5) + " " + endtime.substring(5, endtime.length());

        String patternclub = "hh:mm aa";
        String patternask = "hh:mm aa";
        SimpleDateFormat sdfclub = new SimpleDateFormat(patternclub);
        SimpleDateFormat sdfask = new SimpleDateFormat(patternask);
        SimpleDateFormat inFormat = new SimpleDateFormat("hh:mm aa");
            SimpleDateFormat clubinFormat = new SimpleDateFormat("hh:mm aa");
        SimpleDateFormat outFormat = new SimpleDateFormat("HH:mm");
        String time24 = outFormat.format(inFormat.parse(asktime));
            String clunstsrt=outFormat.format(clubinFormat.parse(starttime));
            String clunend=outFormat.format(clubinFormat.parse(endtime));
        Log.e("error is",""+time24+clunstsrt+clunend);

            Date startdate = sdfclub.parse(starttime);
            Date enddate = sdfclub.parse(endtime);
            Date askdate = sdfask.parse(asktime);

            if(askdate.before(startdate) || askdate.after(enddate) ) {
                return true;
            } else {

                return false;
            }
        } catch (ParseException e){
            e.printStackTrace();
        }
        return false;
    }
    public  static Long dateToLongask(String date1)
    {



        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm aaa", Locale.getDefault());
        TimeZone timeZone = TimeZone.getDefault();
        sdf.setTimeZone(timeZone);
        Date date = null;
        try {
            date = sdf.parse(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long startDate = date.getTime();
        //Date date = new Date(date1);
        return startDate;
    }
    public  static Long dateToLongCuscal2(String date1)
    {



        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        TimeZone timeZone = TimeZone.getDefault();
        sdf.setTimeZone(timeZone);
        Date date = null;
        try {
            date = sdf.parse(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long startDate = date.getTime();
        //Date date = new Date(date1);
        return startDate;
    }
    public static String TimeToDate(Long TimeinMilliSeccond)
    {


        return new SimpleDateFormat("HH:mm").format(new Date(TimeinMilliSeccond));
    }
    public static String TimeToDatePM(Long TimeinMilliSeccond)
    {


        return new SimpleDateFormat("h:mm a").format(new Date(TimeinMilliSeccond));
    }


    /*String dateStr = cursor.getString(cursor.getColumnIndex("start_time"));
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    String shortTimeStr = sdf.format(dateStr);*/
    public  static String hyper="Blood Pressure";
    public  static String ast="Weight";
    public  static String dia="Sugar Level";
    public  static String heart="Heart Rate";
    public  static String thy="Lung Capacity";

    public static long yesterday() {
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        return cal.getTimeInMillis();
    }
    public static long sevenday() {
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        return cal.getTimeInMillis()- TimeUnit.DAYS.toMillis(7);
    }

    public static String dateFormat(String date1)
    {

        String formattedDate="";
        DateFormat originalFormat = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat("h:mm a");
        Date date = null;
        try {
            StringBuilder sb=new StringBuilder(date1);
            sb.deleteCharAt(date1.length()-1);
            date = originalFormat.parse(sb.toString());
            formattedDate = targetFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  formattedDate;
    }
    public static String dateFormatouttime(String date1)
    {

        String formattedDate="";
        DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat("MM/dd");
        Date date = null;
        try {
            StringBuilder sb=new StringBuilder(date1);
            sb.deleteCharAt(date1.length()-1);
            date = originalFormat.parse(sb.toString());
            formattedDate = targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return  formattedDate;
    }

   /* public static String getDayOfMonthSuffix(final int n) {
        checkArgument(n >= 1 && n <= 31, "illegal day of month: " + n);
        if (n >= 11 && n <= 13) {
            return "th";
        }
        switch (n % 10) {
            case 1:  return "st";
            case 2:  return "nd";
            case 3:  return "rd";
            default: return "th";
        }
    }*/

}
