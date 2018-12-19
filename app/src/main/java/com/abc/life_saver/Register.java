package com.abc.life_saver;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.util.Date;

public class Register extends AppCompatActivity {

    private EditText name, email, password, contact, dob;
    private RadioGroup gender;
    private RadioButton genderOption;
    private Spinner blood;
    private Button submit, cancel;
    private FirebaseAuth logAuth;
    private CheckBox donor;

    private DatabaseReference usRef, doRef;

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
        usRef = FirebaseDatabase.getInstance().getReference().child("Users");
        doRef = FirebaseDatabase.getInstance().getReference().child("Donors");
        logAuth = FirebaseAuth.getInstance();

        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        contact = (EditText) findViewById(R.id.contact);
        dob = (EditText) findViewById(R.id.dob);
        gender = (RadioGroup) findViewById(R.id.gender);
        blood = (Spinner) findViewById(R.id.blood);
        submit = (Button) findViewById(R.id.submit);
        cancel = (Button) findViewById(R.id.cancel);
        donor = (CheckBox) findViewById(R.id.donor);

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                genderOption = gender.findViewById(checkedId);

                switch (checkedId) {
                    case R.id.male:
                        userGender = genderOption.getText().toString();
                        break;
                    case R.id.female:
                        userGender = genderOption.getText().toString();
                        break;
                    default:
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddUser();
                finish();
            }
        });
    }


    public void AddUser() {
    /*if (TextUtils.isEmpty(username) ||
                !TextUtils.isEmpty(useremail) ||
                !TextUtils.isEmpty(useraddress) ||
                !TextUtils.isEmpty(usercontact) ||
                !TextUtils.isEmpty(usercontact) ||
                !TextUtils.isEmpty(userdob)) {
            Toast.makeText(this, "You should enter missing things", Toast.LENGTH_LONG).show();
        } else {*/
                username = name.getText().toString().trim();
                useremail = email.getText().toString().trim();
                userpassword = password.getText().toString().trim();
                usercontact = Integer.parseInt(contact.getText().toString().trim());

                DateFormat df = new SimpleDateFormat("DD/MM/YYYY");
                try {
                    userdob = df.parse(dob.getText().toString().trim());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                df.format(userdob);
                bloodGroup = blood.getSelectedItem().toString().trim();

                if (donor.isChecked()) {
                    String i = doRef.push().getKey();
                    User user = new User(username, useremail, userpassword, usercontact, userGender, userdob, bloodGroup);
                    doRef.child(i).setValue(user);
                }
                //FirebaseUser currentUser = mAuth.getCurrentUser();

                String id = usRef.push().getKey();
                User user = new User(username, useremail, userpassword, usercontact, userGender, userdob, bloodGroup);
                usRef.child(id).setValue(user);

                logAuth.createUserWithEmailAndPassword(useremail,userpassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            finish();
                        }
                    }
                });
            }


}
