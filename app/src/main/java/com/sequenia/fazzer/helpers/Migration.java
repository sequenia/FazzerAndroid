package com.sequenia.fazzer.helpers;

import com.sequenia.fazzer.objects.AutoAdvertFullInfo;
import com.sequenia.fazzer.objects.AutoAdvertMinInfo;
import com.sequenia.fazzer.objects.FilterInfo;

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

        /*if (version == 10) {
            Table table = realm.getTable(AutoAdvertMinInfo.class);
            table.addColumn(ColumnType.STRING, "photo_url");

            table = realm.getTable(AutoAdvertFullInfo.class);
            table.addColumn(ColumnType.STRING, "photo_url");

            version++;
        }*/


        /*if (version == 14) {
            Table table = realm.getTable(FilterInfo.class);
            table.addColumn(ColumnType.INTEGER, "cityId");
            table.removeColumn(getIndexForProperty(table, "city_id"));

            version++;
        }*/

        /*if (version == 15) {
            Table table = realm.getTable(AutoAdvertMinInfo.class);
            printColumns(table);
            System.out.println("!!!!!!!!!!!");

            table = realm.getTable(AutoAdvertFullInfo.class);
            printColumns(table);

            version++;
        }*/

        if (version == 16) {
            Table table = realm.getTable(AutoAdvertMinInfo.class);
            table.removeColumn(getIndexForProperty(table, "photo_url"));
            table.addColumn(ColumnType.INTEGER, "car_model_id");
            table.addColumn(ColumnType.INTEGER, "car_mark_id");
            table.addColumn(ColumnType.STRING, "car_model_name");
            table.addColumn(ColumnType.STRING, "car_mark_name");
            table.addColumn(ColumnType.INTEGER, "year");
            table.addColumn(ColumnType.INTEGER, "price");
            table.addColumn(ColumnType.INTEGER, "id");
            table.addColumn(ColumnType.STRING, "photo_preview_url");
            printColumns(table);
            System.out.println("!!!!!!!!!!!");

            table = realm.getTable(AutoAdvertFullInfo.class);
            table.addColumn(ColumnType.INTEGER, "car_model_id");
            table.addColumn(ColumnType.INTEGER, "car_mark_id");
            table.addColumn(ColumnType.STRING, "car_model_name");
            table.addColumn(ColumnType.STRING, "car_mark_name");
            table.addColumn(ColumnType.INTEGER, "year");
            table.addColumn(ColumnType.INTEGER, "price");
            table.addColumn(ColumnType.INTEGER, "id");
            table.addColumn(ColumnType.STRING, "photo_preview_url");
            table.addColumn(ColumnType.STRING, "mileage");
            table.addColumn(ColumnType.STRING, "description");
            table.addColumn(ColumnType.INTEGER, "city_id");
            table.addColumn(ColumnType.STRING, "city_name");
            table.addColumn(ColumnType.STRING, "exchange");
            table.addColumn(ColumnType.STRING, "color");
            table.addColumn(ColumnType.STRING, "code");
            table.addColumn(ColumnType.STRING, "displacement");
            table.addColumn(ColumnType.STRING, "url");
            table.addColumn(ColumnType.STRING, "fuel");
            table.addColumn(ColumnType.STRING, "body");
            table.addColumn(ColumnType.STRING, "steering_wheel");
            table.addColumn(ColumnType.STRING, "drive");
            table.addColumn(ColumnType.STRING, "transmission");
            printColumns(table);

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

    private void printColumns(Table table) {
        for (int i = 0; i < table.getColumnCount(); i++) {
            System.out.println(table.getColumnName(i));
        }
    }
}
