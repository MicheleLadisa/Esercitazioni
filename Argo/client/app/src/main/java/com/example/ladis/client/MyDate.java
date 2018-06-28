package com.example.ladis.client;

/**
 * Created by ladis on 26/06/2018.
 */

public class MyDate {
    int anno;
    int mese;
    int giorno;
    int ora;
    int minuti;

    public MyDate()
    {
        anno=0;
        mese=0;
        giorno=0;
        ora=0;
        minuti=0;
    }

    public MyDate(String date) {
        anno=Integer.parseInt(date.substring(0,4));
        mese=Integer.parseInt(date.substring(5,7));
        giorno=Integer.parseInt(date.substring(8,10));
        ora=Integer.parseInt(date.substring(11,13));
        minuti=Integer.parseInt(date.substring(14,16));
    }

    public void setDateByString(String date){
        anno=Integer.parseInt(date.substring(0,4));
        mese=Integer.parseInt(date.substring(5,7));
        giorno=Integer.parseInt(date.substring(8,10));
        ora=Integer.parseInt(date.substring(11,13));
        minuti=Integer.parseInt(date.substring(14,16));
    }
    public String getStringToSend(){
        String s=anno+"-";
        if(mese<10) s=s+0+mese+"-"; else s=s+mese+"-";
        if(giorno<10) s=s+0+giorno+"T"; else s=s+giorno+"T";
        if(ora<10) s=s+0+ora+":"; else s=s+ora+":";
        if(minuti<10) s=s+0+minuti+":00.000Z"; else s=s+minuti+":00.000Z";
        return s;
    }
}