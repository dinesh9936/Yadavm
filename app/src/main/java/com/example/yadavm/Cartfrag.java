package com.example.yadavm;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.yadavm.Adapters.CartAd;
import com.example.yadavm.Adapters.HomeAd;
import com.example.yadavm.Models.CartMo;
import com.example.yadavm.Models.HomeMo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Cartfrag extends Fragment {
    private Toolbar toolbar;
    FirebaseDatabase database;
    DatabaseReference reference;

    private RecyclerView recyclerView;
    private CartAd cartAd;
    private List<CartMo> mCartMoList;

    private Button buttonPlace;
    private TextView textViewNothing;

    private Context mContext;

    public Cartfrag() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cartfrag,container,false);

        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("");

        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("Carts");

        recyclerView  = (RecyclerView)view.findViewById(R.id.recycler_cat);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mCartMoList = new ArrayList<>();
        cartAd = new CartAd(getContext(),mCartMoList);
        recyclerView.setAdapter(cartAd);

        buttonPlace = (Button)view.findViewById(R.id.button_place);
        textViewNothing = view.findViewById(R.id.text_nothing_in_cart);

        buttonPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FragmentManager fm = getFragmentManager();
                DialogPlaceButton dialogPlaceButton = new DialogPlaceButton();

                dialogPlaceButton.show(fm,"Hello");
            }
        });
        readPost();
        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_toolbar,menu);
    }
    private void readPost(){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount()==0){
                    textViewNothing.setVisibility(View.VISIBLE);
                }
                else {
                    buttonPlace.setVisibility(View.VISIBLE);
                    for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                        CartMo shopmodal = dataSnapshot1.getValue(CartMo.class);
                        mCartMoList.add(shopmodal);
                        cartAd.notifyDataSetChanged();
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

        if (id == R.id.notification){
            Intent intent = new Intent(getActivity(),Notification.class);

            startActivity(intent );
        }
        return super.onOptionsItemSelected(item);

    }


}
