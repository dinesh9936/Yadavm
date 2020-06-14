package com.example.yadavm.Adapters;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yadavm.Dialogs.DialogAddFragment;
import com.example.yadavm.Models.SearchMo;
import com.example.yadavm.R;
import com.example.yadavm.Activity.Search;

import java.util.ArrayList;

public class SearchAd extends RecyclerView.Adapter<SearchAd.MyViewHolder> {

    ArrayList<SearchMo> list;
Context context;
    public SearchAd(ArrayList<SearchMo> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_home_item,parent,false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.itemname.setText(list.get(position).getItemName());

        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(holder.addButton.getContext(), "hello", Toast.LENGTH_SHORT).show();
                Search activity = (Search) (holder.addButton.getContext());
                FragmentManager fm = activity.getSupportFragmentManager();
                DialogAddFragment alertDialog = new DialogAddFragment();

                Bundle args = new Bundle();
                args.putString("itemname", list.get(position).getItemName());
                args.putString("itemimage",list.get(position).getItemImage());
                args.putString("itempricekg",list.get(position).getItemPricekg());
                args.putString("itempricepcs",list.get(position).getItemPricepcs());
                args.putString("itemid",list.get(position).getItemId());
                alertDialog.setArguments(args);
                alertDialog.show(fm, "fragment_alert");
            }
        });
        Glide.with(holder.itemimage.getContext())
                .load(list.get(position).getItemImage())
                .into(holder.itemimage);
        if (list.get(position).getItemPricekg().equals("0")){
            holder.itempricekg.setVisibility(View.GONE);
            holder.itempricepcs.setText("Rs."+list.get(position).getItemPricepcs()+"/Pcs");

        }
        else if (list.get(position).getItemPricepcs().equals("0")){
            holder.itempricepcs.setVisibility(View.GONE);
            holder.itempricekg.setText("Rs."+list.get(position).getItemPricekg()+"/Kg");

        }
        else {
            holder.itempricekg.setText("Rs."+list.get(position).getItemPricekg()+"/Kg");
            holder.itempricepcs.setText("Rs."+list.get(position).getItemPricepcs()+"/Pcs");
        }


      }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView itemname,itempricekg,itempricepcs;
        ImageView itemimage;
        Button addButton;

        public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        itemname = itemView.findViewById(R.id.item_name);
        itempricekg = itemView.findViewById(R.id.item_price_kg);
        itempricepcs = itemView.findViewById(R.id.item_price_pcs);

        itemimage = itemView.findViewById(R.id.item_image);

        addButton = itemView.findViewById(R.id.add_button);

        }
}
}
