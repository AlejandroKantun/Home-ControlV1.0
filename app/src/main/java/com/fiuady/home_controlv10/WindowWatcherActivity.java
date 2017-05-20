package com.fiuady.home_controlv10;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class WindowWatcherActivity extends AppCompatActivity {

    Switch sw1 ,sw2, sw3, sw4, sw5,pir,alarm;
    TextView resultado;
    EditText et;



    byte Sensores = 0;
    byte Activar = 0;
    double SW1,SW2,SW3,SW4,SW5,PIR,ALARM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_watcher);

        sw1 = (Switch) findViewById(R.id.SW1);
        sw2 = (Switch) findViewById(R.id.SW2);
        sw3 = (Switch) findViewById(R.id.SW3);
        sw4 = (Switch) findViewById(R.id.SW4);
        sw5 = (Switch) findViewById(R.id.SW5);
        pir = (Switch) findViewById(R.id.PIR);
        alarm = (Switch) findViewById(R.id.Alarma);







        resultado = (TextView) findViewById(R.id.resultado);



        sw1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Log.i("Log1",String.valueOf(SW1));
                Log.i("Log1",String.valueOf(SW2));
                Log.i("Log1",String.valueOf(SW3));



                resultado.setText(String.valueOf(GetSensores()));


            }
        });

        sw2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Log.i("Log1",String.valueOf(SW1));
                Log.i("Log1",String.valueOf(SW2));
                Log.i("Log1",String.valueOf(SW3));



                resultado.setText(String.valueOf(GetSensores()));


            }
        });

        sw3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Log.i("Log1",String.valueOf(SW1));
                Log.i("Log1",String.valueOf(SW2));
                Log.i("Log1",String.valueOf(SW3));



                resultado.setText(String.valueOf(GetSensores()));


            }
        });

        sw4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Log.i("Log1",String.valueOf(SW1));
                Log.i("Log1",String.valueOf(SW2));
                Log.i("Log1",String.valueOf(SW3));



                resultado.setText(String.valueOf(GetSensores()));


            }
        });

        sw5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Log.i("Log1",String.valueOf(SW1));
                Log.i("Log1",String.valueOf(SW2));
                Log.i("Log1",String.valueOf(SW3));



                resultado.setText(String.valueOf(GetSensores()));


            }
        });

        pir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Log.i("Log1",String.valueOf(SW1));
                Log.i("Log1",String.valueOf(SW2));
                Log.i("Log1",String.valueOf(SW3));



                resultado.setText(String.valueOf(GetSensores()));


            }
        });

        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Log.i("Log1",String.valueOf(SW1));
                Log.i("Log1",String.valueOf(SW2));
                Log.i("Log1",String.valueOf(SW3));



                resultado.setText(String.valueOf(GetSensores()));


            }
        });


    }


    public byte GetSensores()
    {
        if(sw1.isChecked()==true)
        {
            SW1 = Math.pow(2,0);
        }

        else SW1 = 0;

        if(sw2.isChecked()==true)
        {
            SW2 = Math.pow(2,1);
        }

        else SW2 = 0;

        if(sw3.isChecked()==true)
        {
            SW3 = Math.pow(2,2);
        }

        else SW3 = 0;

        if(sw4.isChecked()==true)
        {
            SW4 = Math.pow(2,3);
        }

        else SW4 = 0;
        if(sw5.isChecked()==true)
        {
            SW5 = Math.pow(2,4);
        }

        else SW5 = 0;

        if(pir.isChecked()==true)
        {
            PIR = Math.pow(2,5);
        }

        else PIR = 0;

        if(alarm.isChecked()==true)
        {
            ALARM = Math.pow(2,6);
        }

        else ALARM = 0;


        return Sensores = Byte.valueOf( (byte) (SW1+SW2+SW3+SW4+SW5+PIR+ALARM));
    }



}
