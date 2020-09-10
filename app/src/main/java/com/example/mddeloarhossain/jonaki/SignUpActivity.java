package com.example.mddeloarhossain.jonaki;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUpActivity extends AppCompatActivity {


    private TextInputLayout textInputEmail, textInputConfirmPassword, textInputPassword;
    private ViewFlipper viewFlipper;
    String emailInput, passwordInput, confirmPasswordInput;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();


        textInputEmail = findViewById(R.id.text_input_email);
        textInputConfirmPassword = findViewById(R.id.text_input_confirm_password);
        textInputPassword = findViewById(R.id.text_input_password);


        viewFlipper = findViewById(R.id.view_flipper);
    }

    public void previousView(View v) {
        viewFlipper.setInAnimation(this, android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this, android.R.anim.slide_out_right);
        viewFlipper.showPrevious();
    }

    public void nextView(View v) {
        viewFlipper.setInAnimation(this, R.anim.slide_in_right);
        viewFlipper.setOutAnimation(this, R.anim.slide_out_left);
        if(!validateEmail() | !validatePassword() | !validateConfirmPassword()){
            return;
        }
        /*String input = "Email: "+textInputEmail.getEditText().getText().toString();
        input +="\n";
        input += "Username: "+textInputConfirmPassword.getEditText().getText().toString();
        input +="\n";
        input += "Password: "+textInputPassword.getEditText().getText().toString();*/

        String input = emailInput+"\n"+passwordInput+"\n"+confirmPasswordInput;
        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
        viewFlipper.showNext();
    }
    private boolean validateEmail(){
        emailInput = textInputEmail.getEditText().getText().toString().trim();
        if(emailInput.isEmpty()){
            textInputEmail.setError("Field can't be empty");
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            textInputEmail.setError("Please enter a valid email address");
            return false;
        }
        else {
            textInputEmail.setError(null);
            return true;
        }
    }

    private boolean validatePassword(){
         passwordInput = textInputPassword.getEditText().getText().toString().trim();
        if(passwordInput.isEmpty()){
            textInputPassword.setError("Field can't be empty");
            return false;
        }else if(passwordInput.length() < 6){
            textInputPassword.setError("Minimum length should be 6");
            return false;
        }else {
            textInputPassword.setError(null);
            return true;
        }
    }
    private boolean validateConfirmPassword(){
         confirmPasswordInput = textInputConfirmPassword.getEditText().getText().toString().trim();
        if(confirmPasswordInput.isEmpty()){
            textInputConfirmPassword.setError("Field can't be empty");
            return false;
        }else if(!confirmPasswordInput.equals(passwordInput)){
            textInputConfirmPassword.setError("Password doesn't match");
            return false;
        }
        else {
            textInputPassword.setError(null);
            return true;
        }
    }

    private void userAuthRegister(){
        //Toast.makeText(this,"userAuthRegister", Toast.LENGTH_LONG).show();
        String email = emailInput;
        String password = passwordInput;

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Successfull", Toast.LENGTH_SHORT).show();


                } else {
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(), "Username is already registered.", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Error:"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }



                }
            }
        });

    }


    public void saveInfo(View view){
        switch (view.getId()){
            case R.id.saveInfoId:
                Toast.makeText(this,"saveInfo", Toast.LENGTH_SHORT).show();
                userAuthRegister();
                Toast.makeText(this,"saveInfo close", Toast.LENGTH_LONG).show();
                /*Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);*/
                break;
        }
    }






}