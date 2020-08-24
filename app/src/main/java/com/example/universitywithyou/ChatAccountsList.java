package com.example.universitywithyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ChatAccountsList extends AppCompatActivity {
    ListView listView ;
    List<User> list ;
    User user ;
    DatabaseReference myRef ;
    AdapterUser adapterUser ;
    EditText text_search ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_accounts_list);
        text_search=findViewById(R.id.search);

        setAccountList();
        text_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

               searchItem(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ChatAccountsList.this,ChatRoom.class);
                intent.putExtra("user_id",list.get(position).getId_user());
                startActivity(intent);
            }
        });

    }


    private void setAccountList() {
        listView = findViewById(R.id.lv_account);
        list = new ArrayList<User>();
        user = new User();
        myRef = FirebaseDatabase.getInstance().getReference() ;
        adapterUser = new AdapterUser(this,R.layout.item_user_chat_account,list);

        myRef.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                    user = dataSnapshot1.getValue(User.class);

                    list.add(user);
                    listView.setAdapter(adapterUser);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });



    }

    public void searchItem(String text) {
        int i;
        for (i=0;i<list.size();i++){
            if (!(list.get(i).getFirst_name().toLowerCase().contains(text.trim().toLowerCase()) ||
                    list.get(i).getFamilly_name().toLowerCase().contains(text.toLowerCase().trim())) ){
                list.remove(i);
            }
        }
        adapterUser.notifyDataSetChanged();
    }

    List<Message> list_message ;
    Message message ;
    String lastMessage;

    public String getLastMessage(String user_id){
        message = new Message();
        myRef = FirebaseDatabase.getInstance().getReference();
        list_message = new ArrayList<Message>();
        lastMessage ="";

        myRef.child("Messages").child("room:"+user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    message = dataSnapshot1.getValue(Message.class);
                    list_message.add(message);
                    lastMessage =message.getMessage_text();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return lastMessage;

    }

    ImageButton search , close ;

    public void search(View view) {
        text_search.setVisibility(View.VISIBLE);
        close = findViewById(R.id.search_close_btn);
        search = findViewById(R.id.search_btn);
        search.setVisibility(View.GONE);
        close.setVisibility(View.VISIBLE);

    }

    public void close(View view) {
        text_search.setVisibility(View.GONE);
        close = findViewById(R.id.search_close_btn);
        search = findViewById(R.id.search_btn);
        search.setVisibility(View.VISIBLE);
        close.setVisibility(View.GONE);
        setAccountList();
    }
}
