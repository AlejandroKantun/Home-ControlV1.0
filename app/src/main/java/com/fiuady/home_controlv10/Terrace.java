package com.fiuady.home_controlv10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorChangedListener;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Terrace extends AppCompatActivity {

    private final String KEY_BOOLEAN_SWLAMP1STATUS = "key_swLamp1Status";
    private final String KEY_BOOLEAN_CHBLAMP1AUTOSTATUS = "key_chbLamp1AutoStatus";

    private  Switch switchOnOff;
    private CheckBox chbAuto;
    private EditText edtPWMvalue;
    private TextView txvPWMvalueLabel;
    private SeekBar sebPWMvalue;

    public Boolean swLamp1Status = false;
    public Boolean chbLamp1AutoStatus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terrace);

        switchOnOff = (Switch) findViewById(R.id.swt_lamp1); //Switch para darle On/Off a la lámpara
        chbAuto = (CheckBox) findViewById(R.id.chb_lamp1); //CheckBox para indicar si se quiere el Automático;
        edtPWMvalue = (EditText) findViewById(R.id.edt_lamp1_value); //Valor númerico del PWM correspondiente al seekBar
        txvPWMvalueLabel = (TextView) findViewById(R.id.txv_lamp1_label);
        sebPWMvalue = (SeekBar) findViewById(R.id.seb_lamp1); //SeekBar para poder dar valor de la Intensidad
        edtPWMvalue.setEnabled(false);
        edtPWMvalue.setText(String.valueOf(0));

        if( savedInstanceState != null){
            swLamp1Status = savedInstanceState.getBoolean(KEY_BOOLEAN_SWLAMP1STATUS);
            chbLamp1AutoStatus = savedInstanceState.getBoolean(KEY_BOOLEAN_CHBLAMP1AUTOSTATUS);
        }
        else {
            if (swLamp1Status == false) {
                chbAuto.setVisibility(View.INVISIBLE);
                edtPWMvalue.setVisibility(View.INVISIBLE);
                sebPWMvalue.setVisibility(View.INVISIBLE);
                txvPWMvalueLabel.setVisibility(View.INVISIBLE);
            } else {
                chbAuto.setVisibility(View.VISIBLE);
                edtPWMvalue.setVisibility(View.VISIBLE);
                txvPWMvalueLabel.setVisibility(View.VISIBLE);
                sebPWMvalue.setVisibility(View.VISIBLE);
                //edtPWMvalue.setEnabled(false);
                //edtPWMvalue.setTextIsSelectable(false);

            }
        }


        switchOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                swLamp1Status = switchOnOff.isChecked();
                Toast.makeText(getApplicationContext(),"Estado de la lámpara 1: " + swLamp1Status.toString() ,Toast.LENGTH_SHORT).show();

                if (swLamp1Status == false)
                {
                    chbAuto.setVisibility(View.INVISIBLE);
                    edtPWMvalue.setVisibility(View.INVISIBLE);
                    sebPWMvalue.setVisibility(View.INVISIBLE);
                    txvPWMvalueLabel.setVisibility(View.INVISIBLE);
                    edtPWMvalue.setEnabled(false);
                }
                else {
                    chbAuto.setVisibility(View.VISIBLE);
                    edtPWMvalue.setVisibility(View.VISIBLE);
                    txvPWMvalueLabel.setVisibility(View.VISIBLE);
                    sebPWMvalue.setVisibility(View.VISIBLE);

                }
            }
        });


        /*

        * */


chbAuto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        chbLamp1AutoStatus = chbAuto.isChecked();

        if(chbLamp1AutoStatus == true){
            sebPWMvalue.setEnabled(false);
        }
        else {
            sebPWMvalue.setEnabled(true);
        }
    }
});

        sebPWMvalue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                edtPWMvalue.setText(String.valueOf(progress));
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
                Toast.makeText(getApplicationContext(),"La conexión no parece estar activa", Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),"Ocurrió un problema durante el envío de datos", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(KEY_BOOLEAN_SWLAMP1STATUS, swLamp1Status);
        outState.putBoolean(KEY_BOOLEAN_CHBLAMP1AUTOSTATUS, chbLamp1AutoStatus);
    }
}
