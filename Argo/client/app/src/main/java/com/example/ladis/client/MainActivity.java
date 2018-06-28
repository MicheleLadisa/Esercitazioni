package com.example.ladis.client;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by ladis on 14/05/2018.
 */

public class MainActivity extends AppCompatActivity {
    ListView listView;
    //Button btnDelete;
    //Button btnChangePsw;
    Button btnAddEvent;
    RFService mService;
    private User user = new User();
    private LocationUser locationUser = new LocationUser();
    EditText et;
    Eventi listaEventi;
    private String providerId = LocationManager.GPS_PROVIDER;
    private Geocoder geo = null;
    private LocationManager locationManager = null;
    private static final int MIN_DIST = 50;
    private static final int MIN_PERIOD = 30000;

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            locationUser.setLocation(location);
            getEventi();
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        mService = RetrofitClient.GetClient().create(RFService.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        user.setName(intent.getStringExtra("username"));
        user.setPassword(intent.getStringExtra("psw"));

        listView = findViewById(R.id.listView);
        btnAddEvent =findViewById(R.id.btn_addEvent);
        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEvent();
            }
        });
        /*btnDelete = findViewById(R.id.delete_account);
        btnChangePsw = findViewById(R.id.change_password);
        btnChangePsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePsw();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAccount();
            }
        });*/
        listaEventi = new Eventi();

        //geo = new Geocoder(this, Locale.getDefault());
        //locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        //locationManager.requestLocationUpdates(providerId, MIN_PERIOD, MIN_DIST, locationListener);
    }

    @Override
    protected void onResume() {

        super.onResume();
        getEventi();
    }
    public void changePsw(){
        LinearLayout layout=new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);

        et =new EditText(this);
        et.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        et.setHint("New Password");

        final Toast toast=Toast.makeText(this,"",Toast.LENGTH_LONG);

        layout.addView(et);
        AlertDialog.Builder alert= new AlertDialog.Builder(this);
        alert.setView(layout);
        alert.setTitle("Change Password");

        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String oldPsw =user.getPassword();
                user.setPassword(et.getText().toString());
                mService.changePassword(user).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.body().equals("UpdateSucces")) {
                            toast.setText(response.body());
                            toast.show();
                        }
                        else {
                            toast.setText(response.body());
                            toast.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        user.setPassword(oldPsw);
                        toast.setText("Error");
                        toast.show();
                    }
                });
            }
        });
        Dialog dialog=alert.create();
        dialog.show();
    }

    public void deleteAccount(){
        final Toast toast=Toast.makeText(this,"error",Toast.LENGTH_LONG);
        final Intent intent = new Intent(this, LoginActivity.class);
        mService.deleteAccount(user.getName()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                startActivity(intent);
                myFinish();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                toast.setText(t.getMessage());
                toast.show();
            }
        });
    }

    public void addEvent ()
    {
        Intent intent=new Intent(this,AddEventActivity.class);
        intent.putExtra("username", user.getName());
        intent.putExtra("psw", user.getPassword());
        startActivity(intent);
    }

    public void getEventi(){
        final Toast toast=Toast.makeText(this,"Error",Toast.LENGTH_LONG);
        mService.getEventi().enqueue(new Callback<Evento[]>() {
            @Override
            public void onResponse(Call<Evento[]> call, Response<Evento[]> response) {
                Evento[] eventi=response.body();
                if(eventi!=null)
                {
                    List<Evento> list= new LinkedList<>();
                    Location locationEvento=new Location("");
                    for(final Evento evento:eventi)
                    {
                        locationEvento.setLatitude(evento.getLatitude());
                        locationEvento.setLongitude(evento.getLongitude());
                        if(locationUser.getLocation().distanceTo(locationEvento)<=10000)
                        list.add(evento);
                    }
                    listaEventi.setEventi(list);
                    showEventi();
                }
                else
                    {
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<Evento[]> call, Throwable t) {
                toast.show();
            }
        });
    }

    public void showEventi()
    {
        final EventAdapter adapter=new EventAdapter(this,R.layout.row,listaEventi.getEventi());
        listView.setAdapter(adapter);
        for(final Evento evento:listaEventi.getEventi())
        {
            AdapterView.OnItemClickListener clickListener= new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Evento evento=(Evento)adapter.getItem(i);
                    showEvento(evento);
                }
            };
            listView.setOnItemClickListener(clickListener);
        }
    }

    public void showEvento(Evento evento)
    {
        Intent intent=new Intent(this,EventActivity.class);
        intent.putExtra("username", user.getName());
        intent.putExtra("psw", user.getPassword());
        intent.putExtra("lat",locationUser.getLocation().getLatitude());
        intent.putExtra("lon",locationUser.getLocation().getLongitude());

        intent.putExtra("eName", evento.getName());
        intent.putExtra("eDescrizione",evento.getDescrizione());
        intent.putExtra("eDateStart",evento.getDateStart());
        intent.putExtra("eDateEnd",evento.getDateEnd());
        intent.putExtra("eIndirizzo",evento.getIndirizzo());
        intent.putExtra("eCreatore",evento.getCreator());
        intent.putExtra("eLat",evento.getLatitude());
        intent.putExtra("eLon",evento.getLongitude());

        startActivity(intent);
    }
    private void myFinish(){
        this.finish();
    }
}