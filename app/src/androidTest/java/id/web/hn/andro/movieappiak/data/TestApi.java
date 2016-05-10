package id.web.hn.andro.movieappiak.data;

import android.test.AndroidTestCase;
import android.util.Log;

import id.web.hn.andro.movieappiak.BuildConfig;
import id.web.hn.andro.movieappiak.app.api.BaseTMDBApi;
import id.web.hn.andro.movieappiak.app.model.tmdb.ModelTMDBMovie;
import id.web.hn.andro.movieappiak.app.model.tmdb.MovieTMDB;
import id.web.hn.andro.movieappiak.app.model.tmdb.MovieTMDB2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hahn on 09/05/16.
 */
public class TestApi extends AndroidTestCase {
    final String BASE_URL = "http://api.themoviedb.org/3/";
    String API_KEY = BuildConfig.TMDB_API_KEY;
    String idfilm = "271110";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

//    BaseTMDBApi baseTMDBApi = retrofit.create(BaseTMDBApi.class);
//    Call<MovieTMDB2> call = baseTMDBApi.loadDetailMovie(
//            idfilm,
//            API_KEY
//    );

//    Log.d("onBackground", "Page: " +page);
}
