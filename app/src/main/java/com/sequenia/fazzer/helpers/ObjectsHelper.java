package com.sequenia.fazzer.helpers;

/**
 * Created by chybakut2004 on 06.02.15.
 */
public class ObjectsHelper {
    public static boolean isEmpty(String s) {
        if(s == null) return true;

        return (s.trim().equals(""));
    }

    public static int strToIntNoZero(String s) {
        int result = 0;
        if(!isEmpty(s)) {
            result = Integer.parseInt(s);
        }
        return result;
    }

    public static float strToFloatNoZero(String s) {
        float result = 0.0f;
        if(!isEmpty(s)) {
            result = Float.parseFloat(s);
        }
        return result;
    }

    public static String intToStrNoZero(int i) {
        String result = "";
        if(i != 0) {
            result = String.valueOf(i);
        }
        return result;
    }

    public static String floatToStrNoZero(float f) {
        String result = "";
        if(f != 0.0f) {
            result = String.valueOf(f);
        }
        return result;
    }
}