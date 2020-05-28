package com.example.yadavm.Adapters;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.yadavm.Models.CartMo;
import com.example.yadavm.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.List;

public class CartAd extends RecyclerView.Adapter<CartAd.VieHolder>{

    public Context mContext;
    public List<CartMo> mHomeList;

    String priceprgm;

    DatabaseReference reference;
    public int totalPrice ;

    public CartAd(Context mContext, List<CartMo> mHomeList) {
        this.mContext = mContext;
        this.mHomeList = mHomeList;

    }

    @NonNull
    @Override
    public CartAd.VieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view  = LayoutInflater.from(mContext).inflate(R.layout.card_cart_itme,parent,false);

        reference = FirebaseDatabase.getInstance().getReference().child("Carts");

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
        if (homemodel.getItemQuantitykg().equals("0")){

            holder.linearLayoutkg.setVisibility(View.GONE);
            holder.linearLayoutgm.setVisibility(View.GONE);
            holder.textViewPcs.setText(homemodel.getItemQuantitypcs());
            holder.textViewPcsValue.setText(homemodel.getItemPricepcs());
            holder.textViewTotal.setText("Rs."+homemodel.getItemPricetotal());


        }
        else if (homemodel.getItemQuantitypcs().equals("0")){
            holder.linearLayoutpcs.setVisibility(View.GONE);
            holder.textViewKg.setText(homemodel.getItemQuantitykg());
            holder.textViewGm.setText(homemodel.getItemQuantitygm());
            holder.textViewTotal.setText("Rs."+homemodel.getItemPricetotal());


            holder.textViewKgvalue.setText(homemodel.getItemPricekg());
            holder.textViewGmvalue.setText(homemodel.getItemPricegm());
        }
        else {
            holder.textViewKg.setText(homemodel.getItemQuantitykg());
            holder.textViewGm.setText(homemodel.getItemQuantitygm());
            holder.textViewPcs.setText(homemodel.getItemQuantitypcs());
            holder.textViewPcsValue.setText(homemodel.getItemPricepcs());
            holder.textViewTotal.setText("Rs."+homemodel.getItemPricetotal());

            holder.textViewKgvalue.setText(homemodel.getItemPricekg());
            holder.textViewGmvalue.setText(homemodel.getItemPricegm());
        }

        holder.pluskg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               plusButton(holder.textViewKg,holder.textViewKgvalue,Integer.parseInt(homemodel.getItemPriceprkg()));

               totalPriceFun(homemodel.getItemPriceprkg(),homemodel.getItemPriceprpcs(),holder.textViewPcsValue,holder.textViewKgvalue,holder.textViewGmvalue,holder.textViewTotal);
            }
        });
holder.plusgm.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        plusButton(holder.textViewGm,holder.textViewGmvalue,Integer.parseInt(homemodel.getItemPriceprkg())/10 );
        totalPriceFun(homemodel.getItemPriceprkg(),homemodel.getItemPriceprpcs(),holder.textViewPcsValue,holder.textViewKgvalue,holder.textViewGmvalue,holder.textViewTotal);

    }
});
holder.pluspcs.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        plusButton(holder.textViewPcs,holder.textViewPcsValue,Integer.parseInt(homemodel.getItemPriceprpcs()));
        totalPriceFun(homemodel.getItemPriceprkg(),homemodel.getItemPriceprpcs(),holder.textViewPcsValue,holder.textViewKgvalue,holder.textViewGmvalue,holder.textViewTotal);

    }
});
holder.minuskg.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        minusButton(holder.textViewKg,holder.textViewKgvalue,Integer.parseInt(homemodel.getItemPriceprkg()),holder.textViewTotal);
        totalPriceFun(homemodel.getItemPriceprkg(),homemodel.getItemPriceprpcs(),holder.textViewPcsValue,holder.textViewKgvalue,holder.textViewGmvalue,holder.textViewTotal);

    }
});
holder.minusgm.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        minusButton(holder.textViewGm,holder.textViewGmvalue,Integer.parseInt(homemodel.getItemPriceprkg())/10,holder.textViewTotal);
        totalPriceFun(homemodel.getItemPriceprkg(),homemodel.getItemPriceprpcs(),holder.textViewPcsValue,holder.textViewKgvalue,holder.textViewGmvalue,holder.textViewTotal);

    }
});
holder.minuspcs.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        minusButton(holder.textViewPcs,holder.textViewPcsValue,Integer.parseInt(homemodel.getItemPriceprpcs()),holder.textViewTotal);
        totalPriceFun(homemodel.getItemPriceprkg(),homemodel.getItemPriceprpcs(),holder.textViewPcsValue,holder.textViewKgvalue,holder.textViewGmvalue,holder.textViewTotal);

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
        public TextView itemname,textViewKgvalue,textViewGmvalue,textViewPcsValue,textViewKg,textViewGm,textViewPcs,textViewTotal;
        public ImageView itemimage;
        public ImageButton deleteButton,pluskg,minuskg,plusgm,minusgm,pluspcs,minuspcs;

        public LinearLayout linearLayoutkg,linearLayoutgm,linearLayoutpcs;

        public TextView pricekg;
        public VieHolder(@NonNull View itemView) {
            super(itemView);

            itemname = itemView.findViewById(R.id.cart_card_item_name);

            itemimage = itemView.findViewById(R.id.cart_card_item_image);

            textViewKg = itemView.findViewById(R.id.text_kg);
            textViewGm = itemView.findViewById(R.id.text_gm);
            textViewPcs = itemView.findViewById(R.id.text_pcs);

            textViewKgvalue = itemView.findViewById(R.id.text_value_kg);
            textViewGmvalue = itemView.findViewById(R.id.text_value_gm);
            textViewPcsValue = itemView.findViewById(R.id.text_value_pcs);

            textViewTotal = itemView.findViewById(R.id.text_value_total);

            linearLayoutkg = itemView.findViewById(R.id.layout_kg);
            linearLayoutgm = itemView.findViewById(R.id.layout_gm);
            linearLayoutpcs = itemView.findViewById(R.id.layout_pcs);

            deleteButton = itemView.findViewById(R.id.item_delete);

            pluskg = itemView.findViewById(R.id.plus_kg_button);
            plusgm = itemView.findViewById(R.id.plus_gm_button);
            pluspcs = itemView.findViewById(R.id.plus_pcs_button);

            minuskg = itemView.findViewById(R.id.minus_kg_button);
            minusgm = itemView.findViewById(R.id.minus_gm_button);
            minuspcs = itemView.findViewById(R.id.minus_pcs_button);


        }
    }
    private void totalPriceFun(String pricekg,String pricepcs,TextView textViewPcsValue,TextView textViewKgValue,TextView textViewGmValue,TextView textViewtotal) {

        if (pricekg.equals("0")){
            String totalPricestr = textViewPcsValue.getText().toString().trim();
            totalPrice = Integer.parseInt(totalPricestr);
            textViewtotal.setText(String.valueOf(totalPrice));
        }
        else if(pricepcs.equals("0")){
            String totalPricestra = textViewKgValue.getText().toString().trim();
            String totalPricestrb = textViewGmValue.getText().toString().trim();

            totalPrice = Integer.parseInt(totalPricestra)+Integer.parseInt(totalPricestrb);
            textViewtotal.setText(String.valueOf(totalPrice));

        }
        else {
            String totalPricestra = textViewKgValue.getText().toString().trim();
            String totalPricestrb = textViewGmValue.getText().toString().trim();
            String totalPricestrc = textViewPcsValue.getText().toString().trim();

            totalPrice = Integer.parseInt(totalPricestra)+Integer.parseInt(totalPricestrb)+Integer.parseInt(totalPricestrc);
            textViewtotal.setText(String.valueOf(totalPrice));
        }
    }
    private void plusButton(TextView textViewa,TextView textViewb,int price) {
        String stringVal,str,stringRes;
        str = textViewa.getText().toString().trim();
        int prev = Integer.parseInt(str);
        prev++;

        int res = prev*price;
        stringRes = String.valueOf(res);
        stringVal = Integer.toString(prev);
        textViewa.setText(stringVal);
        textViewb.setText(stringRes);




    }

    private void minusButton(TextView textViewa,TextView textViewb,int price,TextView textViewt) {
        String stringVal,str,stringRes;

        str  = textViewa.getText().toString().trim();
        if (str.equals("0")){
            textViewa.setText("0");
            textViewb.setText("0");
            textViewt.setText("0");



        }
        else {
            int prev = Integer.parseInt(str);
            prev--;
            int res = prev*price;
            stringRes = String.valueOf(res);
            stringVal = String.valueOf(prev);
            textViewa.setText(stringVal);
            textViewb.setText(stringRes);




        }
    }
}

