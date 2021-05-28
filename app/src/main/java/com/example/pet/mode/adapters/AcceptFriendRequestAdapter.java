package com.example.pet.mode.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pet.R;
import com.example.pet.databinding.ItemAcceptRequestFriendBinding;
import com.example.pet.mode.models.User;
import com.example.pet.mode.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AcceptFriendRequestAdapter extends RecyclerView.Adapter<AcceptFriendRequestAdapter.ViewHolder> {
    private Context context;
    private ArrayList<User> users;
    private DatabaseReference reference;

    public AcceptFriendRequestAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ItemAcceptRequestFriendBinding mBinding =
                DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_accept_request_friend, parent, false);
        return new ViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AcceptFriendRequestAdapter.ViewHolder holder, int position) {
        holder.itemView.setUser(users.get(position));
        Glide.with(context)
                .load(users.get(position).getAvatar())
                .into(holder.itemView.avatar);
    }

    @Override
    public int getItemCount() {
        return users != null ? users.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemAcceptRequestFriendBinding itemView;
        String token = Utils.getToken((Activity) context);

        public ViewHolder(@NonNull @NotNull ItemAcceptRequestFriendBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;

            itemView.accept.setOnClickListener(v -> {
                accept();
            });

            itemView.denied.setOnClickListener(v -> {
                deny();
            });
        }

        private void accept() {
            reference = FirebaseDatabase.getInstance().getReference("Users").child(token)
                    .child("listFriends").child(users.get(getAdapterPosition()).getId());
            reference.child("id").setValue(users.get(getAdapterPosition()).getId())
                    .addOnCompleteListener(
                            task -> Toast.makeText(context, "You have accepted the friend request", Toast.LENGTH_LONG).show());
            reference.child("lastMessage").setValue("Ban Da tro thanh ban");
            reference = FirebaseDatabase.getInstance().getReference("Users").child(token)
                    .child("list_request_friend").child(users.get(getAdapterPosition()).getId());
            reference.removeValue();
        }

        private void deny() {
            reference = FirebaseDatabase.getInstance().getReference("Users").child(token)
                    .child("list_request_friend").child(users.get(getAdapterPosition()).getId());
            reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Void> task) {
                    Toast.makeText(context, "You declined the friend request", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
