package com.newtonkarani98gmail.sgbv_db2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.newtonkarani98gmail.sgbv_db2.Adapters.CasesAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CasesActivity extends AppCompatActivity {

    RecyclerView cases;
    ProgressBar progressBar;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cases);
        cases=(RecyclerView)findViewById(R.id.my_cases);
        progressBar=(ProgressBar)findViewById(R.id.home_bar);
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String data=preferences.getString("userdata","");
        if (data.equals("")){
            Toast.makeText(getApplicationContext(), "Error in preferences", Toast.LENGTH_LONG).show();
            {}
        }
        try {
            JSONObject data_ob=new JSONObject(data);
            id=data_ob.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url="http://www.gbv.newtonsoft.co.ke/index.php";
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
               // Toast.makeText(CasesActivity.this, response, Toast.LENGTH_LONG).show();

                try {
                    JSONArray jsonArray=new JSONArray(response);
                    cases.setItemAnimator(new DefaultItemAnimator());
                    cases.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
                    CasesAdapter adapter=new CasesAdapter(jsonArray);
                    cases.setAdapter(adapter);
                    for (int o=0;o<jsonArray.length();o++){
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
                params.put("action","showmycasesdb");
                params.put("id",id);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return super.getHeaders();
            }
        };
        requestQueue.add(stringRequest);
    }
}
