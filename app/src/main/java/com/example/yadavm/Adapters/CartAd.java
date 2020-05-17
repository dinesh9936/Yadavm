package com.example.yadavm.Adapters;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.yadavm.Models.CartMo;
import com.example.yadavm.Models.DialogOrderMo;
import com.example.yadavm.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.List;

public class CartAd extends RecyclerView.Adapter<CartAd.VieHolder>{

    public Context mContext;
    public List<CartMo> mHomeList;
    public  List<DialogOrderMo>dialogOrderMos;
    private DialogItemCartAd dialogItemCartAd;
    DatabaseReference reference;

    public CartAd(Context mContext, List<CartMo> mHomeList, List<DialogOrderMo> dialogOrderMos) {
        this.mContext = mContext;
        this.mHomeList = mHomeList;
        this.dialogOrderMos = dialogOrderMos;
    }

    @NonNull
    @Override
    public CartAd.VieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view  = LayoutInflater.from(mContext).inflate(R.layout.card_cart_itme,parent,false);

        reference = FirebaseDatabase.getInstance().getReference().child("Cart");

        return new  CartAd.VieHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull final CartAd.VieHolder holder, final int position) {

       final CartMo homemodel = mHomeList.get(position);

        holder.itemname.setText(homemodel.getItemName());


        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new AlertDialog.Builder(mContext)
                        .setTitle("Title")
                        .setMessage("Do you really want to remove this item?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes,new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String itemLabel = String.valueOf(mHomeList.get(position));
                                mHomeList.remove(position);

                                FirebaseDatabase.getInstance().getReference().child("Carts")
                                        .child(homemodel.getItemId())
                                        .removeValue();
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position,mHomeList.size());
                                Toast.makeText(mContext, itemLabel, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(android.R.string.no,null)
                        .show();


            }
        });
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
        public ImageButton deleteButton;

        public VieHolder(@NonNull View itemView) {
            super(itemView);

            itemname = itemView.findViewById(R.id.cart_card_item_name);

            itemimage = itemView.findViewById(R.id.cart_card_item_image);


            deleteButton = itemView.findViewById(R.id.item_delete);


        }
    }



}

