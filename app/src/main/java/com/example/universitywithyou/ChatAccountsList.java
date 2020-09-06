package com.example.universitywithyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_accounts_list);

        Toolbar toolbar = findViewById(R.id.chat_accounts_list_toolbar);
        setSupportActionBar(toolbar);

        setAccountList();

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

                    if (!user.getEmail().equals("admin@gmail.com")&&!user.getEmail().equals("team@gmail.com")){
                        list.add(user);
                    }
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
                    list.get(i).getFamily_name().toLowerCase().contains(text.toLowerCase().trim())) ){
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


int before = 0;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.accounts_menu,menu);
       MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        final SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search here!..");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > before){
                    before =newText.length();
                }else {
                    setAccountList();
                    searchItem(newText);
                    before =newText.length();
                }
                if (newText.equals("")){setAccountList();}
                else {searchItem(newText);}
                return true;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                setAccountList();
                return false;
            }
        });
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()){

            case R.id.logout_action:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ChatAccountsList.this,Login.class));
                finish();

            break;

        }

        return true;
    }
}
