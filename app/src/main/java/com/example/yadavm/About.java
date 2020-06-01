package com.example.yadavm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class About extends AppCompatActivity {
private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        toolbar = (Toolbar)findViewById(R.id.toolbar_noti_notifi);
        setSupportActionBar(toolbar);
        (About.this).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_clear);

//        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_clear);
//
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.notification){
            Intent intent = new Intent(this,Notification.class);

            startActivity(intent );
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }
}