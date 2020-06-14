package com.example.yadavm.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import com.example.yadavm.Models.UserMo;
import com.example.yadavm.Dialogs.OtpLoginSheet;
import com.example.yadavm.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    private TextView textViewNew;
    private EditText editTextPhone;

    private Button buttonLogin;

    DatabaseReference reference;

    FirebaseAuth firebaseAuth;
    DialogLoading loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        loading = new DialogLoading();

        reference = FirebaseDatabase.getInstance().getReference();

        editTextPhone = (EditText) findViewById(R.id.user_phone);


        firebaseAuth = FirebaseAuth.getInstance();
        buttonLogin = (Button) findViewById(R.id.login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String phone = editTextPhone.getText().toString().trim();
                int phoneSize = phone.length();
                if (phone.isEmpty()) {
                    editTextPhone.setError("Cannot be blank");
                } else if (phoneSize != 10) {
                    editTextPhone.setError("Enter ten digits");
                } else {


                    buttonLogin.setEnabled(false);
                    loading.show(getSupportFragmentManager(), "Loading");

                    reference.child("User").orderByChild("phone").equalTo("+91" + phone).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null) {
                                reference.child("User").child("+91" + phone).child("Profile").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            UserMo userMo = dataSnapshot.getValue(UserMo.class);
                                            String phonefire = userMo.getPhone();

                                            if (phonefire.equals("+91" + phone)) {
                                                Bundle bundle = new Bundle();
                                                bundle.putString("phone", phone);

                                                loading.dismiss();
                                                OtpLoginSheet otpBottomSheet = new OtpLoginSheet();
                                                otpBottomSheet.setArguments(bundle);
                                                otpBottomSheet.show(getSupportFragmentManager(), "Hello");
                                                buttonLogin.setEnabled(true);

                                            } else {
                                                Toast.makeText(Login.this, "Plz Enter conrrect password", Toast.LENGTH_SHORT).show();
                                                loading.dismiss();
                                                buttonLogin.setEnabled(true);
                                            }
                                        }


                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Toast.makeText(Login.this, databaseError.toString(), Toast.LENGTH_SHORT).show();

                                        loading.dismiss();
                                        buttonLogin.setEnabled(true);

                                    }
                                });
                            } else {
                                Toast.makeText(Login.this, "User not registered ", Toast.LENGTH_SHORT).show();
                                buttonLogin.setEnabled(true);

                                loading.dismiss();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(Login.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
                            buttonLogin.setEnabled(true);
                            loading.dismiss();

                        }
                    });
                }

            }
        });

        textViewNew = findViewById(R.id.text_new_user);
        textViewNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Sign.class);
                startActivity(i);

            }
        });
    }


}
