package com.sequenia.fazzer.helpers;

import android.content.Context;

import com.sequenia.fazzer.requests_data.FilterInfo;

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

    public static FilterInfo findOrCreateFilter(Context context) {
        FilterInfo filterInfo = getFilter(context);

        if(filterInfo == null) {
            filterInfo = createFilter(new FilterInfo(), context);
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

    public static FilterInfo getFilter(Context context) {
        Realm realm = Realm.getInstance(context);

        RealmQuery<FilterInfo> query = realm.where(FilterInfo.class);
        FilterInfo filterInfo = query.findFirst();

        return  filterInfo;
    }

    public static void migrate(Context context) {
        Realm.migrateRealmAtPath(context.getFilesDir().toString() + "/" + Realm.DEFAULT_REALM_NAME, new Migration());
    }
}
