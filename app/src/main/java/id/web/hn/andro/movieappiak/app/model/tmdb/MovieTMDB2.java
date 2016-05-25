package id.web.hn.andro.movieappiak.app.model.tmdb;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by hahn on 04/05/16.
 */
public class MovieTMDB2 implements Serializable  {
    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    @SerializedName("overview")
    @Expose
    private String overview;

    private boolean adult;

    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    @PrimaryKey
    private int id;

    @SerializedName("original_title")
    @Expose
    private String originalTitle;

    @SerializedName("original_language")
    @Expose
    private String originalLanguage;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;

    @SerializedName("popularity")
    @Expose
    private float popularity;

    @SerializedName("vote_count")
    @Expose
    private int voteCount;

    @SerializedName("video")
    @Expose
    private boolean video;

    @SerializedName("vote_average")
    @Expose
    private float voteAverage;

//    @SerializedName("genre_ids")
//    @Expose
//    private int[] genreIds;

    //tambahan dari movie detail (id)
    @SerializedName("imdb_id")
    @Expose
    private String imdbId;

    @SerializedName("genres")
    @Expose
    private List<ModelTMDBGenre> genres = new ArrayList<>();

    @SerializedName("belong_to_collections")
    @Expose
    private List<ModelTMDBCollections> tmdbCollections = new ArrayList<>();;

    @SerializedName("homepage")
    @Expose
    private String hompage;

    @SerializedName("production_companies")
    @Expose
    private List<ModelProductionCompanies> productionCompanies = new ArrayList<>();;

    @SerializedName("production_countries")
    @Expose
    private List<ModelProductionCountries> productionCountries = new ArrayList<>();;

    @SerializedName("revenue")
    @Expose
    private float revenue;

    @SerializedName("runtime")
    @Expose
    private int runtime;

    @SerializedName("spoken_languages")
    @Expose
    private List<ModelSpokenLanguages> spokenLanguages = new ArrayList<>();

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("tagline")
    @Expose
    private String tagline;

    @SerializedName("budget")
    @Expose
    private float budget;

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public List<ModelTMDBGenre> getGenres() {
        return genres;
    }

    public void setGenres(List<ModelTMDBGenre> genres) {
        this.genres = genres;
    }

    public List<ModelTMDBCollections> getTmdbCollections() {
        return tmdbCollections;
    }

    public void setTmdbCollections(List<ModelTMDBCollections> tmdbCollections) {
        this.tmdbCollections = tmdbCollections;
    }

    public List<ModelProductionCompanies> getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(List<ModelProductionCompanies> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public List<ModelProductionCountries> getProductionCountries() {
        return productionCountries;
    }

    public void setProductionCountries(List<ModelProductionCountries> productionCountries) {
        this.productionCountries = productionCountries;
    }

    public String getHompage() {
        return hompage;
    }

    public void setHompage(String hompage) {
        this.hompage = hompage;
    }

    public float getRevenue() {
        return revenue;
    }

    public void setRevenue(float revenue) {
        this.revenue = revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public List<ModelSpokenLanguages> getSpokenLanguages() {
        return spokenLanguages;
    }

    public void setSpokenLanguages(List<ModelSpokenLanguages> spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getId() {
        return id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public float getPopularity() {
        return popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public boolean isVideo() {
        return video;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

//    public int[] getGenreIds() {
//        return genreIds;
//    }


    //setter
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

//    public void setGenreIds(int[] genreIds) {
//        this.genreIds = genreIds;
//    }
}
