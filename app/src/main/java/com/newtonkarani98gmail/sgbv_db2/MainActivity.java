package com.newtonkarani98gmail.sgbv_db2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextInputEditText contact,password;
    ProgressBar progressBar;
    String contact_,password_;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contact=(TextInputEditText)findViewById(R.id.contact);
        password=(TextInputEditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.submit_bt);
        progressBar=(ProgressBar)findViewById(R.id.home_bar);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (contact.getText().toString().matches("")){
                    Toast.makeText(getApplicationContext(), "INPUT PHONE NUMBER", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.getText().toString().matches("")){
                    Toast.makeText(getApplicationContext(), "INPUT PASSWORD", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                contact_=contact.getText().toString();
                password_=password.getText().toString();
                String url="http://www.gbv.newtonsoft.co.ke/index.php";
                RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

                StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                        try {
                            JSONArray jsonArray=new JSONArray(response);
                            JSONObject jsonObject=jsonArray.getJSONObject(0);
                            SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor=preferences.edit();
                            editor.putString("userdata",jsonObject.toString());
                            editor.apply();
                            Intent intent=new Intent(getApplicationContext(),CasesActivity.class);
                            startActivity(intent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params=new HashMap<>();
                        params.put("action","validateuser");
                        params.put("username",contact_);
                        params.put("password",password_);
                        return params;
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        return super.getHeaders();
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
    }

    public void goToRegister(View view){
        Intent intent=new Intent(getApplicationContext(),RegisterActivity.class);
        startActivity(intent);
    }
}
