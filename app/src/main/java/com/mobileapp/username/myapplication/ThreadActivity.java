package com.mobileapp.username.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

/**
 * Created by bchaudhr on 4/26/2017.
 */


    public class ThreadActivity extends AppCompatActivity {

        Thread thread;
        Handler handler;
        ProgressBar progressBar;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_thread);
            progressBar = (ProgressBar) findViewById(R.id.progressBar);

            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    //code here runs within the main thread

                    progressBar.setProgress(msg.arg1);
                }
            };

            thread = new Thread(new MyThread());
            thread.start();
        }

        class MyThread implements Runnable {

            @Override
            public void run() {
                for (int i=0; i<100; i++) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message message = Message.obtain();
                    message.arg1 = i;
                    handler.sendMessage(message);

                }
            }
        }
    }
