package com.example.androidlauncherproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaParser;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.EventListener;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    Timer timer;
    MediaPlayer mediaPlayer;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private double accelerationCurrValue;
    private double accelerationPrevValue;
    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            accelerationCurrValue = Math.sqrt((x*x + y*y + z*z));
            double changeInAcceleration = Math.abs(accelerationCurrValue - accelerationPrevValue);
            accelerationPrevValue = accelerationCurrValue;
            progressBar.setProgress((int) changeInAcceleration);
            Log.i("Accel change " ,"" + changeInAcceleration);

            if(changeInAcceleration > 9){
                mSensorManager.unregisterListener(sensorEventListener); //unRegister Accelerator listener
                //Drop detection
                Log.i("Accel listener " ,"Listener removed");
                Log.i("Accel change " ,"" + changeInAcceleration);
                phoneDroped();
                logDrops(changeInAcceleration);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private void logDrops(double force) {
        //keep logs of phone falling
    }


    @Override
    public void onBackPressed(){
        /*
            MUST DO NOTHING
            LAUNCHER ALWAYS ON START
        */
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("Main Activity", "App Started");
        progressBar = findViewById(R.id.progressBar2);

        //Sensor objects
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


    }

    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(sensorEventListener,mAccelerometer,SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(sensorEventListener);
    }

    private void phoneDroped() {
        //works must find sensitivity

        playAlertSound(R.raw.verifyok); //Alert sound start
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.i("timer","after 5 sec");
                //send messages
                //code here
                sendAlertMessage();
                stopAlertSound();   //close alert sound
                mSensorManager.registerListener(sensorEventListener,mAccelerometer,SensorManager.SENSOR_DELAY_NORMAL);  //Register Accelerator listener
            }
        },10000);

        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
        builder1.setTitle("Phone Droped");
        builder1.setMessage("Are you ok ?");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        timer.cancel();
                        dialog.cancel();
                        stopAlertSound();
                        //Register Accelerator listener
                        mSensorManager.registerListener(sensorEventListener,mAccelerometer,SensorManager.SENSOR_DELAY_NORMAL);

                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void sendAlertMessage() {
        //get family from db
        //get location
        //send message
    }

    private void stopAlertSound() {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
        }

    }

    private void playAlertSound(int song) {
        mediaPlayer = MediaPlayer.create(this,song);
        mediaPlayer.start();
    }



    //region MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }
    //create item click
    //endregion


    //region HOME APPS BUTTONS
    public void dialBtnClicked(View view) {
        Intent intent = new Intent(MainActivity.this, DialActivity.class);
        startActivity(intent);
    }
    //endregion

}