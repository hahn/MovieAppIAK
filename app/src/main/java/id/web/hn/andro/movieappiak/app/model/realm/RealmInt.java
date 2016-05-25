package id.web.hn.andro.movieappiak.app.model.realm;

/**
 * Created by hahn on 21/05/16.
 */

import io.realm.RealmObject;

/**
 * karena realm ga bisa handle int[], harus bikin objek baru buat ngolah int[]

 */
public class RealmInt extends RealmObject {
    private int val;
    public RealmInt(){

    }
    public RealmInt(int val){
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }
}
