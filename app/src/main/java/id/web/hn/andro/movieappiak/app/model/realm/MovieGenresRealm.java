package id.web.hn.andro.movieappiak.app.model.realm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by hahn on 14/05/16.
 */
public class MovieGenresRealm extends RealmObject implements Serializable {
    @SerializedName("id")
    @Expose
    private int idGenre;

    @SerializedName("name")
    @Expose
    private String nameGenre;

    public int getIdGenre() {
        return idGenre;
    }

    public void setIdGenre(int idGenre) {
        this.idGenre = idGenre;
    }

    public String getNameGenre() {
        return nameGenre;
    }

    public void setNameGenre(String nameGenre) {
        this.nameGenre = nameGenre;
    }
}
