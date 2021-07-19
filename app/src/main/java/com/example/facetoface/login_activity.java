package com.example.facetoface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login_activity extends AppCompatActivity {
    private Button createNewAccountbtn;
    private Button loginbtn;
    private EditText email,password;
    FirebaseAuth auth;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        createNewAccountbtn = findViewById(R.id.haveAccountbtn);
        loginbtn = findViewById(R.id.signUpbtn);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait.");
        // Creating a new instance of Firebase Auth
        auth = FirebaseAuth.getInstance();
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                String Email = email.getText().toString();
                String Password = password.getText().toString();
                if (Email.isEmpty() && Password.isEmpty()){
                    Toast.makeText(login_activity.this,"Please Enter Your Email and Password.",Toast.LENGTH_SHORT).show();
                }else if(Email.isEmpty()){
                    Toast.makeText(login_activity.this,"Please Enter Your Email.",Toast.LENGTH_SHORT).show();
                }else if(Password.isEmpty()){
                    Toast.makeText(login_activity.this,"Please Enter Your Password.",Toast.LENGTH_SHORT).show();
                }else if(Password.length() < 6){
                    Toast.makeText(login_activity.this, "Password should be atleast of five characters.", Toast.LENGTH_SHORT).show();
                }
                else{
                    auth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            dialog.dismiss();
                            if (task.isSuccessful()){
                                Toast.makeText(login_activity.this,"You Logged in Successfully",Toast.LENGTH_SHORT).show();
                                Toast.makeText(login_activity.this,"Welcome To FaceToFace",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(login_activity.this,profileActivity.class));
                                finish();
                            }else{
                                Toast.makeText(login_activity.this, "Email and Password is Incorrect.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        createNewAccountbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login_activity.this,newaccountactivity.class));
            }
        });
    }
}