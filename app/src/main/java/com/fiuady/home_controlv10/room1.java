package com.fiuady.home_controlv10;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorChangedListener;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class room1 extends AppCompatActivity {
    private ImageButton Img_Btn_ledOn1;

    final static String KEY_BOOLEAN_TEMP1AUTO = "key_boolTemp1Auto";
    final static String KEY_BOOLEAN_TEMP1ONOFF ="key_boolTemp1OnOff";
    final static String KEY_INT_TEMP1MIN = "key_intTemp1Min";
    final static String KEY_INT_TEMP1MAX = "key_intTemp1Max";

    /*Ventilador y sensor de temperatura*/
    private EditText edtTemp1Actual;
    private Switch swTemp1Auto;
    private Switch swTemp1OnOff;
    private NumberPicker nupTemp1RangeMin;
    private NumberPicker nupTemp1RangeMax;
    /*Ventilador y sensor de temperatura*/

    public static String RGB_PWM1="";//Red
    public static String RGB_PWM2="";//Blue
    public static String RGB_PWM3="";//Green

    /*Ventilador y sensor de temperatura*/
    public Boolean boolTemp1Auto = false;
    public Boolean boolTemp1OnOff = false;
    public int intTemp1Min = 0;
    public int intTemp1Max = 0;
    /*Ventilador y sensor de temperatura*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room1);
        Img_Btn_ledOn1 = (ImageButton)findViewById(R.id.Img_btn_LEDON1);
        Img_Btn_ledOn1.setBackgroundColor(MainActivity.color_background1);
/*----------------------------------------------------------------------------------------------------*/
/*----------------------------------------------------------------------------------------------------*/
         /*Ventilador y sensor de temperatura*/
        edtTemp1Actual = (EditText)findViewById(R.id.edt_temp1);
        swTemp1Auto = (Switch)findViewById(R.id.swt_vent1_auto);
        swTemp1OnOff = (Switch)findViewById(R.id.swt_vent1_on_off);
        nupTemp1RangeMin = (NumberPicker)findViewById(R.id.nup_temp1_min);
        nupTemp1RangeMax = (NumberPicker)findViewById(R.id.nup_temp1_max);

        swTemp1Auto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolTemp1Auto = swTemp1Auto.isChecked();

                if(boolTemp1Auto){
                    swTemp1OnOff.setEnabled(false);
                    nupTemp1RangeMin.setEnabled(true);
                    nupTemp1RangeMax.setEnabled(true);
                    Toast.makeText(getApplicationContext(),"VENT1 AUTO ACT",Toast.LENGTH_SHORT).show();
                }

                else{
                    swTemp1OnOff.setEnabled(true);
                    nupTemp1RangeMin.setEnabled(false);
                    nupTemp1RangeMax.setEnabled(false);
                    Toast.makeText(getApplicationContext(),"VENT1 AUTO DES",Toast.LENGTH_SHORT).show();
                }
            }
        });

        swTemp1OnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolTemp1OnOff = swTemp1OnOff.isChecked();

                if(boolTemp1OnOff){
                    Toast.makeText(getApplicationContext(),"VENT1 ON",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"VENT1 OFF",Toast.LENGTH_SHORT).show();
                }
            }
        });

        nupTemp1RangeMax.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if ((nupTemp1RangeMax.getValue() < nupTemp1RangeMin.getValue() | (nupTemp1RangeMax.getValue() == nupTemp1RangeMin.getValue()))){
                    Toast.makeText(getApplicationContext(),"Esta Temp debe ser mayor",Toast.LENGTH_SHORT).show();
                    intTemp1Max = nupTemp1RangeMin.getValue() + 1;
                    nupTemp1RangeMax.setValue(intTemp1Max);
                }
                else {
                    intTemp1Max = nupTemp1RangeMax.getValue();
                    Toast.makeText(getApplicationContext(),"VENT1 RANGE " + intTemp1Min + " " + intTemp1Max,Toast.LENGTH_SHORT).show();
                }
            }
        });

        nupTemp1RangeMin.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if((nupTemp1RangeMin.getValue() > nupTemp1RangeMax.getValue()) | (nupTemp1RangeMin.getValue() == nupTemp1RangeMax.getValue())){
                    Toast.makeText(getApplicationContext(),"Esta Temp debe ser menor",Toast.LENGTH_SHORT).show();
                    intTemp1Min = nupTemp1RangeMax.getValue() - 1;
                    nupTemp1RangeMin.setValue(intTemp1Min);
                }

                else{
                    intTemp1Min = nupTemp1RangeMin.getValue();
                    Toast.makeText(getApplicationContext(),"VENT1 RANGE " + intTemp1Min + " " + intTemp1Max,Toast.LENGTH_SHORT).show();
                }
            }
        });

    /*Ventilador y sensor de temperatura*/
/*----------------------------------------------------------------------------------------------------*/
/*----------------------------------------------------------------------------------------------------*/

        Img_Btn_ledOn1.setBackgroundColor(MainActivity.color_background1);
        Img_Btn_ledOn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(room1.this);
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
                        MainActivity.color_background1 = colorPickerView.getSelectedColor();
                        for (int x=0;x<aux.length();x++)
                        {

                            if (x==2) {RGB_PWM1=RGB_PWM1+aux.charAt(x);}
                            if (x==3) {RGB_PWM1=RGB_PWM1+aux.charAt(x);}
                            if (x==4) {RGB_PWM2=RGB_PWM2+aux.charAt(x);}
                            if (x==5) {RGB_PWM2=RGB_PWM2+aux.charAt(x);}
                            if (x==6) {RGB_PWM3=RGB_PWM3+aux.charAt(x);}
                            if (x==7) {RGB_PWM3=RGB_PWM3+aux.charAt(x);}

                        }
                        //Toast.makeText(getApplicationContext(),"#"+RGB_PWM1+RGB_PWM2+RGB_PWM3,Toast.LENGTH_SHORT).show();

                        Img_Btn_ledOn1.setBackgroundColor(MainActivity.color_background1);
                        MainActivity.PWMR1 = (Integer.parseInt(RGB_PWM1,16));
                        MainActivity.PWMG1 = (Integer.parseInt(RGB_PWM2,16));
                        MainActivity.PWMB1 = (Integer.parseInt(RGB_PWM2,16));
                        MainActivity.stateRGB = 1;
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
