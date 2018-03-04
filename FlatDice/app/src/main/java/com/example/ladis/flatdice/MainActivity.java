package com.example.ladis.flatdice;

import android.app.Fragment;
import android.app.FragmentManager;
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
                        changeFragment("toLeft");
                    }
                    else{
                        changeFragment("toRigth");
                    }
                }
                else if(Math.abs(deltay)>MIN_DISTANCE)
                {
                    if(y2>y1){
                        changeFragment("toTop");
                    }
                    else{
                        changeFragment("toBot");
                    }
                }
                break;
        }
        return true;
    }

    public void changeFragment(String direction){
        if(findViewById(R.id.fragment)!=null){
            FragmentManager fm = getFragmentManager();
            Fragment nextFragment= new FragmentDice();
            FragmentTransaction ft=fm.beginTransaction();
            switch (direction){
                case "toLeft":
                    ft.setCustomAnimations(R.animator.from_left,R.animator.to_rigth);
                    break;
                case "toRigth":
                    ft.setCustomAnimations(R.animator.from_rigth,R.animator.to_left);
                    break;
                case "toTop":
                    ft.setCustomAnimations(R.animator.from_bot,R.animator.to_top);
                    nextFragment= new FragmentCoin();
                    break;
                case "toBot":
                    ft.setCustomAnimations(R.animator.from_top,R.animator.to_bot);
                    break;
            }
            ft.replace(R.id.fragment,nextFragment);
            ft.commit();
        }
    }
}