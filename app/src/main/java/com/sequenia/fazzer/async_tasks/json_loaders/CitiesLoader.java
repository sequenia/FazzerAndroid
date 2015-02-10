package com.sequenia.fazzer.async_tasks.json_loaders;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sequenia.fazzer.activities.AutoAdvertActivity;
import com.sequenia.fazzer.activities.HomeActivity;
import com.sequenia.fazzer.helpers.RealmHelper;
import com.sequenia.fazzer.requests_data.AutoAdvertFullInfo;
import com.sequenia.fazzer.requests_data.AutoAdvertMinInfo;
import com.sequenia.fazzer.requests_data.City;
import com.sequenia.fazzer.requests_data.Response;

import java.util.ArrayList;

/**
 * Created by chybakut2004 on 09.02.15.
 */
public class CitiesLoader extends JsonLoader {
    Context context;

    public CitiesLoader(Context context) {
        this.context = context;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if(s != null) {
            Response r = new Gson().fromJson(s, new TypeToken<Response<ArrayList<City>>>() {}.getType());
            ArrayList<City> cities = (ArrayList<City>) r.getData();

            RealmHelper.updateCities(context, cities);
        } else {
            Toast.makeText(context, "Данные не получены", Toast.LENGTH_LONG).show();
        }
    }
}
