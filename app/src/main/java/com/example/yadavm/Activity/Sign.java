package com.example.yadavm.Activity;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.example.yadavm.Dialogs.DialogLoading;
import com.example.yadavm.Dialogs.OtpBottomSheet;
import com.example.yadavm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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

    DatabaseReference reference;

    DialogLoading loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        FirebaseApp.initializeApp(this);


        loading = new DialogLoading();

        reference = FirebaseDatabase.getInstance().getReference();
//
//        editTextCPassword =  findViewById(R.id.confirm_password);
//        editTextPassword =  findViewById(R.id.password);
        editTextName =  findViewById(R.id.user_name);
        editTextAddress = findViewById(R.id.user_address);
        editTextPhone =  findViewById(R.id.user_phone_no);
        buttonSend = findViewById(R.id.send_otp);



        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phone = editTextPhone.getText().toString().trim();
                final String name = editTextName.getText().toString().trim();
//                final String password = editTextPassword.getText().toString().trim();
//                String cpassword = editTextCPassword.getText().toString().trim();
                final String address = editTextAddress.getText().toString().trim();

                if (address.isEmpty()){
                    editTextAddress.setError("Enter address");
                }
                else if (name.isEmpty()){
                    editTextName.setError("Name cannot be empty");
                }
                else if (phone.isEmpty())
                    editTextPhone.setError("Enter phone number");

                else if (phone.length() != 10)
                    editTextPhone.setError("Enter ten digit phone number");
                else {
                    buttonSend.setEnabled(false);
                    loading.show(getSupportFragmentManager(),"Loading");
                    reference.child("User").orderByChild("phone").equalTo("+91"+phone).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()){
                                if (dataSnapshot.getValue() != null){
                                    Toast.makeText(Sign.this, "User Already Exist", Toast.LENGTH_SHORT).show();
                                    loading.dismiss();
                                    buttonSend.setEnabled(true);
                                }
                                else {
                                    loading.dismiss();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("phone", phone);
                                    bundle.putString("name",name);

                                    bundle.putString("address",address);


                                    OtpBottomSheet otpBottomSheet = new OtpBottomSheet();
                                    otpBottomSheet.setArguments(bundle);
                                    otpBottomSheet.show(getSupportFragmentManager(),"Hello");

                                    buttonSend.setEnabled(true);
                                }
                            }
                            else {
                                loading.dismiss();
                                Bundle bundle = new Bundle();
                                bundle.putString("phone", phone);
                                bundle.putString("name",name);

                                bundle.putString("address",address);


                                OtpBottomSheet otpBottomSheet = new OtpBottomSheet();
                                otpBottomSheet.setArguments(bundle);
                                otpBottomSheet.show(getSupportFragmentManager(),"Hello");

                                buttonSend.setEnabled(true);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                            buttonSend.setEnabled(true);
                            loading.dismiss();
                        }
                    });




                }




            }
        });


        textViewOlduser =findViewById(R.id.text_old_user);
        textViewOlduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Sign.this, Login.class);
                startActivity(i);

            }
        });



    }






}