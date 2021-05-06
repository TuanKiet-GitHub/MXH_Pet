package com.example.pet.mode.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pet.R;
import com.example.pet.mode.models.Image;
import com.example.pet.mode.utils.Utils;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Image> listSanPham;

   private static OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public ImageAdapter(Context context, ArrayList<Image> listSanPham) {
        this.context = context;
        this.listSanPham = listSanPham;
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.ViewHolder holder, int position) {

        Glide.with(context)
                .load(listSanPham.get(position).getImage())
                .into(holder.image);

    }

    @Override
    public int getItemCount() {
        return listSanPham.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image, chosen;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageItem2);
            cardView = itemView.findViewById(R.id.card);
            chosen = itemView.findViewById(R.id.chosen);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        listener.onItemClick(itemView, getLayoutPosition());
                    }else {
                        boolean isCheck =listSanPham.get(getLayoutPosition()).isChosen();

                        if(isCheck){
                            listSanPham.get(getLayoutPosition()).setChosen(!isCheck);
                            cardView.getLayoutParams().width = (int) Utils.pxFromDp(context, 100);
                            cardView.getLayoutParams().height = (int) Utils.pxFromDp(context, 100);
                            chosen.setVisibility(View.GONE);
                        } else {
                            listSanPham.get(getLayoutPosition()).setChosen(!isCheck);
                            cardView.getLayoutParams().width = (int) Utils.pxFromDp(context, 80);
                            cardView.getLayoutParams().height = (int) Utils.pxFromDp(context, 80);
                            chosen.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });
        }
    }

}
