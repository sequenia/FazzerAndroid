package com.sequenia.fazzer.requests_data;

/**
 * Created by chybakut2004 on 06.02.15.
 */
public class FilterInfo {
    private Integer car_mark_id = null;
    private Integer car_model_id = null;
    private Integer min_year = null;
    private Integer max_year = null;
    private Float min_price = null;
    private Float max_price = null;

    public int getCarMarkId() {
        return car_mark_id;
    }

    public void setCarMarkId(int car_mark_id) {
        this.car_mark_id = car_mark_id;
    }

    public int getCarModelIid() {
        return car_model_id;
    }

    public void setCarModelId(int car_model_id) {
        this.car_model_id = car_model_id;
    }

    public int getMinYear() {
        return min_year;
    }

    public void setMinYear(int min_year) {
        this.min_year = min_year;
    }

    public int getMaxYear() {
        return max_year;
    }

    public void setMaxYear(int max_year) {
        this.max_year = max_year;
    }

    public float getMinPrice() {
        return min_price;
    }

    public void setMinPrice(float min_price) {
        this.min_price = min_price;
    }

    public float getMaxPrice() {
        return max_price;
    }

    public void setMaxPrice(float max_price) {
        this.max_price = max_price;
    }
}
