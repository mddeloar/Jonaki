package com.example.mddeloarhossain.jonaki;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FireServiceRegistrationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private Button saveButton;
    private ProgressBar progressBar;
    DatabaseReference databaseReference;
    private EditText RNameEditText, RCityEditText, RLocationEditText, RContactNoEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_service_registration);

        databaseReference = FirebaseDatabase.getInstance().getReference("fireservices");

        saveButton = findViewById(R.id.saveImageButton);
        //skipButton = findViewById(R.id.skipImageButton);
        progressBar = findViewById(R.id.progressbarId);
        //checkBox = findViewById(R.id.checkboxId);
        RNameEditText = findViewById(R.id.NameId);
        RCityEditText = findViewById(R.id.DistrictId);
        RLocationEditText = findViewById(R.id.AreaId);
        //RAgeEditText = findViewById(R.id.AgeId);
        RContactNoEditText = findViewById(R.id.ContactNumberId);

        //Rbloodgroup = getResources().getStringArray(R.array.BloodGroup);
        //Rgendergroup = getResources().getStringArray(R.array.Gender);
        //Rspinner  = findViewById(R.id.spinnerBloodGroup);

        saveButton.setOnClickListener(this);
        //skipButton.setOnClickListener(this);






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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_home) {

        } else if (id == R.id.nav_admin) {

        }
        else if (id == R.id.nav_blood) {
            Intent intent = (new Intent(FireServiceRegistrationActivity.this, BloodActivity.class));
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
        switch (view.getId()) {
            case R.id.saveImageButton:
                saveData();

                break;
        }
    }
    public void saveData(){

        try{

            final String name = RNameEditText.getText().toString();
            final String city = RCityEditText.getText().toString();
            final String location = RLocationEditText.getText().toString();
            //final String age = RAgeEditText.getText().toString();
            final String contactnumber = RContactNoEditText.getText().toString();
            //final String bloodgroup = Rspinner.getSelectedItem().toString();
            //final String gender = RGenderSpinner.getSelectedItem().toString();


            if(name.isEmpty())
            {
                RNameEditText.setError("Enter your name");
                RNameEditText.requestFocus();
                return;
            }
            if(city.isEmpty())
            {
                RCityEditText.setError("Enter your city");
                RCityEditText.requestFocus();
                return;
            }
            if(location.isEmpty())
            {
                RLocationEditText.setError("Enter your location");
                RLocationEditText.requestFocus();
                return;
            }
            if(contactnumber.isEmpty())
            {
                RContactNoEditText.setError("Enter your contact number");
                RContactNoEditText.requestFocus();
                return;
            }

            String donorname = name;


            String key = databaseReference.push().getKey();
            AdapterAmbulanceRegistration fireservice = new AdapterAmbulanceRegistration(name, city, location, contactnumber);
            databaseReference.child(key).setValue(fireservice);
            //int i=databaseReference.child(key).setValue(student);
            Toast.makeText(getApplicationContext(), "Successfully Data Stored", Toast.LENGTH_LONG).show();
            Intent intent = (new Intent(getApplicationContext(), FireServiceActivity.class));
            //putting artist name and id to intent
            /*intent.putExtra(DONOR_ID, key);
            intent.putExtra(DONOR_NAME, donorname);*/
            startActivity(intent);

        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Error: "+e, Toast.LENGTH_SHORT).show();


        }


    }

}
