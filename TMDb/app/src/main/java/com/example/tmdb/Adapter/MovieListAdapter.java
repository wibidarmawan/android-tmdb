package com.example.tmdb.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tmdb.Activity.MovieDetailActivity;
import com.example.tmdb.Model.MovieModel;
import com.example.tmdb.R;
import com.example.tmdb.Retrofit.RetrofitInstance;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {
    private List<MovieModel> movieModelList;
    private Context context;

    public MovieListAdapter(List<MovieModel> movieModelList, Context context) {
        this.movieModelList = movieModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.grid_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        viewHolder.cardView.setOnClickListener(view -> {
            Intent intent = new Intent(parent.getContext(), MovieDetailActivity.class);
            MovieModel movieModel = new MovieModel();
            movieModel.setId(movieModelList.get(viewHolder.getAdapterPosition()).getId());
            movieModel.setTitle(movieModelList.get(viewHolder.getAdapterPosition()).getTitle());

            intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movieModel);
            parent.getContext().startActivity(intent);
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieModel movieModel = movieModelList.get(position);

        holder.tvTitle.setText(movieModel.getTitle());
        Picasso.get().load(RetrofitInstance.BASE_IMG_URL + movieModel.getPoster_path())
                .fit()
                .error(R.drawable.ic_broken_picture)
                .into(holder.ivPoster, new Callback() {
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
        return movieModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView ivPoster;
        public TextView tvTitle;
        public CardView cardView;
        public ProgressBar progressBar;
        public RecyclerView recyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_grid_title);
            ivPoster = itemView.findViewById(R.id.iv_grid_poster);
            cardView = itemView.findViewById(R.id.cv_grid_item);
            progressBar = itemView.findViewById(R.id.pb_grid_item_poster);
        }
    }

    public void refreshAdapter(List<MovieModel> movieModels){
        this.movieModelList.addAll(movieModels);
        notifyItemRangeChanged(0, this.movieModelList.size());
    }
}
