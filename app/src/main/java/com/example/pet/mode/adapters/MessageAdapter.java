package com.example.pet.mode.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pet.R;
import com.example.pet.mode.models.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHoler>{
    public static final int LEFT = 0 ;
    public static final int RIGHT = 1 ;
    private Context context ;
    private ArrayList<Message> list;
    private String imgUrl;
    FirebaseUser fUser;
    public MessageAdapter(Context context , ArrayList<Message> list , String imgUrl)
    {
        this.context = context;
        this.list = list;
        this.imgUrl = imgUrl;
    }
    @Override
    public ViewHoler onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        if(viewType == RIGHT)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_right,parent,false);
            return new ViewHoler(view);
        }
        else
        {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_left,parent,false);
            return new ViewHoler(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHoler holder, int position) {
        Message message = list.get(position);
        holder.show_message.setText(message.getMessage());
        Glide.with(context).load(imgUrl).fitCenter().into(holder.profile_image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {
        public TextView show_message ;
        public ImageView profile_image;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            show_message = itemView.findViewById(R.id.show_message);
            profile_image = itemView.findViewById(R.id.profile_image);
        }
    }

    @Override
    public int getItemViewType(int position) {
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        if(list.get(position).getSender().equals(fUser.getUid()))
        {
            return RIGHT;
        }
        else {
            return LEFT;
        }
    }
}
