package id.web.hn.andro.movieappiak.app.util.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import id.web.hn.andro.movieappiak.app.util.sqlite.MovieContract.MovieTmdbEntry;
import id.web.hn.andro.movieappiak.app.util.sqlite.MovieContract.GenreTmdbEntry;

import id.web.hn.andro.movieappiak.app.util.sqlite.MovieContract.*;

/**
 * Created by hahn on 06/05/16.
 */
public class MovieDbHelper extends SQLiteOpenHelper {
    //kalau skema db berubah, naikkan versi db
    //1 versi awal
    //2 versi penambahan dari hasil query movie/{id}
    private static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "movieTmdb.db";



    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIETMDB_TABLE = "CREATE TABLE IF NOT EXISTS " + MovieTmdbEntry.TABLE_NAME + " (" +
                MovieTmdbEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieTmdbEntry.COLUMN_ID_FILM + " INTEGER NOT NULL, " +
                MovieTmdbEntry.COLUMN_GENRE_ID + " TEXT NOT NULL, " +
                MovieTmdbEntry.COLUMN_ORIGINAL_TITLE + " TEXT NOT NULL, " +
                MovieTmdbEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                MovieTmdbEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                MovieTmdbEntry.COLUMN_ORIGINAL_LANGUAGE + " TEXT NOT NULL, " +
                MovieTmdbEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                MovieTmdbEntry.COLUMN_POPULARITY + " REAL NOT NULL, " +
                MovieTmdbEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL, " +
                MovieTmdbEntry.COLUMN_VOTE_COUNT + " INTEGER NOT NULL, " +
                MovieTmdbEntry.COLUMN_BACKDROP_PATH + " TEXT NOT NULL, " +
                MovieTmdbEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                MovieTmdbEntry.COLUMN_ADULT + " TEXT NOT NULL, " +
                MovieTmdbEntry.COLUMN_VIDEO + " TEXT NOT NULL, " +
                //versi 2. ada penambahan kolom, untuk melengkapi isi tabel mpvies
//                MovieTmdbEntry.COLUMN_IMDB_ID + " TEXT NOT NULL, " +
////                MovieTmdbEntry.COLUMN_GENRES + " TEXT NOT NULL, " +
////                MovieTmdbEntry.COLUMN_BELONG_TO_COLLECTIONS + " TEXT NOT NULL, " +
//                MovieTmdbEntry.COLUMN_HOMEPAGE + " TEXT NOT NULL, " +
////                MovieTmdbEntry.COLUMN_PRODUCTION_COMPANIES + " TEXT NOT NULL, " +
////                MovieTmdbEntry.COLUMN_PRODUCTION_COUNTRIES + " TEXT NOT NULL, " +
//                MovieTmdbEntry.COLUMN_REVENUE + " REAL NOT NULL, " +
//                MovieTmdbEntry.COLUMN_RUNTIME + " INTEGER NOT NULL, " +
////                MovieTmdbEntry.COLUMN_SPOKEN_LANGUAGES + " TEXT NOT NULL, " +
//                MovieTmdbEntry.COLUMN_STATUS + " TEXT NOT NULL, " +
//                MovieTmdbEntry.COLUMN_TAGLINE + " TEXT NOT NULL, " +
//                MovieTmdbEntry.COLUMN_BUDGET + " REAL NOT NULL, " +


                " UNIQUE (" + MovieTmdbEntry.COLUMN_ID_FILM + " ) ON CONFLICT REPLACE);";

        final String SQL_CREATE_GENRETMDB_TABLE = "CREATE TABLE IF NOT EXISTS " + GenreTmdbEntry.TABLE_NAME + " ( " +
                GenreTmdbEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                GenreTmdbEntry.COLUMN_ID_GENRE + " INTEGER NOT NULL, " +
                GenreTmdbEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                " UNIQUE (" + GenreTmdbEntry.COLUMN_ID_GENRE + ") ON CONFLICT REPLACE);";

        //versi 2
        final String SQL_CREATE_MOVIEDETAILTMDB_TABLE = "CREATE TABLE IF NOT EXISTS " + MovieTmdbEntryBaru.TABLE_NAME + " (" +
                MovieTmdbEntryBaru._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieTmdbEntryBaru.COLUMN_ID_FILM + " INTEGER NOT NULL, " +
                MovieTmdbEntryBaru.COLUMN_GENRE_ID + " TEXT NOT NULL, " +
                MovieTmdbEntryBaru.COLUMN_ORIGINAL_TITLE + " TEXT NOT NULL, " +
                MovieTmdbEntryBaru.COLUMN_TITLE + " TEXT NOT NULL, " +
                MovieTmdbEntryBaru.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                MovieTmdbEntryBaru.COLUMN_ORIGINAL_LANGUAGE + " TEXT NOT NULL, " +
                MovieTmdbEntryBaru.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                MovieTmdbEntryBaru.COLUMN_POPULARITY + " REAL NOT NULL, " +
                MovieTmdbEntryBaru.COLUMN_VOTE_AVERAGE + " REAL NOT NULL, " +
                MovieTmdbEntryBaru.COLUMN_VOTE_COUNT + " INTEGER NOT NULL, " +
                MovieTmdbEntryBaru.COLUMN_BACKDROP_PATH + " TEXT NOT NULL, " +
                MovieTmdbEntryBaru.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                MovieTmdbEntryBaru.COLUMN_ADULT + " TEXT NOT NULL, " +
                MovieTmdbEntryBaru.COLUMN_VIDEO + " TEXT NOT NULL, " +
                //versi 2. ada penambahan kolom, untuk melengkapi isi tabel mpvies
                MovieTmdbEntryBaru.COLUMN_IMDB_ID + " TEXT NOT NULL, " +
//                MovieTmdbEntryBaru.COLUMN_GENRES + " TEXT NOT NULL, " +
//                MovieTmdbEntryBaru.COLUMN_BELONG_TO_COLLECTIONS + " TEXT NOT NULL, " +
                MovieTmdbEntryBaru.COLUMN_HOMEPAGE + " TEXT NOT NULL, " +
//                MovieTmdbEntryBaru.COLUMN_PRODUCTION_COMPANIES + " TEXT NOT NULL, " +
//                MovieTmdbEntryBaru.COLUMN_PRODUCTION_COUNTRIES + " TEXT NOT NULL, " +
                MovieTmdbEntryBaru.COLUMN_REVENUE + " REAL NOT NULL, " +
                MovieTmdbEntryBaru.COLUMN_RUNTIME + " INTEGER NOT NULL, " +
//                MovieTmdbEntryBaru.COLUMN_SPOKEN_LANGUAGES + " TEXT NOT NULL, " +
                MovieTmdbEntryBaru.COLUMN_STATUS + " TEXT NOT NULL, " +
                MovieTmdbEntryBaru.COLUMN_TAGLINE + " TEXT NOT NULL, " +
                MovieTmdbEntryBaru.COLUMN_BUDGET + " REAL NOT NULL, " +


                " UNIQUE (" + MovieTmdbEntryBaru.COLUMN_ID_FILM + " ) ON CONFLICT REPLACE);";

        final String SQL_CREATE_GENRE_RELATION = "CREATE TABLE IF NOT EXISTS " + GenreMovieRelationEntry.TABLE_NAME + " (" +
                GenreMovieRelationEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                GenreMovieRelationEntry.COLUMN_ID_GENRE + " INTEGER NOT NULL, " +
                GenreMovieRelationEntry.COLUMN_ID_MOVIE + " TEXT NOT NULL, " +
                " FOREIGN KEY (" + GenreMovieRelationEntry.COLUMN_ID_GENRE + ") REFERENCES " +
                GenreTmdbEntry.TABLE_NAME + "( " + GenreTmdbEntry.COLUMN_ID_GENRE + " ) " +
                " FOREIGN KEY (" + GenreMovieRelationEntry.COLUMN_ID_MOVIE + ") REFERENCES " +
                MovieTmdbEntryBaru.TABLE_NAME + "( " + MovieTmdbEntryBaru.COLUMN_ID_FILM + " ) " +
                " );";

        //EKSEKUSI
        db.execSQL(SQL_CREATE_MOVIETMDB_TABLE);
        db.execSQL(SQL_CREATE_GENRETMDB_TABLE);

        //versi 2
        db.execSQL(SQL_CREATE_MOVIEDETAILTMDB_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieTmdbEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + GenreTmdbEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MovieTmdbEntryBaru.TABLE_NAME);
        onCreate(db);
    }
}
