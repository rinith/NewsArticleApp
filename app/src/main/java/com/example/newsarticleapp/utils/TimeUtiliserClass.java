package com.example.newsarticleapp.utils;

public class TimeUtiliserClass {
    public static int getMinutes(String time) {

        String[] hm = time.split(":");
        int mins = Integer.parseInt(hm[0]) * 60 + Integer.parseInt(hm[1]);

        return mins;

    }
}
