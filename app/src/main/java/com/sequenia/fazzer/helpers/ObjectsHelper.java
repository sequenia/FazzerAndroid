package com.sequenia.fazzer.helpers;

import com.sequenia.fazzer.objects.CarMark;
import com.sequenia.fazzer.objects.CarModel;
import com.sequenia.fazzer.objects.City;
import com.sequenia.fazzer.objects.Option;

import java.util.ArrayList;

import io.realm.RealmResults;

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

    public static String prettifyPrice(String price) {
        StringBuilder pretty = new StringBuilder("");
        int count = 0;
        for(int i = price.length() - 1; i >= 0; i--) {
            if(count % 3 == 0 && count != 0) {
                pretty.insert(0, ' ');
            }
            pretty.insert(0, price.charAt(i));
            count++;
        }
        pretty.append(" р.");
        return pretty.toString();
    }

    public static ArrayList<Option> genCityOptions(RealmResults<City> cities) {
        ArrayList<Option> options = new ArrayList<Option>();

        for(int i = 0; i < cities.size(); i++) {
            City object = cities.get(i);
            String name = object.getName();
            if(!name.contains(",")) {
                options.add(new Option(String.valueOf(object.getId()), name));
            }
        }

        for(int i = 0; i < cities.size(); i++) {
            City object = cities.get(i);
            String name = object.getName();
            if(name.contains(",")) {
                options.add(new Option(String.valueOf(object.getId()), name));
            }
        }

        return options;
    }

    public static ArrayList<Option> genCarMarkOptions(RealmResults<CarMark> carMarks) {
        ArrayList<Option> options = new ArrayList<Option>();

        for(int i = 0; i < carMarks.size(); i++) {
            CarMark object = carMarks.get(i);
            options.add(new Option(String.valueOf(object.getId()), object.getName()));
        }

        return options;
    }

    public static ArrayList<Option> genCarModelOptions(RealmResults<CarModel> carModels) {
        ArrayList<Option> options = new ArrayList<Option>();

        for(int i = 0; i < carModels.size(); i++) {
            CarModel object = carModels.get(i);
            options.add(new Option(String.valueOf(object.getId()), object.getName()));
        }

        return options;
    }
}
