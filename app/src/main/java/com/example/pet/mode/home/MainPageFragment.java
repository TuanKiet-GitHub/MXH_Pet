package com.example.pet.mode.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.pet.mode.models.New;
import com.example.pet.mode.models.User;
import com.example.pet.mode.utils.Utils;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static android.content.ContentValues.TAG;


public class MainPageFragment extends Fragment {
    private FragmentMainPageBinding mBinding;
    private ListNewsAdapter adapter;
    private ArrayList<New> listNews;
    private String token;
    User user;

    DatabaseReference reference;

    public MainPageFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_page, container, false);
        mBinding.linerStatus.setOnClickListener(v -> startActivity(new Intent(getActivity(), StatusActivity.class)));
        token = Utils.getToken(getActivity());

        String temp = Utils.getUserInfor(getActivity());
        if (!token.equals("1") && !temp.equals("1")) {
            user = new Gson().fromJson(temp, User.class);
            SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
            String date = sf.format(Calendar.getInstance().getTime());

            listNews = getListNew(date);

            Glide.with(Objects.requireNonNull(getActivity()))
                    .load(user.getAvatar())
                    .into(mBinding.avatar);

        }

        return mBinding.getRoot();
    }

    private ArrayList<New> getListNew(String day) {
        ArrayList<New> listNews = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("News").child(day);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot s : snapshot.getChildren()) {

                        New news = s.getValue(New.class);

                        listNews.add(news);

                    }

                    adapter = new ListNewsAdapter(getContext(), listNews, user, day);
                    mBinding.rcvListNew.setLayoutManager(new LinearLayoutManager(getContext()));
                    mBinding.rcvListNew.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        return listNews;
    }
}