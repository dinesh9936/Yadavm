package com.example.yadavm.Dialogs;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.bumptech.glide.Glide;
import com.example.yadavm.Models.CartMo;
import com.example.yadavm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class DialogEditCart extends DialogFragment {
    private TextView textViewItemName;
    private ImageView imageViewItemImage;
    public ImageButton imageButtonPluskg,imageButtonPlusgm,imageButtonPluspcs,imageButtonMinuskg,imageButtonMinusgm,imageButtonMinuspcs;

    public  TextView textViewkg,textViewgm,textViewpcs,textViewKgValue,textViewGmValue,textViewPcsValue,textViewtotal,textViewTotalQt;

    public  String name,image,pricekg,pricepcs,itemid, itemkg, total, itemgm, itempcs, itemkgvalue, itemgmvalue, itempcsvalue, itemtype;
    public LinearLayout linearLayoutkg,linearLayoutgm,linearLayoutpcs;
    public int pricekgint,pricepcsint;

    public int totalPrice ;

    public FirebaseDatabase database;
    public DatabaseReference reference;

    public Button buttonAddtocart;

   DialogLoading loading;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    LinearLayout linearLayout,linearLayout2;
    Button buttonUpdate, buttonOk;

    float pricegmint;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.dialog_item, container, false);
        getDialog().setCanceledOnTouchOutside(false);

        loading = new DialogLoading();

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        database =FirebaseDatabase.getInstance();
        reference = database.getReference();

        name = getArguments().getString("itemname");
        image = getArguments().getString("itemimage");
        pricekg = getArguments().getString("itempriceprkg");
        pricepcs = getArguments().getString("itempriceprpcs");
        itemid = getArguments().getString("itemid");


        itemkg = getArguments().getString("itemkg");
        itemgm = getArguments().getString("itemgm");
        itempcs = getArguments().getString("itempcs");

        itemkgvalue = getArguments().getString("itemkgvalue");
        itemgmvalue = getArguments().getString("itemgmvalue");
        itempcsvalue = getArguments().getString("itempcsvalue");

        itemtype = getArguments().getString("itemtype");

        total = getArguments().getString("total");

        pricekgint = Integer.parseInt(pricekg);


        float pricekgdouble = (float) (pricekgint/20.0);
        Log.d("Debug", "onCreateView: "+pricekgdouble);
        pricegmint = pricekgdouble;

        pricepcsint = Integer.parseInt(pricepcs);


        buttonAddtocart = view.findViewById(R.id.add_to_cart_button);
        buttonOk = view.findViewById(R.id.button_ok);
        buttonOk.setVisibility(View.VISIBLE);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });



        linearLayout = view.findViewById(R.id.double_button_layout);
        linearLayout2 = view.findViewById(R.id.double_button_layout2);
        linearLayout2.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.GONE);
        buttonUpdate = view.findViewById(R.id.button_update_cart_item);
        buttonUpdate.setVisibility(View.VISIBLE);

        linearLayoutkg =view.findViewById(R.id.layout_kg);
        linearLayoutgm =view.findViewById(R.id.layout_gm);
        linearLayoutpcs =view.findViewById(R.id.layout_pcs);

        textViewtotal = view.findViewById(R.id.total);

        textViewtotal.setText(total);



        textViewkg = view.findViewById(R.id.text_kg);

        textViewgm = view.findViewById(R.id.text_gm);

        textViewpcs = view.findViewById(R.id.text_pcs);



        textViewKgValue = view.findViewById(R.id.text_kg_value);


        textViewGmValue = view.findViewById(R.id.text_gm_value);

        textViewPcsValue = view.findViewById(R.id.text_pcs_value);
        textViewTotalQt = view.findViewById(R.id.total_qt);


        textViewTotalQt.setText("Total Quantity = " + itemkg + "Kg+" +itemgm+ "Gms+" + itempcs + "Pcs");


        imageButtonPluskg = view.findViewById(R.id.plus_button_kg);
        imageButtonPlusgm = view.findViewById(R.id.plus_button_gm);
        imageButtonPluspcs = view.findViewById(R.id.plus_button_pcs);

        imageButtonMinuskg = view.findViewById(R.id.minus_button_kg);
        imageButtonMinusgm = view.findViewById(R.id.minus_button_gm);
        imageButtonMinuspcs = view.findViewById(R.id.minus_button_pcs);







        if (pricepcs.equals("0")){
            linearLayoutpcs.setVisibility(View.GONE);

        }
        else if (pricekg.equals("0")){
            linearLayoutgm.setVisibility(View.GONE);
            linearLayoutkg.setVisibility(View.GONE);
        }




        imageButtonPluskg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                plusButton(textViewkg,textViewKgValue,pricekgint);

                totalPriceFun();


            }
        });



        imageButtonPlusgm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plusButton(textViewgm,textViewGmValue,pricegmint);
                totalPriceFun();
                if (textViewgm.getText().toString().trim().equals("19")){

                    imageButtonPlusgm.setEnabled(false);
                }

            }
        });


        imageButtonPluspcs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plusButton(textViewpcs,textViewPcsValue,pricepcsint);
                totalPriceFun();

            }
        });
        imageButtonMinuskg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minusButton(textViewkg,textViewKgValue,pricekgint);
                totalPriceFun();


            }
        });
        imageButtonMinusgm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minusButton(textViewgm,textViewGmValue,pricegmint);
                totalPriceFun();


            }
        });
        imageButtonMinuspcs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minusButton(textViewpcs,textViewPcsValue,pricepcsint);

                totalPriceFun();
            }

        });
        textViewItemName = view.findViewById(R.id.item_name_d);
        imageViewItemImage = view.findViewById(R.id.item_image_d);
        textViewItemName.setText(name);


        Glide.with(getActivity()).load(image).into(imageViewItemImage);


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonUpdate.setEnabled(false);
                String kg = textViewkg.getText().toString().trim();
                String gm = textViewgm.getText().toString().trim();
                String pcs = textViewpcs.getText().toString().trim();
                if (kg.equals("0")&&gm.equals("0") && pcs.equals("0")){
                    Toast.makeText(getContext(), "Add Quantity", Toast.LENGTH_SHORT).show();
                    buttonUpdate.setEnabled(true);
                }

//


                else {

                    CartMo cartMo = new CartMo();
                    cartMo.setItemName(name);
                    if (Integer.parseInt(textViewtotal.getText().toString().trim()) <= 100) {
                        cartMo.setDeliveryCharge("0");

                    }
                    else {
                        cartMo.setDeliveryCharge("20");
                    }
                    cartMo.setItemType(itemtype);
                    cartMo.setItemImage(image);
                    cartMo.setItemId(itemid);
                    cartMo.setItemPriceprkg(pricekg);
                    cartMo.setItemPriceprpcs(pricepcs);
                    cartMo.setItemPricekg(textViewKgValue.getText().toString().trim());
                    cartMo.setItemPricegm(textViewGmValue.getText().toString().trim());
                    cartMo.setItemPricepcs(textViewPcsValue.getText().toString().trim());
                    cartMo.setItemPricetotal(textViewtotal.getText().toString().trim());
                    cartMo.setItemQuantitykg(textViewkg.getText().toString().trim());
                    cartMo.setItemQuantitygm(textViewgm.getText().toString().trim());
                    cartMo.setItemQuantitypcs(textViewpcs.getText().toString().trim());

                    loading.show(getChildFragmentManager(),"Loading");

                    reference.child("User").child(user.getPhoneNumber()).child("Carts").child(itemid).setValue(cartMo);


                    loading.dismiss();


                    Toast.makeText(getContext(), "Item Updated to cart successfully", Toast.LENGTH_SHORT).show();

                    dismiss();
                    buttonUpdate.setEnabled(true);
                }
            }
        });
        return view;
    }

    private void totalPriceFun() {

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

    private void minusButton(TextView textViewa,TextView textViewb,float price) {
        String stringVal,str,stringRes;

        str  = textViewa.getText().toString().trim();
        if (str.equals("0")){
            textViewa.setText("0");
            textViewb.setText("0");
            textViewtotal.setText("0");
            textViewTotalQt.setText("0");


        }
        else {
            int prev = Integer.parseInt(str);
            prev--;
            int res = (int) (prev*price);
            stringRes = String.valueOf(res);
            stringVal = String.valueOf(prev);
            textViewa.setText(stringVal);
            textViewb.setText(stringRes);

            int gm = Integer.parseInt(textViewgm.getText().toString().trim());
            int totalgm  = gm*50;
            String totalGms = String.valueOf(totalgm);

            textViewTotalQt.setText("Total Quantity = "+textViewkg.getText().toString()+"Kg+"+totalGms+"Gms+"+textViewpcs.getText().toString()+"Pcs");



        }
    }

    private void plusButton(TextView textViewa,TextView textViewb,float price) {
        String stringVal,str,stringRes;
        str = textViewa.getText().toString().trim();
        int prev = Integer.parseInt(str);
        prev++;

        int res = (int) (prev*price);
        stringRes = String.valueOf(res);
        stringVal = Integer.toString(prev);
        textViewa.setText(stringVal);
        textViewb.setText(stringRes);
        int gm = Integer.parseInt(textViewgm.getText().toString().trim());
        int totalgm  = gm*50;
        String totalGms = String.valueOf(totalgm);

        textViewTotalQt.setText("Total Quantity = "+textViewkg.getText().toString()+"Kg+"+totalGms+"Gms+"+textViewpcs.getText().toString()+"Pcs");


    }

    @Override
    public void onStart() {
        super.onStart();


        textViewkg.setText(itemkg);
        textViewgm.setText(itemgm);
        textViewpcs.setText(itempcs);
        textViewKgValue.setText(itemkgvalue);
        textViewGmValue.setText(itemgmvalue);
        textViewPcsValue.setText(itempcsvalue);

    }
}
