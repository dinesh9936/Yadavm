package com.example.yadavm.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yadavm.Models.CartMo;
import com.example.yadavm.R;

import java.util.ArrayList;

public class CartPlaceButtonAd extends RecyclerView.Adapter<CartPlaceButtonAd.MyViewHolder> {
ArrayList<CartMo> list;
Context context;

    public CartPlaceButtonAd(ArrayList<CartMo> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_list_item,parent,false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {



        holder.itemname.setText(list.get(position).getItemName());
        holder.itemprice.setText("₹"+list.get(position).getItemPricetotal());
        if (list.get(position).getItemQuantitykg().equals("0")){
            holder.itemqnty.setText(list.get(position).getItemQuantitypcs()+"Pcs");
        }
        else if (list.get(position).getItemQuantitypcs().equals("0")){
            String gm = String.valueOf(Integer.parseInt(list.get(position).getItemQuantitygm())*50);
            holder.itemqnty.setText(list.get(position).getItemQuantitykg()+"Kg"+"+"+gm+"Gm");
        }
        else {
            String gm = String.valueOf(Integer.parseInt(list.get(position).getItemQuantitygm())*50);
            holder.itemqnty.setText(list.get(position).getItemQuantitykg()+"Kg"+"+"+gm+"Gm"+"+"+list.get(position).getItemQuantitypcs()+"Psc");

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView itemname,itemqnty,itemprice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            itemname = itemView.findViewById(R.id.item_nm);
            itemqnty = itemView.findViewById(R.id.item_qt);

            itemprice = itemView.findViewById(R.id.item_prc);

        }
    }


}
