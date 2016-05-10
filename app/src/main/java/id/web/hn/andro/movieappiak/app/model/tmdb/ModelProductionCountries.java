package id.web.hn.andro.movieappiak.app.model.tmdb;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by hahn on 09/05/16.
 */
public class ModelProductionCountries implements Serializable {

    @SerializedName("iso_3166_1")
    @Expose
    private String iso;

    @SerializedName("name")
    @Expose
    private String name;
}
