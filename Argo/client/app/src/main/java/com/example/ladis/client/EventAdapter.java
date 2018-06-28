package com.example.ladis.client;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ladis on 26/06/2018.
 */

public class EventAdapter extends ArrayAdapter<Evento> {
    public EventAdapter(Context context, int textViewResourceId, List<Evento> eventi) {
        super(context,textViewResourceId,eventi);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getViewOptimize(position, convertView, parent);
    }

    private View getViewOptimize(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row, null);
            viewHolder = new ViewHolder();
            viewHolder.name = convertView.findViewById(R.id.eventName);
            viewHolder.date = convertView.findViewById(R.id.eventDate);
            viewHolder.distance=convertView.findViewById(R.id.eventDistance);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Evento evento=getItem(position);
        Location locationEvento = new Location("");
        locationEvento.setLatitude(evento.getLatitude());
        locationEvento.setLongitude(evento.getLongitude());
        viewHolder.name.setText(evento.getName());
        MyDate ds= new MyDate(evento.getDateStart());
        viewHolder.date.setText("il "+ds.giorno+"/"+ds.mese+"/"+ds.anno+" alle "+ds.ora+":"+ds.minuti);
        int distanza=Math.round(viewHolder.myLocation.distanceTo(locationEvento));
        if(distanza>1000) {
            viewHolder.distance.setText(distanza/1000 + " Km");
        }
        else{
            viewHolder.distance.setText(distanza + " m");
        }
        return convertView;
    }
    private class ViewHolder {
        public TextView name;
        public TextView date;
        public TextView distance;
        public Location myLocation;
        ViewHolder()
        {
            myLocation=new Location("");
            myLocation.setLatitude(45.491371);
            myLocation.setLongitude(12.253303);
        }
    }
}
