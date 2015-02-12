package com.sequenia.fazzer.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sequenia.fazzer.activities.HomeActivity;
import com.sequenia.fazzer.requests_data.City;
import com.sequenia.fazzer.requests_data.Response;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by chybakut2004 on 11.02.15.
 */
public class ApiHelper {
    public static final String HOST = "http://178.62.184.226";
    //public static final String HOST = "http://192.168.1.42:3000";
    //public static final String HOST = "http://192.168.0.36:3000";
    public static final String CAR_MARKS_VERSION_URL = HOST + "/api/v1/car_marks_version.json";
    public static final String CAR_MODELS_VERSION_URL = HOST + "/api/v1/car_models_version.json";
    public static final String CITIES_VERSION_URL = HOST + "/api/v1/cities_version.json";
    public static final String CAR_MODELS_URL = HOST + "/api/v1/car_models.json";
    public static final String CAR_MARKS_URL = HOST + "/api/v1/car_marks.json";
    public static final String CITIES_URL = HOST + "/api/v1/cities.json";
    public static final String AUTO_ADVERT_URL = HOST + "/api/v1/auto_adverts/";
    public final static String REGISTER_API_ENDPOINT_URL = HOST + "/api/v1/registrations";
    public final static String LOGIN_API_ENDPOINT_URL = HOST + "/api/v1/sessions.json";
    public static final String FILTERS_URL = HOST + "/api/v1/auto_filters.json";
    public static final String AUTO_ADVERTS_URL = HOST + "/api/v1/auto_adverts.json";

    public static String loadJson(String url_str) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = null;
        if(url_str != null) {
            try {
                URL url = new URL(url_str);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultJson;
    }

    public static int getVersion(String url_str) {
        String s = loadJson(url_str);
        Response r = new Gson().fromJson(s, new TypeToken<Response<Integer>>() {}.getType());
        return (Integer) r.getData();
    }
}
