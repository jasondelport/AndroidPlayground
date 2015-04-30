package com.jasondelport.notes.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by jasondelport on 17/04/15.
 */
public class LogUtils {
    public static void appendLog(String text) {
        File logFile = new File("sdcard/location_log.txt");
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Calendar cal = Calendar.getInstance();
            String time = cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE);
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append(time + " -> " + text);
            buf.newLine();
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
