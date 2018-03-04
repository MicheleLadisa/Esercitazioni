package com.example.ladis.flatdice;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {

    float x1;
    float x2;
    float y1;
    float y2;
    final static float MIN_DISTANCE = 150.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(findViewById(R.id.fragment)!=null)
        {
            FragmentDice fd= new FragmentDice();
            FragmentTransaction ft=getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment,fd);
            ft.commit();
        }
    }

    public boolean onTouchEvent(MotionEvent event){
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1=event.getX();
                y1=event.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2=event.getX();
                y2=event.getY();

                float deltax=x2-x1;
                float deltay=y2-y1;

                if(Math.abs(deltax)>MIN_DISTANCE)
                {
                    if(x2>x1){
                        changeFragment("left");
                    }
                    else{
                        changeFragment("rigth");
                    }
                }
                else if(Math.abs(deltay)>MIN_DISTANCE)
                {
                    if(y2>y1){
                        changeFragment("top");
                    }
                    else{
                        changeFragment("bot");
                    }
                }
                break;
        }
        return true;
    }

    public void changeFragment(String direction){

    }
}