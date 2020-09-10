package com.example.mddeloarhossain.jonaki;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class BloodDonorProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private TextView currentDateText;
    private CardView CurrentDateButton, SubjectCard;
    private DatePickerDialog datePickerDialog;
    //private DatePicker CurrentDatePicker;
    Switch DonateSwitch, TutionSwitch;
    private TextView DonateTextView, TutionTextView, name,city,location,bloodgroup,
            donationstatus,gender,tutionstatus,email,password,age,contactnumber, subjectname;
    private CircleImageView imageView;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    StorageTask uploadTask;
    private FirebaseStorage firebaseStorage;
    ProgressDialog progressDialog;
    //private  AdapterBloodDonorProfileShow imageAdapter;
    //private List<AdapterBloodDonorsShowUpload> uploadList;

    String DonorId, lastDonateDate, donationstatus1, tutionstatus1, subjectname1;
    private Button EditButton, DeleteButton;
    private Spinner Rspinner, RGenderSpinner;
    private String Name2, City2, Location2, Age2, ContactNo2, Email2, DonationStatus2, BloodGroup2,
            Gender2, ImageURL2, LastDonationStatus2, TutionStatus2, SubjectName2;
    private Switch donationSwitch, tutionSwitch;

    private ProgressBar progressBar;
    private Uri imageUri;
    private static final int IMAGE_REQUEST = 1;

    public static final String DONOR_ID = "com.example.mddeloarhossain.bloodbankm.donorid";
    public static final String DONOR_NAME = "com.example.mddeloarhossain.bloodbankm.donorname";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_donor_profile);


        Intent intent = getIntent();
        firebaseStorage = FirebaseStorage.getInstance();
        //databaseReference = FirebaseDatabase.getInstance().getReference("donors").child(intent.getStringExtra(BloodDonorsActivity.DONOR_ID));

        DonorId = intent.getStringExtra(BloodDonorsActivity.DONOR_ID);
        databaseReference = FirebaseDatabase.getInstance().getReference("donors").child(intent.getStringExtra(BloodDonorsActivity.DONOR_ID));
        storageReference = FirebaseStorage.getInstance().getReference("donors");

        EditButton = findViewById(R.id.editButton);
        DeleteButton = findViewById(R.id.deleteButton);


        name = findViewById(R.id.nameTextViewId);
        city = findViewById(R.id.districtTextViewId);
        location = findViewById(R.id.areaTextViewId);
        bloodgroup = findViewById(R.id.bloodGroupTextViewId);
        age = findViewById(R.id.ageTextViewId);
        contactnumber = findViewById(R.id.contactNoTextViewId);
        email = findViewById(R.id.emailTextViewId);
        donationstatus = findViewById(R.id.DonationStatusTextViewId);
        tutionstatus = findViewById(R.id.TutionStatusTextViewId);
        gender = findViewById(R.id.genderTextViewId);
        SubjectCard = findViewById(R.id.SubjectCardViewId);
        subjectname = findViewById(R.id.subjectTextViewId);
        //password = findViewById(R.id.bloodGroupTextViewId);
        imageView = findViewById(R.id.profile_image);
        SubjectCard.setOnClickListener(this);

        donationSwitch = findViewById(R.id.DonateSwitchId);
        tutionSwitch = findViewById(R.id.TutionSwitchId);
        Intent intent1 = getIntent();


            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                    Name2 = dataSnapshot.child("name").getValue().toString();
                    City2 = dataSnapshot.child("city").getValue().toString();
                    Location2 = dataSnapshot.child("location").getValue().toString();
                    BloodGroup2 = dataSnapshot.child("bloodgroup").getValue().toString();
                    Age2 = dataSnapshot.child("age").getValue().toString();
                    ContactNo2 = dataSnapshot.child("contactnumber").getValue().toString();
                    DonationStatus2 = dataSnapshot.child("donationstatus").getValue().toString();
                    Gender2 = dataSnapshot.child("gender").getValue().toString();
                    try {
                        LastDonationStatus2 = dataSnapshot.child("lastDonationDate").getValue().toString();
                        currentDateText.setText(LastDonationStatus2);
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Your last donation date is no set.", Toast.LENGTH_SHORT).show();
                    }
                    try {
                        DonationStatus2 = dataSnapshot.child("donationstatus").getValue().toString();
                        donationstatus.setText(DonationStatus2);
                        donationstatus1=DonationStatus2;
                        if(DonationStatus2.equals("Ready")){
                            donationSwitch.setChecked(true);
                        }
                    }catch (Exception e){
                        donationstatus1="Not Set";
                        //Toast.makeText(getApplicationContext(), "Your last donation date is no set.", Toast.LENGTH_SHORT).show();
                    }
                    try {
                        TutionStatus2 = dataSnapshot.child("tutionstatus").getValue().toString();
                        tutionstatus.setText(TutionStatus2);
                        tutionstatus1=TutionStatus2;
                        if(TutionStatus2.equals("Ready")){
                            tutionSwitch.setChecked(true);
                        }
                    }catch (Exception e){
                        tutionstatus1="Not Set";
                        //Toast.makeText(getApplicationContext(), "Your last donation date is no set.", Toast.LENGTH_SHORT).show();
                    }
                    try {
                        SubjectName2 = dataSnapshot.child("subjectname").getValue().toString();
                        subjectname.setText(SubjectName2);
                        subjectname1=SubjectName2;

                    }catch (Exception e){
                        subjectname1="Not Set";
                        //Toast.makeText(getApplicationContext(), "Your last donation date is no set.", Toast.LENGTH_SHORT).show();
                    }

                    try {

                        ImageURL2 = dataSnapshot.child("imageUrl").getValue().toString();

                        Picasso.with(getApplicationContext())
                                .load(ImageURL2)
                                .placeholder(R.drawable.man)
                                .fit()
                                .centerCrop()
                                .into(imageView);

                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Your profile image is not set", Toast.LENGTH_SHORT).show();

                        //Toast.makeText(this, "Your profile image is not set", Toast.LENGTH_SHORT).show();
                    }

                    name.setText(Name2);
                    city.setText(": "+City2);
                    location.setText(": "+Location2);
                    bloodgroup.setText(BloodGroup2);
                    age.setText(Age2);
                    contactnumber.setText(": "+ContactNo2);
                    donationstatus.setText(": "+DonationStatus2);
                    gender.setText(Gender2);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {


                }
            });

        EditButton.setOnClickListener(this);
        DeleteButton.setOnClickListener(this);
        imageView.setOnClickListener(this);

        currentDateText = findViewById(R.id.lastDonationDateTextId);
        CurrentDateButton = findViewById(R.id.DonationDateId);
        /*currentDateText.setText(currenDate());
        CurrentDatePicker = findViewById(R.id.currentDatePickerId);*/
        //currentDateText.setText();
        CurrentDateButton.setOnClickListener(this);
        DonateTextView = findViewById(R.id.DonationStatusTextViewId);
        TutionTextView = findViewById(R.id.TutionStatusTextViewId);
        DonateSwitch = findViewById(R.id.DonateSwitchId);
        TutionSwitch = findViewById(R.id.TutionSwitchId);


        DonateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    //DonateTextView.setText(": Can't");
                    donationstatus1="Ready";
                }else {
                    //DonateTextView.setText(": Ready");
                    donationstatus1="Can't";
                }
                AdapterBloodDonorStatusSet bloodDonorStatusSet = new AdapterBloodDonorStatusSet(donationstatus1, tutionstatus1);
                databaseReference.child("donationstatus").setValue(bloodDonorStatusSet.getDonationstatus());
                databaseReference.child("tutionstatus").setValue(bloodDonorStatusSet.getTutionstatus());
            }
        });
        TutionSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    //TutionTextView.setText(": Can't");
                    tutionstatus1="Ready";


                }else {
                    //TutionTextView.setText(": Ready");
                    //tutionstatus1="Ready";
                    tutionstatus1="Can't";
                }
                AdapterBloodDonorStatusSet bloodDonorStatusSet = new AdapterBloodDonorStatusSet(donationstatus1, tutionstatus1);
                databaseReference.child("donationstatus").setValue(bloodDonorStatusSet.getDonationstatus());
                databaseReference.child("tutionstatus").setValue(bloodDonorStatusSet.getTutionstatus());

            }
        });



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
            case R.id.DonationDateId:
                DatePicker datePicker = new DatePicker(this);

                int currentDay = datePicker.getDayOfMonth();
                int currentMonth = (datePicker.getMonth())+1;
                int currentYear = datePicker.getYear();
                //currentDateText.setText(currentDay);


                datePickerDialog = new DatePickerDialog(this,

                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

                                //******Must Check if it is future date or not******//
                                lastDonateDate = (dayOfMonth+"/"+(month+1)+"/"+year);
                                //currentDateText.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                                //lastDonateDate = currentDateText;

                                currentDateText.setText(lastDonateDate);
                                AdapterLastDonationDateSet lastDonationDateSet = new AdapterLastDonationDateSet(lastDonateDate);
                                databaseReference.child("lastDonationDate").setValue(lastDonationDateSet.getLastDonationDate());

                            }
                        },currentYear,currentMonth,currentDay);


                datePickerDialog.show();
                break;

            case R.id.editButton:
                editDonorProfile();
                break;
            case R.id.deleteButton:
                deleteDonorProfile();
                break;
            case R.id.profile_image:


                //editDonorProfileImage();
                Intent i = getIntent();
                String Donorid = i.getStringExtra(BloodDonorsActivity.DONOR_ID);
                Intent intent = (new Intent(getApplicationContext(), BloodDonorProfileImageUpdateActivity.class));
                intent.putExtra(DONOR_ID,Donorid);
                intent.putExtra(DONOR_NAME,Name2);
                startActivity(intent);
                break;
            case R.id.SubjectCardViewId:
                editTutionSubject();

                break;

        }

    }
    private void editTutionSubject(){
        try {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(BloodDonorProfileActivity.this);
            LayoutInflater inflater = getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.tution_subject_edit_dialog_layout, null);
            dialogBuilder.setView(dialogView);

            //final EditText editTextName = (EditText) dialogView.findViewById(R.id.NameId);
            final ImageView crossButton = (ImageView) dialogView.findViewById(R.id.crossButtonId);
            final Button saveUpdateButton = (Button) dialogView.findViewById(R.id.updateButton);
            final Button cancelUpdateButton = (Button) dialogView.findViewById(R.id.updateCancelButton);

            final EditText subjectName = (EditText) dialogView.findViewById(R.id.subjectEditTextId);



            subjectName.setText(subjectname1);

            final AlertDialog b = dialogBuilder.create();
            b.show();

            saveUpdateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    progressDialog = new ProgressDialog(BloodDonorProfileActivity.this);
                    progressDialog.setTitle("Updating your information!");
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();
                    //String Name1;
                    subjectname1 = subjectName.getText().toString().trim();

                    AdapterTutionSubjectNameSet tutionSubjectNameSet = new AdapterTutionSubjectNameSet(subjectname1);
                    databaseReference.child("subjectname").setValue(tutionSubjectNameSet.getSubjecname());

                    progressDialog.dismiss();
                    b.dismiss();

                }
            });

            crossButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    b.dismiss();
                }
            });
            cancelUpdateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    b.dismiss();

                }
            });


        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Error: "+e, Toast.LENGTH_SHORT).show();


        }
    }

    private void editDonorProfile(){
        try {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(BloodDonorProfileActivity.this);
            LayoutInflater inflater = getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.blood_donor_edit_profile_dialog_layout, null);
            dialogBuilder.setView(dialogView);

            //final EditText editTextName = (EditText) dialogView.findViewById(R.id.NameId);
            final ImageView crossButton = (ImageView) dialogView.findViewById(R.id.crossButtonId);
            final Button saveUpdateButton = (Button) dialogView.findViewById(R.id.updateButton);
            final Button cancelUpdateButton = (Button) dialogView.findViewById(R.id.updateCancelButton);

            //final EditText Name, City, Location, Age, ContactNo, Email, DonationStatus;
            //final Spinner Gender, BloodGroup;
            final CheckBox checkBox = (CheckBox) dialogView.findViewById(R.id.checkboxId);
            final EditText Name = (EditText) dialogView.findViewById(R.id.NameId);
            final EditText City = (EditText) dialogView.findViewById(R.id.DistrictId);
            final EditText Location = (EditText) dialogView.findViewById(R.id.AreaId);
            //BloodGroup = findViewById(R.id.BloodGroupId);
            final EditText Age = (EditText) dialogView.findViewById(R.id.AgeId);
            final EditText ContactNo = (EditText) dialogView.findViewById(R.id.ContactNumberId);
            final AutoCompleteTextView BloodGroup = (AutoCompleteTextView) dialogView.findViewById(R.id.autoCompleteBloodGroup);
            final AutoCompleteTextView Gender = (AutoCompleteTextView) dialogView.findViewById(R.id.autoCompleteGender);
            String[] bloodGroup;
            String[] gender;
            //String BloodGroupText, GenderText;




            //Gender = (Spinner) dialogView.findViewById(R.id.spinnerGender);
            //BloodGroup = (Spinner) dialogView.findViewById(R.id.spinnerBloodGroup);
            //Email = findViewById(R.id.Email);
            //final EditText DonationStatus = (EditText) findViewById(R.id.DonationDateId);
            //checkBox = dialogView.findViewById(R.id.checkboxId);

            if(DonationStatus2.equals("Ready")){
                checkBox.setChecked(true);
            }

            Name.setText(Name2);
            City.setText(City2);
            Location.setText(Location2);
            Age.setText(Age2);
            ContactNo.setText(ContactNo2);
            BloodGroup.setText(BloodGroup2);
            Gender.setText(Gender2);
            bloodGroup = getResources().getStringArray(R.array.BloodGroup);
            //BloodGroup = findViewById(R.id.autoCompleteBloodGroup);
            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, bloodGroup);
            BloodGroup.setThreshold(1);
            BloodGroup.setAdapter(adapter1);

            BloodGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                     String BloodGroupText = BloodGroup.getText().toString();
                    Toast.makeText(getApplicationContext(), BloodGroupText, Toast.LENGTH_SHORT).show();
                }
            });


            gender = getResources().getStringArray(R.array.Gender);
            //Gender = findViewById(R.id.autoCompleteGender);
            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, gender);
            Gender.setThreshold(1);
            Gender.setAdapter(adapter2);

            Gender.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String GenderText = Gender.getText().toString();
                    Toast.makeText(getApplicationContext(), GenderText, Toast.LENGTH_SHORT).show();
                }
            });

            final AlertDialog b = dialogBuilder.create();
            b.show();

            saveUpdateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    progressDialog = new ProgressDialog(BloodDonorProfileActivity.this);
                    progressDialog.setTitle("Updating your information!");
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();
                    String Name1, City1, Location1, Age1, ContactNo1, Email1, DonationStatus1, BloodGroup1, Gender1, Check1;

                    Name1 = Name.getText().toString().trim();
                    City1 = City.getText().toString().trim();
                    Location1 = Location.getText().toString().trim();
                    Age1 = Age.getText().toString().trim();
                    ContactNo1 = ContactNo.getText().toString().trim();
                    BloodGroup1 = BloodGroup.getText().toString().trim();
                    Gender1 = Gender.getText().toString().trim();

                    //BloodGroup1 = BloodGroup.getSelectedItem().toString();
                    //Gender1 = Gender.getSelectedItem().toString();
                    Check1="Ready";
                    if(checkBox.isChecked()){
                        //Check1="Ready";
                    }else {
                        Check1="Can't";
                    }
                    updateDonorProfile(Name1, City1, Location1, Age1, ContactNo1, Gender1, BloodGroup1, Check1);
                    b.dismiss();

                }
            });

            crossButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    b.dismiss();
                }
            });
            cancelUpdateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    b.dismiss();

                }
            });


        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Error: "+e, Toast.LENGTH_SHORT).show();


        }
    }


    private void updateDonorProfile(String Name1,String City1,String Location1,String Age1,String ContactNo1, String Gender1, String BloodGroup1, String Check1){
        try {

            AdapterBloodDonorsShowUpload adapterBloodDonorRegistration = new AdapterBloodDonorsShowUpload(Name1, City1, Location1, Age1, ContactNo1, BloodGroup1,Gender1, Check1, Name2, ImageURL2);

            databaseReference.setValue(adapterBloodDonorRegistration);
            //Name2 = Name1;
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(),"Your data is successfully updated.",Toast.LENGTH_LONG).show();




        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Error: "+e, Toast.LENGTH_SHORT).show();


        }
    }

    private void deleteDonorProfile(){
        AlertDialog.Builder builder = new AlertDialog.Builder(BloodDonorProfileActivity.this);
        builder.setIcon(R.drawable.ic_question);
        builder.setTitle("Are You Sure ?");
        builder.setMessage("You want to delete your data !");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                databaseReference.removeValue();
                Intent intent = new Intent(BloodDonorProfileActivity.this, BloodDonorsActivity.class);
                //Toast.makeText(MainActivity.this, "You can Log In Now", Toast.LENGTH_SHORT).show();
                startActivity(intent);

            }
        });

        builder.setNeutralButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();



    }

}
