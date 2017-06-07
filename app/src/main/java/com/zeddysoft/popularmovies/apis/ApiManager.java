package com.zeddysoft.popularmovies.apis;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by Azeez.Taiwo on 6/7/2017.
 */

public class ApiManager {
    private static final String API_KEY = "c0040567876f8c727e4a4ff2df55fdec";
    private final String BASE_URL = "http://api.themoviedb.org/3";
    private final String POPULAR_MOVIE_ENDPOINT = "/movie/popular";



    RequestQueue queue;
    static ApiManager apiManager;
    Context context;

    private ApiManager(){
        queue = Volley.newRequestQueue(context);
    }

    public static ApiManager getApiManager(){

        if(apiManager == null){
            apiManager = new ApiManager();
            return apiManager;
        }
        return apiManager;
    }

    public void fetchPopularMovies(final MovieApiCallback movieApiCallback){
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                BASE_URL+POPULAR_MOVIE_ENDPOINT+"?api_key="+API_KEY
                ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        movieApiCallback.onResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                movieApiCallback.onErrorResponse(error);
            }
        });
        queue.add(stringRequest);
    }


   public interface MovieApiCallback{
        void onResponse(String response);
        void onErrorResponse(VolleyError error);
    }
}

