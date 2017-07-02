package com.zeddysoft.popularmovies.parsers;

import com.zeddysoft.popularmovies.models.Review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Azeez.Taiwo on 7/1/2017.
 */

public class ReviewParser {
    public static List<Review> parseReviewResponse(String response) throws JSONException {
        List<Review> reviews = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(response);
        JSONArray jsonArray = jsonObject.getJSONArray("results");
        for(int i =0;i<jsonArray.length(); ++i){
            reviews.add(new Review(jsonArray.getJSONObject(i)));
        }
        return reviews;
    }
}
