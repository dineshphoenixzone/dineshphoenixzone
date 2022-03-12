package com.example.heartattackprediction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.heartattackprediction.ui.home.HomeFragment;

public class IPActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip);
        final EditText etServer = (EditText) findViewById(R.id.etServer);
        Button btnConnect = (Button) findViewById(R.id.btnConnect);
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etServer.getText().toString().equals("")) {
                    Toast.makeText(IPActivity.this, "Please enter Server IP", Toast.LENGTH_SHORT).show();

                } else {
                    ServerUtility.serverURL = "http://" + etServer.getText().toString() + ":5000/login";
//                    startActivity(new Intent(IPActivity.this, HomeActivity.class));
                    startActivity(new Intent(IPActivity.this, MainActivity.class));
                    finish();
                }
            }
        });
    }
}
