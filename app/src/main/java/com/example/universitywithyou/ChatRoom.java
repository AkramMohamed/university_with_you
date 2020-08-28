package com.example.universitywithyou;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

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

    FirebaseUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        imageView = findViewById(R.id.message_img_trying);
        relativeLayout_img = findViewById(R.id.message_img);


        user= FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            if (user.getEmail().equals("team@gmail.com")){
                user_id = getIntent().getStringExtra("user_id");
                sender = "team";}else {
                user_id = user.getUid();
                sender = "Student";
            }
        }else {

            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this,Login.class));
            finish();

        }

        setMessagesList();

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
    EditText message_text;
    String message_textt ;


    public void send(View view) {
        message_text = findViewById(R.id.message_text_send);
        message_textt=message_text.getText().toString();

            Calendar calendar = Calendar.getInstance();
            final String  time = new SimpleDateFormat("hh:mm dd-MMM-yyyy").format(calendar.getTime());

            if (mImageUri!=null){

                storageReference = FirebaseStorage.getInstance().getReference("Messages")
                        .child("room:"+user_id).child(System.currentTimeMillis()+"."+getFileExtention(mImageUri));
                
                storageReference.putFile(mImageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Toast.makeText(ChatRoom.this, "wsalt !!..", Toast.LENGTH_SHORT).show();
                                final String downloadUrl = uri.toString();
                                FirebaseDatabase.getInstance().getReference().child("Messages").child("room:"+user_id).push().setValue(new Message(0,message_textt
                                        ,downloadUrl,sender,time,false));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(ChatRoom.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })      .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ChatRoom.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                mImageUri = null ;
            }else {
                if (! message_text.getText().toString().equals("")){
        FirebaseDatabase.getInstance().getReference().child("Messages").child("room:"+user_id).push().setValue(new Message(0,message_text.getText().toString()
                ,"none",sender,time,false));   }
        }

        message_text.setText("");
        relativeLayout_img.setVisibility(View.GONE);
    }

    public void uploadImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    private Uri mImageUri;
    ImageView imageView ;
    RelativeLayout relativeLayout_img ;
    private StorageReference storageReference;

    @Override

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            mImageUri= data.getData();
            Picasso.get().load(mImageUri).into(imageView);
            relativeLayout_img.setVisibility(View.VISIBLE);

        }
    }

    private String getFileExtention(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}
