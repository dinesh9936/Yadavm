package com.example.yadavm.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yadavm.Models.DialogOrderMo;
import com.example.yadavm.R;

import java.util.List;

public class DialogItemCartAd extends RecyclerView.Adapter<DialogItemCartAd.ViewHolder> {
    public Context mContext;
    public List<DialogOrderMo> mOrderList;

    public DialogItemCartAd(Context mContext, List<DialogOrderMo> mOrderList) {
        this.mContext = mContext;
        this.mOrderList = mOrderList;
    }

    @NonNull
    @Override
    public DialogItemCartAd.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_list_item,parent,false);

        return new DialogItemCartAd.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DialogItemCartAd.ViewHolder holder, int position) {

        final DialogOrderMo dialogOrderMo = mOrderList.get(position);
        holder.itemnm.setText(dialogOrderMo.getItemName()+"->");
        holder.itemqnty.setText(dialogOrderMo.getItemQuantity()+"->");
        holder.itemprc.setText(dialogOrderMo.getItemPrice());
    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemnm,itemqnty,itemprc;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            itemnm = itemView.findViewById(R.id.item_nm);
            itemqnty = itemView.findViewById(R.id.item_qt);
            itemprc = itemView.findViewById(R.id.item_prc);





        }
    }
}
