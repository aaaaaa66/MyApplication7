package com.example.administrator.myapplication7;


import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.*;

import java.util.AbstractList;
import java.util.Calendar;
import java.util.HashMap;

import static android.app.AlarmManager.*;
import static java.util.Calendar.*;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;
String str=new String();
    private RemindersDbAdapter mDbAdapter;
    private RemindersSimpleCursorAdapter mCursorAdapter;
    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);
        mListView = (ListView) findViewById(R.id.reminders_list_view);
        mListView.setDivider(null);
        mDbAdapter = new RemindersDbAdapter(this);
        mDbAdapter.open();
        Cursor cursor = mDbAdapter.fetchAllReminders();
//from columns defined in the db
        String[] from = new String[]{
                RemindersDbAdapter.COL_CONTENT
        };
//to the ids of views in the layout
        int[] to = new int[]{
                R.id.row_text
        };
        mCursorAdapter = new RemindersSimpleCursorAdapter(
//context
                MainActivity.this,
//the layout of the row
                R.layout.reminders_row,
//cursor
                cursor,
//from columns defined in the db
                from,
//to the ids of views in the layout
                to,
//flag - not used
                0);
// the cursorAdapter (controller) is now updating the listView (view)
//with data from the db (model)
        mListView.setAdapter(mCursorAdapter);

    //Abbreviated for brevity

        if (savedInstanceState == null) {
//Clear all data
            mDbAdapter.deleteAllReminders();
//Add some data
            insertSomeReminders("Buy Learn Android Studio", true);
            insertSomeReminders("Send Dad birthday gift", false);
            insertSomeReminders("Dinner at the Gage on Friday", false);
            insertSomeReminders("String squash racket", false);
            insertSomeReminders("Shovel and salt walkways", false);
            insertSomeReminders("Prepare Advanced Android syllabus", true);
            insertSomeReminders("Buy new office chair", false);
            insertSomeReminders("Call Auto-body shop for quote", false);
            insertSomeReminders("Renew membership to club", false);
            insertSomeReminders("Buy new Galaxy Android phone", true);
            insertSomeReminders("Sell old Android phone - auction", false);
            insertSomeReminders("Buy new paddles for kayaks", false);
            insertSomeReminders("Call accountant about tax returns", false);
            insertSomeReminders("Buy 300,000 shares of Google", false);
            insertSomeReminders("Call the Dalai Lama back", true);
        }
//Removed remaining method code for brevity..
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(MainActivity.this, "clicked " + position,
//                        Toast.LENGTH_SHORT).show();
//        Log.d("loglog","create new Reminder22");
//            }
//        });


//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
//
//        {
//            @Override
//            public void onItemClick (AdapterView < ? > parent, View view,
//                                     final int masterListPosition, long id){
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                    ListView modeListView = new ListView(MainActivity.this);
//                    String[] modes = new String[]{"Edit Reminder", "Delete Reminder", "Schedule Reminder"};
//                    ArrayAdapter<String> modeAdapter = new ArrayAdapter<>(MainActivity.this,
//                            android.R.layout.simple_list_item_1, android.R.id.text1, modes);
//                    modeListView.setAdapter(modeAdapter);
//                    builder.setView(modeListView);
//                    final Dialog dialog = builder.create();
//                    dialog.show();
//                    modeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
////                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//////edit reminder
////                        if (position == 0) {
////                            Toast.makeText(MainActivity.this, "edit "
////                                    + masterListPosition, Toast.LENGTH_SHORT).show();
////                            Log.d("loglog1","create new Reminder11");
//////delete reminder
////                        } else {
////                            Toast.makeText(MainActivity.this, "delete "
////                                    + masterListPosition, Toast.LENGTH_SHORT).show();
////                            Log.d("loglog2",masterListPosition+"");
////                        }
////
////                        dialog.dismiss();
////                    }
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////edit reminder
//                            int nId = getIdFromPosition(masterListPosition);
//                            final Reminder reminder = mDbAdapter.fetchReminderById(nId);
//                            if (position == 0) {
//                                fireCustomDialog(reminder);
////delete reminder
//                            } else if (position == 0) {
//                                mDbAdapter.deleteReminderById(getIdFromPosition(masterListPosition));
//                                mCursorAdapter.changeCursor(mDbAdapter.fetchAllReminders());
//                            } else {
////                      final Date today = new Date();
////                            final Date today = new Date(1900-3001, 0-11, 1-31);
//                                TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
//                                    @Override
//                                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
//                                        final Calendar alarmTime = Calendar.getInstance();
//                                        alarmTime.set(Calendar.HOUR, hour);
//                                        alarmTime.set(Calendar.MINUTE, minute);
//                                        scheduleReminder(alarmTime.getTimeInMillis(), reminder.getContent());
////                                    Date alarm = new Date(today.getYear(), today.getMonth(), today.getDate());
////                                    scheduleReminder(alarm.getTime(), reminder.getContent());
//                                    }
//                                };
//                                final Calendar today = Calendar.getInstance();
//                                new TimePickerDialog(MainActivity.this, null, today.get(Calendar.HOUR), today.get(Calendar.MINUTE), false).show();
////                            new TimePickerDialog(MainActivity.this, null,today.getHours(), today.getMinutes(), false).show();
//                            }
////                        else {
////                            Toast.makeText(MainActivity.this, "delete "
////                                    + masterListPosition, Toast.LENGTH_SHORT).show();
////                        }
////                        dialog.dismiss();
//                        }
//                    });
//                }
//            }
//        });


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int masterListPosition, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                ListView modeListView = new ListView(MainActivity.this);
                String[] modes = new String[]{"Edit Reminder", "Delete Reminder", "Schedule Reminder"};
                ArrayAdapter<String> modeAdapter = new ArrayAdapter<>(MainActivity.this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, modes);
                modeListView.setAdapter(modeAdapter);
                builder.setView(modeListView);
                final Dialog dialog = builder.create();
                dialog.show();
                modeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        int nId = getIdFromPosition(masterListPosition);
                        final Reminder reminder = mDbAdapter.fetchReminderById(nId);
                        final Date today = new Date();
                        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                                Date alarm = new Date(today.getYear(), today.getMonth(), today.getDate(), hour,
                                        minute);
                                scheduleReminder(alarm.getTime(), reminder.getContent());
                            }
                        };

                        //edit reminder
                        if (position == 0) {
                            fireCustomDialog(reminder);
                            //delete reminder
                        } else if (position == 1) {
                            mDbAdapter.deleteReminderById(getIdFromPosition(masterListPosition));
                            mCursorAdapter.changeCursor(mDbAdapter.fetchAllReminders());
                        } else {
                            new TimePickerDialog(MainActivity.this,listener, today.getHours(), today.getMinutes(), false).show();
                        }
                        dialog.dismiss();
                    }
                });
            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
            mListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
                @Override
                public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean
                        checked) { }
                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    MenuInflater inflater = mode.getMenuInflater();
                    inflater.inflate(R.menu.cam_menu, menu);
                    return true;
                }
                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }
                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_item_delete_reminder:
                            for (int nC = mCursorAdapter.getCount() - 1; nC >= 0; nC--) {
                                if (mListView.isItemChecked(nC)) {
                                    mDbAdapter.deleteReminderById(getIdFromPosition(nC));
                                }
                            }
                            mode.finish();
                            mCursorAdapter.changeCursor(mDbAdapter.fetchAllReminders());
                            return true;
                    }
                    return false;
                }
                @Override
                public void onDestroyActionMode(ActionMode mode) { }
            });

        }

        }

    private int getIdFromPosition(int nC) {
        return (int)mCursorAdapter.getItemId(nC);
    }

    private void insertSomeReminders(String name, boolean important) {
        mDbAdapter.createReminder(name, important);
    }



private void fireCustomDialog(final Reminder reminder){
// custom dialog
    final Dialog dialog = new Dialog(this);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setContentView(R.layout.dialog_custom);
    TextView titleView =  dialog.findViewById(R.id.custom_title);
    final EditText editCustom = (EditText) dialog.findViewById(R.id.custom_edit_reminder);
    Button commitButton = (Button) dialog.findViewById(R.id.custom_button_commit);
    final CheckBox checkBox = (CheckBox) dialog.findViewById(R.id.custom_check_box);
    checkBox.setChecked(true);
    LinearLayout rootLayout = (LinearLayout) dialog.findViewById(R.id.custom_root_layout);
    final boolean isEditOperation = (reminder != null);
//this is for an edit
    if (isEditOperation){
        titleView.setText("Edit Reminder");
        checkBox.setChecked(reminder.getImportant() == 1);
        editCustom.setText(reminder.getContent());
        rootLayout.setBackgroundColor(getResources().getColor(R.color.blue));
    }
    commitButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String reminderText = editCustom.getText().toString();
            if (isEditOperation) {
                Reminder reminderEdited = new Reminder(reminder.getId(),
                        reminderText, checkBox.isChecked() ? 1 : 0);
                mDbAdapter.updateReminder(reminderEdited);
//this is for new reminder
            } else {
                mDbAdapter.createReminder(reminderText, checkBox.isChecked());
            }
            mCursorAdapter.changeCursor(mDbAdapter.fetchAllReminders());
            dialog.dismiss();
        }
    });
    Button buttonCancel = (Button) dialog.findViewById(R.id.custom_button_cancel);
    buttonCancel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog.dismiss();
        }
    });
    dialog.show();
}
@Override
public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_reminders, menu);
    return true;
}
@Override
//public boolean onOptionsItemSelected(MenuItem item) {
//    switch (item.getItemId()) {
//        case R.id.action_new:
////create new Reminder
//            Log.d(getLocalClassName(),"create new Reminder22");
//            return true;
//        case R.id.action_exit:
//            finish();
//            return true;
//        default:
//            return false;
//    }
//}
public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
        case R.id.action_new:
//create new Reminder
            fireCustomDialog(null);
            return true;
        case R.id.action_exit:
            finish();
            return true;
        default:
            return false;
    }
}
    private void scheduleReminder(long time, String content) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(this, ReminderAlarmReceiver.class);
        alarmIntent.putExtra(ReminderAlarmReceiver.REMINDER_TEXT, content);
        PendingIntent broadcast = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        alarmManager.set(RTC_WAKEUP, time, broadcast);
    }
}
