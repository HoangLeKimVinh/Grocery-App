package com.example.groceryapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.groceryapp.R;
import com.google.gson.Gson;

import io.paperdb.Paper;

public class SplashLoadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_load);
        Paper.init(this);
        Thread thread  = new Thread() {
            public void run() {
                try{
                    sleep(2300);
                }catch(Exception e){

                }finally{
                    if (Paper.book().read("user") == null){

                        Intent intent = new Intent(getApplicationContext(), DangNhapActivity.class);
                        startActivity(intent);
                        finish();
                    }else {

                        Intent home = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(home);
                        finish();
                    }

                }
            }
        };
        thread.start();
    }
}