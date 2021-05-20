package com.example.pet.mode.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.pet.R;
import com.example.pet.databinding.FragmentProfileBinding;
import com.example.pet.databinding.FragmentProfileMakeFriendBinding;
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

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileMakeFriendFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileMakeFriendFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SharedPreferences sharedPreferences;
    private DatabaseReference databaseReference;

    private FirebaseStorage storage;
    private StorageReference storageReference;
    private User user;
    FragmentProfileMakeFriendBinding mBinding;
    private String token;
    private String TAG = "";

    public ProfileMakeFriendFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileMakeFriendFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileMakeFriendFragment newInstance(String param1, String param2) {
        ProfileMakeFriendFragment fragment = new ProfileMakeFriendFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_make_friend, container, false);

        token = Utils.getToken(getActivity());

        getUser(token);


//        mBinding.logout.setOnClickListener(v -> {
//            sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("token", "1");
//            editor.apply();
//            FirebaseAuth.getInstance().signOut();
//
//            Intent intent = new Intent(getActivity(), LoginActivity.class);
//
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//            startActivity(intent);
//        });
//        mBinding.chooseAvatar.setOnClickListener(v -> {
//            Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
//            photoPickerIntent.setType("image/*");
//            startActivityForResult(photoPickerIntent, 1);
//
//        });

        return mBinding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
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
                mBinding.tvYear.setText("Date of Birth: " + user.getYear_born());
                if (!user.getAvatar().equals("default") && ProfileMakeFriendFragment.this.getActivity() != null) {
                    Glide.with(getContext()).load(Uri.parse(user.getAvatar()))
                            .into(mBinding.avatar);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }
}