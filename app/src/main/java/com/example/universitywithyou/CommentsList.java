package com.example.universitywithyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CommentsList extends AppCompatActivity {

    private ListView listView ;
    private List<Comment> list ;
    private Comment comment ;
    private DatabaseReference myRef ;
    private AdapterComment adapterComment;
    String post_id;
    Post post ;
    User user ;
    String user_id , commentator ;
    EditText comment_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_list);
        myRef = FirebaseDatabase.getInstance().getReference() ;
        comment_text = findViewById(R.id.comment_text);


     //   post = (Post) getIntent().getSerializableExtra("Post");


        if(getIntent().hasExtra("Post")) {
            String  data = getIntent().getStringExtra("Post");
            post = new Gson().fromJson(data, Post.class);

            post_id = post.getId_post();
        }
        setCommentsList();




        user= new User();
        myRef.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    user = dataSnapshot.getValue(User.class);

                    user_id = user.getId_user();
                    commentator = user.getFirst_name() +" "+ user.getFamilly_name();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });


    }

    private void setCommentsList() {
        listView = findViewById(R.id.lv_comments);
        list = new ArrayList<Comment>();
        comment = new Comment();
        adapterComment = new AdapterComment(this,R.layout.item_comment,list);

        myRef.child("Comments").child("post:"+post_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                    comment = dataSnapshot1.getValue(Comment.class);

                    list.add(comment);
                    listView.setAdapter(adapterComment);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    public void addComment(View view) {
        if(!comment_text.getText().toString().equals("")){
            Calendar calendar = Calendar.getInstance();

            String time = new SimpleDateFormat("hh:mm dd-MMM-yyyy").format(calendar.getTime());
            FirebaseDatabase.getInstance().getReference().child("Comments").child("post:"+post_id).push().setValue(
                    new Comment("",user_id,post_id,comment_text.getText().toString(),time,commentator,0));
        }
        comment_text.setText("") ;
    }

    public void close(View view) {
        finish();
    }
}
