package com.sequenia.fazzer.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.sequenia.fazzer.objects.City;

import java.lang.reflect.Type;

/**
 * Created by chybakut2004 on 06.02.15.
 */
public class CityDeserializer implements JsonDeserializer<City> {


    @Override
    public City deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        City city = new City();

        JsonObject jsonObject = json.getAsJsonObject();

        city.setId(jsonObject.get("id").getAsInt());
        city.setName(jsonObject.get("name").getAsString());

        return city;
    }
}
