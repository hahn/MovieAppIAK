package id.web.hn.andro.movieappiak.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hahn on 02/05/16.
 */
public class ModelOMDBMovieSearch implements Serializable{
    private List<MovieOMDBSearch> movieSearch = new ArrayList<>();
    @SerializedName("total_results")
    @Expose
    private int totalResults;

    @SerializedName("total_pages")
    @Expose
    private int totalPages;

    private String response;
    private int page;

    public List<MovieOMDBSearch> getMovieSearch() {
        return movieSearch;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getPage() {
        return page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public String getResponse() {
        return response;
    }
}
