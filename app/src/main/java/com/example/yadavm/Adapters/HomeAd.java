package com.example.yadavm.Adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yadavm.Dialoghome;
import com.example.yadavm.Models.CartMo;
import com.example.yadavm.Models.HomeMo;
import com.example.yadavm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class HomeAd extends RecyclerView.Adapter<HomeAd.ViewHolder>{
    public Context mContext;
    public List<HomeMo> mHomeList;
    DatabaseReference reference;
    private static int counter = 0;
    private String stringVal;


    private ProgressDialog progressDialog;



    public HomeAd(Context mContext, List<HomeMo> mHomeList) {
        this.mContext = mContext;
        this.mHomeList = mHomeList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout,false);
//
//        return new HomeAd.VieHolder(view);
        View view  = LayoutInflater.from(mContext).inflate(R.layout.card_home_item,parent,false);

        progressDialog = new ProgressDialog(mContext);
        return new  HomeAd.ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final HomeMo homemodel = mHomeList.get(position);

        holder.itemname.setText(homemodel.getItemName());
        holder.itempricekg.setText(homemodel.getItemPricekg());
        holder.itempricepcs.setText(homemodel.getItemPricepcs());


        Glide.with(holder.itemimage.getContext())
                .load(homemodel.getItemImage())
                .into(holder.itemimage);

        holder.addButton.setOnClickListener(new View.OnClickListener() {

            public String itemname,itemid;

            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(mContext);
                dialog.setContentView(R.layout.dialog_item);
                ImageView imageView = dialog.findViewById(R.id.item_image_d);
                TextView textView = dialog.findViewById(R.id.item_name_d);

                textView.setText(homemodel.getItemName());
                Glide.with(mContext).load(homemodel.getItemImage()).into(imageView);

                ImageButton imageButtonPlusKg = dialog.findViewById(R.id.plus_button_kg);
                imageButtonPlusKg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        EditText editText = dialog.findViewById(R.id.edit_kg);
                        TextView textViewkgValue = dialog.findViewById(R.id.text_kg_value);

                        String str  = editText.getText().toString().trim();
                        if (TextUtils.isEmpty(str)){
                            counter ++;

                            stringVal = Integer.toString(counter);
                            editText.setText(stringVal);



                        }
                        else {
                            int value = Integer.parseInt(editText.getText().toString());
                            value = value+1;
                            stringVal = Integer.toString(value);
                            editText.setText(stringVal);
                        }

                    }
                });
                ImageButton imageButtonMinusKg = dialog.findViewById(R.id.minus_button_kg);
                imageButtonMinusKg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        EditText editText = dialog.findViewById(R.id.edit_kg);
                        String str  = editText.getText().toString().trim();
                        if (TextUtils.isEmpty(str)){

                            stringVal = Integer.toString(counter);
                            editText.setText(stringVal);
                        }
                        else if (stringVal.equals("0")){
                            editText.setText("0");
                        }
                        else {
                            int value = Integer.parseInt(editText.getText().toString());
                            value = value-1;
                            stringVal = Integer.toString(value);
                            editText.setText(stringVal);
                        }
                    }
                });
                ImageButton imageButtonPlusGm = dialog.findViewById(R.id.plus_button_gm);


                imageButtonPlusGm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        EditText editText = dialog.findViewById(R.id.edit_gm);
                        String str  = editText.getText().toString().trim();
                        if (TextUtils.isEmpty(str)){
                            counter ++;
                            stringVal = Integer.toString(counter);
                            editText.setText(stringVal);
                        }
                        else {
                            int value = Integer.parseInt(editText.getText().toString());
                            value = value+1;
                            stringVal = Integer.toString(value);
                            editText.setText(stringVal);
                        }

                    }
                });
                ImageButton imageButtonMinusGm = dialog.findViewById(R.id.minus_button_gm);
                imageButtonMinusGm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        EditText editText = dialog.findViewById(R.id.edit_gm);
                        String str  = editText.getText().toString().trim();
                        if (TextUtils.isEmpty(str)){

                            stringVal = Integer.toString(counter);
                            editText.setText(stringVal);
                        }
                        else if (stringVal.equals("0")){
                            editText.setText("0");
                        }
                        else {
                            int value = Integer.parseInt(editText.getText().toString());
                            value = value-1;
                            stringVal = Integer.toString(value);
                            editText.setText(stringVal);
                        }
                    }
                });



                ImageButton imageButtonPlusPcs = dialog.findViewById(R.id.plus_button_pcs);
                imageButtonPlusPcs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        EditText editText = dialog.findViewById(R.id.edit_pcs);
                        String str  = editText.getText().toString().trim();
                        if (TextUtils.isEmpty(str)){
                            counter ++;
                            stringVal = Integer.toString(counter);
                            editText.setText(stringVal);
                        }
                        else {
                            int value = Integer.parseInt(editText.getText().toString());
                            value = value+1;
                            stringVal = Integer.toString(value);
                            editText.setText(stringVal);
                        }

                    }
                });
                ImageButton imageButtonMinusPcs = dialog.findViewById(R.id.minus_button_pcs);
                imageButtonMinusPcs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        EditText editText = dialog.findViewById(R.id.edit_pcs);
                        String str  = editText.getText().toString().trim();
                        if (TextUtils.isEmpty(str)){

                            stringVal = Integer.toString(counter);
                            editText.setText(stringVal);
                        }
                        else if (stringVal.equals("0")){
                            editText.setText("0");
                        }
                        else {
                            int value = Integer.parseInt(editText.getText().toString());
                            value = value-1;
                            stringVal = Integer.toString(value);
                            editText.setText(stringVal);
                        }
                    }
                });



                Button  buttonCart = dialog.findViewById(R.id.add_to_cart_button);
                buttonCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressDialog.show();
                        CartMo cartMo = new CartMo();
                        Random rnd = new Random();
                        int n = 100000 + rnd.nextInt(900000);
                        cartMo.setItemName(homemodel.getItemName());
                        cartMo.setItemId(String.valueOf(n));
                        cartMo.setItemImage(homemodel.getItemImage());
                        FirebaseDatabase.getInstance().getReference().child("Carts")
                                .child(String.valueOf(n))
                                .setValue(cartMo)

                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        progressDialog.dismiss();
                                        dialog.dismiss();
                                    }
                                });

                    }
                });

                TextView textViewgmValue = dialog.findViewById(R.id.text_gm_value);
                TextView textViewpcsValue = dialog.findViewById(R.id.text_pcs_value);


                dialog.show();
            }
        });


    }



    @Override
    public int getItemCount() {
        return mHomeList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemname;
        public TextView itempricekg;
        public TextView itempricepcs;
        public ImageView itemimage;
        public Button addButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemname = itemView.findViewById(R.id.item_name);
            itempricekg = itemView.findViewById(R.id.item_price_kg);
            itempricepcs = itemView.findViewById(R.id.item_price_pcs);
            itemimage = itemView.findViewById(R.id.item_image);

            addButton = itemView.findViewById(R.id.add_button);


        }
    }
}

