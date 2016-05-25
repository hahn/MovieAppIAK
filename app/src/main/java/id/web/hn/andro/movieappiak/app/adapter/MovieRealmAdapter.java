package id.web.hn.andro.movieappiak.app.adapter;

import android.app.Activity;
import android.content.Context;
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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import id.web.hn.andro.movieappiak.R;
import id.web.hn.andro.movieappiak.app.model.realm.MovieRealm;
import io.realm.RealmList;

/**
 * Created by hahn on 14/05/16.
 */
public class MovieRealmAdapter extends RecyclerView.Adapter<MovieRealmAdapter.ViewHolder> {
    private RealmList<MovieRealm> movie;
    final String urlImage = "https://image.tmdb.org/t/p/w185/";
    Context context;
    private LayoutInflater layoutInflater;
    private AdapterView.OnItemClickListener onItemClickListener;

    public MovieRealmAdapter(Context context){
        this.context = context;
        this.movie = new RealmList<>();
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext()
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_movie_list, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtListTitle.setText("" + movie.get(position).getTitle());
        Picasso picasso = Picasso.with(context);
        picasso.load(urlImage + movie.get(position).getPosterPath())
                .error(R.mipmap.img_background)
                .into(holder.imgList);

        holder.txtListRelease.setText("Release: " + movie.get(position).getReleaseDate());
        holder.ratingList.setRating(movie.get(position).getVoteAverage() / 2.0f);
        holder.ratingList.setStepSize(0.1f);
        holder.position = position;

    }

    @Override
    public int getItemCount() {
        return movie.size();
    }

    public RealmList<MovieRealm> getDataMovie(){
        return movie;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
        }

        public void onClick(View v){
            if (onItemClickListener != null){
                onItemClickListener.onItemClick(null, v, position, 0);
                Toast.makeText(v.getContext(), "onClick", Toast.LENGTH_SHORT).show();
            }
        }


    }
}
