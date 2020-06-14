package com.ykpylcn.kutubisittehadisler_v1.ui;


import android.app.AlertDialog;
import android.app.Notification;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.ykpylcn.kutubisittehadisler_v1.App;
import com.ykpylcn.kutubisittehadisler_v1.R;
import com.ykpylcn.kutubisittehadisler_v1.db.Hadis;
import com.ykpylcn.kutubisittehadisler_v1.db.Note;
import com.ykpylcn.kutubisittehadisler_v1.utils.NotificationUtils;

import java.util.Calendar;


public class Dialogs {

    public Dialogs() {
    }
    public void showNoteDialog(final boolean shouldUpdate, final Note note, final int position, final FragmentActivity activity, final int hadisID) {

        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(activity);
        View view = layoutInflaterAndroid.inflate(R.layout.note_dialog, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(activity);
        alertDialogBuilderUserInput.setView(view);

        final EditText inputNote = view.findViewById(R.id.note);
        TextView dialogTitle = view.findViewById(R.id.dialog_title);
        dialogTitle.setText(!shouldUpdate ? activity.getResources().getText(R.string.lbl_new_note_title) : activity.getResources().getText(R.string.lbl_edit_note_title));

        if (shouldUpdate && note != null) {
            inputNote.setText(note.getNote());
        }
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(shouldUpdate ? activity.getResources().getText(R.string.btn_update) : activity.getResources().getText(R.string.btn_save), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                    }
                })
                .setNegativeButton(activity.getResources().getText(R.string.btn_cancel),
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
                if (TextUtils.isEmpty(inputNote.getText().toString())) {
                    String msg=activity.getResources().getText(R.string.hint_enter_note).toString();
                    Message.show(msg);
                    return;
                } else {
                    alertDialog.dismiss();
                }

                // check if user updating note
                if (shouldUpdate && note != null) {
                    // update note by it's id
                    App.DbAdapter.updateNoteByID(inputNote.getText().toString(), position);
                } else {
                    // create new note
                    long id = App.DbAdapter.insertNote(inputNote.getText().toString(),hadisID);

                }
            }
        });
    }
    public void showNotificationDialog(final boolean shouldUpdate,final FragmentActivity activity, final long hadisID) {

        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(activity);
        View view = layoutInflaterAndroid.inflate(R.layout.notify_dialog, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(activity);
        alertDialogBuilderUserInput.setView(view);

//        final EditText inputNote = view.findViewById(R.id.note);
//        TextView dialogTitle = view.findViewById(R.id.dialog_title);
//        dialogTitle.setText(!shouldUpdate ? activity.getResources().getText(R.string.lbl_new_notify_title) : activity.getResources().getText(R.string.lbl_edit_notify_title));
//
//        TimePicker timePicker=view.findViewById(R.id.editTimePicker);

//        Calendar myCal = Calendar.getInstance();
//
//
//        timePicker.setCurrentHour(myCal.get(Calendar.HOUR_OF_DAY)+12);
//        timePicker.setCurrentMinute(Calendar.MINUTE);

        final EditText time=view.findViewById(R.id.timeText);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        time.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Saat Belirle!");
                mTimePicker.show();

            }
        });
//        notif e gore degistir
//        if (shouldUpdate && note != null) {
//            inputNote.setText(note.getNote());
//        }
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(shouldUpdate ? activity.getResources().getText(R.string.btn_update) : activity.getResources().getText(R.string.btn_save), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                    }
                })
                .setNegativeButton(activity.getResources().getText(R.string.btn_cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
//                                Intent i = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
//                                i.putExtra(Settings.EXTRA_APP_PACKAGE, App.app_context.getPackageName());
//                                i.putExtra(Settings.EXTRA_CHANNEL_ID, NotificationUtils.ANDROID_CHANNEL_ID);
//                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                App.app_context.startActivity(i);
                                dialogBox.cancel();
                            }
                        });


        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                // Show toast message when no text is entered
//                if (TextUtils.isEmpty(inputNote.getText().toString())) {
//                    String msg=activity.getResources().getText(R.string.hint_enter_note).toString();
//                    Message.show(msg);
//                    return;
//                } else {
//                    alertDialog.dismiss();
//                }
                alertDialog.dismiss();
                Hadis hadis=App.DbAdapter.getHadis(hadisID);

                if(hadis!=null) {

                    NotificationUtils mNotificationUtils=new NotificationUtils(App.app_context);
                    Notification.Builder nb = mNotificationUtils.
                            getAndroidChannelNotification(hadis.AnaKonu, hadis.getHadis());

                    mNotificationUtils.getManager().notify((int) hadisID, nb.build());
                }
//                // check if user updating notification
//                if (shouldUpdate && note != null) {
//                    // update note by it's id
//                    App.DbAdapter.updateNoteByID(inputNote.getText().toString(), position);
//                } else {
//                    // create new note
//                    long id = App.DbAdapter.insertNote(inputNote.getText().toString(),hadisID);
//
//                }
            }
        });
    }
}
