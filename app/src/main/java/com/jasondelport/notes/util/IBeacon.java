package com.jasondelport.notes.util;

/**
 * Created by jasondelport on 04/11/15.
 */
public class IBeacon {
    private String uuid;
    private int major;
    private int minor;
    private int txPower;

    public IBeacon(Beacon beacon) {
        byte[] scanRecord = beacon.getData();
        int startByte = beacon.getStartByte();
        byte[] uuidBytes = new byte[16];
        System.arraycopy(scanRecord, startByte + 4, uuidBytes, 0, 16);
        String hexString = BluetoothUtils.bytesToHex(uuidBytes);

        this.uuid = hexString.substring(0, 8) + "-" +
                hexString.substring(8, 12) + "-" +
                hexString.substring(12, 16) + "-" +
                hexString.substring(16, 20) + "-" +
                hexString.substring(20, 32);

        this.major = (scanRecord[startByte + 20] & 0xff) * 0x100 + (scanRecord[startByte + 21] & 0xff);

        this.minor = (scanRecord[startByte + 22] & 0xff) * 0x100 + (scanRecord[startByte + 23] & 0xff);

        this.txPower = (int) scanRecord[startByte + 24]; // this one is signed
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getTxPower() {
        return txPower;
    }

    public String getUuid() {
        return uuid;
    }
}
