package com.sequenia.fazzer.helpers;

import android.content.Context;

import com.sequenia.fazzer.objects.AutoAdvertFullInfo;
import com.sequenia.fazzer.objects.AutoAdvertMinInfo;
import com.sequenia.fazzer.objects.CarMark;
import com.sequenia.fazzer.objects.CarModel;
import com.sequenia.fazzer.objects.City;
import com.sequenia.fazzer.objects.FilterInfo;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

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

    public static RealmResults<City> getCities(Context context) {
        Realm realm = Realm.getInstance(context);

        RealmQuery<City> query = realm.where(City.class);
        RealmResults<City> result = query.findAllSorted("name");

        return result;
    }

    public static RealmResults<CarMark> getCarMarks(Context context) {
        Realm realm = Realm.getInstance(context);

        RealmQuery<CarMark> query = realm.where(CarMark.class);
        RealmResults<CarMark> result = query.findAllSorted("name");

        return result;
    }

    public static RealmResults<CarModel> getCarModels(Context context) {
        Realm realm = Realm.getInstance(context);

        RealmQuery<CarModel> query = realm.where(CarModel.class);
        RealmResults<CarModel> result = query.findAllSorted("name");

        return result;
    }

    public static RealmResults<CarModel> getCarModelsForMark(Context context, int carMarkId) {
        Realm realm = Realm.getInstance(context);

        RealmQuery<CarModel> query = realm.where(CarModel.class).equalTo("car_mark_id", carMarkId);
        RealmResults<CarModel> result = query.findAllSorted("name");

        return result;
    }

    public static AutoAdvertFullInfo saveAutoAdvertFullInfo(Context context, AutoAdvertFullInfo advert) {
        Realm realm = Realm.getInstance(context);
        AutoAdvertFullInfo realmAdvert = null;

        realm.beginTransaction();

        realmAdvert = realm.copyToRealm(advert);

        realm.commitTransaction();

        return realmAdvert;
    }

    public static AutoAdvertFullInfo getAllAutoAdvertFullInfoById(Context context, int id) {
        Realm realm = Realm.getInstance(context);
        return realm.where(AutoAdvertFullInfo.class).equalTo("id", id).findFirst();
    }

    public static void saveAutoAdvertMinInfos(Context context, ArrayList<AutoAdvertMinInfo> adverts) {
        Realm realm = Realm.getInstance(context);

        realm.beginTransaction();

        for(AutoAdvertMinInfo autoAdvertMinInfo : adverts) {
            realm.copyToRealm(autoAdvertMinInfo);
        }

        realm.commitTransaction();
    }

    public static void deleteAllAutoAdvertMinInfos(Context context) {
        Realm realm = Realm.getInstance(context);
        RealmResults<AutoAdvertMinInfo> result = realm.where(AutoAdvertMinInfo.class).findAll();

        realm.beginTransaction();

        for(int i = 0; i < result.size(); i++) {
            AutoAdvertMinInfo info = result.get(i);

            RealmResults<AutoAdvertFullInfo> fullInfoResult = realm.where(AutoAdvertFullInfo.class).equalTo("id", info.getId()).findAll();
            fullInfoResult.clear();
        }
        result.clear();

        realm.commitTransaction();
    }

    public static RealmResults<AutoAdvertMinInfo> getAllAutoAdvertMinInfos(Context context) {
        Realm realm = Realm.getInstance(context);
        return realm.where(AutoAdvertMinInfo.class).findAll();
    }

    public static <E> ArrayList<E> toArrayList(Class<E> clazz, RealmResults realmResults) {
        ArrayList<E> list = new ArrayList<E>();
        for(int i = 0; i < realmResults.size(); i++) {
            list.add((E) realmResults.get(i));
        }
        return list;
    }
}
