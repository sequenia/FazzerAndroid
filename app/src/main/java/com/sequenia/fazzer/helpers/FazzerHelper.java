package com.sequenia.fazzer.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.sequenia.fazzer.activities.HomeActivity;

/**
 * Created by chybakut2004 on 06.02.15.
 */
public class FazzerHelper {
    public static final String CURRENT_USER_PREFERENCES = "CurrentUser";
    public static final String AUTH_TOKEN = "AuthToken";
    public static final String CITIES_VERSION = "CitiesVersion";
    public static final String CAR_MARKS_VERSION = "CarMarksVersion";
    public static final String CAR_MODELS_VERSION = "CarModelsVersion";
    public static final String AUTO_ADVERT_ID = "AutoAdvertId";
    public static final String USER_PHONE = "UserPhone";

    //public static final String HOST = "http://178.62.184.226";
    public static final String HOST = "http://192.168.1.42:3000";
    public static final String AUTO_ADVERTS_URL = HOST + "/api/v1/auto_adverts.json";
    public static final String FILTERS_URL = HOST + "/api/v1/auto_filters.json";
    public final static String LOGIN_API_ENDPOINT_URL = HOST + "/api/v1/sessions.json";
    public final static String REGISTER_API_ENDPOINT_URL = HOST + "/api/v1/registrations";
    public static final String AUTO_ADVERT_URL = HOST + "/api/v1/auto_adverts/";

    public static void updateCatalogs(Context context) {
        SharedPreferences mPreferences = context.getSharedPreferences(CURRENT_USER_PREFERENCES, HomeActivity.MODE_PRIVATE);
        int citiesVersion = mPreferences.getInt(CITIES_VERSION, 0);
        int carMarksVersion = mPreferences.getInt(CAR_MARKS_VERSION, 0);
        int carModelsVersion = mPreferences.getInt(CAR_MODELS_VERSION, 0);

        if(citiesVersion == 0) {
            updateCities(context);
        }

        if(carMarksVersion == 0) {
           updateCarMarks(context);
        }

        if(carModelsVersion == 0) {
            updateCarModels(context);
        }
    }

    public static void updateCities(Context context) {

    }

    public static void updateCarMarks(Context context) {

    }

    public static void updateCarModels(Context context) {

    }
}
