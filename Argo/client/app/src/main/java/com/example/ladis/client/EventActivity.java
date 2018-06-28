package com.example.ladis.client;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class EventActivity extends AppCompatActivity implements OnMapReadyCallback{
    protected User user=new User();
    protected LocationUser locationUser=new LocationUser();
    protected MyDate ds;
    protected MyDate de;

    protected Evento evento=new Evento();

    protected TextView eName ;
    protected TextView eDescrizione ;
    protected TextView eDateStart ;
    protected TextView eDateEnd ;
    protected TextView eIndirizzo ;
    protected TextView eCreatore ;

    protected GoogleMap mMap;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_layout);

        Intent intent=getIntent();
        user.setName(intent.getStringExtra("username"));
        user.setPassword(intent.getStringExtra("psw"));
        Location location=new Location("");
        location.setLatitude(intent.getDoubleExtra("lat",0));
        location.setLongitude(intent.getDoubleExtra("lon",0));
        locationUser.setLocation(location);

        evento.setName(intent.getStringExtra("eName"));
        evento.setDescrizione(intent.getStringExtra("eDescrizione"));
        evento.setIndirizzo(intent.getStringExtra("eIndirizzo"));
        evento.setCreator(intent.getStringExtra("eCreatore"));
        evento.setLatitude(intent.getDoubleExtra("eLat",0));
        evento.setLongitude(intent.getDoubleExtra("eLon",0));
        ds=new MyDate(intent.getStringExtra("eDateStart"));
        de=new MyDate(intent.getStringExtra("eDateEnd"));

        eName=findViewById(R.id.eName);
        eDescrizione=findViewById(R.id.eDescrizione);
        eDateStart=findViewById(R.id.eDateStart);
        eDateEnd=findViewById(R.id.eDateEnd);
        eIndirizzo=findViewById(R.id.eIndirizzo);
        eCreatore=findViewById(R.id.eCreatore);

        eName.setText(evento.getName());
        eDescrizione.setText(evento.getDescrizione());
        eIndirizzo.setText(evento.getIndirizzo());
        eCreatore.setText("creato da "+evento.getCreator());
        eDateStart.setText("Start on "+ds.giorno+"/"+ds.mese+"/"+ds.anno+" at "+ds.ora+":"+ds.minuti);
        eDateEnd.setText("Ends "+de.giorno+"/"+de.mese+"/"+de.anno+" at "+de.ora+":"+de.minuti);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng utente=new LatLng(locationUser.getLocation().getLatitude(),locationUser.getLocation().getLongitude());
        LatLng pEvento=new LatLng(evento.getLatitude(),evento.getLongitude());
        mMap.addMarker(new MarkerOptions().position(utente).title("you"));
        mMap.addMarker(new MarkerOptions().position(pEvento).title(evento.getName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pEvento));
    }
}
