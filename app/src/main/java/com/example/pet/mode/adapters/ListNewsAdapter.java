package com.example.pet.mode.adapters;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pet.R;
import com.example.pet.databinding.ItemListNewsBinding;
import com.example.pet.mode.home.ProfileFragment;
import com.example.pet.mode.models.Comment;
import com.example.pet.mode.models.Image;
import com.example.pet.mode.models.New;
import com.example.pet.mode.models.User;
import com.example.pet.mode.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ListNewsAdapter extends RecyclerView.Adapter<ListNewsAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<New> list;
    ItemListNewsBinding mBinding;
    ImageListNewAdapter adapter;
    CommentAdapter commentAdapter;

    String day;
    DatabaseReference databaseReference;
    int like = 0;
    int click = 0;


    public ListNewsAdapter(Context context, ArrayList<New> list, String day) {
        this.context = context;
        this.list = list;
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

        holder.mBinding.setNews(list.get(position));

        getListImages(position, holder);
        String token = list.get(position).getUser_id();
        getUser(token, holder);
        getListComment(list.get(position).getId(), holder);

    }

    private void getListImages(int position, MyViewHolder holder) {
        ArrayList<Image> listImage = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("News").child(day).child(list.get(position).getId()).child("list_image");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot s : snapshot.getChildren()) {
                    listImage.add(new Image(s.getValue(String.class)));
                }
                adapter = new ImageListNewAdapter(context, listImage);
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

    private void getUser(String token, MyViewHolder holder) {

        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(token);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                holder.mBinding.setUser(user);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    private void getListComment(String idNew, MyViewHolder holder) {
        ArrayList<Comment> comments = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Comments").child(idNew);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                comments.clear();
                for (DataSnapshot s : snapshot.getChildren()
                ) {
                    Comment comment = s.getValue(Comment.class);
                    comments.add(comment);
                }
                commentAdapter = new CommentAdapter(context, comments);
                commentAdapter.notifyDataSetChanged();
                holder.mBinding.rcrComment.setAdapter(commentAdapter);
                holder.mBinding.rcrComment.setLayoutManager(new LinearLayoutManager(context));
                holder.mBinding.rcrComment.setHasFixedSize(true);
                holder.mBinding.rcrComment.setNestedScrollingEnabled(false);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Log.e("TAG", "onCancelled: " + error.getMessage());
            }
        });


    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemListNewsBinding mBinding;
        RecyclerView recyclerView, rc;


        public MyViewHolder(@NonNull ItemListNewsBinding itemView) {
            super(itemView.getRoot());

            this.mBinding = itemView;
            recyclerView = itemView.rcvListImage;
            rc = itemView.rcrComment;
            itemView.heart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (like == 0) {
                        like = 1;
                        Glide.with(context)
                                .load(R.drawable.heartdo)
                                .into(mBinding.heart);
                    } else {
                        like = 0;
                        Glide.with(context)
                                .load(R.drawable.heartden)
                                .into(mBinding.heart);
                    }
                }
            });

            itemView.comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    click = click == 0 ? 1 : 0;
                    if (itemView.showComment.isShown()) {
                        itemView.showComment.setVisibility(View.GONE);
                    } else {
                        itemView.showComment.setVisibility(View.VISIBLE);
                        itemView.edtComment.requestFocus();

                    }
                   // getListComment(list.get(getAdapterPosition()).getId());
                }
            });

            itemView.imvComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String content = itemView.edtComment.getText().toString();

                    if ((!content.equals(""))) {
                        databaseReference = FirebaseDatabase.getInstance().getReference("Comments");
                        Comment comment = new Comment();
                        comment.setContent(content);
                        comment.setUser_id(Utils.getToken((Activity) context));

                        databaseReference.child(list.get(getLayoutPosition()).getId()).push().setValue(comment);

                        itemView.edtComment.setText("");
                    }
                }
            });


        }
    }


}
