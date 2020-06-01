package com.example.yadavm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Loading extends AppCompatActivity {
 FirebaseAuth firebaseAuth;
    private ProgressBar dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        dialog = (ProgressBar)findViewById(R.id.progress_dialog) ;
        firebaseAuth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();



        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null){
            Intent intent = new Intent(Loading.this,MainActivity.class);
            startActivity(intent);
            finish();
            dialog.setVisibility(View.GONE);
        }
        else {

            Intent intent = new Intent(Loading.this,Login.class);
            startActivity(intent);
            finish();
            dialog.setVisibility(View.GONE);

        }



    }
}
