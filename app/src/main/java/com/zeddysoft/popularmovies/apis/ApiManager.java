package com.zeddysoft.popularmovies.apis;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zeddysoft.popularmovies.App;
import com.zeddysoft.popularmovies.R;
import com.zeddysoft.popularmovies.models.Movie;

/**
 * Created by Azeez.Taiwo on 6/7/2017.
 */

public class ApiManager {

    RequestQueue queue;
    static ApiManager apiManager;
    static Context context;

    private ApiManager(){
        queue = Volley.newRequestQueue(App.getContext());
    }

    public static ApiManager getApiManager(){

        if(apiManager == null){
            context = App.getContext();
            apiManager = new ApiManager();
            return apiManager;
        }
        return apiManager;
    }

    public void fetchPopularMovies(final MovieApiCallback movieApiCallback){
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                context.getString(R.string.base_url)+
                        context.getString(R.string.popular_movie_endpoint)+
                        context.getString(R.string.query_parameter)+
                        context.getString(R.string.api_key)
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

    public void fetchMovieTrailers(final MovieApiCallback movieApiCallback, long movieId){
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                context.getString(R.string.base_url)+
                        "/movie/"+movieId +"/videos"+
                        context.getString(R.string.query_parameter)+
                        context.getString(R.string.api_key)
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


    public void fetchHighestRatedMovies(final MovieApiCallback movieApiCallback){
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                context.getString(R.string.base_url)+
                        context.getString(R.string.highest_rated_endpoint)+
                        context.getString(R.string.query_parameter)+
                        context.getString(R.string.api_key)
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

