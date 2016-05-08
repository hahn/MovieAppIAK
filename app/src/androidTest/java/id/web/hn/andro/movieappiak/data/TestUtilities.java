package id.web.hn.andro.movieappiak.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.test.AndroidTestCase;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import id.web.hn.andro.movieappiak.app.model.tmdb.MovieTMDB;
import id.web.hn.andro.movieappiak.app.util.sqlite.MovieContract;

/**
 * Created by hahn on 06/05/16.
 */
public class TestUtilities extends AndroidTestCase {

    static void validateCursor(String error, Cursor valueCursor, ContentValues expectedValues) {
        assertTrue("Empty cursor returned. " + error, valueCursor.moveToFirst());
        validateCurrentRecord(error, valueCursor, expectedValues);
        valueCursor.close();
    }

    static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals("Value '" + entry.getValue().toString() +
                    "' did not match the expected value '" +
                    expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
        }
    }

    public static MovieTMDB setMovieDummy(){
        MovieTMDB movie = new MovieTMDB();

        movie.setId(1231);
        movie.setAdult(true);
        movie.setGenreIds(new int[] {1,2,3});
        movie.setOriginalLanguage("en");
        movie.setOriginalTitle("Judul title asli");
        movie.setTitle("Titelnya");
        movie.setPopularity(59.43f);
        movie.setBackdropPath("gambarBackdrop.jpg");
        movie.setPosterPath("poster.jpg");
        movie.setOverview("overviewnya");
        movie.setVideo(false);
        movie.setVoteAverage(7.66f);
        movie.setVoteCount(6);
        movie.setReleaseDate("2014-20-03");

        return movie;
    }

    static ContentValues createMovieTmdbValues(){
        ContentValues movieValues  = new ContentValues();

        MovieTMDB data = new MovieTMDB();
        data = setMovieDummy();

        //array genre to string
        String sGenre;
        sGenre = Arrays.toString(data.getGenreIds());
        sGenre = sGenre.replace("[", "").replace("]", "").replace(" ", "");

        movieValues.put(MovieContract.MovieTmdbEntry.COLUMN_ID_FILM, data.getId());
        movieValues.put(MovieContract.MovieTmdbEntry.COLUMN_ADULT, "" + String.valueOf(data.isAdult()));
        movieValues.put(MovieContract.MovieTmdbEntry.COLUMN_BACKDROP_PATH, data.getBackdropPath());
        movieValues.put(MovieContract.MovieTmdbEntry.COLUMN_GENRE_ID, sGenre);// yang ini harus diolah dulu
        movieValues.put(MovieContract.MovieTmdbEntry.COLUMN_ORIGINAL_LANGUAGE, data.getOriginalLanguage());
        movieValues.put(MovieContract.MovieTmdbEntry.COLUMN_ORIGINAL_TITLE, data.getOriginalTitle());
        movieValues.put(MovieContract.MovieTmdbEntry.COLUMN_POPULARITY, data.getPopularity());
        movieValues.put(MovieContract.MovieTmdbEntry.COLUMN_POSTER_PATH, data.getPosterPath());
        movieValues.put(MovieContract.MovieTmdbEntry.COLUMN_RELEASE_DATE, data.getReleaseDate());
        movieValues.put(MovieContract.MovieTmdbEntry.COLUMN_TITLE, data.getTitle());
        movieValues.put(MovieContract.MovieTmdbEntry.COLUMN_VIDEO, "" + String.valueOf(data.isVideo()));
        movieValues.put(MovieContract.MovieTmdbEntry.COLUMN_VOTE_AVERAGE, data.getVoteAverage());
        movieValues.put(MovieContract.MovieTmdbEntry.COLUMN_VOTE_COUNT, data.getVoteCount());
        movieValues.put(MovieContract.MovieTmdbEntry.COLUMN_OVERVIEW, data.getOverview());

        return movieValues;
    }

    static ContentValues createGenreTmdbValues(){
        ContentValues genreValue = new ContentValues();

        genreValue.put(MovieContract.GenreTmdbEntry.COLUMN_ID_GENRE, 12);
        genreValue.put(MovieContract.GenreTmdbEntry.COLUMN_NAME, "Ini genrenya");

        return genreValue;
    }
}
