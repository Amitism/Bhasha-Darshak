package com.example.bhashadarshak;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class home extends AppCompatActivity {
Button b1 ;
Button b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        b1=findViewById(R.id.buttontext_recog);
        b2=findViewById(R.id.buttontranslator);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(home.this,textrecog.class);
                        startActivity(i);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(home.this,translator.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(home.this)
                .setTitle("Alert")
                .setMessage("Do you want to exit ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setNegativeButton("No",null)
                .show();
    }
}
