package com.zeddysoft.popularmovies.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zeddysoft.popularmovies.database.MovieContract.FavouriteMovieEntry;

/**
 * Created by Azeez.Taiwo on 7/2/2017.
 */

public class MovieDbHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "popular_movie.db";
    private static final int DATABASE_VERSION = 1;

    public static String SQL_CREATE_FAVOURITE_MOVIE_TABLE = "CREATE TABLE " + FavouriteMovieEntry.TABLE_NAME + " (" +
            FavouriteMovieEntry._ID + " INTEGER PRIMARY KEY," +
            FavouriteMovieEntry.COLUMN_MOVIE_ID + " INTEGER UNIQUE," +
            FavouriteMovieEntry.COLUMN_POSTER_PATH + " TEXT," +
            FavouriteMovieEntry.COLUMN_ORIGINAL_TITLE + " TEXT," +
            FavouriteMovieEntry.COLUMN_OVERVIEW + " TEXT," +
            FavouriteMovieEntry.COLUMN_VOTE_AVERAGE + " REAL," +
            FavouriteMovieEntry.COLUMN_RELEASE_DATE + " TEXT)";

    public MovieDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_FAVOURITE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavouriteMovieEntry.TABLE_NAME);
        onCreate(db);
    }
}
