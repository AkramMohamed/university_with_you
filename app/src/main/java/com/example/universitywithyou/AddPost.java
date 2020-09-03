package com.example.universitywithyou;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddPost extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {
EditText post_text ;
Button put_post ;
String post_id ;
DatabaseReference myRef  ;
String ref ;
Spinner spinner ;
ImageView posetPicture;
TextView poster ;
Boolean byDirector ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        byDirector = false;
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.directedTo,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        imageView = findViewById(R.id.post_img_trying);
        relativeLayout_img = findViewById(R.id.post_img);

        ref=FirebaseDatabase.getInstance().getReference().push().getKey();
        myRef = FirebaseDatabase.getInstance().getReference().child("Posts").child(ref);

        post_text = findViewById(R.id.post_text_add);
        put_post = findViewById(R.id.add_post);

        post_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")){
                    put_post.setEnabled(false);
                }else {put_post.setEnabled(true);}
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        posetPicture = findViewById(R.id.posterPicture);
        poster = findViewById(R.id.poster);
        if (FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("admin@gmail.com")){
            posetPicture.setImageResource(R.drawable.director);
            poster.setText("Director");
            byDirector=true;
        }

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
            put_post.setEnabled(true);
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

    public void addPost(View view) {
        Calendar calendar = Calendar.getInstance();
        final String  time = new SimpleDateFormat("hh:mm dd-MMM-yyyy").format(calendar.getTime());

            if (mImageUri!=null){
                storageReference = FirebaseStorage.getInstance().getReference().child("Posts").child(ref)
                        .child(System.currentTimeMillis()+"."+getFileExtention(mImageUri));
                storageReference.putFile(mImageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        final String downloadUrl = uri.toString();
                                        myRef.setValue(new Post(ref,"",post_text.getText().toString().trim(),downloadUrl,directedTo,time,0,0,byDirector));
                                        relativeLayout_img.setVisibility(View.GONE);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(AddPost.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddPost.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                mImageUri = null ;
                relativeLayout_img.setVisibility(View.GONE);
                finish();

            }else {
                if (! post_text.getText().toString().equals("")){
                    myRef.setValue(new Post(ref,""
                            ,post_text.getText().toString().trim(),"none",directedTo,time,0,0,byDirector));
                    relativeLayout_img.setVisibility(View.GONE);
                    finish();}
            }


    }
String directedTo;
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        directedTo = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void cancel(View view) {
        finish();
    }
}
