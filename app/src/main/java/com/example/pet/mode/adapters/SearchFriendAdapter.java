package com.example.pet.mode.adapters;

import android.app.FragmentTransaction;
import android.content.Context;
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
import com.example.pet.mode.home.ProfileMakeFriendFragment;
import com.example.pet.mode.models.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchFriendAdapter extends RecyclerView.Adapter<SearchFriendAdapter.viewHolder> {

    Context context;
    ArrayList<User> listUser;

    public SearchFriendAdapter(Context context, ArrayList<User> listUser) {
        this.context = context;
        this.listUser = listUser;
    }

    @NonNull
    @NotNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.search_friend,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SearchFriendAdapter.viewHolder holder, int position) {
        User user = listUser.get(position);
        holder.name.setText(user.getNick_name());
        Glide.with(holder.img.getContext()).load(user.getAvatar()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CircleImageView img;
        TextView name;

        public viewHolder(@NonNull @NotNull View itemView) {
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