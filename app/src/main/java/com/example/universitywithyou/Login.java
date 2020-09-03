package com.example.universitywithyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    Button student_login , director_login , listening_login ;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();


        director_login = findViewById(R.id.directorLogin);
        director_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { DirectorLoginDialog(); }
        });

        student_login = findViewById(R.id.studentLogin);
        student_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            StudentLoginDialog();
            }});

        listening_login = findViewById(R.id.listeningTeamLogin);
        listening_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { TeamLoginDialog(); }});

    }

    private void StudentLoginDialog() {
        final Dialog myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.student_login_dialog);
        myDialog.setCancelable(false);
        myDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Button login = myDialog.findViewById(R.id.studentLoginBtn);
        ImageButton close = myDialog.findViewById(R.id.close);

        final EditText email = myDialog.findViewById(R.id.std_email);
        final EditText password = myDialog.findViewById(R.id.std_password);
        myDialog.show();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String e = email.getText().toString();
                String p = password.getText().toString();
                if (e.equals("admin@gmail.com")||e.equals("team@gmail.com")){
                    Toast.makeText(Login.this, "this email is booked up !..", Toast.LENGTH_SHORT).show();
                }else {
                logInUser(e,p);}
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });
    }
    private void DirectorLoginDialog() {
        final Dialog myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.director_login_dialog);
        myDialog.setCancelable(false);
        myDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Button login = myDialog.findViewById(R.id.directorLoginBtn);
        ImageButton close = myDialog.findViewById(R.id.close);


        final EditText password = myDialog.findViewById(R.id.DirPassword);
        myDialog.show();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String p = password.getText().toString();
                logInAdmin("admin@gmail.com",p);

            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });
    }
    private void TeamLoginDialog() {
        final Dialog myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.team_login_dialog);
        myDialog.setCancelable(false);
        myDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Button login = myDialog.findViewById(R.id.teacherLoginBtn);
        ImageButton close = myDialog.findViewById(R.id.close);


        final EditText password = myDialog.findViewById(R.id.teamPassword);
        myDialog.show();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String p = password.getText().toString();
                logInTeam("team@gmail.com",p);

            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });
    }


    void logInUser (final String email , final String password){

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            updateUI(null);
                        }
                    }
                });
    }
    public void  updateUI(FirebaseUser account){
        if(account != null){
            Toast.makeText(this,"U Signed In successfully",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,UserHome.class));
        }else {
            Toast.makeText(this,"U Didn't signed in",Toast.LENGTH_LONG).show();
        }
    }


    void logInAdmin (final String email , final String password){

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUIAdmin(user);
                        } else {
                            updateUIAdmin(null);
                        }
                    }
                });
    }
    public void  updateUIAdmin(FirebaseUser account){
        if(account != null){
            Toast.makeText(this,"U Signed In successfully",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,DirectorHome.class));
        }else {
            Toast.makeText(this,"U Didn't signed in",Toast.LENGTH_LONG).show();
        }
    }


    void logInTeam (final String email , final String password){

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUITeam(user);
                        } else {
                            updateUITeam(null);
                        }
                    }
                });
    }
    public void  updateUITeam(FirebaseUser account){
        if(account != null){
            Toast.makeText(this,"U Signed In successfully",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,ListeningTeamHome.class));
        }else {
            Toast.makeText(this,"U Didn't signed in",Toast.LENGTH_LONG).show();
        }
    }
}
