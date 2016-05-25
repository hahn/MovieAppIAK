package id.web.hn.andro.movieappiak.app.model.realm;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import io.realm.RealmList;

/**
 * Created by hahn on 24/05/16.
 * bisi poho
 * http://www.myandroidsolutions.com/2015/05/29/gson-realm-string-array/
 */
public class RealmIntDeserializer implements JsonDeserializer<RealmList<RealmInt>> {
    @Override
    public RealmList<RealmInt> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        RealmList<RealmInt> realmInts = new RealmList<>();
        JsonArray intList = json.getAsJsonArray();

        for(JsonElement intElement : intList){
            realmInts.add(new RealmInt(intElement.getAsInt()));
        }

        return realmInts;
    }
}
