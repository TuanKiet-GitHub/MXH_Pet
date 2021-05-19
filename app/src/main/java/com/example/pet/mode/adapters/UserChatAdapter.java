package com.example.pet.mode.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pet.R;
import com.example.pet.mode.activities.MessageActivity;
import com.example.pet.mode.models.UserChat;

import java.io.Serializable;
import java.util.ArrayList;

public class UserChatAdapter extends RecyclerView.Adapter<UserChatAdapter.ViewHolder> {

    Context context;
    ArrayList<UserChat> list ;
    public UserChatAdapter(Context context, ArrayList<UserChat> list) {
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
    public void onBindViewHolder(@NonNull UserChatAdapter.ViewHolder holder, int position) {
        UserChat chat = list.get(position);
        Uri uri = Uri.parse(chat.getImage().toString().trim());
        Glide.with(context).load(uri).fitCenter().into(holder.imageView);
        // holder.imageView.setImageResource(chat.getImage());
        holder.name.setText(chat.getName());
        holder.chat.setText(chat.getLastMessage());
        holder.view.setTag(position);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView ;
        TextView name , chat ;
        ImageView img_On, img_Off ;
        View view ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            imageView = itemView.findViewById(R.id.imgAvatarChat);
            name = itemView.findViewById(R.id.tvNameChat);
            chat = itemView.findViewById(R.id.tvMessageChat);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            UserChat chat = list.get(Integer.parseInt(v.getTag()+""));
            Activity activity= (Activity) v.getContext();
            Intent intent = new Intent(activity, MessageActivity.class);
            Bundle bundle = new Bundle();
            int position = Integer.parseInt(v.getTag().toString());
            bundle.putSerializable("FriendChat", (Serializable) list.get(position));
            intent.putExtra("data", bundle);
            activity.startActivity(intent);
//              Activity activity= (Activity) v.getContext();
//              Fragment fragment = new MessageFragment();
//              FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();


        }
    }
}
