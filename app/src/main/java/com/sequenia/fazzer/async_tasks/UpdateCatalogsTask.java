package com.sequenia.fazzer.async_tasks;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sequenia.fazzer.gson.CarMarkDeserializer;
import com.sequenia.fazzer.gson.CarModelDeserializer;
import com.sequenia.fazzer.helpers.ApiHelper;
import com.sequenia.fazzer.helpers.FazzerHelper;
import com.sequenia.fazzer.helpers.RealmHelper;
import com.sequenia.fazzer.objects.CarMark;
import com.sequenia.fazzer.objects.CarModel;
import com.sequenia.fazzer.objects.City;
import com.sequenia.fazzer.requests_data.Response;
import com.sequenia.fazzer.gson.CityDeserializer;

import java.util.ArrayList;

/**
 * Created by chybakut2004 on 09.02.15.
 */
public class UpdateCatalogsTask extends WaitingDialog<Void, Void> {

    private int citiesVersion;
    private int carMarksVersion;
    private int carModelsVersion;

    public UpdateCatalogsTask(Context context) {
        super(context, "Загрузка справочников");
    }

    @Override
    protected void onPreExecute() {
        Context context = getContext();
        citiesVersion = FazzerHelper.getCitiesVersion(context);
        carMarksVersion = FazzerHelper.getCarMarksVersion(context);
        carModelsVersion = FazzerHelper.getCarModelsVersion(context);

        // Если справочников нет вообще, тогда показываем диалог.
        // Если их нужно всего лишь обновить, делаем это в фоне, чтобы не мучить пользователя.
        if(citiesVersion == 0 || carMarksVersion == 0 || carModelsVersion == 0) {
            super.onPreExecute();
        }
    }

    @Override
    protected Void doInBackground(Void... params) {
        Context context = getContext();
        String token = FazzerHelper.getAuthToken(context);

        try {
            int version = ApiHelper.getVersion(ApiHelper.CITIES_VERSION_URL + "?auth_token=" + token);
            if(citiesVersion != version) {
                setMessage("Загрузка городов");
                String s = ApiHelper.loadJson(ApiHelper.CITIES_URL + "?auth_token=" + token);
                if(s != null) {
                    Gson gson = new GsonBuilder()
                            .registerTypeAdapter(City.class, new CityDeserializer())
                            .create();
                    Response r = gson.fromJson(s, new TypeToken<Response<ArrayList<City>>>() {}.getType());
                    ArrayList<City> cities = (ArrayList<City>) r.getData();
                    RealmHelper.updateCities(context, cities);
                }
                FazzerHelper.setCitiesVersion(context, version);
            }

            version = ApiHelper.getVersion(ApiHelper.CAR_MARKS_VERSION_URL + "?auth_token=" + token);
            if(carMarksVersion != version) {
                setMessage("Загрузка марок");
                String s = ApiHelper.loadJson(ApiHelper.CAR_MARKS_URL + "?auth_token=" + token);
                if(s != null) {
                    Gson gson = new GsonBuilder()
                            .registerTypeAdapter(CarMark.class, new CarMarkDeserializer())
                            .create();
                    Response r = gson.fromJson(s, new TypeToken<Response<ArrayList<CarMark>>>() {
                    }.getType());
                    ArrayList<CarMark> carMarks = (ArrayList<CarMark>) r.getData();
                    RealmHelper.updateCarMarks(context, carMarks);
                }
                FazzerHelper.setCarMarksVersion(context, version);
            }

            version = ApiHelper.getVersion(ApiHelper.CAR_MODELS_VERSION_URL + "?auth_token=" + token);
            if(carModelsVersion != version) {
                setMessage("Загрузка моделей");
                String s = ApiHelper.loadJson(ApiHelper.CAR_MODELS_URL + "?auth_token=" + token);
                if(s != null) {
                    Gson gson = new GsonBuilder()
                            .registerTypeAdapter(CarModel.class, new CarModelDeserializer())
                            .create();
                    Response r = gson.fromJson(s, new TypeToken<Response<ArrayList<CarModel>>>() {
                    }.getType());
                    ArrayList<CarModel> carModels = (ArrayList<CarModel>) r.getData();
                    RealmHelper.updateCarModels(context, carModels);
                }
                FazzerHelper.setCarModelsVersion(context, version);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return super.doInBackground(params);
    }
}
