package com.ykpylcn.kutubisittehadisler_v1.db;


import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ykpylcn.kutubisittehadisler_v1.App;
import com.ykpylcn.kutubisittehadisler_v1.R;
import com.ykpylcn.kutubisittehadisler_v1.utils.AlarmNotificationReceiver;
import com.ykpylcn.kutubisittehadisler_v1.utils.NotificationUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class NotifsAdapter extends RecyclerView.Adapter<NotifsAdapter.MyViewHolder> {

    private Context context;
    private List<Notif> notifsList;

    private FragmentActivity activity;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView notifHourMinute;
        public TextView notifDetay;
        public TextView dotnotif;
        public TextView notifdate;

        public MyViewHolder(View view) {
            super(view);
            notifHourMinute = view.findViewById(R.id.notifHourMinute);
            notifDetay = view.findViewById(R.id.tvDetay);
            dotnotif = view.findViewById(R.id.dotnotif);
            notifdate = view.findViewById(R.id.notifdate);
        }
    }


    public NotifsAdapter(Context context, List<Notif> notifsList, FragmentActivity activity) {
        this.context = context;
        this.notifsList = notifsList;
        this.activity=activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notifs_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Notif notif = notifsList.get(position);

        String h=String.valueOf(notif.Hour).length()<2?"0"+String.valueOf(notif.Hour):String.valueOf(notif.Hour);
        String m=String.valueOf(notif.Minute).length()<2?"0"+String.valueOf(notif.Minute):String.valueOf(notif.Minute);
        String isDaly=notif.IsDaily?"Hergün, ":"Birkere, ";
        String showType="Seçili Hadis";
        if(notif.HadisShowType==1)
            showType="Saklı Hadislerden Rastgele";
        else if(notif.HadisShowType==2)
            showType="Tüm Hadislerden Rastgele";
        else if(notif.HadisShowType==3)
            showType="Kırk Hadislerden Rastgele";



//        String text = "<font color=\"red\">Kurulu Değil!</font>";
        SpannableStringBuilder builder = new SpannableStringBuilder();

        builder.append("Saat: "+h+":"+ m);
        String red = " Kurulu Değil!";
        SpannableString redSpannable= new SpannableString(red);
        redSpannable.setSpan(new ForegroundColorSpan(Color.RED), 0, red.length(), 0);

        Intent myIntent= NotificationUtils.getIntent(activity,notif.HadisID);
        if(!NotificationUtils.checkNatification(myIntent,context))
            builder.append(redSpannable);

        holder.notifHourMinute.setText(builder, TextView.BufferType.SPANNABLE);

//        textView.setText(Html.fromHtml(text), BufferType.SPANNABLE);
        holder.notifDetay.setText(String.valueOf("Hatırlat:"+isDaly+showType));
        // Displaying dot from HTML character code
        holder.dotnotif.setText(Html.fromHtml("&#8226;"));
       // &#8227,&#8226

        // Formatting and displaying timestamp
        holder.notifdate.setText(formatDate(notif.Date));
    }

    @Override
    public int getItemCount() {
        return notifsList.size();
    }

    /**
     * Formatting timestamp to `MMM d` format
     * Input: 2018-02-21 00:15:42
     * Output: Feb 21
     */
    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("MMM d");
            return fmtOut.format(date);
        } catch (ParseException e) {
            return dateStr;
        }


    }
}