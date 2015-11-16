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
import com.jasondelport.notes.data.model.BTDeviceData;

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
    private Map<String, BTDeviceData> mDevices;
    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            BTDeviceData wrappedDevice = new BTDeviceData(device, rssi, scanRecord);
            mDevices.put(device.getAddress(), wrappedDevice);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble);

        mDevices = new HashMap<>();

        mBluetoothAdapter = ((BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE))
                .getAdapter();
        if (mBluetoothAdapter != null) {

            if (mBluetoothAdapter.isEnabled()) {

                // below can get names of classic devices but is resource hungry and unreliable
                // mBluetoothAdapter.startDiscovery();

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
    protected void onDestroy() {
        super.onDestroy();
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


    private void showResults() {
        Timber.d("Scan Results:");
        for (Map.Entry<String, BTDeviceData> deviceData : mDevices.entrySet()) {


        }
    }

    @TargetApi(21)
    private class SampleScanCallback extends ScanCallback {

        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            //Timber.d("strength = %d", result.getRssi());
            //Timber.d("device = %s", result.getDevice().getAddress());
            //Timber.d("scan = %d", result.getScanRecord().getDeviceName());
            if (mDevices != null) {
                BTDeviceData deviceData
                        = new BTDeviceData(result.getDevice(), result.getRssi(), result.getScanRecord());
                mDevices.put(result.getDevice().getAddress(), deviceData);
            }
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            Timber.e("Bluetooth Scan Failed: Error code: -> %d", errorCode);
        }
    }

}
