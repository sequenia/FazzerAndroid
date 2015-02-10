package com.sequenia.fazzer.async_tasks;

import android.content.Context;
import android.content.SharedPreferences;

import com.sequenia.fazzer.activities.HomeActivity;
import com.sequenia.fazzer.async_tasks.json_loaders.CitiesLoader;
import com.sequenia.fazzer.helpers.FazzerHelper;

/**
 * Created by chybakut2004 on 09.02.15.
 */
public class UpdateCatalogsTask extends WaitingDialog<Void, Void, Void> {

    private int citiesVersion;
    private String token;
    private int carMarksVersion;
    private int carModelsVersion;

    public UpdateCatalogsTask(Context context, String token) {
        super(context);
        this.token = token;
        setMessage("Загрузка справочников");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Context context = getContext();
        SharedPreferences mPreferences = context.getSharedPreferences(FazzerHelper.CURRENT_USER_PREFERENCES, HomeActivity.MODE_PRIVATE);
        citiesVersion = mPreferences.getInt(FazzerHelper.CITIES_VERSION, 0);
        carMarksVersion = mPreferences.getInt(FazzerHelper.CAR_MARKS_VERSION, 0);
        carModelsVersion = mPreferences.getInt(FazzerHelper.CAR_MODELS_VERSION, 0);
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            if(citiesVersion == 0) {
                CitiesLoader loader = new CitiesLoader(getContext());
                loader.execute(FazzerHelper.CITIES_URL + "?auth_token=" + token);
                loader.get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return super.doInBackground(params);
    }
}
