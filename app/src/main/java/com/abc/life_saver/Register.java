package com.abc.life_saver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class Register extends AppCompatActivity {

    private EditText name, email, address, contact, dob;
   // private RadioButton male, female;
    private Spinner blood;
    private Button submit, cancel;
    private FirebaseAuth mAuth;

    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Authentication
        myRef = FirebaseDatabase.getInstance().getReference().child("User");
        mAuth = FirebaseAuth.getInstance();

        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        address = (EditText) findViewById(R.id.address);
        contact = (EditText) findViewById(R.id.contact);
        dob = (EditText) findViewById(R.id.dob);
        //male = (RadioButton) findViewById(R.id.male);
        //female = (RadioButton) findViewById(R.id.female);
        blood = (Spinner) findViewById(R.id.blood);
        submit = (Button) findViewById(R.id.submit);
        cancel = (Button) findViewById(R.id.cancel);

      /*  submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddUser();
            }
        });*/
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        /*FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null){
            Intent startIntent = new Intent(Register.this, StartActivity.class);
            startActivity(startIntent);
            finish();
        }*/
        AddUser();

    }

    protected void AddUser() {
        final String useraddress = address.getText().toString().trim();
        final String usercontact = contact.getText().toString().trim();
        final String userdob = dob.getText().toString().trim();
        //final String userGender =
        final String bloodGroup = blood.getSelectedItem().toString().trim();

        /*if (TextUtils.isEmpty(username) ||
                !TextUtils.isEmpty(useremail) ||
                !TextUtils.isEmpty(useraddress) ||
                !TextUtils.isEmpty(usercontact) ||
                !TextUtils.isEmpty(usercontact) ||
                !TextUtils.isEmpty(userdob)) {
            Toast.makeText(this, "You should enter missing things", Toast.LENGTH_LONG).show();
        } else {*/
        submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*FirebaseUser currentUser = mAuth.getCurrentUser();
                    if(currentUser == null) {
                        String id = myRef.push().getKey();
                        User user = new User();
                        user.setName(username);
                        user.setEmail(useremail);
                        myRef.child(id).setValue(user);
                        finish();

                        }*/
                    String username = name.getText().toString().trim();
                    String useremail = email.getText().toString().trim();

                    String id = myRef.push().getKey();
                    myRef.child(id).child("name").setValue(username);
                    myRef.child(id).child("email").setValue(useremail);
                    }

            });
        //}
    }

}
