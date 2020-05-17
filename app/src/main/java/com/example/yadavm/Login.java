package com.example.yadavm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {
private TextView textViewNew;
private EditText editTextPassword;
private CheckBox checkBoxShow;
private Button buttonLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonLogin = (Button)findViewById(R.id.login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this,MainActivity.class);
                startActivity(i);
            }
        });
        editTextPassword = (EditText)findViewById(R.id.password);
        textViewNew = (TextView)findViewById(R.id.text_new_user);
        textViewNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this,Sign.class);
                startActivity(i);
            }
        });
        checkBoxShow = (CheckBox)findViewById(R.id.check_show);
        checkBoxShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (!isChecked) {
                    // show password
                    //editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    //editTextCPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

                } else {
                    // hide password
                    //editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    //editTextCPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
    }
}
