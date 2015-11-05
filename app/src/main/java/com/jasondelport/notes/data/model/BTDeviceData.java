package com.jasondelport.notes.data.model;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothDevice;

import com.jasondelport.notes.util.ScanRecord;


/**
 * Created by jasondelport on 03/11/15.
 */
public class BTDeviceData {
    private BluetoothDevice mBluetoothDevcie;
    private int mRSSI;
    private byte[] mScanRecordData;

    private ScanRecord mScanRecord;

    @TargetApi(21)
    public BTDeviceData(BluetoothDevice bluetoothDevcie, int RSSI, android.bluetooth.le.ScanRecord scanRecord) {
        mBluetoothDevcie = bluetoothDevcie;
        mRSSI = RSSI;
        mScanRecordData = scanRecord.getBytes();
        mScanRecord = ScanRecord.parseFromBytes(scanRecord.getBytes());
    }

    public BTDeviceData(BluetoothDevice bluetoothDevcie, int RSSI, byte[] scanRecordData) {
        mBluetoothDevcie = bluetoothDevcie;
        mRSSI = RSSI;
        mScanRecordData = scanRecordData;
    }

    public BluetoothDevice getBluetoothDevice() {
        return mBluetoothDevcie;
    }

    public int getRSSI() {
        return mRSSI;
    }

    public ScanRecord getScanRecord() {
        return mScanRecord;
    }

    public byte[] getScanRecordData() {
        return mScanRecordData;
    }
}
