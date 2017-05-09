package com.mobileapp.username.myapplication;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by bchaudhr on 4/26/2017.
 */


//automatically creates its own thread
public class MyIntentService extends IntentService {

    private static final String TAG = "MyIntentService";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param //name Used to name the worker thread, important only for debugging.
     */
    public MyIntentService() {
        super("MyService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        //worker thread is doing work in the background
        //may be you want to download image here
        // or send messages at a certain time
        Log.d(TAG,"The service has now started");
    }
}
