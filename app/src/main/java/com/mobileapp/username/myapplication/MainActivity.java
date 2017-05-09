package com.mobileapp.username.myapplication;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final String TAG = "Main Activity";
    static final String STATE_SCORE= "playerScore";
    private int mCurrentScore;
    boolean mStopLoop;
    TextView textView;
    private AsyncTask asyncTask;
    //Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //always call the superclass methods
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //This method only gets executed one time during activity lifecycle
        Toast.makeText(getApplicationContext(), "onCreate", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onCreate");
        mCurrentScore = 0;
        //restore storedInstanceState variables
        if (savedInstanceState != null) {
            mCurrentScore = savedInstanceState.getInt(STATE_SCORE);
        }

        textView = (TextView) findViewById(R.id.text_view);
        //handler= new Handler(getApplicationContext().getMainLooper()); <-- main or UI Thread
    }

    public void onStartClick(View view){
        //switch case for r.id
        //initialize the AsyncTask class inside the start button
        mStopLoop = true;
        asyncTask = new MyAsyncTask();
        Object[] args = new Integer[]{mCurrentScore,null,null};
        asyncTask.execute(args);  //initial variable that is going to get updated

        // the stop button will set mStopLoop = false or set the asyncTask.cancel(true);
    }

    public void onStopClick(View view) {
        if (asyncTask != null) {
            asyncTask.cancel(true); //or mStopLoop = false;
        }
    }

    public void onClick(View view) {
       Intent intent = new Intent(this, ThreadActivity.class);
        startActivity(intent);
    }

    public void startServiceIntent(View v) {
        Intent intent = new Intent(this, MyIntentService.class);
        startService(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Toast.makeText( getApplicationContext(), "onSaveInstanceState", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onSaveInstanceState");
        //member variable that needs to be saved before the activity is paused
        //mCurrentScore = 0;
        //example of saving member variables
        savedInstanceState.putInt(STATE_SCORE, mCurrentScore);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        // this savedInstanceState variable is the same one that was received by
        // onSaveInstanceState, onCreate methods. This means you can use the same
        // key names to retrieve these variables.
        super.onRestoreInstanceState(savedInstanceState);

        // the system will only call this method if savedInstanceState is not null, therefore,
        // unlike in onCreate, you do not need to check if the savedInstanceState Bundle is not null
        //restore the saved variables
        mCurrentScore = savedInstanceState.getInt(STATE_SCORE);

        Toast.makeText( getApplicationContext(), "onRestoreInstanceState", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onRestoreInstanceState");
    }

    public void myThreadFunction() {

        Log.i(TAG, "Thread id is " + Thread.currentThread().getId());
        //create two buttons to start and stop the thread
        //new Thread(new Runnable() {
        //    @Override
        //    public void run() {
        //       Log.i(TAG, "Thread id is " + Thread.currentThread().getId());
        //    }
        //}).start();
    }

    public void myUIThreadFunction() {

        Log.i(TAG, "Thread id is " + Thread.currentThread().getId());
        //create two buttons to start and stop the thread
        //new Thread(new Runnable() {
        //    @Override
        //    public void run() {
        //       //while (mStopLoop) {
        //       //      try {
        //       //        Thread.sleep(1000);
        //       //        count++;
        //       //      } catch (InterruptedException ex) {
        //       //        Log.i(TAG, ex.getMessage());
        //       //      }
        //       //   textView.setText(Integer.toString(count)); <--- this will fail because textView is created on the UI Thread
        //      //}
        //    }
        //}).start();
    }


    public void mHandlerThreadFunction() {

        Log.i(TAG, "Thread id is " + Thread.currentThread().getId());
        //create two buttons to start and stop the thread
        //new Thread(new Runnable() {
        //    @Override
        //    public void run() {
        //       //while (mStopLoop) {
        //       //      try {
        //       //        Thread.sleep(1000);
        //       //        count++;
        //       //      } catch (InterruptedException ex) {
        //       //        Log.i(TAG, ex.getMessage());
        //       //      }
        //       //      handler.post(new Runnable() {  or textview.post(new Runnable() {
        //       //      @Override
        //       //      public void run() {
        //       //            textview.setThread(""+count);
        //       //      }
        //       //   });
        //      //}
        //    }
        //}).start();
    }


    // best to declare this class as a static class
    // Params: The type of value that is needed to start execution of the AsyncTask will start its work, it can be any variable type you want, e.g. int or String
    // Progress: The type of information used by the onProgressUpdate method uses to update the UI as and when the UI is being updated
    // Result: The final result update for the user interface
    // AsyncTask only runs once. cannot be restarted
    private class MyAsyncTask extends AsyncTask<Integer, Integer, Integer> {

    private int customCounter;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            customCounter = 0;
            Log.d(TAG, "onPreExecute");
            if(MainActivity.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                MainActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
            } else {
                if(MainActivity.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    MainActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
                }
            }
        }

        @Override
        protected Integer doInBackground(Integer... params) { // params is an array
            customCounter=params[0];
           // new Thread(new Runnable() {
             //   @Override
             //   public void run() {
                   while (mStopLoop) {
                         try {
                           Thread.sleep(10000);
                           customCounter++;
                           publishProgress(customCounter);
                             Log.d(TAG, "doInBackground");
                         } catch (InterruptedException ex) {
                           Log.i(TAG, ex.getMessage());
                         }
                 }
              //  }
            //}).start();
            return customCounter;
        }


        // value is the value returned by the doInBackground method
        @Override
        protected void onPostExecute(Integer value) {
            super.onPostExecute(value);
            textView.setText(Integer.toString(value));
            mCurrentScore=value; //<-- main class variable that is being updated or shown in the UI
            Log.d(TAG, "onPostExecute");
            MainActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        }

        //values is returned by the doInBackgound during its execution
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (!isCancelled()) {
                textView.setText(Integer.toString(values[0]));
                Log.d(TAG, "onProgressUpdate");
            }
        }

    }



    @Override
    protected void onRestart() {
        super.onRestart();
        //Only executed when the activity is coming from the stopped state
        Toast.makeText( getApplicationContext(), "onRestart", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText( getApplicationContext(), "onStart", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText( getApplicationContext(), "onResume", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onResume");
    }


    @Override
    protected void onPause() {
        super.onPause();
        //Executed when hit the home button or another activity comes to the foreground.
        //The activity must be restarted before it can come to the foreground
        Toast.makeText( getApplicationContext(), "onPause", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Executed when hit the home button or another activity comes to the foreground.
        //The activity must be restarted before it can come to the foreground
        Toast.makeText( getApplicationContext(), "onStop", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //This method only gets executed one time during activity lifecycle
        //Executed when the back button is hit
        Toast.makeText( getApplicationContext(), "onDestroy", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onDestroy");
    }
}
