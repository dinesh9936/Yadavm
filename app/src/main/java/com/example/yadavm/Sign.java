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
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class Sign extends AppCompatActivity {
    private EditText editTextPassword, editTextCPassword, editTextName, editTextPhone,editTextAddress;
    private CheckBox checkBoxShow;
    private TextView textViewOlduser;

    private Button buttonSend;
    private String  verificationCodeBySystem;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        FirebaseApp.initializeApp(this);



        reference = FirebaseDatabase.getInstance().getReference();

        editTextCPassword = (EditText) findViewById(R.id.confirm_password);
        editTextPassword = (EditText) findViewById(R.id.password);
        editTextName = (EditText) findViewById(R.id.user_name);
        editTextAddress = (EditText)findViewById(R.id.user_address);
        editTextPhone = (EditText) findViewById(R.id.user_phone_no);
        buttonSend = (Button) findViewById(R.id.send_otp);



        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final String phone = editTextPhone.getText().toString().trim();
               final String name = editTextName.getText().toString().trim();
               final String password = editTextPassword.getText().toString().trim();
               String cpassword = editTextCPassword.getText().toString().trim();
               final String address = editTextAddress.getText().toString().trim();

               if (phone.isEmpty()||name.isEmpty()||password.isEmpty()||cpassword.isEmpty() ||address.isEmpty()){
                   Toast.makeText(Sign.this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
               }
               else {
                   if (password.equals(cpassword)){
                       reference.child("User").orderByChild("phone").equalTo(phone).addListenerForSingleValueEvent(new ValueEventListener() {
                           @Override
                           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                               if (dataSnapshot.getValue() != null){
                                   Toast.makeText(Sign.this, "User Already Exist", Toast.LENGTH_SHORT).show();
                               }
                               else {
                                   Bundle bundle = new Bundle();
                                   bundle.putString("phone", phone);
                                   bundle.putString("name",name);
                                   bundle.putString("password",password);
                                   bundle.putString("address",address);


                                   OtpBottomSheet otpBottomSheet = new OtpBottomSheet();
                                   otpBottomSheet.setArguments(bundle);
                                   otpBottomSheet.show(getSupportFragmentManager(),"Hello");
                               }
                           }

                           @Override
                           public void onCancelled(@NonNull DatabaseError databaseError) {

                           }
                       });

                   }
                   else {
                       Toast.makeText(Sign.this, "Password Not Matched!", Toast.LENGTH_SHORT).show();
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
                finish();
            }
        });

        checkBoxShow = (CheckBox) findViewById(R.id.show_check);
        checkBoxShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (!isChecked) {

                    editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    editTextCPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

                } else {

                    editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    editTextCPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });


    }

    private void sendVerificationCodeToUser(String phone) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+918840723127",        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,   // Activity (for callback binding)
                mCallbacks);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    String code = phoneAuthCredential.getSmsCode();
                    if (code != null) {
                        //progressBar.setVisibility(View.VISIBLE);
                        verifyCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(Sign.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);


                    verificationCodeBySystem = s;
                }
            };

    private void verifyCode(String codeByUser) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem, codeByUser);
        signInTheUserByCredentials(credential);

    }
    private void signInTheUserByCredentials(PhoneAuthCredential credential) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(Sign.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            Toast.makeText(Sign.this, "Your Account has been created successfully!", Toast.LENGTH_SHORT).show();

                            //Perform Your required action here to either let the user sign In or do something required
                            Intent intent = new Intent(getApplicationContext(), Login.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        } else {
                            Toast.makeText(Sign.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
