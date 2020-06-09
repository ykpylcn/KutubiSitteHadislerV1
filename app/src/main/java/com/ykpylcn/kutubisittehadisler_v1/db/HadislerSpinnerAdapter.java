package com.ykpylcn.kutubisittehadisler_v1.db;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ykpylcn.kutubisittehadisler_v1.R;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class HadislerSpinnerAdapter extends ArrayAdapter<Hadis> {
    private Context context;
    private int layoutResourceId;
    private List<Hadis> hadisMapList;
    public HadislerSpinnerAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Hadis> list) {
        super(context, resource,0, list);
        this.context = context;
        this.layoutResourceId = resource;
        this.hadisMapList = list;
    }

    @Override
    public View getDropDownView(int position,@Nullable View convertView,
                                @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
    @Override
    public @NonNull View getView(int position,@Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        final View view = mInflater.inflate(layoutResourceId, parent, false);

        TextView tvAnaKonu = (TextView) view.findViewById(R.id.tvAnaKonu);
        String text=hadisMapList.get(position).AnaKonu +" ("+hadisMapList.get(position).AltKonuSize+")";
        if(position!=0)
            tvAnaKonu.setText(text);
        else {
            text=" "+hadisMapList.get(position).AnaKonu ;
            tvAnaKonu.setText(Html.fromHtml("&#8283;") + text);
        }
        // &#8227,&#8226



        return view;


    }
//    static class ViewHolder {
//        TextView tvAnaKonu;
////        TextView txtViewName;
//    }

}
