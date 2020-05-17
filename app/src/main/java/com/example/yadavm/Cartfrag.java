package com.example.yadavm;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.yadavm.Adapters.CartAd;
import com.example.yadavm.Adapters.DialogItemCartAd;
import com.example.yadavm.Models.CartMo;
import com.example.yadavm.Models.DialogOrderMo;
import com.example.yadavm.Models.OrderDetail;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class Cartfrag extends Fragment {
    private Toolbar toolbar;
    FirebaseDatabase database;
    DatabaseReference reference;

    private RecyclerView recyclerView;
    private CartAd homeadapter;
    private DialogItemCartAd  dialogItemCartAd;

    private List<CartMo> mHomeList;
    private List<DialogOrderMo> dialogOrderMosList;



    private Button buttonPlace, buttoShoping;
    private TextView textViewNothinincart;
    private ImageButton button;


//////new
//    private FirebaseRecyclerAdapter<CartMo,BlogPostHolder> firebaseRecyclerAdapter;
//    ////


    public Cartfrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_cartfrag, container, false);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("");

        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);


        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("Carts");
        reference.keepSynced(true);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_cat);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        textViewNothinincart = (TextView) view.findViewById(R.id.text_nothing_in_cart);


        buttonPlace = (Button) view.findViewById(R.id.button_place);
        buttoShoping = (Button) view.findViewById(R.id.button_go_to_shop);

        buttoShoping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoHomeFrag();
            }
        });


        buttonPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_order_confirm);

                RecyclerView recyclerView = dialog.findViewById(R.id.recycler_dialog_order);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setHasFixedSize(true);
                dialogOrderMosList = new ArrayList<>();
                dialogItemCartAd = new DialogItemCartAd(getContext(),dialogOrderMosList);



                Button buttonconfirm = (Button)dialog.findViewById(R.id.button_confirm);
                buttonconfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        DatabaseReference referenceff = FirebaseDatabase.getInstance().getReference().child("Carts");
//                        referenceff.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
//                                    ItemCarttoOrMo itemCarttoOrMo = dataSnapshot1.getValue(ItemCarttoOrMo.class);
//                                    itemCarttoOrMoList.add(itemCarttoOrMo);
//                                    dialogItemCartAd.notifyDataSetChanged();
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                            }
//                        });
                        Date datetime= new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
                        String currentTime = sdf.format(datetime);
                        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
                        String date = sdf1.format(datetime);
                        Random rnd = new Random();
                        int n = 100000 + rnd.nextInt(900000);
                        OrderDetail orderDetail = new OrderDetail();
                        orderDetail.setOrderid(String.valueOf(n));
                        orderDetail.setOrdertime(String.valueOf(currentTime));
                        orderDetail.setOrderid(String.valueOf(n));
                        orderDetail.setOrderdate(date);
                        orderDetail.setOrderstatus("1");
                        DatabaseReference referencea = FirebaseDatabase.getInstance().getReference()
                                .child("My Orders")
                                .child(String.valueOf(n));

                        referencea.setValue(orderDetail);
                        FirebaseDatabase.getInstance().getReference().child("Carts")
                                .removeValue();
                        dialog.dismiss();
                    }
                });
                recyclerView.setAdapter(dialogItemCartAd);
                readPosta();
                dialog.show();
            }
        });


        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_cat);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mHomeList = new ArrayList<>();
        homeadapter = new CartAd(getContext(), mHomeList,dialogOrderMosList);
        recyclerView.setAdapter(homeadapter);
        readPost();
        return view;
    }

    private void readPosta() {
        reference.keepSynced(true);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    DialogOrderMo dialogOrderMo = dataSnapshot1.getValue(DialogOrderMo.class);
                    dialogOrderMosList.add(dialogOrderMo);
                    dialogItemCartAd.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void gotoHomeFrag() {

        Homefrag homefrag = new Homefrag();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Homefrag NAME = new Homefrag();
        fragmentTransaction.replace(R.id.container, NAME);
        fragmentTransaction.commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_toolbar, menu);
    }

    private void readPost() {
        reference.keepSynced(true);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.getChildrenCount() == 0) {
                    recyclerView.setVisibility(View.GONE);
                    textViewNothinincart.setVisibility(View.VISIBLE);
                    //buttoShoping.setVisibility(View.VISIBLE);
                    buttonPlace.setVisibility(View.GONE);
                    //buttonPlace.setEnabled(false);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    textViewNothinincart.setVisibility(View.GONE);
                    buttonPlace.setVisibility(View.VISIBLE);
                    //buttoShoping.setVisibility(View.GONE);
                    mHomeList.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        CartMo shopmodal = dataSnapshot1.getValue(CartMo.class);
                        mHomeList.add(shopmodal);
                        homeadapter.notifyDataSetChanged();

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.notification) {
            Intent intent = new Intent(getActivity(), Notification.class);

            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);

    }

}