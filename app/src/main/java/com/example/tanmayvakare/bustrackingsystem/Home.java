package com.example.tanmayvakare.bustrackingsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void startTrack(View view) {
        Intent track = new Intent(this,TrackBus.class);
        startActivity(track);
    }

    public void seachBus(View view) {
        Toast.makeText(this,"Still Under Construction",Toast.LENGTH_LONG).show();

        //Intent search = new Intent(this, SearchBus.class);
        //startActivity(search);

    }
}
