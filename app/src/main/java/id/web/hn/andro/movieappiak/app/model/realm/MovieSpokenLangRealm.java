package id.web.hn.andro.movieappiak.app.model.realm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by hahn on 14/05/16.
 */
public class MovieSpokenLangRealm extends RealmObject implements Serializable {

    @SerializedName("iso_639_1")
    @Expose
    private String idLang;

    @SerializedName("name")
    @Expose
    private String name;

    public String getIdLang() {
        return idLang;
    }

    public void setIdLang(String idLang) {
        this.idLang = idLang;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
