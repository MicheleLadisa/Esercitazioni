package com.example.ladis.argo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeToLogin();
    }

    public void changeToSignup()
    {
        setContentView(R.layout.signup);
        TextView link_login=findViewById(R.id.link_login);
        link_login.setOnClickListener((View view) -> changeToLogin());
    }

    public void changeToLogin()
    {
        setContentView(R.layout.login);
        TextView link_signup=findViewById(R.id.link_signup);
        link_signup.setOnClickListener((View view) -> changeToSignup());
    }
}