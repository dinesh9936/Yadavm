package com.example.yadavm.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yadavm.Models.CardOrderItemMo;
import com.example.yadavm.Models.DialogOrderMo;
import com.example.yadavm.Models.OrderMo;
import com.example.yadavm.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class OrderAd extends RecyclerView.Adapter<OrderAd.VieHolder>{
    public Context mContext;
    public List<OrderMo> mHomeList;
    public  List<DialogOrderMo>dialogOrderMos;
  public OrderMo orderMo;
    /////new
    public List<CardOrderItemMo> cardOrderItemMoList;
    public CartOrderItemAd cartOrderItemAd;
/////new
    private DialogItemAd dialogItemAd;




    DatabaseReference reference;


    public OrderAd(Context mContext, List<OrderMo> mHomeList, List<DialogOrderMo> dialogOrderMos) {
        this.mContext = mContext;
        this.mHomeList = mHomeList;
        this.dialogOrderMos = dialogOrderMos;
    }

    @NonNull
    @Override
    public VieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.card_order_item,parent,false);
        reference = FirebaseDatabase.getInstance().getReference().child("My Orders");

        return new OrderAd.VieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAd.VieHolder holder, int position) {

         orderMo = mHomeList.get(position);

//        holder.recyclerViewitems.setAdapter(itemCarttoOrAd);
//        holder.recyclerViewitems.setLayoutManager(new LinearLayoutManager(mContext));
//        holder.recyclerViewitems.setHasFixedSize(true);
//        holder.recyclerViewitems.setAdapter(itemCarttoOrAd);
//        readPostitem();
        holder.recyclerViewitems.setLayoutManager(new LinearLayoutManager(mContext));
        holder.recyclerViewitems.setHasFixedSize(true);
        cardOrderItemMoList = new ArrayList<>();
        cartOrderItemAd = new CartOrderItemAd(cardOrderItemMoList,mContext);
        holder.recyclerViewitems.setAdapter(cartOrderItemAd);

        readPostItemName();

        holder.orderid.setText("Order ID:"+orderMo.getOrderid());
        holder.ordertimedate.setText(orderMo.getOrdertime()+" "+orderMo.getOrderdate());
        //holder.orderitems.setText(orderMo.getOrderitems());


        if (orderMo.getOrderstatus().equals("1")){
            holder.orderimage.setImageResource(R.drawable.ic_order_placed);
            holder.orderstatus.setText("Placed Order");
            holder.orderstatus.setTextColor(Color.parseColor("#FBC711"));
        }
        else if(orderMo.getOrderstatus().equals("2")){
            holder.orderimage.setImageResource(R.drawable.ic_order_delivered);

            holder.orderstatus.setText("Delivered");
            holder.orderstatus.setTextColor(Color.GREEN);
        }
        else if(orderMo.getOrderstatus().equals("3")){
            holder.orderimage.setImageResource(R.drawable.ic_cancel);

            holder.orderstatus.setText("Cancelled");
            holder.orderstatus.setTextColor(Color.RED);
        }
        holder.buttonDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(mContext);
                dialog.setContentView(R.layout.dialog_order_confirm);
                Button buttonCnfirm = dialog.findViewById(R.id.button_confirm);
                buttonCnfirm.setVisibility(View.GONE);
                TextView textView = dialog.findViewById(R.id.order_id);
                textView.setText("ORDER #"+orderMo.getOrderid());
                TextView textView1 = dialog.findViewById(R.id.status_text);
                if (orderMo.getOrderstatus().equals("1"))
                textView1.setText("Delivering to");
                else if(orderMo.getOrderstatus().equals("2"))
                    textView1.setText("Delivered to");
                else {
                    textView1.setText("Delivering to");
                }
                TextView textView2 = dialog.findViewById(R.id.text_address);
                textView2.setText("S-17/286 Nadesar,Varanasi 221002");

                RecyclerView recyclerView = dialog.findViewById(R.id.recycler_dialog_order);
                recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                recyclerView.setHasFixedSize(true);
                dialogOrderMos = new ArrayList<>();
                dialogItemAd = new DialogItemAd(mContext,dialogOrderMos);
                recyclerView.setAdapter(dialogItemAd);
                readPost();
                dialog.show();
            }
        });

    }

    private void readPostItemName() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("My Orders")
                .child(orderMo.getOrderid())
                .child("orderitems");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() == 0){
                    Toast.makeText(mContext, "Nothing", Toast.LENGTH_SHORT).show();
                }
                else {
                    for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        CardOrderItemMo cardOrderItemMo = dataSnapshot1.getValue(CardOrderItemMo.class);
                        cardOrderItemMoList.add(cardOrderItemMo);
                        cartOrderItemAd.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void readPost() {
        reference.keepSynced(true);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    DialogOrderMo dialogOrderMo = dataSnapshot1.getValue(DialogOrderMo.class);
                    dialogOrderMos.add(dialogOrderMo);
                    dialogItemAd.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    public int getItemCount() {
        return mHomeList.size();
    }


    public class VieHolder extends RecyclerView.ViewHolder {
        public TextView orderid,ordertimedate,orderstatus,orderitems;
        public ImageView orderimage;
        public Button buttonDetails;

        public RecyclerView recyclerViewitems;


        public VieHolder(@NonNull View itemView) {
            super(itemView);



            orderid = itemView.findViewById(R.id.order_id);
            orderstatus = itemView.findViewById(R.id.status_name);
            orderimage = itemView.findViewById(R.id.status_image);
            ordertimedate = itemView.findViewById(R.id.order_time_date);
           //orderitems = itemView.findViewById(R.id.oreder_items);

            recyclerViewitems = itemView.findViewById(R.id.order_items_recycler);




            buttonDetails = itemView.findViewById(R.id.button_details);



        }
    }


}

