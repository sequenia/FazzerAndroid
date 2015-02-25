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

    public static int OptionToIntNoZero(Option option) {
        int result = 0;
        if(option != null) {
            String id = option.getId();
            if(id != null) {
                result = Integer.parseInt(id);
            }
        }
        return result;
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

    public static String prettifyNumber(String number, String postfix) {
        if(number == null) {
            return null;
        } else {
            StringBuilder pretty = new StringBuilder("");
            int count = 0;
            for(int i = number.length() - 1; i >= 0; i--) {
                if(count % 3 == 0 && count != 0) {
                    pretty.insert(0, ' ');
                }
                pretty.insert(0, number.charAt(i));
                count++;
            }
            if(postfix != null) {
                pretty.append(postfix);
            }
            return pretty.toString();
        }
    }

    public static String prettifyFuel(String fuel) {
        String pretty = null;
        if(fuel != null) {
            if (fuel.equals("gasoline")) {
                pretty = "бензин";
            } else if (fuel.equals("diesel")) {
                pretty = "дизель";
            }
        }
        return pretty;
    }

    public static String prettifyDisplacement(String displacement) {
        String pretty = null;
        if(displacement != null) {
            pretty = pretty + " л.";
        }
        return null;
    }

    public static String prettifyTransmission(String transmission) {
        String pretty = null;
        if(transmission != null) {
            if(transmission.equals("manual")) {
                pretty = "механика";
            } else if(transmission.equals("automatic")) {
                pretty = "автомат";
            }
        }
        return pretty;
    }

    public static String prettifyDrive(String drive) {
        String pretty = null;
        if(drive != null) {
            if(drive.equals("full")) {
                pretty = "4WD";
            } else if(drive.equals("front")) {
                pretty = "передний";
            } else if(drive.equals("rear")) {
                pretty = "задний";
            }
        }
        return pretty;
    }

    public static String prettifyBody(String body) {
        String pretty = null;
        if(body != null) {
            if(body.equals("sedan")) {
                pretty = "седан";
            } else if(body.equals("jeep")) {
                pretty = "джип";
            } else if(body.equals("hatchback")) {
                pretty = "хэтчбек";
            } else if(body.equals("estate")) {
                pretty = "универсал";
            } else if(body.equals("van")) {
                pretty = "минивэн / микроавтобус";
            } else if(body.equals("coupe")) {
                pretty = "купе";
            } else if(body.equals("open")) {
                pretty = "открытый";
            } else if(body.equals("pickup")) {
                pretty = "пикап";
            }
        }
        return pretty;
    }

    public static String prettifyWheel(String wheel) {
        String pretty = null;
        if(wheel != null) {
            if(wheel.equals("right")) {
                pretty = "правый";
            } else if(wheel.equals("left")) {
                pretty = "левый";
            }
        }
        return pretty;
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
