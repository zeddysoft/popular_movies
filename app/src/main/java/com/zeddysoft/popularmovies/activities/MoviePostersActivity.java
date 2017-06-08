package com.zeddysoft.popularmovies.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.zeddysoft.popularmovies.utils.NetworkUtils;

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

        if(isPhoneConnectedToInternet()){
            showMostPopularMoviesOnly();
        }else{
            showMessage(R.string.no_network_message);
        }

    }

    private void showMessage(int messageId) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setMessage(getString(messageId));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_poster_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_by_most_popular:
                showMostPopularMoviesOnly();
                return true;
            case R.id.sort_by_highest_rated:
                showHighestRatedMoviesOnly();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void showMostPopularMoviesOnly(){
        progressBar.setVisibility(View.VISIBLE);
        apiManager.fetchPopularMovies(this);
    }

    private void showHighestRatedMoviesOnly(){
        progressBar.setVisibility(View.VISIBLE);
        apiManager.fetchHighestRatedMovies(this);
    }

    @Override
    public void onResponse(String response) {
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
        error.printStackTrace();
        progressBar.setVisibility(View.GONE);
    }

    public boolean isPhoneConnectedToInternet() {
        return NetworkUtils.isPhoneConnectedToInternet();
    }
}
