package com.example.mddeloarhossain.jonaki;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    private CardView JonakiCard, BloodCard, TutionCard, AmbulanceCard, FireServiceCard, ContributorCard, OrganizationCard;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();


        JonakiCard = findViewById(R.id.JonakiCardViewId);
        BloodCard = findViewById(R.id.BloodCardViewId);
        TutionCard = findViewById(R.id.TutionCardViewId);
        AmbulanceCard = findViewById(R.id.AmbulanceCardViewId);
        FireServiceCard = findViewById(R.id.FireServiceCardViewId);
        ContributorCard = findViewById(R.id.ContributorCardViewId);
        OrganizationCard = findViewById(R.id.OrganizationCardViewId);

        JonakiCard.setOnClickListener(this);
        BloodCard.setOnClickListener(this);
        TutionCard.setOnClickListener(this);
        AmbulanceCard.setOnClickListener(this);
        FireServiceCard.setOnClickListener(this);
        ContributorCard.setOnClickListener(this);
        OrganizationCard.setOnClickListener(this);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //updateNaHeader();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        //item.setEnabled(true);

        if (id == R.id.nav_home) {
            Intent intent = (new Intent(this, MainActivity.class));
            startActivity(intent);

        } else if (id == R.id.nav_admin) {
            Intent intent = (new Intent(this, SignUpActivity.class));
            startActivity(intent);

        }
        else if (id == R.id.nav_blood) {
            Intent intent = (new Intent(this, BloodActivity.class));
            startActivity(intent);

        }
        else if (id == R.id.nav_cordinator) {
            Intent intent = (new Intent(this, MainActivity.class));
            startActivity(intent);

        }
        else if (id == R.id.nav_tution) {
            Intent intent = (new Intent(this, MainActivity.class));
            startActivity(intent);

        }
        else if (id == R.id.nav_ambulance) {
            Intent intent = (new Intent(this, AmbulanceActivity.class));
            startActivity(intent);

        }
        else if (id == R.id.nav_fire_service) {
            Intent intent = (new Intent(this, FireServiceActivity.class));
            startActivity(intent);

        }
        else if (id == R.id.nav_organization) {
            Intent intent = (new Intent(this, MainActivity.class));
            startActivity(intent);

        }else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {

        Intent intent;
        switch (view.getId()){
            case R.id.JonakiCardViewId:
                intent = (new Intent(getApplicationContext(), MainActivity.class));
                startActivity(intent);
                break;
            case R.id.BloodCardViewId:
                intent = (new Intent(getApplicationContext(), BloodActivity.class));
                startActivity(intent);
                break;
            case R.id.TutionCardViewId:
                intent = (new Intent(getApplicationContext(), BloodActivity.class));
                startActivity(intent);
                break;
            case R.id.AmbulanceCardViewId:
                intent = (new Intent(getApplicationContext(), AmbulanceActivity.class));
                startActivity(intent);
                break;
            case R.id.FireServiceCardViewId:
                intent = (new Intent(getApplicationContext(), FireServiceActivity.class));
                startActivity(intent);
                break;
            case R.id.ContributorCardViewId:
                intent = (new Intent(getApplicationContext(), BloodActivity.class));
                startActivity(intent);
                break;
            case R.id.OrganizationCardViewId:
                intent = (new Intent(getApplicationContext(), BloodActivity.class));
                startActivity(intent);
                break;

        }

    }
   /* public void updateNaHeader(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUserName = headerView.findViewById(R.id.nav_user_name);
        TextView navUserMail = headerView.findViewById(R.id.nav_user_email);
        ImageView navUserPhoto = headerView.findViewById(R.id.nav_user_photo);


        navUserName.setText(currentUser.getDisplayName());
        navUserMail.setText(currentUser.getEmail());
        Picasso.with(this).load(currentUser.getPhotoUrl()).into(navUserPhoto);



    }*/
}
