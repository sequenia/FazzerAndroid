package com.sequenia.fazzer.helpers;

import com.sequenia.fazzer.objects.AutoAdvertFullInfo;
import com.sequenia.fazzer.objects.AutoAdvertMinInfo;

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
        /*
            Table table = realm.getTable(CarMark.class);

            table.addColumn(ColumnType.INTEGER, "id");
            table.addColumn(ColumnType.STRING, "name");

            table = realm.getTable(CarModel.class);

            table.addColumn(ColumnType.INTEGER, "id");
            table.addColumn(ColumnType.STRING, "name");
            table.addColumn(ColumnType.INTEGER, "car_mark_id");

            version++;
        */

        if (version == 10) {
            Table table = realm.getTable(AutoAdvertMinInfo.class);
            table.addColumn(ColumnType.STRING, "photo_url");

            table = realm.getTable(AutoAdvertFullInfo.class);
            table.addColumn(ColumnType.STRING, "photo_url");

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
