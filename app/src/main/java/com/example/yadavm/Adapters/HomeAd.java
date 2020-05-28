package com.example.yadavm.Adapters;

import android.app.ProgressDialog;
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
import com.example.yadavm.DialogAddFragment;
import com.example.yadavm.MainActivity;
import com.example.yadavm.Models.HomeMo;
import com.example.yadavm.R;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class HomeAd extends RecyclerView.Adapter<HomeAd.ViewHolder>{
    public Context mContext;
    public List<HomeMo> mHomeList;
    DatabaseReference reference;
    private static int counter = 0;
    private String stringVal;


    private ProgressDialog progressDialog;



    public HomeAd(Context mContext, List<HomeMo> mHomeList) {
        this.mContext = mContext;
        this.mHomeList = mHomeList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view  = LayoutInflater.from(mContext).inflate(R.layout.card_home_item,parent,false);

        progressDialog = new ProgressDialog(mContext);
        return new  HomeAd.ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final HomeMo homemodel = mHomeList.get(position);

        holder.itemname.setText(homemodel.getItemName());
        if (homemodel.getItemPricekg().equals("0")){
           holder.itempricekg.setVisibility(View.GONE);
           holder.itempricepcs.setText("Rs."+homemodel.getItemPricepcs()+"/"+"Pcs");

        }
        else if (homemodel.getItemPricepcs().equals("0")){
holder.itempricepcs.setVisibility(View.GONE);
            holder.itempricekg.setText("Rs."+homemodel.getItemPricekg()+"/"+"Kg");

        }
        else {
            holder.itempricepcs.setText("Rs."+homemodel.getItemPricepcs()+"/"+"Pcs");
            holder.itempricekg.setText("Rs."+homemodel.getItemPricekg()+"/"+"Kg");

        }

        Glide.with(holder.itemimage.getContext())
                .load(homemodel.getItemImage())
                .into(holder.itemimage);

        holder.addButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                MainActivity activity = (MainActivity) (mContext);
                FragmentManager fm = activity.getSupportFragmentManager();
                DialogAddFragment alertDialog = new DialogAddFragment();

                Bundle args = new Bundle();
                args.putString("itemname", homemodel.getItemName());
                args.putString("itemimage", homemodel.getItemImage());
                args.putString("itempricekg",homemodel.getItemPricekg());
                args.putString("itempricepcs",homemodel.getItemPricepcs());
                args.putString("itemid",homemodel.getItemId());
                alertDialog.setArguments(args);
                alertDialog.show(fm, "fragment_alert");


//                Button  buttonCart = dialog.findViewById(R.id.add_to_cart_button);
//                buttonCart.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        progressDialog.show();
//                        CartMo cartMo = new CartMo();
//                        Random rnd = new Random();
//                        int n = 100000 + rnd.nextInt(900000);
//                        cartMo.setItemName(homemodel.getItemName());
//                        cartMo.setItemId(String.valueOf(n));
//                        cartMo.setItemImage(homemodel.getItemImage());
//                        FirebaseDatabase.getInstance().getReference().child("Carts")
//                                .child(String.valueOf(n))
//                                .setValue(cartMo)
//
//                                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        progressDialog.dismiss();
//                                        dialog.dismiss();
//                                    }
//                                });
//
//                    }
//                });
//
//                TextView textViewgmValue = dialog.findViewById(R.id.text_gm_value);
//                TextView textViewpcsValue = dialog.findViewById(R.id.text_pcs_value);
//
//
//                dialog.show();
            }
        });


    }



    @Override
    public int getItemCount() {
        return mHomeList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemname;
        public TextView itempricekg;
        public TextView itempricepcs;
        public ImageView itemimage;
        public Button addButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemname = itemView.findViewById(R.id.item_name);
            itempricekg = itemView.findViewById(R.id.item_price_kg);
            itempricepcs = itemView.findViewById(R.id.item_price_pcs);
            itemimage = itemView.findViewById(R.id.item_image);

            addButton = itemView.findViewById(R.id.add_butto);


        }
    }
}

