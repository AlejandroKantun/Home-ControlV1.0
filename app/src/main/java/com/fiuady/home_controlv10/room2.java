package com.fiuady.home_controlv10;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorChangedListener;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class room2 extends AppCompatActivity {
    private ImageButton Img_Btn_ledOn2 ;

    public static String RGB_PWM1="";//Red
    public static String RGB_PWM2="";//Blue
    public static String RGB_PWM3="";//Green

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room2);
        Img_Btn_ledOn2= (ImageButton)findViewById(R.id.Img_btn_LEDON2);
        Img_Btn_ledOn2.setBackgroundColor(MainActivity.color_background2);
        Img_Btn_ledOn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(room2.this);
                dialog.setTitle("Estados: ");
                dialog.setContentView(R.layout.color_picker);
                dialog.show();

                final ColorPickerView colorPickerView = (ColorPickerView)dialog.findViewById(R.id.color_picker_view);

                colorPickerView.addOnColorChangedListener(new OnColorChangedListener() {
                    @Override public void onColorChanged(int selectedColor) {
                        // Handle on color change
                        RGB_PWM1="";
                        RGB_PWM2="";
                        RGB_PWM3="";
                        String aux =Integer.toHexString(colorPickerView.getSelectedColor()).toUpperCase();
                        MainActivity.color_background2 = colorPickerView.getSelectedColor();

                        for (int x=0;x<aux.length();x++)
                        {

                            if (x==2) {RGB_PWM1=RGB_PWM1+aux.charAt(x);}
                            if (x==3) {RGB_PWM1=RGB_PWM1+aux.charAt(x);}
                            if (x==4) {RGB_PWM2=RGB_PWM2+aux.charAt(x);}
                            if (x==5) {RGB_PWM2=RGB_PWM2+aux.charAt(x);}
                            if (x==6) {RGB_PWM3=RGB_PWM3+aux.charAt(x);}
                            if (x==7) {RGB_PWM3=RGB_PWM3+aux.charAt(x);}

                        }

                        Img_Btn_ledOn2.setBackgroundColor(MainActivity.color_background2);
                        MainActivity.PWMR2 = (Integer.parseInt(RGB_PWM1,16));
                        MainActivity.PWMG2 = (Integer.parseInt(RGB_PWM2,16));
                        MainActivity.PWMB2 = (Integer.parseInt(RGB_PWM2,16));
                        MainActivity.stateRGB = 2;
                        //Toast.makeText(getApplicationContext(),MainActivity.getJSONString(),Toast.LENGTH_SHORT).show();

                        SendMessage(MainActivity.getJSONString());
                        //SendMessage(RGB_PWM1+RGB_PWM2+RGB_PWM3);
                    }
                });
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
}
