package id.web.hn.andro.movieappiak.app.api;

import id.web.hn.andro.movieappiak.BuildConfig;
import id.web.hn.andro.movieappiak.app.model.ModelOMDBMovieDetails;
import id.web.hn.andro.movieappiak.app.model.ModelOMDBMovieSearch;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by hahn on 02/05/16.
 */
public interface BaseOMDBApi {

    String params = "movie?";
    @GET(params)
    Call<ModelOMDBMovieSearch> loadMovie(
            @Query("primary_release_year") int year,
            @Query("sort_by") String sort_by,
            @Query("api_key") String API_KEY);


}
