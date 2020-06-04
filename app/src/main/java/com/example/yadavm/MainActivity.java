package com.example.yadavm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private BottomNavigationView bttmNvgtnvw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        // This is a demo pull req.

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

    }
}
