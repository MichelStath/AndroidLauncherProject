package com.example.androidlauncherproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.EventListener;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;

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

            if(changeInAcceleration > 10){
                //Drop detection
                //remove accelerator listener
                //mSensorManager.unregisterListener(sensorEventListener);
                Log.i("Accel change " ,"" + changeInAcceleration);
                //phoneDroped();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };




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
        Log.d("Main Activity", "App Started");
        progressBar = findViewById(R.id.progressBar2);

        //sensor objects
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
        //start alarm
        //show dialog
        //press ok to register again the listener
        //mSensorManager.registerListener(sensorEventListener,mAccelerometer,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    public void dialBtnClicked(View view) {
        Intent intent = new Intent(MainActivity.this, DialActivity.class);
        startActivity(intent);
    }
}