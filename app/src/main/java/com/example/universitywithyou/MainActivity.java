package com.example.universitywithyou;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            if (user.getEmail().equals("admin@gmail.com")){startActivity(new Intent(MainActivity.this,DirectorHome.class));}else {
            if (user.getEmail().equals("team@gmail.com")){startActivity(new Intent(MainActivity.this,ListeningTeamHome.class));}else {
            startActivity(new Intent(MainActivity.this,UserHome.class));}}
        }else{
            startActivity(new Intent(MainActivity.this,Login.class));
        }

        finish();
    }

}
