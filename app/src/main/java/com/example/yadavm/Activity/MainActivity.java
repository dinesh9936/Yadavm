package com.example.yadavm.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.yadavm.Fragments.Cartfrag;
import com.example.yadavm.Fragments.Homefrag;
import com.example.yadavm.Fragments.Ordersfrag;
import com.example.yadavm.Fragments.Profile;
import com.example.yadavm.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private BottomNavigationView bttmNvgtnvw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FirebaseApp.initializeApp(this);


        loadFragment(new Homefrag());

        bttmNvgtnvw = findViewById(R.id.bottom_nav_bar);
        bttmNvgtnvw.setOnNavigationItemSelectedListener(this);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragmenta = null;
        switch (menuItem.getItemId()) {
            case R.id.home:
                fragmenta = new Homefrag();
                break;
            case R.id.order:
                fragmenta = new Ordersfrag();
                break;
            case R.id.cart:
                fragmenta = new Cartfrag();
                break;
            case R.id.profile:
                fragmenta = new Profile();
                break;

        }
        return loadFragment(fragmenta);
    }
    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            Intent intent = new Intent(MainActivity.this,Login.class);
            startActivity(intent);
            finish();
        }

    }


}
