package com.example.pet.mode.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pet.R;
import com.example.pet.mode.models.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchFriendAdapter extends FirebaseRecyclerAdapter<User, SearchFriendAdapter.viewHolder> {

    public SearchFriendAdapter(@NonNull @NotNull FirebaseRecyclerOptions<User> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull SearchFriendAdapter.viewHolder viewHolder, int i, @NonNull @NotNull User user) {
        viewHolder.name.setText(user.getNick_name());
        Glide.with(viewHolder.img.getContext()).load(user.getAvatar()).into(viewHolder.img);
    }

    @NonNull
    @NotNull
    @Override
    public SearchFriendAdapter.viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.search_friend,parent,false);
        return new viewHolder(view);
    }

    class viewHolder extends RecyclerView.ViewHolder {
        CircleImageView img;
        TextView name, course, email;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            img = (CircleImageView) itemView.findViewById(R.id.img1);
            name = (TextView) itemView.findViewById(R.id.nametext);
        }
    }   }