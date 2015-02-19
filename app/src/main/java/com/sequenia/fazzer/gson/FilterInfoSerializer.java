package com.sequenia.fazzer.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.sequenia.fazzer.requests_data.FilterInfo;

import java.lang.reflect.Type;

/**
 * Created by chybakut2004 on 06.02.15.
 */
public class FilterInfoSerializer implements JsonSerializer<FilterInfo> {

    @Override
    public JsonElement serialize(FilterInfo filterInfo, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();

        setIfNotZero(result, "car_mark_id", filterInfo.getCarMarkId());
        setIfNotZero(result, "car_model_id", filterInfo.getCarModelId());
        setIfNotZero(result, "max_year", filterInfo.getMaxYear());
        setIfNotZero(result, "min_year", filterInfo.getMinYear());
        setIfNotZero(result, "max_price", filterInfo.getMaxPrice());
        setIfNotZero(result, "min_price", filterInfo.getMinPrice());

        return result;
    }

    private void setIfNotZero(JsonObject result, String name, int data) {
        if(data != 0) {
            result.addProperty(name, data);
        }
    }

    private void setIfNotZero(JsonObject result, String name, float data) {
        if(data != 0) {
            result.addProperty(name, data);
        }
    }
}
