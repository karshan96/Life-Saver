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
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Update extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {
    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar = null;

    private EditText name;
    private EditText email;
    private EditText password;
    private EditText contact;
    private EditText dob;
    private RadioGroup gender;
    private RadioButton genderOption;
    private Spinner blood;
    private Button update;
    private CheckBox donor;
    private FirebaseAuth logAuth;
    private FirebaseDatabase database;
    private String userGender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);
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

        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        contact = (EditText) findViewById(R.id.contact);
        dob = (EditText) findViewById(R.id.dob);
        gender = (RadioGroup) findViewById(R.id.gender);
        blood = (Spinner) findViewById(R.id.blood);
        update = (Button) findViewById(R.id.update);
        donor = (CheckBox) findViewById(R.id.donor);

        BaseAdapter adapter = ArrayAdapter.createFromResource(this,R.array.bloodGroup,android.R.layout.simple_spinner_item);
        blood.setAdapter(adapter);
        blood.setOnItemSelectedListener(this);


        logAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        final DatabaseReference usRef = database.getReference("Users");
        final DatabaseReference doRef = database.getReference("Donors");

        usRef.child(logAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final User u = dataSnapshot.getValue(User.class);
                name.setText(u.getName());
                email.setText(u.getEmail());
                password.setText(u.getPassword());
                contact.setText(Integer.toString(u.getContact()));
                dob.setText(u.getDob());
                if(usRef.child(logAuth.getUid()).equals(doRef.child(FirebaseAuth.getInstance().getUid()))){
                    donor.setChecked(true);
                }else{
                    donor.setChecked(false);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionUpdate();
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        if(logAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }
        // Check if user is signed in (non-null) and update UI accordingly.
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
        getMenuInflater().inflate(R.menu.content, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id){
            case R.id.nav_search:
                Intent s = new Intent(Update.this,Search.class);
                startActivity(s);
                break;

            case R.id.nav_list:
                Intent l = new Intent(Update.this,BloodStock.class);
                startActivity(l);
                break;

            case R.id.nav_update:
                Intent u = new Intent(Update.this, Update.class);
                startActivity(u);
                break;
        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        TextView textView = (TextView) view;
        textView.setTextColor(Color.parseColor("#000000"));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void actionUpdate(){
        final DatabaseReference usRef = database.getReference("Users");
        final DatabaseReference doRef = database.getReference("Donors");
        if (donor.isChecked()) {
            User use = new User(name.getText().toString(),email.getText().toString(),password.getText().toString(),Integer.parseInt(contact.getText().toString()),
                    userGender,dob.getText().toString(),blood.getSelectedItem().toString());
            doRef.child(FirebaseAuth.getInstance().getUid()).setValue(use);
            Toast.makeText(Update.this, "Your are in donors list", Toast.LENGTH_LONG).show();
        }else {
            doRef.child(FirebaseAuth.getInstance().getUid()).removeValue();
            Toast.makeText(Update.this, "Your aren't in donors list", Toast.LENGTH_LONG).show();
        }
        User user = new User(name.getText().toString(),email.getText().toString(),password.getText().toString(),Integer.parseInt(contact.getText().toString()),
                userGender,dob.getText().toString(),blood.getSelectedItem().toString());
        usRef.child(FirebaseAuth.getInstance().getUid()).setValue(user);
        Toast.makeText(Update.this, "Data updated successfully!", Toast.LENGTH_LONG).show();
    }
}
