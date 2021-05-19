package com.example.tmdb.Adapter;

import android.content.Context;
import android.content.Intent;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tmdb.Model.MovieModel;
import com.example.tmdb.Activity.MovieDetailActivity;
import com.example.tmdb.R;
import com.example.tmdb.Retrofit.RetrofitInstance;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieHorizontalAdapter extends RecyclerView.Adapter<MovieHorizontalAdapter.ViewHolder> {
    private List<MovieModel> movieModels;
    private Context context;

    public MovieHorizontalAdapter(Context context, List<MovieModel> movieModels) {
        this.context = context;
        this.movieModels = movieModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.rv_horizontal_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        viewHolder.cardView.setOnClickListener(view -> {
            Intent intent = new Intent(parent.getContext(), MovieDetailActivity.class);
            MovieModel movieModel = new MovieModel();
            movieModel.setId(movieModels.get(viewHolder.getAdapterPosition()).getId());
            movieModel.setTitle(movieModels.get(viewHolder.getAdapterPosition()).getTitle());

            intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movieModel);
            parent.getContext().startActivity(intent);
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieModel movieModel = movieModels.get(position);

        holder.tv_popular_title.setText(movieModel.getTitle());
        Picasso.get().load(RetrofitInstance.BASE_IMG_URL + movieModel.getPoster_path())
                .fit()
                .error(R.drawable.ic_broken_picture)
                .into(holder.iv_popular_poster, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        holder.progressBar.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return movieModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_popular_title;
        public ImageView iv_popular_poster;
        CardView cardView;
        ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_popular_title = itemView.findViewById(R.id.tv_pupular_title);
            iv_popular_poster = itemView.findViewById(R.id.iv_popular_poster);
            cardView = itemView.findViewById(R.id.item_popular);
            progressBar = itemView.findViewById(R.id.pb_horizontal_item_poster);
        }
    }
}
