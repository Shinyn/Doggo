package com.l.doggo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    TextView emailHeader;
    private FirebaseAuth mAuth;
    FirebaseUser user;

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


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.nav_open, R.string.nav_closed);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MapsActivity()).commit();
            navigationView.setCheckedItem(R.id.nav_mapMenu);
        }

        //

        user = FirebaseAuth.getInstance().getCurrentUser();
        //String navEmail = findViewById(R.id.nav_header_email).toString();
        //String email = user.getEmail();

        // navemail set text email borde fungera?

        // Krashar allt..
        //String daMail = getIntent().getStringExtra("email");
        //emailHeader = findViewById(R.id.nav_header_email);
        //emailHeader.setText("asd");
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
                logout();
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

    public void logout() {
        // Sätter man onclick i drawer_menu xml:en så krashar appen när man skapar konto eller loggar in
        // Kanske funkar om man gör en onClickListener?
        Toast.makeText(this, "logout method called", Toast.LENGTH_SHORT).show();
    }
}
