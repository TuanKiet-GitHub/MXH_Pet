package com.example.pet.mode.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pet.R;
import com.example.pet.databinding.FragmentLoveBinding;
import com.example.pet.mode.adapters.ListNewsAdapter;
import com.example.pet.mode.models.Item;
import com.example.pet.mode.models.Message;
import com.example.pet.mode.models.New;
import com.example.pet.mode.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;


public class LoveFragment extends Fragment {

    private RecyclerView recyclerView;
    private FragmentLoveBinding binding;
    private ListNewsAdapter adapter;
    private DatabaseReference reference;

    ValueEventListener seenListener ;

    public LoveFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_love, container, false);
        seenMessage(FirebaseAuth.getInstance().getCurrentUser().getUid());
        setListNews();
        return binding.getRoot();
    }
    private void setListNews() {
        ArrayList<New> list = new ArrayList<>();
        ArrayList<String> listFavorite = new ArrayList<>();
        if (getActivity() != null) {
            String token = Utils.getToken(getActivity());
            reference = FirebaseDatabase.getInstance().getReference("Users").child(token).child("favorite_posts");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    listFavorite.clear();
                    if (snapshot.exists()) {
                        for (DataSnapshot s : snapshot.getChildren()) {
                            reference = FirebaseDatabase.getInstance().getReference("News").child(s.getValue(String.class));
                            reference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                    list.add(snapshot.getValue(New.class));


                                    adapter = new ListNewsAdapter(getContext(), list, ListNewsAdapter.FRAGMENT_LOVE);
                                    binding.recyclerViewLove.setLayoutManager(new LinearLayoutManager(getContext()));
                                    binding.recyclerViewLove.setAdapter(adapter);
                                }

                                @Override
                                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                }
                            });
                        }


                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }

    }
    private void seenMessage (final String iDReceiver)
    {
        // Log.e("seen", "VAO");
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        seenListener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Message message = dataSnapshot.getValue(Message.class);
                    // token là người gửi.
                     Log.e("Log","LoveFragment Received !!!"  );
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
    public void onPause() {
        super.onPause();
        reference.removeEventListener(seenListener);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}