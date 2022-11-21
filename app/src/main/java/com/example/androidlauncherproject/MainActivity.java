package com.example.androidlauncherproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.EventListener;

public class MainActivity extends AppCompatActivity {

    Button button;

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
        button=findViewById(R.id.dial_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DialActivity.class);
                startActivity(intent);
            }
        });

    }

    public void test(){
        //yolo
        //yolooo
    }
}