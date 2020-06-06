package com.ykpylcn.kutubisittehadisler_v1.db;


import android.content.Context;
import android.content.SharedPreferences;
import android.text.method.KeyListener;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import com.ykpylcn.kutubisittehadisler_v1.R;

import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SeekBar;
import android.widget.TextView;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class HadisViewFlipperAdapter extends BaseAdapter implements Filterable {
    private ArrayList<Hadis> hadisler;
    private ArrayList<Hadis> hadislerTempFiltered;
    private Context mContext;
    int textSizeHadis=16;
    SharedPreferences refTextSize;
    public HadisViewFlipperAdapter(Context context, ArrayList<Hadis> hadisler) {
        this.mContext = context;
        this.hadisler = hadisler;
        this.hadislerTempFiltered = hadisler;
        this.refTextSize=mContext.getSharedPreferences("refTextSize",0);
        this.textSizeHadis=refTextSize.getInt("HadisTextSize",textSizeHadis);

    }
    @Override
    public int getCount() {
        return hadislerTempFiltered.size();
    }



    @Override
    public Object getItem(int position) {

        return hadislerTempFiltered.get(position);
    }


    public long getItemId(int position) {

        if(hadislerTempFiltered.size()==0)
            return -1;
        return  hadislerTempFiltered.get(position).getHadisNo();
    }


//    public int getItemIndexByHadisID(int HadisID) {
//
//        return  hadislerTempFiltered.indexOf(getItem(HadisID));
//    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).
                    inflate(R.layout.hadis_list_row, parent, false);
        }

        Hadis hadis = hadislerTempFiltered.get(position);
        final TextView textHadis = convertView.findViewById(R.id.txt_hadis);
        textHadis.setText(hadis.getHadis());
        textHadis.setMovementMethod(new ScrollingMovementMethod());
        TextView text_kaynak = convertView.findViewById(R.id.text_kaynak);
        text_kaynak.setText(hadis.getKaynak());
        text_kaynak.setMovementMethod(new ScrollingMovementMethod());
        TextView textHadisNo = convertView.findViewById(R.id.txt_hadisno);
        textHadisNo.setText("Hadis No: "+String.valueOf(hadis.getHadisNo()));
        TextView text_rivayet = convertView.findViewById(R.id.text_rivayet);
        text_rivayet.setText(hadis.getRivayet());
//        textHadisNo.setText(String.valueOf(hadis.getHadisNo()));

//        textHadisNo.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Message.show(mContext,"long basildi");
//
//                return false;
//            }
//        });

//        textHadisNo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
////                if(KeyEvent.KEYCODE_ENTER==66){
//                    Message.show(mContext,"entere basildi");
////                    textHadisNo.setVisibility(View.VISIBLE);
//
//                return false;
//            }
//        });
        final SharedPreferences.Editor editor=refTextSize.edit();

        textHadis.setTextSize(textSizeHadis);
        SeekBar seekBar;
        seekBar=convertView.findViewById(R.id.seekBar);
        seekBar.setProgress(textSizeHadis);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textHadis.setTextSize(progress);
                textSizeHadis=progress;
                editor.putInt("HadisTextSize",progress);
                editor.commit();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String query = charSequence.toString();

                ArrayList<Hadis> filtered = new ArrayList<>();

                if (query.isEmpty()) {
                    filtered = hadisler;
                } else {
                    for (Hadis hadis : hadisler) {
                        if (hadis.getHadis().toLowerCase().contains(query.toLowerCase())) {
                            filtered.add(hadis);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.count = filtered.size();
                results.values = filtered;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                hadislerTempFiltered = (ArrayList<Hadis>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}