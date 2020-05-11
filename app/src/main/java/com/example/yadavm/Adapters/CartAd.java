package com.example.yadavm.Adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yadavm.Models.CartMo;

import com.example.yadavm.R;

import java.util.List;

public class CartAd extends RecyclerView.Adapter<CartAd.VieHolder>{

    public Context mContext;
    public List<CartMo> mHomeList;

    public CartAd(Context mContext, List<CartMo> mHomeList) {
        this.mContext = mContext;
        this.mHomeList = mHomeList;
    }

    @NonNull
    @Override
    public CartAd.VieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout,false);
//
//        return new HomeAd.VieHolder(view);
        View view  = LayoutInflater.from(mContext).inflate(R.layout.card_cart_itme,parent,false);
        return new  CartAd.VieHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull CartAd.VieHolder holder, int position) {

        CartMo homemodel = mHomeList.get(position);

        holder.itemname.setText(homemodel.getItemName());


        Glide.with(holder.itemimage.getContext())
                .load(homemodel.getItemImage())
                .into(holder.itemimage);

    }



    @Override
    public int getItemCount() {
        return mHomeList.size();
    }


    public class VieHolder extends RecyclerView.ViewHolder {
        public TextView itemname;
        public ImageView itemimage;

        public VieHolder(@NonNull View itemView) {
            super(itemView);

            itemname = itemView.findViewById(R.id.cart_card_item_name);

            itemimage = itemView.findViewById(R.id.cart_card_item_image);



        }
    }
}

