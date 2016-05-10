package id.web.hn.andro.movieappiak.app.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.Voice;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import id.web.hn.andro.movieappiak.BuildConfig;
import id.web.hn.andro.movieappiak.R;
import id.web.hn.andro.movieappiak.activity.DetailActivity;
import id.web.hn.andro.movieappiak.activity.MainActivity;
import id.web.hn.andro.movieappiak.app.adapter.MovieTMDBAdapter;
import id.web.hn.andro.movieappiak.app.api.BaseOMDBApi;
import id.web.hn.andro.movieappiak.app.api.BaseTMDBApi;
import id.web.hn.andro.movieappiak.app.model.ModelOMDBMovieDetails;
import id.web.hn.andro.movieappiak.app.model.ModelOMDBMovieSearch;
import id.web.hn.andro.movieappiak.app.model.tmdb.ModelTMDBMovie;
import id.web.hn.andro.movieappiak.app.model.tmdb.MovieTMDB;
import id.web.hn.andro.movieappiak.app.util.ConnectionDetector;
import id.web.hn.andro.movieappiak.app.util.sqlite.MovieDbSQLiteQuery;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFrontFragment extends Fragment {
    TextView txtTitleFront;
    ImageView imgTitleFront;
    RecyclerView rvListMovieFront;
    RatingBar rating;
    MovieTMDBAdapter mAdapter, mAdapterLocal;
    LinearLayout llfront;
    LinearLayoutManager llm;
    ProgressDialog dialog;

    private MovieDbSQLiteQuery movieQuery;
    private ConnectionDetector connectionDetector;
    private boolean isInternetConnected = false;


    private int page, pastVisiblesItems, visibleItemCount, totalItemCount;

    MovieTMDB moviePopularFront; //untuk halaman pertamanya (semacam headline) aja
    List<MovieTMDB> movieResult = new ArrayList<>(); //untuk ambil dari lokal

    final String urlimage = "https://image.tmdb.org/t/p/w185/";

    public MovieFrontFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_movie_front, container, false);
        llm = new LinearLayoutManager(getActivity());
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle(R.string.app_name);
        dialog = new ProgressDialog(getActivity());

        //inisiasi widget
        txtTitleFront = (TextView) rootview.findViewById(R.id.txt_title_front);
        imgTitleFront = (ImageView) rootview.findViewById(R.id.img_front);
        rating = (RatingBar) rootview.findViewById(R.id.rating_front);
        rvListMovieFront = (RecyclerView) rootview.findViewById(R.id.rv_list_movie_front);
        llfront = (LinearLayout) rootview.findViewById(R.id.ll_front);

        page = 0;
        mAdapter = new MovieTMDBAdapter(getContext());
        mAdapterLocal = new MovieTMDBAdapter(getContext());
        connectionDetector = new ConnectionDetector(getContext());
        isInternetConnected = connectionDetector.isInternetConnected();

        movieQuery = new MovieDbSQLiteQuery(getContext());

        rvListMovieFront.setLayoutManager(llm);
//        rvListMovieFront.setAdapter(mAdapter);

        //toolbar

        mAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("data", mAdapter.getDataMovie().get(position));
                startActivity(intent);
            }

        });

//        untuk clicklistener bagian atas aja
        llfront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("data", moviePopularFront);
                startActivity(intent);

            }
        });

        return rootview;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.front_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_search){
            gotoSearchFragment();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        if(isInternetConnected == true){


            //Ada koneksi. cek apakah adapter berisi atau tidak
            if(mAdapter.getDataMovie().size() == 0){
                //adapter kosong. ambil API
                Log.d("connection", "hasil: " + isInternetConnected + ", size: " + mAdapter.getDataMovie().size());
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setMessage("Loading. Please wait...");
                dialog.setIndeterminate(true);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                getDataFromAPI();

            }else{
                //konek tapi adapter berisi
                Log.d("connection", "ada koneksi tapi adapter berisi.");
            }
        }else{
            //tidak konek. cek apa adapter berisi atau tidak
            if(mAdapter.getDataMovie().size() !=0){
                Log.d("connection", "tidak ada koneksi tapi adapter berisi.");
             //tidak konek, isi adapter adaan. ya sudah
            } else {
                //tidak konek, adapter kosong
                Log.d("connection", "tidak ada koneksi, adapter kosong.");
                Snackbar.make(getView(), "Can't connect to internet. Using cache data", Snackbar.LENGTH_LONG)
                        .setAction("Warning", null).show();
                getDataFromLocal();
            }

        }
        rvListMovieFront.setAdapter(mAdapter);

    }


    //get data from local DB
    private void getDataFromLocal() {

        int i = 0;
        //ambil data dari database
        movieQuery.openDb();
        movieResult = movieQuery.selectMovie();
        for (MovieTMDB mv : movieResult){

            if(i == 0){
                moviePopularFront = mv;
                txtTitleFront.setText(mv.getTitle());
                Picasso picasso = Picasso.with(getActivity());
                picasso.setIndicatorsEnabled(true);
                picasso.load(urlimage + mv.getBackdropPath())
                        .error(R.mipmap.img_background)
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .into(imgTitleFront);

//                Picasso.with(getActivity())
//                        .load(urlimage + mv.getBackdropPath())
//                        .into(imgTitleFront);
                float rt = mv.getVoteAverage() /2;
                rating.setRating(rt);
                rating.setStepSize(0.1f);
                Log.d("rating", "rating " + mv.getTitle() + ": " + rt);
                i++;
            }else{
                mAdapter.getDataMovie().add(mv);
                mAdapter.notifyDataSetChanged();
            }

        }

        movieQuery.closeDb();

    }


    public void getDataFromAPI(){
        //bersihkan dulu
        movieQuery.openDb();
        movieQuery.clearTableMovie();
        movieQuery.closeDb();
        //hapus dulu isi adapternya
        FetchMovieTask fetchMovieTask = new FetchMovieTask();
        fetchMovieTask.execute(page);

    }

    public void gotoSearchFragment(){
        getFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.container_fragment,
                        new MovieSearchFragment(),
                        MovieSearchFragment.class.getSimpleName())
                .addToBackStack(null).commit();
    }

    public class FetchMovieTask extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer... params) {
            final String BASE_URL = "http://api.themoviedb.org/3/";
            int primaryReleaseYear;
            String sortBy = "popularity.desc";
            String API_KEY = BuildConfig.TMDB_API_KEY;
            int page = params[0];

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            Date thisYear = new Date();
            String year = sdf.format(thisYear);
            primaryReleaseYear = Integer.parseInt(year);

            //update page
            page++;

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            BaseTMDBApi baseTMDBApi = retrofit.create(BaseTMDBApi.class);
            Call<ModelTMDBMovie> call = baseTMDBApi.loadMovie(
                    primaryReleaseYear,
                    sortBy,
                    page,
                    API_KEY
            );
            Log.d("onBackground", "Page: " +page);

            call.enqueue(new Callback<ModelTMDBMovie>() {
                @Override
                public void onResponse(Call<ModelTMDBMovie> call, Response<ModelTMDBMovie> response) {
                    Log.d("onResponse", "response Asyncs " + response.message());

                    int p = response.body().getPage();
                    if(p == 1){
                        moviePopularFront = response.body().getResults().get(0);
                        isiMovieFront(moviePopularFront);

                    }

                    isiListMovie(response.body().getResults());

                }

                @Override
                public void onFailure(Call<ModelTMDBMovie> call, Throwable t) {
                    Log.d("onFailure", "response failure");
                    dialog.dismiss();
                }
            });
            //

        return null;
        }

        private void isiListMovie(List<MovieTMDB> results) {
            movieQuery.openDb();
            int i = 0;
            MovieTMDB mv;
            if(results != null){
                for(MovieTMDB mt : results){
                    mAdapter.getDataMovie().add(mt);
                    mAdapter.notifyDataSetChanged();
                    movieQuery.insertMovie(mt);
                }
                //close db
                movieQuery.closeDb();
                dialog.dismiss();
            }

        }

        private void isiMovieFront(final MovieTMDB movie){
//            movieQuery.openDb();

            txtTitleFront.setText(movie.getTitle());

            Picasso picasso = Picasso.with(getActivity());
//            picasso.setIndicatorsEnabled(true);
            picasso.load(urlimage + movie.getBackdropPath())
                    .error(R.mipmap.img_background)
                    .into(imgTitleFront, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    Log.d("Picasso", "error. try again");
                    Picasso.with(getActivity())
                            .load(urlimage + movie.getBackdropPath())
                            .error(R.mipmap.img_background)
                            .into(imgTitleFront, new com.squareup.picasso.Callback() {
                                @Override
                                public void onSuccess() {
                                    Log.d("Picasso", "Sukses kali kedua");

                                }

                                @Override
                                public void onError() {
                                    Log.d("Picasso", "can't fetch image");
                                }
                            });
                }
            });
//            picasso.setLoggingEnabled(true);

//
//            Picasso.with(getActivity())
//                    .load(urlimage + movie.getBackdropPath())
//                    .networkPolicy(NetworkPolicy.OFFLINE)
//                        .into(imgTitleFront, new com.squareup.picasso.Callback() {
//                            @Override
//                            public void onSuccess() {
//
//                            }
//
//                            @Override
//                            public void onError() {
//                                Log.d("Picasso", "error. try again");
//                                Picasso.with(getActivity())
//                                        .load(urlimage + movie.getBackdropPath())
//                                        .error(R.drawable.imgdum)
//                                        .into(imgTitleFront, new com.squareup.picasso.Callback() {
//                                            @Override
//                                            public void onSuccess() {
//                                                Log.d("Picasso", "Sukses kali kedua");
//
//                                            }
//
//                                            @Override
//                                            public void onError() {
//                                                Log.d("Picasso", "can't fetch image");
//                                            }
//                                        });
//                            }
//                        });
//




            rating.setRating(movie.getVoteAverage() / 2.0f);
            rating.setStepSize(0.1f);

//            movieQuery.insertMovie(movie);
//            movieQuery.closeDb();
        }


    }

}
