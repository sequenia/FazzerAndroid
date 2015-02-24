package com.sequenia.fazzer.objects;

import io.realm.RealmObject;

/**
 * Created by chybakut2004 on 09.02.15.
 */
public class City extends RealmObject {
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
