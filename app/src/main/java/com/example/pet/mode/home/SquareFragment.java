package com.example.pet.mode.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.pet.R;
import com.example.pet.mode.activities.StatusActivity;
import com.example.pet.mode.adapters.ItemAdapter;
import com.example.pet.mode.models.Item;

import java.util.ArrayList;


public class SquareFragment extends Fragment {
    ArrayList<Item> listItem ;
    ItemAdapter adapter ;
    RecyclerView recyclerView ;
    LinearLayout linerStatus ;
    public SquareFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_square, container, false);
        recyclerView = view.findViewById(R.id.recyclerFragmentSquare);

        listItem = new ArrayList<>();
        listItem.add(new Item(R.drawable.avatar, "Tuấn Kiệt" , "Mọi người chia sẻ kinh nghiệm cho em nuôi hai bé mèo này với ạ !" , "24 phút" ));
        listItem.add(new Item(R.drawable.avatar, "Hồng Biên" , "Mọi người chia sẻ kinh nghiệm cho em nuôi hai bé mèo này với ạ !" , "24 phút" ));
        listItem.add(new Item(R.drawable.avatar, "Văn B" , "Mọi người chia sẻ kinh nghiệm cho em nuôi hai bé mèo này với ạ !" , "24 phút" ));
        listItem.add(new Item(R.drawable.avatar, "Văn C" , "Mọi người chia sẻ kinh nghiệm cho em nuôi hai bé mèo này với ạ !" , "24 phút" ));
         adapter = new ItemAdapter(getContext(), listItem);
         recyclerView.setAdapter(adapter);
        linerStatus = view.findViewById(R.id.linerStatus);
        linerStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext() , StatusActivity.class);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}