package com.example.yadavm;

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

import com.example.yadavm.Models.UserMo;
import com.firebase.ui.auth.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
private TextView textViewNew;
private EditText editTextPassword,editTextPhone;
private CheckBox checkBoxShow;
private Button buttonLogin;

DatabaseReference reference;

FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        reference = FirebaseDatabase.getInstance().getReference();
        editTextPassword = (EditText)findViewById(R.id.password);
        editTextPhone = (EditText)findViewById(R.id.user_phone);

        firebaseAuth = FirebaseAuth.getInstance();
        buttonLogin = (Button)findViewById(R.id.login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phone = editTextPhone.getText().toString().trim();
                final String password = editTextPassword.getText().toString().trim();
                if (phone.isEmpty()||password.isEmpty()){
                    Toast.makeText(Login.this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    reference.child("User").orderByChild("phone").equalTo("+91"+phone).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null){
                                reference.child("User").child("+91"+phone).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            UserMo userMo = dataSnapshot.getValue(UserMo.class);
                                            String phonefire = userMo.getPhone();
                                            String passwordfire = userMo.getPassword();
                                            if (phonefire.equals("+91"+phone)&&passwordfire.equals(password)){
                                                Intent i = new Intent(Login.this,MainActivity.class);
                                                startActivity(i);
                                                finish();
                                            }
                                            else {
                                                Toast.makeText(Login.this, "Plz Enter conrrect password", Toast.LENGTH_SHORT).show();
                                            }



                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                            else {
                                Toast.makeText(Login.this, "User not registered ", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }
        });

        textViewNew = (TextView)findViewById(R.id.text_new_user);
        textViewNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this,Sign.class);
                startActivity(i);
                finish();
            }
        });
        checkBoxShow = (CheckBox)findViewById(R.id.check_show);
        checkBoxShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (!isChecked) {
                       editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

                } else {
                      editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = firebaseAuth.getCurrentUser();

    }
}
