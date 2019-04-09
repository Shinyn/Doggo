package com.l.doggo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    TextView emailHeader;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer);
        NavigationView navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);
        intent = new Intent(this, MainActivity.class);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.nav_open, R.string.nav_closed);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MapsActivity()).commit();
            navigationView.setCheckedItem(R.id.nav_mapMenu);
        }

        //

        // Hämtar den inloggade användarens email och sätter TextViewn till den mailen.
        user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();

        View header = navigationView.getHeaderView(0);
        TextView navEmail = header.findViewById(R.id.nav_header_email);

        if (navEmail == null)
            Log.d("!!!", "onCreate: view is null ");
        else
            navEmail.setText(email);
            // Sätt också beskrivning, mobNr och användarnamn vid inloggning (om det finns)
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_mapMenu:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MapsActivity()).commit();
                break;
            case R.id.nav_petsMenu:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PetsFragment()).commit();
                break;
            case R.id.nav_addPetMenu:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AddPetFragment()).commit();
                break;
            case R.id.nav_profileMenu:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
                break;
            case R.id.nav_signOutMenu:
                mAuth.signOut();
                startActivity(intent);
        }
        drawerLayout.closeDrawers();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}
