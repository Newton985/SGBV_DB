package com.newtonkarani98gmail.sgbv_db2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class RegisterActivity extends AppCompatActivity {
    int PERMISSION_ID = 44,REQUEST_CHECK_SETTINGS=10; Location location;
    FusedLocationProviderClient mFusedLocationClient;
    String latitude,longitude,name_,contact_,password_,career;
    RadioGroup category;
    TextInputEditText name,contact,password;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        category=(RadioGroup)findViewById(R.id.career_category);
        name=(TextInputEditText)findViewById(R.id.nametxt);
        contact=(TextInputEditText)findViewById(R.id.contact);
        password=(TextInputEditText)findViewById(R.id.password);
        register=(Button)findViewById(R.id.submit_bt);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLastLocation();
            }
        });
    }

    public void goToLogin(View view){
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }


    private  void doRegister(){
        String url="http://www.gbv.newtonsoft.co.ke/index.php";
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        switch (category.getCheckedRadioButtonId()){
            case R.id.doctor: career="doctor"; break;
            case R.id.lawyer: career="lawyer"; break;
            case R.id.police: career="police"; break;
            case R.id.psychologist: career="psychologist"; break;
            case R.id.chief:career="chief"; break;
            default:
                Toast.makeText(this, "SELECT A SPECIALIZATION", Toast.LENGTH_LONG).show();
                return;
        }
        if (name.getText().toString().matches("")){
            Toast.makeText(this, "PROVIDE A NAME", Toast.LENGTH_SHORT).show();
        }
        else if (contact.getText().toString().matches("") || !contact.getText().toString().startsWith("07") || !contact.getText().toString().startsWith("01")){
            Toast.makeText(this, "PROVIDE A VALID CONTACT", Toast.LENGTH_SHORT).show();
        }
        else if (password.getText().toString().matches("")){
            Toast.makeText(this, "PROVIDE A PASSWORD", Toast.LENGTH_SHORT).show();
        } else {

            final EveAlertDialog eveAlertDialog=new EveAlertDialog(RegisterActivity.this);
            eveAlertDialog.setCancelable(false);
            eveAlertDialog.showProgressDialog();

            name_=name.getText().toString();
            contact_=contact.getText().toString();
            password_=password.getText().toString();
            StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    eveAlertDialog.dismiss();
                    try {
                        JSONArray jsonArray=new JSONArray(response);
                        JSONObject data=jsonArray.getJSONObject(0);
                        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor=preferences.edit();
                        editor.putString("userdata",data.toString());
                        editor.apply();
                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
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
                    params.put("action","registeruser");
                    params.put("latitude",latitude);
                    params.put("longitude",longitude);
                    params.put("proffesion",career);
                    params.put("name",name_);
                    params.put("contact",contact_);
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

    }

    @SuppressLint("MissingPermission")
    public void getLastLocation(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    Toast.makeText(getApplicationContext(), location.toString(), Toast.LENGTH_LONG).show();
                                    latitude=Double.toString(location.getLatitude());
                                    longitude=Double.toString(location.getLongitude());
                                    doRegister();
                                }
                            }
                        }
                );
            } else {

                GoogleApiClient googleApiClient = new GoogleApiClient.Builder(getApplicationContext()).addApi(LocationServices.API).build();
                googleApiClient.connect();
                LocationRequest locationRequest = LocationRequest.create();
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                locationRequest.setInterval(10000);
                locationRequest.setFastestInterval(10000 / 2);
                LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
                builder.setAlwaysShow(true);


                PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
                result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                    @Override
                    public void onResult(LocationSettingsResult result) {
                        final Status status = result.getStatus();
                        switch (status.getStatusCode()) {
                            case LocationSettingsStatusCodes.SUCCESS:
                                Log.i(TAG, "All location settings are satisfied.");
                                break;
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the result
                                    // in onActivityResult().
                                    status.startResolutionForResult(RegisterActivity.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException e) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                                break;
                        }
                    }
                });

            }
        } else {
            requestPermissions();
        }

    }

    // @SuppressLint("MissingPermission")
    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            Toast.makeText(getApplicationContext(), mLastLocation.toString(), Toast.LENGTH_LONG).show();
            latitude=Double.toString(mLastLocation.getLatitude());
            longitude=Double.toString(mLastLocation.getLongitude());
            doRegister();
        }
    };

    private boolean checkPermissions() {
        boolean enabled=false;
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            enabled=true;
        }
        return enabled;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CHECK_SETTINGS){
            getLastLocation();
        }else {
            Toast.makeText(getApplicationContext(), "unable to location", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==44){
            getLastLocation();
        }
    }
}
