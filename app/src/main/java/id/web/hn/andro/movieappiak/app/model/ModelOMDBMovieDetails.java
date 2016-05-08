package id.web.hn.andro.movieappiak.app.model;

import java.io.Serializable;

/**
 * Created by hahn on 02/05/16.
 */
public class ModelOMDBMovieDetails implements Serializable{

    private String Title;
    private String Year;
    private String Released;
    private String Runtime;
    private String Genre;
    private String Director;
    private String Writer;
    private String Actors;
    private String Plot;
    private String Language;
    private String Country;
    private String Awards;
    private String Poster;
    private String Metascore;
    private float imdbRating;
    private float imdbVotes;
    private String imdbID;
    private String Type;
    private String Response;

    public String getTitle() {
        return Title;
    }

    public String getYear() {
        return Year;
    }

    public String getReleased() {
        return Released;
    }

    public String getRuntime() {
        return Runtime;
    }

    public String getGenre() {
        return Genre;
    }

    public String getDirector() {
        return Director;
    }

    public String getWriter() {
        return Writer;
    }

    public String getActors() {
        return Actors;
    }

    public String getPlot() {
        return Plot;
    }

    public String getLanguage() {
        return Language;
    }

    public String getCountry() {
        return Country;
    }

    public String getAwards() {
        return Awards;
    }

    public String getPoster() {
        return Poster;
    }

    public String getMetascore() {
        return Metascore;
    }

    public float getImdbRating() {
        return imdbRating;
    }

    public float getImdbVotes() {
        return imdbVotes;
    }

    public String getImdbID() {
        return imdbID;
    }

    public String getType() {
        return Type;
    }

    public String getResponse() {
        return Response;
    }
}
