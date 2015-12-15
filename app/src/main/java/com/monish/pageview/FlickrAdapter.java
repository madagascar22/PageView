package com.monish.pageview;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by monish.kumar on 13/12/15.
 */
public class FlickrAdapter extends BaseAdapter {

    Context context;
    ArrayList<FlickrItem> items;
    FlickrWorkerThread workerThread;

    public FlickrAdapter(){

    }

    public FlickrAdapter(Context context, ArrayList<FlickrItem> items) {
        this.context = context;
        this.items = items;
        workerThread = new FlickrWorkerThread(new Handler());
        workerThread.start();
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View mainView = null;
        LayoutInflater inflater = LayoutInflater.from(context);
        if(convertView == null){
            mainView = new MyListItemView(context);
        } else {
            mainView = convertView;
        }

        ImageView iv = (ImageView) mainView.findViewById(R.id.imageView);
        FlickrItem item = items.get(position);
        String uri = item.url;

        TextView editText = (TextView) mainView.findViewById(R.id.editText);
        editText.setText(item.title);

//        iv.setImageBitmap(getImageBItmap(uri));

        workerThread.putRequest(iv, uri);
        return mainView;
    }
}
