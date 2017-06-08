package com.zeddysoft.popularmovies.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.android.volley.VolleyError;
import com.zeddysoft.popularmovies.R;
import com.zeddysoft.popularmovies.adapters.MovieAdapter;
import com.zeddysoft.popularmovies.apis.ApiManager;
import com.zeddysoft.popularmovies.models.Movie;
import com.zeddysoft.popularmovies.parsers.MovieParser;

import org.json.JSONException;

import java.util.List;

public class MoviePostersActivity extends AppCompatActivity
        implements ApiManager.MovieApiCallback{

    ApiManager apiManager;
    private GridView moviePosterView;
    private ProgressBar progressBar;
    private List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moviePosterView = (GridView) findViewById(R.id.movie_posters_view);
        moviePosterView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie selectedMovie=movies.get(position);

                Intent intent = new Intent(MoviePostersActivity.this,MovieDetailActivity.class);
                intent.putExtra(getString(R.string.movie_intent_key),selectedMovie);

                startActivity(intent);
            }
        });
        progressBar =(ProgressBar) findViewById(R.id.progressBar);

        apiManager=ApiManager.getApiManager();

        showMostPopularMoviesOnly();
    }

    private void showMostPopularMoviesOnly(){

        apiManager.fetchPopularMovies(this);
    }

    private void showHighestRatedMoviesOnly(){

    }

    private void showMovieDetailScreen(){

    }

    @Override
    public void onResponse(String response) {
        Log.d("Response",response);
        progressBar.setVisibility(View.GONE);
        try {
            movies = MovieParser.parseMovieResponse(response);
            moviePosterView.setAdapter(new MovieAdapter(this,movies));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progressBar.setVisibility(View.GONE);
    }

}
