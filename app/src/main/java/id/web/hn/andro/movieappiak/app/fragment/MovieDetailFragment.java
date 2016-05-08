package id.web.hn.andro.movieappiak.app.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import id.web.hn.andro.movieappiak.R;
import id.web.hn.andro.movieappiak.activity.DetailActivity;
import id.web.hn.andro.movieappiak.app.model.tmdb.MovieTMDB;

/**
 * A simple {@link Fragment} subclass.
 * belum dipakai karena eh eh karena bermasalah dengan collapsingtoolbar :D
 */
public class MovieDetailFragment extends Fragment {

    private Bundle extras;
    private MovieTMDB data;
    TextView txtTitle;

    public MovieDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        txtTitle = (TextView) rootView.findViewById(R.id.txt_detail_title);

        extras = getActivity().getIntent().getExtras();
        data = (MovieTMDB) extras.getSerializable("data");

        String x = data.getTitle().toString();
        txtTitle.setText(x);

        return rootView;
    }

}
