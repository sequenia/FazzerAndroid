package com.sequenia.fazzer.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.sequenia.fazzer.async_tasks.UpdateCatalogsTask;

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
    public static final String REGISTERED = "Registered";
    public final static String NEEDS_UPDATE_PREF = "NeedsUpdate";

    public static final String NO_CONNECTION = "Не удалось подключиться к серверу";

    public static String getAuthToken(Context context) {
        SharedPreferences mPreferences = getUserPreferences(context);
        return mPreferences.getString("AuthToken", "");
    }

    public static String getUserPhone(Context context) {
        SharedPreferences mPreferences = getUserPreferences(context);
        return mPreferences.getString(USER_PHONE, "");
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

    public static SharedPreferences getUserPreferences(Context context) {
        return context.getSharedPreferences(FazzerHelper.CURRENT_USER_PREFERENCES, context.MODE_PRIVATE);
    }

    public static void logout(Context context) {
        SharedPreferences mPreferences = getUserPreferences(context);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.remove(AUTH_TOKEN);
        editor.remove(USER_PHONE);
        editor.commit();
    }

    public static void updateCatalogs(Context context) {
        if(ActivityHelper.isNetworkAvailable(context)) {
            new UpdateCatalogsTask(context).execute();
        }
    }

    public static boolean isLoggedIn(Context context) {
        return getUserPreferences(context).contains(AUTH_TOKEN);
    }

    public static void setNeedsUpdate(Context context, boolean needsUpdate) {
        SharedPreferences.Editor editor = getUserPreferences(context).edit();
        editor.putBoolean(NEEDS_UPDATE_PREF, needsUpdate);
        editor.commit();
    }

    public static boolean isRegistered(Context context) {
        return getUserPreferences(context).getBoolean(REGISTERED, false);
    }
}
