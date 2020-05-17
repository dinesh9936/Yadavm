package com.example.yadavm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.yadavm.Adapters.HomeAd;
import com.example.yadavm.Models.HomeMo;
import com.example.yadavm.Models.OrderMo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Homefrag extends Fragment {
    private Toolbar toolbar;
    FirebaseDatabase database;
    DatabaseReference reference;

    private RecyclerView recyclerView;
    private HomeAd homeadapter;
    private List<HomeMo> mHomeList;

    //searching_item
    private LinearLayout searchLayout;

    ///private ProgressDialog pdialog;

    public Homefrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_homefrag, container, false);

        //pdialog = new ProgressDialog(getContext());
        //pdialog.show();

        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("");

        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("Sweets");
        reference.keepSynced(true);


        //searching_item
        searchLayout = (LinearLayout)view.findViewById(R.id.searchview_button);
        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),search.class);
                startActivity(intent);
            }
        });
        recyclerView  = (RecyclerView)view.findViewById(R.id.recycler_home);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mHomeList = new ArrayList<>();
        homeadapter = new HomeAd(getContext(),mHomeList);
        recyclerView.setAdapter(homeadapter);
        readPost();
        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_toolbar,menu);
    }

    private void readPost(){
        reference.keepSynced(true);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               //pdialog.show();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    HomeMo shopmodal = dataSnapshot1.getValue(HomeMo.class);
                    mHomeList.add(shopmodal);
                    homeadapter.notifyDataSetChanged();
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

    }    }

