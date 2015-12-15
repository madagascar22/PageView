package com.monish.pageview;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String TAG = "MainActivity";
    ListView listView;
    ArrayList<FlickrItem> items = new ArrayList<FlickrItem>();
    FlickrJSONTask flickrJSONTask;
    FlickrAdapter adapter;

    class FlickrJSONTask extends AsyncTask<String, Void, ArrayList<FlickrItem>> {
        @Override
        protected ArrayList<FlickrItem> doInBackground(String... params) {

            FlickrGetter getter = new FlickrGetter();
            return getter.fetchItems(params[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<FlickrItem> flickrItems) {
            items.clear();
            items.addAll(flickrItems);
            adapter.notifyDataSetChanged();
            Log.i(TAG, flickrItems.toString());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        adapter = new FlickrAdapter(this, items);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        flickrJSONTask = new FlickrJSONTask();
        flickrJSONTask.execute("dogs");
    }

    public void search(View view){


    }
}
