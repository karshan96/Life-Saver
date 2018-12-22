package com.abc.life_saver;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HosRegisterActivity extends AppCompatActivity {

    private EditText hosName,hosEmail,hosPassword,hosContact,hosAddress,chiefName,chiefContact;
    private Button submit,cancel;

    String hosname;
    String hosemail;
    String hospassword;
    int hoscontact;
    String hosaddress;
    String chiefname;
    int chiefcontact;

    private DatabaseReference hosRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hos_register);

        hosRef = FirebaseDatabase.getInstance().getReference().child("Hospital");

        hosName =(EditText) findViewById(R.id.name);
        hosEmail =(EditText) findViewById(R.id.email);
        hosPassword =(EditText) findViewById(R.id.password);
        hosContact =(EditText) findViewById(R.id.contact);
        hosAddress =(EditText) findViewById(R.id.address);
        chiefName =(EditText) findViewById(R.id.cheifname);
        chiefContact =(EditText) findViewById(R.id.cheifno);
        submit = (Button) findViewById(R.id.submit);
        cancel = (Button) findViewById(R.id.cancel);
    }

    @Override
    public void onStart() {
        super.onStart();
        AddHospiatl();


    }

    protected void AddHospiatl(){

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hosname = hosName.getText().toString().trim();
                hosemail = hosEmail.getText().toString().trim();
                hospassword = hosPassword.getText().toString().trim();
                hoscontact = Integer.parseInt(hosContact.getText().toString().trim());
                hosaddress = hosAddress.getText().toString().trim();
                chiefname = chiefName.getText().toString().trim();
                chiefcontact = Integer.parseInt(chiefContact.getText().toString().trim());

                String id = hosRef.push().getKey();
                Hospital hospital = new Hospital(hosname, hosemail, hospassword, hoscontact, hosaddress, chiefname, chiefcontact);
                hosRef.child(id).setValue(hospital);
                Toast.makeText(HosRegisterActivity.this, "Hospital registration finished", Toast.LENGTH_LONG).show();
                finish();

            }
        });

    }

}
