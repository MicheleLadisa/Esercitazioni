package com.example.ladis.argo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.PUT;

public class MainActivity extends AppCompatActivity {

    private RFService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mService = RFService.retrofit.create(RFService.class);
        super.onCreate(savedInstanceState);
        changeToLogin();
    }

    public void changeToSignup()
    {
        setContentView(R.layout.signup);
        TextView link_login=findViewById(R.id.link_login);
        link_login.setOnClickListener((View view) -> changeToLogin());
        //Toast.makeText(MainActivity.this,getUtenti(),Toast.LENGTH_SHORT).show();
        getUtenti();
    }

    public void changeToLogin()
    {
        setContentView(R.layout.login);
        TextView link_signup=findViewById(R.id.link_signup);
        link_signup.setOnClickListener((View view) -> changeToSignup());
        //Toast.makeText(MainActivity.this,getUtenti(),Toast.LENGTH_SHORT).show();
        getUtenti();
    }

    public void getUtenti(){
        mService.getPojoEventi().enqueue(new Callback<PojoEvent>() {
            @Override
            public void onResponse(Call<PojoEvent> call, Response<PojoEvent> response) {
                    String s = response.toString();
                    Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<PojoEvent> call, Throwable t) {
                Toast.makeText(MainActivity.this,"error",Toast.LENGTH_SHORT).show();
            }
        });
    }
}