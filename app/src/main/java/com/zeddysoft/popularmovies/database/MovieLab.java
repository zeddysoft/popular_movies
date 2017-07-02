package com.zeddysoft.popularmovies.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zeddysoft.popularmovies.database.MovieContract.FavouriteMovieEntry;
import com.zeddysoft.popularmovies.models.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Azeez.Taiwo on 7/2/2017.
 */

public class MovieLab {
    private Context mContext;
    private SQLiteDatabase mDb;

    private static MovieLab movieLab;

    private MovieLab(Context context) {
        mContext = context.getApplicationContext();
        mDb = new MovieDbHelper(context).getWritableDatabase();
    }

    public static MovieLab getInstance(Context context) {
        return movieLab == null ? new MovieLab(context) : movieLab;
    }

    private FavouriteMovieCursorWrapper queryFavouriteMovies() {
        Cursor cursor = mDb.query(
                FavouriteMovieEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        return new FavouriteMovieCursorWrapper(cursor);

    }

    public void addFavouriteMovie(Movie movie) {
        ContentValues contentValues = getContentValues(movie);
        mDb.insert(FavouriteMovieEntry.TABLE_NAME, null, contentValues);
    }

    private static ContentValues getContentValues(Movie movie) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(FavouriteMovieEntry.COLUMN_MOVIE_ID, movie.getId());
        contentValues.put(FavouriteMovieEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
        contentValues.put(FavouriteMovieEntry.COLUMN_ORIGINAL_TITLE, movie.getOriginalTitle());
        contentValues.put(FavouriteMovieEntry.COLUMN_OVERVIEW, movie.getOverview());
        contentValues.put(FavouriteMovieEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
        contentValues.put(FavouriteMovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());

        return contentValues;

    }

    public List<Movie> getFavouriteMovies() {

        List<Movie> favouriteMovies = new ArrayList<>();
        FavouriteMovieCursorWrapper cursorWrapper = queryFavouriteMovies();

        try {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()) {
                Movie favouriteMovie = cursorWrapper.getMovie();
                favouriteMovies.add(favouriteMovie);
                cursorWrapper.moveToNext();
            }
        } finally {
            cursorWrapper.close();
        }
        return favouriteMovies;
    }
}