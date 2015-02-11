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


    public static String getAuthToken(Context context) {
        SharedPreferences mPreferences = getUserPreferences(context);
        return mPreferences.getString("AuthToken", "");
    }

    public static int getCitiesVersion(Context context) {
        SharedPreferences mPreferences = getUserPreferences(context);
        return mPreferences.getInt(CITIES_VERSION, 0);
    }

    public static int getCarMarksVersion(Context context) {
        SharedPreferences mPreferences = getUserPreferences(context);
        return mPreferences.getInt(CAR_MARKS_VERSION, 0);
    }

    public static int getCarModelsVersion(Context context) {
        SharedPreferences mPreferences = getUserPreferences(context);
        return mPreferences.getInt(CAR_MODELS_VERSION, 0);
    }


    public static void setCitiesVersion(Context context, int version) {
        SharedPreferences mPreferences = getUserPreferences(context);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(FazzerHelper.CITIES_VERSION, version);
        editor.commit();
    }

    public static void setCarMarksVersion(Context context, int version) {
        SharedPreferences mPreferences = getUserPreferences(context);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(FazzerHelper.CAR_MARKS_VERSION, version);
        editor.commit();
    }

    public static void setCarModelsVersion(Context context, int version) {
        SharedPreferences mPreferences = getUserPreferences(context);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(FazzerHelper.CAR_MODELS_VERSION, version);
        editor.commit();
    }

    private static SharedPreferences getUserPreferences(Context context) {
        return context.getSharedPreferences(FazzerHelper.CURRENT_USER_PREFERENCES, HomeActivity.MODE_PRIVATE);
    }
}
