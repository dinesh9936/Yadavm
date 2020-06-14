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
import com.example.yadavm.Dialogs.DialogAddFragment;
import com.example.yadavm.Activity.MainActivity;
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
    public HomeAd.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_home_item,parent,false);


        return new  ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull final HomeAd.ViewHolder holder, int position) {

        final HomeMo homemodel = mHomeList.get(position);

 


        holder.itemname.setText(homemodel.getItemName());
        if (homemodel.getItemType().equals("pcs")){
           holder.itempricekg.setVisibility(View.GONE);
           holder.itempricepcs.setText("Rs."+homemodel.getItemPricepcs()+"/"+"Pcs");

        }
        else if (homemodel.getItemType().equals("kg")){
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
                args.putString("itemtype",homemodel.getItemType());
                alertDialog.setArguments(args);
                alertDialog.show(fm, "fragment_alert");



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

            addButton = itemView.findViewById(R.id.add_button);


        }
    }
}

