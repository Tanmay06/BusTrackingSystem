package com.example.tanmayvakare.bustrackingsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import static android.R.string.no;

public class TrackBus extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference busData;
    ArrayList<BusDetails> busList = new ArrayList<BusDetails>();
    RecyclerView busListView;
    BusListAdapter listAdapter;
    GridLayoutManager listManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_bus);

        database = FirebaseDatabase.getInstance();
        busData = database.getReference("/Bus_details");
        busListView = (RecyclerView) findViewById(R.id.bus_list);

        busData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    generateList(dataSnapshot);
                    listAdapter = new BusListAdapter(getBaseContext(),busList);
                    listManager = new GridLayoutManager(getBaseContext(),1);
                    busListView.setLayoutManager(listManager);
                    busListView.setAdapter(listAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void generateList(DataSnapshot dataSnapshot) {

        String no;
        double locLat;
        double locLong;
        String source;
        String dest;
        ArrayList<DataSnapshot> datalist = new ArrayList<DataSnapshot>();
        Iterable<DataSnapshot> dataitr = dataSnapshot.getChildren();
        busList.clear();
        for(DataSnapshot ds : dataitr){
            datalist.add(ds);
        }

        for(int i = 0; i < dataSnapshot.getChildrenCount();i++){
            no = datalist.get(i).child("bus_no").getValue(String.class);
            locLat = datalist.get(i).child("loclat").getValue(Double.class);
            locLong = datalist.get(i).child("loclong").getValue(Double.class);
            source = datalist.get(i).child("source").getValue(String.class);
            dest = datalist.get(i).child("dest").getValue(String.class);

            busList.add(new BusDetails(no,locLat,locLong,source,dest));
        }
    }
}
