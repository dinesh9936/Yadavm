package com.example.yadavm.Adapters;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yadavm.Dialogs.DialogDetailsButton;
import com.example.yadavm.Activity.MainActivity;
import com.example.yadavm.Models.OrderMo;
import com.example.yadavm.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class OrderAd extends RecyclerView.Adapter<OrderAd.MyViewHolder> {
    public Context mContext;
    public List<OrderMo> orderMoList;

    FirebaseUser user;



    public OrderAd(Context mContext, List<OrderMo> orderMoList) {
        this.mContext = mContext;
        this.orderMoList = orderMoList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_order_item,parent,false);


        user = FirebaseAuth.getInstance().getCurrentUser();

        return new OrderAd.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        final OrderMo orderMo = orderMoList.get(position);



        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                dialog.setCancelable(false);
                dialog.setTitle("Dialog on Android");
                dialog.setMessage("Are you sure you want to delete this entry?" );
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //Action for "Delete".
                        String itemLabel = String.valueOf(orderMoList.get(position));
                        orderMoList.remove(position);

                        FirebaseDatabase.getInstance().getReference().child("User").child(user.getPhoneNumber())
                                .child("My Orders")
                                .child(orderMo.getOrderId())
                                .removeValue();
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position,orderMoList.size());

                    }
                })
                        .setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                final AlertDialog alert = dialog.create();
                alert.show();
                return false;
            }
        });
        holder.textVieworderid.setText("Order Id#"+orderMo.getOrderId());
        holder.textViewtime.setText(orderMo.getOrderTime()+"  "+orderMo.getOrderDate());
        holder.textViewitems.setText(orderMo.getOrderItems());

        holder.buttonDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) (mContext);
                FragmentManager fm = activity.getSupportFragmentManager();
                DialogDetailsButton dialogPlaceButton = new DialogDetailsButton();
                Bundle args = new Bundle();
                args.putString("orderid", orderMo.getOrderId());
                dialogPlaceButton.setArguments(args);
//                dialogPlaceButton.getDialog().findViewById(R.id.button_confirm_plce).setVisibility(View.GONE);
                //dialogPlaceButton.getDialog().findViewById(R.id.button_confirm_plce)
                dialogPlaceButton.show(fm,"Hello");
            }
        });
        String cancelMessage = orderMo.getCancelMessage();
        if (!cancelMessage.isEmpty()){
            holder.textViewCancelMessage.setText(cancelMessage);
            holder.textViewCancelMessage.setTextColor(Color.RED);
        }
        else
            holder.textViewCancelMessage.setVisibility(View.GONE);


        String sts = orderMo.getOrderStatus();
        if (sts.equals("1")){
            holder.imageViewStatus.setImageResource(R.drawable.ic_order_placed);
            holder.textViewStatus.setText("Placed");
            holder.textViewStatus.setTextColor(R.color.colorPrimaryDark);

        }
        else if (sts.equals("0")){
            holder.imageViewStatus.setImageResource(R.drawable.ic_waiting);
            holder.textViewStatus.setText("Waiting");
            holder.textViewStatus.setTextColor(R.color.waiting);


        }
        else if (sts.equals("2")){
            holder.imageViewStatus.setImageResource(R.drawable.ic_order_delivered);
            holder.textViewStatus.setText("Delivered");
            holder.textViewStatus.setTextColor(Color.GREEN);

        }
        else if (sts.equals("3")){
            holder.imageViewStatus.setImageResource(R.drawable.ic_cancel);
            holder.textViewStatus.setText("Canceled");
            holder.textViewStatus.setTextColor(Color.RED);

        }
    }

    @Override
    public int getItemCount() {
        return orderMoList.size();
    }

   public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textVieworderid,textViewtime,textViewCancelMessage, textViewitems,textViewStatus;
        public ImageView imageViewStatus;
        public Button buttonDetails;

        public LinearLayout linearLayout;

         public MyViewHolder(@NonNull View itemView) {
             super(itemView);

             textVieworderid = itemView.findViewById(R.id.order_id);
             textViewtime = itemView.findViewById(R.id.order_time_date);
             textViewitems = itemView.findViewById(R.id.oreder_items);

             textViewStatus = itemView.findViewById(R.id.status_name);
             imageViewStatus = itemView.findViewById(R.id.status_image);

             buttonDetails = itemView.findViewById(R.id.button_details);


             linearLayout = itemView.findViewById(R.id.order_card_layout);

             textViewCancelMessage = itemView.findViewById(R.id.text_cancel_message);


         }
     }
}

