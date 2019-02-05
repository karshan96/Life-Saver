package com.abc.life_saver;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity {
    Button register,login;
    EditText email,password;
    private FirebaseAuth logAuth;
    private FirebaseUser user;

    private DatabaseReference usRef;

    public String userEmail;
    private String userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logAuth = FirebaseAuth.getInstance();

        register = (Button) findViewById(R.id.register);
        login = (Button) findViewById(R.id.login);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    public void logIn(){
        userEmail = email.getText().toString().trim();
        userPassword = password.getText().toString().trim();

        if(userEmail.isEmpty()){
            Toast.makeText(MainActivity.this, "Email is empty", Toast.LENGTH_LONG).show();
            email.requestFocus();
            return;
        }
        if(userPassword.length()<6){
            Toast.makeText(MainActivity.this, "Password require more then 6 digits", Toast.LENGTH_LONG).show();
            password.requestFocus();
            return;
        }

        logAuth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //checkEmailVerification();
                    finish();
                    startActivity(new Intent(MainActivity.this,Search.class));
                }
                else {
                    Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void registerUser(){
        Intent intent = new Intent(this,UserRegister.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(logAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(this,Search.class));
        }
        // Check if user is signed in (non-null) and update UI accordingly.
    }

    public void checkEmailVerification(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag = user.isEmailVerified();

        if(emailflag){
            finish();
            startActivity(new Intent(this,Search.class));
        } else {
            Toast.makeText(MainActivity.this,"Verify user email", Toast.LENGTH_LONG).show();
            logAuth.signOut();
        }
    }
}
