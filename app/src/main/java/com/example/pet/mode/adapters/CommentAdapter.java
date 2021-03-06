package com.example.pet.mode.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pet.R;
import com.example.pet.databinding.ItemCommentBinding;
import com.example.pet.mode.models.Comment;
import com.example.pet.mode.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder>{
    Context context;
    ArrayList<Comment> list;
    ItemCommentBinding itemCommentBinding;

    public CommentAdapter(Context context, ArrayList<Comment> list) {
        this.context = context;
        this.list = list;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        itemCommentBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_comment, parent, false);
        return new MyViewHolder(itemCommentBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CommentAdapter.MyViewHolder holder, int position) {
        holder.itemCommentBinding.setComment(list.get(position));

        setAvatarAndNickName(holder, list.get(position).getUser_id());

    }

    private void setAvatarAndNickName(MyViewHolder holder, String token){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(token);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                holder.itemCommentBinding.setUser(user);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ItemCommentBinding itemCommentBinding;
        public MyViewHolder(@NonNull @NotNull ItemCommentBinding itemCommentBinding) {
            super(itemCommentBinding.getRoot());
            this.itemCommentBinding = itemCommentBinding;
        }
    }
}
