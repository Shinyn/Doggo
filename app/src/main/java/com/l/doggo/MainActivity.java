package com.l.doggo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Intent intent;
    Intent intent2;
    Intent intent3;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // if user is signed in -> go to HomeActivity
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            intent3 = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }


        // Byter vy till "login"
        intent = new Intent(this, LoginActivity.class);
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });


        // byter vy till "skapa konto"
        intent2 = new Intent(this, CreateAccountActivity.class);
        Button createAccountButton = findViewById(R.id.createAccountButton);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent2);
            }
        });



    }
}
