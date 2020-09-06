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

import com.google.firebase.auth.FirebaseAuth;

public class ListeningTeamHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listening_team_home);
        Toolbar toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout_item :
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this,Login.class));
                finish();
                return true;

        }

        return true;
    }

    public void Posts(View view) {
        startActivity(new Intent(this,PostsManagement.class));
    }

    public void accountsList(View view) {
        startActivity(new Intent(this,AccountsList.class));
    }

    public void chat(View view) {
        startActivity(new Intent(this,ChatAccountsList.class));
    }
}
