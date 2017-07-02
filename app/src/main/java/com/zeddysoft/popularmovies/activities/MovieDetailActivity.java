package com.zeddysoft.popularmovies.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;
import com.zeddysoft.popularmovies.R;
import com.zeddysoft.popularmovies.adapters.MovieAdapter;
import com.zeddysoft.popularmovies.adapters.TrailerAdapter;
import com.zeddysoft.popularmovies.apis.ApiManager;
import com.zeddysoft.popularmovies.fragments.OverviewFragment;
import com.zeddysoft.popularmovies.fragments.ReviewFragment;
import com.zeddysoft.popularmovies.fragments.TrailerFragment;
import com.zeddysoft.popularmovies.models.Movie;
import com.zeddysoft.popularmovies.models.Trailer;
import com.zeddysoft.popularmovies.parsers.MovieParser;
import com.zeddysoft.popularmovies.parsers.TrailerParser;
import com.zeddysoft.popularmovies.utils.ZoomOutPageTransformer;

import org.json.JSONException;

import java.util.List;

public class MovieDetailActivity extends AppCompatActivity
        implements ApiManager.MovieApiCallback, TrailerAdapter.TrailerPlayListener {

    private ImageView posterThumbnail;
    private TextView releaseDate;
    private TextView overview;
    private TextView movieRating;
    private FloatingActionButton markAsFavourite;
    private TextView movieTitle;
    private ApiManager apiManager;
    private ProgressBar progressBar;
    private RecyclerView trailer_list;
    private List<Trailer> trailers;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ViewPager viewpager;
    private ScreenSlidePagerAdapter mPagerAdapter;
    private TabLayout movieDetailsTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
//
//        enableActionBar();
//        setActionBarTitle();

        Movie movie = getIntent().getExtras().getParcelable(getString(R.string.movie_intent_key));

        initViews();
        apiManager = ApiManager.getApiManager();
        //loadDataIntoViews(movie);
    }

    private void setActionBarTitle() {

        String movieDetailsTitle = getString(R.string.movie_details_title);
        getSupportActionBar().setTitle(movieDetailsTitle);
    }

    private void enableActionBar() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedItemId = item.getItemId();
        if (selectedItemId == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadDataIntoViews(Movie movie) {
        releaseDate.setText(movie.getReleaseDate().substring(0, 4));
        overview.setText(movie.getOverview());
        String userRating = movie.getVoteAverage() + getString(R.string.movie_rating_suffix);
        movieRating.setText(userRating);
        movieTitle.setText(movie.getOriginalTitle());

        Picasso.with(this).load(getString(R.string.movie_image_base_url) + movie.getPosterPath()).into(posterThumbnail);

        fetchMovieTrailers(movie.getId());
    }

    private void fetchMovieTrailers(long id) {
        apiManager.fetchMovieTrailers(this, id);
    }

    private void initViews() {
        movieDetailsTab = (TabLayout) findViewById(R.id.movie_details_tab);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        posterThumbnail = (ImageView) findViewById(R.id.movie_poster_thumbnail);
        viewpager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
//        viewpager.setPageTransformer(true, new ZoomOutPageTransformer());
        viewpager.setAdapter(mPagerAdapter);
        movieDetailsTab.setupWithViewPager(viewpager);
        markAsFavourite = (FloatingActionButton) findViewById(R.id.mark_as_favourite_btn);
//        trailer_list = (RecyclerView) findViewById(R.id.trailer_list);
//        releaseDate = (TextView) findViewById(R.id.movie_release_date);
//        movieRating = (TextView) findViewById(R.id.movie_rating);
//        overview = (TextView) findViewById(R.id.overview);
//        movieTitle = (TextView) findViewById(R.id.movie_title);

        markAsFavourite.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(MovieDetailActivity.this, getString(R.string.mark_as_favourite_response), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResponse(String response) {
        progressBar.setVisibility(View.GONE);

        progressBar.setVisibility(View.GONE);
        try {
            trailers = TrailerParser.parseTrailerResponse(response);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            trailer_list.setLayoutManager(mLayoutManager);
            trailer_list.setItemAnimator(new DefaultItemAnimator());
            trailer_list.setAdapter(new TrailerAdapter(trailers, this));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onTrailerClicked(Trailer trailer) {
        viewAndPlayTrailer(trailer);
    }

    private void viewAndPlayTrailer(Trailer trailer) {
        String url = "https://www.youtube.com/watch?v=" + trailer.getKey();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        private static final int NUM_PAGES = 3;

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    Log.d("Fragment", "called");
                    return new OverviewFragment();
                case 1:
                    Log.d("Fragment", "called");
                    return new TrailerFragment();
                case 2:
                    Log.d("Fragment", "called");
                    return new ReviewFragment();
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    Log.d("Fragment title", "called");
                    return "Overview";
                case 1:
                    Log.d("Fragment title", "called");
                    return "Trailers";
                case 2:
                    Log.d("Fragment title", "called");
                    return "Reviews";

            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
