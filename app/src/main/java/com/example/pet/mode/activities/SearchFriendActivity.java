package com.example.pet.mode.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.pet.R;
import com.example.pet.mode.adapters.SearchFriendAdapter;
import com.example.pet.mode.adapters.UserChatAdapter;
import com.example.pet.mode.models.Friend;
import com.example.pet.mode.models.User;
import com.example.pet.mode.models.UserChat;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SearchFriendActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SearchFriendAdapter adapter;

    ArrayList<User> listUser ;
    ArrayList<User> listFriend ;
    private String token;
    private SharedPreferences sharedPreferences;
    private DatabaseReference friendReference;
    private DatabaseReference showFriendReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_friend);

//        setTitle("");
//
        recyclerView = findViewById(R.id.recyclerViewFriendSearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        FirebaseRecyclerOptions<User> options =
//                new FirebaseRecyclerOptions.Builder<User>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Users"), User.class)
//                        .build();
//        adapter = new SearchFriendAdapter(options);
//        recyclerView.setAdapter(adapter);

        sharedPreferences = getApplicationContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "1");
        listFriend = new ArrayList<>();
        listUser = new ArrayList<>();
        adapter = new SearchFriendAdapter(getApplicationContext(), listUser);

        if (!token.equals("1")) {
            friendReference = FirebaseDatabase.getInstance().getReference("Users").child(token).child("listFriends");
            friendReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        for (DataSnapshot dataSnapshot : snapshot.getChildren())
                        {
                            User friend = dataSnapshot.getValue(User.class);
                            listFriend.add(new User(friend.getId(), friend.getNick_name(), friend.getAvatar()));
                            adapter.notifyDataSetChanged();
                            showFriendReference =FirebaseDatabase.getInstance().getReference("Users");
                            showFriendReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                        User user = dataSnapshot.getValue(User.class);
                                        for (int i = 0; i < listFriend.size(); i++) {
                                            Log.e("TAG", "" + listFriend.get(i).getId() + "-----" + user.getId());
                                            if (!user.getId().equals(listFriend.get(i).getId()) && !user.getId().equals(token)) {
                                                listUser.add(new User(user.getId(), user.getNick_name(), user.getAvatar()));
                                            }
                                        }
                                        adapter.notifyDataSetChanged();
                                    }
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
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }


        SearchView searchView = findViewById(R.id.searchview_friend);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //processsearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //processsearch(newText);
                return false;
            }
        });
    }

//    private void processsearch(String s) {
//        FirebaseRecyclerOptions<User> options =
//                new FirebaseRecyclerOptions.Builder<User>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("nick_name")
//                                .startAt(s).endAt(s+"\uf8ff"), User.class)
//                        .build();
//        adapter = new SearchFriendAdapter(options);
//        adapter.startListening();
//        recyclerView.setAdapter(adapter);
//    }

    public void ReturnMain(View view) {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
    }
}