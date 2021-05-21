package com.example.pet.mode.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pet.R;
import com.example.pet.mode.activities.SearchFriendActivity;
import com.example.pet.mode.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFriendFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFriendFragment extends Fragment {
    private DatabaseReference friendReference;
    CircleImageView circleImageView;
    TextView userName, nickName, gender, DOB, address, phone;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFriendFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFriendFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFriendFragment newInstance(String param1, String param2) {
        ProfileFriendFragment fragment = new ProfileFriendFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_friend, container, false);

        circleImageView = view.findViewById(R.id.avatar_friend);
        userName = view.findViewById(R.id.tv_username_friend);
        nickName = view.findViewById(R.id.tv_nick_name_friend);
        gender = view.findViewById(R.id.tv_gender_friend);
        DOB = view.findViewById(R.id.tv_year_friend);
        address = view.findViewById(R.id.tv_address_friend);
        phone = view.findViewById(R.id.tv_phoneNumber_friend);

        String value = this.getArguments().getString("id");
        Log.e("TAG", ""+ value );

        if (!value.isEmpty()) {
            friendReference = FirebaseDatabase.getInstance().getReference("Users").child(value);
            friendReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                            User user = snapshot.getValue(User.class);
                            Glide.with(getContext()).load(user.getAvatar()).into(circleImageView);
                            userName.setText(user.getFull_name());
                            nickName.setText(user.getNick_name());
                            gender.setText("Gender: " + user.getGender());
                            DOB.setText("Day Of Birth: " + user.getYear_born());
                            address.setText("Address: " + user.getAddress());
                            phone.setText("Phone Number: " + user.getPhone_number());
//                            Log.e("TAG", "" + user);
                        }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }

        // Return
        ImageButton btn_back = view.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), SearchFriendActivity.class));
            }
        });

        // Send Request
        Button send = view.findViewById(R.id.SendRequest);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), SearchFriendActivity.class));
            }
        });

        return view;
    }
}