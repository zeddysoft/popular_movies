package com.zeddysoft.popularmovies.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.zeddysoft.popularmovies.database.MovieContract.FavouriteMovieEntry;
import com.zeddysoft.popularmovies.models.Movie;

/**
 * Created by Azeez.Taiwo on 7/2/2017.
 */

public class FavouriteMovieCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public FavouriteMovieCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Movie getMovie(){
        Movie movie = new Movie();
        long id = getLong(getColumnIndex(FavouriteMovieEntry.COLUMN_MOVIE_ID));
        String posterPath = getString(getColumnIndex(FavouriteMovieEntry.COLUMN_POSTER_PATH));
        String originalTitle = getString(getColumnIndex(FavouriteMovieEntry.COLUMN_ORIGINAL_TITLE));
        String overview = getString(getColumnIndex(FavouriteMovieEntry.COLUMN_OVERVIEW));
        double voteAverage = getDouble(getColumnIndex(FavouriteMovieEntry.COLUMN_VOTE_AVERAGE));
        String releaseDate = getString(getColumnIndex(FavouriteMovieEntry.COLUMN_RELEASE_DATE));

        movie.setId(id);
        movie.setPosterPath(posterPath);
        movie.setOriginalTitle(originalTitle);
        movie.setOverview(overview);
        movie.setVoteAverage(voteAverage);
        movie.setReleaseDate(releaseDate);

        return movie;
    }
}
