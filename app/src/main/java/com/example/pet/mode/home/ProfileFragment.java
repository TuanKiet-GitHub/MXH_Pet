package com.example.pet.mode.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.pet.R;
import com.example.pet.databinding.FragmentProfileBinding;
import com.example.pet.mode.activities.LoginActivity;
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

import java.io.IOException;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment {
    private SharedPreferences sharedPreferences;
    private DatabaseReference databaseReference;

    private FirebaseStorage storage;
    private StorageReference storageReference;
    private User user;
    FragmentProfileBinding mBinding;
    private String token;
    private String TAG = "";

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);

        token = Utils.getToken(getActivity());

        String temp = Utils.getUserInfor(getActivity());
        if(!temp.equals("1")){
            user = new Gson().fromJson(temp, User.class);
            mBinding.setUser(user);
            mBinding.tvAddress.setText("Address: " + user.getAddress());
            mBinding.tvGender.setText("Gender: " + user.getGender());
            mBinding.tvPhoneNumber.setText("Phone Number: " + user.getPhone_number());
            mBinding.tvYear.setText("Year Born: " + user.getYear_born());
            if (!user.getAvatar().equals("default") && ProfileFragment.this.getActivity() != null) {
                Glide.with(getContext()).load(Uri.parse(user.getAvatar()))
                        .into(mBinding.avatar);

            }
        }

        mBinding.logout.setOnClickListener(v -> {
            sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("token", "1");
            editor.putString("user_infor", "1");
            editor.apply();
            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(getActivity(), LoginActivity.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(intent);
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

}