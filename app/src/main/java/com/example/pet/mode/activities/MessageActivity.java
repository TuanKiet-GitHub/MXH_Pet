package com.example.pet.mode.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pet.R;
import com.example.pet.mode.models.Chat;

public class MessageActivity extends AppCompatActivity {
    private Chat chat ;
    ImageView imgBack , imgAvatar , imgSend ;
    TextView txtNickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        imgBack= findViewById(R.id.imgBackMessage);
        imgAvatar = findViewById(R.id.imgavatarMessage);
        imgSend = findViewById(R.id.imgSendMessage);
        txtNickName = findViewById(R.id.txtNickName);

        Intent intentGet = getIntent();
        Bundle bundle = intentGet.getBundleExtra("data");
        if (bundle!=null)
        {
            chat = (Chat) bundle.getSerializable("FriendChat");
       //     Log.e("Chat" , chat.getImage());
//            //Toast.makeText(OrderPageActivity.this , "" + foodMode.getTitle(), Toast.LENGTH_SHORT).show();
            Uri uri = Uri.parse(chat.getImage().toString().trim());
            Glide.with(getApplicationContext()).load(uri).dontAnimate().centerCrop().into(imgAvatar);
            //  Toast.makeText(getApplicationContext(), uri.toString() , Toast.LENGTH_SHORT).show();
            txtNickName.setText(chat.getName());
        //    Log.e("ListCart" , "Oncreate :" + foodMode.getId());
        }
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}