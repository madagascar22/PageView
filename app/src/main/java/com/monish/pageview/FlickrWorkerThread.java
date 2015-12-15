package com.monish.pageview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

/**
 * Created by monish.kumar on 13/12/15.
 */
public class FlickrWorkerThread  extends HandlerThread {
    HashMap<ImageView, String> requestQueue = new HashMap<ImageView, String>();
    LruCache<String, Bitmap> cache;
    Handler workerThreadHandler;
    Handler uiThreadHandler;
    String TAG = "FlickrWorkerThread";

    public void putRequest(ImageView img, String uri){
        requestQueue.put(img, uri);

        Message msg = Message.obtain();
        msg.obj = img;
        workerThreadHandler.sendMessage(msg);
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();

        workerThreadHandler = new Handler(getLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                final ImageView iv = (ImageView) msg.obj;
                String uri = requestQueue.get(iv);
                final Bitmap b = getImageBItmap(uri);
                uiThreadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iv.setImageBitmap(b);
                    }
                });
//                requestQueue.remove(iv);
            }
        };
    }

    public FlickrWorkerThread(Handler handler){
        super("FlickrWorkerThread");
        this.uiThreadHandler = handler;
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        cache = new LruCache<String, Bitmap>(maxMemory/8){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount()/1024;
            }
        };
    }

    Bitmap getImageBItmap(String uri) {
        try{

            Bitmap image = cache.get(uri);
            if(image == null) {

                URL url = new URL(uri);
                URLConnection connection = url.openConnection();
                InputStream stream = connection.getInputStream();
                image = BitmapFactory.decodeStream(stream);
                cache.put(uri, image);
            }
            return  image;
        } catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }

}
