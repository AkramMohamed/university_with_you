package com.example.universitywithyou;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final ImageView imageView = findViewById(R.id.uc2logo);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (user != null) {
                    if (user.getEmail().equals("admin@gmail.com")){startActivity(new Intent(MainActivity.this,DirectorHome.class));}else {
                        if (user.getEmail().equals("team@gmail.com")){startActivity(new Intent(MainActivity.this,ListeningTeamHome.class));}else {
                            startActivity(new Intent(MainActivity.this,UserHome.class));}}
                }else{
                    Intent intent =new Intent(MainActivity.this,Login.class);
                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,imageView,ViewCompat.getTransitionName(imageView));

                    startActivity(intent,optionsCompat.toBundle());
                }

                finish();
            }
        },3000);

    }

}
