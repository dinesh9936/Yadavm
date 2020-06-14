package com.example.yadavm.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListenStatus extends Service {
    FirebaseDatabase db;
    DatabaseReference orders;


    @Override
    public void onCreate() {
        super.onCreate();

//        db = FirebaseDatabase.getInstance();
//        orders = db.getReference().child()

    }

    public ListenStatus() {
    }

    @Override
    public IBinder onBind(Intent intent) {
      return null;
    }
}
