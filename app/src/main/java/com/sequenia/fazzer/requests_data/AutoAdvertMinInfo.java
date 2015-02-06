package com.sequenia.fazzer.requests_data;

/**
 * Created by chybakut2004 on 04.02.15.
 */
public class AutoAdvertMinInfo {
    private int car_model_id;
    private int car_mark_id;
    private String car_model_name;
    private String car_mark_name;
    private int year;
    private int price;
    private int id;


    public AutoAdvertMinInfo() {


    };

    public int getCarMarkId() {
        return car_mark_id;
    }

    public void setCarMarkId(int car_mark_id) {
        this.car_mark_id = car_mark_id;
    }

    public int getCarModelId() {
        return car_model_id;
    }

    public void setCarModelId(int car_model_id) {
        this.car_model_id = car_model_id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCarModelName() {
        return car_model_name;
    }

    public void setCarModelName(String car_model_name) {
        this.car_model_name = car_model_name;
    }

    public String getCarMarkName() {
        return car_mark_name;
    }

    public void setCarMarkName(String car_mark_name) {
        this.car_mark_name = car_mark_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
