package com.zeddysoft.popularmovies.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.VolleyError;
import com.zeddysoft.popularmovies.R;
import com.zeddysoft.popularmovies.apis.ApiManager;

public class MainActivity extends AppCompatActivity implements ApiManager.MovieApiCallback{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ApiManager.get

        showMostPopularMoviesOnly();
    }

    private void showMostPopularMoviesOnly(){

    }

    private void showHighestRatedMoviesOnly(){


    }

    private void showMovieDetailScreen(){

    }

    @Override
    public void onResponse(String response) {

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }
}
