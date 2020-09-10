package com.example.mddeloarhossain.jonaki;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class AmbulanceDetailsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView  name,city,location,contactnumber;
    DatabaseReference databaseReference;
    private LinearLayout callButton, messageButton;
    private String call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance_details);

        Intent intent = getIntent();
        //firebaseStorage = FirebaseStorage.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("ambulances").child(intent.getStringExtra(AmbulanceActivity.AMBULANCE_ID));



        callButton = findViewById(R.id.callButtonId);
        messageButton = findViewById(R.id.messageButtonId);

        name = findViewById(R.id.nameTextViewId);
        city = findViewById(R.id.districtTextViewId);
        location = findViewById(R.id.areaTextViewId);
        //bloodgroup = findViewById(R.id.bloodGroupTextViewId);
        //age = findViewById(R.id.ageTextViewId);
        contactnumber = findViewById(R.id.contactNoTextViewId);

        String Name2 = intent.getStringExtra(AmbulanceActivity.AMBULANCE_NAME);
        call=intent.getStringExtra(AmbulanceActivity.AMBULANCE_CONTACTNO);

        name.setText(Name2);
        city.setText(": "+intent.getStringExtra(AmbulanceActivity.AMBULANCE_CITY));
        location.setText(": "+intent.getStringExtra(AmbulanceActivity.AMBULANCE_LOCATION));
        //bloodgroup.setText(intent.getStringExtra(BloodDonorsActivity.DONOR_BLOODGROUP));
        //age.setText(intent.getStringExtra(BloodDonorsActivity.DONOR_AGE));
        contactnumber.setText(": "+call);

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call="01765436702";
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                //int CallingNumber = String.toInteger(call);
                Toast.makeText(getApplicationContext(), "You calling Number is:"+call, Toast.LENGTH_SHORT).show();
                callIntent.setData(Uri.parse("tel:"+call));
                if (ActivityCompat.checkSelfPermission(AmbulanceDetailsActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(DonorDetails.this, "In if method", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Toast.makeText(DonorDetails.this, "Before startActivity.", Toast.LENGTH_SHORT).show();
                startActivity(callIntent);
            }
        });
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AmbulanceDetailsActivity.this);
                builder.setIcon(R.drawable.ic_question);
                builder.setTitle("Do you want to send message ?");
                builder.setMessage("Standard Data Charge Apply !");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //call = "01765436702";

                        sendSMS(call);
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                        //Toast.makeText(MainActivity.this, "You can Log In Now", Toast.LENGTH_SHORT).show();
                        //startActivity(intent);
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
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
            Intent intent = (new Intent(AmbulanceDetailsActivity.this, BloodActivity.class));
            startActivity(intent);

        }else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    protected void sendSMS(String number) {
        Log.i("Send SMS", "");
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);

        smsIntent.setData(Uri.parse("smsto:"));
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address"  , new String (number));
        smsIntent.putExtra("sms_body"  , "Jonaki\nName:\nWhere:\nTime:\nDescription:");

        try {
            startActivity(smsIntent);
            finish();
            Log.i("Finished sending SMS...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(AmbulanceDetailsActivity.this,
                    "Sending SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
        }
    }
}
