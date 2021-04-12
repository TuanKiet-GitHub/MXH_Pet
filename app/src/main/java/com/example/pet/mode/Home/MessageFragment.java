package com.example.pet.mode.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pet.R;
import com.example.pet.mode.Adapter.ChatAdapter;
import com.example.pet.mode.Mode.Chat;

import java.util.ArrayList;

public class MessageFragment extends Fragment {
    ArrayList<Chat> listChat ;
    ChatAdapter adapter ;
    RecyclerView recyclerView ;
    public MessageFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewChat);
        listChat = new ArrayList<>();
        listChat.add(new Chat(R.drawable.avatar,"Văn A" , "Nôi mèo sau bạn !", R.drawable.circle_blue));
        listChat.add(new Chat(R.drawable.people,"Văn Lên " , "Nôi mèo sau bạn !", R.drawable.circle_silver));
        listChat.add(new Chat(R.drawable.avatar,"Tuấn Việt" , "Nôi mèo sau bạn !",  R.drawable.circle_blue));
        listChat.add(new Chat(R.drawable.avatar,"Anh Thư" , "Nôi mèo sau bạn !",  R.drawable.circle_silver));
        adapter = new ChatAdapter(getContext(),listChat);
        recyclerView.setAdapter(adapter);
        Log.e("Log" , "" + listChat.size());
        return view;
    }
}