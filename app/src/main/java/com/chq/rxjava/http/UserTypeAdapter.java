package com.chq.rxjava.http;

import com.chq.rxjava.entity.ExpressInfo;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**

 * Created by chq18099
 */
public class UserTypeAdapter implements JsonDeserializer<ExpressInfo> {
    @Override
    public ExpressInfo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonObject()) {
            Gson gson = new Gson();
            return gson.fromJson(json, ExpressInfo.class);
        }
        return null;
    }
}
