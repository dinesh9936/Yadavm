package com.example.yadavm.Adapters;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.yadavm.Dialogs.DialogEditCart;
import com.example.yadavm.Activity.MainActivity;
import com.example.yadavm.Models.CartMo;
import com.example.yadavm.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CartAd extends RecyclerView.Adapter<CartAd.VieHolder>{

    public Context mContext;
    public List<CartMo> mHomeList;

    DatabaseReference reference;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    String itemid;

    long timestamp;

    public CartAd(Context mContext, List<CartMo> mHomeList) {
        this.mContext = mContext;
        this.mHomeList = mHomeList;

        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public CartAd.VieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view  = LayoutInflater.from(mContext).inflate(R.layout.card_cart_itme,parent,false);

        timestamp= System.currentTimeMillis();


        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("User").child(user.getPhoneNumber()).child("Carts");

        return new  CartAd.VieHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull  final CartAd.VieHolder holder,  final int position) {

        final CartMo homemodel = mHomeList.get(position);

        itemid = homemodel.getItemId();

        holder.textViewItemName.setText(homemodel.getItemName());
        holder.textViewKgValue.setText(homemodel.getItemPricekg());
        holder.imageButtonDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mContext)
                        .setTitle("Delete Item")
                        .setMessage("Do you really want to remove this item?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes,new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String itemLabel = String.valueOf(mHomeList.get(holder.getAdapterPosition()));
                                mHomeList.remove(holder.getAdapterPosition());
                                FirebaseDatabase.getInstance().getReference().child("User").child(user.getPhoneNumber()).child("Carts")
                                        .child(homemodel.getItemId())
                                        .removeValue();
                                notifyItemRemoved(holder.getAdapterPosition());
                                notifyItemRangeChanged(holder.getAdapterPosition(),getItemCount());
                            }
                        })
                        .setNegativeButton(android.R.string.no,null)
                        .show();


            }
        });



        if (homemodel.getItemType().equals("kg")){
            holder.linearLayoutPcs.setVisibility(View.GONE);
            holder.textViewKg.setText(homemodel.getItemQuantitykg());
            holder.textViewGm.setText(homemodel.getItemQuantitygm()+"00");
            holder.textViewKgValue.setText(homemodel.getItemPricekg());
            holder.textViewGmValue.setText(homemodel.getItemPricegm());
            holder.textViewTotal.setText("Rs."+homemodel.getItemPricetotal());
        }
        else if (homemodel.getItemType().equals("pcs")){
            holder.linearLayoutKg.setVisibility(View.GONE);
            holder.linearLayoutGm.setVisibility(View.GONE);
            holder.textViewPcs.setText(homemodel.getItemQuantitypcs());
            holder.textViewPcsValue.setText(homemodel.getItemPricepcs());
            holder.textViewTotal.setText("Rs."+homemodel.getItemPricetotal());
        }

        else {
            holder.textViewKg.setText(homemodel.getItemQuantitykg());
            holder.textViewGm.setText(homemodel.getItemQuantitygm()+"00");
            holder.textViewPcs.setText(homemodel.getItemQuantitypcs());
            holder.textViewPcsValue.setText(homemodel.getItemPricepcs());
            holder.textViewTotal.setText("Rs."+homemodel.getItemPricetotal());

            holder.textViewKgValue.setText(homemodel.getItemPricekg());
            holder.textViewGmValue.setText(homemodel.getItemPricegm());
        }





        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity activity = (MainActivity) (mContext);
                FragmentManager fm = activity.getSupportFragmentManager();
                DialogEditCart alertDialog = new DialogEditCart();

                Bundle args = new Bundle();

                args.putString("itemid",homemodel.getItemId());
                args.putString("total",homemodel.getItemPricetotal());
                args.putString("itemname", homemodel.getItemName());
                args.putString("itemimage", homemodel.getItemImage());
                args.putString("itempriceprkg",homemodel.getItemPriceprkg());
                args.putString("itempriceprpcs",homemodel.getItemPriceprpcs());
                args.putString("itemkg",holder.textViewKg.getText().toString().trim());
                args.putString("itemgm",homemodel.getItemQuantitygm());
                args.putString("itempcs",holder.textViewPcs.getText().toString().trim());
                args.putString("itemkgvalue",holder.textViewKgValue.getText().toString().trim());
                args.putString("itemgmvalue",holder.textViewGmValue.getText().toString().trim());
                args.putString("itempcsvalue",holder.textViewPcsValue.getText().toString().trim());
                args.putString("itemtype",homemodel.getItemType());
                alertDialog.setArguments(args);
                alertDialog.show(fm, "fragment_alert");
            }
        });

        Glide.with(holder.imageViewItemImage.getContext())
                .load(homemodel.getItemImage())
                .into(holder.imageViewItemImage);

    }
    @Override
    public int getItemCount() {
        return mHomeList.size();
    }

    public class VieHolder extends RecyclerView.ViewHolder {

        public TextView textViewItemName, textViewKgValue, textViewGmValue, textViewPcsValue, textViewKg, textViewGm, textViewPcs, textViewTotal;
        public ImageView imageViewItemImage;
        public ImageButton imageButtonDeleteButton;
        public LinearLayout linearLayoutKg, linearLayoutGm, linearLayoutPcs;
        public Button buttonEdit;

        public VieHolder(@NonNull View itemView) {
            super(itemView);

            textViewItemName = itemView.findViewById(R.id.cart_card_item_name);

            textViewKg = itemView.findViewById(R.id.text_kg);
            textViewGm = itemView.findViewById(R.id.text_gm);
            textViewPcs = itemView.findViewById(R.id.text_pcs);

            textViewKgValue = itemView.findViewById(R.id.text_value_kg);
            textViewGmValue = itemView.findViewById(R.id.text_value_gm);
            textViewPcsValue = itemView.findViewById(R.id.text_value_pcs);

            textViewTotal = itemView.findViewById(R.id.text_value_total);

            linearLayoutKg = itemView.findViewById(R.id.layout_kg);
            linearLayoutGm = itemView.findViewById(R.id.layout_gm);
            linearLayoutPcs = itemView.findViewById(R.id.layout_pcs);


            buttonEdit = itemView.findViewById(R.id.edit);






            imageButtonDeleteButton = itemView.findViewById(R.id.item_delete);



            imageViewItemImage = itemView.findViewById(R.id.cart_card_item_image);

        }
    }



}

