package id.web.hn.andro.movieappiak.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.media.Rating;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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

import java.util.ArrayList;
import java.util.List;

import id.web.hn.andro.movieappiak.R;
import id.web.hn.andro.movieappiak.app.model.tmdb.ModelTMDBMovie;
import id.web.hn.andro.movieappiak.app.model.tmdb.MovieTMDB;

/**
 * Created by hahn on 04/05/16.
 */
public class MovieTMDBAdapter extends RecyclerView.Adapter<MovieTMDBAdapter.ViewHolder> {
    private List<MovieTMDB> dataMovie;
    final String urlimage = "https://image.tmdb.org/t/p/w185/";
    Context context;
    private LayoutInflater inflater;

    private AdapterView.OnItemClickListener onItemClickListener;

    public MovieTMDBAdapter(Context context){
        this.context = context;
        this.dataMovie = new ArrayList<>();
        this.inflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

        @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_movie_list, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtListTitle.setText(" " + dataMovie.get(position).getTitle());
        Picasso picasso = Picasso.with(context);
//        picasso.setIndicatorsEnabled(true);
//        picasso.setLoggingEnabled(true);

        picasso.load(urlimage + dataMovie.get(position).getPosterPath())
                .error(R.mipmap.img_background)
                .into(holder.imgList);
//        Picasso.with(context)
//                .load(urlimage + dataMovie.get(position).getPosterPath())
//                .into(holder.imgList);

        holder.txtListRelease.setText("Release: " + dataMovie.get(position).getReleaseDate());
        holder.ratingList.setRating(dataMovie.get(position).getVoteAverage() / 2.0f);
        holder.ratingList.setStepSize(0.1f);
        holder.position = position;

    }

    @Override
    public int getItemCount() {
        return dataMovie.size();
    }

    public List<MovieTMDB> getDataMovie(){
        return dataMovie;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtListTitle, txtListRelease;
        ImageView imgList;
        RatingBar ratingList;
        int position;
        LinearLayout llList;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            txtListTitle = (TextView) itemView.findViewById(R.id.txt_list_title);
            txtListRelease = (TextView) itemView.findViewById(R.id.txt_list_release_date);
            imgList = (ImageView) itemView.findViewById(R.id.img_list);
            ratingList = (RatingBar) itemView.findViewById(R.id.rating_list);
            llList = (LinearLayout) itemView.findViewById(R.id.ll_list_movie);

//            llList.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null){
                onItemClickListener.onItemClick(null, v, position, 0);
//                Toast.makeText(v.getContext(), "onClick", Toast.LENGTH_SHORT).show();
            }

        }


    }
}
