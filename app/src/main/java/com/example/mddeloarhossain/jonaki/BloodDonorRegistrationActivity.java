package com.example.mddeloarhossain.jonaki;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.hitomi.cmlibrary.OnMenuStatusChangeListener;
import com.squareup.picasso.Picasso;

public class BloodDonorRegistrationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Button saveButton, skipButton;
    private ProgressBar progressBar;
    DatabaseReference databaseReference;
    private EditText RNameEditText, RCityEditText, RLocationEditText, RAgeEditText, RContactNoEditText;
    String[] Rbloodgroup, Rgendergroup;
    private Spinner Rspinner, RGenderSpinner;
    Spinner spinnerGenre;
    private CheckBox checkBox;

    //we will use these constants later to pass the artist name and id to another activity
    public static final String DONOR_NAME = "com.example.mddeloarhossain.bloodbankm.donorname";
    public static final String DONOR_ID = "com.example.mddeloarhossain.bloodbankm.donorid";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_donor_registration);


            databaseReference = FirebaseDatabase.getInstance().getReference("donors");

            saveButton = findViewById(R.id.saveImageButton);
            skipButton = findViewById(R.id.skipImageButton);
            progressBar = findViewById(R.id.progressbarId);
            checkBox = findViewById(R.id.checkboxId);
            RNameEditText = findViewById(R.id.NameId);
            RCityEditText = findViewById(R.id.DistrictId);
            RLocationEditText = findViewById(R.id.AreaId);
            RAgeEditText = findViewById(R.id.AgeId);
            RContactNoEditText = findViewById(R.id.ContactNumberId);

            Rbloodgroup = getResources().getStringArray(R.array.BloodGroup);
            Rgendergroup = getResources().getStringArray(R.array.Gender);
            Rspinner  = findViewById(R.id.spinnerBloodGroup);

        saveButton.setOnClickListener(this);
        skipButton.setOnClickListener(this);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_sample_layout,R.id.spinnerampleId,Rbloodgroup);
            Rspinner.setAdapter(adapter);
            RGenderSpinner =  findViewById(R.id.spinnerGender);
            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,R.layout.spinner_sample_layout,R.id.spinnerampleId,Rgendergroup);
            RGenderSpinner.setAdapter(adapter1);




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
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.saveImageButton:
                saveData();

                break;
            case R.id.skipImageButton:
                Intent intent = (new Intent(getApplicationContext(), BloodDonorUploadImage.class));
                startActivity(intent);

                break;

        }



    }

    public void saveData(){

        try{

            final String name = RNameEditText.getText().toString();
            final String city = RCityEditText.getText().toString();
            final String location = RLocationEditText.getText().toString();
            final String age = RAgeEditText.getText().toString();
            final String contactnumber = RContactNoEditText.getText().toString();
            final String bloodgroup = Rspinner.getSelectedItem().toString();
            final String gender = RGenderSpinner.getSelectedItem().toString();


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
            if(age.isEmpty())
            {
                RAgeEditText.setError("Enter your age");
                RAgeEditText.requestFocus();
                return;
            }
            if(contactnumber.isEmpty())
            {
                RContactNoEditText.setError("Enter your contact number");
                RContactNoEditText.requestFocus();
                return;
            }

            if(bloodgroup.equals("Blood Group"))
            {
                Toast.makeText(getApplicationContext(), "Blood Group is Needed.", Toast.LENGTH_LONG).show();
                return;
            }
            if(gender.equals("Gender"))
            {
                Toast.makeText(getApplicationContext(), "Gender is Needed.", Toast.LENGTH_LONG).show();
                return;
            }

            //long rowId = mydatabaseHelper.InsertData(name, city, location, bloodgroup, gender, birthdate, contactnumber, email, password, inTheList);

            //final String key = databaseReference.push().getKey();

            final String donationstatus;
            if(checkBox.isChecked()){
                donationstatus = "Ready";
            }else {
                donationstatus = "Can't";
            }
            String donorname = name;


            String key = databaseReference.push().getKey();
            AdapterBloodDonorRegistration donor = new AdapterBloodDonorRegistration(name, city, location, age, contactnumber, bloodgroup, gender, donationstatus);
            databaseReference.child(key).setValue(donor);
            //int i=databaseReference.child(key).setValue(student);
            Toast.makeText(getApplicationContext(), "Successfully Data Stored", Toast.LENGTH_LONG).show();
            Intent intent = (new Intent(getApplicationContext(), BloodDonorUploadImage.class));
            //putting artist name and id to intent
            intent.putExtra(DONOR_ID, key);
            intent.putExtra(DONOR_NAME, donorname);
            startActivity(intent);

        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Please enter your information.", Toast.LENGTH_SHORT).show();


        }


    }
}
