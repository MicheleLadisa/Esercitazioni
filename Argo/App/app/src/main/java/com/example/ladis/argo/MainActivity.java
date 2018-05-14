package com.example.ladis.argo;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.Gravity.CENTER_HORIZONTAL;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by ladis on 14/05/2018.
 */

public class MainActivity extends AppCompatActivity {
    ScrollView scrollView;
    Button btnDelete;
    Button btnChangePsw;
    RFService mService;

    protected void onCreate(Bundle savedInstanceState) {
        mService = RFService.retrofit.create(RFService.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=getIntent();
        scrollView=findViewById(R.id.scrollViewll);
        btnDelete=findViewById(R.id.delete_account);
        btnChangePsw=findViewById(R.id.change_password);
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
        });
        getEventi();
    }

    public void changePsw(){

    }

    public void deleteAccount(){

    }

    public void getEventi(){
        mService.getEventi().enqueue(new Callback<Eventi>()
        {
            private ArrayList<Evento> eventi=new ArrayList<>();
            @Override
            public void onResponse(Call<Eventi> call, Response<Eventi> response) {
                eventi=(ArrayList<Evento>)response.body().getEventi();
                showEventi(eventi);
            }

            @Override
            public void onFailure(Call<Eventi> call, Throwable t) {

            }
        });
    }

    public void showEventi(ArrayList<Evento> eventi)
    {
        scrollView.removeAllViews();
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
        for(final Evento evento:eventi)
        {
            TextView tv = new TextView(this);
            tv.setLayoutParams(params);
            tv.setPadding(0, 30, 0, 30);
            tv.setText(evento.getName());
            tv.setGravity(CENTER_HORIZONTAL);
            tv.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    showEvento(evento);
                }
            });
            linearLayout.addView(tv);
        }
        scrollView.addView(linearLayout);
    }
    public void showEvento(Evento evento)
    {
        AlertDialog.Builder alert=new AlertDialog.Builder(this);
        alert.setTitle(evento.getName());
        String s="Indirizzo: "+evento.getIndirizzo()+"\nInizio: "+evento.getDateStart()+"\nFine: "+evento.getDateEnd();
        alert.setMessage(s);
        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        Dialog dialog=alert.create();
        dialog.show();
    }
}
