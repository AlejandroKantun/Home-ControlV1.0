package com.fiuady.home_controlv10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

public class DoorsActivity extends AppCompatActivity {

    private Switch frontDoorSwitch;
    private Switch garageDoorSwitch;

    private String doorsVariable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doors);
        frontDoorSwitch = (Switch) findViewById(R.id.frontdoor_switch);
        garageDoorSwitch = (Switch) findViewById(R.id.garage_switch);
        //Toast.makeText(getApplicationContext(),MainActivity.device.getName().toString(),Toast.LENGTH_SHORT).show();
        garageDoorSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (frontDoorSwitch.isChecked()&&garageDoorSwitch.isChecked())
                {
                    //asignar a la variable de las puertas: 003
                    doorsVariable = "003";
                }
                else if(frontDoorSwitch.isChecked()&&!garageDoorSwitch.isChecked())
                {
                    //asignar a la variable de las puertas: 002
                    doorsVariable = "002";
                }
                else if(!frontDoorSwitch.isChecked()&&garageDoorSwitch.isChecked())
                {
                    //Asignar a la variable de las puertas: 001
                    doorsVariable = "001";
                }
                else
                    {
                        doorsVariable = "000";
                    }

                Toast.makeText(getApplicationContext(),doorsVariable,Toast.LENGTH_SHORT).show();
            }
        });
        frontDoorSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (frontDoorSwitch.isChecked()&&garageDoorSwitch.isChecked())
                {
                    //asignar a la variable de las puertas: 003
                    doorsVariable = "003";
                }
                else if(frontDoorSwitch.isChecked()&&!garageDoorSwitch.isChecked())
                {
                    //asignar a la variable de las puertas: 002
                    doorsVariable = "002";
                }
                else if(!frontDoorSwitch.isChecked()&&garageDoorSwitch.isChecked())
                {
                    //Asignar a la variable de las puertas: 001
                    doorsVariable = "001";
                }
                else
                {
                    doorsVariable = "000";
                }
                Toast.makeText(getApplicationContext(),doorsVariable,Toast.LENGTH_SHORT).show();

            }
        });
    }
}
