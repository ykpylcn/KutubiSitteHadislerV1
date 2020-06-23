package com.ykpylcn.kutubisittehadisler_v1.db;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.text.method.KeyListener;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;
import android.widget.BaseAdapter;
import android.widget.EditText;

import com.ykpylcn.kutubisittehadisler_v1.App;
import com.ykpylcn.kutubisittehadisler_v1.R;
import com.ykpylcn.kutubisittehadisler_v1.ui.Message;
import com.ykpylcn.kutubisittehadisler_v1.utils.OnSwipeTouchListener;

import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;


public class HadisViewFlipperAdapter extends BaseAdapter {
    private ArrayList<Hadis> hadisler;
    private Context mContext;
    int textSizeHadis=16;
    SharedPreferences refTextSize;
    AdapterViewFlipper adapViewFlipper;
    public HadisViewFlipperAdapter(Context context, ArrayList<Hadis> hadisler, AdapterViewFlipper adapVFlipper) {
        this.mContext = context;
        this.hadisler = hadisler;
        this.refTextSize=mContext.getSharedPreferences("refTextSize",0);
        this.textSizeHadis=refTextSize.getInt("HadisTextSize",textSizeHadis);
        this.adapViewFlipper=adapVFlipper;
    }
    @Override
    public int getCount() {
        if(hadisler==null)
            return 0;
        return hadisler.size();
    }



    @Override
    public Object getItem(int position) {

        return hadisler.get(position);
    }


    public long getItemId(int position) {

        if(hadisler==null)
            return -1;
        if(hadisler.size()==0)
            return -1;
        return  hadisler.get(position).getHadisNo();
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

        final Hadis hadis = hadisler.get(position);
        final TextView textHadis = convertView.findViewById(R.id.txt_hadis);
        textHadis.setText(hadis.getHadis());
        textHadis.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody =hadis.getHadis();
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, hadis.getAltKonu());
                sharingIntent.putExtra(Intent.EXTRA_TITLE, hadis.getAltKonu());
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Intent chooserIntent = Intent.createChooser(sharingIntent, "Paylaş: ");
                chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                App.app_context.startActivity(chooserIntent);


//                Intent sharingIntent = new Intent(Intent.ACTION_VIEW);
//                sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                sharingIntent.setData(Uri.parse("http://muhaddis.org/cgi-bin/dbman/db.cgi?db=ks&uid=default&SNo="+hadis.getHadisNo()+"&mh=10&view_records=Sorgula"));
//
//                Intent chooserIntent = Intent.createChooser(sharingIntent, "Open With");
//                chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                App.app_context.startActivity(chooserIntent);
                return false;
            }
        });
        textHadis.setMovementMethod(new ScrollingMovementMethod());
        textHadis.setOnTouchListener(new OnSwipeTouchListener(mContext.getApplicationContext()) {
            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();

                Ileri();

            }
            @Override
            public void onSwipeRight() {
                super.onSwipeRight();

                Geri();

            }
        });
        LinearLayout linLayout = convertView.findViewById(R.id.linLayout);

        linLayout.setOnTouchListener(new OnSwipeTouchListener(mContext.getApplicationContext()) {
            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();

                Ileri();

            }
            @Override
            public void onSwipeRight() {
                super.onSwipeRight();

                Geri();

            }
        });
        TextView text_kaynak = convertView.findViewById(R.id.text_kaynak);
        text_kaynak.setText(hadis.getKaynak());
        text_kaynak.setMovementMethod(new ScrollingMovementMethod());
        TextView textHadisNo = convertView.findViewById(R.id.txt_hadisno);
        textHadisNo.setText(String.valueOf(hadis.getHadisNo())+". Hadis");
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

    private void Geri() {
        adapViewFlipper.stopFlipping();
        adapViewFlipper.setInAnimation(App.app_context, android.R.animator.fade_in);
//                    adapViewFlipper.setOutAnimation(con1, android.R.animator.fade_out);
        adapViewFlipper.showPrevious();
//                    CheckIsFavorite(adapViewFlipper.getDisplayedChild());
    }

    private void Ileri() {
        adapViewFlipper.stopFlipping();
        adapViewFlipper.setInAnimation(App.app_context, android.R.animator.fade_in);
        adapViewFlipper.setOutAnimation(App.app_context, android.R.animator.fade_out);
        adapViewFlipper.showNext();
    }


}