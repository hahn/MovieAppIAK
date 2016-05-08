package id.web.hn.andro.movieappiak.app.util.sqlite;

import android.provider.BaseColumns;
import android.support.design.widget.Snackbar;

/**
 * Created by hahn on 06/05/16.
 * Mendefinisikan tabel dan kolom untuk movie. tabel movie dan genre (TmdbApi)
 */
public class MovieContract {


    public static final class MovieTmdbEntry implements BaseColumns {
        public static final String TABLE_NAME = "movieiak";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_ADULT = "adult";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_GENRE_ID = "genre_id";
        public static final String COLUMN_ID_FILM  = "id";
        public static final String COLUMN_ORIGINAL_TITLE = "original_title";
        public static final String COLUMN_ORIGINAL_LANGUAGE = "original_language";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_BACKDROP_PATH = "backdrop_path";
        public static final String COLUMN_POPULARITY = "popularity";
        public static final String COLUMN_VOTE_COUNT = "vote_count";
        public static final String COLUMN_VIDEO = "video";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";

        //versi 2. penambahan dari query movie/id
//        public static final String COLUMN_IMDB_ID = "imdb_id";
//        public static final String COLUMN_GENRES = "genres";
//        public static final String COLUMN_BELONG_TO_COLLECTIONS = "belong_to_collections";
//        public static final String COLUMN_HOMEPAGE = "homepage";
//        public static final String COLUMN_PRODUCTION_COMPANIES = "production_companies";
//        public static final String COLUMN_PRODUCTION_COUNTRIES = "production_countries";
//        public static final String COLUMN_REVENUE = "revenue";
//        public static final String COLUMN_RUNTIME = "runtime";
//        public static final String COLUMN_SPOKEN_LANGUAGES = "spoken_languages";
//        public static final String COLUMN_STATUS = "status";
//        public static final String COLUMN_TAGLINE = "tagline";
//        public static final String COLUMN_BUDGET = "budget";
    }

    public static final class GenreTmdbEntry implements BaseColumns {
        public static final String TABLE_NAME = "genremovieiak";
        public static final String COLUMN_ID_GENRE = "id";
        public static final String COLUMN_NAME = "name";
    }

    //versi 2. menambah tabel lagi
    //Bikin tabel baru untuk movie. supaya ga ganggu tabel awal.

    public static final class MovieTmdbEntryBaru implements BaseColumns {
        public static final String TABLE_NAME = "movie_detail_iak";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_ADULT = "adult";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_GENRE_ID = "genre_id";
        public static final String COLUMN_ID_FILM  = "id";
        public static final String COLUMN_ORIGINAL_TITLE = "original_title";
        public static final String COLUMN_ORIGINAL_LANGUAGE = "original_language";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_BACKDROP_PATH = "backdrop_path";
        public static final String COLUMN_POPULARITY = "popularity";
        public static final String COLUMN_VOTE_COUNT = "vote_count";
        public static final String COLUMN_VIDEO = "video";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";

        //versi 2. penambahan dari query movie/id
        public static final String COLUMN_IMDB_ID = "imdb_id";
        public static final String COLUMN_GENRES = "genres";
        public static final String COLUMN_BELONG_TO_COLLECTIONS = "belong_to_collections";
        public static final String COLUMN_HOMEPAGE = "homepage";
        public static final String COLUMN_PRODUCTION_COMPANIES = "production_companies";
        public static final String COLUMN_PRODUCTION_COUNTRIES = "production_countries";
        public static final String COLUMN_REVENUE = "revenue";
        public static final String COLUMN_RUNTIME = "runtime";
        public static final String COLUMN_SPOKEN_LANGUAGES = "spoken_languages";
        public static final String COLUMN_STATUS = "status";
        public static final String COLUMN_TAGLINE = "tagline";
        public static final String COLUMN_BUDGET = "budget";
    }

    public static final class GenreMovieRelationEntry implements BaseColumns{
        public static final String TABLE_NAME = "genremovieiak_relation";
        public static final String COLUMN_ID_GENRE = "id_genre";
        public static final String COLUMN_ID_MOVIE = "id_movie";

    }

    public static final class MovieOmdbEntry implements BaseColumns {
        //nanti sajalah
    }
}
