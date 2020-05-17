package com.example.yadavm.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yadavm.Models.NotiMo;
import com.example.yadavm.R;

import java.util.List;

public class NotiAd extends RecyclerView.Adapter<NotiAd.ViewHolder> {

public Context mContext;
public List<NotiMo>notiMoList;

    public NotiAd(Context mContext, List<NotiMo> notiMoList) {
        this.mContext = mContext;
        this.notiMoList = notiMoList;
    }

    @NonNull
    @Override
    public NotiAd.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_notification,parent,false);
        return new NotiAd.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotiAd.ViewHolder holder, int position) {

        NotiMo notiMo = notiMoList.get(position);

        holder.title.setText(notiMo.getNotititle());
        holder.time.setText("."+notiMo.getTime());
        holder.message.setText(notiMo.getNotimessage());

        if (notiMo.getNotititle().equals("Delivery")){
            holder.titleimage.setImageResource(R.drawable.ic_directions_bike);
        }
        else if (notiMo.getNotititle().equals("Offer")){
            holder.titleimage.setImageResource(R.drawable.ic_star);
        }
        else {
            holder.titleimage.setImageResource(R.drawable.ic_message);
        }
    }

    @Override
    public int getItemCount() {
        return notiMoList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title,message,time;
        public ImageView titleimage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

           title = itemView.findViewById(R.id.title_text_noti);
           message = itemView.findViewById(R.id.message_text_noti);
           time = itemView.findViewById(R.id.time_text_noti);

           titleimage = itemView.findViewById(R.id.title_image_noti);




        }
    }
}
