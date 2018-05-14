package com.example.ladis.argo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private RFService mService;
    private User user=new User();
    EditText input_name;
    EditText input_pass;
    EditText input_repeatPass;
    TextView change;
    Button btnLoginSingup;
    boolean isLogin =true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mService = RFService.retrofit.create(RFService.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginsingup);
        input_name=findViewById(R.id.input_name);
        input_pass=findViewById(R.id.input_password);
        input_repeatPass=findViewById(R.id.input_repeatpassword);
        change=findViewById(R.id.input_change);
        btnLoginSingup=findViewById(R.id.btn_login_singup);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Change();
            }
        });
        btnLoginSingup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLogin){
                    login();
                }
                else{
                    singup();
                }
            }
        });
    }

    public void login()
    {
        user.setName(input_name.getText().toString());
        user.setPassword(input_pass.getText().toString());
        if(!user.getName().isEmpty() || !user.getPassword().isEmpty())
        {
            Toast notExist=Toast.makeText(this,"The user don't exist",Toast.LENGTH_LONG);
            Toast WrongPassword=Toast.makeText(this,"Wrong Password",Toast.LENGTH_LONG);
            Toast error=Toast.makeText(this,"Connection Error",Toast.LENGTH_LONG);
            Toast succes=Toast.makeText(this,"Login succes",Toast.LENGTH_LONG);

            Intent intent=new Intent(this,MainActivity.class);
            mService.login(user).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.body().equals("LoginSucces"))
                    {
                        succes.show();
                        startActivity(intent);
                    }
                    else if(response.body().equals("WrongPassword"))
                    {
                        WrongPassword.show();
                    }
                    else if(response.body().equals("UserNotExist"))
                    {
                        notExist.show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    error.show();
                }
            });
        }
    }

    public void singup()
    {
        if(input_pass.getText().toString().equals(input_repeatPass.getText().toString()))
        {
            user.setName(input_name.getText().toString());
            user.setPassword(input_pass.getText().toString());

            Toast error=Toast.makeText(this,"Connection Error",Toast.LENGTH_LONG);
            Toast succes=Toast.makeText(this,"Singup succes",Toast.LENGTH_LONG);
            Toast alredyExist=Toast.makeText(this,"The user alredy exist",Toast.LENGTH_LONG);

            Intent intent=new Intent(this,MainActivity.class);

            mService.singup(user).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.body().equals("UserAddedSuccessfully"))
                    {
                        succes.show();
                        startActivity(intent);
                    }
                    else if(response.body().equals("UserAlreadyExists"))
                    {
                        alredyExist.show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    error.show();
                }
            });
        }
        else
        {
            Toast.makeText(this,"Wrong password",Toast.LENGTH_LONG).show();
        }
    }

    public void Change()
    {
        isLogin =!isLogin;
        if(isLogin)
        {
            btnLoginSingup.setText("Login");
            input_repeatPass.setVisibility(View.INVISIBLE);
            change.setText("No account yet? Create one");
            input_pass.setText("");
            input_repeatPass.setText("");
            input_name.setText("");
        }
        else
        {
            btnLoginSingup.setText("Singup");
            input_repeatPass.setVisibility(View.VISIBLE);
            change.setText("Already a member? Login");
            input_pass.setText("");
            input_repeatPass.setText("");
            input_name.setText("");
        }
    }
}