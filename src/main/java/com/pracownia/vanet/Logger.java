package com.pracownia.vanet;

import java.io.File;
import java.io.FileWriter;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private static Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
    private static String path = "Log_" + new SimpleDateFormat("HH_mm_ss_dd_MM_yyyy").format(new Date());
    private static File file = new File(path + ".txt");

    public static void log(String msg)
    {

        FileWriter fr = null;
        try {
            fr = new FileWriter(file, true);
            fr.write(msg + System.lineSeparator());
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
