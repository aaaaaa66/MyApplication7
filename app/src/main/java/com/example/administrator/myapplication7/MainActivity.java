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
import android.support.annotation.RequiresApi;
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

import java.text.SimpleDateFormat;
import java.util.*;

import java.util.AbstractList;
import java.util.Calendar;
import java.util.HashMap;

import static android.app.AlarmManager.*;
import static java.util.Calendar.*;

public class MainActivity extends AppCompatActivity {

    private Date alarm;
    private ListView mListView;
    private int mHour = 12;
    private int mMinute = 30;
    private int mSecond = 0;
    String str1=new String();
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



        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int masterListPosition, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                ListView modeListView = new ListView(MainActivity.this);
                String[] modes = new String[]{"Edit Reminder", "Delete Reminder","设定提醒"};
                ArrayAdapter<String> modeAdapter = new ArrayAdapter<>(MainActivity.this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, modes);
                modeListView.setAdapter(modeAdapter);
                builder.setView(modeListView);
                final Dialog dialog = builder.create();
                dialog.show();
                modeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                         Reminder reminder;
                        reminder = mDbAdapter.fetchReminderById(getIdFromPosition(masterListPosition));
                        final Date today = new Date();
//                      final Calendar c= Calendar.getInstance();
//                        c.setTimeInMillis(System.currentTimeMillis());
//                        int hour = c.get(Calendar.HOUR_OF_DAY);dddddddddddddddddd
//                        int minute = c.get(Calendar.MINUTE);

                        //edit reminder
                        if (position == 0) {
                            fireCustomDialog(reminder);
                           // System.out.print(""+reminder.getContent());
                            //delete reminder
                        } else if (position == 1) {
                            mDbAdapter.deleteReminderById(getIdFromPosition(masterListPosition));

                            mCursorAdapter.changeCursor(mDbAdapter.fetchAllReminders());
                        } else {


                            new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                    str1=String.valueOf(getIdFromPosition(masterListPosition));
                                    System.out.println("dddddddddddddddddd"+str1);
                                    mHour = hourOfDay;
                                    mMinute = minute;
                                    mSecond = 0;
                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");//初始化Formatter的转换格式。


                                    Date today = new Date();
                                    alarm = new Date(today.getYear(), today.getMonth(), today.getDate(), mHour, mMinute);
                                    String hms = formatter.format(alarm);

                                    // mTextView.setText(mHour + ":" + mMinute);

                                  // scheduleReminder(alarm.getTime(),reminder.getContent());
                                   scheduleReminder(alarm.getTime(),str1);
                                }
                            }, today.getHours(), today.getMinutes(), true).show();                        }
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
        System.out.println("dddddddddddddddddd" + content);
        //创建Intent对象，action为ELITOR_CLOCK，附加信息为字符串“你该打酱油了”
        Intent intent = new Intent();
        intent.putExtra("aa","aa");
        intent.putExtra("aa",content);
        intent.setClass(this, ReminderAlarmReceiver.class);



//定义一个PendingIntent对象，PendingIntent.getBroadcast包含了sendBroadcast的动作。
//也就是发送了action 为"ELITOR_CLOCK"的intent
        PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this,0,intent,0);

//AlarmManager对象,注意这里并不是new一个对象，Alarmmanager为系统级服务
        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);

//设置闹钟从当前时间开始，每隔5s执行一次PendingIntent对象pi，注意第一个参数与第二个参数的关系
// 5秒后通过PendingIntent pi对象发送广播
        // am.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),5*1000,pi);
       // am.setRepeating(AlarmManager.RTC_WAKEUP,time,parseLong(null),pi);
        am.set(AlarmManager.RTC_WAKEUP,time,pi);
    }
}
