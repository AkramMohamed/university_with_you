package com.example.universitywithyou;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddPost extends AppCompatActivity {
EditText post_text ;
Button put_post ;
int post_id ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        post_text = findViewById(R.id.post_text_add);
        put_post = findViewById(R.id.put_post);
        post_id = getIntent().getIntExtra("post_id",0);
        put_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (! post_text.getText().toString().equals("")){
                    Calendar calendar = Calendar.getInstance();

                    String time = new SimpleDateFormat("hh:mm dd-MMM-yyyy").format(calendar.getTime());
                FirebaseDatabase.getInstance().getReference().child("Posts").push().setValue(new Post(post_id,""
                        ,post_text.getText().toString().trim(),"","",time,0,0));

                finish();}
            }
        });
    }

}
