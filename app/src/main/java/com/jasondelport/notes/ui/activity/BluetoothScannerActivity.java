package com.jasondelport.notes.ui.activity;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.jasondelport.notes.R;
import com.jasondelport.notes.data.model.BluetoothDeviceWrapper;
import com.jasondelport.notes.util.IBeacon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import timber.log.Timber;

/**
 * Created by jasondelport on 03/11/15.
 */
public class BluetoothScannerActivity extends BaseActivity {

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeScanner mBluetoothLeScanner;
    private SampleScanCallback mScanCallback;
    private Map<String, BluetoothDeviceWrapper> mDevices = new HashMap<>();
    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            BluetoothDeviceWrapper wrappedDevice = new BluetoothDeviceWrapper(device, rssi, scanRecord);
            mDevices.put(device.getAddress(), wrappedDevice);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble);


        mBluetoothAdapter = ((BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE))
                .getAdapter();
        if (mBluetoothAdapter != null) {

            // Is Bluetooth turned on?
            if (mBluetoothAdapter.isEnabled()) {

                Timber.d("Start scanning");
                if (android.os.Build.VERSION.SDK_INT >= 21) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            stopScan();
                        }
                    }, 10000);

                    mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
                    mScanCallback = new SampleScanCallback();
                    mBluetoothLeScanner.startScan(buildScanFilters(), buildScanSettings(), mScanCallback);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mBluetoothAdapter.stopLeScan(mLeScanCallback);
                            showResults();
                        }
                    }, 10000);
                    mBluetoothAdapter.startLeScan(mLeScanCallback);
                }
            } else {
                // Prompt user to turn on Bluetooth (logic continues in onActivityResult()).
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    @TargetApi(21)
    private void stopScan() {
        mBluetoothLeScanner.stopScan(mScanCallback);
        mScanCallback = null;
        showResults();
    }


    @Override
    protected void onPause() {
        super.onStop();
        mDevices.clear();
        mDevices = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:

                if (resultCode == RESULT_OK) {


                } else {


                }

            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @TargetApi(21)
    private List<ScanFilter> buildScanFilters() {
        List<ScanFilter> scanFilters = new ArrayList<>();

        ScanFilter.Builder builder = new ScanFilter.Builder();
        // Comment out the below line to see all BLE devices around you
        //builder.setServiceUuid(Constants.Service_UUID);
        scanFilters.add(builder.build());

        return scanFilters;
    }

    @TargetApi(21)
    private ScanSettings buildScanSettings() {
        ScanSettings.Builder builder = new ScanSettings.Builder();
        builder.setScanMode(ScanSettings.SCAN_MODE_LOW_POWER);
        return builder.build();
    }

    private String getTypeDescription(int type) {
        String result = "UNKNOWN";
        switch (type) {
            case BluetoothDevice.DEVICE_TYPE_CLASSIC:
                result = "CLASSIC";
                break;
            case BluetoothDevice.DEVICE_TYPE_DUAL:
                result = "DUAL";
                break;
            case BluetoothDevice.DEVICE_TYPE_LE:
                result = "LE";
                break;
            case BluetoothDevice.DEVICE_TYPE_UNKNOWN:
                result = "UNKNOWN";
                break;
        }
        return result;
    }

    private void showResults() {
        Timber.d("Scan Results:");
        for (Map.Entry<String, BluetoothDeviceWrapper> wrappedDevice : mDevices.entrySet()) {
            BluetoothDevice device = wrappedDevice.getValue().getBluetoothDevcie();
            int type = device.getType();
            String deviceName = device.getName();
            String address = wrappedDevice.getKey(); // hardware address
            int strength = wrappedDevice.getValue().getRSSI(); // signal strength in dBm
            Timber.d("strength = %d", strength);
            Timber.d("device = %s -> %s -> %s", deviceName, address, getTypeDescription(type));
            IBeacon iBeacon = IBeacon.fromScanData(wrappedDevice.getValue().getScanRecordData(),
                    wrappedDevice.getValue().getRSSI(), wrappedDevice.getValue().getBluetoothDevcie());
            if (iBeacon != null) {

            } else {
                Timber.d("Not an iBeacon");
            }
        }
    }

    @TargetApi(21)
    private class SampleScanCallback extends ScanCallback {

        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            BluetoothDeviceWrapper wrappedDevice
                    = new BluetoothDeviceWrapper(result.getDevice(), result.getRssi(), result.getScanRecord());
            mDevices.put(result.getDevice().getAddress(), wrappedDevice);
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            Timber.e("Bluetooth Scan Failed: Error code: -> %d", errorCode);
        }
    }

}
