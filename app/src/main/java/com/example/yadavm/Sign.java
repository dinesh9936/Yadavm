package com.example.yadavm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Sign extends AppCompatActivity {
    private EditText editTextPassword, editTextCPassword, editTextName, editTextPhone;
    private CheckBox checkBoxShow;
    private TextView textViewOlduser;

    private Button buttonSend;
    FirebaseAuth fAuth;

    private String verID;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        fAuth = FirebaseAuth.getInstance();
        editTextCPassword = (EditText) findViewById(R.id.confirm_password);
        editTextPassword = (EditText) findViewById(R.id.password);
        editTextName = (EditText) findViewById(R.id.user_name);
        editTextPhone = (EditText) findViewById(R.id.user_phone);
        buttonSend = (Button) findViewById(R.id.send_otp);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String phone = editTextPhone.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String cpassword = editTextCPassword.getText().toString().trim();


                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(password) || TextUtils.isEmpty(cpassword)) {
                    Toast.makeText(Sign.this, "please input all entries", Toast.LENGTH_SHORT).show();
                } else {
                    if (password.equals(cpassword)) {
                        Toast.makeText(Sign.this, "matched", Toast.LENGTH_SHORT).show();
                        //sendVerificationCode(phone);
                        Intent i = new Intent(Sign.this,Login.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(Sign.this, "mismatch", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });


        textViewOlduser = (TextView) findViewById(R.id.text_old_user);
        textViewOlduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Sign.this, Login.class);
                startActivity(i);
            }
        });

        checkBoxShow = (CheckBox) findViewById(R.id.show_check);
        checkBoxShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (!isChecked) {
                    // show password
                    //editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    editTextCPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

                } else {
                    // hide password
                    //editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    editTextCPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });


    }




}
