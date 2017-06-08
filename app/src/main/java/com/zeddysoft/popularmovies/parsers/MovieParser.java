package com.zeddysoft.popularmovies.parsers;

import android.util.Log;

import com.zeddysoft.popularmovies.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by azeez on 6/7/17.
 */

public class MovieParser {

    public static List<Movie> parseMovieResponse(String response) throws JSONException {
        JSONObject responseObject = new JSONObject(response);
        JSONArray array = responseObject.getJSONArray("results");
        Log.d("MP",response);
        List<Movie> movies = new ArrayList<>();

        for(int i=0;i<array.length();++i){
            movies.add(new Movie(array.getJSONObject(i)));
        }

        return movies;
    }
}

