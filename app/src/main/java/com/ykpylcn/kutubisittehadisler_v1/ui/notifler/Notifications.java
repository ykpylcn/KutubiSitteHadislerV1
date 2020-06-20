package com.ykpylcn.kutubisittehadisler_v1.ui.notifler;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ykpylcn.kutubisittehadisler_v1.App;
import com.ykpylcn.kutubisittehadisler_v1.MainActivity;
import com.ykpylcn.kutubisittehadisler_v1.R;
import com.ykpylcn.kutubisittehadisler_v1.db.Note;
import com.ykpylcn.kutubisittehadisler_v1.db.NotesAdapter;
import com.ykpylcn.kutubisittehadisler_v1.db.Notif;
import com.ykpylcn.kutubisittehadisler_v1.db.NotifsAdapter;
import com.ykpylcn.kutubisittehadisler_v1.ui.notlar.NotlarViewModel;
import com.ykpylcn.kutubisittehadisler_v1.utils.AlarmNotificationReceiver;
import com.ykpylcn.kutubisittehadisler_v1.utils.MyDividerItemDecoration;
import com.ykpylcn.kutubisittehadisler_v1.utils.NotificationUtils;
import com.ykpylcn.kutubisittehadisler_v1.utils.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;


public class Notifications extends Fragment {


    private NotifsAdapter mAdapter;
    private List<Notif> notifList = new ArrayList<>();
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;
    private TextView noNotifView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        recyclerView = root.findViewById(R.id.recycler_view);
        noNotifView = root.findViewById(R.id.empty_notif_view);

        notifList.addAll(App.DbAdapter.getAllNotifs());

//        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Dialogs dial = new Dialogs();
//                dial.showNoteDialog(false,null,-1,getActivity(),0);
//                //showNoteDialog(false, null, -1);
//            }
//        });
        mAdapter = new NotifsAdapter(App.app_context, notifList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(App.app_context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(App.app_context, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);

        toggleEmptyNotes();
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(App.app_context,
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {

                showActionsDialog(position);
            }

            @Override
            public void onLongClick(View view, int position) {

                SharedPreferences refindexVP=App.app_context.getSharedPreferences("refindexVP",0);

                SharedPreferences.Editor editor=refindexVP.edit();
                editor.putInt("refindexVPkey",App.DbAdapter.getHadisRowIndex(notifList.get(position).HadisID));
                editor.commit();

                Context context=getContext();

                context.startActivity(new Intent(context, MainActivity.class));
//                getActivity().finish();
            }
        }));
        return root;
    }
    private void showActionsDialog(final int position) {
        String sEdit=  getResources().getString(R.string.btn_edit);
        String sDelete=  getResources().getString(R.string.btn_delete);
        CharSequence colors[] = new CharSequence[]{sEdit, sDelete};
        Context con=getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(con);
        builder.setTitle(getResources().getString(R.string.activity_title_home));
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {

//                    showNoteDialog(true, notesList.get(position), position);
                } else {
                    NotificationUtils.deleteNatification(getActivity(),notifList.get(position).HadisID);
                    deleteNotif(position);
                }
            }
        });
        builder.show();
    }
    private void deleteNotif(int position) {
        // deleting the note from db
        App.DbAdapter.deleteNotif(notifList.get(position).HadisID);

        // removing the note from the list
        notifList.remove(position);
        mAdapter.notifyItemRemoved(position);

        toggleEmptyNotes();
    }

    private void toggleEmptyNotes() {
        // you can check notesList.size() > 0

        if (App.DbAdapter.getNotifsCount() > 0) {
            noNotifView.setVisibility(View.GONE);
        } else {
            noNotifView.setVisibility(View.VISIBLE);
        }
    }
}