package com.ykpylcn.kutubisittehadisler_v1.ui.hadisler;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterViewFlipper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ykpylcn.kutubisittehadisler_v1.R;
import com.ykpylcn.kutubisittehadisler_v1.db.DBAdapter;
import com.ykpylcn.kutubisittehadisler_v1.db.Hadis;
import com.ykpylcn.kutubisittehadisler_v1.db.HadisViewFlipperAdapter;
import com.ykpylcn.kutubisittehadisler_v1.db.Note;
import com.ykpylcn.kutubisittehadisler_v1.ui.Dialogs;
import com.ykpylcn.kutubisittehadisler_v1.ui.Message;

import java.util.ArrayList;
import java.util.List;

public class HadislerFragment extends Fragment {

    DBAdapter dbAdapter;
    private HadislerViewModel hadislerViewModel;
    BottomNavigationView bnavigate;
    Button before, next;
    ViewFlipper simpleViewFlipper;
    AdapterViewFlipper adapViewFlipper;
    SharedPreferences refindexVP;
    Context context;
    int indexVP=0;
    private float startX;
    int i=1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        hadislerViewModel =
                ViewModelProviders.of(this).get(HadislerViewModel.class);

        context=getActivity().getApplicationContext();
//        if(savedInstanceState!=null)
//            indexVP=savedInstanceState.getInt("indexVP");
        refindexVP=context.getSharedPreferences("refindexVP",0);
        indexVP=refindexVP.getInt("refindexVPkey",indexVP);

        final View root = inflater.inflate(R.layout.fragment_hadisler, container, false);
//      final TextView textView = root.findViewById(R.id.text_home);
        hadislerViewModel.getHadisler().observe(getViewLifecycleOwner(), new Observer<ArrayList<Hadis>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Hadis> s) {

                FlipMethodCalistirbyAdapter(root,s);

            }
        });

        dbAdapter=new DBAdapter(getActivity().getApplicationContext());
        bnavigate=root.findViewById(R.id.bottom_navigation);
        bnavigate.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigate_hadis_no:
                        ShowNavHadisNoDialog();
                        return true;
                    case R.id.navigation_note:
                        long hadisNo=adapViewFlipper.getAdapter().getItemId(adapViewFlipper.getDisplayedChild());
                        Note not= dbAdapter.getNoteByHadisID(hadisNo);
                        Dialogs dial = new Dialogs();
                        if(not!=null){

                            dial.showNoteDialog(true,not,not.getId(),getActivity(), (int) hadisNo);
                        }
                        else{
                            dial.showNoteDialog(false,null,0,getActivity(), (int) hadisNo);
                        }
                        return true;
                    case R.id.navigation_notifications:

                        return true;

                }
                return false;
            }


        });


        return root;
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
                .setNegativeButton(getResources().getText(R.string.btn_note_cancel),
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

                    Message.show(getActivity(),msg);
                    return;
                } else {
                    alertDialog.dismiss();
                }
                int hadisno=Integer.parseInt(inputHadisNo.getText().toString());
                //Message.show(getActivity(),String.valueOf(hadisno));
                if(dbAdapter.CheckHadisBy(hadisno)){
//                    int i= hadislerViewModel.getHadisler().getValue().indexOf(dbAdapter.getHadis(hadisno));
                    int i=GetIndexID(hadislerViewModel.getHadisler().getValue(),hadisno);
                    if(i<0){
                        msg=getResources().getText(R.string.hint_enter_hadis_go2).toString();
                        Message.show(getActivity(),msg);
                        return;
                    }
                    adapViewFlipper.setSelection(i);
                }
                else{
                    Message.show(getActivity(),msg);

                }


                //inputHadisNo.setMax adapViewFlipper.getAdapter().getCount()
                //long hadisNo=adapViewFlipper.getAdapter().getItemId(adapViewFlipper.getDisplayedChild());

            }
        });

    }

    public int GetIndexID(ArrayList<Hadis> hadisler,int hadisid){
        for (int i=0;i<hadisler.size();i++){

            if (hadisler.get(i).getHadisNo()==hadisid) {
                return i;
            }
        }
        return -1;
    }

    private void FlipMethodCalistirbyAdapter(View root,ArrayList<Hadis> list1) {
        before=root.findViewById(R.id.btn_before);
        next=root.findViewById(R.id.btn_Next);


        adapViewFlipper = root.findViewById(R.id.adapterViewFlipper);
        adapViewFlipper.setAdapter(new HadisViewFlipperAdapter(getActivity().getApplicationContext(), list1));
        adapViewFlipper.setDisplayedChild(indexVP);

        adapViewFlipper.setFlipInterval(1000);

//        adapViewFlipper.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//            @Override
//            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//                CheckIsFavorite(adapViewFlipper.getDisplayedChild());
//
//            }
//        });
//        adapViewFlipper.o
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Message.show(getContext(),"DisplayedChild(indexVP) "+adapViewFlipper.getDisplayedChild());
//                simpleViewFlipper.setInAnimation(AnimationUtils.loadAnimation(con1, R.anim.in));
                try {
                    adapViewFlipper.stopFlipping();
                    adapViewFlipper.setInAnimation(context, android.R.animator.fade_in);
                    adapViewFlipper.setOutAnimation(context, android.R.animator.fade_out);
                    adapViewFlipper.showNext();
//                    CheckIsFavorite(adapViewFlipper.getDisplayedChild());

                }catch (Exception ex){
                    Message.show(context,ex.getMessage());
                }




            }
        });
        before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    adapViewFlipper.stopFlipping();
                    adapViewFlipper.setInAnimation(context, android.R.animator.fade_in);
//                    adapViewFlipper.setOutAnimation(con1, android.R.animator.fade_out);
                    adapViewFlipper.showPrevious();
//                    CheckIsFavorite(adapViewFlipper.getDisplayedChild());
                }catch (Exception ex){
                    Message.show(context,ex.getMessage());
                }

            }
        });


    }
    private void FlipMethodCalistir(View root) {
        before=root.findViewById(R.id.btn_before);
        next=root.findViewById(R.id.btn_Next);
//<ViewFlipper
//        android:id="@+id/simpleViewFlipper"
//        android:layout_width="fill_parent"
//        android:layout_height="wrap_content"
//        android:layout_gravity="center|center_horizontal"
//        android:scrollbars="vertical"
//        android:paddingHorizontal="@dimen/activity_vertical_margin">
//
//
//
//        </ViewFlipper>


        simpleViewFlipper = root.findViewById(R.id.simpleViewFlipper);

        List<Hadis> list=dbAdapter.getAllHadisler();
        for (Hadis hadis : list) {
            TextView tv = new TextView(getActivity().getApplicationContext());
            tv.setText(hadis.getHadis());
            tv.setTextSize(24);

            simpleViewFlipper.addView(tv);

        }
        simpleViewFlipper.setDisplayedChild(indexVP);
        simpleViewFlipper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getActionMasked();

                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        float endX = event.getX();
                        float endY = event.getY();

                        //swipe right
                        if (startX < endX) {
                            simpleViewFlipper.showNext();
                        }

                        //swipe left
                        if (startX > endX) {
                            simpleViewFlipper.showPrevious();
                        }

                        break;
                }
                return true;
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                indexVP++;

                Context con1 = getActivity().getApplicationContext();
//                simpleViewFlipper.setInAnimation(AnimationUtils.loadAnimation(con1, R.anim.in));

                simpleViewFlipper.setInAnimation(AnimationUtils.loadAnimation(con1, android.R.anim.slide_in_left));
                simpleViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(con1, android.R.anim.slide_out_right));
                simpleViewFlipper.showNext();

            }
        });
        before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                indexVP--;

                Context con1 = getActivity().getApplicationContext();
//                simpleViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(con1, R.anim.out));
                simpleViewFlipper.setInAnimation(AnimationUtils.loadAnimation(con1, R.anim.slide_out_right));
                simpleViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(con1, R.anim.slide_in_left));
                simpleViewFlipper.showPrevious();



            }
        });


        //simpleViewFlipper.startFlipping(); // start the flipping of views
        simpleViewFlipper.setFlipInterval(2000); //set 1 seconds for interval time
    }

    private boolean CheckIsFavorite(int index){
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

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
