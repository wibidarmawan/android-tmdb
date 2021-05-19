package com.example.tmdb.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tmdb.Adapter.TrailerListAdapter;
import com.example.tmdb.Model.GetMovieResponseModel;
import com.example.tmdb.Model.MovieDetailModel;
import com.example.tmdb.Model.MovieModel;
import com.example.tmdb.Model.VideoResultModel;
import com.example.tmdb.R;
import com.example.tmdb.Retrofit.ApiInterface;
import com.example.tmdb.Retrofit.RetrofitInstance;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";
    int movieId, runtime;
    String title;
    String trailerKey = "";
    ImageView ivPoster, ivBackdrop;
    TextView tvTitle, tvOverview, tvRating, tvYear, tvRuntime;
    MovieModel movieModel;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        tvRating = findViewById(R.id.tv_detail_rating);
        tvYear = findViewById(R.id.tv_detail_year);
        tvRuntime = findViewById(R.id.tv_detail_runtime);
        tvTitle = findViewById(R.id.tv_detail_title);
        tvOverview = findViewById(R.id.tv_detail_overview);
        ivPoster = findViewById(R.id.iv_detail_poster);
        ivBackdrop = findViewById(R.id.iv_detail_backdrop);
        recyclerView = findViewById(R.id.rv_trailer_list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        movieModel = getIntent().getParcelableExtra(EXTRA_MOVIE);
        movieId = movieModel.getId();
        title = movieModel.getTitle();
        getSupportActionBar().setTitle(title);

        ApiInterface apiInterface = RetrofitInstance.getRetrofitInstance().create(ApiInterface.class);
        Call<MovieDetailModel> call = apiInterface.getDetail(movieId, RetrofitInstance.API_KEY, "videos");
        call.enqueue(new Callback<MovieDetailModel>() {
            @Override
            public void onResponse(Call<MovieDetailModel> call, Response<MovieDetailModel> response) {
                MovieDetailModel movieDetailModel = response.body();
                List<VideoResultModel> videoResultModelList = movieDetailModel.getVideoModel().getVideoResultModels();
                TrailerListAdapter trailerListAdapter = new TrailerListAdapter(videoResultModelList, MovieDetailActivity.this);
                recyclerView.setAdapter(trailerListAdapter);

                runtime = movieDetailModel.getRuntime();

                Picasso.get().load(RetrofitInstance.BASE_IMG_URL + movieDetailModel.getPoster_path())
                        .fit()
                        .error(R.drawable.ic_broken_picture)
                        .into(ivPoster);
                Picasso.get().load(RetrofitInstance.BASE_IMG_URL + movieDetailModel.getBackdrop_path())
                        .placeholder(R.drawable.ic_broken_picture)
                        .into(ivBackdrop);
                tvTitle.setText(movieDetailModel.getTitle());
                tvOverview.setText(movieDetailModel.getOverview());
                tvRating.setText(String.valueOf(movieDetailModel.getVote_average()));
                tvYear.setText(movieDetailModel.getRelease_date().substring(0, 4));
                tvRuntime.setText((runtime / 60) + "h" + (runtime % 60) + "m");
            }

            @Override
            public void onFailure(Call<MovieDetailModel> call, Throwable t) {

            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}