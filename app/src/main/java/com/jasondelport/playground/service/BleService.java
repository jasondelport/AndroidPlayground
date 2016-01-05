package com.jasondelport.playground.service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import timber.log.Timber;

public class BleService extends Service implements BluetoothAdapter.LeScanCallback {
    public static final String KEY_MAC_ADDRESSES = "KEY_MAC_ADDRESSES";
    public static final int MSG_REGISTER = 1;
    public static final int MSG_UNREGISTER = 2;
    public static final int MSG_START_SCAN = 3;
    public static final int MSG_STATE_CHANGED = 4;
    public static final int MSG_DEVICE_FOUND = 5;
    private static final long SCAN_PERIOD = 5000;
    private final IncomingHandler mHandler;
    private final Messenger mMessenger;
    private final List<Messenger> mClients = new LinkedList<>();
    private final Map<String, BluetoothDevice> mDevices = new HashMap<>();
    private BluetoothAdapter mBluetooth = null;
    private State mState = State.UNKNOWN;

    public BleService() {
        mHandler = new IncomingHandler(this);
        mMessenger = new Messenger(mHandler);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    private void startScan() {
        mDevices.clear();
        setState(State.SCANNING);
        if (mBluetooth == null) {
            BluetoothManager bluetoothMgr = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
            mBluetooth = bluetoothMgr.getAdapter();
        }
        if (mBluetooth == null || !mBluetooth.isEnabled()) {
            setState(State.BLUETOOTH_OFF);
        } else {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mState == State.SCANNING) {
                        mBluetooth.stopLeScan(BleService.this);
                        setState(State.IDLE);
                    }
                }
            }, SCAN_PERIOD);

            //BluetoothLeScanner scanner = BluetoothAdapter.getDefaultAdapter().getBluetoothLeScanner();
            //ScanSettings scanSettings = new ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_LOW_POWER).build();
            //List<ScanFilter> scanFilters = new ArrayList<>();
            //scanner.startScan(scanFilters, scanSettings, scanCallback);
            mBluetooth.startLeScan(this);
        }
    }

    @Override
    public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
        if (device != null && !mDevices.containsValue(device) && device.getName() != null) {
            mDevices.put(device.getAddress(), device);
            Message msg = Message.obtain(null, MSG_DEVICE_FOUND);
            if (msg != null) {
                Bundle bundle = new Bundle();
                String[] addresses = mDevices.keySet().toArray(new String[mDevices.size()]);
                bundle.putStringArray(KEY_MAC_ADDRESSES, addresses);
                msg.setData(bundle);
                sendMessage(msg);
            }
            Timber.d("Added " + device.getName() + ": " + device.getAddress());
        }
    }

    private void setState(State newState) {
        if (mState != newState) {
            mState = newState;
            Message msg = getStateMessage();
            if (msg != null) {
                sendMessage(msg);
            }
        }
    }

    private Message getStateMessage() {
        Message msg = Message.obtain(null, MSG_STATE_CHANGED);
        if (msg != null) {
            msg.arg1 = mState.ordinal();
        }
        return msg;
    }

    private void sendMessage(Message msg) {
        Timber.d("Client size -> %d", mClients.size());
        for (int i = mClients.size() - 1; i >= 0; i--) {
            Messenger messenger = mClients.get(i);
            if (!sendMessage(messenger, msg)) {
                mClients.remove(messenger);
            }
        }
    }

    private boolean sendMessage(Messenger messenger, Message msg) {
        boolean success = true;
        try {
            messenger.send(msg);
            Timber.d("Sending message");
        } catch (RemoteException e) {
            Timber.d("Lost connection to client", e);
            success = false;
        }
        return success;
    }

    public enum State {
        UNKNOWN,
        IDLE,
        SCANNING,
        BLUETOOTH_OFF,
        CONNECTING,
        CONNECTED,
        DISCONNECTING
    }

    private static class IncomingHandler extends Handler {
        private final WeakReference<BleService> mService;

        public IncomingHandler(BleService service) {
            mService = new WeakReference<>(service);
        }

        @Override
        public void handleMessage(Message msg) {
            BleService service = mService.get();
            if (service != null) {
                switch (msg.what) {
                    case MSG_REGISTER:
                        service.mClients.add(msg.replyTo);
                        Timber.d("Registered");
                        break;
                    case MSG_UNREGISTER:
                        service.mClients.remove(msg.replyTo);
                        Timber.d("Unegistered");
                        break;
                    case MSG_START_SCAN:
                        service.startScan();
                        Timber.d("Start Scan");
                        break;
                    default:
                        super.handleMessage(msg);
                }
            }
        }
    }
}