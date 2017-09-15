package com.example.tanmayvakare.bustrackingsystem;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    FirebaseDatabase database;
    DatabaseReference busRef;
    double loclat;
    double loclong;
    String busno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent call = getIntent();

        database = FirebaseDatabase.getInstance();
        busRef = database.getReference("/Bus_details");

        busno = call.getExtras().getString("BusNo","");

        busRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    loclat = dataSnapshot.child(busno).child("loclat").getValue(Double.class);
                    loclong = dataSnapshot.child(busno).child("loclong").getValue(Double.class);
                    updateMarker(loclat,loclong);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng loc = new LatLng(loclat,loclong);
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(loc).title("Bus"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc,15));
        //mMap.getMyLocation();
    }

    private void updateMarker(double lat, double log){
        LatLng loc = new LatLng(lat,log);
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(loc).title("Bus"));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(loc));
    }
}
