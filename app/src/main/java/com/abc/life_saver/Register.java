package com.abc.life_saver;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Date;

import static java.util.Date.parse;

public class Register extends AppCompatActivity {

    private EditText name, email, password, contact, dob;
    private RadioGroup gender;
    private RadioButton male,female;
    private Spinner blood;
    private Button submit, cancel;
    private FirebaseAuth mAuth;
    private CheckBox donor;

    private DatabaseReference usRef,doRef;

    String username;
    String useremail;
    String userpassword;
    int usercontact;
    Date userdob;
    String userGender;
    String bloodGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Authentication
        usRef = FirebaseDatabase.getInstance().getReference().child("User");
        doRef = FirebaseDatabase.getInstance().getReference().child("Donor");
        mAuth = FirebaseAuth.getInstance();

        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        contact = (EditText) findViewById(R.id.contact);
        dob = (EditText) findViewById(R.id.dob);
        gender = (RadioGroup) findViewById(R.id.gender);
        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        blood = (Spinner) findViewById(R.id.blood);
        submit = (Button) findViewById(R.id.submit);
        cancel = (Button) findViewById(R.id.cancel);
        donor = (CheckBox) findViewById(R.id.donor);

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
                    username = name.getText().toString().trim();
                    useremail = email.getText().toString().trim();
                    userpassword = password.getText().toString().trim();
                    usercontact = Integer.parseInt(contact.getText().toString().trim());
                    if(male.isSelected()){
                        userGender ="male";
                    }
                    else if(female.isSelected()){
                        userGender ="female";
                    }
                    DateFormat df = new SimpleDateFormat("DD/MM/YYYY");
                    try {
                        userdob = df.parse(dob.getText().toString().trim());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    df.format(userdob);
                    bloodGroup = blood.getSelectedItem().toString().trim();

                    if(donor.isChecked()){
                        String i = doRef.push().getKey();
                        User user = new User(username,useremail,userpassword,usercontact,userGender,userdob,bloodGroup);
                        doRef.child(i).setValue(user);
                    }
                    //FirebaseUser currentUser = mAuth.getCurrentUser();

                        String id = usRef.push().getKey();
                        User user = new User(username,useremail,userpassword,usercontact,userGender,userdob,bloodGroup);
                        usRef.child(id).setValue(user);
                        finish();

                    //myRef.child(id).child("name").setValue(username);
                    //myRef.child(id).child("email").setValue(useremail);
                    }

            });
        //}
    }

}
