package com.sequenia.fazzer.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.sequenia.fazzer.objects.CarModel;

import java.lang.reflect.Type;

/**
 * Created by chybakut2004 on 06.02.15.
 */
public class CarModelDeserializer implements JsonDeserializer<CarModel> {


    @Override
    public CarModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        CarModel carModel = new CarModel();

        JsonObject jsonObject = json.getAsJsonObject();

        carModel.setId(jsonObject.get("id").getAsInt());
        carModel.setCar_mark_id(jsonObject.get("car_mark_id").getAsInt());
        carModel.setName(jsonObject.get("name").getAsString());

        return carModel;
    }
}
