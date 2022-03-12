package com.example.heartattackprediction;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText etCp, etTrestbps, etChol, etfbs, etrestecg, etthalach, etexang, etoldpeak, etslope, etca, etthal, etAge;
    ProgressDialog pDialog;
    TextView txtOut, txtPatientName;
    Spinner spinner;
    JSONParser jsonParser = new JSONParser();
    String patient_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
//        if (intent != null) {
//            patient_name = intent.getExtras().getString("patient_name");
//        }
        txtPatientName = (TextView) findViewById(R.id.txtPatientName);
//        txtPatientName.setText(patient_name);
        spinner = (Spinner) findViewById(R.id.spinner);
        txtOut = (TextView) findViewById(R.id.txtOutput);
        etCp = (EditText) findViewById(R.id.etCp);
        etTrestbps = (EditText) findViewById(R.id.etTrestbps);
        etChol = (EditText) findViewById(R.id.etChol);
        etfbs = (EditText) findViewById(R.id.etfbs);
        etrestecg = (EditText) findViewById(R.id.etrestecg);
        etthalach = (EditText) findViewById(R.id.etthalach);
        etexang = (EditText) findViewById(R.id.etexang);
        etoldpeak = (EditText) findViewById(R.id.etoldpeak);
        etslope = (EditText) findViewById(R.id.etslope);
        etca = (EditText) findViewById(R.id.etca);
        etthal = (EditText) findViewById(R.id.etthal);
        Button btnPredict = (Button) findViewById(R.id.btnPredict);
        Button btnCancel = (Button) findViewById(R.id.btnCancel);
        etAge = (EditText) findViewById(R.id.etAge);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetParamters();
            }
        });
        btnPredict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtOut.setText("");
//                showAlert("This is sample Result");

                if (etCp.getText().toString().equals("") || etTrestbps.getText().toString().equals("") || etChol.getText().toString().equals("") ||
                        etfbs.getText().toString().equals("") || etrestecg.getText().toString().equals("") || etthalach.getText().toString().equals("") ||
                        etAge.getText().toString().equals("") || etexang.getText().toString().equals("") || etoldpeak.getText().toString().equals("")
                        || etslope.getText().toString().equals("") || etca.getText().toString().equals("") || etthal.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "Please enter all values", Toast.LENGTH_SHORT).show();
                } else {
//                    new SendParams().execute();
                    predictDisease();
                }
            }
        });
    }

    public void resetParamters() {
        txtOut.setText("");
        etCp.setText("");
        etTrestbps.setText("");
        etChol.setText("");
        etfbs.setText("");
        etrestecg.setText("");
        etthalach.setText("");
        etexang.setText("");
        etoldpeak.setText("");
        etslope.setText("");
        etca.setText("");
        etthal.setText("");
        etAge.setText("");
        spinner.setSelection(0);
    }

    public class SendParams extends AsyncTask<String, String, String> {
        List<NameValuePair> params = new ArrayList<>();
        int output = -1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            params.add(new BasicNameValuePair("age", etAge.getText().toString()));
            params.add(new BasicNameValuePair("sex", "" + spinner.getSelectedItemPosition()));
            params.add(new BasicNameValuePair("cp", etCp.getText().toString()));
            params.add(new BasicNameValuePair("trestbps", etTrestbps.getText().toString()));
            params.add(new BasicNameValuePair("chol", etChol.getText().toString()));
            params.add(new BasicNameValuePair("fbs", etfbs.getText().toString()));
            params.add(new BasicNameValuePair("restecg", etrestecg.getText().toString()));
            params.add(new BasicNameValuePair("thalach", etthalach.getText().toString()));
            params.add(new BasicNameValuePair("exang", etexang.getText().toString()));
            params.add(new BasicNameValuePair("oldpeak", etoldpeak.getText().toString()));
            params.add(new BasicNameValuePair("slope", etslope.getText().toString()));
            params.add(new BasicNameValuePair("ca", etca.getText().toString()));
            params.add(new BasicNameValuePair("thal", etthal.getText().toString()));
            params.add(new BasicNameValuePair("thal", etthal.getText().toString()));

            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            JSONObject object = jsonParser.makeHttpRequest(ServerUtility.serverURL, "POST", params);
            try {
                output = object.getInt("value");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (pDialog.isShowing())
                pDialog.dismiss();

            if (output != -1) {
                String str = "";
                if (output == 0) {
                    str = "According to provided parameter values, there is no possibility of heart attack";

                } else {
                    str = "According to provided parameter values, there is possibility of heart attack.\n\n";
                    String precautions = "Then, follow the tips below for a healthy lifestyle.\n" +
                            "Stop smoking. Tobacco use is a major risk factor for heart disease. ...\n" +
                            "Control your blood pressure. ...\n" +
                            "Control your cholesterol levels. ...\n" +
                            "Check for diabetes. ...\n" +
                            "Exercise. ...\n" +
                            "Eat a heart-healthy diet. ...\n" +
                            "Control your stress level.";
                    str = str + precautions;

                }
                showAlert(str);
                txtOut.setText(str);
            }
        }
    }

    private void showAlert(String msg) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(
                MainActivity.this);
        builder.setTitle("Result");
        builder.setMessage(msg);
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        //  Toast.makeText(getApplicationContext(),"Yes is clicked",Toast.LENGTH_LONG).show();
                        resetParamters();
                    }
                });
        builder.show();
    }


    private void predictDisease()
    {

        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerUtility.serverURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            final JSONObject jsonObject = new JSONObject(response);
                            try {
                             int   output = jsonObject.getInt("value");
                                if (output != -1) {
                                    String str = "";
                                    if (output == 0) {
                                        str = "According to provided parameter values, there is no possibility of heart attack";

                                    } else {
                                        str = "According to provided parameter values, there is possibility of heart attack.\n\n";
                                        String precautions = "Then, follow the tips below for a healthy lifestyle.\n" +
                                                "Stop smoking. Tobacco use is a major risk factor for heart disease. ...\n" +
                                                "Control your blood pressure. ...\n" +
                                                "Control your cholesterol levels. ...\n" +
                                                "Check for diabetes. ...\n" +
                                                "Exercise. ...\n" +
                                                "Eat a heart-healthy diet. ...\n" +
                                                "Control your stress level.";
                                        str = str + precautions;

                                    }
                                    showAlert(str);
                                    txtOut.setText(str);
                                }
                                if (pDialog.isShowing())
                                    pDialog.dismiss();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("JSONError: ", error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("age", etAge.getText().toString());
                params.put("sex", "" + spinner.getSelectedItemPosition());
                params.put("cp", etCp.getText().toString());
                params.put("trestbps", etTrestbps.getText().toString());
                params.put("chol", etChol.getText().toString());
                params.put("fbs", etfbs.getText().toString());
                params.put("restecg", etrestecg.getText().toString());
                params.put("thalach", etthalach.getText().toString());
                params.put("exang", etexang.getText().toString());
                params.put("oldpeak", etoldpeak.getText().toString());
                params.put("slope", etslope.getText().toString());
                params.put("ca", etca.getText().toString());
                params.put("thal", etthal.getText().toString());
                return params;
            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }
}
