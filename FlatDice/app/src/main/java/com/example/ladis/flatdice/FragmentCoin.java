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

public class FragmentCoin extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Random r=new Random();
        View v=inflater.inflate(R.layout.dicefragment, container, false);
        ImageView iv=(ImageView) v.findViewById(R.id.DiceImageView);
        boolean coinface=r.nextBoolean();
        if(coinface) {
            iv.setImageResource(R.drawable.testa);
        }
        else {
            iv.setImageResource(R.drawable.croce);
        }
        return v;
    }
}