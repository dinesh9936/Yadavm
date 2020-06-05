package com.example.yadavm;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.yadavm.Adapters.OrderAd;
import com.example.yadavm.Models.OrderMo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Ordersfrag extends Fragment {
    FirebaseDatabase database;
    DatabaseReference reference;

    private OrderAd orderAd;
    private List<OrderMo> orderMos;


    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    public Ordersfrag() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_ordersfrag, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("");

        ((MainActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("User").child(Objects.requireNonNull(user.getPhoneNumber())).child("My Orders");






        //textViewnothing = (TextView)view.findViewById(R.id.text_nothing_in_order);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_order);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        orderMos = new ArrayList<>();

        orderAd = new OrderAd(getContext(),orderMos);
        recyclerView.setAdapter(orderAd);


        readPost();
        return view;
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_toolbar,menu);
    }
    private void readPost(){
        reference.keepSynced(true);
        reference.orderByChild("orderStatus").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    orderMos.clear();
                    for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                        OrderMo shopmodal = dataSnapshot1.getValue(OrderMo.class);
                        orderMos.add(shopmodal);
orderAd.notifyDataSetChanged();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.notification){
            Intent intent = new Intent(getActivity(),Notification.class);

            startActivity(intent );
        }
        return super.onOptionsItemSelected(item);

    }
}
