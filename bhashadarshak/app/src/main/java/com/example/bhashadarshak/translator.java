package com.example.bhashadarshak;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;

public class translator extends AppCompatActivity {
Button translate;
EditText entered_text,translated_text;
Boolean isDownloaded=false;
Button b2;
SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translator);

        translate=findViewById(R.id.button1);
        entered_text=findViewById(R.id.editText1);
        translated_text=findViewById(R.id.editText2);
        b2=findViewById(R.id.modeldown);

        // Create an English-German translator:
        FirebaseTranslatorOptions options =
                new FirebaseTranslatorOptions.Builder()
                        .setSourceLanguage(FirebaseTranslateLanguage.HI)
                        .setTargetLanguage(FirebaseTranslateLanguage.EN)
                        .build();
        final FirebaseTranslator hindiEnglishTranslator =
                FirebaseNaturalLanguage.getInstance().getTranslator(options);



        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(translator.this, "Model will be downloaded in the background", Toast.LENGTH_SHORT).show();

                FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder()
                        // .requireWifi()
                        .build();
                hindiEnglishTranslator.downloadModelIfNeeded()
                        .addOnSuccessListener(
                                new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void v) {
                                        // Model downloaded successfully. Okay to start translating.
                                        // (Set a flag, unhide the translation UI, etc.)
                                        final SharedPreferences.Editor editor= sharedPreferences.edit();
                                        editor.putBoolean("Download",true);
                                        editor.apply();
                                        Toast.makeText(translator.this, "Model Downloaded", Toast.LENGTH_SHORT).show();

                                    }
                                })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Model couldn’t be downloaded or other internal error.
                                        // ...

                                    }
                                });

            }
        });


        translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isDownloaded=getDownload();
                if(isDownloaded==true){
                    if(TextUtils.isEmpty(entered_text.getText().toString())){
                        entered_text.setError("Required");
                        return;
                    }

                hindiEnglishTranslator.translate(entered_text.getText().toString())
                        .addOnSuccessListener(
                                new OnSuccessListener<String>() {
                                    @Override
                                    public void onSuccess(@NonNull String translatedText) {
                                        // Translation successful.
                                        translated_text.setText(translatedText);
                                    }
                                })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Error.
                                        // ...
                                    }
                                });
            }else{
                    Toast.makeText(translator.this, "Model is not downloaded", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private Boolean getDownload() {
        Boolean bool;
        SharedPreferences sharedPreferences=getSharedPreferences("application", Context.MODE_PRIVATE);
        bool=sharedPreferences.getBoolean("Download",false);
        return bool;
    }
}
