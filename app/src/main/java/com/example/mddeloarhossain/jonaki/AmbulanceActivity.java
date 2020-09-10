package com.example.mddeloarhossain.jonaki;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class AmbulanceActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private  AdapterAmbulanceShow imageAdapter;
    private List<AdapterAmbulanceRegistration> uploadList;
    DatabaseReference databaseReference;
    //private ProgressBar progressBar;
    private FirebaseStorage firebaseStorage;
    public static final String AMBULANCE_ID = "com.example.mddeloarhossain.bloodbankm.ambulanceid";



    //************ YOU NEED TO DELETE THIS< THIS IS A BAD IDEA************************//
    public static final String AMBULANCE_NAME = "com.example.mddeloarhossain.bloodbankm.ambulancename";
    public static final String AMBULANCE_CITY = "com.example.mddeloarhossain.bloodbankm.ambulancecity";
    public static final String AMBULANCE_LOCATION = "com.example.mddeloarhossain.bloodbankm.ambulancelocation";
    public static final String AMBULANCE_CONTACTNO = "com.example.mddeloarhossain.bloodbankm.ambulancecontactno";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance);


        recyclerView = findViewById(R.id.recyclerViewId);
        //progressBar = findViewById(R.id.RecyclerprogressbarId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        uploadList = new ArrayList<>();

        firebaseStorage = FirebaseStorage.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("ambulances");
        //databaseReference = FirebaseDatabase.getInstance().getReference("donorsimages");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {


                uploadList.clear();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    AdapterAmbulanceRegistration upload = dataSnapshot1.getValue(AdapterAmbulanceRegistration.class);
                    upload.setKey(dataSnapshot1.getKey());
                    uploadList.add(upload);

                }
                imageAdapter = new AdapterAmbulanceShow(AmbulanceActivity.this, uploadList);
                recyclerView.setAdapter(imageAdapter);

                imageAdapter.setOnItemClickListener(new AdapterAmbulanceShow.OnItemClickListener() {
                    @Override
                    public void OnItemClick(int position) {
                        //String text = uploadList.get(position).getImageName();
                        AdapterAmbulanceRegistration selectedItem = uploadList.get(position);
                        final String key = selectedItem.getKey();
                        final String name = selectedItem.getName();
                        final String city = selectedItem.getCity();
                        final String location = selectedItem.getLocation();
                        //final String email = selectedItem.get();
                        final String contactno = selectedItem.getContactnumber();
                        /*final String gender = selectedItem.getGender();
                        final String bloodgroup = selectedItem.getBloodgroup();
                        final String age = selectedItem.getAge();
                        final String donationstatus = selectedItem.getDonationstatus();
                        final String imageurl = selectedItem.getImageUrl();*/

                        Toast.makeText(getApplicationContext(),name+" is selected : "+position,Toast.LENGTH_SHORT).show();
                        Intent intent = (new Intent(getApplicationContext(), AmbulanceDetailsActivity.class));
                        Toast.makeText(getApplicationContext(), "Welcome to your profile", Toast.LENGTH_SHORT).show();
                        intent.putExtra(AMBULANCE_ID, key);
                        intent.putExtra(AMBULANCE_NAME, name);
                        intent.putExtra(AMBULANCE_CITY, city);
                        intent.putExtra(AMBULANCE_LOCATION, location);
                        intent.putExtra(AMBULANCE_CONTACTNO, contactno);
                        /*intent.putExtra(DONOR_AGE, age);
                        intent.putExtra(DONOR_BLOODGROUP, bloodgroup);
                        intent.putExtra(DONOR_GENDER, gender);
                        //intent.putExtra(DONOR_EMAIL, key);
                        intent.putExtra(DONOR_DONATIONSTATUS, donationstatus);
                        intent.putExtra(DONOR_IMAGEURL, imageurl);*/

                        startActivity(intent);

                    }

                    @Override
                    public void onDoAnyTask(int position) {
                        Toast.makeText(getApplicationContext(),"onDoAnyTask is selected.",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onDelete(int position) {
                        AdapterAmbulanceRegistration selectedItem = uploadList.get(position);
                        final String key = selectedItem.getKey();
                        databaseReference.child(key).removeValue();

                        Toast.makeText(getApplicationContext(),"Data is deleted successfully.",Toast.LENGTH_SHORT).show();


                       /* StorageReference storageReference = firebaseStorage.getReferenceFromUrl(selectedItem.getImageUrl());
                        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                databaseReference.child(key).removeValue();

                                Toast.makeText(getApplicationContext(),"Image is deleted successfully.",Toast.LENGTH_SHORT).show();

                            }
                        });*/

                        //Toast.makeText(getApplicationContext(),"onDelete is selected.",Toast.LENGTH_SHORT).show();

                    }
                });


                //progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(),"Error: "+databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                //progressBar.setVisibility(View.INVISIBLE);
            }
        });





        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = (new Intent(getApplicationContext(), AmbulanceRegistrationActivity.class));
                startActivity(intent);
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
        getMenuInflater().inflate(R.menu.main_with_search, menu);

        MenuItem menuItem = menu.findItem(R.id.SerchViewId);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                imageAdapter.getFilter().filter(s);
                return false;
            }
        });


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

        if (id == R.id.nav_home) {
            Intent intent = (new Intent(this, BloodActivity.class));
            startActivity(intent);

        } else if (id == R.id.nav_admin) {
            Intent intent = (new Intent(this, BloodActivity.class));
            startActivity(intent);

        }
        else if (id == R.id.nav_blood) {
            Intent intent = (new Intent(this, BloodActivity.class));
            startActivity(intent);

        }
        else if (id == R.id.nav_cordinator) {
            Intent intent = (new Intent(this, BloodActivity.class));
            startActivity(intent);

        }
        else if (id == R.id.nav_tution) {
            Intent intent = (new Intent(this, BloodActivity.class));
            startActivity(intent);

        }
        else if (id == R.id.nav_ambulance) {
            Intent intent = (new Intent(this, BloodActivity.class));
            startActivity(intent);

        }
        else if (id == R.id.nav_fire_service) {
            Intent intent = (new Intent(this, BloodActivity.class));
            startActivity(intent);

        }
        else if (id == R.id.nav_organization) {
            Intent intent = (new Intent(this, BloodActivity.class));
            startActivity(intent);

        }else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
