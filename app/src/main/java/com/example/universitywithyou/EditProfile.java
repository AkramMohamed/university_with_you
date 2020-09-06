package com.example.universitywithyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfile extends AppCompatActivity {
    private EditText  firstname , familyname , cardnumber , speciality ;
    private TextView  email ;
    private TextView  password ;
    User user; DatabaseReference myRef ;private String user_id ;
    String oldfirstname , oldfamilyname , oldcardnumber , oldspeciality , oldemail , textUpdate ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Toolbar toolbar = findViewById(R.id.edit_profile_toolbar);
        setSupportActionBar(toolbar);

        firstname = findViewById(R.id.first_name);
        familyname = findViewById(R.id.family_name);
        cardnumber = findViewById(R.id.card_number);
        speciality = findViewById(R.id.speciality);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        setProfile();

        FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();

        if (user1.getEmail().equals("admin@gmail.com")||user1.getEmail().equals("team@gmail.com")){
            user_id = getIntent().getStringExtra("user_id") ;

        }

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

                        firstname.setText(user.getFirst_name());
                        oldfirstname=user.getFirst_name();
                        familyname.setText(user.getFamily_name());
                        oldfamilyname=user.getFamily_name();
                        cardnumber.setText(user.getCard_number());
                        oldcardnumber=user.getCard_number();
                        speciality.setText(user.getSpeciality());
                        oldspeciality=user.getSpeciality();
                        email.setText(user.getEmail());


                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(EditProfile.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getPassword(View view) {
        password.setText(user.getPassword());
        findViewById(R.id.getPassword).setVisibility(View.GONE);
        findViewById(R.id.copyPassword).setVisibility(View.VISIBLE);
    }

    public void copyPassword(View view) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("EditText",password.getText().toString());
        clipboard.setPrimaryClip(clipData);

        Toast.makeText(this, "the password has been copied", Toast.LENGTH_SHORT).show();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_profile_menu,menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.confirm_update :
                textUpdate ="";
                if (!oldfirstname.equals(firstname.getText().toString())){
                    textUpdate= textUpdate+"firstname| ";
                }
                if (!oldfamilyname.equals(familyname.getText().toString())){
                    textUpdate= textUpdate+"familyname| ";
                }
                if (!oldcardnumber.equals(cardnumber.getText().toString())){
                    textUpdate= textUpdate+"cardnumber| ";
                }
                if (!oldspeciality.equals(speciality.getText().toString())){
                    textUpdate= textUpdate+"speciality| ";
                }

                if (!textUpdate.equals("")){

                    AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile.this);
                    builder.setCancelable(false);
                    builder.setMessage("Are you sure you want update "+textUpdate+" of this profile")
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (!oldfirstname.equals(firstname.getText().toString())){
                                        myRef.child("Users").child(user_id).child("first_name").setValue(firstname.getText().toString().trim());
                                    }
                                    if (!oldfamilyname.equals(familyname.getText().toString())){
                                        myRef.child("Users").child(user_id).child("family_name").setValue(familyname.getText().toString().trim());
                                    }
                                    if (!oldcardnumber.equals(cardnumber.getText().toString())){
                                        myRef.child("Users").child(user_id).child("card_number").setValue(cardnumber.getText().toString().trim());
                                    }
                                    if (!oldspeciality.equals(speciality.getText().toString())){
                                        myRef.child("Users").child(user_id).child("speciality").setValue(speciality.getText().toString().trim());
                                    }

                                    dialog.dismiss();
                                    finish();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }


                break;
        }
        return true;
    }
}
