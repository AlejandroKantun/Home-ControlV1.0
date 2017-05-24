package com.fiuady.home_controlv10;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fiuady.home_controlv10.db.Account;
import com.fiuady.home_controlv10.db.Cuentas;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    public static final String Account_ID = "com.fiuady.home_controlv10.Account_ID";
    public static final String Account_UserName= "com.fiuady.home_controlv10.Account_User";
    public static final String Account_aux= "com.fiuady.home_controlv10.aux";
    private TextView UserName;
    private Account account;
    static private String ID;

    static String getSelectedID ()
    {
        return ID;
    }

    static String getBYTEFormatted(int number)
    {
        if(number>=100)
        {
            return String.valueOf(number);
        }
        else if(number>=10)
        {
            return "0"+String.valueOf(number);
        }
        else
            {
                return "00" +String.valueOf(number);
            }
    }

    static String getJSONString()
    {


        return "{\"data\":[" +
                getBYTEFormatted(PWMR1)+"," +
                getBYTEFormatted(PWMG1)+"," +
                getBYTEFormatted(PWMB1)+"," +
                getBYTEFormatted(PWMR2)+"," +
                getBYTEFormatted(PWMG2)+"," +
                getBYTEFormatted(PWMB2)+"," +
                getBYTEFormatted(stateRGB)+"," +
                getBYTEFormatted(receivedSwitches)+"," +
                getBYTEFormatted(alarmConfig)+"," +
                doorSelection+"]}";
    }

    static String doorSelection="";
    static int receivedSwitches=5;
    static int alarmConfig=8;
    static int PWMR1=255;
    static int PWMG1=255;
    static int PWMB1=255;
    static int PWMR2=255;
    static int PWMG2=255;
    static int PWMB2=255;
    static int stateRGB=0;
    static int color_background1=0;
    static int color_background2=0;

    private class BtBackgroundTask extends AsyncTask<BufferedReader, String, Void> {
        @Override
        protected Void doInBackground(BufferedReader... params) {
            try {
                while (!isCancelled()) {
                    publishProgress(params[0].readLine());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            //appendMessageText("[Recibido] " + values[0]);
            String aux = values[0];
            aux.toUpperCase();
        }
    }

    //Adapter for places----------------------------------------------------------------
    private class PlacesHolder extends RecyclerView.ViewHolder {
        private ImageView image_room;
        private TextView txt_Room;

        public PlacesHolder(final View itemView) {
            super(itemView);
            image_room = (ImageView) itemView.findViewById(R.id.imageView);
            txt_Room = (TextView) itemView.findViewById(R.id.Txt_Room);


            //When u touch a item
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int pos = getAdapterPosition();
                    if (device_name.length()>0)
                    {
                        getDevicetoConnect(device_name);
                        connectdevice();
                    }

                    if (pos == 0)
                    {
                        Intent i = new Intent(MainActivity.this, DoorsActivity.class);
                        startActivity(i);
                    }

                    if (pos == 1)
                    {

                    }
                    if (pos == 2)
                    {

                    }
                    if (pos == 3)
                    {
                        Intent i = new Intent(MainActivity.this, room1.class);
                        startActivity(i);
                    }
                    if (pos == 4)
                    {
                        Intent i = new Intent(MainActivity.this, room2.class);
                        startActivity(i);
                    }
                    if (pos == 5)
                    {

                    }
                    if (pos == 6)
                    {
                        Intent i = new Intent(MainActivity.this, Terrace.class);
                        startActivity(i);
                    }
                }
            });
        }

        public void bindplaces(int a) {
            if (a == 1) {
                image_room.setImageResource(R.drawable.living);
                txt_Room.setText("Living Room");
            } else if (a== 2) {
                image_room.setImageResource(R.drawable.kitchen);
                txt_Room.setText("Kitchen");
            } else if (a == 3) {
                image_room.setImageResource(R.drawable.room1);
                txt_Room.setText("Room 1");
            } else if (a == 4) {
                image_room.setImageResource(R.drawable.room2);
                txt_Room.setText("Room 2");
            }
            else if (a ==5)
            {
                image_room.setImageResource(R.drawable.room3);
                txt_Room.setText("Bathroom");
            }
            else if(a== 6){
                image_room.setImageResource(R.drawable.pool);
                txt_Room.setText("Terrace");
            }
            else
            {
                image_room.setImageResource(R.drawable.puertas);
                txt_Room.setText("Doors");
            }
        }
    }


    private class PlacesAdapter extends RecyclerView.Adapter<PlacesHolder> {
        private List<Integer> a;

        public PlacesAdapter(List<Integer> a) {
            this.a = a;
        }

        @Override
        public PlacesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_item, parent, false);
            return new PlacesHolder(v);
        }

        @Override
        public void onBindViewHolder(PlacesHolder holder, int position) {
            holder.bindplaces(a.get(position));
        }

        @Override
        public int getItemCount() {
            return a.size();
        }
    }
    private RecyclerView PlacesRV;
    private PlacesAdapter placesAdapter;

    private final List<Integer> Aux_places = new ArrayList<Integer>();
    private ImageButton Img_btn_BT, Img_btn_Account_settings,Img_btn_Alarm, Img_btn_door;

    private String device_name="";
    public static BluetoothDevice device;
    private static final int REQUEST_ENABLE_BT = 1;
    private static final UUID SERIAL_PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    //private List<BluetoothDevice> devices;
    public static BluetoothSocket connectedSocket;

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(RESULT_OK==resultCode) {
            device_name = data.getStringExtra("device_name");

        }
    }
private BluetoothAdapter btAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserName = (TextView) findViewById(R.id.Txt_NameUser);
        Intent intent = getIntent();
        /*final String */ID = intent.getStringExtra(Account_ID);
        String User = intent.getStringExtra(Account_UserName);
        String aux = intent.getStringExtra(Account_aux);
        UserName.setText(User);



        btAdapter = BluetoothAdapter.getDefaultAdapter();
        Img_btn_BT = (ImageButton)findViewById(R.id.ImgBtn_BTSetting);
        Img_btn_Account_settings = (ImageButton)findViewById(R.id.ImgBtn_AccountSettings);
        Img_btn_Alarm = (ImageButton)findViewById(R.id.ImgBtn_Alarm);
        Img_btn_Alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, WindowWatcherActivity.class);
                startActivity(i);

            }
        });
        PlacesRV=(RecyclerView)findViewById(R.id.RV_places);
        int display_mode = getResources().getConfiguration().orientation;
        if (display_mode == Configuration.ORIENTATION_PORTRAIT) {
            PlacesRV.setLayoutManager(new LinearLayoutManager(this));
        } else {
            GridLayoutManager manager = new GridLayoutManager(MainActivity.this, 3);
            PlacesRV.setLayoutManager(manager);
        }
        Aux_places.add(0);
        Aux_places.add(1);
        Aux_places.add(2);
        Aux_places.add(3);
        Aux_places.add(4);
        Aux_places.add(5);
        Aux_places.add(6);
        placesAdapter = new PlacesAdapter(Aux_places);
        PlacesRV.setAdapter(placesAdapter);

        Img_btn_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisconectSocket();
                Intent i = new Intent(MainActivity.this, bt_set.class);
                startActivityForResult(i,2);
            }
        });
        Img_btn_Account_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final PopupMenu popupMenu = new PopupMenu(MainActivity.this, Img_btn_Account_settings);
                popupMenu.getMenuInflater().inflate(R.menu.pop_menu_account, popupMenu.getMenu());
                popupMenu.getMenu().clear();
                popupMenu.getMenu().add("Agregar cuenta");
                popupMenu.getMenu().add("Modificar cuenta");
                popupMenu.getMenu().add("Eliminar cuenta");
                popupMenu.getMenu().add("Log out");
                account = new Account(getApplicationContext());
                final Cuentas cuentas = account.getAccountbyid(ID);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals("Agregar cuenta") )
                        {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                           // builder1.setTitle("Modificar producto existente");
                            View product_view = getLayoutInflater().inflate(R.layout.add_account, null);

                            final EditText Username = (EditText) product_view.findViewById(R.id.EditText_UserName);
                            final EditText Email = (EditText) product_view.findViewById(R.id.EditEmail);
                            final EditText Password = (EditText) product_view.findViewById(R.id.EditPassword);
                            final EditText Pin= (EditText) product_view.findViewById(R.id.EditPin);



                            builder1.setPositiveButton("Crear", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int whichButton) {

                                    if(!Username.getText().toString().isEmpty() && !Email.getText().toString().isEmpty() && !Password.getText().toString().isEmpty() && !Pin.getText().toString().isEmpty())
                                    {
                                           account.CreateNonAdminAccount(Username.getText().toString(), Email.getText().toString(), Password.getText().toString(), Pin.getText().toString());
                                        Toast.makeText(MainActivity.this,"Cuenta creada"
                                              , Toast.LENGTH_SHORT).show();

                                    }

                                    else
                                    {


                                        Toast.makeText(MainActivity.this,"Operación no exitosa. "+
                                                "Debe llenar todos los campos ", Toast.LENGTH_SHORT).show();
                                    }


                                }
                            });
                            builder1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                            // builder.show();
                            builder1.setView(product_view);

                            //  builder.setView(product_layout);
                            builder1.show();

                        }
                        else if(item.getTitle().equals("Modificar cuenta"))
                        {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                            // builder1.setTitle("Modificar producto existente");
                            View product_view = getLayoutInflater().inflate(R.layout.modify_account, null);

                            final EditText Username = (EditText) product_view.findViewById(R.id.EditText_UserName);
                          //  final EditText Email = (EditText) product_view.findViewById(R.id.EditEmail);
                            final EditText Password = (EditText) product_view.findViewById(R.id.EditPassword);
                            final EditText Pin= (EditText) product_view.findViewById(R.id.EditPin);
                             final String Id = String.valueOf(cuentas.getId());


                            builder1.setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int whichButton) {

                                    if(!Username.getText().toString().isEmpty()  && !Password.getText().toString().isEmpty() && !Pin.getText().toString().isEmpty())
                                    {
                                        account.modify_account( Id ,Username.getText().toString(), Password.getText().toString(), Pin.getText().toString());
                                        Toast.makeText(MainActivity.this,"Cuenta modificada"
                                                , Toast.LENGTH_SHORT).show();
                                        UserName.setText(Username.getText().toString());

                                    }

                                    else
                                    {


                                        Toast.makeText(MainActivity.this,"Operación no exitosa. "+
                                                "Debe llenar todos los campos ", Toast.LENGTH_SHORT).show();
                                    }


                                }
                            });
                            builder1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                            // builder.show();
                            builder1.setView(product_view);

                            //  builder.setView(product_layout);
                            builder1.show();

                        }
                        else if(item.getTitle().equals("Eliminar cuenta"))
                        {



                        }
                        else
                        {
                            finish();
                           // Intent i = new Intent(MainActivity.this, LoginActivity.class);
                           // startActivityForResult(i);
                        }
                        return false;
                    }
                });

                popupMenu.show();
            }
        });

    }


    public boolean getDevicetoConnect(String name)
    {
        boolean aux= false;

        // Get paired devices
        Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();
        // Check if there are paired devices
        if (pairedDevices.size() > 0) {
            if (pairedDevices.size() == 1) {
                //appendStateText("[Info] Se encontró 1 dispositivo.");
                for (BluetoothDevice device_a: pairedDevices)
                {
                    if (name.length()>0){
                        if (name.equals(device_a.getName().toString()))
                        {
                            device = device_a;
                            aux= true;
                        }
                    }

                }
            } else {
                //appendStateText("[Info] Se encontraron " + pairedDevices.size() + " dispositivos.");
                for (BluetoothDevice device_a: pairedDevices)
                {
                    if (name.equals(device_a.getName().toString()))
                    {
                        device = device_a;
                        aux= true;
                    }

                }
            }

        } else {
            //appendStateText("[Info] No se encontraron dispositivos sincronizados.");
        }
        return aux;
    }
    public boolean connectdevice ()
    {
        boolean aux= false;
        // Use a temporary socket until correct connection is done
        BluetoothSocket tmpSocket = null;

        // Connect with BluetoothDevice
        if (connectedSocket == null) {
            try {
                tmpSocket = device.createRfcommSocketToServiceRecord(MainActivity.SERIAL_PORT_UUID);

                // Get device's own Bluetooth adapter
                BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();

                // Cancel discovery because it otherwise slows down the connection.
                btAdapter.cancelDiscovery();

                // Connect to the remote device through the socket. This call blocks until it succeeds or throws an exception
                tmpSocket.connect();

                // Acknowledge connected socket
                connectedSocket = tmpSocket;

                // Create socket reader thread
                BufferedReader br = new BufferedReader(new InputStreamReader(connectedSocket.getInputStream()));
                new MainActivity.BtBackgroundTask().execute(br);

                Toast.makeText(getApplicationContext(),"[Estado] Conectado.",Toast.LENGTH_SHORT).show();

                //finish();
            } catch (IOException e) {
                try {
                    if (tmpSocket != null) {
                        tmpSocket.close();
                    }
                } catch (IOException closeExceptione) {
                }

                Toast.makeText(getApplicationContext(),"[Error] No se pudo establecer conexión!",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

        return  aux;
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
}
