package com.example.pet.mode.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pet.R;
import com.example.pet.mode.home.ProfileFriendFragment;
import com.example.pet.mode.models.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchFriendAdapater2 extends FirebaseRecyclerAdapter<User, SearchFriendAdapater2.viewholder> {

    public SearchFriendAdapater2(@NonNull @NotNull FirebaseRecyclerOptions<User> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull SearchFriendAdapater2.viewholder viewholder, int i, @NonNull @NotNull User user) {
        viewholder.name.setText(user.getNick_name());
        Glide.with(viewholder.img.getContext()).load(user.getAvatar()).into(viewholder.img);
    }

    @NonNull
    @NotNull
    @Override
    public SearchFriendAdapater2.viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.search_friend,parent,false);
        return new viewholder(view);
    }

    public class viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView img;
        TextView name;

        public viewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            img = (CircleImageView) itemView.findViewById(R.id.img1);
            name = (TextView) itemView.findViewById(R.id.nametext);
            itemView.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View v) {
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            Fragment myFragment = new ProfileFriendFragment();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_search_friend, myFragment).addToBackStack(null).commit();
        }
    }
}
