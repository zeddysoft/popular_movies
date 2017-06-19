package com.zeddysoft.popularmovies.parsers;

import com.zeddysoft.popularmovies.models.Movie;
import com.zeddysoft.popularmovies.models.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by azeez on 6/19/17.
 */

public class TrailerParser {

    public static List<Trailer> parseTrailerResponse(String response) throws JSONException {
        List<Trailer> trailers = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(response);
        JSONArray jsonArray = jsonObject.getJSONArray("results");
        for(int i =0;i<jsonArray.length(); ++i){
            trailers.add(new Trailer(jsonArray.getJSONObject(i)));
        }
        return trailers;
    }
}
