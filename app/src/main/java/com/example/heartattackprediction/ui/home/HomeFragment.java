package com.example.heartattackprediction.ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heartattackprediction.AddPatientInfo;
import com.example.heartattackprediction.Database;
import com.example.heartattackprediction.MainActivity;
import com.example.heartattackprediction.PatientAdapter;
import com.example.heartattackprediction.PatientInfo;
import com.example.heartattackprediction.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {


    Database db;
    RecyclerView rvPatient;
    public static List<PatientInfo> patientList = new ArrayList<>();
    private RecyclerView.Adapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        db = new Database(this.getContext());
        rvPatient = (RecyclerView) view.findViewById(R.id.recyclerview);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeFragment.this.getContext(), AddPatientInfo.class));
            }
        });
        rvPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txtName = (TextView) v.findViewById(R.id.txtPatient);
                Toast.makeText(HomeFragment.this.getContext(), txtName.getText().toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomeFragment.this.getContext(), MainActivity.class);
                intent.putExtra("patient_name", txtName.getText().toString());
                startActivity(new Intent(HomeFragment.this.getContext(), MainActivity.class));
            }
        });
        loadPatientInfo();
        return view;
    }

    public void loadPatientInfo() {
        db.open();
        Cursor cursor = db.getPatientInfo();
        patientList = new ArrayList<>();
        int couter = 1;
        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            String mobile = cursor.getString(1);
            String email = cursor.getString(2);
            String address = cursor.getString(3);
            PatientInfo patientInfo = new PatientInfo("" + couter, name, mobile, address, email);
            patientList.add(patientInfo);

        }
        adapter = new PatientAdapter(patientList, this.getContext());
        rvPatient.setAdapter(adapter);
        rvPatient.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter.notifyDataSetChanged();

    }


}