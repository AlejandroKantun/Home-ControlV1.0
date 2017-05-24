package com.fiuady.home_controlv10;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle; //bt_set
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class bt_set extends ActionBarActivity { //AppCompatActivity

    private static final int REQUEST_ENABLE_BT = 1;
    private static final UUID SERIAL_PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    private BtDevRvAdapter adapter;
    private List<BluetoothDevice> devices;
    private BluetoothSocket connectedSocket;

    private EditText txtState;
    private EditText txtMessages;
    private EditText txtToSend;

    private ImageButton ImgBtn_PairedBt;
    private ImageButton ImgBtn_findBt;
    private ImageButton ImgBtn_BtOff;

    private Context context = getApplication();

    // ************************************************
    // BtBackgroundTask
    // ************************************************

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
        }
    }


    // ************************************************
    // ViewHolder for RecyclerView
    // ************************************************

    private class BtDevRvHolder extends RecyclerView.ViewHolder {
        private final TextView lblName;
        private final TextView lblAddress;
        private final ImageView img_device;
        private BluetoothDevice device;

        BtDevRvHolder(View itemView) {
            super(itemView);

            lblName = (TextView) itemView.findViewById(R.id.device_name);
            lblAddress = (TextView) itemView.findViewById(R.id.device_address);
            img_device = (ImageView) itemView.findViewById(R.id.Img_Device);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(bt_set.this, lblName);
                    popup.getMenuInflater().inflate(R.menu.device_popup, popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.connect_menu_item:
                                    // Use a temporary socket until correct connection is done
                                    BluetoothSocket tmpSocket = null;

                                    // Connect with BluetoothDevice
                                    if (connectedSocket == null) {
                                        try {
                                            tmpSocket = device.createRfcommSocketToServiceRecord(bt_set.SERIAL_PORT_UUID);

                                            // Get device's own Bluetooth adapter
                                            BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();

                                            // Cancel discovery because it otherwise slows down the connection.
                                            btAdapter.cancelDiscovery();

                                            // Connect to the remote device through the socket. This call blocks until it succeeds or throws an exception
                                            tmpSocket.connect();

                                            // Acknowledge connected socket
                                            connectedSocket = tmpSocket;
                                            Intent intent = new Intent();
                                            intent.putExtra("device_name", device.getName().toString()); //value should be your string from the edittext
                                            setResult(RESULT_OK,intent);
                                            // Create socket reader thread
                                            BufferedReader br = new BufferedReader(new InputStreamReader(connectedSocket.getInputStream()));
                                            new BtBackgroundTask().execute(br);

                                            appendStateText("[Estado] Conectado.");
                                            //finish();
                                        } catch (IOException e) {
                                            try {
                                                if (tmpSocket != null) {
                                                    tmpSocket.close();
                                                }
                                            } catch (IOException closeExceptione) {
                                            }

                                            appendStateText("[Error] No se pudo establecer conexión!");
                                            e.printStackTrace();
                                        }
                                    }
                                    break;

                                default:
                                    break;
                            }

                            return true;
                        }
                    });

                    popup.show();
                }
            });
        }

        void bind(BluetoothDevice device) {
            this.device = device;
            lblName.setText(device.getName());
            lblAddress.setText(device.getAddress());
            img_device.setImageResource(R.drawable.devicepaired);

        }
    }


    // ************************************************
    // Adapter for RecyclerView
    // ************************************************

    private class BtDevRvAdapter extends RecyclerView.Adapter<BtDevRvHolder> {
        private List<BluetoothDevice> devices;

        BtDevRvAdapter(List<BluetoothDevice> devices) {
            this.devices = devices;
        }

        @Override
        public BtDevRvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(bt_set.this);
            return new BtDevRvHolder(inflater.inflate(R.layout.device_list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(BtDevRvHolder holder, int position) {
            holder.bind(devices.get(position));
        }

        @Override
        public int getItemCount() {
            return devices.size();
        }

        void update() {
            notifyDataSetChanged();
        }
    }


    // ************************************************
    // MainActivity implementation
    // ************************************************

    // Create a BroadcastReceiver for ACTION_STATE_CHANGED
    private final BroadcastReceiver btStateReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(intent.getAction())) {
                // Bluetooth adapter state has changed
                switch (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0)) {
                    case BluetoothAdapter.STATE_OFF:
                        appendStateText("[Estado] Apagado.");
                        break;

                    case BluetoothAdapter.STATE_ON:
                        appendStateText("[Estado] Encendido.");
                        break;

                    case BluetoothAdapter.STATE_TURNING_OFF:
                        appendStateText("[Acción] Apagando...");
                        break;

                    case BluetoothAdapter.STATE_TURNING_ON:
                        appendStateText("[Acción] Encendiendo...");
                        break;
                }
            }
        }
    };

    // Create a BroadcastReceiver for ACTION_DISCOVERY_STARTED
    private final BroadcastReceiver btDiscoveryStartedReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(intent.getAction())) {
                appendStateText("[Acción] Iniciando búsqueda de dispositivos...");
            }
        }
    };

    // Create a BroadcastReceiver for ACTION_DISCOVERY_FINISHED
    private final BroadcastReceiver btDiscoveryFinishedReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(intent.getAction())) {
                appendStateText("[Acción] Finalizando búsqueda de dispositivos...");
            }
        }
    };

    // Create a BroadcastReceiver for ACTION_FOUND
    private final BroadcastReceiver btFoundReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (BluetoothDevice.ACTION_FOUND.equals(intent.getAction())) {
                // Discovery has found a device. Get the BluetoothDevice object and its info from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                devices.add(device);
                adapter.update();

                appendStateText("[Info] Dispositivo encontrado: " + device.getName() + ".");
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bt_set);
        final BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();

        ImgBtn_BtOff = (ImageButton)findViewById(R.id.ImgBtn_BToff);
        ImgBtn_BtOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connectedSocket != null) {
                    try {
                        connectedSocket.close();
                    } catch (IOException e) {
                        appendStateText("[Error] Ocurrió un problema al intentar cerrar la conexión!");
                        e.printStackTrace();
                    } finally {
                        connectedSocket = null;
                        appendStateText("[Estado] Desconectado.");
                    }

                } else {
                    appendStateText("[Info] La conexión no parece estar activa.");
                }
            }
        });
        ImgBtn_findBt = (ImageButton)findViewById(R.id.ImgBtn_BTfind);
        ImgBtn_findBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btAdapter == null) {
                    appendStateText("[Error] Dispositivo Bluetooth no encontrado!");
                }
                // Check if device adapter is not enabled
                else if (!btAdapter.isEnabled()) {
                    // Issue a request to enable Bluetooth through the system settings (without stopping application)
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent, REQUEST_ENABLE_BT);
                }
                // Start discovery process
                else {
                    devices.clear();
                    adapter.update();
                    btAdapter.startDiscovery();
                }
            }
        });
        ImgBtn_PairedBt = (ImageButton)findViewById(R.id.ImgBtn_BTDevicespaired);
        ImgBtn_PairedBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                devices.clear();
                adapter.update();

                // Get paired devices
                appendStateText("[Acción] Buscando dispositivos sincronizados...");
                Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();

                // Check if there are paired devices
                if (pairedDevices.size() > 0) {
                    if (pairedDevices.size() == 1) {
                        appendStateText("[Info] Se encontró 1 dispositivo.");
                    } else {
                        appendStateText("[Info] Se encontraron " + pairedDevices.size() + " dispositivos.");
                    }

                    // Loop through paired devices
                    for (BluetoothDevice device : pairedDevices) {
                        devices.add(device);
                        appendStateText("[Info] Dispositivo sincronizado: " + device.getName() + ".");
                    }

                    adapter.update();
                } else {
                    appendStateText("[Info] No se encontraron dispositivos sincronizados.");
                }
            }
        });
        // Setup devices recycler-view
        RecyclerView rvDevices = (RecyclerView) findViewById(R.id.devices_list);
        rvDevices.setLayoutManager(new LinearLayoutManager(context));

        devices = new ArrayList<>();
        adapter = new BtDevRvAdapter(devices);
        rvDevices.setAdapter(adapter);

        // Setup state multiline edit-text
        txtState = (EditText) findViewById(R.id.state_text);
        txtState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(bt_set.this, findViewById(R.id.state_text_label));
                popup.getMenuInflater().inflate(R.menu.clear_popup, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.erase_menu_item:
                                // Clear state text-edit
                                txtState.setText("");
                                break;
                        }

                        return true;
                    }
                });

                popup.show();
            }
        });

        // Setup message-to-send edit-text
      //  txtToSend = (EditText) findViewById(R.id.message_to_send_text);

      // Button btnSend = (Button) findViewById(R.id.send_button);
      // btnSend.setOnClickListener(new View.OnClickListener() {
      //     @Override
      //     public void onClick(View v) {
      //         // ... Transmisión de datos
      //         try {
      //             if ((connectedSocket != null) && (connectedSocket.isConnected())) {
      //                 String toSend = txtToSend.getText().toString().trim();

      //                 if (toSend.length() > 0) {
      //                     // TBI - This object "should" be a mem
      //                     // ber variable
      //                     BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connectedSocket.getOutputStream()));
      //                     bw.write(toSend);
      //                     bw.write("\r\n");
      //                     bw.flush();

      //                     //appendMessageText("[Enviado] " + toSend);
      //                 }

      //                 txtToSend.setText("");
      //             } else {
      //                 appendStateText("[Error] La conexión no parece estar activa!");
      //             }
      //         } catch (IOException e) {
      //             appendStateText("[Error] Ocurrió un problema durante el envío de datos!");
      //             e.printStackTrace();
      //         }
      //     }
      // });


        // Register for broadcasts when bluetooth device state changes
        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(btStateReceiver, filter);

        // Register for broadcasts when bluetooth discovery state changes
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        registerReceiver(btDiscoveryStartedReceiver, filter);

        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(btDiscoveryFinishedReceiver, filter);

        // Register for broadcasts when a device is discovered.
        filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(btFoundReceiver, filter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case RESULT_OK:
                if (requestCode == REQUEST_ENABLE_BT) {
                    // Get device's own Bluetooth adapter
                    BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();

                    // Start discovery process
                    devices.clear();
                    adapter.update();
                    btAdapter.startDiscovery();
                }
                break;

            case RESULT_CANCELED:
            default:
                //appendStateText("[Error] El dispositivo Bluetooth no pudo ser habilitado!");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Unregister the ACTION_STATE_CHANGE receiver
        unregisterReceiver(btStateReceiver);

        // Unregister the ACTION_DISCOVERY_STARTED receiver.
        unregisterReceiver(btDiscoveryStartedReceiver);

        // Unregister the ACTION_DISCOVERY_FINISHED receiver.
        unregisterReceiver(btDiscoveryFinishedReceiver);

        // Unregister the ACTION_FOUND receiver
        unregisterReceiver(btFoundReceiver);

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

    private void appendStateText(String text) {
        txtState.setText(text + "\n" + txtState.getText());
    }

}