package com.sequenia.fazzer.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.sequenia.fazzer.requests_data.CarMark;
import com.sequenia.fazzer.requests_data.City;

import java.lang.reflect.Type;

/**
 * Created by chybakut2004 on 06.02.15.
 */
public class CarMarkDeserializer implements JsonDeserializer<CarMark> {


    @Override
    public CarMark deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        CarMark carMark = new CarMark();

        JsonObject jsonObject = json.getAsJsonObject();

        carMark.setId(jsonObject.get("id").getAsInt());
        carMark.setName(jsonObject.get("name").getAsString());

        return carMark;
    }
}
