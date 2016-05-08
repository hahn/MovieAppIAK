package id.web.hn.andro.movieappiak.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import id.web.hn.andro.movieappiak.app.model.tmdb.MovieTMDB;
import id.web.hn.andro.movieappiak.app.util.sqlite.MovieContract;
import id.web.hn.andro.movieappiak.app.util.sqlite.MovieDbHelper;

/**
 * Created by hahn on 06/05/16.
 */
public class TestDb extends AndroidTestCase {

    void deleteTheDatabase(){
        mContext.deleteDatabase(MovieDbHelper.DATABASE_NAME);
    }
     public void setUp(){
         deleteTheDatabase();
     }

    public void testCreateDb() throws Throwable {
        final HashSet<String> tableNameHashSet = new HashSet<>();
        tableNameHashSet.add(MovieContract.MovieTmdbEntry.TABLE_NAME);
        tableNameHashSet.add(MovieContract.GenreTmdbEntry.TABLE_NAME);

        mContext.deleteDatabase(MovieDbHelper.DATABASE_NAME);
        SQLiteDatabase db = new MovieDbHelper(
                this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());

        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        assertTrue("Error: This means that the database has not been created correctly",
                c.moveToFirst());
        do{
            tableNameHashSet.remove(c.getString(0));
        } while (c.moveToNext());

        assertTrue("Error: Your database was created without both the location entry and weather entry tables",
                tableNameHashSet.isEmpty());

        c = db.rawQuery("PRAGMA table_info(" + MovieContract.MovieTmdbEntry.TABLE_NAME + ")",
                null);

        assertTrue("Error: This means that we were unable to query the database for table information.",
                c.moveToFirst());

        // Build a HashSet of all of the column names we want to look for
        final HashSet<String> movieColumnHashSet = new HashSet<String>();
        movieColumnHashSet.add(MovieContract.MovieTmdbEntry._ID);
        movieColumnHashSet.add(MovieContract.MovieTmdbEntry.COLUMN_ID_FILM);
        movieColumnHashSet.add(MovieContract.MovieTmdbEntry.COLUMN_ADULT);
        movieColumnHashSet.add(MovieContract.MovieTmdbEntry.COLUMN_BACKDROP_PATH);
        movieColumnHashSet.add(MovieContract.MovieTmdbEntry.COLUMN_GENRE_ID);
        movieColumnHashSet.add(MovieContract.MovieTmdbEntry.COLUMN_ORIGINAL_LANGUAGE);
        movieColumnHashSet.add(MovieContract.MovieTmdbEntry.COLUMN_ORIGINAL_TITLE);
        movieColumnHashSet.add(MovieContract.MovieTmdbEntry.COLUMN_POPULARITY);
        movieColumnHashSet.add(MovieContract.MovieTmdbEntry.COLUMN_POSTER_PATH);
        movieColumnHashSet.add(MovieContract.MovieTmdbEntry.COLUMN_RELEASE_DATE);
        movieColumnHashSet.add(MovieContract.MovieTmdbEntry.COLUMN_TITLE);
        movieColumnHashSet.add(MovieContract.MovieTmdbEntry.COLUMN_VIDEO);
        movieColumnHashSet.add(MovieContract.MovieTmdbEntry.COLUMN_VOTE_AVERAGE);
        movieColumnHashSet.add(MovieContract.MovieTmdbEntry.COLUMN_VOTE_COUNT);

        int columnNameIndex = c.getColumnIndex("name");
        do {
            String columnName = c.getString(columnNameIndex);
            movieColumnHashSet.remove(columnName);
        } while(c.moveToNext());

        // if this fails, it means that your database doesn't contain all of the required location
        // entry columns
        assertTrue("Error: The database doesn't contain all of the required location entry columns",
                movieColumnHashSet.isEmpty());
        db.close();

    }

    public void testMovieTable(){
        MovieDbHelper dbHelper = new MovieDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues mvValues = TestUtilities.createMovieTmdbValues();
        long movieId = db.insert(MovieContract.MovieTmdbEntry.TABLE_NAME, null, mvValues);
        assertTrue(movieId != -1);

        Cursor cursor = db.query(
                MovieContract.MovieTmdbEntry.TABLE_NAME,
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null  // sort order
        );
        List<MovieTMDB> movieTMDBList = new ArrayList<>();

        if(cursor.moveToFirst()){
            //ubah dulu string ke int array (genreId)

            String[] s = cursor.getString(2).split(",");

            int[] genreIds = new int[s.length];
            for(int i = 0; i < s.length; i++){
                Log.d("cursor", "genreIds[" + i + "]: " + s[i]);
                genreIds[i] = Integer.parseInt(s[i]);
            }

            do {
                MovieTMDB data = new MovieTMDB();

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

//        assertTrue( "Error: No Records returned from location query", movCursor.moveToFirst() );
//
//        TestUtilities.validateCurrentRecord("tes validasi gagal", movCursor, mvValues);
//        assertFalse("Error: More than one record returned from movie query",
//                movCursor.moveToNext());

        cursor.close();
        dbHelper.close();


    }

}
