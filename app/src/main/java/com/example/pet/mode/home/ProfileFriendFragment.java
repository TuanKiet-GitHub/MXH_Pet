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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pet.R;
import com.example.pet.mode.activities.SearchFriendActivity;
import com.example.pet.mode.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.concurrent.Executor;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFriendFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFriendFragment extends Fragment {
    private DatabaseReference friendReference, requestRef, friendRequestRef;
    CircleImageView circleImageView;
    TextView userName, nickName, gender, DOB, address, phone;
    Button send, decline;
    ImageButton back;
    String value;
    SharedPreferences sharedPreferences;
    String token;
    String currentState = "nothing_happen";

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
        value = this.getArguments().getString("id");
        sharedPreferences = view.getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "1");

        circleImageView = view.findViewById(R.id.avatar_friend);
        userName = view.findViewById(R.id.tv_username_friend);
        nickName = view.findViewById(R.id.tv_nick_name_friend);
        gender = view.findViewById(R.id.tv_gender_friend);
        DOB = view.findViewById(R.id.tv_year_friend);
        address = view.findViewById(R.id.tv_address_friend);
        phone = view.findViewById(R.id.tv_phoneNumber_friend);

        send = view.findViewById(R.id.SendRequest_friend);
        decline = view.findViewById(R.id.DeclineRequest_friend);
        back = view.findViewById(R.id.btn_back);


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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), SearchFriendActivity.class));
            }
        });

        requestRef = FirebaseDatabase.getInstance().getReference().child("Users").child(""+token).child("Requests");
        friendRequestRef = FirebaseDatabase.getInstance().getReference().child("Users").child(""+value).child("Friend_Request");

        // Send Request

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perfromAction(value);
            }
        });

        // Declide Request

        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    // Thực hiện kết bạn

    private void perfromAction(String value) {
        if (currentState.equals("nothing_happen")){
            HashMap hashMap = new HashMap();
            hashMap.put("status", "Pending");
            hashMap.put("userId", value);
            requestRef.child(value).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull @NotNull Task task) {
                    if (task.isSuccessful()){
                        HashMap hashMap = new HashMap();
                        hashMap.put("status", "Friend_Request");
                        hashMap.put("userId", token);
                        friendRequestRef.child(token).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(getContext(), "You just send a friend request", Toast.LENGTH_SHORT).show();
                                    decline.setVisibility(View.GONE);
                                    currentState = "I_sent_pending";
                                    send.setText("Cancel Friend Request");
                                }
                                else {
                                    Toast.makeText(getContext(), "Can't send \n" + task.getException() , Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else {
                        Toast.makeText(getContext(), "Can't send \n" + task.getException() , Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        if (currentState.equals("I_sent_pending") || currentState.equals("I_sent_decline")){
            requestRef.child(value).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Void> task) {
                    if (task.isSuccessful()){
                        friendRequestRef.child(token).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(getContext(), "You have canceled a friend request", Toast.LENGTH_SHORT).show();
                                    decline.setVisibility(View.GONE);
                                    currentState = "nothing_happen";
                                    send.setText("Send Friend Request");
                                    decline.setVisibility(View.VISIBLE);
                                }
                                else {
                                    Toast.makeText(getContext(), "Incomplete \n" + task.getException() , Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else {
                        Toast.makeText(getContext(), "Incomplete \n" + task.getException() , Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        if (currentState.equals("He_sent_pending")){
            requestRef.child(value).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Void> task) {
                    if (task.isSuccessful()){

                        HashMap hashMap = new HashMap();
                        hashMap.put("status", "Friend_Pending");
                        hashMap.put("userId", token);

                        friendRequestRef.child(token).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task task) {
                                if (task.isSuccessful()){
                                    HashMap hashMap = new HashMap();
                                    hashMap.put("status", "Friend_Pending");
                                    hashMap.put("userId", value);
                                    requestRef.child(value).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                                        @Override
                                        public void onComplete(@NonNull @NotNull Task task) {
                                            if (task.isSuccessful()){
                                                currentState = "watting_accept_friend";
                                                send.setText("Accept");
                                                decline.setText("Cancel");
                                                decline.setVisibility(View.VISIBLE);
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                    else {
                        Toast.makeText(getContext(), "Incomplete \n" + task.getException() , Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        if (currentState.equals("watting_accept_friend")){
            //
        }
    }
}