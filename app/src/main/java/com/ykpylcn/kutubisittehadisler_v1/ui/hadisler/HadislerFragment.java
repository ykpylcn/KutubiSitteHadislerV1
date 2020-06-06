package com.ykpylcn.kutubisittehadisler_v1.ui.hadisler;


import android.animation.Animator;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterViewFlipper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.DrawableRes;
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

import java.util.ArrayList;
import java.util.List;

public class HadislerFragment extends Fragment {


    private HadislerViewModel hadislerViewModel;
    SearchView searchView;
    BottomNavigationView bnavigate;
    Button before, next;
    AdapterViewFlipper adapViewFlipper;
    HadisViewFlipperAdapter hadisViewFlipperAdapter;
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

        setHasOptionsMenu(true);
//        dbAdapter=new DBAdapter(getActivity().getApplicationContext());
        bnavigate=root.findViewById(R.id.bottom_navigation);
        bnavigate.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                long hadisNo=adapViewFlipper.getAdapter().getItemId(adapViewFlipper.getDisplayedChild());
                switch (item.getItemId()) {
                    case R.id.navigate_hadis_no:
                        ShowNavHadisNoDialog();
                        return true;
                    case R.id.navigation_note:

                        Note not= App.DbAdapter.getNoteByHadisID(hadisNo);
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
                    case R.id.fav_add:
                        UpdateHadisIsFav(hadisNo);
                        return true;


                }
                return false;
            }


        });


        return root;
    }
    private void UpdateHadisIsFav(long hadisId){

        Hadis hadis=App.DbAdapter.getHadis(hadisId);
        String msg="";
        if(hadis!=null){

            if (hadis.getIsFav()){
                App.DbAdapter.updateHadisIsFav(hadisId,false);
                UpdateIconHadisIsFav(false);
                msg=getResources().getText(R.string.hint_hadis_isfav_remove).toString();
            }
            else{
                App.DbAdapter.updateHadisIsFav(hadisId,true);
                UpdateIconHadisIsFav(true);
                msg=getResources().getText(R.string.hint_hadis_isfav_add).toString();
            }

        }else
            msg=getResources().getText(R.string.hint_enter_hadis_go2).toString();
        Message.show(getActivity(),msg);
    }
    private void CheckIsFavorite(long hadisId){
        Hadis hadis=App.DbAdapter.getHadis(hadisId);
        UpdateIconHadisIsFav(hadis.getIsFav());
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
                if(App.DbAdapter.CheckHadisBy(hadisno)){

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
        hadisViewFlipperAdapter=new HadisViewFlipperAdapter(getActivity().getApplicationContext(), list1);
        adapViewFlipper.setAdapter(hadisViewFlipperAdapter);
        adapViewFlipper.setDisplayedChild(indexVP);
        adapViewFlipper.setFlipInterval(1000);
        adapViewFlipper.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                CheckIsFavorite(adapViewFlipper.getAdapter().getItemId(adapViewFlipper.getDisplayedChild()));
            }
        });
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



    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate( R.menu.options_menu, menu);

        final MenuItem myActionMenuItem = menu.findItem( R.id.search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Toast like print
                Message.show(getContext(), "Search: " + query);
                hadisViewFlipperAdapter.getFilter().filter(query);

//                if( ! searchView.isIconified()) {
//                    searchView.setIconified(true);
//                }
//                myActionMenuItem.collapseActionView();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                // UserFeedback.show( "SearchOnQueryTextChanged: " + s);
                return false;
            }
        });
        return;

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



//Eski Kod
//    private void FlipMethodCalistir(View root) {
//        before=root.findViewById(R.id.btn_before);
//        next=root.findViewById(R.id.btn_Next);
//
//
//
//        simpleViewFlipper = root.findViewById(R.id.simpleViewFlipper);
//
//        List<Hadis> list=dbAdapter.getAllHadisler();
//        for (Hadis hadis : list) {
//            TextView tv = new TextView(getActivity().getApplicationContext());
//            tv.setText(hadis.getHadis());
//            tv.setTextSize(24);
//
//            simpleViewFlipper.addView(tv);
//
//        }
//        simpleViewFlipper.setDisplayedChild(indexVP);
//        simpleViewFlipper.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent event) {
//                int action = event.getActionMasked();
//
//                switch (action) {
//                    case MotionEvent.ACTION_DOWN:
//                        startX = event.getX();
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        float endX = event.getX();
//                        float endY = event.getY();
//
//                        //swipe right
//                        if (startX < endX) {
//                            simpleViewFlipper.showNext();
//                        }
//
//                        //swipe left
//                        if (startX > endX) {
//                            simpleViewFlipper.showPrevious();
//                        }
//
//                        break;
//                }
//                return true;
//            }
//        });
//
//
//        next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                indexVP++;
//
//                Context con1 = getActivity().getApplicationContext();
////                simpleViewFlipper.setInAnimation(AnimationUtils.loadAnimation(con1, R.anim.in));
//
//                simpleViewFlipper.setInAnimation(AnimationUtils.loadAnimation(con1, android.R.anim.slide_in_left));
//                simpleViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(con1, android.R.anim.slide_out_right));
//                simpleViewFlipper.showNext();
//
//            }
//        });
//        before.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                indexVP--;
//
//                Context con1 = getActivity().getApplicationContext();
////                simpleViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(con1, R.anim.out));
//                simpleViewFlipper.setInAnimation(AnimationUtils.loadAnimation(con1, R.anim.slide_out_right));
//                simpleViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(con1, R.anim.slide_in_left));
//                simpleViewFlipper.showPrevious();
//
//
//
//            }
//        });
//
//
//        //simpleViewFlipper.startFlipping(); // start the flipping of views
//        simpleViewFlipper.setFlipInterval(2000); //set 1 seconds for interval time
//    }
}
