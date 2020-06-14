package com.example.yadavm.Fragments;

import android.app.ProgressDialog;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.yadavm.Activity.MainActivity;
import com.example.yadavm.Activity.Search;
import com.example.yadavm.Adapters.HomeAd;
import com.example.yadavm.Dialogs.DialogLoading;
import com.example.yadavm.Models.HomeMo;
import com.example.yadavm.R;
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


    private LinearLayout searchLayout;

    DialogLoading loading;



    public Homefrag() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homefrag, container, false);


        loading = new  DialogLoading();

        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("");

        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("Sweets");


        //searching_item
        searchLayout = (LinearLayout)view.findViewById(R.id.searchview_button);
        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Search.class);
                startActivity(intent);
            }
        });
        recyclerView  = (RecyclerView)view.findViewById(R.id.recycler_home);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        mHomeList = new ArrayList<>();



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
        loading.show(getChildFragmentManager(),"Loading");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if (isAdded()){
                   if (dataSnapshot.exists()){
                       loading.dismiss();
                       mHomeList = new ArrayList<>();
                       for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                           mHomeList.add(dataSnapshot1.getValue(HomeMo.class));
                       }
                       homeadapter = new HomeAd(getContext(),mHomeList);
                       recyclerView.setAdapter(homeadapter);

                   }
                   else {
                       loading.dismiss();
                   }
               }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.toString(), Toast.LENGTH_SHORT).show();

                loading.dismiss();
            }

        });
    }




}

