package com.example.administrator.myapplication7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.reminders_list_view);
//The arrayAdatper is the controller in our
//model-view-controller relationship. (controller)
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
//context
                this,
//layout (view)
                R.layout.reminders_row,
//row (view)
                R.id.row_text,
//data (model) with bogus data to test our listview
                new String[]{"first record", "second record", "third record"});
        mListView.setAdapter(arrayAdapter);
    }
//Remainder of the class listing omitted for brevity
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
