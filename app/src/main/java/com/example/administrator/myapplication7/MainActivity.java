package com.example.administrator.myapplication7;


import android.app.Dialog;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;
    private RemindersDbAdapter mDbAdapter;
    private RemindersSimpleCursorAdapter mCursorAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()

        {
            @Override
            public void onItemClick (AdapterView < ? > parent, View view,
                                     final int masterListPosition, long id){
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                ListView modeListView = new ListView(MainActivity.this);
                String[] modes = new String[]{"Edit Reminder", "Delete Reminder"};
                ArrayAdapter<String> modeAdapter = new ArrayAdapter<>(MainActivity.this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, modes);
                modeListView.setAdapter(modeAdapter);
                builder.setView(modeListView);
                final Dialog dialog = builder.create();
                dialog.show();
                modeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//edit reminder
                        if (position == 0) {
                            Toast.makeText(MainActivity.this, "edit "
                                    + masterListPosition, Toast.LENGTH_SHORT).show();
                            Log.d("loglog1","create new Reminder11");
//delete reminder
                        } else {
                            Toast.makeText(MainActivity.this, "delete "
                                    + masterListPosition, Toast.LENGTH_SHORT).show();
                            Log.d("loglog2",masterListPosition+"");
                        }
                        dialog.dismiss();
                    }
                });
            }
        });


        }

    private void insertSomeReminders(String name, boolean important) {
        mDbAdapter.createReminder(name, important);
    }


//Removed remaining method code for brevity...


//    private ListView mListView;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        mListView = (ListView) findViewById(R.id.reminders_list_view);
////The arrayAdatper is the controller in our
////model-view-controller relationship. (controller)
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
////context
//                this,
////layout (view)
//                R.layout.reminders_row,
////row (view)
//                R.id.row_text,
////data (model) with bogus data to test our listview
//                new String[]{"first record", "second record", "third record"});
//        mListView.setAdapter(arrayAdapter);
//    }
////Remainder of the class listing omitted for brevity
@Override
public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_reminders, menu);
    return true;
}
@Override
public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
        case R.id.action_new:
//create new Reminder
            Log.d(getLocalClassName(),"create new Reminder22");
            return true;
        case R.id.action_exit:
            finish();
            return true;
        default:
            return false;
    }
}
}
