package com.abc.life_saver;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserRegister extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,AdapterView.OnItemSelectedListener {
    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar = null;

    private EditText name, email, password, contact, dob;
    private RadioGroup gender;
    private RadioButton genderOption;
    private Spinner blood;
    private Button submit;
    private FirebaseAuth logAuth;
    private CheckBox donor;
    private FirebaseUser user;

    private DatabaseReference usRef, doRef;

    String username;
    String useremail;
    String userpassword;
    int usercontact;
    String userdob;
    String userGender;
    String bloodGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Authentication
        logAuth = FirebaseAuth.getInstance();

        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        contact = (EditText) findViewById(R.id.contact);
        dob = (EditText) findViewById(R.id.dob);
        gender = (RadioGroup) findViewById(R.id.gender);
        blood = (Spinner) findViewById(R.id.blood);
        submit = (Button) findViewById(R.id.submit);
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

        BaseAdapter adapter = ArrayAdapter.createFromResource(this,R.array.bloodGroup,android.R.layout.simple_spinner_item);
        blood.setAdapter(adapter);
        blood.setOnItemSelectedListener(this);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddUser();
            }
        });
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
        getMenuInflater().inflate(R.menu.user_register, menu);
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
        switch (id){
            case R.id.nav_person:
                Intent p = new Intent(UserRegister.this,UserRegister.class);
                startActivity(p);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
        if(useremail.isEmpty()){
            Toast.makeText(UserRegister.this, "Email is empty", Toast.LENGTH_LONG).show();
            email.requestFocus();
            return;
        }
        userpassword = password.getText().toString().trim();
        if(userpassword.length()<6){
            Toast.makeText(UserRegister.this, "Password require more then 6 digits", Toast.LENGTH_LONG).show();
            password.requestFocus();
            return;
        }
        usercontact = Integer.parseInt(contact.getText().toString().trim());

        userdob = dob.getText().toString().trim();

        bloodGroup = blood.getSelectedItem().toString();

        usRef = FirebaseDatabase.getInstance().getReference("Users");
        doRef = FirebaseDatabase.getInstance().getReference("Donors");

        logAuth.createUserWithEmailAndPassword(useremail,userpassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    if (donor.isChecked()) {
                        User user = new User(username, useremail, userpassword, usercontact, userGender, userdob, bloodGroup);
                        doRef.child(FirebaseAuth.getInstance().getUid()).setValue(user);
                    }

                    User user = new User(username, useremail, userpassword, usercontact, userGender, userdob, bloodGroup);
                    usRef.child(FirebaseAuth.getInstance().getUid()).setValue(user);
                    sendEmailVerification();
                    finish();
                } else{
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(UserRegister.this, "You are already register", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(UserRegister.this,task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if(logAuth.getCurrentUser() != null){

        }
        // Check if user is signed in (non-null) and update UI accordingly.
    }

    public void sendEmailVerification(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(UserRegister.this,"Successfully registered, Verification mail sent!", Toast.LENGTH_LONG).show();
                        logAuth.signOut();
                        finish();
                        startActivity(new Intent(UserRegister.this,MainActivity.class));
                    }
                    else {
                        Toast.makeText(UserRegister.this,"Verification mail isn't sent!", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        TextView textView = (TextView) view;
        textView.setTextColor(Color.parseColor("#000000"));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
