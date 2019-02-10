package com.example.sahebojha.todoapp;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

public class splashscreen extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent intent = new Intent(splashscreen.this,MainActivity.class);
                splashscreen.this.startActivity(intent);
                splashscreen.this.finish();

            }
        },1000);


    }
}
