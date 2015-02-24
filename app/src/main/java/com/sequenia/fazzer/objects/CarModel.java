package com.sequenia.fazzer.objects;

import io.realm.RealmObject;

/**
 * Created by chybakut2004 on 11.02.15.
 */
public class CarModel extends RealmObject {
    private int id;
    private String name;
    private int car_mark_id;

    public int getCar_mark_id() {
        return car_mark_id;
    }

    public void setCar_mark_id(int car_mark_id) {
        this.car_mark_id = car_mark_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
