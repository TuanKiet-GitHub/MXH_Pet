package com.example.pet.mode.home;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.pet.R;
import com.example.pet.databinding.FragmentMainPageBinding;
import com.example.pet.mode.activities.StatusActivity;
import com.example.pet.mode.adapters.ListNewsAdapter;
import com.example.pet.mode.models.Message;
import com.example.pet.mode.models.New;
import com.example.pet.mode.models.User;
import com.example.pet.mode.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.jetbrains.annotations.NotNull;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class MainPageFragment extends Fragment {
    private FragmentMainPageBinding mBinding;
    private ListNewsAdapter adapter;
    private ArrayList<New> listNews;
    private String token;


    DatabaseReference reference;
    ValueEventListener seenListener ;
    public MainPageFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_page, container, false);
        mBinding.linerStatus.setOnClickListener(v -> startActivity(new Intent(getActivity(), StatusActivity.class)));
        token = Utils.getToken(getActivity());

       // seenMessage(FirebaseAuth.getInstance().getCurrentUser().getUid());

        if (!token.equals("1")) {
            getUser(token);
            SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
            String date = sf.format(Calendar.getInstance().getTime());

            listNews = getListNew();
        }

        return mBinding.getRoot();
    }

    private ArrayList<New> getListNew() {
        ArrayList<New> listNews = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("News");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                listNews.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot s : snapshot.getChildren()) {

                        New news = s.getValue(New.class);

                        listNews.add(news);

                    }

                    adapter = new ListNewsAdapter(getContext(), listNews, ListNewsAdapter.FRAGMENT_NEW);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    linearLayoutManager.setReverseLayout(true);
                    mBinding.rcvListNew.setLayoutManager(linearLayoutManager);
                    mBinding.rcvListNew.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        return listNews;
    }

    private void getUser(String token) {

        reference = FirebaseDatabase.getInstance().getReference("Users").child(token);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if (getActivity() != null) {
                    Glide.with((getActivity()))
                            .load(user.getAvatar()).placeholder(R.drawable.bg_login)
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
        Log.e("seen", "VAO");
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        seenListener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Message message = dataSnapshot.getValue(Message.class);
                    // token là người gửi.
                    Log.e("L","Receiver"+ message.getReceiver() + " | sender " + message.getSender() + " | " + message.getStatus()+ "token" + token );
                    if(message.getReceiver().equals(iDReceiver))
                    {
                        HashMap<String , Object> hashMap = new HashMap<>();
                        Log.e("seen", "VAO Seen");
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
        // reference.removeEventListener(seenListener);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}