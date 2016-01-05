package com.jasondelport.playground.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import timber.log.Timber;

/**
 * Created by jasondelport on 17/04/15.
 */
public class LogUtils {
    public static void appendLog(String text) {
        File logFile = new File("sdcard/location_log.txt");
        if (!logFile.exists()) {
            try {
                boolean created = logFile.createNewFile();
            } catch (IOException e) {
                Timber.e(e, "Error creating log file");
            }
        }
        try {
            Calendar cal = Calendar.getInstance();
            String time = cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE);
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append(time + " -> " + text);
            buf.newLine();
            buf.close();
        } catch (Exception e) {
            Timber.e(e, "Error logging to file");
        }
    }

}
