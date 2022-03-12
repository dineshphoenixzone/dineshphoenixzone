package com.example.heartattackprediction;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.ViewHolder> {


    private List<PatientInfo> listItems;
    private Context context;

    public PatientAdapter(List<PatientInfo> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_patient_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final PatientInfo patientInfo = listItems.get(position);
        holder.txtAddress.setText(patientInfo.getAddress());
        holder.txtMobile.setText(patientInfo.getMobile());
        holder.txtName.setText(patientInfo.getName());
        holder.btnPredict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("patient_name", patientInfo.getName());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtMobile, txtAddress;
        Button btnPredict;

        public ViewHolder(View view) {
            super(view);
            txtName = (TextView) view.findViewById(R.id.txtPatient);
            txtMobile = (TextView) view.findViewById(R.id.txtMobile);
            txtAddress = (TextView) view.findViewById(R.id.txtAddress);
            btnPredict = (Button) view.findViewById(R.id.btnPredict);

        }

    }
}
