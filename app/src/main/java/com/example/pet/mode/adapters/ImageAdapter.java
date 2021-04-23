package com.example.pet.mode.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pet.R;
import com.example.pet.mode.models.Image;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder>  {

    Context context;
    ArrayList<Image> listSanPham;

    public ImageAdapter(Context context, ArrayList<Image> listSanPham) {
        this.context = context;
        this.listSanPham = listSanPham;
    }

    @NonNull
    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.ViewHolder holder, int position) {
        Image image = listSanPham.get(position);
        holder.image.setImageResource(image.getImage());
    }

    @Override
    public int getItemCount() {
        return listSanPham.size();
    }


    class ViewHolder extends  RecyclerView.ViewHolder {
        ImageView image ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageItem2);
        }
    }

}
