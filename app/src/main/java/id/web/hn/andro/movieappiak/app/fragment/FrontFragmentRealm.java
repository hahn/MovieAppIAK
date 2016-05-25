package id.web.hn.andro.movieappiak.app.fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import id.web.hn.andro.movieappiak.BuildConfig;
import id.web.hn.andro.movieappiak.R;
import id.web.hn.andro.movieappiak.activity.MainActivity;
import id.web.hn.andro.movieappiak.app.adapter.MovieRealmAdapter;
import id.web.hn.andro.movieappiak.app.adapter.MovieTMDBAdapter;
import id.web.hn.andro.movieappiak.app.api.BaseRealmApi;
import id.web.hn.andro.movieappiak.app.model.realm.ModelMovieRealm;
import id.web.hn.andro.movieappiak.app.model.realm.MovieRealm;
import id.web.hn.andro.movieappiak.app.model.realm.RealmInt;
import id.web.hn.andro.movieappiak.app.model.realm.RealmIntDeserializer;
import id.web.hn.andro.movieappiak.app.model.tmdb.MovieTMDB;
import id.web.hn.andro.movieappiak.app.util.ConnectionDetector;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hahn on 14/05/16.
 */
public class FrontFragmentRealm extends Fragment {

    TextView txtTitleFront;
    ImageView imgTitleFront;
    RecyclerView rvListMovieFront;
    RatingBar rating;

    MovieRealmAdapter mAdapter, mAdapterLocal;
    MovieTMDBAdapter movieTMDBAdapter;

    LinearLayout llfront;
    LinearLayoutManager llm;
    ProgressDialog dialog;

    private ConnectionDetector connectionDetector;
    private boolean isInternetConnected = false;

    //realm
    Realm myrealm;

    MovieRealm moviePopularFront;
    RealmList<MovieRealm> movieResults = new RealmList<>();

    private int page;
    final String urlimage = "https://image.tmdb.org/t/p/w185/";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootview = inflater.inflate(R.layout.fragment_movie_front, container, false);

        llm = new LinearLayoutManager(getActivity());
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle(R.string.app_name);
        dialog = new ProgressDialog(getActivity());

        txtTitleFront = (TextView) rootview.findViewById(R.id.txt_title_front);
        imgTitleFront = (ImageView) rootview.findViewById(R.id.img_front);
        rating = (RatingBar) rootview.findViewById(R.id.rating_front);
        rvListMovieFront = (RecyclerView) rootview.findViewById(R.id.rv_list_movie_front);
        llfront = (LinearLayout) rootview.findViewById(R.id.ll_front);

        page = 0;

        //adapter
        mAdapter = new MovieRealmAdapter(getContext());
        mAdapterLocal = new MovieRealmAdapter(getContext());

        movieTMDBAdapter = new MovieTMDBAdapter(getContext());

        connectionDetector = new ConnectionDetector(getContext());
        isInternetConnected = connectionDetector.isInternetConnected();

        //konfigurasi realm
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(getContext()).build();
        myrealm = Realm.getInstance(realmConfig);

        rvListMovieFront.setLayoutManager(llm);

        //itemClickListener
        mAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //dummy dulu aja
                Snackbar.make(rootview, "Diklik", Snackbar.LENGTH_LONG).show();
            }
        });

        return rootview;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(isInternetConnected==true){
//            Toast.makeText(getContext(), "konek", Toast.LENGTH_SHORT).show();
            if(mAdapter.getDataMovie().size() == 0){
                Log.d("onStart", "koneksi: " + isInternetConnected + ", size: " + mAdapter.getDataMovie().size());
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setMessage("Loading. Please wait...");
                dialog.setIndeterminate(true);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                getDataFromAPI();
            }
            else{
                Log.d("onStart", "koneksi: " + isInternetConnected + ", size: " + mAdapter.getDataMovie().size());

            }
        }
        else{
            //tak ada koneksi
            Log.d("onStart", "koneksi: " + isInternetConnected + ", size: " + mAdapter.getDataMovie().size());
            if(mAdapter.getDataMovie().size() !=0){
                Log.d("onStart", "size not 0, koneksi: " + isInternetConnected + ", size: " + mAdapter.getDataMovie().size());
            }
            else{
                Log.d("onStart", "koneksi: " + isInternetConnected + ", size: " + mAdapter.getDataMovie().size());
                Snackbar.make(getView(), "Can't connect to internet. Using cache data", Snackbar.LENGTH_LONG)
                        .setAction("Warning", null).show();
                getDataFromLocal();
            }
        }
        rvListMovieFront.setAdapter(mAdapter);

    }

    public void getDataFromAPI() {
        //hapus dari realm
        RealmResults<MovieRealm> results = myrealm.where(MovieRealm.class).findAll();
        myrealm.beginTransaction();
        results.deleteAllFromRealm();
        myrealm.commitTransaction();

        //fetch data from network
        FetchMovieTask fetchMovieTask = new FetchMovieTask();
        fetchMovieTask.execute(page);
    }

    public void getDataFromLocal(){
        int i = 0;
        List<MovieRealm> mlistLocal = myrealm.where(MovieRealm.class).findAll();
        for(MovieRealm mv: mlistLocal){
            if(i == 0){
                txtTitleFront.setText(mv.getTitle());
                Picasso picasso = Picasso.with(getActivity());
                picasso.setIndicatorsEnabled(true);
                picasso.load(urlimage + mv.getBackdropPath())
                        .error(R.mipmap.img_background)
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .into(imgTitleFront);

                float rt = mv.getVoteAverage() /2;
                rating.setRating(rt);
                rating.setStepSize(0.1f);
                Log.d("rating", "rating " + mv.getTitle() + ": " + rt);
                i++;
            }
            else{
                mAdapter.getDataMovie().add(mv);
                mAdapter.notifyDataSetChanged();
            }
        }

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
            //supaya int[] bisa dibaca sama realm
            GsonBuilder gsonBuilder = new GsonBuilder()
                    .setExclusionStrategies(new ExclusionStrategy() {
                        @Override
                        public boolean shouldSkipField(FieldAttributes f) {
                            return f.getDeclaringClass().equals(RealmObject.class);
                        }

                        @Override
                        public boolean shouldSkipClass(Class<?> clazz) {
                            return false;
                        }
                    });
            //deserialisasi
            gsonBuilder.registerTypeAdapter(new TypeToken<RealmList<RealmInt>>(){
            }.getType(), new RealmIntDeserializer());

            Gson gson = gsonBuilder.create();

            //retrofit
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            BaseRealmApi baseRealmApi = retrofit.create(BaseRealmApi.class);
            Call<ModelMovieRealm> call = baseRealmApi.loadMovie(
                    primaryReleaseYear,
                    sortBy,
                    page,
                    API_KEY
            );
            
            call.enqueue(new Callback<ModelMovieRealm>() {
                @Override
                public void onResponse(Call<ModelMovieRealm> call, Response<ModelMovieRealm> response) {
                    int p;
                    p = response.body().getPage();
                    if(p == 1){
                        moviePopularFront = response.body().getResults().get(0);
                        isiMovieFront(moviePopularFront);

                    }
                    Log.d("onResponse" ," response code: " + response.code());
                    isiListMovie(response.body().getResults());
                }

                @Override
                public void onFailure(Call<ModelMovieRealm> call, Throwable t) {
                    Log.d("onFailure", "response failure");
                    t.printStackTrace();
                    dialog.dismiss();
                }
            });
            return null;
        }

        private void isiListMovie(RealmList<MovieRealm> results) {
            List<MovieTMDB> mtl = new ArrayList<>();
            if(results != null){
                for(MovieRealm mv : results){

                    //masukan ke realm
                    myrealm.beginTransaction();
                    myrealm.copyToRealmOrUpdate(mv);
                    myrealm.commitTransaction();

                    //masukkan ke adapter
//                    Log.d("isiList", "movie: " + mv.getTitle());
//                    MovieTMDB mt = new MovieTMDB();
//                    mt.setAdult(mv.isAdult());
//                    mt.setPosterPath(mv.getPosterPath());
//                    mt.setOverview(mv.getOverview());
//                    mt.setReleaseDate(mv.getReleaseDate());
//                    mt.setId(mv.getId());
//                    mt.setOriginalTitle(mv.getOriginalTitle());
//                    mt.setOriginalLanguage(mv.getOriginalLanguage());
//                    mt.setTitle(mv.getTitle());
//                    mt.setBackdropPath(mv.getBackdropPath());
//                    mt.setPopularity(mv.getPopularity());
//                    mt.setVoteCount(mv.getVoteCount());
//                    mt.setVideo(mv.isVideo());
//                    mt.setVoteAverage(mv.getVoteAverage());
//                    mtl.add(mt);
//                    mt.setGenreIds(mv.getGenreIds().to );
                    mAdapter.getDataMovie().add(mv);
                    mAdapter.notifyDataSetChanged();
                }
//                for(MovieTMDB mt : mtl){
//                    movieTMDBAdapter.getDataMovie().add(mt);
//                    movieTMDBAdapter.notifyDataSetChanged();
//                }
                dialog.dismiss();
            }


        }

        private void isiMovieFront(final MovieRealm movie) {
            txtTitleFront.setText(movie.getTitle());

            rating.setRating(movie.getVoteAverage() / 2.0f);
            rating.setStepSize(0.1f);
            //masukkan gambar pakai picasso
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

        }
    }
}
