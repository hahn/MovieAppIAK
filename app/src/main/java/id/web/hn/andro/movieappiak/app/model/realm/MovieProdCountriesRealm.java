package id.web.hn.andro.movieappiak.app.model.realm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by hahn on 14/05/16.
 */
public class MovieProdCountriesRealm extends RealmObject implements Serializable {

    @SerializedName("iso_3166_1")
    @Expose
    private String iso;

    @SerializedName("name")
    @Expose
    private String name;

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
