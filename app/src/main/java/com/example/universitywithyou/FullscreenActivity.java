package com.example.universitywithyou;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class FullscreenActivity extends AppCompatActivity {
ImageView imageView;
String image_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        imageView = findViewById(R.id.imageFullScreen);
        if (getIntent().hasExtra("image_url")){
            image_url=getIntent().getStringExtra("image_url");
            Picasso.get().load(image_url).into(imageView);
        }
    }
}
