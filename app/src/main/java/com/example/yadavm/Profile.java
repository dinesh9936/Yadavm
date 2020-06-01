package com.example.yadavm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yadavm.Models.SuggestMo;
import com.example.yadavm.Models.UserMo;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {
private Toolbar toolbar;

private FirebaseAuth firebaseAuth;
DatabaseReference reference;

    private StorageReference mStorageReference;


TextView textViewName,textViewPhone,textViewAddress,textViewAbout;
Button buttonLogOut;

String name,phone;
ImageButton imageButtonAdd;

ProgressDialog progressDialog;

    CircleImageView imageViewUploadItem;

    private Uri mItemImageUri;

    ImageButton imageButtonPhone,imageButtonAddress,imageButtonSuggestion;

    EditText editTextSuggestion;

    FirebaseUser user;
LinearLayout linearLayout;
    public Profile() {
        // Required empty public constructor
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_toolbar,menu);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_profile,container,false);


        firebaseAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();
        mStorageReference = FirebaseStorage.getInstance().getReference();


        progressDialog = new ProgressDialog(getActivity());

        textViewName = (TextView)view.findViewById(R.id.name_profile);
        textViewPhone = (TextView)view.findViewById(R.id.phone_profile);


        editTextSuggestion = view.findViewById(R.id.edit_suggestion);

        linearLayout = view.findViewById(R.id.support);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:9452251055"));
                startActivity(intent);
            }
        });
        textViewAbout = (TextView)view.findViewById(R.id.about);
        textViewAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),About.class);
                startActivity(intent);
            }
        });


        textViewAddress = (TextView)view.findViewById(R.id.address_profile);
        imageButtonAdd = (ImageButton)view.findViewById(R.id.add_image_button);

        imageViewUploadItem = (CircleImageView)view.findViewById(R.id.image_view_profile);

//        imageButtonPhone = view.findViewById(R.id.edit_phone);
//        imageButtonPhone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MainActivity activity = (MainActivity) (getContext());
//                FragmentManager fm = activity.getSupportFragmentManager();
//                DialogUpdate alertDialog = new DialogUpdate();
//
//                Bundle args = new Bundle();
//
//                alertDialog.show(fm, "fragment_alert");
//            }
//        });
        imageButtonAddress = view.findViewById(R.id.edit_address);
        imageButtonAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) (getContext());
                FragmentManager fm = activity.getSupportFragmentManager();
                DialogUpdate alertDialog = new DialogUpdate();

                Bundle args = new Bundle();

                alertDialog.show(fm, "fragment_alert");
            }
        });
        imageButtonSuggestion = view.findViewById(R.id.send_suggestion);
        imageButtonSuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String suggestion = editTextSuggestion.getText().toString().trim();
                SuggestMo suggestMo = new SuggestMo(suggestion,user.getPhoneNumber(),name);

                reference.child("Suggestions").push().setValue(suggestMo);
                editTextSuggestion.setText(null);
                Toast.makeText(getActivity(), "Suggestion Sent", Toast.LENGTH_SHORT).show();
            }
        });

        imageButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setAspectRatio(1,1)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(getContext(), Profile.this);


            }


        });

        buttonLogOut = (Button)view.findViewById(R.id.logout);
        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent intent = new Intent(getActivity(),Login.class);
                startActivity(intent);
                getActivity().finish();
            }
        });


        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("");

        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.notification){
            Intent intent = new Intent(getActivity(),Notification.class);

            startActivity(intent );
            getActivity().finish();
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onStart() {
        super.onStart();
         user = firebaseAuth.getCurrentUser();

        if (user != null){
           reference.child("User").child(user.getPhoneNumber()).addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   if (dataSnapshot.exists()){
                       UserMo userMo = dataSnapshot.getValue(UserMo.class);
                       name = userMo.getName();
                       phone = userMo.getPhone();
                       textViewName.setText(name);
                       textViewPhone.setText(phone);
                       textViewAddress.setText(userMo.getAddress());

                       String image = userMo.getProfilepic();
                       if (image != null){
                           Glide.with(getContext())
                                   .load(image)
                                   .into(imageViewUploadItem);
                       }
                   }

               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
           });

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mItemImageUri = result.getUri();
                //Toast.makeText(getContext(), mItemImageUri.toString(), Toast.LENGTH_SHORT).show();
                imageViewUploadItem.setImageURI(mItemImageUri);
                mStorageReference.child("User").child(user.getPhoneNumber()).putFile(mItemImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
//                        String uri = String.valueOf(task.getResult().getMetadata().getReference());
//                        reference.child("User").child(user.getPhoneNumber()).child("profilepic").setValue(uri);

                        mStorageReference.child("User").child(user.getPhoneNumber()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                reference.child("User").child(user.getPhoneNumber()).child("profilepic").setValue(uri.toString());
                                Glide.with(getContext())
                                        .load(uri.toString())
                                        .into(imageViewUploadItem);

                               // Toast.makeText(getActivity(), uri.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        progressDialog.setMessage((int) progress + "");
                    }
                })
                        .addOnCanceledListener(new OnCanceledListener() {
                            @Override
                            public void onCanceled() {
                                Toast.makeText(getContext(), "Not Success", Toast.LENGTH_SHORT).show();
                            }
                        });

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }
}
