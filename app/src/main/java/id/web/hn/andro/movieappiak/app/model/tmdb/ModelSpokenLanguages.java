package id.web.hn.andro.movieappiak.app.model.tmdb;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by hahn on 09/05/16.
 */
public class ModelSpokenLanguages extends RealmObject implements Serializable {
    //cek lagi bray
    @SerializedName("iso_639_1")
    @Expose
    private String idLang;

    @SerializedName("name")
    @Expose
    private String language;
}
