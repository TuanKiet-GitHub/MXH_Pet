package com.example.pet.mode.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pet.R;
import com.example.pet.databinding.ItemListNewsBinding;
import com.example.pet.mode.models.Image;
import com.example.pet.mode.models.New;
import com.example.pet.mode.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListNewsAdapter extends RecyclerView.Adapter<ListNewsAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<New> list;
    private User user;
    ItemListNewsBinding mBinding;
    private ArrayList<Image> listImage;
    ImageAdapter adapter;
    String day;
    DatabaseReference databaseReference;


    public ListNewsAdapter(Context context, ArrayList<New> list, User user, String day) {
        this.context = context;
        this.list = list;
        this.user = user;
        this.day = day;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_list_news, parent, false);

        return new MyViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.mBinding.setUser(user);
        holder.mBinding.setNews(list.get(position));
        listImage = new ArrayList<>();
//        for (Image image: list.get(position).getImages()) {
//           // Log.e("TAG", "ListNewsAdapter: "+image.getContent() );
//            listImage.add(image);
//        }
        getListImages(position, holder);

    }

    private void getListImages(int position, MyViewHolder holder ) {
        ArrayList<Image> arrayList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("News").child(day).child(list.get(position).getId()).child("list_image");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {

                for (DataSnapshot s: snapshot.getChildren()) {
                    arrayList.add(new Image(s.getValue(String.class)));

                }
                adapter = new ImageAdapter(context, arrayList, 0);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
                holder.mBinding.rcvListImage.setLayoutManager(linearLayoutManager);
                holder.mBinding.rcvListImage.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("Loi", "onCancelled: " + error.getMessage());
            }
        });


    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemListNewsBinding mBinding;
        RecyclerView recyclerView;

        public MyViewHolder(@NonNull ItemListNewsBinding itemView) {
            super(itemView.getRoot());

            this.mBinding = itemView;
            recyclerView = itemView.rcvListImage;
        }
    }

}