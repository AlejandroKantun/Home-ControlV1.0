package com.fiuady.home_controlv10;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class WindowWatcherActivity extends AppCompatActivity {

    Switch sw1 ,sw2, sw3, sw4, sw5,pir,alarm;
    static TextView resultado;
    private BluetoothSocket connectedSocket;
    static  TextView STATE1, STATE2, STATE3 ,STATE4 ,STATE5, PIRTXT, txt;
    static String SWState1=" ";
    static String SWState2=" ";
    static String SWState3=" ";
    static String SWState4=" ";
    static String SWState5=" ";
    static String PIRState=" ";









    byte Sensores = 0;
    double SW1,SW2,SW3,SW4,SW5,PIR,ALARM;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_watcher);

        final BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();

        sw1 = (Switch) findViewById(R.id.SW1);
        sw2 = (Switch) findViewById(R.id.SW2);
        sw3 = (Switch) findViewById(R.id.SW3);
        sw4 = (Switch) findViewById(R.id.SW4);
        sw5 = (Switch) findViewById(R.id.SW5);
        pir = (Switch) findViewById(R.id.PIR);
        alarm = (Switch) findViewById(R.id.Alarma);
        resultado = (TextView) findViewById(R.id.resultado);

        STATE1 = (TextView) findViewById(R.id.txtSW1state);
        STATE2 = (TextView) findViewById(R.id.txtSW2state);
        STATE3 = (TextView) findViewById(R.id.txtSW3state);
        STATE4 = (TextView) findViewById(R.id.txtSW4state);
        STATE5 = (TextView) findViewById(R.id.txtSW5state);
        PIRTXT = (TextView) findViewById(R.id.txtPIRstate);

        txt=(TextView) findViewById(R.id.textView);



        MainActivity.ISActivityWWStarted = true;


        STATE1.setText(SWState1);
        STATE2.setText(SWState2);
        STATE3.setText(SWState3);
        STATE4.setText(SWState4);
        STATE5.setText(SWState5);
        PIRTXT.setText(PIRState);

        //getDevicetoConnect(deviceName);
        //connectdevice();








        sw1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Log.i("Log1",String.valueOf(SW1));
                Log.i("Log1",String.valueOf(SW2));
                Log.i("Log1",String.valueOf(SW3));




                resultado.setText(StringByte(GetSensores()));
                MainActivity.alarmConfig=Integer.valueOf(GetSensores());
                Toast.makeText(getApplicationContext(),MainActivity.getJSONString(),Toast.LENGTH_SHORT).show();
                SendMessage(MainActivity.getJSONString());




            }
        });

        sw2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Log.i("Log1",String.valueOf(SW1));
                Log.i("Log1",String.valueOf(SW2));
                Log.i("Log1",String.valueOf(SW3));



                resultado.setText(StringByte(GetSensores()));
                MainActivity.alarmConfig=Integer.valueOf(GetSensores());
                Toast.makeText(getApplicationContext(),MainActivity.getJSONString(),Toast.LENGTH_SHORT).show();
                SendMessage(MainActivity.getJSONString());



            }
        });

        sw3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Log.i("Log1",String.valueOf(SW1));
                Log.i("Log1",String.valueOf(SW2));
                Log.i("Log1",String.valueOf(SW3));



                resultado.setText(StringByte(GetSensores()));
                MainActivity.alarmConfig=Integer.valueOf(GetSensores());
                Toast.makeText(getApplicationContext(),MainActivity.getJSONString(),Toast.LENGTH_SHORT).show();
                SendMessage(MainActivity.getJSONString());


            }
        });

        sw4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Log.i("Log1",String.valueOf(SW1));
                Log.i("Log1",String.valueOf(SW2));
                Log.i("Log1",String.valueOf(SW3));



                resultado.setText(StringByte(GetSensores()));
                MainActivity.alarmConfig=Integer.valueOf(GetSensores());
                Toast.makeText(getApplicationContext(),MainActivity.getJSONString(),Toast.LENGTH_SHORT).show();
                SendMessage(MainActivity.getJSONString());


            }
        });

        sw5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Log.i("Log1",String.valueOf(SW1));
                Log.i("Log1",String.valueOf(SW2));
                Log.i("Log1",String.valueOf(SW3));



                resultado.setText(StringByte(GetSensores()));
                MainActivity.alarmConfig=Integer.valueOf(GetSensores());
                Toast.makeText(getApplicationContext(),MainActivity.getJSONString(),Toast.LENGTH_SHORT).show();
                SendMessage(MainActivity.getJSONString());


            }
        });

        pir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Log.i("Log1",String.valueOf(SW1));
                Log.i("Log1",String.valueOf(SW2));
                Log.i("Log1",String.valueOf(SW3));



                resultado.setText(StringByte(GetSensores()));
                MainActivity.alarmConfig=Integer.valueOf(GetSensores());
                Toast.makeText(getApplicationContext(),MainActivity.getJSONString(),Toast.LENGTH_SHORT).show();
                SendMessage(MainActivity.getJSONString());



            }
        });

        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Log.i("Log1",String.valueOf(SW1));
                Log.i("Log1",String.valueOf(SW2));
                Log.i("Log1",String.valueOf(SW3));



                resultado.setText(StringByte(GetSensores()));
                MainActivity.alarmConfig=GetSensores();
                Toast.makeText(getApplicationContext(),MainActivity.getJSONString(),Toast.LENGTH_SHORT).show();
                SendMessage(MainActivity.getJSONString());



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






    public void DisconectSocket()
    {
        if (connectedSocket != null) {
            try {
                connectedSocket.close();
            } catch (IOException e) {
                //appendStateText("[Error] Ocurrió un problema al intentar cerrar la conexión!");
                e.printStackTrace();
            } finally {
                connectedSocket = null;
                //appendStateText("[Estado] Desconectado.");
            }

        } else {
            //appendStateText("[Info] La conexión no parece estar activa.");
        }
    }


    public void SendMessage(String toSend)
    {
        try {
            if ((MainActivity.connectedSocket != null) && (MainActivity.connectedSocket.isConnected())) {

                if (toSend.length() > 0) {
                    // TBI - This object "should" be a mem
                    // ber variable
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(MainActivity.connectedSocket.getOutputStream()));
                    bw.write(toSend);
                    bw.write("\r\n");
                    bw.flush();

                }

            } else {
                //appendStateText("[Error] La conexión no parece estar activa!");
                Toast.makeText(getApplicationContext(),"La conexión no parece estar activa", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),"Ocurrió un problema durante el envío de datos", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    public String StringByte(Byte valor)
    {
        String value;

        if(valor<10)
        {
            value="00"+String.valueOf(valor);
            return value;
        }

        else if (valor<100 && valor >=10)
        {
            value="0"+String.valueOf(valor);
            return value;
        }

        else if (valor>=100)
        {
            value=String.valueOf(valor);
            return value;
        }

        else return " ";
    }



    public static void SwitchesState(byte i)
    {
        if((i&0x01)==0 )
        {
            SWState1="CERRADO";
            STATE1.setText(SWState1);
        }

        else {
            SWState1="ABIERTO";
            STATE1.setText(SWState1);}

        if((i&0x02)==0 )
        {
            SWState2="CERRADO";
            STATE2.setText(SWState2);
        }

        else { SWState2="ABIERTO";
            STATE2.setText(SWState2);}

        if((i&0x04)==0 )
        {
            SWState3="CERRADO";
            STATE3.setText(SWState3);
        }

        else { SWState3="ABIERTO";
            STATE3.setText(SWState3);}

        if((i&0x08)==0 )
        {
            SWState4="CERRADO";
            STATE4.setText(SWState4);
        }

        else { SWState4="ABIERTO";
            STATE4.setText(SWState4);}

        if((i&0x10)==0 )
        {
            SWState5="CERRADO";
            STATE5.setText(SWState5);
        }

        else { SWState5="ABIERTO";
            STATE5.setText(SWState5);}

        if((i&0x20)==0 )
        {
            PIRState="Desactivado";
            PIRTXT.setText(PIRState);
        }

        else { PIRState="Activado";
            PIRTXT.setText(PIRState);}

    }


}
