package com.example.tanmayvakare.bustrackingsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import static android.media.CamcorderProfile.get;

public class SearchBus extends AppCompatActivity {

    String source;
    String dest;
    RecyclerView result;
    FirebaseDatabase database;
    DatabaseReference dataRef;
    BusListAdapter listAdapter;
    GridLayoutManager listManager;

    ArrayList<DataSnapshot> busDs;
    ArrayList<BusDetails> busList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bus);

        busDs = new ArrayList<DataSnapshot>();

        database = FirebaseDatabase.getInstance();

        dataRef = database.getReference("/");

        busList = new ArrayList<BusDetails>();

        result = (RecyclerView) findViewById(R.id.search_result);

    }

    private void checkRoutes(String source, String dest, DataSnapshot dataSnapshot) {

        ArrayList<DataSnapshot> routes = new ArrayList<DataSnapshot>();

        for (DataSnapshot ds : dataSnapshot.child("Route_Details").getChildren()) {
            routes.add(ds);
        }

        for (DataSnapshot route : routes) {

            for (int i = 0; i < route.getChildrenCount(); i++) {

                if (source.equalsIgnoreCase(route.child("" + i).child("stop").getValue().toString())) {

                    for (int j = i + 1; j < route.getChildrenCount(); j++) {

                        if (dest.equalsIgnoreCase(route.child("" + j).child("stop").getValue().toString())) {

                            if (route.child("" + i).child("route").getValue().toString().equalsIgnoreCase(route.child("" + j).child("route").getValue().toString())) {

                                String routeS = route.child("" + i).child("route").getValue().toString();

                                getBus(dataSnapshot , routeS);
                            }
                        }
                    }
                }
            }

        }
    }

    private void getBus(DataSnapshot dataSnapshot, String routeS) {

        ArrayList<String> bus = new ArrayList<String>();

        for(DataSnapshot ds : dataSnapshot.child("Routes").getChildren()){

            if (routeS.equalsIgnoreCase(ds.child("route").getValue().toString())) {

                bus.add(ds.child("bus_no").getValue().toString());
            }
        }

        for (String each : bus){

            busDs.add(dataSnapshot.child("Bus_details").child(each));
        }
    }

    private void generateList() {

        String no;
        double locLat;
        double locLong;
        String source;
        String dest;

        busList.clear();


        for(DataSnapshot bus : busDs){
            no = bus.child("bus_no").getValue(String.class);
            locLat = bus.child("loclat").getValue(Double.class);
            locLong = bus.child("loclong").getValue(Double.class);
            source = bus.child("source").getValue(String.class);
            dest = bus.child("dest").getValue(String.class);

            busList.add(new BusDetails(no,locLat,locLong,source,dest));
        }
    }


    public void startSearch(View view) {

        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    source = ((EditText) findViewById(R.id.source_in)).getText().toString();
                    dest = ((EditText) findViewById(R.id.dest_in)).getText().toString();
                    checkRoutes(source, dest, dataSnapshot);
                    generateList();
                    listAdapter = new BusListAdapter(getBaseContext(),busList);
                    listManager = new GridLayoutManager(getBaseContext(),1);
                    result.setLayoutManager(listManager);
                    result.setAdapter(listAdapter);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
