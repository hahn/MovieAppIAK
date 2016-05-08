package id.web.hn.andro.movieappiak.app.util.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import id.web.hn.andro.movieappiak.app.model.tmdb.MovieTMDB;
import id.web.hn.andro.movieappiak.app.util.sqlite.MovieContract.*;
/**
 * Created by hahn on 02/05/16.
 */
public class MovieDbSQLiteQuery {
    private SQLiteDatabase db;
    private MovieDbHelper dbHelper;

    public MovieDbSQLiteQuery(Context context){
        dbHelper = new MovieDbHelper(context);
    }

    public void openDb() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void closeDb() {
        dbHelper.close();
    }

    public void insertMovie(MovieTMDB data){

        openDb();
        ContentValues movieValues = new ContentValues();

        //array genre to string
        String sGenre;
        sGenre = Arrays.toString(data.getGenreIds());
        sGenre = sGenre.replace("[", "").replace("]", "").replace(" ", "");

        movieValues.put(MovieTmdbEntry.COLUMN_ID_FILM, data.getId());
        movieValues.put(MovieTmdbEntry.COLUMN_ADULT, "" + data.isAdult()); //disimpan dalam bentuk array
        movieValues.put(MovieTmdbEntry.COLUMN_BACKDROP_PATH, data.getBackdropPath());
        movieValues.put(MovieTmdbEntry.COLUMN_GENRE_ID, sGenre);// yang ini harus diolah dulu
        movieValues.put(MovieTmdbEntry.COLUMN_ORIGINAL_LANGUAGE, data.getOriginalLanguage());
        movieValues.put(MovieTmdbEntry.COLUMN_ORIGINAL_TITLE, data.getOriginalTitle());
        movieValues.put(MovieTmdbEntry.COLUMN_POPULARITY, data.getPopularity());
        movieValues.put(MovieTmdbEntry.COLUMN_POSTER_PATH, data.getPosterPath());
        movieValues.put(MovieTmdbEntry.COLUMN_RELEASE_DATE, data.getReleaseDate());
        movieValues.put(MovieTmdbEntry.COLUMN_TITLE, data.getTitle());
        movieValues.put(MovieTmdbEntry.COLUMN_OVERVIEW, data.getOverview());
        movieValues.put(MovieTmdbEntry.COLUMN_VIDEO, "" + data.isVideo()); //simpan dalam bentuk string
        movieValues.put(MovieTmdbEntry.COLUMN_VOTE_AVERAGE, data.getVoteAverage());
        movieValues.put(MovieTmdbEntry.COLUMN_VOTE_COUNT, data.getVoteCount());

        db.insert(MovieTmdbEntry.TABLE_NAME, null, movieValues);
        db.close();
    }

    public List<MovieTMDB> selectMovie(){
        List<MovieTMDB> movieTMDBList = new ArrayList<>();


        Cursor cursor = db.query(
                MovieTmdbEntry.TABLE_NAME,
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null  // sort order
                );

        if(cursor.moveToFirst()){
            //ubah dulu string ke int array (genreId)
            String[] s = cursor.getString(2).split(",");
            int[] genreIds = new int[s.length];
            for(int i = 0; i<s.length; i++){
                genreIds[i] = Integer.parseInt(s[i]);
            }

            do {
                MovieTMDB data = new MovieTMDB();

                //data kolom ke-0 adalah kolom _id, jadi mulai dari 1
                data.setId(cursor.getInt(1));
                data.setGenreIds(genreIds);
                data.setOriginalTitle(cursor.getString(3));
                data.setTitle(cursor.getString(4));
                data.setOverview(cursor.getString(5));
                data.setOriginalLanguage(cursor.getString(6));
                data.setReleaseDate(cursor.getString(7));
                data.setPopularity(cursor.getFloat(8));
                data.setVoteAverage(cursor.getFloat(9));
                data.setVoteCount(cursor.getInt(10));
                data.setBackdropPath(cursor.getString(11));
                data.setPosterPath(cursor.getString(12));
                data.setAdult(Boolean.parseBoolean(cursor.getString(13)));
                data.setVideo(Boolean.parseBoolean(cursor.getString(14)));

                movieTMDBList.add(data);

            } while (cursor.moveToNext());

        }

        return movieTMDBList;
    }

    public void clearTableMovie(){
        db.execSQL("DELETE FROM " + MovieTmdbEntry.TABLE_NAME);
        //count kembali ke 1
        db.execSQL("DELETE FROM sqlite_sequence where name= '" + MovieTmdbEntry.TABLE_NAME + "'");

    }
}
