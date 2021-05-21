package com.example.pet.mode.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pet.R;
import com.example.pet.databinding.FragmentProfileBinding;
import com.example.pet.mode.adapters.UserChatAdapter;
import com.example.pet.mode.models.Friend;
import com.example.pet.mode.models.User;
import com.example.pet.mode.models.UserChat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class MessageFragment extends Fragment {
    ArrayList<UserChat> listChat ;
    UserChatAdapter adapter ;
    RecyclerView recyclerView ;
    private String token;
    private SharedPreferences sharedPreferences;
    private DatabaseReference friendReference;
    private DatabaseReference showFriendReference;
    public ArrayList<Friend> listFriend ;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    FragmentProfileBinding mBinding;
    public MessageFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewChat);
        sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "1");
        listFriend = new ArrayList<>();
        listChat = new ArrayList<>();
        adapter = new UserChatAdapter(getContext(), listChat);
        if (!token.equals("1")) {
            friendReference = FirebaseDatabase.getInstance().getReference("Users").child(token).child("listFriends");
            friendReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        for (DataSnapshot dataSnapshot : snapshot.getChildren())
                        {
                            Friend friend =dataSnapshot.getValue(Friend.class);
                            showFriendReference =FirebaseDatabase.getInstance().getReference("Users").child("" + friend.getId());
                            showFriendReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    User user = snapshot.getValue(User.class);
                                    for ( int i = 0 ; i < listChat.size() ; i ++)
                                    {
                                        if (user.getId().equals(listChat.get(i).getId()))
                                        {
                                            listChat.remove(i);
                                        }
                                    }
                                    Log.e("friend", user.getId() + "|" + user.getNick_name()  + " | " + friend.getLastMessage()  + "|"+ user.getAvatar());
                                    listChat.add(new UserChat(token,user.getId() , user.getAvatar(), user.getNick_name() , friend.getLastMessage(), user.getStatus()));
                                    //  Log.e("friend", listChat.get(0).getLastMessage());
                                    adapter.notifyDataSetChanged();
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                        }
                    }
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
        return view;
    }
}