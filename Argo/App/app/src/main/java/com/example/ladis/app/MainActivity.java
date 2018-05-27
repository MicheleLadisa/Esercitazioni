package com.example.ladis.app;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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
    private User user=new User();
    EditText et;

    protected void onCreate(Bundle savedInstanceState) {
        mService = RetrofitClient.GetClient().create(RFService.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent=getIntent();
        user.setName(intent.getStringExtra("username"));
        user.setPassword(intent.getStringExtra("psw"));

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
                        if(response.body().equals("UpdateSucces"))
                        {
                            toast.setText(response.body());
                            toast.show();
                        }
                        else
                        {
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
        mService.deleteAccount(user.getName()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                toast.setText(t.getMessage());
                toast.show();
            }
        });
    }

    public void getEventi(){
        final Toast toast=Toast.makeText(this,"Error",Toast.LENGTH_LONG);
        mService.getEventi().enqueue(new Callback<Evento[]>() {
            @Override
            public void onResponse(Call<Evento[]> call, Response<Evento[]> response) {
                Evento[] eventi=response.body();
                if(eventi!=null)
                {
                    showEventi(eventi);
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

    public void showEventi(Evento[] eventi)
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