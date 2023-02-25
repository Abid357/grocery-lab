package com.example.myapplication.core;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-YYYY");
    private static final DecimalFormat decimalFormatter = new DecimalFormat("0.###");

    public static Date parseDate(String string){
        if (string.isEmpty()) return null;
        try {
            return dateFormatter.parse(string);
        }
        catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }

    public static String formatDate(Date date){
        return dateFormatter.format(date);
    }

    public static String formatDecimal(double decimal){
        return decimalFormatter.format(decimal);
    }
}
