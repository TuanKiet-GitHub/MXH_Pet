package com.example.pet.mode.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pet.R;
import com.example.pet.mode.Mode.Chat;
import com.example.pet.mode.Mode.Model;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    Context context;
    ArrayList<Chat> list ;
    public ChatAdapter(Context context, ArrayList<Chat> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chat, parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
        Chat chat = list.get(position);
        holder.imageView.setImageResource(chat.getImage());
        holder.name.setText(chat.getName());
        holder.chat.setText(chat.getChat());
        holder.status.setImageResource(chat.getStatus());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView  , status;
        TextView name , chat ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgAvatarChat);
            name = itemView.findViewById(R.id.tvNameChat);
            chat = itemView.findViewById(R.id.tvMessageChat);
            status = itemView.findViewById(R.id.imgStatusMessage);
        }
    }
}
