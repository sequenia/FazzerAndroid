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
    public static final String NEEDS_CLOSE = "NeedsClose";

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

    /*public static void loadAutoAdvertsFromAPI(Context context, ArrayList<AutoAdvertMinInfo> autoAdverts, AutoAdvertsAdapter adapter) {
        new AutoAdvertsLoader(context, autoAdverts, adapter).execute(ApiHelper.AUTO_ADVERTS_URL + "?auth_token=" + getUserPreferences(context).getString("AuthToken", ""));
    }*/
}
