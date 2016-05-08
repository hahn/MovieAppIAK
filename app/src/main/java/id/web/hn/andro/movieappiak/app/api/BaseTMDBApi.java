package id.web.hn.andro.movieappiak.app.api;

import id.web.hn.andro.movieappiak.app.model.ModelOMDBMovieSearch;
import id.web.hn.andro.movieappiak.app.model.tmdb.ModelTMDBMovie;
import id.web.hn.andro.movieappiak.app.model.tmdb.MovieTMDB;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by hahn on 04/05/16.
 */
public interface BaseTMDBApi {
    String paramsDiscover = "discover/movie?";
    String paramSearch = "search/movie?";
    String paramDetail = "movie/";
    @GET(paramsDiscover)
    Call<ModelTMDBMovie> loadMovie(
            @Query("primary_release_year") int year,
            @Query("sort_by") String sort_by,
            @Query("page") int pg,
            @Query("api_key") String API_KEY);

    @GET(paramSearch)
    Call<ModelTMDBMovie> searchMovie(
            @Query("query") String title,
            @Query("page") int page,
            @Query("api_key") String API_KEY);

    @GET(paramDetail)
    Call<MovieTMDB> loadDetailMovie(
            @Query("") int id_movie,
            @Query("api_key") String API_KEY);

}
