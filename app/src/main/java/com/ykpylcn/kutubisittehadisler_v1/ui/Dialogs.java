package com.ykpylcn.kutubisittehadisler_v1.ui;


import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.ykpylcn.kutubisittehadisler_v1.App;
import com.ykpylcn.kutubisittehadisler_v1.R;
import com.ykpylcn.kutubisittehadisler_v1.db.Hadis;
import com.ykpylcn.kutubisittehadisler_v1.db.Note;
import com.ykpylcn.kutubisittehadisler_v1.utils.AlarmNotificationReceiver;
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
    public void showNotificationDialog(final boolean shouldUpdate, final Context activity, final long hadisID, final Intent intent) {

        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(activity);
        View view = layoutInflaterAndroid.inflate(R.layout.notify_dialog, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(activity);
        alertDialogBuilderUserInput.setView(view);

        final AlarmManager manager = (AlarmManager)activity.getSystemService(Context.ALARM_SERVICE);
//        final EditText inputNote = view.findViewById(R.id.note);
//        TextView dialogTitle = view.findViewById(R.id.dialog_title);
//        dialogTitle.setText(!shouldUpdate ? activity.getResources().getText(R.string.lbl_new_notify_title) : activity.getResources().getText(R.string.lbl_edit_notify_title));
//
        final TimePicker timePicker=view.findViewById(R.id.timePicker);
        final CheckBox isDaily=view.findViewById(R.id.cbHergun);
        timePicker.setIs24HourView(true);
//        Calendar myCal = Calendar.getInstance();
//
//
//        timePicker.setCurrentHour(myCal.get(Calendar.HOUR_OF_DAY)+12);
//        timePicker.setCurrentMinute(Calendar.MINUTE);

//        final EditText time=view.findViewById(R.id.timePicker);
//        time.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Calendar mcurrentTime = Calendar.getInstance();
//                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
//                int minute = mcurrentTime.get(Calendar.MINUTE);
//                TimePickerDialog mTimePicker;
//                mTimePicker = new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//                        time.setText(selectedHour + ":" + selectedMinute);
//                    }
//                }, hour, minute, true);//Yes 24 hour time
//                mTimePicker.setTitle("Saat Belirle!");
//                mTimePicker.show();
//
//            }
//        });
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

        if(shouldUpdate){
            alertDialogBuilderUserInput.setNeutralButton("Kaldir" , new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogBox, int id) {


                    PendingIntent pendingIntent = PendingIntent.getBroadcast(activity, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);//the same as up
                    manager.cancel(pendingIntent);//important
                    pendingIntent.cancel();//important
                }
            });
        }


        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();



                if(Build.VERSION.SDK_INT>=23)
                startAlarm(manager,hadisID,isDaily.isChecked(),timePicker.getHour(),timePicker.getMinute(),activity,intent);
                else
                startAlarm(manager,hadisID,isDaily.isChecked(),timePicker.getCurrentHour(),timePicker.getCurrentMinute(),activity,intent);

//DATABASE ISLERI BURDAN
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
    private void startAlarm(AlarmManager manager,long hadisID, boolean isRepeat,int hour, int minute,Context activity, Intent myIntent) {

//        Intent myIntent;
        PendingIntent pendingIntent;

        // SET TIME HERE
        Calendar calendar= Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,0);

//        myIntent = new Intent(activity, AlarmNotificationReceiver.class);
//        myIntent.putExtra("hadisid",hadisID);
//        myIntent.setAction(String.valueOf(hadisID));


        pendingIntent = PendingIntent.getBroadcast(activity,0,myIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        if(!isRepeat)
            manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pendingIntent);
        else
            manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY,pendingIntent);
    }


}
