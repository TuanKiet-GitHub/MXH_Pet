package com.example.pet.mode.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pet.R;
import com.example.pet.mode.adapters.MessageAdapter;
import com.example.pet.mode.models.Message;
import com.example.pet.mode.models.UserChat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MessageActivity extends AppCompatActivity {
    private UserChat userChat ;
    ImageView imgBack , imgAvatar , imgSend ;
    TextView txtNickName;
    EditText edSendMessage ;
    MessageAdapter adapter ;
    private ArrayList<Message> list ;
    private String uriString;
    private  DatabaseReference reference;
    private  RecyclerView recyclerView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        imgBack= findViewById(R.id.imgBackMessage);
        imgAvatar = findViewById(R.id.imgavatarMessage);
        imgSend = findViewById(R.id.imgSendMessage);
        txtNickName = findViewById(R.id.txtNickName);
        edSendMessage = findViewById(R.id.SendMessage);
        recyclerView = findViewById(R.id.recyclerViewMessage);
        recyclerView.setHasFixedSize(true);

        Intent intentGet = getIntent();
        Bundle bundle = intentGet.getBundleExtra("data");
        if (bundle!=null)
        {
            userChat = (UserChat) bundle.getSerializable("FriendChat");
       //     Log.e("Chat" , chat.getImage());
//            //Toast.makeText(OrderPageActivity.this , "" + foodMode.getTitle(), Toast.LENGTH_SHORT).show();
            Uri uri = Uri.parse(userChat.getImage().toString().trim());
            uriString  = userChat.getImage().toString().trim();
            Glide.with(getApplicationContext()).load(uri).dontAnimate().centerCrop().into(imgAvatar);
            //  Toast.makeText(getApplicationContext(), uri.toString() , Toast.LENGTH_SHORT).show();
            txtNickName.setText(userChat.getName());
        //    Log.e("ListCart" , "Oncreate :" + foodMode.getId());
        }
        Log.e("log",userChat.getTokenSender() + "|" + userChat.getId() + "|" + uriString );
        readMessage(userChat.getTokenSender(), userChat.getId(), uriString);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mess =  edSendMessage.getText().toString().trim() ;
                 if (!mess.equals(""))
                 {
                     sendMessage(userChat.getTokenSender(), userChat.getId() ,mess);
                 }
                 else
                 {
                 }
                 edSendMessage.setText("");
            }
        });
    }
    private void sendMessage(String sender, String receiver, String message)
    {
        reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String , Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        reference.child("Chats").push().setValue(hashMap);

    }
    private void readMessage(String id ,String userId, String imgUrl)
    {
        list = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Message message = dataSnapshot.getValue(Message.class);
                    Log.e("log", message.getReceiver() + " | " + message.getSender());
                    if (message.getReceiver().equals(id) && message.getSender().equals(userId)
                            || message.getReceiver().equals(userId) && message.getSender().equals(id))
                    {
                        list.add(message);
                    }
                    adapter = new MessageAdapter(MessageActivity.this , list , imgUrl);
                    recyclerView.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}