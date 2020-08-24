package com.example.universitywithyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChatRoom extends AppCompatActivity {
    ListView listView ;
    List<Message> list ;
    Message message ;
    DatabaseReference myRef ;
    AdapeterMessage adapeterMessage;
    String user_id ,sender;
    EditText message_text;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        message_text=findViewById(R.id.message_text_send);


        user= FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            if (user.getEmail().equals("team@gmail.com")){
                user_id = getIntent().getStringExtra("user_id");
                sender = "admin";}else {
                user_id = user.getUid();
                sender = "Student";
            }
        }else {

            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this,Login.class));
            finish();

        }

        setMessagesList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setVisibility(View.INVISIBLE);
            }
        });
    }
    private void setMessagesList() {
        listView = findViewById(R.id.lv_messages);
        list = new ArrayList<Message>();
        myRef = FirebaseDatabase.getInstance().getReference() ;
        adapeterMessage = new AdapeterMessage(this,R.layout.item_right_message,R.layout.item_left_message,list);
        message = new Message();

        myRef.child("Messages").child("room:"+user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                    message = dataSnapshot1.getValue(Message.class);
                    if(user.getEmail().equals("team@gmail.com")){
                    if(!message.getSender().equals("admin")){
                    dataSnapshot1.getRef().child("seen").setValue(true);}}
                    else {if(message.getSender().equals("admin")){
                        dataSnapshot1.getRef().child("seen").setValue(true);}}
                    list.add(message);
                    listView.setAdapter(adapeterMessage);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    public void send(View view) {
        if (! message_text.getText().toString().equals("")){
            Calendar calendar = Calendar.getInstance();

            String time = new SimpleDateFormat("hh:mm dd-MMM-yyyy").format(calendar.getTime());
        FirebaseDatabase.getInstance().getReference("Messages").child("room:"+user_id).push().setValue(new Message(0,message_text.getText().toString(),null
        ,sender,time,false));}
        message_text.setText("");
    }
}
