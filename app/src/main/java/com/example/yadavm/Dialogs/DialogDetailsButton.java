package com.example.yadavm.Dialogs;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yadavm.Adapters.CartPlaceButtonAd;
import com.example.yadavm.Models.CartMo;
import com.example.yadavm.Models.HomeMo;
import com.example.yadavm.Models.OrderAddMo;
import com.example.yadavm.Models.OrderItemsMo;
import com.example.yadavm.Models.OrderMo;
import com.example.yadavm.Models.PriceMo;
import com.example.yadavm.Models.UserMo;
import com.example.yadavm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DialogDetailsButton extends DialogFragment {
    private TextView orderId,orderAddress,textTotal,deliveryChargeText,textViewGlobaltotal;
    DatabaseReference reference;

    Button buttonConfirm;

    RecyclerView recyclerView;

    private CartPlaceButtonAd cartPlaceButtonAd;
    private ArrayList<CartMo> cartMoList;

    String orderid;
    int globaltotal;

    String allItems;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    ProgressDialog dialog;


    String orderddress;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_cart_place_button,container,false);




        getDialog().setCanceledOnTouchOutside(false);
        orderid = getArguments().getString("orderid");
        dialog = new ProgressDialog(getActivity());

        reference = FirebaseDatabase.getInstance().getReference();

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        orderId = view.findViewById(R.id.order_id);



        orderId.setText(String.valueOf("ORDER ID#"+orderid));


        orderAddress = view.findViewById(R.id.text_address);

        textTotal = view.findViewById(R.id.text_tota);

        deliveryChargeText = view.findViewById(R.id.text_delivery);

        textViewGlobaltotal = view.findViewById(R.id.global_total_text);



        recyclerView = view.findViewById(R.id.recycler_dialog_order);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        readPost();
        buttonConfirm = view.findViewById(R.id.button_confirm_plce);
 buttonConfirm.setVisibility(View.GONE);


        reference.child("User").child(user.getPhoneNumber()).child("Carts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String items="";
                for (DataSnapshot ds : dataSnapshot.getChildren()){

                    OrderItemsMo orderItemsMo = ds.getValue(OrderItemsMo.class);
                    items = items+"\n"+orderItemsMo.getItemName();

                    allItems = items;

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        reference.child("User").child(user.getPhoneNumber()).child("Profile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserMo userMo = dataSnapshot.getValue(UserMo.class);
                orderddress = userMo.getAddress();
                orderAddress.setText(orderddress);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        reference.child("User").child(user.getPhoneNumber()).child("My Orders").child(orderid).child("ItemsDetails").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int sum = 0;
                for (DataSnapshot ds :dataSnapshot.getChildren()){
                    PriceMo priceMo = ds.getValue(PriceMo.class);
                    String ff = priceMo.getItemPricetotal();
                    String deliverycharge = priceMo.getDeliveryCharge();
                    int deliveryChar = Integer.parseInt(deliverycharge);
                    int pValue = Integer.parseInt(String.valueOf(ff));
                    sum +=pValue;
                    globaltotal = sum;
                    if (sum <=100){
                        deliveryChargeText.setText("₹ 20");
                        globaltotal = globaltotal+20;
                    }
                    else {
                        deliveryChargeText.setText("₹ 0");
                    }
                    String globalStr = String.valueOf(globaltotal);
                    String d = String.valueOf(sum);
                    textTotal.setText("₹ "+d);
                    textViewGlobaltotal.setText("Total ₹ "+globalStr);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.toString(), Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }




    private void readPost(){
        reference.keepSynced(true);
        reference.child("User").child(user.getPhoneNumber()).child("My Orders").child(orderid).child("ItemsDetails").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (isAdded()){
                    if (dataSnapshot.exists()){
                        cartMoList = new ArrayList<>();
                        for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){

                            cartMoList.add(dataSnapshot1.getValue(CartMo.class));



                        }
                        cartPlaceButtonAd = new CartPlaceButtonAd(cartMoList);
                        recyclerView.setAdapter(cartPlaceButtonAd);

                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.toString(), Toast.LENGTH_SHORT).show();

            }

        });




    }

    private void moveItemDetails(final DatabaseReference fromPath, final DatabaseReference toPath) {
        fromPath.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                toPath.setValue(dataSnapshot.getValue(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError firebaseError, DatabaseReference firebase) {
                        if (firebaseError != null) {

                        } else {


                        }
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
