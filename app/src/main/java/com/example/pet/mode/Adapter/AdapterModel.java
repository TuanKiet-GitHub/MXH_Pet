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
import com.example.pet.mode.Mode.Model;

import java.util.ArrayList;

public class AdapterModel extends RecyclerView.Adapter<AdapterModel.ViewHolder> {

    Context context;
    ArrayList<Model> list ;
    public AdapterModel(Context context, ArrayList<Model> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.model, parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Model model = list.get(position);
        holder.Image.setImageResource(model.getImage());
        holder.describe.setText(model.getDescribe());
        holder.title.setText(model.getTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView Image ;
        TextView title , describe ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            describe = itemView.findViewById(R.id.describe);
        }
    }
}
