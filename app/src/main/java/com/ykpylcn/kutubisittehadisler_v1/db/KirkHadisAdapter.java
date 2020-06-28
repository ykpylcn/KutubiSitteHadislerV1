package com.ykpylcn.kutubisittehadisler_v1.db;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ykpylcn.kutubisittehadisler_v1.R;

import java.util.ArrayList;

public class KirkHadisAdapter extends ArrayAdapter<Hadis> implements View.OnClickListener {
    private ArrayList<Hadis> dataSet;
    Context mContext;

    @Override
    public void onClick(View v) {
        int position=(Integer) v.getTag();
        Object object= getItem(position);
        Hadis dataModel=(Hadis)object;

        switch (v.getId())
        {
            case R.id.hadis_name:

                break;
        }
    }

    // View lookup cache
    private static class ViewHolder {
        TextView txtHadisName;
        TextView txtKaynak;

        ImageView infoShare;

    }

    public KirkHadisAdapter(ArrayList<Hadis> data, Context context) {
        super(context, R.layout.kirkhadis_row_item, data);
        this.dataSet = data;
        this.mContext=context;

    }
    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Hadis dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        final ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.kirkhadis_row_item, parent, false);
            viewHolder.txtHadisName = (TextView) convertView.findViewById(R.id.hadis_name);
            viewHolder.txtKaynak = (TextView) convertView.findViewById(R.id.kaynak_name);

            viewHolder.infoShare = (ImageView) convertView.findViewById(R.id.item_info);


            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtHadisName.setText(dataModel.getHadis());
        viewHolder.txtKaynak.setText(dataModel.getKaynak());

        viewHolder.infoShare.setOnClickListener(this);
        viewHolder.infoShare.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
}
