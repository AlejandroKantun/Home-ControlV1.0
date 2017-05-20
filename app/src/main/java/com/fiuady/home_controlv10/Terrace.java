package com.fiuady.home_controlv10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorChangedListener;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Terrace extends AppCompatActivity {
    public static String RGB_PWM1="";//Red
    public static String RGB_PWM2="";//Blue
    public static String RGB_PWM3="";//Green

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terrace);
        final ColorPickerView colorPickerView = (ColorPickerView) findViewById(R.id.color_picker_view);

        colorPickerView.addOnColorChangedListener(new OnColorChangedListener() {
            @Override public void onColorChanged(int selectedColor) {
                // Handle on color change
                RGB_PWM1="";
                RGB_PWM2="";
                RGB_PWM3="";
                String aux =Integer.toHexString(colorPickerView.getSelectedColor()).toUpperCase();

                for (int x=0;x<aux.length();x++)
                {

                    if (x==2) {RGB_PWM1=RGB_PWM1+aux.charAt(x);}
                    if (x==3) {RGB_PWM1=RGB_PWM1+aux.charAt(x);}
                    if (x==4) {RGB_PWM2=RGB_PWM2+aux.charAt(x);}
                    if (x==5) {RGB_PWM2=RGB_PWM2+aux.charAt(x);}
                    if (x==6) {RGB_PWM3=RGB_PWM3+aux.charAt(x);}
                    if (x==7) {RGB_PWM3=RGB_PWM3+aux.charAt(x);}

                }
                RGB_PWM1 = IntToString(Integer.parseInt(RGB_PWM1,16)); //IntToString funcion especial para formato JSON
                RGB_PWM2 = IntToString(Integer.parseInt(RGB_PWM2,16));
                RGB_PWM3 = IntToString(Integer.parseInt(RGB_PWM3,16));
                //Toast.makeText(getApplicationContext(),RGB_PWM1+RGB_PWM2+RGB_PWM3,Toast.LENGTH_SHORT).show();
                SendMessage(RGB_PWM1+RGB_PWM2+RGB_PWM3);
            }
        });
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

    public String IntToString (int a)
    {
        String aux = "";
        if (a == 0)
        {
            aux = "000";
        }
        else if (a<10)
        {
            aux ="00"+String.valueOf(a);
        }
        else if(a<100)
        {
            aux ="0"+String.valueOf(a);

        }
        else
        {
            aux =String.valueOf(a);

        }
        return aux;
    }
}
