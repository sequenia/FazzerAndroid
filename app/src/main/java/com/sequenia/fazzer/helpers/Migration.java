package com.sequenia.fazzer.helpers;

import com.sequenia.fazzer.requests_data.CarMark;
import com.sequenia.fazzer.requests_data.CarModel;
import com.sequenia.fazzer.requests_data.City;
import com.sequenia.fazzer.requests_data.FilterInfo;

import io.realm.Realm;
import io.realm.RealmMigration;
import io.realm.internal.ColumnType;
import io.realm.internal.Table;

/**
 * Created by chybakut2004 on 06.02.15.
 */
public class Migration implements RealmMigration {

    @Override
    public long execute(Realm realm, long version) {
        /*System.out.println(version);
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

        if(version == 1) {
            version++;
        }

        if(version == 2) {
            version++;
        }

        if (version == 3) {
            Table filtersTable = realm.getTable(FilterInfo.class);

            filtersTable.renameColumn(getIndexForProperty(filtersTable, "user_id"), "userId");

            version++;
        }

        if (version == 6) {
            version++;
        }

        if (version == 7) {
            Table filtersTable = realm.getTable(FilterInfo.class);

            filtersTable.removeColumn(getIndexForProperty(filtersTable, "userId"));
            filtersTable.addColumn(ColumnType.STRING, "userPhone");

            version++;
        }

        if (version == 8) {
            Table table = realm.getTable(City.class);

            table.addColumn(ColumnType.INTEGER, "id");
            table.addColumn(ColumnType.STRING, "name");

            version++;
        }

        if (version == 9) {
            Table table = realm.getTable(CarMark.class);

            table.addColumn(ColumnType.INTEGER, "id");
            table.addColumn(ColumnType.STRING, "name");

            table = realm.getTable(CarModel.class);

            table.addColumn(ColumnType.INTEGER, "id");
            table.addColumn(ColumnType.STRING, "name");
            table.addColumn(ColumnType.INTEGER, "car_mark_id");

            version++;
        }*/

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
