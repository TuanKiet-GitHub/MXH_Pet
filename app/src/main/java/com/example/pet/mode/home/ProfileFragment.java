package com.example.pet.mode.home;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.pet.R;
import com.example.pet.databinding.FragmentProfileBinding;
import com.example.pet.mode.activities.LoginActivity;
import com.example.pet.mode.activities.UpdatePassActivity;
import com.example.pet.mode.activities.Update_Info_Activity;
import com.example.pet.mode.models.Message;
import com.example.pet.mode.models.User;
import com.example.pet.mode.utils.Utils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment {
    private SharedPreferences sharedPreferences;
    private DatabaseReference databaseReference;
    LinearLayout linerPassWord , linerInformation;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private User user;
    FragmentProfileBinding mBinding;
    private String token;
    private String TAG = "";
    private int check ;
    ValueEventListener seenListener ;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);

        token = Utils.getToken(getActivity());

        getUser(token);
        seenMessage(FirebaseAuth.getInstance().getCurrentUser().getUid());

        mBinding.logout.setOnClickListener(v -> {
            sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("token", "1");
            editor.apply();
            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(getActivity(), LoginActivity.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(intent);
        });
        mBinding.seeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.setting_dialog);
                LinearLayout linearPassword = dialog.findViewById(R.id.linerPassword);
                LinearLayout linearInformation = dialog.findViewById(R.id.linerInformation);
                ImageView imgPass = dialog.findViewById(R.id.imgPassword);
                ImageView imgInformation = dialog.findViewById(R.id.imgInformation);
                LinearLayout linerCannel = dialog.findViewById(R.id.linerBtnCannel);
                LinearLayout linerDone = dialog.findViewById(R.id.linerBtnDone);
                linearPassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                //        Log.e("C", "Click Password");
                        imgPass.setVisibility(View.VISIBLE);
                        check  = 0 ;
                        imgInformation.setVisibility(View.INVISIBLE);
                    }
                });
                linearInformation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      //  Log.e("C", "Click Information");
                        imgPass.setVisibility(View.INVISIBLE);
                        imgInformation.setVisibility(View.VISIBLE);
                        check = 1 ;
                    }
                });
                linerCannel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();

                    }
                });
                linerDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(check == 0 )
                        {

                            Intent intent = new Intent(getActivity(), UpdatePassActivity.class);
                            dialog.cancel();
                            startActivity(intent);

                        }
                        else
                        {
                            Intent intent = new Intent(getActivity(), Update_Info_Activity.class);
                            dialog.cancel();
                            startActivity(intent);
                        }
                    }
                });
                dialog.show();

            }
        });
        mBinding.chooseAvatar.setOnClickListener(v -> {
            Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, 1);

        });


        return mBinding.getRoot();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri chosenImageUri = data.getData();

            Bitmap mBitmap = null;
            try {
                mBitmap = MediaStore.Images.Media.getBitmap(this.getContext().getContentResolver(), chosenImageUri);
                mBinding.avatar.setImageBitmap(mBitmap);
                uploadImage(chosenImageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage(Uri uri) {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("ListAvatars");

        storageReference.child(token).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        FirebaseDatabase.getInstance().getReference("Users").child(token);
                        databaseReference.child("avatar").setValue(uri.toString());
                    }
                });
            }
        });
    }

    private void getUser(String token) {

        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(token);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                mBinding.setUser(user);
                mBinding.tvAddress.setText("Address: " + user.getAddress());
                mBinding.tvGender.setText("Gender: " + user.getGender());
                mBinding.tvPhoneNumber.setText("Phone Number: " + user.getPhone_number());
                mBinding.tvYear.setText("Day of Birth: " + user.getYear_born());
                if (!user.getAvatar().equals("default") && ProfileFragment.this.getActivity() != null) {
                    Glide.with(getContext()).load(Uri.parse(user.getAvatar()))
                            .into(mBinding.avatar);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }
    private void seenMessage (final String iDReceiver)
    {
        // Log.e("seen", "VAO");
        databaseReference = FirebaseDatabase.getInstance().getReference("Chats");
        seenListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Message message = dataSnapshot.getValue(Message.class);
                    // token là người gửi.
                     Log.e("Log", "ProfileFragment" );
                    if(message.getReceiver().equals(iDReceiver))
                    {
                        HashMap<String , Object> hashMap = new HashMap<>();
//                          Log.e("seen", "VAO Seen");
                        hashMap.put("status", "Received");
                        dataSnapshot.getRef().updateChildren(hashMap);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        databaseReference.removeEventListener(seenListener);
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}