package com.sequenia.fazzer.objects;

import io.realm.RealmObject;

/**
 * Created by chybakut2004 on 04.02.15.
 */
public class AutoAdvertMinInfo extends RealmObject {
    private int car_model_id;
    private int car_mark_id;
    private String car_model_name;
    private String car_mark_name;
    private int year;
    private int price;
    private int id;
    private String photo_preview_url;

    public AutoAdvertMinInfo() {

    };

    public String getPhoto_preview_url() {
        return photo_preview_url;
    }

    public void setPhoto_preview_url(String photo_preview_url) {
        this.photo_preview_url = photo_preview_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCar_mark_name() {
        return car_mark_name;
    }

    public void setCar_mark_name(String car_mark_name) {
        this.car_mark_name = car_mark_name;
    }

    public String getCar_model_name() {
        return car_model_name;
    }

    public void setCar_model_name(String car_model_name) {
        this.car_model_name = car_model_name;
    }

    public int getCar_mark_id() {
        return car_mark_id;
    }

    public void setCar_mark_id(int car_mark_id) {
        this.car_mark_id = car_mark_id;
    }

    public int getCar_model_id() {
        return car_model_id;
    }

    public void setCar_model_id(int car_model_id) {
        this.car_model_id = car_model_id;
    }
}
