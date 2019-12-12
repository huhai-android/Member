package com.newdjk.member.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public enum DateUtil {
    instance;

    public boolean dfDate(long current, String f2) {
        Date date2 = null;
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            date2 = sp.parse(f2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long will = date2.getTime();
        long time = 40L * 7 * 24 * 60 * 60 * 1000;
        long t = will - current;
        if (t > time) {
            return false;
        }

        return true;
    }
}
