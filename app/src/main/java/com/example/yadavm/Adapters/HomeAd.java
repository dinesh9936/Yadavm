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
import com.example.yadavm.Homefrag;
import com.example.yadavm.MainActivity;
import com.example.yadavm.Models.HomeMo;
import com.example.yadavm.R;

import java.util.List;

public class HomeAd extends RecyclerView.Adapter<HomeAd.VieHolder>{
    public Context mContext;
    public List<HomeMo> mHomeList;

    public HomeAd(Context mContext, List<HomeMo> mHomeList) {
        this.mContext = mContext;
        this.mHomeList = mHomeList;
    }


    @NonNull
    @Override
    public VieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout,false);
//
//        return new HomeAd.VieHolder(view);
        View view  = LayoutInflater.from(mContext).inflate(R.layout.card_home_item,parent,false);
        return new  HomeAd.VieHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull HomeAd.VieHolder holder, int position) {

        HomeMo homemodel = mHomeList.get(position);

        holder.itemname.setText(homemodel.getItemName());
        holder.itempricekg.setText(homemodel.getItemPricekg());
        holder.itempricepcs.setText(homemodel.getItemPricepcs());

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
        public TextView itempricekg;
        public TextView itempricepcs;
        public ImageView itemimage;

        public VieHolder(@NonNull View itemView) {
            super(itemView);

            itemname = itemView.findViewById(R.id.item_name);
            itempricekg = itemView.findViewById(R.id.item_price_kg);
            itempricepcs = itemView.findViewById(R.id.item_price_pcs);
            itemimage = itemView.findViewById(R.id.item_image);



        }
    }
}

