package com.ykpylcn.kutubisittehadisler_v1.ui.notlar;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ykpylcn.kutubisittehadisler_v1.App;
import com.ykpylcn.kutubisittehadisler_v1.MainActivity;
import com.ykpylcn.kutubisittehadisler_v1.R;
import com.ykpylcn.kutubisittehadisler_v1.db.Note;
import com.ykpylcn.kutubisittehadisler_v1.db.NotesAdapter;
import com.ykpylcn.kutubisittehadisler_v1.ui.Dialogs;
import com.ykpylcn.kutubisittehadisler_v1.ui.Message;
import com.ykpylcn.kutubisittehadisler_v1.utils.MyDividerItemDecoration;
import com.ykpylcn.kutubisittehadisler_v1.utils.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class NotlarFragment extends Fragment {

    private NotlarViewModel notlarViewModel;

    private NotesAdapter mAdapter;
    private List<Note> notesList = new ArrayList<>();
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;
    private TextView noNotesView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notlarViewModel =
                ViewModelProviders.of(this).get(NotlarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notlar, container, false);
//        final TextView textView = root.findViewById(R.id.text_slideshow);
        notlarViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
            }
        });
//        coordinatorLayout = root.findViewById(R.id.coordinator_layout);
        recyclerView = root.findViewById(R.id.recycler_view);
        noNotesView = root.findViewById(R.id.empty_notes_view);


        notesList.addAll(App.DbAdapter.getAllNotes());

//        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Dialogs dial = new Dialogs();
//                dial.showNoteDialog(false,null,-1,getActivity(),0);
//                //showNoteDialog(false, null, -1);
//            }
//        });
        mAdapter = new NotesAdapter(App.app_context, notesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(App.app_context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(App.app_context, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);

        toggleEmptyNotes();

        /**
         * On long press on RecyclerView item, open alert dialog
         * with options to choose
         * Edit and Delete
         * */
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
                editor.putInt("refindexVPkey",App.DbAdapter.getHadisRowIndex(notesList.get(position).getHadisNo()));
                editor.commit();

                Context context=getContext();

                context.startActivity(new Intent(context, MainActivity.class));
//                getActivity().finish();
            }
        }));
        return root;
    }

    /**
     * Opens dialog with Edit - Delete options
     * Edit - 0
     * Delete - 0
     */
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

                    Dialogs dial=new Dialogs();
                    dial.showNoteDialog(true,notesList.get(position),notesList.get(position).getId(),getActivity(), 0,notesList,mAdapter,position);
                    //showNoteDialog(true, notesList.get(position), position);
                } else {
                    deleteNote(position);
                }
            }
        });
        builder.show();
    }
    /**
     * Deleting note from SQLite and removing the
     * item from the list by its position
     */
    private void deleteNote(int position) {
        // deleting the note from db
        App.DbAdapter.deleteNote(notesList.get(position));

        // removing the note from the list
        notesList.remove(position);
        mAdapter.notifyItemRemoved(position);

        toggleEmptyNotes();
    }


    /**
     * Inserting new note in db
     * and refreshing the list
     */
    private void createNote(String note) {
        // inserting note in db and getting
        // newly inserted note id
        long id = App.DbAdapter.insertNote(note,0);

        // get the newly inserted note from db
        Note n = App.DbAdapter.getNote(id);

        if (n != null) {
            // adding new note to array list at 0 position
            notesList.add(0, n);

            // refreshing the list
            mAdapter.notifyDataSetChanged();

            toggleEmptyNotes();
        }
    }
//    /**
//     * Updating note in db and updating
//     * item in the list by its position
//     */
//    private void updateNote(String note, int position) {
//        Note n = notesList.get(position);
//        // updating note text
//        n.setNote(note);
//
//        // updating note in db
//        App.DbAdapter.updateNote(n);
//
//        // refreshing the list
//        notesList.set(position, n);
//        mAdapter.notifyItemChanged(position);
//
//        toggleEmptyNotes();
//    }
    /**
     * Toggling list and empty notes view
     */
    private void toggleEmptyNotes() {
        // you can check notesList.size() > 0

        if (App.DbAdapter.getNotesCount() > 0) {
            noNotesView.setVisibility(View.GONE);
        } else {
            noNotesView.setVisibility(View.VISIBLE);
        }
    }
}
