package com.mobileapp.username.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by bchaudhr on 4/26/2017.
 */

//whatever happens in the service it should not interfere with the functioning of the application
public class MyService extends Service {

    private static final String TAG = "MyService";
    public MyService() {

    }

    //when you start the service what do you want the service to do
    //you have to create your own thread
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"The service has now started");

        //create
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                for (int i=0; i<5; i++) {
                    Log.d(TAG, "Service is running code on another thread");
                }
            }
        };
        Thread myThread = new Thread(runnable);
        myThread.start();
        return Service.START_STICKY;
    }

    public void onDestroy() {
        Log.d(TAG,"The service has now ended");

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
