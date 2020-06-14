package com.example.yadavm.Dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.yadavm.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DialogUpdateName extends DialogFragment {
    EditText editTextAddress;
    Button button;
    DatabaseReference reference;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    ImageButton imageButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_update_name,container,false);
        getDialog().setCanceledOnTouchOutside(false);
        reference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        editTextAddress = view.findViewById(R.id.dialog_edit_text_name);
        imageButton = view.findViewById(R.id.clear_dialog);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        button = view.findViewById(R.id.submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = editTextAddress.getText().toString().trim();

                if (address.isEmpty()){
                    Toast.makeText(getActivity(), "Enter Your Nick Name...", Toast.LENGTH_SHORT).show();
                }

                else {
                    reference.child("User").child(user.getPhoneNumber()).child("Profile").child("name").setValue(address);
                    getDialog().dismiss();
                }

            }
            });
        return view;
    }
}
