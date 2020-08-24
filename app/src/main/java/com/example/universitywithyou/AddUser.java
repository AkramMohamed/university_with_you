package com.example.universitywithyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class AddUser extends AppCompatActivity {
    private EditText first_name , familly_name, card_number, email , password ;
    private Button signup ;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        first_name=findViewById(R.id.first_name);
        familly_name=findViewById(R.id.family_name);
        card_number=findViewById(R.id.card_number);
        email=findViewById(R.id.std_email);
        password=findViewById(R.id.password);
        signup=findViewById(R.id.register);
        mAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e = email.getText().toString();
                String p = password.getText().toString();
                signUpUser(e,p);
            }
        });
    }

    void signUpUser (final String email , final String password){
final String speciality ="none";
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(AddUser.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AddUser.this, "auth complete !..", Toast.LENGTH_SHORT).show();

                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(new User(FirebaseAuth.getInstance().getCurrentUser().getUid()
                                            ,card_number.getText().toString(),first_name.getText().toString()
                                            ,familly_name.getText().toString(),speciality,email,password,false));
                            finish();

                        } else {
                            Toast.makeText(AddUser.this, "failed auth !..", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }
}
