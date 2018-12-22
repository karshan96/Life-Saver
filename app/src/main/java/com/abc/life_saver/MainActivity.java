package com.abc.life_saver;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Button register,login;
    EditText email,passwoard;

    private DatabaseReference usRef;

    private String userEmail;
    private String userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usRef = FirebaseDatabase.getInstance().getReference().child("Users");

        register =(Button)findViewById(R.id.register);
        login = (Button)findViewById(R.id.login);
        email = (EditText) findViewById(R.id.email);
        passwoard = (EditText)findViewById(R.id.password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        userEmail = email.getText().toString().trim();
        userPassword = passwoard.getText().toString().trim();

        Query query = usRef.orderByChild("email").equalTo(userEmail);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot user : dataSnapshot.getChildren()) {
                        User u = user.getValue(User.class);

                        if (u.getPassword().equals(userPassword)){
                            Intent intent = new Intent(MainActivity.this, Search.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Password is wrong", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Toast.makeText(MainActivity.this, "User not found", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void registerUser(View view){
        Intent intent = new Intent(this,RegistrationMenuActivity.class);
        startActivity(intent);
    }
}
