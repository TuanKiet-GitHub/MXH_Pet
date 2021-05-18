package com.example.pet.mode.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pet.R;
import com.example.pet.mode.models.Image;
import com.example.pet.mode.models.Item;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    RecyclerView recyclerView ;
    ArrayList<Image> listImage ;
    ImageAdapter adapter ;

    Context context;
    ArrayList<Item> listItem ;

    public ItemAdapter(Context context, ArrayList<Item> listItem) {
        this.context = context;
        this.listItem = listItem;
    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        recyclerView =  view.findViewById(R.id.recyclerViewItem);
        listImage = new ArrayList<>();

        listImage.add(new Image("https://i.pinimg.com/736x/d1/32/64/d132644360376beb74abb10578952888.jpg"));
        listImage.add(new Image("https://i.pinimg.com/736x/d1/32/64/d132644360376beb74abb10578952888.jpg"));
        listImage.add(new Image("https://i.pinimg.com/736x/d1/32/64/d132644360376beb74abb10578952888.jpg"));
        adapter = new ImageAdapter(parent.getContext(), listImage, 1);
        recyclerView.setAdapter(adapter);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = listItem.get(position);
        holder.avatar.setImageResource(item.getAvatar());
        holder.name.setText(item.getName());
        holder.status.setText(item.getStatus());
        holder.time.setText(item.getTime());
    }
    @Override
    public int getItemCount() {
        return listItem.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView avatar;
        TextView name , status , time;

        public ViewHolder(@NonNull View itemView ) {
            super(itemView);
            avatar = itemView.findViewById(R.id.imgavatar);
            name = itemView.findViewById(R.id.txtName) ;
            status = itemView.findViewById(R.id.txtStatus) ;
            time = itemView.findViewById(R.id.txtTime);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

        }
    }


}


