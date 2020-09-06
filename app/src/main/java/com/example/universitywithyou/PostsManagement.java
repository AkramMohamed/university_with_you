package com.example.universitywithyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PostsManagement extends AppCompatActivity {
    private ListView listView ;
    private List<Post> list ;
    private Post post ;
    private DatabaseReference myRef ;
    private AdapterPost adapterPost;

    String email ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_management);
        setPostList();

        Toolbar toolbar = findViewById(R.id.posts_toolbar);
        setSupportActionBar(toolbar);
        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

    }
    private void setPostList() {
        listView = findViewById(R.id.lv_posts);
        list = new ArrayList<Post>();
        post = new Post();
        myRef = FirebaseDatabase.getInstance().getReference() ;
        adapterPost = new AdapterPost(this,R.layout.item_post,list);

        myRef.child("Posts").orderByChild("sort").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                    post = dataSnapshot1.getValue(Post.class);

                    list.add(post);

                }

                listView.setAdapter(adapterPost);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.posts_menu,menu);
        return true;

    }
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        MenuItem add = menu.findItem(R.id.add_post);
        if (!(email.equals("admin@gmail.com")||email.equals("team@gmail.com"))){
            add.setVisible(false);
        }
        else
        {
            add.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()){
            case R.id.add_post :
                Intent intent = new Intent(this,AddPost.class);
                intent.putExtra("sort",0-list.size());
                startActivity(intent);

            break;

            case R.id.logout_action:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(PostsManagement.this,Login.class));
                finish();

            break;

        }

        return true;
    }

}
