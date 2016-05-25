package id.web.hn.andro.movieappiak.app.model.realm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by hahn on 14/05/16.
 */
public class ModelMovieRealm extends RealmObject implements Serializable{
    private int page;

    @SerializedName("total_results")
    @Expose
    private int totalResults;

    @SerializedName("total_pages")
    @Expose
    private int totalPages;

    @SerializedName("results")
    @Expose
    private RealmList<MovieRealm> results = new RealmList<>();

    public int getPage() {
        return page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public RealmList<MovieRealm> getResults() {
        return results;
    }
}
