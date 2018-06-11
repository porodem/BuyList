package com.porodem.buylist.cheque;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Cheque {

    public static final int TIME_AND_DAY = 0;
    public static final int MONTH = 1;
    public static final int YEAR = 2;
    public static final int TIME = 3;

    private static final String LOG = "Cheque";

    private Date mDate;
    //private String data;
    private String mLable;
    private int sum;

    public Cheque() {
        mDate = new Date();
        /*Locale locale = new Locale("ru", "RU");
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM kk:mm", locale);
        data = sdf.format(date);
        mDate = date;*/
    }

    public Cheque(String lable) {
        mLable = lable;
    }

    /*public String getData() {
        return data;
    }*/

    public void setDate(String date, String month, String year) {
        String pattern = "dd kk:mm MMM yyyy";
        String d = date + " " + month + " " + year;
        String dd = "11 11 2018";
        try {
            /*DateFormat df = new SimpleDateFormat(pattern, new Locale("ru","RU"));
            mDate = df.parse(dd);*/

            mDate = new SimpleDateFormat(pattern, new Locale("ru","RU")).parse(d);
            Log.d(LOG,"setDate() OK! - " + getDateString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }



    public String[] getDate() {
        String[] date = new String[4];
        date[TIME_AND_DAY] = partOfData("dd kk:mm",mDate);
        date[MONTH] = partOfData("MMM", mDate);
        date[YEAR] = partOfData("yyyy", mDate);
        date[TIME] = partOfData("kk:mm", mDate);
        return date;
    }
    public String getDateString() {
        return getDate()[MONTH] + " " +  getDate()[TIME_AND_DAY];
    }

    private String partOfData(String pattern, Date date) {
        Locale locale = new Locale("ru", "RU");
        SimpleDateFormat simpleDF = new SimpleDateFormat(pattern, locale);
        String dataPart = simpleDF.format(date);
        return dataPart;
    }
    public String getMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM");
        Log.d(LOG, "getMonth() = " + sdf.format(mDate));
        return sdf.format(mDate);
    }
    public String getYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Log.d(LOG, "getYear() = " + sdf.format(mDate));
        return sdf.format(mDate);
    }

    /*public void setData(String data) {
        this.data = data;
    }*/

    public String getLable() {
        return mLable;
    }

    public void setLable(String lable) {
        if (lable == null) {
            lable = "null";
        }
        this.mLable = lable;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
