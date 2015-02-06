package com.sequenia.fazzer.helpers;

import com.sequenia.fazzer.requests_data.FilterInfo;

import io.realm.Realm;
import io.realm.RealmMigration;
import io.realm.internal.Table;

/**
 * Created by chybakut2004 on 06.02.15.
 */
public class Migration implements RealmMigration {

    @Override
    public long execute(Realm realm, long version) {
        if (version == 0) {
            Table filtersTable = realm.getTable(FilterInfo.class);

            filtersTable.renameColumn(getIndexForProperty(filtersTable, "car_model_id"), "carModelId");
            filtersTable.renameColumn(getIndexForProperty(filtersTable, "car_mark_id"), "carMarkId");
            filtersTable.renameColumn(getIndexForProperty(filtersTable, "min_year"), "minYear");
            filtersTable.renameColumn(getIndexForProperty(filtersTable, "max_year"), "maxYear");
            filtersTable.renameColumn(getIndexForProperty(filtersTable, "min_price"), "minPrice");
            filtersTable.renameColumn(getIndexForProperty(filtersTable, "max_price"), "maxPrice");

            version++;
        }

        return version;
    }

    private long getIndexForProperty(Table table, String name) {
        for (int i = 0; i < table.getColumnCount(); i++) {
            if (table.getColumnName(i).equals(name)) {
                return i;
            }
        }
        return -1;
    }
}
