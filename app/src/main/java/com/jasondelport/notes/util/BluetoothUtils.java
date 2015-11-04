package com.jasondelport.notes.util;

/**
 * Created by jasondelport on 04/11/15.
 */
public class BluetoothUtils {
    final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    enum BeaconType {
        ALTBEACON, IBEACON, EDDYSTONE, UNKNOWN;
    }

    public static BeaconType getBeaconType(byte[] scanRecord) {
        for (int startByte = 0; startByte < scanRecord.length; startByte++) {

            if (scanRecord.length-startByte > 19) { // need at least 19 bytes for Eddystone-UID
                if (scanRecord[startByte+0] == (byte)0xaa && scanRecord[startByte+1] == (byte)0xfe &&
                        scanRecord[startByte+2] == (byte)0x00) {
                    return BeaconType.EDDYSTONE;
                }
            }

            if (scanRecord.length-startByte > 24) { // need at least 24 bytes for AltBeacon
                if (scanRecord[startByte+2] == (byte)0xbe && scanRecord[startByte+3] == (byte)0xac) {
                    return BeaconType.ALTBEACON;
                }
            }

            if (((int) scanRecord[startByte + 2] & 0xff) == 0x02 &&
                    ((int) scanRecord[startByte + 3] & 0xff) == 0x15) {
                return BeaconType.IBEACON;
            }

        }
        return BeaconType.UNKNOWN;
    }

      /*


    for (int startByte = 0; startByte < scanRecord.length; startByte++) {
            if (scanRecord.length-startByte > 19) { // need at least 19 bytes for Eddystone-UID
                // Check that this has the right pattern needed for this to be Eddystone-UID
                if (scanRecord[startByte+0] == (byte)0xaa && scanRecord[startByte+1] == (byte)0xfe &&
                        scanRecord[startByte+2] == (byte)0x00) {
                    // This is an Eddystone-UID beacon.
                    byte[] namespaceIdentifierBytes = Arrays.copyOfRange(scanRecord, startByte+4, startByte+13);
                    byte[] instanceIdentifierBytes = Arrays.copyOfRange(scanRecord, startByte+14, startByte+19);
                    // TODO: do something with the above identifiers here
                }
            }
            if (scanRecord.length-startByte > 24) { // need at least 24 bytes for AltBeacon
                // Check that this has the right pattern needed for this to be AltBeacon
                // iBeacon has a slightly different layout.  Do a Google search to find it.
                if (scanRecord[startByte+2] == (byte)0xbe && scanRecord[startByte+3] == (byte)0xac) {
                    // This is an AltBeacon
                    byte[] uuidBytes = Arrays.copyOfRange(scanRecord, startByte+4, startByte+19);
                    byte[] majorBytes = Arrays.copyOfRange(scanRecord, startByte+20, startByte+21);
                    byte[] minorBytes = Arrays.copyOfRange(scanRecord, startByte+22, startByte+23);
                    // TODO: do something with the above identifiers here
                }

            }
        }



     */


    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public void getAppleBeacon(byte[] scanRecord) {
        int startByte = 2;
        while (startByte <= 5) {
            if (((int) scanRecord[startByte + 2] & 0xff) == 0x02 && //Identifies an iBeacon
                    ((int) scanRecord[startByte + 3] & 0xff) == 0x15) { //Identifies correct data length
                break;
            }
            startByte++;
        }
        //Convert to hex String
        byte[] uuidBytes = new byte[16];
        System.arraycopy(scanRecord, startByte + 4, uuidBytes, 0, 16);
        String hexString = bytesToHex(uuidBytes);

        //Here is your UUID
        String uuid = hexString.substring(0, 8) + "-" +
                hexString.substring(8, 12) + "-" +
                hexString.substring(12, 16) + "-" +
                hexString.substring(16, 20) + "-" +
                hexString.substring(20, 32);

        //Here is your Major value
        int major = (scanRecord[startByte + 20] & 0xff) * 0x100 + (scanRecord[startByte + 21] & 0xff);

        //Here is your Minor value
        int minor = (scanRecord[startByte + 22] & 0xff) * 0x100 + (scanRecord[startByte + 23] & 0xff);

        int txPower = (int) scanRecord[startByte + 24]; // this one is signed

    }


    protected static double getDistance(int txPower, double rssi) {
        if (rssi == 0) {
            return -1.0; // if we cannot determine accuracy, return -1.
        }

        double ratio = rssi*1.0/txPower;
        if (ratio < 1.0) {
            return Math.pow(ratio,10);
        }
        else {
            double distance =  (0.89976)*Math.pow(ratio,7.7095) + 0.111;
            return distance;
        }
    }
}
