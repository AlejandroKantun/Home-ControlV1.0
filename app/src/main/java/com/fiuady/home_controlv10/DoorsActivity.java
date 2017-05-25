package com.fiuady.home_controlv10;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import com.fiuady.home_controlv10.db.Account;
import com.fiuady.home_controlv10.db.Cuentas;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class DoorsActivity extends AppCompatActivity {

    public void SendMessage(String toSend) {
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
                Toast.makeText(getApplicationContext(), "La conexión no parece estar activa", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Ocurrió un problema durante el envío de datos", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private Switch frontDoorSwitch;
    private Switch garageDoorSwitch;
    private ImageButton loginButton;
    private String doorsVariable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doors);
        frontDoorSwitch = (Switch) findViewById(R.id.frontdoor_switch);
        garageDoorSwitch = (Switch) findViewById(R.id.garage_switch);
        loginButton = (ImageButton) findViewById(R.id.ImgBtn_doorsLogin);
        //garageDoorSwitch.setChecked(MainActivity.garageDoorState);
        //frontDoorSwitch.setChecked(MainActivity.frontDoorState);
        Account cuenta = new Account(getApplicationContext());
        final Cuentas cuentas = cuenta.getAccountbyid(MainActivity.getSelectedID());



        cuenta.Update_JSON_Reyes(String.valueOf(cuentas.getId()),doorsVariable);
        if (MainActivity.doorSelection.equals("003")) {
            garageDoorSwitch.setChecked(true);
            frontDoorSwitch.setChecked(true);
        } else if (MainActivity.doorSelection.equals("002")) {
            garageDoorSwitch.setChecked(false);
            frontDoorSwitch.setChecked(true);
        } else if (MainActivity.doorSelection.equals("001")) {
            garageDoorSwitch.setChecked(true);
            frontDoorSwitch.setChecked(false);
        }

        garageDoorSwitch.setEnabled(false);
        frontDoorSwitch.setEnabled(false);
        Toast.makeText(getApplicationContext(),cuentas.getUser(),Toast.LENGTH_SHORT).show();
        garageDoorSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (frontDoorSwitch.isChecked() && garageDoorSwitch.isChecked()) {
                    //asignar a la variable de las puertas: 003
                    doorsVariable = "003";
                } else if (frontDoorSwitch.isChecked() && !garageDoorSwitch.isChecked()) {
                    //asignar a la variable de las puertas: 002
                    doorsVariable = "002";
                } else if (!frontDoorSwitch.isChecked() && garageDoorSwitch.isChecked()) {
                    //Asignar a la variable de las puertas: 001
                    doorsVariable = "001";
                } else {
                    doorsVariable = "000";
                }

                Toast.makeText(getApplicationContext(), doorsVariable, Toast.LENGTH_SHORT).show();
                MainActivity.doorSelection = doorsVariable;
                Toast.makeText(getApplicationContext(), MainActivity.getJSONString(), Toast.LENGTH_SHORT).show();
                SendMessage(MainActivity.getJSONString());

            }
        });
        frontDoorSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (frontDoorSwitch.isChecked() && garageDoorSwitch.isChecked()) {
                    //asignar a la variable de las puertas: 003
                    doorsVariable = "003";
                } else if (frontDoorSwitch.isChecked() && !garageDoorSwitch.isChecked()) {
                    //asignar a la variable de las puertas: 002
                    doorsVariable = "002";
                } else if (!frontDoorSwitch.isChecked() && garageDoorSwitch.isChecked()) {
                    //Asignar a la variable de las puertas: 001
                    doorsVariable = "001";
                } else {
                    doorsVariable = "000";
                }
                Toast.makeText(getApplicationContext(), doorsVariable, Toast.LENGTH_SHORT).show();
                MainActivity.doorSelection = doorsVariable;
                Toast.makeText(getApplicationContext(), MainActivity.getJSONString(), Toast.LENGTH_SHORT).show();
                SendMessage(MainActivity.getJSONString());

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DoorsActivity.this);
                builder.setTitle("PIN Necesario");
                builder.setMessage("Introduzca el PIN para abrir las puertas: ");
                final EditText input = new EditText(DoorsActivity.this);
                builder.setView(input);
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(input.getText().toString().equals(cuentas.getPin()))
                        {
                            frontDoorSwitch.setEnabled(true);
                            garageDoorSwitch.setEnabled(true);
                            Toast.makeText(getApplicationContext(),"PIN Correcto", Toast.LENGTH_LONG).show();
                        }
                        else
                            {
                                Toast.makeText(getApplicationContext(),"PIN Incorrecto", Toast.LENGTH_LONG).show();
                            }
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
    }
}

