package com.fiuady.home_controlv10;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
import java.util.StringTokenizer;
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
                doorSelection+","+command+","+value1+","+value2+","+value3+"]}";
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
    static String command="1";
    static String value1 = "4";
    static String value2="5";
    static String value3="9";
    static int color_background1=0;
    static int color_background2=0;

    static String result = " ";

    static boolean ISActivityWWStarted = false;


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
            if(ISActivityWWStarted==true)
            {
                //WindowWatcherActivity.SwitchesState(Byte.valueOf((byte) (receivedSwitches)));**********
                // WindowWatcherActivity.txt.setText(String.valueOf(receivedSwitches));****************
                if(values[0].startsWith("ESTADO")) {
                    StringTokenizer st = new StringTokenizer(values[0]," ");
                    st.nextToken();
                    String estado = st.nextToken();

                    result = estado;
                    receivedSwitches = Integer.parseInt(result,16);
                    WindowWatcherActivity.SwitchesState(Byte.valueOf((byte) (receivedSwitches)));

                    WindowWatcherActivity.txt.setText(estado);
                    //WindowWatcherActivity.resultado.setText(String.valueOf((receivedSwitches)));
                }
            }

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
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private String User; //name

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // UserName = (TextView) findViewById(R.id.Txt_NameUser);
        Intent intent = getIntent();
        /*final String */ID = intent.getStringExtra(Account_ID);
         User = intent.getStringExtra(Account_UserName);
        String aux = intent.getStringExtra(Account_aux);
        //UserName.setText(User);
        toolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home Control");


        account = new Account(getApplicationContext());
        final Cuentas cuentas = account.getAccountbyid(ID);


if(cuentas.getJson1()!= null && cuentas.getJson2()!= null && cuentas.getJson3()!=null && cuentas.getJson4()!= null && cuentas.getJson5()!= null && cuentas.getJson6()!=null && cuentas.getJson7()!= null && cuentas.getJson10()!=null)

{
    doorSelection = String.valueOf(cuentas.getJson10());
    PWMR1=Integer.valueOf(cuentas.getJson1());
    PWMG1=Integer.valueOf(cuentas.getJson2());
    PWMB1=Integer.valueOf(cuentas.getJson3());
    PWMR2=Integer.valueOf(cuentas.getJson4());
    PWMG2=Integer.valueOf(cuentas.getJson5());
    PWMB2=Integer.valueOf(cuentas.getJson6());
    stateRGB=Integer.valueOf(cuentas.getJson7());
    //SendMessage(getJSONString());
}



        btAdapter = BluetoothAdapter.getDefaultAdapter();
       /* Img_btn_BT = (ImageButton)findViewById(R.id.ImgBtn_BTSetting);
        Img_btn_Account_settings = (ImageButton)findViewById(R.id.ImgBtn_AccountSettings);
        Img_btn_Alarm = (ImageButton)findViewById(R.id.ImgBtn_Alarm);
        Img_btn_Alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (device_name.length()>0)
                {
                    getDevicetoConnect(device_name);
                    connectdevice();
                }
                Intent i = new Intent(MainActivity.this, WindowWatcherActivity.class);
                startActivity(i);

            }
        });*/
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

        /*Img_btn_BT.setOnClickListener(new View.OnClickListener() {
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


            }
        });*/
        navigationView = (NavigationView)findViewById(R.id.navigation_view);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                switch (menuItem.getItemId()){


                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.doors:
                       /* Toast.makeText(getApplicationContext(),"Inbox Selected",Toast.LENGTH_SHORT).show();
                        ContentFragment fragment = new ContentFragment();
                        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frame,fragment);
                        fragmentTransaction.commit();*/
                        Toast.makeText(getApplicationContext(),"Doors",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, DoorsActivity.class);
                        intent.putExtra(DoorsActivity.Account_ID2, ID);
                        startActivity(intent);
                        return true;

                    // For rest of the options we just show a toast on click

                    case R.id.alarms:
                        Toast.makeText(getApplicationContext(),"Alarms",Toast.LENGTH_SHORT).show();
                        if (device_name.length()>0)
                        {
                            getDevicetoConnect(device_name);
                            connectdevice();
                        }
                        Intent i = new Intent(MainActivity.this, WindowWatcherActivity.class);
                        startActivity(i);
                        return true;
                    case R.id.Modify_activeAccount:
                        Toast.makeText(getApplicationContext(),"Modify",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.Delete_activeAccount:
                        Toast.makeText(getApplicationContext(),"Deleter",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.LogOut_activeAccount:
                        Toast.makeText(getApplicationContext(),"Logout",Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return true;


                }
            }
        });
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                TextView userName = (TextView)findViewById(R.id.username);
                TextView userEmail = (TextView)findViewById(R.id.email);

                userName.setText(User);
                Cuentas cuenta = account.getAccountbyid(ID);
                userEmail.setText(cuenta.getMail());


                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

        if (cuentas.getExtra1()!= null)
        {
            color_background1 = Integer.valueOf(cuentas.getExtra1());
            PWMR1 = Integer.valueOf(cuentas.getJson1());
            PWMG1 = Integer.valueOf(cuentas.getJson2());
            PWMB1 = Integer.valueOf(cuentas.getJson3());
            stateRGB = Integer.valueOf(cuentas.getJson7());
        }
        if (cuentas.getExtra2()!= null)
        {
            color_background2 = Integer.valueOf(cuentas.getExtra2());
            PWMR2 = Integer.valueOf(cuentas.getJson4());
            PWMG2 = Integer.valueOf(cuentas.getJson5());
            PWMB2 = Integer.valueOf(cuentas.getJson6());
            stateRGB = Integer.valueOf(cuentas.getJson7());
        }
        else {
            PWMR1=255;
            PWMG1=255;
            PWMB1=255;
            PWMR2=255;
            PWMG2=255;
            PWMB2=255;
            stateRGB=0;
            color_background1 =0;
            color_background2=0;
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.option_AccountsManager:
                Intent intent = new Intent(MainActivity.this,ManageUsersActivity.class);
                startActivity(intent);
                break;

            case R.id.option_BluetoothManager:
                DisconectSocket();
                Intent i = new Intent(MainActivity.this, bt_set.class);
                startActivityForResult(i,2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //MenuInflater menuInflater = getMenuInflater();
        Cuentas cuentas = account.getAccountbyid(ID);
        if(cuentas.getType() == 1) {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.main_menu,menu);
        }
        else
        {   MenuInflater menuInflater2 = getMenuInflater();
            menuInflater2.inflate(R.menu.main_menu2,menu);

        }
        return super.onCreateOptionsMenu(menu);
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
