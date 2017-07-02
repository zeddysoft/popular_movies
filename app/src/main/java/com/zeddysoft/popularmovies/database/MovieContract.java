package com.zeddysoft.popularmovies.database;

import android.provider.BaseColumns;

/**
 * Created by Azeez.Taiwo on 7/2/2017.
 */

public class MovieContract {

    private MovieContract(){}

    public static final class FavouriteMovieEntry implements BaseColumns{
        public static final String TABLE_NAME = "favourite_movie";

        public static final String COLUMN_MOVIE_ID = "movieId";
        public static final String COLUMN_POSTER_PATH = "posterPath";
        public static final String COLUMN_ORIGINAL_TITLE = "originalTitle";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_VOTE_AVERAGE = "voteAverage";
        public static final String COLUMN_RELEASE_DATE = "releaseDate";
    }
}
