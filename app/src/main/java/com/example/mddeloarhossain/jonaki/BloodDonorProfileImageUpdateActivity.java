package com.example.mddeloarhossain.jonaki;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import com.squareup.picasso.Picasso;

public class BloodDonorProfileImageUpdateActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView donorNameText;
    private Button chooseButton, saveButton, skipButton;
    private ImageView imageView;
    private EditText imageNameEditText;
    private ProgressBar progressBar;
    private Uri imageUri;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    StorageTask uploadTask;

    private static final int IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_donor_profile_image_update);

        Intent intent = getIntent();

        databaseReference = FirebaseDatabase.getInstance().getReference("donors").child(intent.getStringExtra(BloodDonorProfileActivity.DONOR_ID));
        storageReference = FirebaseStorage.getInstance().getReference("donors");


        donorNameText = findViewById(R.id.updateNameId);
        chooseButton = findViewById(R.id.chooseUpdateImageButton);
        saveButton = findViewById(R.id.saveImageUpdateButton);
        skipButton = findViewById(R.id.cancelImageUpdateButton);
        progressBar = findViewById(R.id.progressbarId);
        imageView = findViewById(R.id.imageUpdateId);

        donorNameText.setText(intent.getStringExtra(BloodDonorProfileActivity.DONOR_NAME));

        chooseButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        skipButton.setOnClickListener(this);



    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.chooseUpdateImageButton:
                openFileChooser();

                break;

            case R.id.saveImageUpdateButton:
                if(uploadTask !=null && uploadTask.isInProgress()){
                    Toast.makeText(getApplicationContext(),"Uploading is in progress",Toast.LENGTH_SHORT).show();

                }else{
                    //saveData();
                    Intent intent = (new Intent(getApplicationContext(), BloodDonorProfileActivity.class));
                    startActivity(intent);


                }


                break;

            case R.id.cancelImageUpdateButton:

                Intent intent = (new Intent(getApplicationContext(), BloodDonorProfileActivity.class));
                startActivity(intent);

                break;
        }
    }

    void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            imageUri = data.getData();
            Picasso.with(this).load(imageUri).into(imageView);
        }
    }

    //getting the extesion of the image
    public String getFileExtension(Uri imageUri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUri));

    }

    public void saveData() {

        try {
            Intent intent = getIntent();
            final String imageName = intent.getStringExtra(BloodDonorProfileActivity.DONOR_NAME);
            //final String imageName = "mddeloarhossain";
            StorageReference ref = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));

            ref.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            // Get a URL to the uploaded content
                            try {

                                Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                                while (!urlTask.isSuccessful()) ;
                                Uri downloadUri = urlTask.getResult();

                                AdapterBloodDonorUploadImage upload = new AdapterBloodDonorUploadImage(imageName, downloadUri.toString());

                                //String uploadId = databaseReference.push().getKey();
                                Intent intent = getIntent();
                                String uploadId = intent.getStringExtra(BloodDonorProfileActivity.DONOR_ID);
                                //databaseReference.child(uploadId).setValue(upload);
                                //databaseReference.child("imageName").setValue(upload.getImageName());
                                databaseReference.child("imageUrl").setValue(upload.getImageUrl());
                                Toast.makeText(getApplicationContext(), "Successfully image stored.", Toast.LENGTH_SHORT).show();
                            }catch(Exception e){
                                Toast.makeText(getApplicationContext(), "Error: "+e, Toast.LENGTH_SHORT).show();


                            }


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                            Toast.makeText(getApplicationContext(), " Image not stored.", Toast.LENGTH_SHORT).show();
                        }
                    });


        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Error: "+e, Toast.LENGTH_SHORT).show();


        }
    }
}
