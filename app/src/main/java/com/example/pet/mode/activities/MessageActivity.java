package com.example.pet.mode.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pet.R;
import com.example.pet.mode.adapters.MessageAdapter;
import com.example.pet.mode.models.Message;
import com.example.pet.mode.models.User;
import com.example.pet.mode.models.UserChat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class MessageActivity extends AppCompatActivity {
    private UserChat userChat ;
    ImageView imgBack , imgAvatar , imgSend ;
    TextView txtNickName;
    EditText edSendMessage ;
    MessageAdapter adapter ;
    private ArrayList<Message> list ;
    private String imgUrl;
    private DatabaseReference reference;
    private RecyclerView recyclerView ;
    private SharedPreferences sharedPreferences;
    private String token;
    FirebaseUser userid;
    private int listMessage;
    ValueEventListener seenListener ;
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
        sharedPreferences = getApplicationContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "1");
        Intent intentGet = getIntent();
        Bundle bundle = intentGet.getBundleExtra("data");
        if (bundle!=null)
        {
            userChat = (UserChat) bundle.getSerializable("FriendChat");
//            //Toast.makeText(OrderPageActivity.this , "" + foodMode.getTitle(), Toast.LENGTH_SHORT).show();
            Uri uri = Uri.parse(userChat.getImage().toString().trim());
            imgUrl  = userChat.getImage().toString().trim();
            Glide.with(getApplicationContext()).load(uri).dontAnimate().centerCrop().into(imgAvatar);
        //    Log.e("Log", "Sender " + userChat.getTokenSender() + " | " + "Receiver "+ userChat.getId());
            //  Toast.makeText(getApplicationContext(), uri.toString() , Toast.LENGTH_SHORT).show();
            txtNickName.setText(userChat.getName());
            //    Log.e("ListCart" , "Oncreate :" + foodMode.getId());
        }
        Log.e("log",userChat.getTokenSender() + "|" + userChat.getId()  );


        readMessage(userChat.getTokenSender(), userChat.getId(), imgUrl);
        seenMessage(userChat.getTokenSender());

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
        //                Log.e("Click", "Click");

        edSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                                Log.e("Click", "Click");
                 checkKeyBroard(listMessage);
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
        hashMap.put("status", "Sent");
        Log.e("seen", "VAO Sent");
        reference.child("Chats").push().setValue(hashMap);
    }
    private void seenMessage (final String iDReceiver)
    {
        Log.e("seen", "VAO");
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        seenListener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Message message = dataSnapshot.getValue(Message.class);
                    // token là người gửi.
                    Log.e("L","Receiver"+ message.getReceiver() + " | sender " + message.getSender() + " | " + message.getStatus()+ "token" + token );
                      if(message.getReceiver().equals(iDReceiver))
                      {
                          HashMap<String , Object> hashMap = new HashMap<>();
                          Log.e("seen", "VAO Seen");
                          hashMap.put("status", "Received");
                          dataSnapshot.getRef().updateChildren(hashMap);
                      }
                }
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
    }
    private void readMessage(String id ,String userId, String imgUrl)
    {
        list = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Message message = dataSnapshot.getValue(Message.class);
                    Log.e("ReadMessage", message.getStatus());
                    if (message.getReceiver().equals(id) && message.getSender().equals(userId)
                            || message.getReceiver().equals(userId) && message.getSender().equals(id))
                    {
                        list.add(message);
                    }
                    adapter = new MessageAdapter(MessageActivity.this , list , imgUrl);
                    listMessage = list.size()-1;
                    recyclerView.scrollToPosition(listMessage);
                    recyclerView.setAdapter(adapter);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void status (String status)
    {
        reference = FirebaseDatabase.getInstance().getReference("Users").child(token);
        HashMap<String , Object> hashMap = new HashMap<>();
        hashMap.put("status", status);
        reference.updateChildren(hashMap);
    }
    private void checkKeyBroard(int size)
    {
        final View view = findViewById(R.id.RelativeLayoutMessage);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                view.getWindowVisibleDisplayFrame(r);
                int heightDiff = view.getRootView().getHeight() - r.height();
                if (heightDiff > 0.25*view.getRootView().getHeight())
                {
                    if(size>0)
                    {
                        recyclerView.scrollToPosition(size);
                        view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        reference.removeEventListener(seenListener);
        status("offline");
    }

    @Override
    protected void onResume() {
        super.onResume();
        status("online");
    }

}