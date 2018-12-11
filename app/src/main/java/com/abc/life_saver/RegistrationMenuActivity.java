package com.abc.life_saver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegistrationMenuActivity extends AppCompatActivity {
    Button person,hospital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_menu);
        person = (Button) findViewById(R.id.person);
        hospital = (Button) findViewById(R.id.hospital);
    }

    public void registerPerson(View v) {
        Intent intent = new Intent(this,Register.class);
        startActivity(intent);
    }

    public void registerHospital(View view) {
        Intent intent = new Intent(this,HosRegisterActivity.class);
        startActivity(intent);
    }
}
