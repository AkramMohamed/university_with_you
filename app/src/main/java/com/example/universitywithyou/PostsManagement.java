package com.example.universitywithyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PostsManagement extends AppCompatActivity {
    private ListView listView ;
    private List<Post> list ;
    private Post post ;
    private DatabaseReference myRef ;
    private AdapterPost adapterPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_management);
        setPostList();
    }
    private void setPostList() {
        listView = findViewById(R.id.lv_posts);
        list = new ArrayList<Post>();
        post = new Post();
        myRef = FirebaseDatabase.getInstance().getReference() ;
        adapterPost = new AdapterPost(this,R.layout.item_post,list);

        myRef.child("Posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                    post = dataSnapshot1.getValue(Post.class);

                    list.add(post);
                    listView.setAdapter(adapterPost);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    public void addPost(View view) {
        Intent intent = new Intent(this,AddPost.class);
        if (list.isEmpty()){intent.putExtra("post_id",1);}
        else {intent.putExtra("post_id",list.get(list.size()-1).getId_post()+1);}
        startActivity(intent);
    }
}
