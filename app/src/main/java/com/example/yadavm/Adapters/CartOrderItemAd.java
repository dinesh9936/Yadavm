package com.example.yadavm.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yadavm.Models.CardOrderItemMo;
import com.example.yadavm.R;

import java.util.List;

public class CartOrderItemAd extends RecyclerView.Adapter<CartOrderItemAd.ViewHolder> {

    public List<CardOrderItemMo> mCardOrderItemMoList;
    public Context mContext;

    public CartOrderItemAd(List<CardOrderItemMo> mCardOrderItemMoList, Context mContext) {
        this.mCardOrderItemMoList = mCardOrderItemMoList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CartOrderItemAd.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_card_item_names,parent,false);

        return new CartOrderItemAd.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartOrderItemAd.ViewHolder holder, int position) {
        final CardOrderItemMo cardOrderItemMo = mCardOrderItemMoList.get(position);
        holder.itemname.setText(cardOrderItemMo.getItemname());

    }

    @Override
    public int getItemCount() {
        return mCardOrderItemMoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView itemname;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemname = itemView.findViewById(R.id.item_name_card_card);


        }
    }
}
