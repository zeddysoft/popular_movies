package com.zeddysoft.popularmovies.database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Azeez.Taiwo on 7/2/2017.
 */

public class MovieContract {

    private MovieContract(){}

    public static final String AUTHORITY = "com.zeddysoft.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_FAVOURITE_MOVIES = "favourite_movie";

    public static final class FavouriteMovieEntry implements BaseColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVOURITE_MOVIES).build();

        public static final String TABLE_NAME = "favourite_movie";

        public static final String COLUMN_MOVIE_ID = "movieId";
        public static final String COLUMN_POSTER_PATH = "posterPath";
        public static final String COLUMN_ORIGINAL_TITLE = "originalTitle";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_VOTE_AVERAGE = "voteAverage";
        public static final String COLUMN_RELEASE_DATE = "releaseDate";
    }
}
