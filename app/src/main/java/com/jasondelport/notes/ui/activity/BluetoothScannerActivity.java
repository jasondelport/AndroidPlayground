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
import android.os.ParcelUuid;

import com.jasondelport.notes.R;
import com.jasondelport.notes.data.model.BTDeviceData;
import com.jasondelport.notes.util.Beacon;
import com.jasondelport.notes.util.BluetoothUtils;
import com.jasondelport.notes.util.IBeacon;
import com.jasondelport.notes.util.ScanRecord;

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

    private String getDeviceTypeName(int type) {
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

    private String getBeaconTypeName(Beacon beacon) {
        String result;
        switch (beacon.getType()) {
            case Beacon.EDDYSTONE:
                result = "EDDYSTONE";
                break;
            case Beacon.IBEACON:
                result = "IBEACON";
                break;
            case Beacon.ALTBEACON:
                result = "ALTBEACON";
                break;
            default:
                result = "UNKNOWN";
                break;
        }
        return result;
    }

    private void showResults() {
        Timber.d("Scan Results:");
        for (Map.Entry<String, BTDeviceData> deviceData : mDevices.entrySet()) {
            BluetoothDevice device = deviceData.getValue().getBluetoothDevice();
            int type = device.getType();
            String deviceName = device.getName();
            String address = deviceData.getKey(); // hardware address
            int strength = deviceData.getValue().getRSSI(); // signal strength in dBm
            ScanRecord record = deviceData.getValue().getScanRecord();
            Timber.d(printByteData(deviceData.getValue().getScanRecord().getBytes()));
            Timber.d("strength = %d", strength);
            Timber.d("device = %s -> %s -> %s", deviceName, address, getDeviceTypeName(type));
            Timber.d("record = %s -> %d", record.getDeviceName(), record.getTxPowerLevel());
            if (record.getTxPowerLevel() != Integer.MIN_VALUE) {
                Timber.d("distance : %f", BluetoothUtils.getDistance(record.getTxPowerLevel(), strength));
            } else {
                Timber.d("distance = 0. getTxPowerLevel() returned Integer.MIN_VALUE");
            }
            Beacon beacon =
                    BluetoothUtils.getBeaconType(deviceData.getValue().getScanRecordData());
            Timber.d("beacon = %s", getBeaconTypeName(beacon));
            if (beacon.getType() == Beacon.IBEACON) {
                IBeacon iBeacon = new IBeacon(beacon);
                Timber.d("uuid: %s", iBeacon.getUuid());
                Timber.d("major + minor: %d %d", iBeacon.getMajor(), iBeacon.getMinor());
                Timber.d("txPower: %d", iBeacon.getTxPower());
                Timber.d("distance : %f", BluetoothUtils.getDistance(iBeacon.getTxPower(), strength));
            } else if (beacon.getType() == Beacon.EDDYSTONE) {
                byte[] serviceData = record.getServiceData(ParcelUuid.fromString("0000FEAA-0000-1000-8000-00805F9B34FB"));
                Timber.d("service data -> %s", BluetoothUtils.bytesToHex(serviceData));
                //int eddyStoneType = EddyStone.getEddyStoneBeaconType(serviceData);
            }

        }
    }

    public String printByteData(byte[] bytes) {
        String result = "BINARY  |DEC|HX|ASCII\n";
        for (byte b : bytes) {
            String binary = Integer.toBinaryString(b & 255 | 256).substring(1);
            int decimal = Integer.parseInt(binary, 2);
            String dec =  Integer.toString(decimal);
            String hex = Integer.toHexString(decimal);
            //int dec = Integer.parseInt(hex, 16);
            String ascii = Character.toString((char) decimal);
            result += binary
                    + "|" + String.format("%1$" + 3 + "s", dec)
                    + "|" + String.format("%1$" + 2 + "s", hex)
                    + "|" + ascii + "\n";
        }
        return result;
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
