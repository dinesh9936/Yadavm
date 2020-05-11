package com.example.yadavm.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yadavm.Models.OrderMo;
import com.example.yadavm.R;

import java.util.List;

public class OrderAd extends RecyclerView.Adapter<OrderAd.VieHolder>{
    public Context mContext;
    public List<OrderMo> mHomeList;

    public OrderAd(Context mContext, List<OrderMo> mHomeList) {
        this.mContext = mContext;
        this.mHomeList = mHomeList;
    }


    @NonNull
    @Override
    public VieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.card_order_item,parent,false);
//
//        return new OrderAd.VieHolder(view);
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_order_item,parent,false);
        return new OrderAd.VieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAd.VieHolder holder, int position) {

        OrderMo orderMo = mHomeList.get(position);

        holder.orderid.setText("Order ID:"+orderMo.getOrderid());
        holder.ordertimedate.setText(orderMo.getOrdertime()+" "+orderMo.getOrderdate());
        holder.orderitems.setText(orderMo.getOrderitems());
        if (orderMo.getOrderstatus().equals("1")){
            holder.orderimage.setImageResource(R.drawable.ic_order_delivered);
            holder.orderstatus.setText("Delivered");
            holder.orderstatus.setTextColor(Color.GREEN);
        }
        else if(orderMo.getOrderstatus().equals("2")){
            holder.orderimage.setImageResource(R.drawable.ic_order_placed);

            holder.orderstatus.setText("Order Placed");
            holder.orderstatus.setTextColor(Color.parseColor("#FBC711"));
        }
        else if(orderMo.getOrderstatus().equals("3")){
            holder.orderimage.setImageResource(R.drawable.ic_cancel);

            holder.orderstatus.setText("Cancelled");
            holder.orderstatus.setTextColor(Color.RED);
        }


    }



    @Override
    public int getItemCount() {
        return mHomeList.size();
    }


    public class VieHolder extends RecyclerView.ViewHolder {
        public TextView orderid,ordertimedate,orderstatus,orderitems;
        public ImageView orderimage;


        public VieHolder(@NonNull View itemView) {
            super(itemView);



            orderid = itemView.findViewById(R.id.order_id);
            orderstatus = itemView.findViewById(R.id.status_name);
            orderimage = itemView.findViewById(R.id.status_image);
            ordertimedate = itemView.findViewById(R.id.order_time_date);
            orderitems = itemView.findViewById(R.id.order_items);



        }
    }
}

