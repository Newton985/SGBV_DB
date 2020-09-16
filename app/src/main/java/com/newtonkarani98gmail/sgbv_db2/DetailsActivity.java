package com.newtonkarani98gmail.sgbv_db2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_view);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng myLocation=new LatLng(-0.702545,34.775399);
        googleMap.addMarker(new MarkerOptions().position(myLocation).title("Case Site"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
        googleMap.setMinZoomPreference(20);
        googleMap.setMaxZoomPreference(80);
    }
}
