package com.zeddysoft.popularmovies.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import com.zeddysoft.popularmovies.database.MovieContract.FavouriteMovieEntry;
import com.zeddysoft.popularmovies.models.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Azeez.Taiwo on 7/2/2017.
 */

public class MovieLab {


    public static ContentValues getContentValues(Movie movie) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(FavouriteMovieEntry.COLUMN_MOVIE_ID, movie.getId());
        contentValues.put(FavouriteMovieEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
        contentValues.put(FavouriteMovieEntry.COLUMN_ORIGINAL_TITLE, movie.getOriginalTitle());
        contentValues.put(FavouriteMovieEntry.COLUMN_OVERVIEW, movie.getOverview());
        contentValues.put(FavouriteMovieEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
        contentValues.put(FavouriteMovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());

        return contentValues;

    }

    public static List<Movie> getFavouriteMovies(FavouriteMovieCursorWrapper cursorWrapper) {

        List<Movie> favouriteMovies = new ArrayList<>();

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