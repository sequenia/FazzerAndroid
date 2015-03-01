package com.sequenia.fazzer.gson;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import io.realm.RealmObject;

/**
 * Created by chybakut2004 on 27.02.15.
 */
public class RealmStrategy implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return f.getDeclaringClass().equals(RealmObject.class);
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}
