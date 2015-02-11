package com.sequenia.fazzer.requests_data;

import io.realm.RealmObject;

/**
 * Created by chybakut2004 on 11.02.15.
 */
public class CarMark extends RealmObject {
    private int id;
    private String name;

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
