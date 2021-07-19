package com.example.facetoface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.Slide;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

public class newaccountactivity extends AppCompatActivity {
    private ImageView back;
    private Button haveAccountbtn,signUpbtn;
    private EditText username;
    private EditText email;
    private EditText password;
    private FirebaseAuth auth;
    private FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newaccountactivity);
        back = findViewById(R.id.back);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        username = findViewById(R.id.username);
        haveAccountbtn = findViewById(R.id.haveAccountbtn);
        signUpbtn = findViewById(R.id.signUpbtn);
        //Craeting a new instance of Firebase
        auth = FirebaseAuth.getInstance();
        // Creating Database Instance for storing data of user
        database = FirebaseFirestore.getInstance();
        // This button helps to go back in the Login activity.
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(newaccountactivity.this,login_activity.class));
                finish();
            }
        });
        // This Button Allows you to go back to Login Activity
        haveAccountbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(newaccountactivity.this,login_activity.class));
                finish();
            }
        });
        // This button allows you to Sign Up for New Account
        signUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Username = username.getText().toString();
                String Password = password.getText().toString();
                String Email = email.getText().toString();
                // Adding data in Database
                final Users users = new Users();
                users.setEmail(Email);
                users.setPass(Password);
                users.setUsername(Username);
                if (Username.isEmpty() && Password.isEmpty() && Email.isEmpty()){
                   Toast.makeText(newaccountactivity.this,"Please Fill the required fields.",Toast.LENGTH_SHORT).show();
                }else if(Username.isEmpty()){
                    Toast.makeText(newaccountactivity.this, "Please Enter Your Name.", Toast.LENGTH_SHORT).show();
                }else if(Email.isEmpty()){
                    Toast.makeText(newaccountactivity.this,"Please Enter Your Email Address.",Toast.LENGTH_SHORT).show();
                }else if(Password.isEmpty()){
                    Toast.makeText(newaccountactivity.this,"Please Enter Your Password.",Toast.LENGTH_SHORT).show();
                }else if(Password.length() < 6){
                    Toast.makeText(newaccountactivity.this, "Password should be atleast of five characters.", Toast.LENGTH_SHORT).show();
                }
                else{
                    auth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                database.collection("Users").document(Email).set(users).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(newaccountactivity.this,"Account Created Successful.",Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(newaccountactivity.this,login_activity.class));
                                        finish();
                                    }
                                });

                            }else{
                                Toast.makeText(newaccountactivity.this,task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });
    }
}