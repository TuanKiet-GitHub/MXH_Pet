package com.example.pet.mode.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pet.R;
import com.example.pet.mode.Adapter.ItemAdapter;
import com.example.pet.mode.Mode.Item;

import java.util.ArrayList;


public class LoveFragment extends Fragment {

    ArrayList<Item> listItem ;
    ItemAdapter adapter ;
    RecyclerView recyclerView ;
    public LoveFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_love, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewLove);
        listItem = new ArrayList<>();
        listItem.add(new Item(R.drawable.people, "Tuấn Kiệt" , "Mọi người chia sẻ kinh nghiệm cho em nuôi hai bé mèo này với ạ !" , "24 phút" ));
        listItem.add(new Item(R.drawable.people, "Tuấn Kiệt" , "Hello mọi người !" , "1 ngày" ));
        adapter = new ItemAdapter(getContext(), listItem);
        recyclerView.setAdapter(adapter);
        return view;
    }
}