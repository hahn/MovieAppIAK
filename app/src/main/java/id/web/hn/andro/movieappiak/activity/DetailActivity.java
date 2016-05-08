package id.web.hn.andro.movieappiak.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import id.web.hn.andro.movieappiak.R;
import id.web.hn.andro.movieappiak.app.model.tmdb.MovieTMDB;

/**
 * Created by hahn on 05/05/16.
 */
public class DetailActivity extends AppCompatActivity {
    String urlimage = "https://image.tmdb.org/t/p/w185/";
    ImageView imgDetail, imgCollapse;
    TextView txtTitle, txtOverview, txtReleaseDate, txtPopularity, txtVoteCount;
    RatingBar ratingBar;
    Toolbar toolbar;
    private Bundle extras;
    private MovieTMDB data;

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

        //getIntent
        extras = getIntent().getExtras();
        data = (MovieTMDB) extras.getSerializable("data");

        //set data
        Picasso.with(this)
                .load(urlimage + data.getBackdropPath())
                .into(imgCollapse);

        Picasso.with(this)
                .load(urlimage + data.getPosterPath())
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


    }



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if(item.getItemId() == android.R.id.home){
//            getFragmentManager().popBackStack();
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
