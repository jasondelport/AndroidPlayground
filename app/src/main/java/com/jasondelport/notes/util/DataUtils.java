package com.jasondelport.notes.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by jasondelport on 16/11/15.
 */
public class DataUtils {
    final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String printByteData(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("BINARY  |DEC|HX|ASCII\n");
        for (byte b : bytes) {
            String binary = Integer.toBinaryString(b & 255 | 256).substring(1);
            int decimal = Integer.parseInt(binary, 2); // base 2 to base 10, eg (00010011)2 = (19)10
            String dec = Integer.toString(decimal);
            String hex = Integer.toHexString(decimal); // base 10 to base 16, eg (19)10 = (13)16
            //int dec = Integer.parseInt(hex, 16);
            String ascii = Character.toString((char) decimal);
            sb.append(binary
                    + "|" + String.format("%1$" + 3 + "s", dec)
                    + "|" + String.format("%1$" + 2 + "s", hex)
                    + "|" + ascii + "\n");
        }
        return sb.toString();
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
