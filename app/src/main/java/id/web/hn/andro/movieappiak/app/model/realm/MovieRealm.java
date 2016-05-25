package id.web.hn.andro.movieappiak.app.model.realm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by hahn on 14/05/16.
 */
public class MovieRealm extends RealmObject implements Serializable {
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

    //realm tak support int[]
    @SerializedName("genre_ids")
    @Expose
    private RealmList<RealmInt> genreIds;

    //tambahan dari movie detail (id)
    @SerializedName("imdb_id")
    @Expose
    private String imdbId;

    @SerializedName("genres")
    @Expose
    private RealmList<MovieGenresRealm> genres;/// = new RealmList<>();

    @SerializedName("belong_to_collections")
    @Expose
    private RealmList<MovieCollectionsRealm> tmdbCollections;// = new RealmList<>();

    @SerializedName("homepage")
    @Expose
    private String homepage;

    @SerializedName("production_companies")
    @Expose
    private RealmList<MovieProdCompaniesRealm> productionCompanies;// = new RealmList<>();

    @SerializedName("production_countries")
    @Expose
    private RealmList<MovieProdCountriesRealm> productionCountries;// = new RealmList<>();;

    @SerializedName("revenue")
    @Expose
    private float revenue;

    @SerializedName("runtime")
    @Expose
    private int runtime;

    @SerializedName("spoken_languages")
    @Expose
    private RealmList<MovieSpokenLangRealm> spokenLanguages = new RealmList<>();

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("tagline")
    @Expose
    private String tagline;

    @SerializedName("budget")
    @Expose
    private float budget;

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public RealmList<RealmInt> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(RealmList<RealmInt> genreIds) {
        this.genreIds = genreIds;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public RealmList<MovieGenresRealm> getGenres() {
        return genres;
    }

    public void setGenres(RealmList<MovieGenresRealm> genres) {
        this.genres = genres;
    }

    public RealmList<MovieCollectionsRealm> getTmdbCollections() {
        return tmdbCollections;
    }

    public void setTmdbCollections(RealmList<MovieCollectionsRealm> tmdbCollections) {
        this.tmdbCollections = tmdbCollections;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public RealmList<MovieProdCompaniesRealm> getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(RealmList<MovieProdCompaniesRealm> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public RealmList<MovieProdCountriesRealm> getProductionCountries() {
        return productionCountries;
    }

    public void setProductionCountries(RealmList<MovieProdCountriesRealm> productionCountries) {
        this.productionCountries = productionCountries;
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

    public RealmList<MovieSpokenLangRealm> getSpokenLanguages() {
        return spokenLanguages;
    }

    public void setSpokenLanguages(RealmList<MovieSpokenLangRealm> spokenLanguages) {
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
}
