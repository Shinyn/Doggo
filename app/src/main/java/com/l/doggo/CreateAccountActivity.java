package com.l.doggo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateAccountActivity extends AppCompatActivity {

    Intent intent;
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    private EditText emailView;
    private EditText passwordView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        // Initializing Auth and database
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Editable fields
        emailView = findViewById(R.id.newEmail);
        passwordView = findViewById(R.id.newPassword);
    }

    // Creates a new account with email and password and on success go to homeActivity
    private void signIn(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            intent = new Intent(CreateAccountActivity.this, HomeActivity.class);
                            intent.putExtra("email", emailView.toString());
                            startActivity(intent);
                            Toast.makeText(CreateAccountActivity.this, "YAY!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CreateAccountActivity.this, "You have to fill all fields correctly", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void createAccount(View v) {
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();

        signIn(email, password);
    }
}
