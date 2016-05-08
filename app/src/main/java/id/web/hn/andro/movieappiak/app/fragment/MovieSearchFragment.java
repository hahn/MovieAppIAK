package id.web.hn.andro.movieappiak.app.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.web.hn.andro.movieappiak.BuildConfig;
import id.web.hn.andro.movieappiak.R;
import id.web.hn.andro.movieappiak.activity.DetailActivity;
import id.web.hn.andro.movieappiak.activity.MainActivity;
import id.web.hn.andro.movieappiak.app.adapter.MovieTMDBAdapter;
import id.web.hn.andro.movieappiak.app.api.BaseTMDBApi;
import id.web.hn.andro.movieappiak.app.model.tmdb.ModelTMDBMovie;
import id.web.hn.andro.movieappiak.app.model.tmdb.MovieTMDB;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieSearchFragment extends Fragment {
    private EditText edtSearch;
    private RecyclerView rvListSearch;
    private LinearLayoutManager llm;
    private MovieTMDBAdapter mAdapter;
    private static final int LIMIT = 20;
    private int page, pastVisiblesItems, visibleItemCount, totalItemCount;
    private boolean loading;

    int totpage = 0;

    public MovieSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movie_search, container, false);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Search Movie");
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        page = 0;

        edtSearch = (EditText) rootView.findViewById(R.id.edt_search_movie);

        edtSearch.setOnKeyListener(
                new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        String movie = "";
                        loading = true;

                        if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                            movie = edtSearch.getText().toString().trim();
                            searchMovie(movie, false);
                            return true;
                        }
                        return false;
                    }
                }
        );

        mAdapter = new MovieTMDBAdapter(getContext());

        rvListSearch = (RecyclerView) rootView.findViewById(R.id.rv_list_movie_search);
        llm = new LinearLayoutManager(getContext());

        rvListSearch.setLayoutManager(llm);
        rvListSearch.setAdapter(mAdapter);

        rvListSearch.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = llm.getChildCount();
                totalItemCount = llm.getItemCount();
                pastVisiblesItems = llm.findFirstVisibleItemPosition();

                if(((visibleItemCount + pastVisiblesItems) >= totalItemCount)){
                    loading = true;
                }

                if(loading){
                    Log.d("searching", "searching. loading: " + loading);
                    if (((visibleItemCount + pastVisiblesItems) >= totalItemCount)
                            && (totalItemCount >= LIMIT) && (totpage >= page)){
                    searchMovie(edtSearch.getText().toString().trim(), true);
                    }
                } else{
                    Log.d("searching", "loading false: " + loading);
                    loading = false;
                }
            }


        });

        mAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("data", mAdapter.getDataMovie().get(position));
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            getFragmentManager().popBackStack();
        }

        return super.onOptionsItemSelected(item);
    }

    public void searchMovie(String movie, boolean isLoadMore){
        page += 1;
        loading = false;
        FetchSearchMovieTask movieSearchTask = new FetchSearchMovieTask();
        movieSearchTask.execute(movie, String.valueOf(page), String.valueOf(isLoadMore));
        AsyncTask.Status stat = movieSearchTask.getStatus();
//        Log.d("status AsyncTask", "page: " + page + " status: " + stat.toString());
    }

    private class FetchSearchMovieTask extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... params) {
//            Log.d("doInBackgroud", "searching movie, page: " + params[1]);

            final String BASE_URL = "http://api.themoviedb.org/3/";
            String query = params[0];
            int page = Integer.parseInt(params[1]);
            String API_KEY = BuildConfig.TMDB_API_KEY;
            boolean isloadmore = Boolean.parseBoolean(params[2]);

            if(!isloadmore){
                mAdapter.getDataMovie().clear();
            }

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            BaseTMDBApi baseTMDBApi = retrofit.create(BaseTMDBApi.class);
            Call<ModelTMDBMovie> call = baseTMDBApi.searchMovie(
                    query,
                    page,
                    API_KEY
            );

            call.enqueue(new Callback<ModelTMDBMovie>() {
                List<MovieTMDB> mv = new ArrayList<>();
                @Override
                public void onResponse(Call<ModelTMDBMovie> call, Response<ModelTMDBMovie> response) {
//                    Log.d("onResponse", "response Asyncs " + response.body().getTotalPages());
                    mv = response.body().getResults();
                    totpage = response.body().getTotalPages();

                    for(MovieTMDB m : mv){
                        mAdapter.getDataMovie().add(m);
                        mAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<ModelTMDBMovie> call, Throwable t) {
                    Log.d("onResponse", "Failure bray" );
                    t.printStackTrace();
                }
            });

            return null;
        }
    }
}
