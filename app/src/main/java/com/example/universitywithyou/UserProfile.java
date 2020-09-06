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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity {
private TextView deleg, fullname , firstname , familyname , cardnumber , speciality , email ;
User user; DatabaseReference myRef ;private String user_id ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = findViewById(R.id.user_profile_toolbar);
        setSupportActionBar(toolbar);

        deleg = findViewById(R.id.deleg);
        fullname = findViewById(R.id.full_name);
        firstname = findViewById(R.id.first_name);
        familyname = findViewById(R.id.family_name);
        cardnumber = findViewById(R.id.card_number);
        speciality = findViewById(R.id.speciality);
        email = findViewById(R.id.email );

        if (FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("admin@gmail.com")
                ||FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("team@gmail.com")){
            user_id = getIntent().getStringExtra("user_id");
        }else  {user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();}

        ImageView imageView1 = findViewById(R.id.profile_picture);
        setProfile();
    }

    private void setProfile() {

        user = new User();
        myRef = FirebaseDatabase.getInstance().getReference() ;


        myRef.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    user = dataSnapshot1.getValue(User.class);
                    if (user.getId_user().equals(user_id)) {
                        fullname.setText(user.getFirst_name() + " " + user.getFamily_name());
                        firstname.setText(user.getFirst_name());
                        familyname.setText(user.getFamily_name());
                        cardnumber.setText(user.getCard_number());
                        speciality.setText(user.getSpeciality());
                        email.setText(user.getEmail());
                        if (user.getDeleg()){
                            deleg.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UserProfile.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit_profile :
                Intent intent = new Intent(UserProfile.this,EditProfile.class);
                intent.putExtra("user_id",user_id);
                startActivity(intent);
            break;

        }

        return true;
    }
}
