package com.zeddysoft.popularmovies.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by azeez on 6/19/17.
 */

public class Trailer {
    private String key;
    private String name;

    public Trailer(JSONObject jsonObject) throws JSONException {
        setKey(jsonObject.getString("key"));
        setName(jsonObject.getString("name"));
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
