package com.example.yadavm.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.yadavm.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Loading extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 1500;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        user = FirebaseAuth.getInstance().getCurrentUser();

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if (user!= null){
                    Intent mainIntent = new Intent(Loading.this,MainActivity.class);
                    Loading.this.startActivity(mainIntent);
                    Loading.this.finish();
                }
                else {
                    Intent mainIntent = new Intent(Loading.this,Login.class);
                    Loading.this.startActivity(mainIntent);
                    Loading.this.finish();
                }
                /* Create an Intent that will start the Menu-Activity. */

            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    }





