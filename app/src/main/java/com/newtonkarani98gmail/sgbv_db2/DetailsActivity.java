package com.newtonkarani98gmail.sgbv_db2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    String longitude,latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_view);
        mapFragment.getMapAsync(this);

        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String data=preferences.getString("victimDetails","");

        try {
            JSONObject jsonObject=new JSONObject(data);
            latitude=jsonObject.getString("latitude");
            longitude=jsonObject.getString("longitude");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        double latDouble=Double.parseDouble(latitude);
        double lonDouble=Double.parseDouble(longitude);

        LatLng myLocation=new LatLng(latDouble,lonDouble);
        googleMap.addMarker(new MarkerOptions().position(myLocation).title("Case Site"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        googleMap.setMinZoomPreference(16);
        googleMap.setMaxZoomPreference(80);
    }
}
