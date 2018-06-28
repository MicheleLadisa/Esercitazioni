package com.example.ladis.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

        mService = RetrofitClient.GetClient().create(RFService.class);
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
            final Toast toast=Toast.makeText(this,"",Toast.LENGTH_LONG);

            final Intent intent=new Intent(this,MainActivity.class);
            intent.putExtra("username",user.getName());
            intent.putExtra("psw",user.getPassword());

            mService.login(user).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.body().equals("LoginSucces"))
                    {
                        startActivity(intent);
                        myFinish();
                    }
                    else if(response.body().equals("WrongPassword"))
                    {
                        toast.setText("Wrong Password");
                        toast.show();
                    }
                    else if(response.body().equals("UserNotExist"))
                    {
                        toast.setText("The user don't exist");
                        toast.show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    toast.setText("Connection Error");
                    toast.show();
                }
            });
        }
    }

    public void singup()
    {
        if(input_pass.getText().toString().equals(input_repeatPass.getText().toString())) {
            Pattern pattern;
            pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,}$");
            Matcher matcher = pattern.matcher(input_pass.getText().toString());
            if (matcher.matches())
            {
                user.setName(input_name.getText().toString());
                user.setPassword(input_pass.getText().toString());
                final Toast toast = Toast.makeText(this, "", Toast.LENGTH_LONG);

                final Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("username", user.getName());
                intent.putExtra("psw", user.getPassword());

                mService.singup(user).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.body().equals("UserAddedSuccessfully")) {
                            startActivity(intent);
                            finish();
                        } else if (response.body().equals("UserAlreadyExists")) {
                            toast.setText("The user alredy exist");
                            toast.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        toast.setText("Connection Error");
                        toast.show();
                    }
                });
            }
            else
            {
                Toast.makeText(this,"The password must have a number, a lower and upper case letter and must be al least six character long",Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(this,"Passwords don't match",Toast.LENGTH_LONG).show();
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

    private void myFinish()
    {
        this.finish();
    }
}