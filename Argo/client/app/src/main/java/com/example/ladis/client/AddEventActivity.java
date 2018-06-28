package com.example.ladis.client;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEventActivity extends AppCompatActivity implements View.OnClickListener{
    RFService mService;
    private User user = new User();
    private Evento evento=new Evento();
    private LocationUser locationUser=new LocationUser();
    MyDate ds=new MyDate();
    MyDate de=new MyDate();
    private int mYear, mMonth, mDay, mHour, mMinute;

    Button btnDateStart, btnTimeStart, btnDateEnd, btnTimeEnd, btnAddEvent;
    TextView dateStart, timeStart, dateEnd, timeEnd;
    EditText nomeEvento, indirizzo, descrizione;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        mService = RetrofitClient.GetClient().create(RFService.class);

        Intent intent = getIntent();
        user.setName(intent.getStringExtra("username"));
        user.setPassword(intent.getStringExtra("psw"));

        btnDateStart= findViewById(R.id.btn_dateStart);
        btnTimeStart= findViewById(R.id.btn_timeStart);
        btnDateEnd=findViewById(R.id.btn_dateEnd);
        btnTimeEnd=findViewById(R.id.btn_timeEnd);
        btnAddEvent=findViewById(R.id.btn_addEvent);

        dateStart=findViewById(R.id.dateStart);
        dateEnd=findViewById(R.id.dateEnd);
        timeStart=findViewById(R.id.timeStart);
        timeEnd=findViewById(R.id.timeEnd);

        nomeEvento=findViewById(R.id.input_NomeEvento);
        indirizzo=findViewById(R.id.input_indirizzo);
        descrizione=findViewById(R.id.input_Descrizione);

        btnDateStart.setOnClickListener(this);
        btnTimeStart.setOnClickListener(this);
        btnDateEnd.setOnClickListener(this);
        btnTimeEnd.setOnClickListener(this);
        btnAddEvent.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if(v== btnDateStart)
        {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            dateStart.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            ds.anno=year;
                            ds.mese=monthOfYear+1;
                            ds.giorno=dayOfMonth;
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        else if(v== btnDateEnd)
        {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            dateEnd.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            de.anno=year;
                            de.mese=monthOfYear+1;
                            de.giorno=dayOfMonth;
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        else if(v== btnTimeStart)
        {
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            timeStart.setText(hourOfDay + ":" + minute);
                            ds.ora=hourOfDay;
                            ds.minuti=minute;
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
        else if(v== btnTimeEnd)
        {
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            timeEnd.setText(hourOfDay + ":" + minute);
                            de.ora=hourOfDay;
                            de.minuti=minute;
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
        else if(v==btnAddEvent)
        {
            addEvent();
        }
    }

    public void addEvent(){
        evento.setName(nomeEvento.getText().toString());
        evento.setCreator(user.getName());
        evento.setDateStart(ds.getStringToSend());
        evento.setDateEnd(de.getStringToSend());
        evento.setLatitude(locationUser.getLocation().getLatitude());
        evento.setLongitude(locationUser.getLocation().getLongitude());
        evento.setIndirizzo(indirizzo.getText().toString());
        evento.setDescrizione(descrizione.getText().toString());

        final Toast toast = Toast.makeText(this, "", Toast.LENGTH_LONG);

        if(!evento.getName().isEmpty()&& ds.giorno!=0 && ds.mese!=0 && de.giorno!=0 && de.mese!=0 && !evento.getIndirizzo().isEmpty() && !evento.getDescrizione().isEmpty())
        {

            mService.addEvent(evento).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.body().equals("EventAddedSuccessfully")){
                        toast.setText("Evento aggionto con successo");
                        toast.show();
                        //finish();
                    }
                    else{
                        toast.setText("Impossibile aggiugere l'evento");
                        toast.show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    toast.setText("Errore di connessione");
                    toast.show();
                }
            });
        }
        else{
            toast.setText("Inserire tutti i campi");
            toast.show();
        }
    }

    private void myFinish()
    {
        this.finish();
    }
}
