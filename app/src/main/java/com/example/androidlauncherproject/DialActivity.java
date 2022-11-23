package com.example.androidlauncherproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.xml.sax.helpers.LocatorImpl;

public class DialActivity extends AppCompatActivity {

    TextView phoneNumber;
    static int PERMISSION_CODE = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial);
        phoneNumber = findViewById(R.id.phoneInp);

        if(ContextCompat.checkSelfPermission(DialActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(DialActivity.this,new String[]{Manifest.permission.CALL_PHONE},PERMISSION_CODE);
        }

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
        Log.i("Dial Activity", "Call BTN Pressed");
        try {
            String phoneNum = phoneNumber.getText().toString().trim();
            Intent i = new Intent(Intent.ACTION_CALL);
            i.setData(Uri.parse("tel:" + phoneNum));
            startActivity(i);
        } catch (Exception e) {
            Log.e("Dial Activity" , "Call error ");
            e.printStackTrace();
        }finally {
            Log.i("Dial Activity" , "Call Success");
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != PERMISSION_CODE || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }
}
