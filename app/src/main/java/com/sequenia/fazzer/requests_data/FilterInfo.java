package com.sequenia.fazzer.requests_data;

import io.realm.RealmObject;

/**
 * Created by chybakut2004 on 06.02.15.
 */
public class FilterInfo extends RealmObject {
    private int carMarkId;
    private int carModelId;
    private int minYear;
    private int maxYear;
    private float minPrice;
    private float maxPrice;

    public int getCarMarkId() {
        return carMarkId;
    }

    public void setCarMarkId(int carMarkId) {
        this.carMarkId = carMarkId;
    }

    public int getCarModelId() {
        return carModelId;
    }

    public void setCarModelId(int carModelId) {
        this.carModelId = carModelId;
    }

    public int getMinYear() {
        return minYear;
    }

    public void setMinYear(int minYear) {
        this.minYear = minYear;
    }

    public int getMaxYear() {
        return maxYear;
    }

    public void setMaxYear(int maxYear) {
        this.maxYear = maxYear;
    }

    public float getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(float minPrice) {
        this.minPrice = minPrice;
    }

    public float getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(float maxPrice) {
        this.maxPrice = maxPrice;
    }
}
