package com.zeddysoft.popularmovies.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Azeez.Taiwo on 7/1/2017.
 */

public class Review {
    private String author;
    private String content;


    public Review(JSONObject jsonObject) throws JSONException {
        setAuthor(jsonObject.getString("author"));
        setContent(jsonObject.getString("content"));
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
