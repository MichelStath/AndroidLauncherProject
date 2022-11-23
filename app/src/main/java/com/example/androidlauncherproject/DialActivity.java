package com.example.androidlauncherproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DialActivity extends AppCompatActivity {

    TextView phoneNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial);
        phoneNumber = findViewById(R.id.phoneInp);
    }

    public void clicktest(View view) {
        if(phoneNumber.getText().toString().length()<10) {
            Button currBTN = (Button) view;
            String currNum = phoneNumber.getText().toString().trim();
            String pressNum = currBTN.getText().toString().trim();
            currNum = currNum + pressNum;
            phoneNumber.setText(currNum);
            Log.i("Dial Activity","Number Pressed: "+ pressNum + " Phone Number: " + currNum);
        }else {
            Toast.makeText(this, "Exta Digit Pressed !", Toast.LENGTH_SHORT).show();
            Log.i("Dial Activity","Extra Number Preesed.");
        }
    }

    public void deleteChar(View view) {
        if(phoneNumber.getText().toString().trim().length() > 0){
            StringBuilder stringBuilder = new StringBuilder(phoneNumber.getText());
            stringBuilder.deleteCharAt(phoneNumber.getText().length()-1);
            String currNum = stringBuilder.toString();
            phoneNumber.setText(currNum);
        }else {
            Toast.makeText(this, "Nothing to Delete", Toast.LENGTH_SHORT).show();
        }

    }

    public void makeCAll(View view) {
    }
}
