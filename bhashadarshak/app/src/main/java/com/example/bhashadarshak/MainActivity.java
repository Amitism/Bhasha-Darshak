package com.example.bhashadarshak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(Build.VERSION.SDK_INT>16){
            getWindow().setFlags(1024,1024);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i= new Intent(getApplicationContext(),home.class);
                startActivity(i);
                finish();
            }
        },3000);
    }
}
