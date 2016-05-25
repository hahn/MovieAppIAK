package id.web.hn.andro.movieappiak.app.model.tmdb;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by hahn on 09/05/16.
 */
public class ModelProductionCompanies extends RealmObject implements Serializable {

    @SerializedName("id")
    @Expose
    private int idCompany;

    @SerializedName("name")
    @Expose
    private String nameCompany;
}
