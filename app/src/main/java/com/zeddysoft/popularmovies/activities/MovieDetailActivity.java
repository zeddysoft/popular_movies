package com.zeddysoft.popularmovies.activities;

import android.content.ContentValues;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.zeddysoft.popularmovies.R;
import com.zeddysoft.popularmovies.apis.ApiManager;
import com.zeddysoft.popularmovies.database.MovieContract;
import com.zeddysoft.popularmovies.database.MovieContract.FavouriteMovieEntry;
import com.zeddysoft.popularmovies.database.MovieLab;
import com.zeddysoft.popularmovies.fragments.OverviewFragment;
import com.zeddysoft.popularmovies.fragments.ReviewFragment;
import com.zeddysoft.popularmovies.fragments.TrailerFragment;
import com.zeddysoft.popularmovies.models.Movie;
import com.zeddysoft.popularmovies.models.Trailer;
import com.zeddysoft.popularmovies.utils.ZoomOutPageTransformer;


import java.sql.SQLException;
import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView posterThumbnail;
    private TextView releaseDate;
    private TextView movieRating;
    private FloatingActionButton markAsFavourite;
    private TextView movieTitle;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ViewPager viewpager;
    private ScreenSlidePagerAdapter mPagerAdapter;
    private TabLayout movieDetailsTab;
    private NestedScrollView scrollView;
    private Movie movie;
    private MovieLab movieLab;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        movie = getIntent().getExtras().getParcelable(getString(R.string.movie_intent_key));

        initViews();
        enableActionBar();

        loadDataIntoViews(movie);
    }

    private void enableActionBar() {

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {

            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            collapsingToolbarLayout.setTitle(" ");

        }
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
        double userRating = movie.getVoteAverage();
        movieRating.setText(userRating + "");
        movieTitle.setText(movie.getOriginalTitle());
        releaseDate.setText(movie.getReleaseDate().substring(0, 4));
        Picasso.with(this).load(getString(R.string.movie_image_base_url) + movie.getPosterPath()).into(posterThumbnail);

    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        releaseDate = (TextView) findViewById(R.id.tv_movie_language_date);
        movieTitle = (TextView) findViewById(R.id.movie_title_TV);
        movieRating = (TextView) findViewById(R.id.movie_ratings);
        movieDetailsTab = (TabLayout) findViewById(R.id.movie_details_tab);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        posterThumbnail = (ImageView) findViewById(R.id.movie_poster_thumbnail);
        viewpager = (ViewPager) findViewById(R.id.pager);
        scrollView = (NestedScrollView) findViewById(R.id.nested_scrollview);
        if (scrollView != null) {
            scrollView.setFillViewport(true);
        }
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewpager.setPageTransformer(true, new ZoomOutPageTransformer());
        viewpager.setAdapter(mPagerAdapter);
        movieDetailsTab.setupWithViewPager(viewpager);
        markAsFavourite = (FloatingActionButton) findViewById(R.id.mark_as_favourite_btn);

        markAsFavourite.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ContentValues contentValues = MovieLab.getContentValues(movie);

                try{
                    Uri uri = MovieDetailActivity.this.getContentResolver().insert(
                            FavouriteMovieEntry.CONTENT_URI, contentValues
                    );
                    if( uri != null){
                        Toast.makeText(MovieDetailActivity.this, getString(R.string.mark_as_favourite_response), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception ex){
                    Toast.makeText(MovieDetailActivity.this, getString(R.string.favourite_already_added), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        private static final int NUM_PAGES = 3;

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Bundle bundle = new Bundle();
            switch (position) {
                case 0:
                    bundle.putString(getString(R.string.overview_data_key), movie.getOverview());
                    Fragment overviewFragment = new OverviewFragment();
                    overviewFragment.setArguments(bundle);
                    return overviewFragment;
                case 1:
                    bundle.putLong(getString(R.string.trailer_data_key), movie.getId());
                    Fragment trailerFragment = new TrailerFragment();
                    trailerFragment.setArguments(bundle);
                    return trailerFragment;
                case 2:
                    bundle.putLong(getString(R.string.review_data_key), movie.getId());
                    Fragment reviewFragment = new ReviewFragment();
                    reviewFragment.setArguments(bundle);
                    return reviewFragment;
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Overview";
                case 1:
                    return "Trailers";
                case 2:
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
