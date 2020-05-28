package com.example.yadavm.Adapters;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yadavm.DialogPlaceButton;
import com.example.yadavm.MainActivity;
import com.example.yadavm.Models.OrderMo;
import com.example.yadavm.R;

import java.util.List;

public class OrderAd extends RecyclerView.Adapter<OrderAd.MyViewHolder> {
    public Context mContext;
    public List<OrderMo> orderMoList;

    public OrderAd(Context mContext, List<OrderMo> orderMoList) {
        this.mContext = mContext;
        this.orderMoList = orderMoList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_order_item,parent,false);

        return new OrderAd.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        OrderMo orderMo = orderMoList.get(position);

        holder.textVieworderid.setText("Order Id#"+orderMo.getOrderId());
        holder.textViewtime.setText(orderMo.getOrderTime()+"  "+orderMo.getOrderDate());
        holder.textViewitems.setText(orderMo.getOrderItems());

        holder.buttonDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) (mContext);
                FragmentManager fm = activity.getSupportFragmentManager();
                DialogPlaceButton dialogPlaceButton = new DialogPlaceButton();

//                dialogPlaceButton.getDialog().findViewById(R.id.button_confirm_plce).setVisibility(View.GONE);
                //dialogPlaceButton.getDialog().findViewById(R.id.button_confirm_plce)
                dialogPlaceButton.show(fm,"Hello");
            }
        });
        String status = orderMo.getOrderStatus();
        if (status.equals("3")){
            holder.imageViewStatus.setImageResource(R.drawable.ic_cancel);
            holder.textViewStatus.setText("Canceled");
            holder.textViewStatus.setTextColor(Color.RED);

        }
        else if (status.equals("1")){
            holder.imageViewStatus.setImageResource(R.drawable.ic_order_placed);
            holder.textViewStatus.setText("Placed");
            holder.textViewStatus.setTextColor(Color.YELLOW);


        }
        else if (status.equals("2")){
            holder.imageViewStatus.setImageResource(R.drawable.ic_order_delivered);
            holder.textViewStatus.setText("Delivered");
            holder.textViewStatus.setTextColor(Color.GREEN);

        }
    }

    @Override
    public int getItemCount() {
        return orderMoList.size();
    }

   public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textVieworderid,textViewtime,textViewitems,textViewStatus;
        public ImageView imageViewStatus;
        public Button buttonDetails;

         public MyViewHolder(@NonNull View itemView) {
             super(itemView);

             textVieworderid = itemView.findViewById(R.id.order_id);
             textViewtime = itemView.findViewById(R.id.order_time_date);
             textViewitems = itemView.findViewById(R.id.oreder_items);

             textViewStatus = itemView.findViewById(R.id.status_name);
             imageViewStatus = itemView.findViewById(R.id.status_image);

             buttonDetails = itemView.findViewById(R.id.button_details);


         }
     }
}

