package com.example.heartattackprediction;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddPatientInfo extends AppCompatActivity {
    EditText etName, etMobile, etEmail, etAddress;

    Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient_info);
        db = new Database(this);
        etName = (EditText) findViewById(R.id.etPatientName);
        etMobile = (EditText) findViewById(R.id.etMobile);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etAddress = (EditText) findViewById(R.id.etAddress);
        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etAddress.getText().toString().equals("") || etMobile.getText().toString().equals("") || etEmail.getText().toString().equals("") || etName.getText().toString().equals("")) {
                    Toast.makeText(AddPatientInfo.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                } else {

                    db.open();
                    long data_inserted = db.insertPatientList(etName.getText().toString(), etMobile.getText().toString(), etEmail.getText().toString(), etAddress.getText().toString());
                    if (data_inserted > 0) {
                        Toast.makeText(AddPatientInfo.this, "Data inserted successfully...", Toast.LENGTH_SHORT).show();
                    }
                    db.close();
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
