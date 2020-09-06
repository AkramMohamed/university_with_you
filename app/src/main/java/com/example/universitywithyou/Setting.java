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
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Setting extends AppCompatActivity {
    EditText current , new_password , confirm ;
    Button reset ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        current = findViewById(R.id.current_password);
        new_password = findViewById(R.id.new_password);
        confirm= findViewById(R.id.confirm_password);
        reset = findViewById(R.id.button3);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword(current.getText().toString(),new_password.getText().toString(),confirm.getText().toString());
            }
        });

    }

    void  resetPassword(String current , final String neww , final String confirm){

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        AuthCredential credential = EmailAuthProvider
                .getCredential(user.getEmail(), current);

        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (neww.equals(confirm) ){
                            user.updatePassword(neww)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(Setting.this, "done !..", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }else {
                            Toast.makeText(Setting.this, "confirm password is nit the same !..", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
