package com.example.yadavm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;



public class Dialoghome extends AppCompatDialogFragment {
    public TextView itemname;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_item,null);

        builder.setView(view)
                .setTitle("hello");

        itemname = view.findViewById(R.id.item_name_d);
        itemname.setText("Hello");
        return builder.create();

    }
}
