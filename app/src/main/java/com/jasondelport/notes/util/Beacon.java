package com.jasondelport.notes.util;

/**
 * Created by jasondelport on 04/11/15.
 */
public class Beacon {
    public final static int ALTBEACON = 0;
    public final static int IBEACON = 1;
    public final static int EDDYSTONE = 2;
    public final static int UNKNOWN = 3;

    private byte[] mData;
    private int mStartByte;
    private int mType;

    public byte[] getData() {
        return mData;
    }

    public void setData(byte[] data) {
        mData = data;
    }

    public int getStartByte() {
        return mStartByte;
    }

    public void setStartByte(int startByte) {
        mStartByte = startByte;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }
}
