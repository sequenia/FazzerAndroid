package com.sequenia.fazzer.helpers;

import android.content.Context;

import com.sequenia.fazzer.requests_data.CarMark;
import com.sequenia.fazzer.requests_data.CarModel;
import com.sequenia.fazzer.requests_data.City;
import com.sequenia.fazzer.requests_data.FilterInfo;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmMigration;
import io.realm.RealmQuery;
import io.realm.internal.ColumnType;
import io.realm.internal.Table;
import io.realm.processor.RealmVersionChecker;

/**
 * Created by chybakut2004 on 06.02.15.
 */
public class RealmHelper {

    public static FilterInfo findOrCreateFilter(Context context, String userPhone) {
        FilterInfo filterInfo = getFilter(context, userPhone);

        if(filterInfo == null) {
            filterInfo = new FilterInfo();
            filterInfo.setUserPhone(userPhone);
            filterInfo = createFilter(filterInfo, context);
        }

        return filterInfo;
    }

    public static FilterInfo createFilter(FilterInfo filterInfo, Context context) {
        Realm realm = Realm.getInstance(context);
        FilterInfo realmFilterInfo = null;

        realm.beginTransaction();

        realmFilterInfo = realm.copyToRealm(filterInfo);

        realm.commitTransaction();

        return realmFilterInfo;
    }

    public static FilterInfo getFilter(Context context, String userPhone) {
        Realm realm = Realm.getInstance(context);

        RealmQuery<FilterInfo> query = realm.where(FilterInfo.class).equalTo("userPhone", userPhone);
        FilterInfo filterInfo = query.findFirst();

        return  filterInfo;
    }

    public static void migrate(Context context) {
        Realm.migrateRealmAtPath(context.getFilesDir().toString() + "/" + Realm.DEFAULT_REALM_NAME, new Migration());
    }

    public static void updateCities(Context context, ArrayList<City> cities) {
        Realm realm = Realm.getInstance(context);

        realm.beginTransaction();

        for(City city : cities) {
            City existed = getCityById(context, city.getId());
            if(existed == null) {
                realm.copyToRealm(city);
            } else {
                if(existed.getName() != city.getName()) {
                    existed.setName(city.getName());
                }
            }
        }

        realm.commitTransaction();
    }

    public static void updateCarMarks(Context context, ArrayList<CarMark> carMarks) {
        Realm realm = Realm.getInstance(context);

        realm.beginTransaction();

        for(CarMark carMark : carMarks) {
            CarMark existed = getCarMarkById(context, carMark.getId());
            if(existed == null) {
                realm.copyToRealm(carMark);
            } else {
                if(existed.getName() != carMark.getName()) {
                    existed.setName(carMark.getName());
                }
            }
        }

        realm.commitTransaction();
    }

    public static void updateCarModels(Context context, ArrayList<CarModel> carModels) {
        Realm realm = Realm.getInstance(context);

        realm.beginTransaction();

        for(CarModel carModel : carModels) {
            CarModel existed = getCarModelById(context, carModel.getId());
            if(existed == null) {
                realm.copyToRealm(carModel);
            } else {
                if(existed.getName() != carModel.getName()) {
                    existed.setName(carModel.getName());
                }
                if(existed.getCar_mark_id() != carModel.getCar_mark_id()) {
                    existed.setCar_mark_id(carModel.getCar_mark_id());
                }
            }
        }

        realm.commitTransaction();
    }

    public static City getCityById(Context context, int id) {
        Realm realm = Realm.getInstance(context);

        RealmQuery<City> query = realm.where(City.class).equalTo("id", id);
        City city = query.findFirst();

        return city;
    }

    public static CarMark getCarMarkById(Context context, int id) {
        Realm realm = Realm.getInstance(context);

        RealmQuery<CarMark> query = realm.where(CarMark.class).equalTo("id", id);
        CarMark carMark = query.findFirst();

        return carMark;
    }

    public static CarModel getCarModelById(Context context, int id) {
        Realm realm = Realm.getInstance(context);

        RealmQuery<CarModel> query = realm.where(CarModel.class).equalTo("id", id);
        CarModel carModel = query.findFirst();

        return carModel;
    }
}
