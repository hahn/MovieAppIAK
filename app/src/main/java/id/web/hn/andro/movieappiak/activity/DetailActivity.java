package id.web.hn.andro.movieappiak.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import id.web.hn.andro.movieappiak.BuildConfig;
import id.web.hn.andro.movieappiak.R;
import id.web.hn.andro.movieappiak.app.api.BaseTMDBApi;
import id.web.hn.andro.movieappiak.app.model.tmdb.MovieTMDB;
import id.web.hn.andro.movieappiak.app.model.tmdb.MovieTMDB2;
import id.web.hn.andro.movieappiak.app.util.ConnectionDetector;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hahn on 05/05/16.
 */
public class DetailActivity extends AppCompatActivity {
    String urlimage = "https://image.tmdb.org/t/p/w185/";
    ImageView imgDetail, imgCollapse;
    TextView txtTitle, txtOverview, txtReleaseDate, txtPopularity, txtVoteCount;
    TextView txtGenres, txtTagline, txtStatus, txtHomepage;
    TextView txtGenresT, txtTaglineT, txtStatusT, txtHomepageT;
    RatingBar ratingBar;
    Toolbar toolbar;
    ProgressDialog dialog; // this = YourActivity

    private Bundle extras;
    private MovieTMDB data;

    //realm
    Realm myrealm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

//        pakai fragment ga ngerti jadi pake biasa aja deh
//        if(savedInstanceState == null){
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.container_fragment_detail, new MovieDetailFragment())
//                    .commit();
//        }

        //collapse
        imgCollapse = (ImageView) findViewById(R.id.backdroppath);
        CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        //detail
        imgDetail = (ImageView) findViewById(R.id.img_detail);
        txtTitle = (TextView) findViewById(R.id.txt_detail_title);
        txtOverview = (TextView) findViewById(R.id.txt_detail_overview);
        txtReleaseDate = (TextView) findViewById(R.id.txt_detail_release_date);
        txtPopularity = (TextView) findViewById(R.id.txt_detail_popularity);
        txtVoteCount = (TextView) findViewById(R.id.txt_detail_vote_count);
        ratingBar = (RatingBar) findViewById(R.id.rating_detail);

        //jika konek, ini ditampilkan
        txtGenres = (TextView) findViewById(R.id.txt_detail_genres);
        txtGenresT = (TextView) findViewById(R.id.text_genres);
        txtTagline = (TextView) findViewById(R.id.txt_detail_tagline);
        txtTaglineT = (TextView) findViewById(R.id.text_tagline);
        txtStatus = (TextView) findViewById(R.id.txt_detail_status);
        txtStatusT = (TextView) findViewById(R.id.text_status);
        txtHomepage = (TextView) findViewById(R.id.txt_detail_homepage);
        txtHomepageT = (TextView) findViewById(R.id.text_homepage);

        //konfigurasi realm
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).build();
        myrealm = Realm.getInstance(realmConfig);

        dialog = new ProgressDialog(this);

        //getIntent
        extras = getIntent().getExtras();
        data = (MovieTMDB) extras.getSerializable("data");

        //set data
        Picasso.with(this)
                .load(urlimage + data.getBackdropPath())
                .error(R.mipmap.img_background)
                .into(imgCollapse);

        Picasso.with(this)
                .load(urlimage + data.getPosterPath())
                .error(R.mipmap.img_background)
                .into(imgDetail);

        String overview = data.getOverview();
        txtTitle.setText(data.getTitle());
        txtReleaseDate.setText("Release: " + data.getReleaseDate());
        txtPopularity.setText("Popularity: " + data.getPopularity());
        txtVoteCount.setText((data.getVoteAverage()/2) + "/5 (" + data.getVoteCount() + " votes)");

        txtOverview.setText(overview);

        ratingBar.setRating(data.getVoteAverage() / 2);
        ratingBar.setStepSize(0.1f);

        //toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //collapsing toolbar
        collapsingToolbarLayout.setTitle(txtTitle.getText());
        collapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);

        ActionBar mActionBar = getSupportActionBar();

        mActionBar.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mActionBar.setDisplayHomeAsUpEnabled(true);

        int idfilm = data.getId();
        //tambahan data dari movie/{id}
        ConnectionDetector connectionDetector;
        boolean isInternetConnected = false;
        //cek koneksi
        connectionDetector = new ConnectionDetector(this);
        isInternetConnected = connectionDetector.isInternetConnected();
        if(isInternetConnected){
            tesLoadDetail(idfilm);

            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage("Loading. Please wait...");
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();



            //
           Log.d("internet", "internet konek");

        }else{
            View v = findViewById(R.id.view);
            Snackbar.make(v, "Can't connect to internet. Using local data", Snackbar.LENGTH_LONG).show();

        }

    }

    public void tesLoadDetail(int id){
        final MovieTMDB2[] data = new MovieTMDB2[1];
        String BASE_URL = "http://api.themoviedb.org/3/";
        String API_KEY = BuildConfig.TMDB_API_KEY;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BaseTMDBApi baseTMDBApi = retrofit.create(BaseTMDBApi.class);
        Call<MovieTMDB2> call = baseTMDBApi.loadDetailMovie(
                id,
                API_KEY
        );

        call.enqueue(new Callback<MovieTMDB2>() {
            @Override
            public void onResponse(Call<MovieTMDB2> call, Response<MovieTMDB2> response) {
                Log.d("onResponse", "response Asyncs Detail " + response.message());
                data[0] = response.body();
                insertDetail(data[0]);
            }

            private void insertDetail(MovieTMDB2 movieTMDB2) {
                int i = movieTMDB2.getGenres().size();
                String genres = "";
                for(int j = 0; j < i; j++){
                    if(j == i - 1 ){
                        genres += movieTMDB2.getGenres().get(j).getNameGenre() + ".";
                    }else {
                        genres += movieTMDB2.getGenres().get(j).getNameGenre() + ", ";
                    }
                }

//                //realm. update database
//                myrealm.beginTransaction();
//                myrealm.copyToRealmOrUpdate(movieTMDB2);
//                myrealm.commitTransaction();

                txtGenresT.setText("Genre");
                txtGenres.setText(genres);
                txtTaglineT.setText("Tagline");
                txtTagline.setText(movieTMDB2.getTagline());
                txtStatusT.setText("Status");
                txtStatus.setText(movieTMDB2.getStatus());
                txtHomepageT.setText("Homepage");
                txtHomepage.setText(movieTMDB2.getHompage());
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<MovieTMDB2> call, Throwable t) {
                Log.d("onFailure", " " );
                t.printStackTrace();
                dialog.dismiss();
            }
        });

    }
}
