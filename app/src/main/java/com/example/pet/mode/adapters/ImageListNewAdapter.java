package com.example.pet.mode.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pet.R;
import com.example.pet.databinding.ItemListImageBinding;
import com.example.pet.mode.activities.FullScreenActivity;
import com.example.pet.mode.models.Image;
import com.google.gson.Gson;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ImageListNewAdapter extends RecyclerView.Adapter<ImageListNewAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Image> listImage;
    ItemListImageBinding mBinding;

    private static ImageListNewAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(ImageListNewAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }


    public ImageListNewAdapter(Context context, ArrayList<Image> listImage) {
        this.context = context;
        this.listImage = listImage;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_list_image, parent, false);
        return new MyViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ImageListNewAdapter.MyViewHolder holder, int position) {
        Glide.with(context)
                .load(listImage.get(position).getImage())
                .into(mBinding.image);
    }

    @Override
    public int getItemCount() {
        return listImage == null ? 0 : listImage.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ItemListImageBinding imageBinding;

        public MyViewHolder(@NonNull @org.jetbrains.annotations.NotNull ItemListImageBinding mBinding) {
            super(mBinding.getRoot());
            imageBinding = mBinding;
            mBinding.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, FullScreenActivity.class);
                    Gson gson = new Gson();
                    String temp = gson.toJson(listImage);
                    intent.putExtra("list_image", temp);

                    context.startActivity(intent);
                }
            });
        }
    }


}
