package com.example.ladis.flatdice;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Random;

/**
 * Created by ladis on 04/03/2018.
 */

public class FragmentDice  extends Fragment{
     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
         Random r=new Random();
         View v=inflater.inflate(R.layout.dicefragment, container, false);
         ImageView iv=(ImageView) v.findViewById(R.id.DiceImageView);
         int diceface=1+r.nextInt(6);
         switch (diceface) {
             case 1:
                 iv.setImageResource(R.drawable.dice1);
                 break;
             case 2:
                 iv.setImageResource(R.drawable.dice1);
                 break;
             case 3:
                 iv.setImageResource(R.drawable.dice1);
                 break;
             case 4:
                 iv.setImageResource(R.drawable.dice1);
                 break;
             case 5:
                 iv.setImageResource(R.drawable.dice1);
                 break;
             case 6:
                 iv.setImageResource(R.drawable.dice1);
                 break;
         }
         return v;
     }
}
