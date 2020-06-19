package com.ykpylcn.kutubisittehadisler_v1.ui.hadisler;


import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ykpylcn.kutubisittehadisler_v1.App;
import com.ykpylcn.kutubisittehadisler_v1.R;
import com.ykpylcn.kutubisittehadisler_v1.db.DBAdapter;
import com.ykpylcn.kutubisittehadisler_v1.db.Hadis;
import com.ykpylcn.kutubisittehadisler_v1.db.HadisViewFlipperAdapter;
import com.ykpylcn.kutubisittehadisler_v1.db.Note;
import com.ykpylcn.kutubisittehadisler_v1.ui.Dialogs;
import com.ykpylcn.kutubisittehadisler_v1.ui.Message;
import com.ykpylcn.kutubisittehadisler_v1.utils.AlarmNotificationReceiver;

import java.util.ArrayList;

public class HadislerFragment extends Fragment {


    private HadislerViewModel hadislerViewModel;

    BottomNavigationView bnavigate;
    Button before, next;
    AdapterViewFlipper adapViewFlipper;
    HadisViewFlipperAdapter hadisViewFlipperAdapter;
    SharedPreferences refindexVP;
    int indexVP=0;
    private float startX;
    int i=1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        hadislerViewModel =
                ViewModelProviders.of(this).get(HadislerViewModel.class);


//        if(savedInstanceState!=null)
//            indexVP=savedInstanceState.getInt("indexVP");
        refindexVP=App.app_context.getSharedPreferences("refindexVP",0);
        indexVP=refindexVP.getInt("refindexVPkey",indexVP);

        final View root = inflater.inflate(R.layout.fragment_hadisler, container, false);
//      final TextView textView = root.findViewById(R.id.text_home);
        hadislerViewModel.getHadisler().observe(getViewLifecycleOwner(), new Observer<ArrayList<Hadis>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Hadis> s) {


                FlipMethodCalistirbyAdapter(root,s);

            }
        });



        bnavigate=root.findViewById(R.id.bottom_navigation);
        bnavigate.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Dialogs dial = new Dialogs();
                long hadisNo=adapViewFlipper.getAdapter().getItemId(adapViewFlipper.getDisplayedChild());
                switch (item.getItemId()) {
                    case R.id.navigate_hadis_no:
                        ShowNavHadisNoDialog();
                        return true;
                    case R.id.navigation_note:

                        Note not= App.DbAdapter.getNoteByHadisID(hadisNo);

                        if(not!=null){

                            dial.showNoteDialog(true,not,not.getId(),getActivity(), (int) hadisNo);
                        }
                        else{
                            dial.showNoteDialog(false,null,0,getActivity(), (int) hadisNo);
                        }
                        return true;
                    case R.id.navigation_notifications:

                        Intent myIntent = new Intent(getActivity(), AlarmNotificationReceiver.class);
                        myIntent.putExtra("hadisid",hadisNo);
                        myIntent.setAction(String.valueOf(hadisNo));
                        dial.showNotificationDialog(checkNatification(myIntent,getActivity()),getActivity(), hadisNo,myIntent);
                        return true;
                    case R.id.fav_add:
                        UpdateHadisIsFav(hadisNo);
                        return true;


                }
                return false;
            }


        });


        return root;
    }



    private boolean checkNatification(Intent intent, Context activity){

        boolean alarmUp = (PendingIntent.getBroadcast(activity, 0,
                intent,
                PendingIntent.FLAG_NO_CREATE) != null);
        return  alarmUp;
    }
    private void UpdateHadisIsFav(long hadisId){

        Hadis hadis=App.DbAdapter.getHadis(hadisId);

        if(hadis!=null){

            if (hadis.getIsFav()){
                App.DbAdapter.updateHadisIsFav(hadisId,false);
                UpdateIconHadisIsFav(false);
                Message.show(getResources().getText(R.string.hint_fav_hadis_2).toString());

            }
            else{
                App.DbAdapter.updateHadisIsFav(hadisId,true);
                UpdateIconHadisIsFav(true);
                Message.show(getResources().getText(R.string.hint_fav_hadis_1).toString());
            }

        }else
            Message.show(getResources().getText(R.string.hint_enter_hadis_go2).toString());

    }
    private void CheckIsFavorite(long hadisId){

        Hadis hadis=App.DbAdapter.getHadis(hadisId);
        UpdateIconHadisIsFav(hadis==null?false:hadis.getIsFav());
    }
    private void UpdateIconHadisIsFav(boolean isfav){


        Menu menu = bnavigate.getMenu();
        MenuItem item;
            if (isfav){

                item= menu.findItem(R.id.fav_add);
                item.setIcon(R.drawable.ic_action_favorite);
                item.setTitle(R.string.hint_hadis_isfav_add);

            }
            else{
                item=menu.findItem(R.id.fav_add);
                item.setIcon(R.drawable.ic_action_favorite_border);
                item.setTitle(R.string.hint_hadis_isfav_remove);
            }



    }
    private void ShowNavHadisNoDialog() {


        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getActivity());
        View view = layoutInflaterAndroid.inflate(R.layout.hadis_go_dialog, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getActivity());
        alertDialogBuilderUserInput.setView(view);

        final EditText inputHadisNo = view.findViewById(R.id.hadisno);
//            int maxLength = adapViewFlipper.getCount();
//            inputHadisNo.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
//            TextView dialogTitle = view.findViewById(R.id.dialog_title);
//            dialogTitle.setText(!shouldUpdate ? getString(R.string.lbl_new_note_title) : getString(R.string.lbl_edit_note_title));
        inputHadisNo.requestFocus();

        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(getResources().getText(R.string.btn_hadis_go), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                    }
                })
                .setNegativeButton(getResources().getText(R.string.btn_cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show toast message when no text is entered
                String msg=getResources().getText(R.string.hint_enter_hadis_go).toString();
                if (TextUtils.isEmpty(inputHadisNo.getText().toString())) {

                    Message.show(msg);
                    return;
                } else {
                    alertDialog.dismiss();
                }
                int hadisno=Integer.parseInt(inputHadisNo.getText().toString());
                //Message.show(getActivity(),String.valueOf(hadisno));
                if(App.DbAdapter.CheckHadisBy(hadisno)){

                    int i=App.DbAdapter.getHadisRowIndex(hadisno);
                    if(i<0){
                        msg=getResources().getText(R.string.hint_enter_hadis_go2).toString();
                        Message.show(msg);
                        return;
                    }
                    adapViewFlipper.setSelection(i);
                }
                else{
                    Message.show(msg);

                }


                //inputHadisNo.setMax adapViewFlipper.getAdapter().getCount()
                //long hadisNo=adapViewFlipper.getAdapter().getItemId(adapViewFlipper.getDisplayedChild());

            }
        });

    }
    private void FlipMethodCalistirbyAdapter(View root,ArrayList<Hadis> list1) {
        before=root.findViewById(R.id.btn_before);
        next=root.findViewById(R.id.btn_Next);

        adapViewFlipper = root.findViewById(R.id.adapterViewFlipper);
        hadisViewFlipperAdapter=new HadisViewFlipperAdapter(getActivity().getApplicationContext(), list1);
        adapViewFlipper.setAdapter(hadisViewFlipperAdapter);
        adapViewFlipper.setDisplayedChild(indexVP);
        adapViewFlipper.setFlipInterval(1000);
        adapViewFlipper.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                CheckIsFavorite(adapViewFlipper.getAdapter().getItemId(adapViewFlipper.getDisplayedChild()));
                SetVPIndex();
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Message.show(getContext(),"DisplayedChild(indexVP) "+adapViewFlipper.getDisplayedChild());
//                simpleViewFlipper.setInAnimation(AnimationUtils.loadAnimation(con1, R.anim.in));
                try {
                    adapViewFlipper.stopFlipping();
                    adapViewFlipper.setInAnimation(App.app_context, android.R.animator.fade_in);
                    adapViewFlipper.setOutAnimation(App.app_context, android.R.animator.fade_out);
                    adapViewFlipper.showNext();
//                    CheckIsFavorite(adapViewFlipper.getDisplayedChild());

                }catch (Exception ex){
                    Message.show(ex.getMessage());
                }




            }
        });
        before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    adapViewFlipper.stopFlipping();
                    adapViewFlipper.setInAnimation(App.app_context, android.R.animator.fade_in);
//                    adapViewFlipper.setOutAnimation(con1, android.R.animator.fade_out);
                    adapViewFlipper.showPrevious();
//                    CheckIsFavorite(adapViewFlipper.getDisplayedChild());
                }catch (Exception ex){
                    Message.show(ex.getMessage());
                }

            }
        });


    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();

        SetVPIndex();
    }

    private void SetVPIndex() {
        SharedPreferences.Editor editor=refindexVP.edit();
        editor.putInt("refindexVPkey",adapViewFlipper.getDisplayedChild());
        editor.commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

//        indexVP=adapViewFlipper.getDisplayedChild();
//        outState.putInt("indexVP",indexVP);

    }




}
