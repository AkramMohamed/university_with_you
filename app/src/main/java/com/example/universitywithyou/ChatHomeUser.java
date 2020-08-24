package com.example.universitywithyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatHomeUser extends AppCompatActivity {
DatabaseReference myRef ;
String user_id ;
Message message ;
TextView last_message , last_message_time ;
ImageView notify ;
List<Message> list;
    ColorStateList oldColors;
    String show_text ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_home_user);
        last_message = findViewById(R.id.last_message_user);
        last_message_time=findViewById(R.id.last_message_time_user);
        notify = findViewById(R.id.team_message_notif);
        user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        oldColors =  last_message.getTextColors();
        setLastMessage();
    }
    private void setLastMessage(){
        myRef = FirebaseDatabase.getInstance().getReference();
        list = new ArrayList<Message>();
        myRef.child("Messages").child("room:"+user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    message = dataSnapshot1.getValue(Message.class);
                    list.add(message);
                    show_text="";
                    if(list.get(list.size()-1).getMessage_text().length()>16){
                        char[] charArray = list.get(list.size()-1).getMessage_text().toCharArray();
                        for (int i=0;i<20;i++){show_text=show_text+charArray[i];}
                        last_message.setText(show_text+"...");
                    }else { last_message.setText(list.get(list.size()-1).getMessage_text());}

                    last_message_time.setText(list.get(list.size()-1).getTime());
                    if (message.getSender().equals("admin")){
                    if (! list.get(list.size()-1).getSeen()){
                        last_message.setTypeface(null, Typeface.BOLD);
                        last_message.setTextColor(Color.parseColor("#FF000000"));
                        notify.setVisibility(View.VISIBLE);
                    }else {
                        last_message.setTypeface(null, Typeface.NORMAL);
                        last_message.setTextColor(oldColors);
                        notify.setVisibility(View.INVISIBLE);
                    }}
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    public void teamChat(View view) {
        startActivity(new Intent(this,ChatRoom.class));
    }

}
