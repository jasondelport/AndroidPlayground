package com.jasondelport.notes.util;

/**
 * Created by jasondelport on 04/11/15.
 */
public class BluetoothUtils {
    final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static Beacon getBeaconType(byte[] scanRecord) {
        Beacon beacon = new Beacon();
        beacon.setData(scanRecord);
        beacon.setStartByte(0);
        beacon.setType(Beacon.UNKNOWN);
        for (int startByte = 0; startByte < scanRecord.length; startByte++) {

            if (scanRecord.length-startByte > 19) { // need at least 19 bytes for Eddystone-UID
                if (scanRecord[startByte+0] == (byte)0xaa && scanRecord[startByte+1] == (byte)0xfe &&
                        scanRecord[startByte+2] == (byte)0x00) {
                    beacon.setData(scanRecord);
                    beacon.setStartByte(startByte);
                    beacon.setType(Beacon.EDDYSTONE);
                    break;

                }
            }

            if (scanRecord.length-startByte > 24) { // need at least 24 bytes for AltBeacon
                if (scanRecord[startByte+2] == (byte)0xbe && scanRecord[startByte+3] == (byte)0xac) {
                    beacon.setData(scanRecord);
                    beacon.setStartByte(startByte);
                    beacon.setType(Beacon.ALTBEACON);
                    break;
                }
            }
            if (startByte <= 5) {
                if (((int) scanRecord[startByte + 2] & 0xff) == 0x02 &&
                        ((int) scanRecord[startByte + 3] & 0xff) == 0x15) {
                    beacon.setData(scanRecord);
                    beacon.setStartByte(startByte);
                    beacon.setType(Beacon.IBEACON);
                    break;
                }
            }

        }
        return beacon;
    }





      /*
      EddyStone-URL
      int txPower = (int) serviceData[1];
      byte[] urlBytes = Arrays.copyOfRange(serviceData, 2, 20);
      String url = UrlUtils.decodeUrl(serviceData);

    EddyStone-UID
    int txPower = (int) serviceData[1];
    byte[] uidBytes = Arrays.copyOfRange(serviceData, 2, 18);
    beacon.uidStatus.uidValue = Utils.toHexString(uidBytes);

    EddyStone-TLM
    ByteBuffer buf = ByteBuffer.wrap(serviceData);
    buf.get();  // We already know the frame type byte is 0x20.

    // The version should be zero.
    byte ver = buf.get();
    String version = String.format("0x%02X", version);

    short voltage = buf.getShort();
    String voltage = String.valueOf(voltage);

    byte tempIntegral = buf.get();
    int tempFractional = (buf.get() & 0xff);
    float temp = tempIntegral + (tempFractional / 256.0f);
    String temperature = String.valueOf(temp);

    int advCnt = buf.getInt();
    String advanceCnt = String.valueOf(advCnt);

    int uptime = buf.getInt();
    String secCnt = String.format("%d (%d days)", uptime, TimeUnit.SECONDS.toDays(uptime / 10));





byte[] serviceData = result
    .getScanRecord()
    .getServiceData(ParcelUuid.fromString("0000FEAA-0000-1000-8000-00805F9B34FB"));



    switch (serviceData[0]) {
      case Constants.UID_FRAME_TYPE:
        UidValidator.validate(deviceAddress, serviceData, beacon);
        break;
      case Constants.TLM_FRAME_TYPE:
        TlmValidator.validate(deviceAddress, serviceData, beacon);
        break;
      case Constants.URL_FRAME_TYPE:
        UrlValidator.validate(deviceAddress, serviceData, beacon);
        break;
      default:
        String err = String.format("Invalid frame type byte %02X", serviceData[0]);
        beacon.frameStatus.invalidFrameType = err;
        logDeviceError(deviceAddress, err);
        break;
    }




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


    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }


    public static double getDistance(int txPower, double rssi) {
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
