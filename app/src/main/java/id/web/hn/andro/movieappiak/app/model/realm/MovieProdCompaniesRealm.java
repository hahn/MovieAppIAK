package id.web.hn.andro.movieappiak.app.model.realm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by hahn on 14/05/16.
 */
public class MovieProdCompaniesRealm extends RealmObject implements Serializable {

    @SerializedName("id")
    @Expose
    private int idCompany;

    @SerializedName("name")
    @Expose
    private String nameCompany;

    public int getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(int idCompany) {
        this.idCompany = idCompany;
    }

    public String getNameCompany() {
        return nameCompany;
    }

    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }
}
