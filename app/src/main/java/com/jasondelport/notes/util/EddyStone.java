package com.jasondelport.notes.util;

/**
 * Created by jasondelport on 05/11/15.
 */
public class EddyStone {
    public final static int UID = 0;
    public final static int TLM = 1;
    public final static int URL = 2;
    public final static int UNKNOWN = 3;
    private static final byte UID_FRAME_TYPE = 0x00;
    private static final byte URL_FRAME_TYPE = 0x10;
    private static final byte TLM_FRAME_TYPE = 0x20;


    public static int getEddyStoneBeaconType(byte[] serviceData) {
        switch (serviceData[0]) {
            case UID_FRAME_TYPE:
                return UID;
            case TLM_FRAME_TYPE:
                return TLM;
            case URL_FRAME_TYPE:
                return URL;
            default:
                return UNKNOWN;
        }
    }
}
